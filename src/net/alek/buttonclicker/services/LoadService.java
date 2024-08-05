package net.alek.buttonclicker.services;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.engine.ResourceManager;
import net.alek.buttonclicker.libraries.StorageLibrary;

import net.alek.buttonclicker.utilities.AudioUtility;
import net.alek.buttonclicker.utilities.JSONUtility;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;

public class LoadService {
    public LoadService(){
        StorageLibrary.logger.info("Loading Preferences...");
        StorageLibrary.currentSave = JSONUtility.readAStringFromJsonFile("Data/settings.json", "current_save");
        if(!Objects.equals(StorageLibrary.currentSave, "null")){
            StorageLibrary.startGameButton.setBackground(Color.GREEN);
            StorageLibrary.startGameButton.setFont(new Font("Nunito", Font.BOLD, 27));
            StorageLibrary.quitButton.setText("Save & Quit");

            if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                loadSave1();
                if(StorageLibrary.playedBefore1){
                    StorageLibrary.startGameButton.setText("Resume Game");
                }else if(!StorageLibrary.playedBefore1){
                    StorageLibrary.startGameButton.setText("Start Game");
                }
            }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                loadSave2();
                if(StorageLibrary.playedBefore2){
                    StorageLibrary.startGameButton.setText("Resume Game");
                }else if(!StorageLibrary.playedBefore2){
                    StorageLibrary.startGameButton.setText("Start Game");
                }
            }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                loadSave3();
                if(StorageLibrary.playedBefore3){
                    StorageLibrary.startGameButton.setText("Resume Game");
                }else if(!StorageLibrary.playedBefore3){
                    StorageLibrary.startGameButton.setText("Start Game");
                }
            }
        }else if(StorageLibrary.currentSave.equals("null")){
            StorageLibrary.startGameButton.setText("Select A Save To Continue");
            StorageLibrary.startGameButton.setBackground(Color.GRAY);
            StorageLibrary.startGameButton.setFont(new Font("Nunito", Font.BOLD, 21));
            StorageLibrary.quitButton.setText("Quit");
        }
        StorageLibrary.logger.info("Current Save: {}", StorageLibrary.currentSave);

        if(Objects.equals(StorageLibrary.currentSave, "null")){
            StorageLibrary.currentSaveText.setText("Current Save: none");
        }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
            StorageLibrary.currentSaveText.setText("Current Save: "+StorageLibrary.save1Name);
        }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
            StorageLibrary.currentSaveText.setText("Current Save: "+StorageLibrary.save2Name);
        }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
            StorageLibrary.currentSaveText.setText("Current Save: "+StorageLibrary.save3Name);
        }

        int masterVolume = JSONUtility.readAIntFromJsonFile("Data/settings.json", "master_volume");
        StorageLibrary.masterVolumeSlider.setValue(masterVolume);

        AudioUtility.SoundManager.setMasterVolume(StorageLibrary.masterVolumeSlider.getValue());
        StorageLibrary.logger.info("Master Volume: {}", masterVolume+"%");

        int musicVolume = JSONUtility.readAIntFromJsonFile("Data/settings.json", "music_volume");
        StorageLibrary.musicVolumeSlider.setValue(musicVolume);
        StorageLibrary.logger.info("Music Volume: {}", musicVolume+"%");

        int sfxVolume = JSONUtility.readAIntFromJsonFile("Data/settings.json", "sfx_volume");
        StorageLibrary.sfxVolumeSlider.setValue(sfxVolume);
        StorageLibrary.logger.info("SFX Volume: {}", sfxVolume+"%");

        int musicDelay = JSONUtility.readAIntFromJsonFile("Data/settings.json", "music_delay");
        StorageLibrary.musicDelaySpinner.setValue(musicDelay);
        StorageLibrary.musicDelaySpinnerText.setText(musicDelay+" Secs");
        StorageLibrary.logger.info("Music Delay: {}", musicDelay+" Secs");

        StorageLibrary.logger.info("Finished Loading Preferences!");

        new AudioUtility();
    }

    public static void loadSave1() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Saves/save1.bcs"));
            StorageLibrary.clicks1 = ois.readLong();
            StorageLibrary.clickPower1 = ois.readLong();
            StorageLibrary.side1 = ois.readByte();
            StorageLibrary.save1Name = ois.readUTF();
            StorageLibrary.playedBefore1 = ois.readBoolean();
            ois.close();
        } catch (IOException e) {
        }

        if(!Objects.equals(StorageLibrary.save1Name, "null")){
            if(StorageLibrary.side1==1){
                StorageLibrary.save1Button.setBackground(Color.YELLOW);
                StorageLibrary.save1Button.setText(StorageLibrary.save1Name);
            }else if(StorageLibrary.side1==2){
                StorageLibrary.save1Button.setBackground(Color.BLACK);
                StorageLibrary.save1Button.setText(StorageLibrary.save1Name);
            }else if(StorageLibrary.side1==3){
                StorageLibrary.save1Button.setBackground(Color.MAGENTA);
                StorageLibrary.save1Button.setText(StorageLibrary.save1Name);
            }else if(StorageLibrary.side1==4){
                StorageLibrary.save1Button.setBackground(Color.RED);
                StorageLibrary.save1Button.setText(StorageLibrary.save1Name);
            }
        }else if(Objects.equals(StorageLibrary.save1Name, "null")){
            StorageLibrary.save1Button.setBackground(Color.GRAY);
            StorageLibrary.save1Button.setText("Save 1 Empty");
        }
    }

    public static void loadSave2() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Saves/save2.bcs"));
            StorageLibrary.clicks2 = ois.readLong();
            StorageLibrary.clickPower2 = ois.readLong();
            StorageLibrary.side2 = ois.readByte();
            StorageLibrary.save2Name = ois.readUTF();
            StorageLibrary.playedBefore2 = ois.readBoolean();
            ois.close();
        } catch (IOException e) {
        }

        if(!Objects.equals(StorageLibrary.save2Name, "null")){
            if(StorageLibrary.side2==1){
                StorageLibrary.save2Button.setBackground(Color.YELLOW);
                StorageLibrary.save2Button.setText(StorageLibrary.save2Name);
            }else if(StorageLibrary.side2==2){
                StorageLibrary.save2Button.setBackground(Color.BLACK);
                StorageLibrary.save2Button.setText(StorageLibrary.save2Name);
            }else if(StorageLibrary.side2==3){
                StorageLibrary.save2Button.setBackground(Color.MAGENTA);
                StorageLibrary.save2Button.setText(StorageLibrary.save2Name);
            }else if(StorageLibrary.side2==4){
                StorageLibrary.save2Button.setBackground(Color.RED);
                StorageLibrary.save2Button.setText(StorageLibrary.save2Name);
            }
        }else if(Objects.equals(StorageLibrary.save2Name, "null")){
            StorageLibrary.save2Button.setBackground(Color.GRAY);
            StorageLibrary.save2Button.setText("Save 2 Empty");
        }
    }

    public static void loadSave3() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Saves/save3.bcs"));
            StorageLibrary.clicks3 = ois.readLong();
            StorageLibrary.clickPower3 = ois.readLong();
            StorageLibrary.side3 = ois.readByte();
            StorageLibrary.save3Name = ois.readUTF();
            StorageLibrary.playedBefore3 = ois.readBoolean();
            ois.close();
        } catch (IOException e) {
        }

        if(!Objects.equals(StorageLibrary.save3Name, "null")){
            if(StorageLibrary.side3==1){
                StorageLibrary.save3Button.setBackground(Color.YELLOW);
                StorageLibrary.save3Button.setText(StorageLibrary.save3Name);
            }else if(StorageLibrary.side3==2){
                StorageLibrary.save3Button.setBackground(Color.BLACK);
                StorageLibrary.save3Button.setText(StorageLibrary.save3Name);
            }else if(StorageLibrary.side3==3){
                StorageLibrary.save3Button.setBackground(Color.MAGENTA);
                StorageLibrary.save3Button.setText(StorageLibrary.save3Name);
            }else if(StorageLibrary.side3==4){
                StorageLibrary.save3Button.setBackground(Color.RED);
                StorageLibrary.save3Button.setText(StorageLibrary.save3Name);
            }
        }else if(Objects.equals(StorageLibrary.save3Name, "null")){
            StorageLibrary.save3Button.setBackground(Color.GRAY);
            StorageLibrary.save3Button.setText("Save 3 Empty");
        }
    }
}