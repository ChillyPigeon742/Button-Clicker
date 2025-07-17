package net.alek.buttonclicker.transfer.request;

import net.alek.buttonclicker.data.model.AppData;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum Request {
    GET_APPDATA(AppData.class);

    private final Class<? extends Record> responseClass;
    private static final RequestBus BUS = new RequestBus();

    Request(Class<? extends Record> responseClass) {
        if (!responseClass.isRecord()) {
            throw new IllegalArgumentException("Response class must be a record: " + responseClass.getName());
        }
        this.responseClass = responseClass;
    }

    public void handle(Supplier<? extends Record> handler) {
        BUS.handle(this, handler);
    }

    public RequestFuture<?> request() {
        return request(0, 0);
    }

    public RequestFuture<?> request(int retries, int timeoutSeconds) {
        CompletableFuture<?> future = BUS.requestAsync(this, retries, timeoutSeconds);
        return new RequestFuture<>(future);
    }

    public Class<? extends Record> getResponseClass() {
        return responseClass;
    }

    public static class RequestFuture<R> {
        private final CompletableFuture<R> future;

        public RequestFuture(CompletableFuture<R> future) {
            this.future = future;
        }

        public CompletableFuture<R> future() {
            return future;
        }

        public R await() {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException("Failed to await request", e);
            }
        }

        public RequestFuture<R> then(Consumer<R> onSuccess) {
            CompletableFuture<R> newFuture = future.thenApply(res -> {
                onSuccess.accept(res);
                return res;
            });
            return new RequestFuture<>(newFuture);
        }

        public RequestFuture<R> exceptionally(Consumer<Throwable> onError) {
            CompletableFuture<R> newFuture = future.exceptionally(ex -> {
                onError.accept(ex);
                return null;
            });
            return new RequestFuture<>(newFuture);
        }
    }
}