package net.alek.buttonclicker.loaders;

import net.alek.buttonclicker.data.Images;
import net.alek.buttonclicker.data.event.type.DeliveryMode;
import net.alek.buttonclicker.data.event.type.Event;
import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.services.RenderService;
import net.alek.buttonclicker.utilities.read.ReadUtility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    private static final String IMAGE_PATH = "/assets/buttonclicker/images/";
    private static Images images;

    static {
        Event.LOAD_GAME.subscribe(DeliveryMode.SYNC, ignored -> loadAll());
        Event.LOAD_GAME.subscribe(DeliveryMode.SYNC, ignored -> unloadAll());
    }

    public static Images getImages() {
        return images;
    }

    public static void loadAll() {
        ImageIcon missingIcon = loadIcon("missing.png", null);
        BufferedImage missingImage = loadImage("missing.png", null);

        images = new Images(
                missingIcon,
                loadIcon("button_clicker_icon1.png", missingIcon),
                loadIcon("button_clicker_icon2.png", missingIcon),
                loadIcon("button_clicker_icon3.png", missingIcon),
                loadIcon("light_side.png", missingIcon),
                loadIcon("dark_side.png", missingIcon),
                loadIcon("magic_side.png", missingIcon),
                loadIcon("neutral_side.png", missingIcon),
                loadIcon("error_icon.png", missingIcon),
                loadIcon("warning_icon.png", missingIcon),
                loadIcon("information_icon.png", missingIcon),
                loadIcon("question_icon.png", missingIcon),
                loadIcon("arrow_left.png", missingIcon),
                loadIcon("arrow_right.png", missingIcon),
                loadIcon("base.png", missingIcon),
                loadIcon("button.png", missingIcon),
                loadIcon("click_power_icon.png", missingIcon),
                loadIcon("cog.png", missingIcon),
                missingImage,
                loadImage("portal_whitewall.png", missingImage)
        );
    }

    public static void unloadAll() {
        images = null;
    }

    private static ImageIcon loadIcon(String fileName, ImageIcon fallback) {
        try {
            URL url = ReadUtility.class.getResource(IMAGE_PATH + fileName);
            if (url != null) return new ImageIcon(url);
        } catch (Exception e) {
            LoggingService.Logger.error("Failed to load icon: " + fileName);
            ErrorHandler.Exception(e);
        }
        return fallback != null ? fallback : new ImageIcon();
    }

    private static BufferedImage loadImage(String fileName, BufferedImage fallback) {
        try {
            URL url = ReadUtility.class.getResource(IMAGE_PATH + fileName);
            if (url != null) return ImageIO.read(url);
        } catch (IOException e) {
            LoggingService.Logger.error("Failed to load image: " + fileName);
            ErrorHandler.Exception(e);
        }
        return fallback != null ? fallback : RenderService.generateMissingTexture(64);
    }
}