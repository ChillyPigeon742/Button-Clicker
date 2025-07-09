package net.alek.buttonclicker.loaders;

import javafx.scene.media.Media;
import net.alek.buttonclicker.data.Audio;
import net.alek.buttonclicker.data.Music;
import net.alek.buttonclicker.data.SFX;
import net.alek.buttonclicker.data.event.type.DeliveryMode;
import net.alek.buttonclicker.data.event.type.Event;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.utilities.read.ReadUtility;

public class AudioLoader {
    private static SFX sfx;
    private static Music music;
    private static Audio audio;

    static {
        Event.LOAD_GAME.subscribe(DeliveryMode.SYNC, ignored -> loadAudioData());
        Event.LOAD_GAME.subscribe(DeliveryMode.SYNC, ignored -> unloadAudioData());
    }

    public static Audio getAudio() {
        return audio;
    }

    private static void loadAudioData() {
        sfx = loadSFXAudioData();
        music = loadMusicAudioData();
        audio = new Audio(sfx, music);
    }

    private static void unloadAudioData() {
        sfx = null;
        music = null;
        audio = null;
    }

    private static Media loadMedia(String path, Media fallback) {
        try {
            var resource = ReadUtility.class.getResource(path);
            if (resource != null) return new Media(resource.toExternalForm());
            else {
                LoggingService.Logger.error("Audio not found: " + path);
                return fallback;
            }
        } catch (Exception e) {
            return fallback;
        }
    }

    private static SFX loadSFXAudioData() {
        Media fallback = null;

        return new SFX(
                loadMedia("/assets/buttonclicker/audio/sfx/select.mp3", fallback),
                loadMedia("/assets/buttonclicker/audio/sfx/click.mp3", fallback),
                loadMedia("/assets/buttonclicker/audio/sfx/purchase.mp3", fallback),
                loadMedia("/assets/buttonclicker/audio/sfx/declined.mp3", fallback)
        );
    }

    private static Music loadMusicAudioData() {
        Media fallback = null;

        return new Music(
                loadMedia("/assets/buttonclicker/audio/music/menu/menu1.mp3", fallback),
                loadMedia("/assets/buttonclicker/audio/music/menu/menu2.mp3", fallback),
                loadMedia("/assets/buttonclicker/audio/music/menu/loading.mp3", fallback),
                loadMedia("/assets/buttonclicker/audio/music/game/game1.mp3", fallback),
                loadMedia("/assets/buttonclicker/audio/music/game/game2.mp3", fallback),
                loadMedia("/assets/buttonclicker/audio/music/game/pause.mp3", fallback),
                loadMedia("/assets/buttonclicker/audio/music/game/shop.mp3", fallback)
        );
    }
}