package net.alek.buttonclicker.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import net.alek.buttonclicker.ui.components.ATimer;

public class SoundManager {
    private MediaPlayer musicAudioPlayer;
    private MediaPlayer sMusicAudioPlayer;
    private MediaPlayer sfxAudioPlayer;
    private double masterVolume;
    private double musicVolume;
    private double sfxVolume;
    private byte masterVolumeUSER;
    private byte musicVolumeUSER;
    private byte sfxVolumeUSER;
    private byte musicDelay;
    private final ATimer musicDelayTimer = new ATimer();

    public static void loadTrack(int type, Media track){
        if(type==1){
            sfxAudioPlayer = new MediaPlayer(track);
            refreshVolume();
        }else if(type==2){
            musicAudioPlayer = new MediaPlayer(track);
            refreshVolume();
        }else if(type==3){
            sMusicAudioPlayer = new MediaPlayer(track);
            refreshVolume();
        }
    }

    public void refreshVolume() {
        if (sfxAudioPlayer != null && sfxAudioPlayer.getStatus() != MediaPlayer.Status.DISPOSED) {
            sfxAudioPlayer.setVolume(sfxVolume);
        }
        if (musicAudioPlayer != null && musicAudioPlayer.getStatus() != MediaPlayer.Status.DISPOSED) {
            musicAudioPlayer.setVolume(musicVolume);
        }
        if (sMusicAudioPlayer != null && sMusicAudioPlayer.getStatus() != MediaPlayer.Status.DISPOSED) {
            sMusicAudioPlayer.setVolume(musicVolume);
        }
    }

    public void setMasterVolume(byte value) {
        masterVolume = value / 100.0;
        musicVolume = masterVolume * musicVolume;
        sfxVolume = masterVolume * sfxVolume;

        masterVolumeUSER = value;

        refreshVolume();
    }

    public void setMusicVolume(byte value) {
        musicVolume = masterVolume * (value / 100.0);
        musicVolumeUSER = value;

        refreshVolume();
    }

    public void setSFXVolume(byte value) {
        sfxVolume = masterVolume * (value / 100.0);
        sfxVolumeUSER = value;

        refreshVolume();
    }

    public void setMusicDelay(byte value) {
        musicDelay = value;
        musicDelayTimer.setInterval(musicDelay);
    }

    public Double getSFXVolume() {
        return sfxVolume;
    }

    public Double getMusicVolume() {
        return musicVolume;
    }

    public Double getMasterVolume() {
        return masterVolume;
    }

    public static MediaPlayer getMusicAudioPlayer(){
        return musicAudioPlayer;
    }

    public MediaPlayer getSMusicAudioPlayer(){
        return sMusicAudioPlayer;
    }

    public MediaPlayer getSFXAudioPlayer(){
        return sfxAudioPlayer;
    }

    public static ATimer getMusicDelayTimer(){
        return musicDelayTimer;
    }

    public Byte getMusicDelay(){
        return musicDelay;
    }
}
