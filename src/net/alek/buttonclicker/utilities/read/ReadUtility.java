package net.alek.buttonclicker.utilities.read;

import javafx.scene.media.Media;
import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.services.RenderService;
import net.alek.buttonclicker.utilities.write.WriteUtility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.Objects;

public class ReadUtility {

    public static Map<String, Object> save1 = new HashMap<>();
    public static Map<String, Object> save2 = new HashMap<>();
    public static Map<String, Object> save3 = new HashMap<>();
    public static Map<String, Object> settings = new HashMap<>();

    public static ImageIcon bcIcon;
    public static ImageIcon bcIcon2;
    public static ImageIcon bcIcon3;
    public static ImageIcon lightSideIcon;
    public static ImageIcon darkSideIcon;
    public static ImageIcon magicSideIcon;
    public static ImageIcon neutralSideIcon;
    public static ImageIcon errorIcon;
    public static ImageIcon warningIcon;
    public static ImageIcon infoIcon;
    public static ImageIcon questionIcon;
    public static ImageIcon backwardArrowIcon;
    public static ImageIcon forwardArrowIcon;
    public static ImageIcon trollIcon;
    public static ImageIcon buttonBaseIcon;
    public static ImageIcon buttonTopIcon;
    public static ImageIcon clickPowerIcon;
    public static ImageIcon missingIcon;
    public static ImageIcon cogIcon;

    public static BufferedImage portalWhitewall;

    public static Font Nunito = null;
    public static Font IndieFlower = null;
    public static Font Consolas = null;

    public static void loadGame(){
        LoggingService.Logger.info("Loading Game...");

        LoggingService.Logger.info("Loading Fonts...");
        loadFonts();
        registerFonts();

        LoggingService.Logger.info("Loading Icons...");
        loadIcons();

        LoggingService.Logger.info("Loading Images...");
        loadImages();

        LoggingService.Logger.info("Loading Audio Data...");
        loadAudioData();

        LoggingService.Logger.info("Initializing Save Data...");
        initializeSaveData();

        LoggingService.Logger.info("Reading Save Data...");
        loadSave(1);
        loadSave(2);
        loadSave(3);

        LoggingService.Logger.info("Initializing Settings Data");
        initializeSettingsData();

        LoggingService.Logger.info("Loading Settings...");
        loadSettings();

        LoggingService.Logger.info("Loading Complete!");
    }

    public static void unloadGame() {
        bcIcon = null;
        bcIcon2 = null;
        bcIcon3 = null;
        lightSideIcon = null;
        darkSideIcon = null;
        magicSideIcon = null;
        neutralSideIcon = null;
        errorIcon = null;
        warningIcon = null;
        infoIcon = null;
        questionIcon = null;
        backwardArrowIcon = null;
        forwardArrowIcon = null;
        trollIcon = null;
        buttonBaseIcon = null;
        buttonTopIcon = null;
        clickPowerIcon = null;
        cogIcon = null;

        portalWhitewall = null;

        WriteUtility.Wipe(1);
        WriteUtility.Wipe(2);
        WriteUtility.Wipe(3);

        Nunito = null;
        IndieFlower = null;
        Consolas = null;

        System.gc();
    }

