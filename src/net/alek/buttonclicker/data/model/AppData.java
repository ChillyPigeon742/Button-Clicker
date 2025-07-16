package net.alek.buttonclicker.data.model;

import java.nio.file.Path;

public record AppData(
        boolean debugMode,
        Path APPDATA_PATH,
        Path DATA_PATH,
        Path LOGS_PATH,
        String version
) {}