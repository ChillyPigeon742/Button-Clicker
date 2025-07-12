package net.alek.buttonclicker.write.json;

import net.alek.buttonclicker.core.ErrorHandler;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.read.json.JSONReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JSONWriter {

    private final Map<String, Object> data;
    private final File outputFile;
    private final JSONSerializer serializer;

    private JSONWriter(File file) {
        this.outputFile = file;
        this.data = new HashMap<>();

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                JSONReader readerInstance = new JSONReader(json.toString());
                Map<String, Object> existingData = readerInstance.readMap("");
                this.data.putAll(existingData);

            } catch (IOException e) {
                LoggingService.Logger.error("Could not read JSON file! " + e.getMessage());
                ErrorHandler.Exception(e);
            }
        }
        this.serializer = new JSONSerializer(this);
    }

    public static JSONWriter toFile(String filePath) {
        return new JSONWriter(new File(filePath));
    }

    public static JSONWriter toFile(File file) {
        return new JSONWriter(file);
    }

    public JSONWriter disablePrettyPrint() {
        this.serializer.setPrettyPrint(false);
        return this;
    }

    public JSONWriter indentWith(String indentUnit) {
        this.serializer.setIndentUnit(indentUnit);
        return this;
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {
            writer.write(serializer.serialize(data));
        } catch (IOException e) {
            LoggingService.Logger.error("Could not write to JSON file! " + e.getMessage());
            ErrorHandler.Exception(e);
        }
    }

    public JSONWriter writeInt(String key, int value) {
        data.put(key, value);
        saveToFile();
        return this;
    }

    public JSONWriter writeLong(String key, long value) {
        data.put(key, value);
        saveToFile();
        return this;
    }

    public JSONWriter writeFloat(String key, float value) {
        data.put(key, value);
        saveToFile();
        return this;
    }

    public JSONWriter writeDouble(String key, double value) {
        data.put(key, value);
        saveToFile();
        return this;
    }

    public JSONWriter writeBoolean(String key, boolean value) {
        data.put(key, value);
        saveToFile();
        return this;
    }

    public JSONWriter writeString(String key, String value) {
        data.put(key, value);
        saveToFile();
        return this;
    }

    public JSONWriter writeList(String key, List<?> value) {
        data.put(key, value);
        saveToFile();
        return this;
    }

    public JSONWriter writeMap(String key, Map<String, ?> value) {
        data.put(key, value);
        saveToFile();
        return this;
    }

    public JSONWriter writeObject(String key, Object value) {
        data.put(key, value);
        saveToFile();
        return this;
    }

    public JSONSerializer getSerializer(){
        return serializer;
    }
}