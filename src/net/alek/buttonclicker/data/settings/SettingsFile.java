package net.alek.buttonclicker.data.settings;

public class SettingsFile {
    private String version;
    private UserSettingsData userSettings;
    private DebugSettingsData debugSettings;

    public SettingsFile(String version, UserSettingsData userSettings, DebugSettingsData debugSettings) {
        this.version = version;
        this.userSettings = userSettings;
        this.debugSettings = debugSettings;
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

    // ----------------- USER SETTING DATA MUTATION -----------------

    public byte getMasterVolume() {
        return userSettings.masterVolume();
    }

    public void setMasterVolume(byte masterVolume) {
        userSettings = updatedUserSettings(masterVolume, null, null, null);
    }

    public byte getSFXVolume() {
        return userSettings.sfxVolume();
    }

    public void setSFXVolume(byte sfxVolume) {
        userSettings = updatedUserSettings(null, sfxVolume, null, null);
    }

    public byte getMusicVolume() {
        return userSettings.musicVolume();
    }

    public void setMusicVolume(byte musicVolume) {
        userSettings = updatedUserSettings(null, null, musicVolume, null);
    }

    public byte getMusicDelay() {
        return userSettings.musicDelay();
    }

    public void setMusicDelay(byte musicDelay) {
        userSettings = updatedUserSettings(null, null, null, musicDelay);
    }

    private UserSettingsData updatedUserSettings(Byte master, Byte sfx, Byte music, Byte delay) {
        return new UserSettingsData(
                master != null ? master : userSettings.masterVolume(),
                sfx != null ? sfx : userSettings.sfxVolume(),
                music != null ? music : userSettings.musicVolume(),
                delay != null ? delay : userSettings.musicDelay()
        );
    }

    // ----------------- DEBUG SETTING DATA MUTATION -----------------
    // TODO: debug settings
}