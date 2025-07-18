package net.alek.buttonclicker.read.assets;

import net.alek.buttonclicker.data.asset.Fonts;
import net.alek.buttonclicker.transfer.event.type.SubscribeMethod;
import net.alek.buttonclicker.transfer.event.type.Event;
import net.alek.buttonclicker.transfer.request.Request;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontLoader {
    private static Fonts fonts;

    static {
        Request.GET_FONTS.handle(FontLoader::getFonts);
        Event.LOAD_GAME.subscribe(SubscribeMethod.SYNC, ignored -> loadFonts());
        Event.UNLOAD_GAME.subscribe(SubscribeMethod.SYNC, ignored -> unloadFonts());
    }

    private static void loadFonts() {
        Font nunito = loadFont("Nunito.ttf");
        Font indieFlower = loadFont("IndieFlower.ttf");
        Font consolas = loadFont("Consolas.ttf");

        fonts = new Fonts(nunito, indieFlower, consolas);
    }

    private static void unloadFonts() {
        fonts = null;
    }

    private static Font loadFont(String fontFileName) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(
                            FontLoader.class.getResourceAsStream("/assets/buttonclicker/fonts/" + fontFileName)))
                    .deriveFont(12f);
        } catch (FontFormatException | IOException | NullPointerException e) {
            return new Font("SansSerif", Font.PLAIN, 12);
        }
    }

    public static Fonts getFonts(){
        return fonts;
    }
}