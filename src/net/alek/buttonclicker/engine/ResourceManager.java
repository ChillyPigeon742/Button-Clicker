package net.alek.buttonclicker.engine;

import net.alek.buttonclicker.libraries.StorageLibrary;
import net.alek.buttonclicker.utilities.AudioUtility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ResourceManager {
    public ResourceManager(){
        StorageLibrary.logger.info("Loading Resources...");
        try{
            Font Nunito = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/fonts/Nunito.ttf")).openStream()).deriveFont(12f);
            Font IndieFlower = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/fonts/IndieFlower.ttf")).openStream()).deriveFont(12f);

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(Nunito);
            graphicsEnvironment.registerFont(IndieFlower);
        } catch(IOException e){
            ErrorHandler.IOException("RESOURCE", 23);
        }catch(FontFormatException e1){
            ErrorHandler.FontFormatException("RESOURCE", 25);
        }

        StorageLibrary.bcIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/button_clicker_icon1.png")));
        StorageLibrary.bcIcon2 = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/button_clicker_icon2.png")));
        StorageLibrary.bcIcon3 = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/button_clicker_icon3.png")));
        StorageLibrary.lightSideIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/light_side.png")));
        StorageLibrary.darkSideIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/dark_side.png")));
        StorageLibrary.magicSideIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/magic_side.png")));
        StorageLibrary.neutralSideIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/neutral_side.png")));
        StorageLibrary.errorIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/error_icon.png")));
        StorageLibrary.warningIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/warning_icon.png")));
        StorageLibrary.infoIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/information_icon.png")));
        StorageLibrary.questionIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/question_icon.png")));
        StorageLibrary.backwardArrowIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/arrow_left.png")));
        StorageLibrary.forwardArrowIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/arrow_right.png")));
        StorageLibrary.trollIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/troll.png")));
        StorageLibrary.buttonBaseIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/base.png")));
        StorageLibrary.buttonTopIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/button.png")));
        try {
            StorageLibrary.portal2Whitewall = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/portal2_whitewall.png")));
        } catch (IOException e) {
            ErrorHandler.IOException("RESOURCE", 47);
        }
        StorageLibrary.clickPowerIcon = new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/images/click_power_icon.png")));

        AudioUtility.SoundManager.loadTrack(2, "/assets/buttonclicker/audio/music/menu/menu1.mp3");
        AudioUtility.SoundManager.loadTrack(2, "/assets/buttonclicker/audio/music/menu/menu2.mp3");
        AudioUtility.SoundManager.loadTrack(3, "/assets/buttonclicker/audio/music/menu/loading.mp3");
        AudioUtility.SoundManager.loadTrack(2, "/assets/buttonclicker/audio/music/game/game1.mp3");
        AudioUtility.SoundManager.loadTrack(2, "/assets/buttonclicker/audio/music/game/game2.mp3");
        AudioUtility.SoundManager.loadTrack(3, "/assets/buttonclicker/audio/music/game/pause.mp3");
        AudioUtility.SoundManager.loadTrack(3, "/assets/buttonclicker/audio/music/game/shop.mp3");

        AudioUtility.SoundManager.loadTrack(1, "/assets/buttonclicker/audio/sfx/select.mp3");
        AudioUtility.SoundManager.loadTrack(1, "/assets/buttonclicker/audio/sfx/click.mp3");
        AudioUtility.SoundManager.loadTrack(1, "/assets/buttonclicker/audio/sfx/purchase.mp3");
        AudioUtility.SoundManager.loadTrack(1, "/assets/buttonclicker/audio/sfx/declined.mp3");

        StorageLibrary.logger.info("Finished Loading Resources!");
    }
}