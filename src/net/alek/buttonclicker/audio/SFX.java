package net.alek.buttonclicker.audio;

import net.alek.buttonclicker.data.asset.Audio;
import net.alek.buttonclicker.transfer.request.Request;

import java.util.HashMap;
import java.util.Map;

public class SFX {
    private MediaPlayer sfxAudioPlayer;
    private final Map<Track, TrackInfo> trackMap = new HashMap<>();
    private double sfxVolume;

    public SFX() {
        Audio audio = (Audio) Request.GET_AUDIO.request().await().get();
        net.alek.buttonclicker.data.asset.SFX sfx = audio.sfx();

        trackMap.put(Track.SELECT, new TrackInfo(sfx.select()));
        trackMap.put(Track.CLICK, new TrackInfo(sfx.click()));
        trackMap.put(Track.PURCHASE, new TrackInfo(sfx.purchase()));
        trackMap.put(Track.DECLINED, new TrackInfo(sfx.declined()));
    }

    private void loadTrack(Media track){
        if (sfxAudioPlayer != null) {
            sfxAudioPlayer.dispose();
        }
        sfxAudioPlayer = new MediaPlayer(track);
        refreshVolume();
    }

    public void playSFX(Track track) {
        TrackInfo info = trackMap.get(track);
        if (info == null) return;

        loadTrack(info.media);
        sfxAudioPlayer.play();
    }

    public void refreshVolume() {
        if (sfxAudioPlayer != null && sfxAudioPlayer.getStatus() != MediaPlayer.Status.DISPOSED) {
            sfxAudioPlayer.setVolume(sfxVolume);
        }
    }

    public void setSFXVolume(double value) {
        sfxVolume = value;
        refreshVolume();
    }

    public double getSFXVolume() {
        return sfxVolume;
    }

    public MediaPlayer getSFXAudioPlayer(){
        return sfxAudioPlayer;
    }

    private static class TrackInfo {
        Media media;
        TrackInfo(Media media) {this.media = media;}
    }
}
