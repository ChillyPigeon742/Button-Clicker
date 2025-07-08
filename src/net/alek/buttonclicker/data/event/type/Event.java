package net.alek.buttonclicker.data.event.type;

public enum Event {
    START_APP(null),
    LOAD_GAME(null),
    CLOSE_APP(null);

    private final Class<? extends Record> payloadType;

    Event(Class<? extends Record> payloadType) {
        this.payloadType = payloadType;
    }

    public Class<? extends Record> getPayloadType() {
        return payloadType;
    }
}