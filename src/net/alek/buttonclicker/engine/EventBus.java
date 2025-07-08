package net.alek.buttonclicker.engine;

import net.alek.buttonclicker.data.event.type.Event;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class EventBus {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private record EventKey(Event event, Class<? extends Record> payloadType) {}

    private final Map<EventKey, List<Consumer<?>>> subscribers = new ConcurrentHashMap<>();

    public <T extends Record> void subscribe(Event event, Consumer<T> handler) {
        Class<T> payloadType = (Class<T>) event.getPayloadType();
        EventKey key = new EventKey(event, payloadType);
        subscribers
                .computeIfAbsent(key, k -> new CopyOnWriteArrayList<>())
                .add(handler);
    }

    public void publish(Event event, Record payload) {
        if (event == null) throw new IllegalArgumentException("Event cannot be null");

        Class<? extends Record> payloadType = event.getPayloadType();
        if (payload != null && !payloadType.isInstance(payload)) {
            throw new IllegalArgumentException("Payload type mismatch for event " + event);
        }

        EventKey key = new EventKey(event, payloadType);
        List<Consumer<?>> handlers = subscribers.get(key);

        if (handlers != null) {
            for (Consumer<?> handler : handlers) {
                executor.submit(() -> {
                    @SuppressWarnings("unchecked")
                    Consumer<Record> typedHandler = (Consumer<Record>) handler;
                    typedHandler.accept(payload);
                });
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}