package net.alek.buttonclicker.event.payload;

public record CloseAppPayload(int exitCode, boolean exitJVM) {}