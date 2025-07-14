package net.alek.buttonclicker.data.model;

import net.alek.buttonclicker.command.CommandDefinitions;
import java.nio.file.Path;

public record AppData(
        boolean debugMode,
        Path APPDATA_PATH,
        Path DATA_PATH,
        Path LOGS_PATH,
        CommandDefinitions commandDefinitions,
        String version
) {}