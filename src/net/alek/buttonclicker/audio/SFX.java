package net.alek.buttonclicker.audio;

import javafx.scene.media.Media;

import java.util.Objects;

public class SFX {
    public static Media select;
    public static Media click;
    public static Media purchase;
    public static Media declined;

    public static void playSFX(String sfxName) {
        if(Objects.equals(sfxName, "select")){
            SoundManager.loadTrack(1, select);
            SoundManager.getSFXAudioPlayer().play();
        }else if(Objects.equals(sfxName, "click")){
            SoundManager.loadTrack(1, click);
            SoundManager.getSFXAudioPlayer().play();
        }else if(Objects.equals(sfxName, "purchase")){
            SoundManager.loadTrack(1, purchase);
            SoundManager.getSFXAudioPlayer().play();
        }else if(Objects.equals(sfxName, "declined")){
            SoundManager.loadTrack(1, declined);
            SoundManager.getSFXAudioPlayer().play();
        }
    }
}
