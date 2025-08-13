package net.alek.buttonclicker.read.settings;

import net.alek.buttonclicker.data.settings.SettingsFile;
import net.alek.buttonclicker.read.json.JSONReader;
import net.alek.buttonclicker.transfer.request.type.Request;
import net.alek.buttonclicker.transfer.request.payload.SettingsFilePayload;
import net.alek.buttonclicker.ui.RenderService;

import java.awt.*;

public class SettingsReader {
    private static final JSONReader jsonReader;
    private static final SettingsFile settingsFile;

    static {
        SettingsFilePayload payload = (SettingsFilePayload) Request.GET_SETTINGS_FILE.request().await().get();
        settingsFile = payload.settingsFile();
        jsonReader = JSONReader.fromFile(getSettingsFile().getFilePath());
    }

    public static SettingsFile getSettingsFile() {
        return settingsFile;
    }

    public JSONReader getJsonReader() {
        return jsonReader;
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
