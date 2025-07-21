package net.alek.buttonclicker.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import net.alek.buttonclicker.data.asset.Audio;
import net.alek.buttonclicker.transfer.request.Request;
import net.alek.buttonclicker.ui.MenuManager;
import net.alek.buttonclicker.ui.components.ATimer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Music {
    private MediaPlayer musicAudioPlayer;
    private MediaPlayer sMusicAudioPlayer;
    private final ATimer musicDelayTimer = new ATimer();

    private final Map<Track, TrackInfo> trackMap = new HashMap<>();

    private double musicVolume;
    private byte musicDelay;
    private boolean finishedPlaying = false;
    
    public Music(){
        Audio audio = (Audio) Request.GET_AUDIO.request().await().get();
        net.alek.buttonclicker.data.asset.Music music = audio.music();

        trackMap.put(Track.MENU1, new TrackInfo(music.menu1(), false, Track.MENU2, false));
        trackMap.put(Track.MENU2, new TrackInfo(music.menu2(), false, Track.MENU1, false));
        trackMap.put(Track.LOADING, new TrackInfo(music.loading(), false, null, false));
        trackMap.put(Track.GAME1, new TrackInfo(music.game1(), false, Track.GAME2, false));
        trackMap.put(Track.GAME2, new TrackInfo(music.game2(), false, Track.GAME1, false));
        trackMap.put(Track.PAUSE, new TrackInfo(music.pause(), true, Track.PAUSE, true));
        trackMap.put(Track.SHOP, new TrackInfo(music.shop(), true, Track.SHOP, true));
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

        loadTrack(info.isSecondaryPlayer ? MusicPlayerType.S_MUSIC_PLAYER : MusicPlayerType.MUSIC_PLAYER, info.media);
        MediaPlayer player = info.isSecondaryPlayer ? sMusicAudioPlayer : musicAudioPlayer;

        player.setOnEndOfMedia(() -> {
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

        player.play();
    }

    public void resumeMusic(){
        if(getMusicAudioPlayer().getStatus()== MediaPlayer.Status.PAUSED){
            getMusicAudioPlayer().play();
            getMusicAudioPlayer().set
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

    public void loadTrack(MusicPlayerType type, Media track){
        if(type==MusicPlayerType.MUSIC_PLAYER){
            musicAudioPlayer = new MediaPlayer(track);
            refreshVolume();
        }else if(type==MusicPlayerType.S_MUSIC_PLAYER){
            sMusicAudioPlayer = new MediaPlayer(track);
            refreshVolume();
        }
    }

    public void refreshVolume() {
        if (musicAudioPlayer != null && musicAudioPlayer.getStatus() != MediaPlayer.Status.DISPOSED) {
            musicAudioPlayer.setVolume(musicVolume);
        }
        if (sMusicAudioPlayer != null && sMusicAudioPlayer.getStatus() != MediaPlayer.Status.DISPOSED) {
            sMusicAudioPlayer.setVolume(musicVolume);
        }
    }

    public void setMusicVolume(double value) {
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

    public MediaPlayer getSMusicAudioPlayer(){
        return sMusicAudioPlayer;
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
        Media media;
        boolean isSecondaryPlayer;
        Track nextTrack;
        boolean conditionalLoop;

        TrackInfo(Media media, boolean isSecondaryPlayer, Track nextTrack, boolean conditionalLoop) {
            this.media = media;
            this.isSecondaryPlayer = isSecondaryPlayer;
            this.nextTrack = nextTrack;
            this.conditionalLoop = conditionalLoop;
        }
    }
}
