package net.alek.buttonclicker.utilities.read;

import net.alek.buttonclicker.services.LoggingService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JSONReader {
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