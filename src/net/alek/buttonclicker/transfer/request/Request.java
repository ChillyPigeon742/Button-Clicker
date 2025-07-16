package net.alek.buttonclicker.transfer.request;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public enum Request {
    GET_VOLUME(Integer.class),
    GET_PLAYER_NAME(String.class),
    IS_MUTED(Boolean.class);

    private final Class<?> responseClass;
    private static final RequestBus BUS = new RequestBus();

    Request(Class<?> responseClass) {
        this.responseClass = responseClass;
    }

    public <R> void handle(Supplier<R> handler) {
        BUS.handle(this, handler);
    }

    public <R> RequestFuture<R> request() {
        CompletableFuture<R> future = BUS.requestAsync(this);
        return new RequestFuture<>(future);
    }

    public Class<?> getResponseClass() {
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
    }
}