    private static void loadFonts(){
        try {
            Nunito = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(
                    ReadUtility.class.getResourceAsStream("/assets/buttonclicker/fonts/Nunito.ttf"))).deriveFont(12f);
            IndieFlower = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(
                    ReadUtility.class.getResourceAsStream("/assets/buttonclicker/fonts/IndieFlower.ttf"))).deriveFont(12f);
            Consolas = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(
                    ReadUtility.class.getResourceAsStream("/assets/buttonclicker/fonts/Consolas.ttf"))).deriveFont(12f);
        } catch (FontFormatException | IOException e) {
            ErrorHandler.Exception(e);
        }
    }

    private static void registerFonts(){
        RenderService.graphicsEnvironment.registerFont(Nunito);
        RenderService.graphicsEnvironment.registerFont(IndieFlower);
        RenderService.graphicsEnvironment.registerFont(Consolas);
    }

    private static void loadIcons(){
        bcIcon = loadIcon("button_clicker_icon1.png");
        bcIcon2 = loadIcon("button_clicker_icon2.png");
        bcIcon3 = loadIcon("button_clicker_icon3.png");
        lightSideIcon = loadIcon("light_side.png");
        darkSideIcon = loadIcon("dark_side.png");
        magicSideIcon = loadIcon("magic_side.png");
        neutralSideIcon = loadIcon("neutral_side.png");
        errorIcon = loadIcon("error_icon.png");
        warningIcon = loadIcon("warning_icon.png");
        infoIcon = loadIcon("information_icon.png");
        questionIcon = loadIcon("question_icon.png");
        backwardArrowIcon = loadIcon("arrow_left.png");
        forwardArrowIcon = loadIcon("arrow_right.png");
        trollIcon = loadIcon("troll.png");
        buttonBaseIcon = loadIcon("base.png");
        buttonTopIcon = loadIcon("button.png");
        clickPowerIcon = loadIcon("click_power_icon.png");
        missingIcon = loadIcon("missing.png");
        cogIcon = loadIcon("cog.png");
    }

    private static ImageIcon loadIcon(String fileName) {
        return new ImageIcon(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/images/" + fileName)));
    }

    private static void loadImages(){
        try {
            portalWhitewall = ImageIO.read(Objects.requireNonNull(
                    ReadUtility.class.getResource("/assets/buttonclicker/images/portal_whitewall.png")));
        } catch (IOException e) {
            ErrorHandler.Exception(e);
        }
    }

    private static void loadAudioData(){
        loadSFXAudioData();
        loadMusicAudioData();
    }

    private static void loadSFXAudioData(){
        AudioService.SFX.select = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/sfx/select.mp3")).toExternalForm());
        AudioService.SFX.click = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/sfx/click.mp3")).toExternalForm());
        AudioService.SFX.purchase = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/sfx/purchase.mp3")).toExternalForm());
        AudioService.SFX.declined = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/sfx/declined.mp3")).toExternalForm());
    }

    private static void loadMusicAudioData(){
        AudioService.Music.menu1 = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/music/menu/menu1.mp3")).toExternalForm());
        AudioService.Music.menu2 = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/music/menu/menu2.mp3")).toExternalForm());
        AudioService.Music.loading = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/music/menu/loading.mp3")).toExternalForm());
        AudioService.Music.game1 = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/music/game/game1.mp3")).toExternalForm());
        AudioService.Music.game2 = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/music/game/game2.mp3")).toExternalForm());
        AudioService.Music.pause = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/music/game/pause.mp3")).toExternalForm());
        AudioService.Music.shop = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/music/game/shop.mp3")).toExternalForm());
    }

    private static void initializeSaveData(){
        save1.put("saveName", null);
        save1.put("playedBefore", null);
        save1.put("side", null);
        save1.put("clicks", null);
        save1.put("clickPower", null);

        save2.put("saveName", null);
        save2.put("playedBefore", null);
        save2.put("side", null);
        save2.put("clicks", null);
        save2.put("clickPower", null);

        save3.put("saveName", null);
        save3.put("playedBefore", null);
        save3.put("side", null);
        save3.put("clicks", null);
        save3.put("clickPower", null);
    }

    private static void initializeSettingsData(){
        save1.put("currentSave", null);
        save1.put("masterVolume", null);
        save1.put("musicVolume", null);
        save1.put("sfxVolume", null);
        save1.put("musicDelay", null);
    }

    public static void loadSave(int saveNumber) {
        String filePath = "Saves/save" + saveNumber + ".bcs";

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Map<String, Object> readHashMap = castSave(in.readObject());

            if(saveNumber == 1){
                save1 = readHashMap;
            }else if(saveNumber == 2){
                save2 = readHashMap;
            }else if(saveNumber == 3){
                save3 = readHashMap;
            }
        } catch (IOException | ClassNotFoundException e) {
            ErrorHandler.Exception(e);
        }
    }

    private static Map<String, Object> castSave(Object obj) {
        if (!(obj instanceof Map<?, ?> rawMap)) {
            ErrorHandler.Exception(new ClassCastException("Object is not a Map"));
            return Collections.emptyMap();
        }

        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (!(key instanceof String)) {
                ErrorHandler.Exception(new ClassCastException("Map contains non-String key: " + key));
                return Collections.emptyMap();
            }

            result.put((String) key, value);
        }

        return result;
    }

    private static void loadSettings(){
        settings.put("currentSave", JSONReader.getString("Data/settings.json", "currentSave"));

        Object currentSaveKey = settings.get("currentSave");
        Map<String, Object> currentSave = getCurrentSave();

        if (currentSaveKey == null) {
            RenderService.startGameButton.setText("Select A Save To Continue");
            RenderService.startGameButton.setBackground(Color.GRAY);
            RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 21));
            RenderService.quitButton.setText("Quit");
            RenderService.currentSaveText.setText("Current Save: none");
        } else {
            RenderService.startGameButton.setBackground(Color.GREEN);
            RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 27));
            RenderService.quitButton.setText("Save & Quit");

            boolean playedBefore = currentSave != null && Boolean.TRUE.equals(currentSave.get("playedBefore"));
            RenderService.startGameButton.setText(playedBefore ? "Resume Game" : "Start Game");

            String saveName = currentSave != null ? currentSave.getOrDefault("saveName", "unknown").toString() : "unknown";
            RenderService.currentSaveText.setText("Current Save: " + saveName);
        }

        AudioService.SoundManager.setMasterVolume(JSONReader.getInt("Data/settings.json", "master_volume"));
        RenderService.masterVolumeSlider.setValue(AudioService.SoundManager.masterVolumeUSER);

        AudioService.SoundManager.setMusicVolume(JSONReader.getInt("Data/settings.json", "music_volume"));
        RenderService.musicVolumeSlider.setValue(AudioService.SoundManager.musicVolumeUSER);

        AudioService.SoundManager.setSFXVolume(JSONReader.getInt("Data/settings.json", "sfx_volume"));
        RenderService.sfxVolumeSlider.setValue(AudioService.SoundManager.sfxVolumeUSER);

        AudioService.SoundManager.setMusicDelay((byte) JSONReader.getInt("Data/settings.json", "music_delay"));
        RenderService.musicDelaySpinner.setValue(AudioService.SoundManager.getMusicDelay());
        RenderService.musicDelaySpinnerText.setText(AudioService.SoundManager.getMusicDelay()+" Secs");
    }

    public static Map<String, Object> getCurrentSave(){
        Object raw = settings.get("currentSave");
        if (!(raw instanceof String currentSave)) {
            return null;
        }

        return switch (currentSave) {
            case "Saves/save1.bcs" -> save1;
            case "Saves/save2.bcs" -> save2;
            case "Saves/save3.bcs" -> save3;
            default -> null;
        };
    }

    public static int getCurrentSaveNumber(){
        Object raw = settings.get("currentSave");
        if (!(raw instanceof String currentSave)) {
            return -1;
        }

        return switch (currentSave) {
            case "Saves/save1.bcs" -> 1;
            case "Saves/save2.bcs" -> 2;
            case "Saves/save3.bcs" -> 3;
            default -> 0;
        };
    }
}