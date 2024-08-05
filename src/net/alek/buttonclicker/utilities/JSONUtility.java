package net.alek.buttonclicker.utilities;

import com.google.gson.*;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.engine.ResourceManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class JSONUtility {
    private static final Gson gson = new Gson();

    public static void writeAStringToJsonFile(String filePath, String key, String value) {
        try {
            JsonObject existingContent = new JsonObject();
            try (FileReader reader = new FileReader(filePath)) {
                existingContent = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (IOException e) {
                ErrorHandler.IOException("JSON", 23);
            }

            existingContent.addProperty(key, value);

            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(existingContent, writer);
            } catch (IOException e) {
                ErrorHandler.IOException("JSON", 31);
            }
        } catch (JsonIOException e) {
            ErrorHandler.JsonIOException("JSON", 34);
        } catch (JsonSyntaxException e) {
            ErrorHandler.JsonSyntaxException("JSON", 36);
        }
    }

    public static String readAStringFromJsonFile(String filePath, String key) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get(key).getAsString();
        } catch (IOException e) {
            ErrorHandler.IOException("JSON", 45);
            return null;
        }
    }

    public static String readAStringThroughAURLFromJsonFile(String filePath, String key) {
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(ResourceManager.class.getResource(filePath)).openStream())) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get(key).getAsString();
        } catch (IOException e) {
            ErrorHandler.IOException("JSON", 55);
            return null;
        }
    }

    public static void writeAIntToJsonFile(String filePath, String key, int value) {
        try {
            JsonObject existingContent = new JsonObject();
            try (FileReader reader = new FileReader(filePath)) {
                existingContent = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (IOException e) {
                ErrorHandler.IOException("JSON", 66);
            }

            existingContent.addProperty(key, value);

            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(existingContent, writer);
            } catch (IOException e) {
                ErrorHandler.IOException("JSON", 74);
            }
        } catch (JsonIOException e) {
            ErrorHandler.JsonIOException("JSON", 77);
        } catch (JsonSyntaxException e) {
            ErrorHandler.JsonSyntaxException("JSON", 79);
        }
    }

    public static int readAIntFromJsonFile(String filePath, String key) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get(key).getAsInt();
        } catch (IOException e) {
            ErrorHandler.IOException("JSON", 88);
        }
        return 0;
    }
}