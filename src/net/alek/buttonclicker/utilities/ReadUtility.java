package net.alek.buttonclicker.utilities;

import javafx.scene.media.Media;
import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.services.RenderService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Objects;

public class ReadUtility {

    public static Map<String, Object> save1 = new HashMap<>();
    public static Map<String, Object> save2 = new HashMap<>();
    public static Map<String, Object> save3 = new HashMap<>();

    public static String currentSave = null;

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

    public ReadUtility(){
        loadGame();
    }

    private static void loadGame(){
        LoggingService.Logger.info("Loading Game...");

        LoggingService.Logger.info("Loading Fonts...");
        try {
            Nunito = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ReadUtility.class.getResource("/assets/buttonclicker/fonts/Nunito.ttf")).openStream()).deriveFont(12f);
            IndieFlower = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ReadUtility.class.getResource("/assets/buttonclicker/fonts/IndieFlower.ttf")).openStream()).deriveFont(12f);
            Consolas = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ReadUtility.class.getResource("/assets/buttonclicker/fonts/Consolas.ttf")).openStream()).deriveFont(12f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Nunito);
            ge.registerFont(IndieFlower);
            ge.registerFont(Consolas);
        } catch (IOException e) {
            ErrorHandler.IOException();
        } catch (FontFormatException e1) {
            ErrorHandler.FontFormatException();
        }

        LoggingService.Logger.info("Loading Icons...");
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

        LoggingService.Logger.info("Loading Images...");
        try {
            portalWhitewall = ImageIO.read(Objects.requireNonNull(
                    ReadUtility.class.getResource("/assets/buttonclicker/images/portal_whitewall.png")));
        } catch (IOException e) {
            ErrorHandler.IOException();
        }

        LoggingService.Logger.info("Loading Audio Data...");
        LoggingService.Logger.info("Loading SFX Data...");
        AudioService.SFX.select = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/sfx/select.mp3")).toExternalForm());
        AudioService.SFX.click = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/sfx/click.mp3")).toExternalForm());
        AudioService.SFX.purchase = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/sfx/purchase.mp3")).toExternalForm());
        AudioService.SFX.declined = new Media(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/audio/sfx/declined.mp3")).toExternalForm());

        LoggingService.Logger.info("Loading Music Data...");
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


        LoggingService.Logger.info("Initializing Save Data...");
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

        LoggingService.Logger.info("Reading Save Data...");
        loadSave(1);
        loadSave(2);
        loadSave(3);

        LoggingService.Logger.info("Loading Preferences...");
        currentSave = Objects.requireNonNull(JSONReader.getString("Data/settings.json", "current_save"));

        if (currentSave == null) {
            RenderService.startGameButton.setText("Select A Save To Continue");
            RenderService.startGameButton.setBackground(Color.GRAY);
            RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 21));
            RenderService.quitButton.setText("Quit");
        } else {
            RenderService.startGameButton.setBackground(Color.GREEN);
            RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 27));
            RenderService.quitButton.setText("Save & Quit");

            Map<String, Object> save = switch (currentSave) {
                case "Saves/save1.bcs" -> save1;
                case "Saves/save2.bcs" -> save2;
                case "Saves/save3.bcs" -> save3;
                default -> null;
            };

            if (save != null && Boolean.TRUE.equals(save.get("playedBefore"))) {
                RenderService.startGameButton.setText("Resume Game");
            } else {
                RenderService.startGameButton.setText("Start Game");
            }
        }

        if (currentSave == null) {
            RenderService.currentSaveText.setText("Current Save: none");
        } else {
            Map<String, Object> save = switch (currentSave) {
                case "Saves/save1.bcs" -> save1;
                case "Saves/save2.bcs" -> save2;
                case "Saves/save3.bcs" -> save3;
                default -> null;
            };

            if (save != null) {
                String name = (String) save.getOrDefault("saveName", "unknown");
                RenderService.currentSaveText.setText("Current Save: " + name);
            } else {
                RenderService.currentSaveText.setText("Current Save: unknown");
            }
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
        LoggingService.Logger.info("Finished Loading Preferences!");

        LoggingService.Logger.info("Loading Complete!");
    }

    private static ImageIcon loadIcon(String fileName) {
        return new ImageIcon(Objects.requireNonNull(
                ReadUtility.class.getResource("/assets/buttonclicker/images/" + fileName)));
    }

    public static void loadSave(int saveNumber) {
        String filePath = "Saves/save" + saveNumber + ".bcs";

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Map<String, Object> readHashMap = (Map<String, Object>) in.readObject();

            if(saveNumber == 1){
                save1 = readHashMap;
            }else if(saveNumber == 2){
                save2 = readHashMap;
            }else if(saveNumber == 3){
                save3 = readHashMap;
            }
        } catch (IOException e) {
            ErrorHandler.IOException();
        } catch (ClassNotFoundException e) {
            ErrorHandler.ClassNotFoundException();
        }
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

    public static class JSONReader {
        private static <T> T readJson(String filePath, String key) {
            File file = new File(filePath);
            if (!file.exists()) return null;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                Map<String, Object> data = parse(json.toString());
                return cast(data.get(key));

            } catch (IOException e) {
                LoggingService.Logger.error("Could not read JSON file!");
                return null;
            }
        }

        private static <T> T cast(Object obj) {
            @SuppressWarnings("unchecked")
            T result = (T) obj;
            return result;
        }

        public static boolean keyExists(String filePath, String key) {
            File file = new File(filePath);
            if (!file.exists()) return false;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                Map<String, Object> data = parse(json.toString());
                return data.containsKey(key);

            } catch (IOException e) {
                LoggingService.Logger.error("Could not verify key existence!");
                return false;
            }
        }

        public static Set<String> getAllKeys(String filePath) {
            Set<String> keys = new HashSet<>();
            File file = new File(filePath);
            if (!file.exists()) return keys;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                Map<String, Object> data = parse(json.toString());
                keys.addAll(data.keySet());

            } catch (IOException e) {
                LoggingService.Logger.error("Could not retrieve the keys within the JSON file!");
            }
            return keys;
        }

        public static Map<String, Object> readAll(String filePath) {
            File file = new File(filePath);
            if (!file.exists()) return new HashMap<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                return parse(json.toString());

            } catch (IOException e) {
                LoggingService.Logger.error("Could not parse the JSON file!");
                return new HashMap<>();
            }
        }

        public static String getString(String filePath, String key) {
            Object val = readJson(filePath, key);
            return val != null ? val.toString() : null;
        }

        public static boolean getBoolean(String filePath, String key) {
            Object val = readJson(filePath, key);
            return val instanceof Boolean ? (Boolean) val : Boolean.parseBoolean(String.valueOf(val));
        }

        public static int getInt(String filePath, String key) {
            Object val = readJson(filePath, key);
            return val instanceof Number ? ((Number) val).intValue() : Integer.parseInt(String.valueOf(val));
        }

        public static byte getByte(String filePath, String key) {
            Object val = readJson(filePath, key);
            return val instanceof Number ? ((Number) val).byteValue() : Byte.parseByte(String.valueOf(val));
        }

        public static double getDouble(String filePath, String key) {
            Object val = readJson(filePath, key);
            return val instanceof Number ? ((Number) val).doubleValue() : Double.parseDouble(String.valueOf(val));
        }

        @SuppressWarnings("unchecked")
        public static List<Object> getList(String filePath, String key) {
            Object val = readJson(filePath, key);
            return val instanceof List<?> ? (List<Object>) val : new ArrayList<>();
        }

        @SuppressWarnings("unchecked")
        public static Map<String, Object> getMap(String filePath, String key) {
            Object val = readJson(filePath, key);
            return val instanceof Map<?, ?> ? (Map<String, Object>) val : new HashMap<>();
        }

        private static String json;
        private static int index;

        public static Map<String, Object> parse(String json) {
            json = json.trim();
            index = 0;
            return parseObject();
        }

        private static Map<String, Object> parseObject() {
            Map<String, Object> map = new HashMap<>();
            expect('{');

            while (true) {
                skipWhitespace();
                if (peek() == '}') {
                    index++;
                    break;
                }

                String key = parseString();
                skipWhitespace();
                expect(':');
                skipWhitespace();
                Object value = parseValue();
                map.put(key, value);

                skipWhitespace();
                if (peek() == ',') {
                    index++;
                } else if (peek() == '}') {
                    index++;
                    break;
                } else {
                    throw new RuntimeException("Expected ',' or '}' at position " + index);
                }
            }

            return map;
        }

        private static List<Object> parseArray() {
            List<Object> list = new ArrayList<>();
            expect('[');

            while (true) {
                skipWhitespace();
                if (peek() == ']') {
                    index++;
                    break;
                }

                Object value = parseValue();
                list.add(value);

                skipWhitespace();
                if (peek() == ',') {
                    index++;
                } else if (peek() == ']') {
                    index++;
                    break;
                } else {
                    throw new RuntimeException("Expected ',' or ']' at position " + index);
                }
            }

            return list;
        }

        private static Object parseValue() {
            skipWhitespace();
            char c = peek();

            if (c == '"') return parseString();
            if (c == '{') return parseObject();
            if (c == '[') return parseArray();
            if (startsWith("true")) {
                index += 4;
                return true;
            }
            if (startsWith("false")) {
                index += 5;
                return false;
            }
            if (startsWith("null")) {
                index += 4;
                return null;
            }
            return parseNumberOrLiteral();
        }

        private static String parseString() {
            expect('"');
            StringBuilder sb = new StringBuilder();
            while (true) {
                char c = json.charAt(index++);
                if (c == '\\') {
                    char next = json.charAt(index++);
                    switch (next) {
                        case '"': sb.append('"'); break;
                        case '\\': sb.append('\\'); break;
                        case 'n': sb.append('\n'); break;
                        case 't': sb.append('\t'); break;
                        case 'u':
                            String hex = json.substring(index, index + 4);
                            index += 4;
                            int codePoint = Integer.parseInt(hex, 16);
                            sb.append((char) codePoint);
                            break;
                        default: sb.append(next); break;
                    }
                } else if (c == '"') {
                    break;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        private static Object parseNumberOrLiteral() {
            int start = index;
            while (index < json.length() && !isEndChar(json.charAt(index))) {
                index++;
            }
            String raw = json.substring(start, index);
            try {
                if (raw.contains(".")) return Double.parseDouble(raw);
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                return raw;
            }
        }

        private static boolean isEndChar(char c) {
            return c == ',' || c == '}' || c == ']' || Character.isWhitespace(c);
        }

        private static void skipWhitespace() {
            while (index < json.length() && Character.isWhitespace(json.charAt(index))) index++;
        }

        private static char peek() {
            if (index >= json.length()) throw new RuntimeException("Unexpected end of JSON");
            return json.charAt(index);
        }

        private static void expect(char expected) {
            if (peek() != expected) {
                throw new RuntimeException("Expected '" + expected + "' at position " + index);
            }
            index++;
        }

        private static boolean startsWith(String s) {
            return json.startsWith(s, index);
        }
    }
}