package net.alek.buttonclicker.data.settings;

import net.alek.buttonclicker.transfer.request.Request;
import net.alek.buttonclicker.transfer.request.payload.SettingsFilePayload;

import java.nio.file.Path;

public class SettingsFile {
    private static final SettingsFile instance = new SettingsFile();

    private Path filePath;
    private String version;
    private UserSettingsData userSettings;
    private DebugSettingsData debugSettings;

    static {
        Request.GET_SETTINGS_FILE.handle(SettingsFile::getInstance);
    }

    private SettingsFile(){}

    public void resetFile(Path filePath, String version, UserSettingsData userSettings, DebugSettingsData debugSettings) {
        this.filePath = filePath;
        this.version = version;
        this.userSettings = userSettings;
        this.debugSettings = debugSettings;
    }

    private static SettingsFilePayload getInstance(){
        return new SettingsFilePayload(instance);
    }

    public Path getFilePath(){
        return filePath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public UserSettingsData getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettingsData userSettings) {
        this.userSettings = userSettings;
    }

    public DebugSettingsData getDebugSettings() {
        return debugSettings;
    }

    public void setDebugSettings(DebugSettingsData debugSettings) {
        this.debugSettings = debugSettings;
    }

    // ----------------- USER SETTING DATA GETTERS -----------------

    public byte getMasterVolume() {
        return userSettings.masterVolume();
    }

    public byte getSFXVolume() {
        return userSettings.sfxVolume();
    }

    public byte getMusicVolume() {
        return userSettings.musicVolume();
    }

    public byte getMusicDelay() {
        return userSettings.musicDelay();
    }

    public boolean isAutoSaveEnabled() {
        return userSettings.autoSaveEnabled();
    }

    public byte getAutoSaveInterval() {
        return userSettings.autoSaveInterval();
    }

    // ----------------- USER SETTING DATA SETTERS -----------------

    public void setMasterVolume(byte masterVolume) {
        userSettings = updatedUserSettings(masterVolume, null, null, null, null, null);
    }

    public void setSFXVolume(byte sfxVolume) {
        userSettings = updatedUserSettings(null, sfxVolume, null, null, null, null);
    }

    public void setMusicVolume(byte musicVolume) {
        userSettings = updatedUserSettings(null, null, musicVolume, null, null, null);
    }

    public void setMusicDelay(byte musicDelay) {
        userSettings = updatedUserSettings(null, null, null, musicDelay, null, null);
    }

    public void setAutoSaveEnabled(boolean autoSaveEnabled) {
        userSettings = updatedUserSettings(null, null, null, null, autoSaveEnabled, null);
    }

    public void setAutoSaveInterval(byte autoSaveInterval) {
        userSettings = updatedUserSettings(null, null, null, null, null, autoSaveInterval);
    }

    private UserSettingsData updatedUserSettings(
            Byte master, Byte sfx, Byte music, Byte delay, Boolean autoSave, Byte autoInterval
    ) {
        return new UserSettingsData(
                master != null ? master : userSettings.masterVolume(),
                sfx != null ? sfx : userSettings.sfxVolume(),
                music != null ? music : userSettings.musicVolume(),
                delay != null ? delay : userSettings.musicDelay(),
                autoSave != null ? autoSave : userSettings.autoSaveEnabled(),
                autoInterval != null ? autoInterval : userSettings.autoSaveInterval()
        );
    }

    // ----------------- DEBUG SETTING DATA MUTATION -----------------
    // TODO: debug settings
}