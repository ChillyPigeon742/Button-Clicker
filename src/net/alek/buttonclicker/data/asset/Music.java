package net.alek.buttonclicker.data.asset;

import javafx.scene.media.Media;

public record Music(
        Media menu1,
        Media menu2,
        Media loading,
        Media game1,
        Media game2,
        Media pause,
        Media shop
) {}