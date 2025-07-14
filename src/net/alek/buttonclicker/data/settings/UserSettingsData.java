package net.alek.buttonclicker.data.settings;

public record UserSettingsData(
        byte masterVolume,
        byte sfxVolume,
        byte musicVolume,
        byte musicDelay,
        boolean autoSaveEnabled,
        byte autoSaveInterval
) {}