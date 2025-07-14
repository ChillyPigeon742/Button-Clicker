package net.alek.buttonclicker.event.payload;

import net.alek.buttonclicker.core.log.LogType;

public record LogPayload(LogType logType, String message) {}