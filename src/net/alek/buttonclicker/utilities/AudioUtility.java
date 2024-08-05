package net.alek.buttonclicker.utilities;

import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.engine.MenuManager;
import net.alek.buttonclicker.engine.ResourceManager;

import net.alek.buttonclicker.libraries.ATimer;
import net.alek.buttonclicker.libraries.StorageLibrary;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;

public class AudioUtility {
    public AudioUtility(){
        Random random = new Random();

        int number = random.nextInt(1,3);

        if(number==1){
            AudioUtility.Music.playMusic("menu1");
        }else if(number==2){
            AudioUtility.Music.playMusic("menu2");
        }

        StorageLibrary.logger.info("Audio Pipeline Initialized!");
    }

    public static class SFX{
        public static void playSFX(String sfxName) {
            if(Objects.equals(sfxName, "select")){
                SoundManager.loadTrack(1, "/assets/buttonclicker/audio/sfx/select.mp3");
                SoundManager.refreshSFXVolume();

                SoundManager.getSFXAudioPlayer().play();
            }else if(Objects.equals(sfxName, "click")){
                SoundManager.loadTrack(1, "/assets/buttonclicker/audio/sfx/click.mp3");
                SoundManager.refreshSFXVolume();

                SoundManager.getSFXAudioPlayer().play();
            }else if(Objects.equals(sfxName, "purchase")){
                SoundManager.loadTrack(1, "/assets/buttonclicker/audio/sfx/purchase.mp3");
                SoundManager.refreshSFXVolume();

                SoundManager.getSFXAudioPlayer().play();
            }else if(Objects.equals(sfxName, "declined")){
                SoundManager.loadTrack(1, "/assets/buttonclicker/audio/sfx/declined.mp3");
                SoundManager.refreshSFXVolume();

                SoundManager.getSFXAudioPlayer().play();
            }
        }
    }

    public static class Music{
        private static boolean finishedPlaying = false;
        public static boolean playNextSong = false;
        public static String nextSong = "brothisshitissobadillfixitinthenextupdate";

        public static void playMusic(String trackName){
            if(Objects.equals(trackName, "menu1")){
                stopMusic();
                finishedPlaying = false;

                SoundManager.loadTrack(2, "/assets/buttonclicker/audio/music/menu/menu1.mp3");
                SoundManager.refreshMusicVolume();
                
                SoundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                    finishedPlaying = true;

                    SoundManager.getMusicDelay().setDelay(JSONUtility.readAIntFromJsonFile("Data/settings.json", "music_delay"));
                    SoundManager.getMusicDelay().setTask(() -> {
                        playMusic("menu2");
                    });
                    SoundManager.getMusicDelay().start();
                });

