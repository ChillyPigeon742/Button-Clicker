package net.alek.buttonclicker.utilities;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.engine.MenuManager;
import net.alek.buttonclicker.components.ATimer;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.services.RenderService;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class WriteUtility {
    public static Byte saveBeingCreated = null;
    
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> scheduledFuture;

    public static void startAutosaveTimer() {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            scheduledFuture = scheduler.scheduleAtFixedRate(() -> SwingUtilities.invokeLater(() -> {
                if(MenuManager.isMenuOpen("Game")){
                    LoggingService.Logger.info("Saving...");

                    RenderService.titleImage.startSpinning();
                    RenderService.titleImage.setBounds(535, 110, 110, 110);
                    RenderService.titleImage.setVisible(true);

                    RenderService.autoSavingText.setVisible(true);

                    ATimer timer = new ATimer();
                    timer.setDelay(2);
                    timer.setTask(() -> {
                        if(MenuManager.isMenuOpen("Game")){
                            RenderService.titleImage.stopSpinning();
                            RenderService.titleImage.setBounds(10, 0, 100, 100);
                            RenderService.titleImage.setVisible(false);

                            RenderService.autoSavingText.setVisible(false);
                        }

                        if (Objects.equals(ReadUtility.currentSave, "Saves/save1.bcs")) {
                            writeSave(ReadUtility.save1, 1);
                        } else if (Objects.equals(ReadUtility.currentSave, "Saves/save2.bcs")) {
                            writeSave(ReadUtility.save2, 2);
                        } else if (Objects.equals(ReadUtility.currentSave, "Saves/save3.bcs")) {
                            writeSave(ReadUtility.save3, 3);
                        }
                    });
                    timer.start();
                }
            }), 60, 60, TimeUnit.SECONDS);
        }
    }

    public static void createSave(byte saveNumber){
        String filePath = "Saves/save" + saveNumber + ".bcs";
        try {
            File save = new File(filePath);
            save.createNewFile();
        } catch (IOException e) {
            ErrorHandler.IOException();
        }
    }

    public static void deleteSave(byte saveNumber){
        String filePath = "Saves/save" + saveNumber + ".bcs";
        File save = new File(filePath);
        save.delete();
    }

    public static void writeSave(Map<String, Object> saveData, int saveNumber){
        LoggingService.Logger.info("Writing map to save file "+saveNumber);
        String filePath = "Saves/save" + saveNumber + ".bcs";

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(saveData);
        } catch (IOException e) {
            ErrorHandler.IOException();
        }
        LoggingService.Logger.info("Finished writing successfully!");
    }

    public static void saveCurrentSave(){
        if (currentSave == null) {

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
    }

    public static void Wipe(int save){
        if(save == 1){
            ReadUtility.save1 = null;
        }else if(save == 2){
            ReadUtility.save2 = null;
        }else if(save == 3){
            ReadUtility.save3 = null;
        }
    }

    public static class JSONWriter {
        public static void writeJson(String filePath, String key, Object value) {
            Map<String, Object> data = new HashMap<>();

            File file = new File(filePath);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder json = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        json.append(line);
                    }
                    data = ReadUtility.JSONReader.parse(json.toString());
                } catch (IOException e) {
                    LoggingService.Logger.error("Could not write to the JSON file!");
                }
            }

            data.put(key, value);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(serialize(data, true, 0));
            } catch (IOException e) {
                LoggingService.Logger.error("Could not serialize the data to the JSON file!");
            }
        }

        private static String serialize(Object obj, boolean pretty, int indentLevel) {
            String indent = pretty ? "\t".repeat(indentLevel) : "";
            String nextIndent = pretty ? "\t".repeat(indentLevel + 1) : "";

            if (obj instanceof Map<?, ?> map) {
                StringBuilder sb = new StringBuilder();
                sb.append("{").append(pretty ? "\n" : "");
                for (var entry : map.entrySet()) {
                    sb.append(nextIndent).append("\"").append(entry.getKey()).append("\":");
                    sb.append(pretty ? " " : "").append(serialize(entry.getValue(), pretty, indentLevel + 1)).append(",");
                    if (pretty) sb.append("\n");
                }
                if (!map.isEmpty()) {
                    sb.setLength(sb.length() - (pretty ? 2 : 1)); // remove last comma and optional newline
                    if (pretty) sb.append("\n").append(indent);
                }
                sb.append("}");
                return sb.toString();
            } else if (obj instanceof List<?> list) {
                StringBuilder sb = new StringBuilder();
                sb.append("[").append(pretty ? "\n" : "");
                for (Object item : list) {
                    sb.append(nextIndent).append(serialize(item, pretty, indentLevel + 1)).append(",");
                    if (pretty) sb.append("\n");
                }
                if (!list.isEmpty()) {
                    sb.setLength(sb.length() - (pretty ? 2 : 1)); // remove last comma and optional newline
                    if (pretty) sb.append("\n").append(indent);
                }
                sb.append("]");
                return sb.toString();
            } else if (obj instanceof String str) {
                return "\"" + str.replace("\"", "\\\"") + "\"";
            } else if (obj instanceof Boolean || obj instanceof Number) {
                return obj.toString();
            } else {
                return "\"" + obj.toString().replace("\"", "\\\"") + "\"";
            }
        }
    }
}