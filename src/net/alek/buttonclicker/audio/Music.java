package net.alek.buttonclicker.audio;

import net.alek.buttonclicker.data.asset.Audio;
import net.alek.buttonclicker.transfer.request.Request;
import net.alek.buttonclicker.ui.MenuManager;
import net.alek.buttonclicker.ui.components.ATimer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Music {
    private MediaPlayer musicAudioPlayer;
    private final ATimer musicDelayTimer = new ATimer();
    private final Map<Track, TrackInfo> trackMap = new HashMap<>();

    private byte musicVolume;
    private byte musicDelay;
    private boolean finishedPlaying = false;
    
    public Music(){
        Audio audio = (Audio) Request.GET_AUDIO.request().await().get();
        net.alek.buttonclicker.data.asset.Music music = audio.music();

        trackMap.put(Track.MENU1, new TrackInfo(music.menu1(), Track.MENU2, false));
        trackMap.put(Track.MENU2, new TrackInfo(music.menu2(), Track.MENU1, false));
        trackMap.put(Track.LOADING, new TrackInfo(music.loading(), null, false));
        trackMap.put(Track.GAME1, new TrackInfo(music.game1(), Track.GAME2, false));
        trackMap.put(Track.GAME2, new TrackInfo(music.game2(), Track.GAME1, false));
        trackMap.put(Track.PAUSE, new TrackInfo(music.pause(), Track.PAUSE, true));
        trackMap.put(Track.SHOP, new TrackInfo(music.shop(), Track.SHOP, true));
    }

    public void chooseMusic(){
        int number = new Random().nextInt(1, 3);
        playMusic(number == 1 ? Track.MENU1 : Track.MENU2);
    }

    public void playMusic(Track track) {
        TrackInfo info = trackMap.get(track);
        if (info == null) return;

        stopMusic();
        finishedPlaying = info.nextTrack == null;

        loadTrack(info.audio);

        musicAudioPlayer.setOnEndOfMedia(() -> {
            finishedPlaying = true;
            musicDelayTimer.setInterval(musicDelay);

            musicDelayTimer.setTask(() -> {
                if (info.conditionalLoop) {
                    if (!MenuManager.isMenuOpen("Game")) {
                        playMusic(info.nextTrack);
                    }
                } else if (info.nextTrack != null) {
                    playMusic(info.nextTrack);
                }
            });

            musicDelayTimer.start();
        });

        musicAudioPlayer.play();
    }

    public void resumeMusic(){
        if(getMusicAudioPlayer().getStatus()== MediaPlayer.Status.PAUSED){
            getMusicAudioPlayer().play();
        }
        if(isFinishedPlaying()){
            getMusicDelayTimer().resume();
        }
    }

    public void pauseMusic(){
        if(getMusicAudioPlayer().getStatus()==MediaPlayer.Status.PLAYING){
            getMusicAudioPlayer().pause();
        }else if(isFinishedPlaying()){
            getMusicDelayTimer().pause();
        }
    }

    public void stopMusic(){
        getMusicAudioPlayer().stop();
        getMusicDelayTimer().stop();
    }

    public void loadTrack(SoundClip track){
        musicAudioPlayer = new MediaPlayer(track);
        refreshVolume();
    }

    public void refreshVolume() {
        if (musicAudioPlayer != null && musicAudioPlayer.getStatus() != MediaPlayer.Status.DISPOSED) {
            musicAudioPlayer.setVolume(musicVolume);
        }
    }

    public void setMusicVolume(byte value) {
        musicVolume = value;
        refreshVolume();
    }
    
    public void setMusicDelay(byte value) {
        musicDelay = value;
        musicDelayTimer.setInterval(musicDelay);
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public MediaPlayer getMusicAudioPlayer(){
        return musicAudioPlayer;
    }

    public ATimer getMusicDelayTimer(){
        return musicDelayTimer;
    }

    public byte getMusicDelay(){
        return musicDelay;
    }

    public boolean isFinishedPlaying(){
        return finishedPlaying;
    }

    private static class TrackInfo {
        SoundClip audio;
        Track nextTrack;
        boolean conditionalLoop;

        TrackInfo(SoundClip audio, Track nextTrack, boolean conditionalLoop) {
            this.audio = audio;
            this.nextTrack = nextTrack;
            this.conditionalLoop = conditionalLoop;
        }
    }
}
