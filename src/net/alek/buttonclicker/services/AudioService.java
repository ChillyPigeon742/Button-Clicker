package net.alek.buttonclicker.services;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import net.alek.buttonclicker.ui.MenuManager;
import net.alek.buttonclicker.ui.components.ATimer;

import java.util.Objects;
import java.util.Random;

public class AudioService {
    public static void chooseMusic(){
        Random random = new Random();
        int number = random.nextInt(1,3);

        if(number==1){
            AudioService.Music.playMusic("menu1");
        }else if(number==2){
            AudioService.Music.playMusic("menu2");
        }

        LoggingService.Logger.info("Audio Pipeline Initialized!");
    }

    public static class SFX{
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

    public static class Music{
        public static Media menu1;
        public static Media menu2;
        public static Media loading;
        public static Media game1;
        public static Media game2;
        public static Media pause;
        public static Media shop;
        private static boolean finishedPlaying = false;

        public static void playMusic(String trackName){
            if(Objects.equals(trackName, "menu1")){
                stopMusic();
                finishedPlaying = false;

                SoundManager.loadTrack(2, menu1);
                SoundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                    finishedPlaying = true;

                    SoundManager.getMusicDelayTimer().setDelay(SoundManager.musicDelay);
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

                    SoundManager.getMusicDelayTimer().setDelay(SoundManager.musicDelay);
                    SoundManager.getMusicDelayTimer().setTask(() -> playMusic("menu1"));
                    SoundManager.getMusicDelayTimer().start();
                });
                SoundManager.getMusicAudioPlayer().play();
            }else if(Objects.equals(trackName, "loading")){
                stopMusic();
                finishedPlaying = true;

                SoundManager.loadTrack(3, loading);
                SoundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                    SoundManager.getMusicDelayTimer().setDelay(SoundManager.musicDelay);
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

                    SoundManager.getMusicDelayTimer().setDelay(SoundManager.musicDelay);
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

                    SoundManager.getMusicDelayTimer().setDelay(SoundManager.musicDelay);
                    SoundManager.getMusicDelayTimer().setTask(() -> playMusic("game1"));
                    SoundManager.getMusicDelayTimer().start();
                });

                SoundManager.getMusicAudioPlayer().play();
            }else if(Objects.equals(trackName, "pause")){
                SoundManager.loadTrack(3, pause);
                SoundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                    SoundManager.getMusicDelayTimer().setDelay(SoundManager.musicDelay);
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
                    SoundManager.getMusicDelayTimer().setDelay(SoundManager.musicDelay);
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
            if(SoundManager.getMusicAudioPlayer().getStatus()==MediaPlayer.Status.PAUSED){
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

    public static class SoundManager{
        private static MediaPlayer musicAudioPlayer;
        private static MediaPlayer sMusicAudioPlayer;
        private static MediaPlayer sfxAudioPlayer;
        public static Double masterVolume;
        public static Double musicVolume;
        public static Double sfxVolume;
        public static Integer masterVolumeUSER;
        public static Integer musicVolumeUSER;
        public static Integer sfxVolumeUSER;
        public static Byte musicDelay;
        private static final ATimer musicDelayTimer = new ATimer();

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

        public static void refreshVolume() {
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

        public static void setMasterVolume(int value) {
            masterVolume = value / 100.0;
            musicVolume = masterVolume * musicVolume;
            sfxVolume = masterVolume * sfxVolume;

            masterVolumeUSER = value;

            refreshVolume();
        }

        public static void setMusicVolume(int value) {
            musicVolume = masterVolume * (value / 100.0);
            musicVolumeUSER = value;

            refreshVolume();
        }

        public static void setSFXVolume(int value) {
            sfxVolume = masterVolume * (value / 100.0);
            sfxVolumeUSER = value;

            refreshVolume();
        }

        public static void setMusicDelay(Byte value) {
            musicDelay = value;
            musicDelayTimer.setDelay(musicDelay);
        }

        public static Double getSFXVolume() {
            return sfxVolume;
        }

        public static Double getMusicVolume() {
            return musicVolume;
        }

        public static Double getMasterVolume() {
            return masterVolume;
        }

        public static MediaPlayer getMusicAudioPlayer(){
            return musicAudioPlayer;
        }

        public static MediaPlayer getSMusicAudioPlayer(){
            return sMusicAudioPlayer;
        }

        public static MediaPlayer getSFXAudioPlayer(){
            return sfxAudioPlayer;
        }

        public static ATimer getMusicDelayTimer(){
            return musicDelayTimer;
        }

        public static Byte getMusicDelay(){
            return musicDelay;
        }
    }
}