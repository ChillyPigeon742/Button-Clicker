package net.alek.buttonclicker.read.saves;

import net.alek.buttonclicker.core.ErrorHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SaveReader {
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
