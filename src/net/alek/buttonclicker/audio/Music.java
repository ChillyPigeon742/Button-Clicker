package net.alek.buttonclicker.audio;

import javafx.scene.media.MediaPlayer;
import net.alek.buttonclicker.ui.MenuManager;

import java.util.Objects;
import java.util.Random;

public class Music {
    private final SoundManager soundManager;
    private boolean finishedPlaying = false;

    public Music(SoundManager soundManager){
        this.soundManager = soundManager;
    }

    public void chooseMusic(){
        int number = new Random().nextInt(1, 3);
        playMusic(number == 1 ? Track.MENU1 : Track.MENU2);
    }

    public void playMusic(Track track){
        if(Objects.equals(trackName, "menu1")){
            stopMusic();
            finishedPlaying = false;

            this.soundManager.loadTrack(2, menu1);
            this.soundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                finishedPlaying = true;

                this.soundManager.getMusicDelayTimer().setInterval(this.soundManager.musicDelay);
                this.soundManager.getMusicDelayTimer().setTask(() -> playMusic("menu2"));
                this.soundManager.getMusicDelayTimer().start();
            });

            this.soundManager.getMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "menu2")){
            stopMusic();
            finishedPlaying = false;

            this.soundManager.loadTrack(2, menu2);
            this.soundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                finishedPlaying = true;

                this.soundManager.getMusicDelayTimer().setInterval(this.soundManager.musicDelay);
                this.soundManager.getMusicDelayTimer().setTask(() -> playMusic("menu1"));
                this.soundManager.getMusicDelayTimer().start();
            });
            this.soundManager.getMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "loading")){
            stopMusic();
            finishedPlaying = true;

            this.soundManager.loadTrack(3, loading);
            this.soundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                this.soundManager.getMusicDelayTimer().setInterval(this.soundManager.musicDelay);
                this.soundManager.getMusicDelayTimer().setTask(() -> {
                    Random random = new Random();
                    int number = random.nextInt(1,3);

                    if(number==1){
                        playMusic("game1");
                    }else if(number==2) {
                        playMusic("game2");
                    }
                });
                this.soundManager.getMusicDelayTimer().start();
            });

            this.soundManager.getSMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "game1")){
            stopMusic();
            finishedPlaying = false;

            this.soundManager.loadTrack(2, game1);
            this.soundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                finishedPlaying = true;

                this.soundManager.getMusicDelayTimer().setInterval(this.soundManager.musicDelay);
                this.soundManager.getMusicDelayTimer().setTask(() -> playMusic("game2"));
                this.soundManager.getMusicDelayTimer().start();
            });

            this.soundManager.getMusicAudioPlayer().play();

        }else if(Objects.equals(trackName, "game2")){
            stopMusic();
            finishedPlaying = false;

            this.soundManager.loadTrack(2, game2);
            this.soundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                finishedPlaying = true;

                this.soundManager.getMusicDelayTimer().setInterval(this.soundManager.musicDelay);
                this.soundManager.getMusicDelayTimer().setTask(() -> playMusic("game1"));
                this.soundManager.getMusicDelayTimer().start();
            });

            this.soundManager.getMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "pause")){
            this.soundManager.loadTrack(3, pause);
            this.soundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                this.soundManager.getMusicDelayTimer().setInterval(this.soundManager.musicDelay);
                this.soundManager.getMusicDelayTimer().setTask(() -> {
                    if(!MenuManager.isMenuOpen("Game")){
                        playMusic("pause");
                    }
                });
                this.soundManager.getMusicDelayTimer().start();
            });

            this.soundManager.getSMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "shop")){
            this.soundManager.loadTrack(3, shop);
            this.soundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                this.soundManager.getMusicDelayTimer().setInterval(this.soundManager.musicDelay);
                this.soundManager.getMusicDelayTimer().setTask(() -> {
                    if(!MenuManager.isMenuOpen("Game")){
                        playMusic("shop");
                    }
                });
                this.soundManager.getMusicDelayTimer().start();
            });

            this.soundManager.getSMusicAudioPlayer().play();
        }
    }

    public void resumeMusic(){
        if(this.soundManager.getMusicAudioPlayer().getStatus()== MediaPlayer.Status.PAUSED){
            this.soundManager.getMusicAudioPlayer().play();
        }
        if(isFinishedPlaying()){
            this.soundManager.getMusicDelayTimer().resume();
        }
    }

    public void pauseMusic(){
        if(this.soundManager.getMusicAudioPlayer().getStatus()==MediaPlayer.Status.PLAYING){
            this.soundManager.getMusicAudioPlayer().pause();
        }
        if(isFinishedPlaying()){
            this.soundManager.getMusicDelayTimer().pause();
        }
    }

    public void stopMusic(){
        this.soundManager.getMusicAudioPlayer().stop();
        this.soundManager.getMusicDelayTimer().stop();
    }

    public boolean isFinishedPlaying(){
        return finishedPlaying;
    }
}