                SoundManager.getMusicAudioPlayer().play();
            }else if(Objects.equals(trackName, "menu2")){
                stopMusic();
                finishedPlaying = false;

                SoundManager.loadTrack(2, "/assets/buttonclicker/audio/music/menu/menu2.mp3");
                SoundManager.refreshMusicVolume();

                SoundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                    finishedPlaying = true;

                    SoundManager.getMusicDelay().setDelay(JSONUtility.readAIntFromJsonFile("Data/settings.json", "music_delay"));
                    SoundManager.getMusicDelay().setTask(() -> {
                        playMusic("menu1");
                    });
                    SoundManager.getMusicDelay().start();
                });

                SoundManager.getMusicAudioPlayer().play();
            }else if(Objects.equals(trackName, "loading")){
                SoundManager.loadTrack(3, "/assets/buttonclicker/audio/music/menu/loading.mp3");
                SoundManager.refreshMusicVolume();

                SoundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                    SoundManager.getMusicDelay().setDelay(JSONUtility.readAIntFromJsonFile("Data/settings.json", "music_delay"));
                    SoundManager.getMusicDelay().setTask(() -> {
                        Random random = new Random();

                        int number = random.nextInt(1,3);

                        if(number==1){
                            if(MenuManager.isMenuOpen("Game")){
                                playMusic("game1");
                            }else{
                                playNextSong = true;
                                nextSong = "game1";
                            }
                        }else if(number==2) {
                            if(MenuManager.isMenuOpen("Game")){
                                playMusic("game2");
                            }else{
                                playNextSong = true;
                                nextSong = "game2";
                            }
                        }
                    });
                    SoundManager.getMusicDelay().start();
                });

                SoundManager.getSMusicAudioPlayer().play();
            }else if(Objects.equals(trackName, "game1")){
                stopMusic();
                finishedPlaying = false;

                SoundManager.loadTrack(2, "/assets/buttonclicker/audio/music/game/game1.mp3");
                SoundManager.refreshMusicVolume();

                SoundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                    finishedPlaying = true;

                    SoundManager.getMusicDelay().setDelay(JSONUtility.readAIntFromJsonFile("Data/settings.json", "music_delay"));
                    SoundManager.getMusicDelay().setTask(() -> {
                        if(MenuManager.isMenuOpen("Game")){
                            playMusic("game2");
                        }else{
                            playNextSong = true;
                            nextSong = "game2";
                        }
                    });
                    SoundManager.getMusicDelay().start();
                });

                SoundManager.getMusicAudioPlayer().play();

            }else if(Objects.equals(trackName, "game2")){
                stopMusic();
                finishedPlaying = false;

                SoundManager.loadTrack(2, "/assets/buttonclicker/audio/music/game/game2.mp3");
                SoundManager.refreshMusicVolume();

                SoundManager.getMusicAudioPlayer().setOnEndOfMedia(() -> {
                    finishedPlaying = true;

                    SoundManager.getMusicDelay().setDelay(JSONUtility.readAIntFromJsonFile("Data/settings.json", "music_delay"));
                    SoundManager.getMusicDelay().setTask(() -> {
                        if(MenuManager.isMenuOpen("Game")){
                            playMusic("game1");
                        }else{
                            playNextSong = true;
                            nextSong = "game1";
                        }
                    });
                    SoundManager.getMusicDelay().start();
                });

                SoundManager.getMusicAudioPlayer().play();
            }else if(Objects.equals(trackName, "pause")){
                SoundManager.loadTrack(3, "/assets/buttonclicker/audio/music/game/pause.mp3");
                SoundManager.refreshMusicVolume();

                SoundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                    SoundManager.getMusicDelay().setDelay(JSONUtility.readAIntFromJsonFile("Data/settings.json", "music_delay"));
                    SoundManager.getMusicDelay().setTask(() -> {
                        if(!MenuManager.isMenuOpen("Game")){
                            playMusic("pause");
                        }
                    });
                    SoundManager.getMusicDelay().start();
                });

                SoundManager.getSMusicAudioPlayer().play();
            }else if(Objects.equals(trackName, "shop")){
                SoundManager.loadTrack(3, "/assets/buttonclicker/audio/music/game/shop.mp3");
                SoundManager.refreshMusicVolume();

                SoundManager.getSMusicAudioPlayer().setOnEndOfMedia(() -> {
                    SoundManager.getMusicDelay().setDelay(JSONUtility.readAIntFromJsonFile("Data/settings.json", "music_delay"));
                    SoundManager.getMusicDelay().setTask(() -> {
                        if(!MenuManager.isMenuOpen("Game")){
                            playMusic("shop");
                        }
                    });
                    SoundManager.getMusicDelay().start();
                });

                SoundManager.getSMusicAudioPlayer().play();
            }
        }

        public static void resumeMusic(){
            if(SoundManager.getMusicAudioPlayerStatus()==MediaPlayer.Status.PAUSED){
                SoundManager.getMusicAudioPlayer().play();
            }
            if(finishedPlaying){
                SoundManager.getMusicDelay().resume();
            }
        }

        public static void pauseMusic(){
            if(SoundManager.getMusicAudioPlayerStatus()==MediaPlayer.Status.PLAYING){
                SoundManager.getMusicAudioPlayer().pause();
                SoundManager.getMusicDelay().pause();
            }
        }

        public static void stopMusic(){
            SoundManager.getMusicAudioPlayer().stop();
            SoundManager.getMusicDelay().stop();
        }

        public static boolean isFinishedPlaying(){
            return finishedPlaying;
        }
    }

    public static class SoundManager{
        private static MediaPlayer musicAudioPlayer;
        private static MediaPlayer sMusicAudioPlayer;
        private static MediaPlayer sfxAudioPlayer;
        private static ATimer musicDelay = new ATimer();

        public static void loadTrack(int type, String filePath){
            if(type==1){
                try {
                    Media audioTrack = new Media(Objects.requireNonNull(ResourceManager.class.getResource(filePath)).toURI().toString());
                    sfxAudioPlayer = new MediaPlayer(audioTrack);
                } catch (URISyntaxException e) {
                    ErrorHandler.URISyntaxException("AUDIO", 236);
                }
            }else if(type==2){
                try {
                    Media audioTrack = new Media(Objects.requireNonNull(ResourceManager.class.getResource(filePath)).toURI().toString());
                    musicAudioPlayer = new MediaPlayer(audioTrack);
                } catch (URISyntaxException e) {
                    ErrorHandler.URISyntaxException("AUDIO", 243);
                }
            }else if(type==3){
                try {
                    Media audioTrack = new Media(Objects.requireNonNull(ResourceManager.class.getResource(filePath)).toURI().toString());
                    sMusicAudioPlayer = new MediaPlayer(audioTrack);
                } catch (URISyntaxException e) {
                    ErrorHandler.URISyntaxException("AUDIO", 250);
                }
            }
        }

        public static void refreshSFXVolume() {
            double masterVolume = StorageLibrary.masterVolumeSlider.getValue() / 100.0;
            double sfxVolume = StorageLibrary.sfxVolumeSlider.getValue() / 100.0;
            sfxAudioPlayer.setVolume(masterVolume * sfxVolume);
        }

        public static void refreshMusicVolume() {
            double masterVolume = StorageLibrary.masterVolumeSlider.getValue() / 100.0;
            double musicVolume = StorageLibrary.musicVolumeSlider.getValue() / 100.0;
            musicAudioPlayer.setVolume(masterVolume * musicVolume);
            sMusicAudioPlayer.setVolume(masterVolume * musicVolume);
        }

        public static void setMasterVolume(int value) {
            double masterVolume = value / 100.0;
            double musicVolume = masterVolume * (StorageLibrary.musicVolumeSlider.getValue() / 100.0);
            double sfxVolume = masterVolume * (StorageLibrary.sfxVolumeSlider.getValue() / 100.0);

            musicAudioPlayer.setVolume(musicVolume);
            sMusicAudioPlayer.setVolume(musicVolume);
            sfxAudioPlayer.setVolume(sfxVolume);
        }

        public static int getSFXVolume(int type) {
            if(type==1){
                return StorageLibrary.sfxVolumeSlider.getValue()/100;
            }else if(type==2){
                return StorageLibrary.sfxVolumeSlider.getValue();
            }
            return -213;
        }

        public static int getMusicVolume(int type) {
            if(type==1){
                return StorageLibrary.musicVolumeSlider.getValue()/100;
            }else if(type==2){
                return StorageLibrary.musicVolumeSlider.getValue();
            }
            return -213;
        }

        public static int getMasterVolume(int type) {
            if(type==1){
                return StorageLibrary.masterVolumeSlider.getValue()/100;
            }else if(type==2){
                return StorageLibrary.masterVolumeSlider.getValue();
            }
            return -213;
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

        public static MediaPlayer.Status getMusicAudioPlayerStatus(){
            return getMusicAudioPlayer().getStatus();
        }

        public static ATimer getMusicDelay(){
            return musicDelay;
        }
    }
}