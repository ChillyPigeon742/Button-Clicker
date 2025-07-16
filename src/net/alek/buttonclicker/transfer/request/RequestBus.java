package net.alek.buttonclicker.transfer.request;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class RequestBus {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<Request, Supplier<?>> handlers = new ConcurrentHashMap<>();

    public <R> void handle(Request request, Supplier<R> handler) {
        handlers.put(request, handler);
    }
    
    public <R> CompletableFuture<R> requestAsync(Request request) {
        Supplier<?> handler = handlers.get(request);
        if (handler == null) {
            CompletableFuture<R> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalStateException("No handler registered for " + request));
            return failed;
        }

        return CompletableFuture.supplyAsync(() -> {
            Object result = handler.get();
            if (!request.getResponseClass().isInstance(result)) {
                throw new CompletionException(new ClassCastException(
                        "Response for " + request + " must be of type " + request.getResponseClass()));
            }
            return (R) result;
        }, executor);
    }
}