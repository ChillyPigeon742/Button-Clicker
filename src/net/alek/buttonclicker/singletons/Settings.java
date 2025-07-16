package net.alek.buttonclicker.singletons;

import net.alek.buttonclicker.data.settings.SettingsFile;
import net.alek.buttonclicker.read.settings.SettingsReader;
import net.alek.buttonclicker.write.settings.SettingsWriter;

public class Settings {
    private static final Settings instance = new Settings();
    private SettingsFile settingsFile;
    private SettingsReader settingsReader;
    private SettingsWriter settingsWriter;

    private Settings() {}

    public static Settings getInstance() {
        return instance;
    }

    public Settings init(SettingsFile file) {
        if (this.settingsFile != null)
            throw new IllegalStateException("Already initialized!");
        this.settingsFile = file;
        this.settingsReader = new SettingsReader(file);
        this.settingsWriter = new SettingsWriter(file);
        return this;
    }

    public SettingsFile getSettingsFile() {
        return settingsFile;
    }

    public SettingsReader getSettingsReader() {
        return settingsReader;
    }

    public SettingsWriter getSettingsWriter() {
        return settingsWriter;
    }
}