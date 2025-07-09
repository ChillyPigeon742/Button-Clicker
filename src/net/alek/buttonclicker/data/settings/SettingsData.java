package net.alek.buttonclicker.data.settings;

public record SettingsData(
        String currentSave,
        byte masterVolume,
        byte sfxVolume,
        byte musicVolume,
        byte musicDelay
) {}