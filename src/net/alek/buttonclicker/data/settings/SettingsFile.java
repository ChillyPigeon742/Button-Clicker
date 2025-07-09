package net.alek.buttonclicker.data.settings;

public class SettingsFile {
    private int version;
    private SettingsData settings;

    public SettingsFile(int version, SettingsData settings) {
        this.version = version;
        this.settings = settings;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public SettingsData getSettings() {
        return settings;
    }

    public void setSettings(SettingsData settings) {
        this.settings = settings;
    }

    //-----------------SAVE DATA MUTATION BEGINS HERE

    public String getCurrentSave() {
        return settings.currentSave();
    }

    public void setCurrentSave(String filePath) {
        settings = new SettingsData(
                filePath,
                settings.masterVolume(),
                settings.sfxVolume(),
                settings.musicVolume(),
                settings.musicDelay()
        );
    }

    public byte getMasterVolume() {
        return settings.masterVolume();
    }

    public void setMasterVolume(byte masterVolume) {
        settings = new SettingsData(
                settings.currentSave(),
                masterVolume,
                settings.sfxVolume(),
                settings.musicVolume(),
                settings.musicDelay()
        );
    }

    public byte getSFXVolume() {
        return settings.sfxVolume();
    }

    public void setSFXVolume(byte sfxVolume) {
        settings = new SettingsData(
                settings.currentSave(),
                settings.masterVolume(),
                sfxVolume,
                settings.musicVolume(),
                settings.musicDelay()
        );
    }

    public byte getMusicVolume() {
        return settings.musicVolume();
    }

    public void setMusicVolume(byte musicVolume) {
        settings = new SettingsData(
                settings.currentSave(),
                settings.masterVolume(),
                settings.sfxVolume(),
                musicVolume,
                settings.musicDelay()
        );
    }

    public byte getMusicDelay() {
        return settings.musicDelay();
    }

    public void setMusicDelay(byte musicDelay) {
        settings = new SettingsData(
                settings.currentSave(),
                settings.masterVolume(),
                settings.sfxVolume(),
                settings.musicVolume(),
                musicDelay
        );
    }
}