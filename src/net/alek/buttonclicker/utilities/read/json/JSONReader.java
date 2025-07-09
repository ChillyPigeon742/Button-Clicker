package net.alek.buttonclicker.utilities.read.json;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.services.LoggingService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JSONReader {
    private final String json;
    private final Object root;
    private final JSONParser parser;
    private final JSONDeserializer deserializer;

    public JSONReader(String json) {
        this.json = json;
        this.deserializer = new JSONDeserializer();
        this.parser = new JSONParser(json, deserializer);
        this.root = parser.getRoot();
    }

    public static JSONReader fromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return new JSONReader(sb.toString());

        } catch (Exception e) {
            LoggingService.Logger.error("Could not read JSON file! " + e.getMessage());
            ErrorHandler.Exception(e);
            return null;
        }
    }

    public <T> T read(String key, Class<T> targetClass) {
        if (!(root instanceof Map<?, ?> map)) return null;
        Object rawValue = map.get(key);
        return deserializer.castWithDeserialization(rawValue, targetClass);
    }

    public String readString(String key) {
        return read(key, String.class);
    }

    public boolean readBoolean(String key) {
        Object val = read(key, Object.class);
        if (val instanceof Boolean) return (Boolean) val;
        return Boolean.parseBoolean(String.valueOf(val));
    }

    public int readInt(String key) {
        Object val = read(key, Object.class);
        if (val instanceof Number) return ((Number) val).intValue();
        return Integer.parseInt(String.valueOf(val));
    }

    public long readLong(String key) {
        Object val = read(key, Object.class);
        if (val instanceof Number) return ((Number) val).longValue();
        return Long.parseLong(String.valueOf(val));
    }

    public byte readByte(String key) {
        Object val = read(key, Object.class);
        if (val instanceof Number) return ((Number) val).byteValue();
        return Byte.parseByte(String.valueOf(val));
    }

    public float readFloat(String key) {
        Object val = read(key, Object.class);
        if (val instanceof Number) return ((Number) val).floatValue();
        return Float.parseFloat(String.valueOf(val));
    }

    public double readDouble(String key) {
        Object val = read(key, Object.class);
        if (val instanceof Number) return ((Number) val).doubleValue();
        return Double.parseDouble(String.valueOf(val));
    }

    public List<Object> readList(String key) {
        Object val = read(key, Object.class);
        if (!(val instanceof List<?>)) return new ArrayList<>();
        return convertList((List<?>) val);
    }

    public Map<String, Object> readMap(String key) {
        Object val = read(key, Object.class);
        if (!(val instanceof Map<?, ?>)) return new HashMap<>();
        return convertMap((Map<?, ?>) val);
    }

    private Map<String, Object> convertMap(Map<?, ?> rawMap) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map<?, ?>) {
                value = convertMap((Map<?, ?>) value);
            } else if (value instanceof List<?>) {
                value = convertList((List<?>) value);
            }
            result.put(entry.getKey().toString(), value);
        }
        return result;
    }

    private List<Object> convertList(List<?> rawList) {
        List<Object> result = new ArrayList<>();
        for (Object item : rawList) {
            if (item instanceof Map<?, ?>) {
                result.add(convertMap((Map<?, ?>) item));
            } else if (item instanceof List<?>) {
                result.add(convertList((List<?>) item));
            } else {
                result.add(item);
            }
        }
        return result;
    }

    public JSONDeserializer getDeserializer() {
        return deserializer;
    }

    public JSONParser getParser() {
        return parser;
    }

    public Object getRoot() {
        return root;
    }
}