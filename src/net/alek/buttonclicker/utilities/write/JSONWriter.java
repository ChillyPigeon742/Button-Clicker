package net.alek.buttonclicker.utilities.write;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.utilities.read.JSONReader;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class JSONWriter {

    private final Map<String, Object> data;
    private final File outputFile;
    private boolean prettyPrint = true;
    private String indentUnit = "\t";

    private static final Map<Class<?>, BiFunction<Object, JSONWriter, String>> typeAdapters = new ConcurrentHashMap<>();
    private static final Map<Class<?>, CustomSerializer> customSerializers = new ConcurrentHashMap<>();

    static {
        registerTypeAdapter(Enum.class, (obj, writer) -> "\"" + ((Enum<?>) obj).name() + "\"");
        registerTypeAdapter(LocalDate.class, (obj, writer) -> "\"" + ((LocalDate) obj).format(DateTimeFormatter.ISO_LOCAL_DATE) + "\"");
        registerTypeAdapter(LocalDateTime.class, (obj, writer) -> "\"" + ((LocalDateTime) obj).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\"");
        registerTypeAdapter(ZonedDateTime.class, (obj, writer) -> "\"" + ((ZonedDateTime) obj).format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + "\"");
        registerTypeAdapter(Color.class, (obj, writer) -> {
            Color c = (Color) obj;
            return "\"" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + "\"";
        });
        registerTypeAdapter(BigInteger.class, (obj, writer) -> "\"" + obj.toString() + "\"");
        registerTypeAdapter(BigDecimal.class, (obj, writer) -> "\"" + obj.toString() + "\"");
    }

    public interface CustomSerializer {
        String serialize(Object obj, JSONWriter writer);
    }

    private JSONWriter(File file) {
        this.outputFile = file;
        this.data = new HashMap<>();

        // Load existing data if file exists
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                JSONReader readerInstance = new JSONReader(json.toString());
                Map<String, Object> existingData = readerInstance.getMap("");
                this.data.putAll(existingData);

            } catch (IOException e) {
                LoggingService.Logger.error("Could not read JSON file! " + e.getMessage());
                ErrorHandler.Exception(e);
            }
        }
    }

    public static JSONWriter toFile(String filePath) {
        return new JSONWriter(new File(filePath));
    }

    public static JSONWriter toFile(File file) {
        return new JSONWriter(file);
    }

    public JSONWriter disablePrettyPrint() {
        this.prettyPrint = false;
        return this;
    }

    public JSONWriter indentWith(String indentUnit) {
        this.indentUnit = indentUnit;
        return this;
    }

    public static void registerCustomSerializer(Class<?> clazz, CustomSerializer serializer) {
        customSerializers.put(clazz, serializer);
    }

    public static void registerTypeAdapter(Class<?> type, BiFunction<Object, JSONWriter, String> adapter) {
        typeAdapters.put(type, adapter);
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {
            writer.write(serialize(data, 0));
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

    private String serialize(Object obj, int indentLevel) {
        return serialize(obj, indentLevel, new HashSet<>());
    }

    private String serialize(Object obj, int indentLevel, Set<Object> seen) {
        if (obj == null) {
            return "null";
        }

        if (seen.contains(obj)) {
            return "\"[cyclic_reference]\"";
        }

        boolean addToSeen = obj instanceof Map || obj instanceof Collection;
        if (addToSeen) seen.add(obj);

        StringBuilder sb = new StringBuilder();

        // Check custom serializers first
        for (var entry : customSerializers.entrySet()) {
            if (entry.getKey().isInstance(obj)) {
                String result = entry.getValue().serialize(obj, this);
                if (addToSeen) seen.remove(obj);
                return result;
            }
        }

        // Check type adapters
        for (var entry : typeAdapters.entrySet()) {
            if (entry.getKey().isInstance(obj)) {
                String result = entry.getValue().apply(obj, this);
                if (addToSeen) seen.remove(obj);
                return result;
            }
        }

        if (obj instanceof Map<?, ?> map) {
            sb.append("{");
            if (prettyPrint) sb.append("\n");
            int count = 0;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key == null) {
                    if (addToSeen) seen.remove(obj);
                    throw new RuntimeException("JSON object key is null");
                }

                if (count++ > 0) {
                    sb.append(",");
                    if (prettyPrint) sb.append("\n");
                }

                if (prettyPrint) sb.append(indent(indentLevel + 1));
                sb.append("\"").append(escapeString(key.toString())).append("\":");
                if (prettyPrint) sb.append(" ");
                sb.append(serialize(value, indentLevel + 1, seen));
            }
            if (prettyPrint) sb.append("\n").append(indent(indentLevel));
            sb.append("}");
            if (addToSeen) seen.remove(obj);
            return sb.toString();
        }

        if (obj instanceof Collection<?> coll) {
            sb.append("[");
            if (prettyPrint) sb.append("\n");
            int count = 0;
            for (Object item : coll) {
                if (count++ > 0) {
                    sb.append(",");
                    if (prettyPrint) sb.append("\n");
                }
                if (prettyPrint) sb.append(indent(indentLevel + 1));
                sb.append(serialize(item, indentLevel + 1, seen));
            }
            if (prettyPrint) sb.append("\n").append(indent(indentLevel));
            sb.append("]");
            if (addToSeen) seen.remove(obj);
            return sb.toString();
        }

        if (obj instanceof String s) {
            if (addToSeen) seen.remove(obj);
            return "\"" + escapeString(s) + "\"";
        }

        if (obj instanceof Number || obj instanceof Boolean) {
            if (addToSeen) seen.remove(obj);
            return obj.toString();
        }

        // Handle arbitrary objects
        sb.append("{");
        if (prettyPrint) sb.append("\n");
        if (prettyPrint) sb.append(indent(indentLevel + 1));
        sb.append("\"type\": \"").append(escapeString(obj.getClass().getSimpleName())).append("\"");

        for (var field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldVal = field.get(obj);
                if (fieldVal == null) continue;
                sb.append(",");
                if (prettyPrint) sb.append("\n");
                if (prettyPrint) sb.append(indent(indentLevel + 1));
                sb.append("\"").append(escapeString(field.getName())).append("\":");
                if (prettyPrint) sb.append(" ");
                sb.append(serialize(fieldVal, indentLevel + 1, seen));
            } catch (IllegalAccessException e) {
                ErrorHandler.Exception(e);
            }
        }

        if (prettyPrint) sb.append("\n").append(indent(indentLevel));
        sb.append("}");
        if (addToSeen) seen.remove(obj);
        return sb.toString();
    }

    private String indent(int level) {
        return indentUnit.repeat(level);
    }

    private static String escapeString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\' -> sb.append("\\\\");
                case '"' -> sb.append("\\\"");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20 || c > 0x7E) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }
}