package net.alek.buttonclicker.data;

import javafx.scene.media.Media;

public record SFX(
        Media select,
        Media click,
        Media purchase,
        Media declined
) {}