package net.alek.buttonclicker.read.settings;

import net.alek.buttonclicker.data.settings.SettingsFile;
import net.alek.buttonclicker.read.json.JSONReader;
import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.ui.RenderService;

import java.awt.*;
import java.util.Map;

public class SettingsReader {
    private final JSONReader jsonReader;
    private final SettingsFile settingsFile;

    public SettingsReader(SettingsFile settingsFile) {
        this.settingsFile = settingsFile;
        this.jsonReader = JSONReader.fromFile(String.valueOf(getSettingsFile().getFilePath()));
    }

    public SettingsFile getSettingsFile() {
        return this.settingsFile;
    }

    public JSONReader getJsonReader() {
        return this.jsonReader;
    }

    public SettingsFile reload(){
        readMasterVolume();
        readSFXVolume();
        readMusicVolume();
        readMusicDelay();
        readAutoSaveEnabled();
        readAutoSaveInterval();
        return getSettingsFile();
    }

    private JSONReader userSettingsReader(){
        return getJsonReader().getObject("userSettings");
    }

    private JSONReader debugSettingsReader(){
        return getJsonReader().getObject("debugSettings");
    }

    public byte readMasterVolume() {
        var masterVolume = userSettingsReader().readByte("masterVolume");
        getSettingsFile().setMasterVolume(masterVolume);
        return masterVolume;
    }

    public byte readSFXVolume() {
        var sfxVolume = userSettingsReader().readByte("sfxVolume");
        getSettingsFile().setSFXVolume(sfxVolume);
        return sfxVolume;
    }

    public byte readMusicVolume() {
        var musicVolume = userSettingsReader().readByte("musicVolume");
        getSettingsFile().setMusicVolume(musicVolume);
        return musicVolume;
    }

    public byte readMusicDelay() {
        var musicDelay = userSettingsReader().readByte("musicDelay");
        getSettingsFile().setMusicDelay(musicDelay);
        return musicDelay;
    }

    public boolean readAutoSaveEnabled() {
        var autoSaveEnabled = userSettingsReader().readBoolean("autoSaveEnabled");
        getSettingsFile().setAutoSaveEnabled(autoSaveEnabled);
        return autoSaveEnabled;
    }

    public byte readAutoSaveInterval() {
        var autoSaveInterval = userSettingsReader().readByte("autoSaveInterval");
        getSettingsFile().setAutoSaveInterval(autoSaveInterval);
        return autoSaveInterval;
    }

    public SettingsReader loadSettings(){
        Object currentSaveKey = settings.get("currentSave");
        Map<String, Object> currentSave = getCurrentSave();

        if (currentSaveKey == null) {
            RenderService.startGameButton.setText("Select A Save To Continue");
            RenderService.startGameButton.setBackground(Color.GRAY);
            RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 21));
            RenderService.quitButton.setText("Quit");
            RenderService.currentSaveText.setText("Current Save: none");
        } else {
            RenderService.startGameButton.setBackground(Color.GREEN);
            RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 27));
            RenderService.quitButton.setText("Save & Quit");

            boolean playedBefore = currentSave != null && Boolean.TRUE.equals(currentSave.get("playedBefore"));
            RenderService.startGameButton.setText(playedBefore ? "Resume Game" : "Start Game");

            String saveName = currentSave != null ? currentSave.getOrDefault("saveName", "unknown").toString() : "unknown";
            RenderService.currentSaveText.setText("Current Save: " + saveName);
        }

        AudioService.SoundManager.setMasterVolume(JSONReader.fromFile("Data/settings.json").getObject("userSettings").readByte("masterVolume"));
        RenderService.masterVolumeSlider.setValue(AudioService.SoundManager.masterVolumeUSER);

        AudioService.SoundManager.setMusicVolume(JSONReader.fromFile("Data/settings.json").getObject("userSettings").readByte("musicVolume"));
        RenderService.musicVolumeSlider.setValue(AudioService.SoundManager.musicVolumeUSER);

        AudioService.SoundManager.setSFXVolume(JSONReader.fromFile("Data/settings.json").getObject("userSettings").readByte("sfxVolume"));
        RenderService.sfxVolumeSlider.setValue(AudioService.SoundManager.sfxVolumeUSER);

        AudioService.SoundManager.setMusicDelay(JSONReader.fromFile("Data/settings.json").getObject("userSettings").readByte("musicDelay"));
        RenderService.musicDelaySpinner.setValue(AudioService.SoundManager.getMusicDelay());
        RenderService.musicDelaySpinnerText.setText(AudioService.SoundManager.getMusicDelay()+" Secs");
    }
}
