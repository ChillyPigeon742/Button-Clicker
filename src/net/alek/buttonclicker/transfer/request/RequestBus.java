package net.alek.buttonclicker.transfer.request;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class RequestBus {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<Request, Supplier<? extends Record>> handlers = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void handle(Request request, Supplier<? extends Record> handler) {
        handlers.put(request, handler);
    }

    @SuppressWarnings("unchecked")
    public <R extends Record> CompletableFuture<R> requestAsync(Request request, int retries, int timeoutSeconds) {
        Supplier<?> rawHandler = handlers.get(request);
        if (rawHandler == null) {
            CompletableFuture<R> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalStateException("No handler registered for " + request));
            return failed;
        }

        Supplier<R> handler = (Supplier<R>) rawHandler;

        CompletableFuture<R> resultFuture = new CompletableFuture<>();
        doAttempt(request, handler, retries, timeoutSeconds, resultFuture);
        return resultFuture;
    }

    private <R extends Record> void doAttempt(Request request, Supplier<R> handler, int retriesLeft, int timeoutSeconds, CompletableFuture<R> resultFuture) {
        CompletableFuture<R> future = CompletableFuture.supplyAsync(() -> {
            R res = handler.get();

            if (!request.getResponseClass().isInstance(res)) {
                throw new CompletionException(new ClassCastException(
                        "Response for " + request + " must be of type " + request.getResponseClass().getName()));
            }

            return res;
        }, executor);

        CompletableFuture<R> timeoutFuture = failAfter(timeoutSeconds);

        CompletableFuture.anyOf(future, timeoutFuture).whenComplete((ignored, err) -> {
            if (resultFuture.isDone()) return;

            if (future.isDone() && !future.isCompletedExceptionally()) {
                future.whenComplete((r, e) -> {
                    if (e == null) resultFuture.complete(r);
                    else {
                        if (retriesLeft > 0) {
                            doAttempt(request, handler, retriesLeft - 1, timeoutSeconds, resultFuture);
                        } else {
                            resultFuture.completeExceptionally(e);
                        }
                    }
                });
            } else {
                if (retriesLeft > 0) {
                    doAttempt(request, handler, retriesLeft - 1, timeoutSeconds, resultFuture);
                } else {
                    if (err != null) {
                        resultFuture.completeExceptionally(err);
                    } else {
                        resultFuture.completeExceptionally(new TimeoutException("Request " + request + " timed out after retries"));
                    }
                }
            }
        });
    }

    private <R> CompletableFuture<R> failAfter(int timeoutSeconds) {
        CompletableFuture<R> promise = new CompletableFuture<>();
        if (timeoutSeconds <= 0) {
            return promise;
        }
        scheduler.schedule(() -> promise.completeExceptionally(
                        new TimeoutException("Operation timed out after " + timeoutSeconds + " seconds")),
                timeoutSeconds, TimeUnit.SECONDS);
        return promise;
    }
}