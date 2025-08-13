package net.alek.buttonclicker.transfer.request.type;

import net.alek.buttonclicker.data.asset.Audio;
import net.alek.buttonclicker.data.asset.Fonts;
import net.alek.buttonclicker.data.asset.Images;
import net.alek.buttonclicker.data.model.AppData;

import net.alek.buttonclicker.transfer.request.RequestBus;
import net.alek.buttonclicker.transfer.request.payload.SettingsFilePayload;
import net.alek.buttonclicker.transfer.request.payload.SoundManagerClassPayload;
import java.util.concurrent.CompletableFuture;

public enum Request {
    GET_APPDATA(AppData.class),
    GET_AUDIO(Audio.class),
    GET_FONTS(Fonts.class),
    GET_IMAGES(Images.class),
    GET_SOUND_MANAGER(SoundManagerClassPayload.class),
    GET_SETTINGS_FILE(SettingsFilePayload.class);

    private final Class<? extends Record> responseClass;
    private static final RequestBus BUS = new RequestBus();

    Request(Class<? extends Record> responseClass) {
        if (!responseClass.isRecord()) {
            throw new IllegalArgumentException("Response class must be a record: " + responseClass.getName());
        }
        this.responseClass = responseClass;
    }

    public void handle(java.util.function.Supplier<? extends Record> handler) {
        BUS.handle(this, handler);
    }

    public <R extends Record> RequestFuture<R> request() {
        return request(0, 0);
    }

    public <R extends Record> RequestFuture<R> request(int retries, int timeoutSeconds) {
        CompletableFuture<?> future = BUS.requestAsync(this, retries, timeoutSeconds);
        return new RequestFuture<>((CompletableFuture<R>) future);
    }

    public Class<? extends Record> getResponseClass() {
        return responseClass;
    }

    public static void shutdown() {
        BUS.shutdown();
    }
}