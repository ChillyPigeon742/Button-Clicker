package net.alek.buttonclicker.transfer.event.payload;

import net.alek.buttonclicker.core.log.LogType;

public record LogPayload(LogType logType, String message) {}