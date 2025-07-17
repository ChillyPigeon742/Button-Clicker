package net.alek.buttonclicker.audio;

import javafx.scene.media.MediaPlayer;
import net.alek.buttonclicker.ui.MenuManager;

import java.util.Objects;
import java.util.Random;

public class Music {
    private static boolean finishedPlaying = false;

    public void chooseMusic(){
        int number = new Random().nextInt(1, 3);
        playMusic(number == 1 ? Track.MENU1 : Track.MENU2);
    }

    public static void playMusic(Track track){
        if(Objects.equals(trackName, "menu1")){
            stopMusic();
            finishedPlaying = false;

            SoundManager.loadTrack(2, menu1);
            SoundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                finishedPlaying = true;

                SoundManager.getMusicDelayTimer().setInterval(SoundManager.musicDelay);
                SoundManager.getMusicDelayTimer().setTask(() -> playMusic("menu2"));
                SoundManager.getMusicDelayTimer().start();
            });

            SoundManager.getMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "menu2")){
            stopMusic();
            finishedPlaying = false;

            SoundManager.loadTrack(2, menu2);
            SoundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                finishedPlaying = true;

                SoundManager.getMusicDelayTimer().setInterval(SoundManager.musicDelay);
                SoundManager.getMusicDelayTimer().setTask(() -> playMusic("menu1"));
                SoundManager.getMusicDelayTimer().start();
            });
            SoundManager.getMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "loading")){
            stopMusic();
            finishedPlaying = true;

            SoundManager.loadTrack(3, loading);
            SoundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                SoundManager.getMusicDelayTimer().setInterval(SoundManager.musicDelay);
                SoundManager.getMusicDelayTimer().setTask(() -> {
                    Random random = new Random();
                    int number = random.nextInt(1,3);

                    if(number==1){
                        playMusic("game1");
                    }else if(number==2) {
                        playMusic("game2");
                    }
                });
                SoundManager.getMusicDelayTimer().start();
            });

            SoundManager.getSMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "game1")){
            stopMusic();
            finishedPlaying = false;

            SoundManager.loadTrack(2, game1);
            SoundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                finishedPlaying = true;

                SoundManager.getMusicDelayTimer().setInterval(SoundManager.musicDelay);
                SoundManager.getMusicDelayTimer().setTask(() -> playMusic("game2"));
                SoundManager.getMusicDelayTimer().start();
            });

            SoundManager.getMusicAudioPlayer().play();

        }else if(Objects.equals(trackName, "game2")){
            stopMusic();
            finishedPlaying = false;

            SoundManager.loadTrack(2, game2);
            SoundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                finishedPlaying = true;

                SoundManager.getMusicDelayTimer().setInterval(SoundManager.musicDelay);
                SoundManager.getMusicDelayTimer().setTask(() -> playMusic("game1"));
                SoundManager.getMusicDelayTimer().start();
            });

            SoundManager.getMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "pause")){
            SoundManager.loadTrack(3, pause);
            SoundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                SoundManager.getMusicDelayTimer().setInterval(SoundManager.musicDelay);
                SoundManager.getMusicDelayTimer().setTask(() -> {
                    if(!MenuManager.isMenuOpen("Game")){
                        playMusic("pause");
                    }
                });
                SoundManager.getMusicDelayTimer().start();
            });

            SoundManager.getSMusicAudioPlayer().play();
        }else if(Objects.equals(trackName, "shop")){
            SoundManager.loadTrack(3, shop);
            SoundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                SoundManager.getMusicDelayTimer().setInterval(SoundManager.musicDelay);
                SoundManager.getMusicDelayTimer().setTask(() -> {
                    if(!MenuManager.isMenuOpen("Game")){
                        playMusic("shop");
                    }
                });
                SoundManager.getMusicDelayTimer().start();
            });

            SoundManager.getSMusicAudioPlayer().play();
        }
    }

    public static void resumeMusic(){
        if(SoundManager.getMusicAudioPlayer().getStatus()== MediaPlayer.Status.PAUSED){
            SoundManager.getMusicAudioPlayer().play();
        }
        if(isFinishedPlaying()){
            SoundManager.getMusicDelayTimer().resume();
        }
    }

    public static void pauseMusic(){
        if(SoundManager.getMusicAudioPlayer().getStatus()==MediaPlayer.Status.PLAYING){
            SoundManager.getMusicAudioPlayer().pause();
        }
        if(isFinishedPlaying()){
            SoundManager.getMusicDelayTimer().pause();
        }
    }

    public static void stopMusic(){
        SoundManager.getMusicAudioPlayer().stop();
        SoundManager.getMusicDelayTimer().stop();
    }

    public static boolean isFinishedPlaying(){
        return finishedPlaying;
    }
}
