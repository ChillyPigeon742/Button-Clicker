package net.alek.buttonclicker.utilities.read;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.services.LoggingService;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class JSONReader {
    private static String json;
    private static int index;

    private static final Map<Class<?>, Function<Object, ?>> deserializers = new ConcurrentHashMap<>();

    static {
        deserializers.put(Color.class, JSONReader::deserializeColor);
        deserializers.put(Enum.class, JSONReader::deserializeEnum);
        deserializers.put(LocalDate.class, JSONReader::deserializeLocalDate);
        deserializers.put(LocalDateTime.class, JSONReader::deserializeLocalDateTime);
        deserializers.put(ZonedDateTime.class, JSONReader::deserializeZonedDateTime);
    }

    private static <T> T readJson(String filePath, String key) {
        File file = new File(filePath);
        if (!file.exists()) return null;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            json = sb.toString();
            index = 0;
            skipWhitespaceAndComments();
            Map<String, Object> data = parseObject();

            Object rawValue = data.get(key);
            return castWithDeserialization(rawValue, null);

        } catch (Exception e) {
            LoggingService.Logger.error("Could not read JSON file! " + e.getMessage());
            ErrorHandler.Exception(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T castWithDeserialization(Object obj, Class<T> targetClass) {
        if (obj == null) return null;

        if (targetClass != null) {
            for (var entry : deserializers.entrySet()) {
                if (entry.getKey().isAssignableFrom(targetClass)) {
                    try {
                        return (T) entry.getValue().apply(obj);
                    } catch (Exception e) {
                        ErrorHandler.Exception(new RuntimeException("Failed to deserialize to " + targetClass.getName(), e));
                        return null;
                    }
                }
            }
        }

        try {
            return (T) obj;
        } catch (ClassCastException e) {
            ErrorHandler.Exception(new RuntimeException("Failed to cast object to " + (targetClass == null ? "unknown" : targetClass.getName()), e));
            return null;
        }
    }

    public static <T> T get(String filePath, String key, Class<T> targetClass) {
        Object rawValue = readJson(filePath, key);
        return castWithDeserialization(rawValue, targetClass);
    }

    public static String getString(String filePath, String key) {
        return get(filePath, key, String.class);
    }

    public static boolean getBoolean(String filePath, String key) {
        Object val = readJson(filePath, key);
        if (val instanceof Boolean) return (Boolean) val;
        return Boolean.parseBoolean(String.valueOf(val));
    }

    public static int getInt(String filePath, String key) {
        Object val = readJson(filePath, key);
        if (val instanceof Number) return ((Number) val).intValue();
        return Integer.parseInt(String.valueOf(val));
    }

    public static byte getByte(String filePath, String key) {
        Object val = readJson(filePath, key);
        if (val instanceof Number) return ((Number) val).byteValue();
        return Byte.parseByte(String.valueOf(val));
    }

    public static double getDouble(String filePath, String key) {
        Object val = readJson(filePath, key);
        if (val instanceof Number) return ((Number) val).doubleValue();
        return Double.parseDouble(String.valueOf(val));
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

    public static boolean keyExists(String filePath, String key) {
        File file = new File(filePath);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            json = sb.toString();
            index = 0;
            skipWhitespaceAndComments();
            Map<String, Object> data = parseObject();
            return data.containsKey(key);

        } catch (Exception e) {
            LoggingService.Logger.error("Could not verify key existence! " + e.getMessage());
            ErrorHandler.Exception(e);
            return false;
        }
    }

    public static Set<String> getAllKeys(String filePath) {
        Set<String> keys = new HashSet<>();
        File file = new File(filePath);
        if (!file.exists()) return keys;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            json = sb.toString();
            index = 0;
            skipWhitespaceAndComments();
            Map<String, Object> data = parseObject();
            keys.addAll(data.keySet());

        } catch (Exception e) {
            LoggingService.Logger.error("Could not retrieve the keys within the JSON file! " + e.getMessage());
            ErrorHandler.Exception(e);
        }
        return keys;
    }

    public static Map<String, Object> readAll(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            json = sb.toString();
            index = 0;
            skipWhitespaceAndComments();
            return parseObject();

        } catch (Exception e) {
            LoggingService.Logger.error("Could not parse the JSON file! " + e.getMessage());
            ErrorHandler.Exception(e);
            return new HashMap<>();
        }
    }

    public static Map<String, Object> parse(String input) {
        json = input;
        index = 0;
        skipWhitespaceAndComments();
        return parseObject();
    }

    private static Map<String, Object> parseObject() {
        Map<String, Object> map = new HashMap<>();
        expect('{');
        skipWhitespaceAndComments();

        while (true) {
            skipWhitespaceAndComments();
            if (peek() == '}') {
                index++;
                break;
            }

            String key = parseString();
            skipWhitespaceAndComments();
            expect(':');
            skipWhitespaceAndComments();
            Object value = parseValue();
            map.put(key, value);

            skipWhitespaceAndComments();
            char c = peek();
            if (c == ',') {
                index++;
            } else if (c == '}') {
                index++;
                break;
            } else {
                ErrorHandler.Exception(new RuntimeException("Expected ',' or '}' at position " + index));
            }
        }

        return map;
    }

    private static List<Object> parseArray() {
        List<Object> list = new ArrayList<>();
        expect('[');
        skipWhitespaceAndComments();

        while (true) {
            skipWhitespaceAndComments();
            if (peek() == ']') {
                index++;
                break;
            }

            Object value = parseValue();
            list.add(value);

            skipWhitespaceAndComments();
            char c = peek();
            if (c == ',') {
                index++;
            } else if (c == ']') {
                index++;
                break;
            } else {
                ErrorHandler.Exception(new RuntimeException("Expected ',' or ']' at position " + index));
            }
        }

        return list;
    }

    private static Object parseValue() {
        skipWhitespaceAndComments();
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
            if (index >= json.length()) ErrorHandler.Exception(new RuntimeException("Unexpected end of string"));
            char c = json.charAt(index++);
            if (c == '\\') {
                if (index >= json.length()) ErrorHandler.Exception(new RuntimeException("Unexpected end of string"));
                char next = json.charAt(index++);
                switch (next) {
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case '/': sb.append('/'); break;
                    case 'b': sb.append('\b'); break;
                    case 'f': sb.append('\f'); break;
                    case 'n': sb.append('\n'); break;
                    case 'r': sb.append('\r'); break;
                    case 't': sb.append('\t'); break;
                    case 'u':
                        if (index + 4 > json.length()) ErrorHandler.Exception(new RuntimeException("Incomplete unicode escape"));
                        String hex = json.substring(index, index + 4);
                        index += 4;
                        int codePoint = Integer.parseInt(hex, 16);
                        if (Character.isHighSurrogate((char) codePoint)) {
                            if (index + 6 <= json.length() && json.charAt(index) == '\\' && json.charAt(index + 1) == 'u') {
                                String lowHex = json.substring(index + 2, index + 6);
                                int lowCodePoint = Integer.parseInt(lowHex, 16);
                                if (Character.isLowSurrogate((char) lowCodePoint)) {
                                    sb.append(Character.toChars((Character.toCodePoint((char) codePoint, (char) lowCodePoint))));
                                    index += 6;
                                    break;
                                }
                            }
                        }
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
        boolean hasDecimal = false;
        boolean hasExponent = false;

        if (peek() == '-') index++;

        while (index < json.length()) {
            char c = json.charAt(index);
            if (Character.isDigit(c)) {
                index++;
            } else if (c == '.' && !hasDecimal) {
                hasDecimal = true;
                index++;
            } else if ((c == 'e' || c == 'E') && !hasExponent) {
                hasExponent = true;
                index++;
                if (index < json.length()) {
                    char next = json.charAt(index);
                    if (next == '+' || next == '-') {
                        index++;
                    }
                }
            } else {
                break;
            }
        }

        String raw = json.substring(start, index);
        try {
            if (hasDecimal || hasExponent) {
                return Double.parseDouble(raw);
            }
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return raw;
        }
    }

    private static void skipWhitespaceAndComments() {
        while (index < json.length()) {
            char c = json.charAt(index);
            if (Character.isWhitespace(c)) {
                index++;
                continue;
            }
            if (c == '/') {
                if (index + 1 >= json.length()) break;
                char next = json.charAt(index + 1);
                if (next == '/') {
                    index += 2;
                    while (index < json.length() && json.charAt(index) != '\n') {
                        index++;
                    }
                    continue;
                } else if (next == '*') {
                    index += 2;
                    while (index + 1 < json.length() && !(json.charAt(index) == '*' && json.charAt(index + 1) == '/')) {
                        index++;
                    }
                    index += 2;
                    continue;
                }
            }
            break;
        }
    }

    private static char peek() {
        if (index >= json.length()) ErrorHandler.Exception(new RuntimeException("Unexpected end of JSON"));
        return json.charAt(index);
    }

    private static void expect(char expected) {
        if (peek() != expected) {
            ErrorHandler.Exception(new RuntimeException("Expected '" + expected + "' at position " + index));
        }
        index++;
    }

    private static boolean startsWith(String s) {
        return json.startsWith(s, index);
    }

    public static void registerDeserializer(Class<?> type, Function<Object, ?> deserializer) {
        deserializers.put(type, deserializer);
    }

    private static Color deserializeColor(Object obj) {
        if (!(obj instanceof String)) return null;
        String[] parts = ((String) obj).split(",");
        if (parts.length != 3) return null;
        try {
            int r = Integer.parseInt(parts[0]);
            int g = Integer.parseInt(parts[1]);
            int b = Integer.parseInt(parts[2]);
            return new Color(r, g, b);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Object deserializeEnum(Object obj) {
        if (obj instanceof String s) return s;
        return null;
    }

    private static LocalDate deserializeLocalDate(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return LocalDate.parse((String) obj, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return null;
        }
    }

    private static LocalDateTime deserializeLocalDateTime(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return LocalDateTime.parse((String) obj, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            return null;
        }
    }

    private static ZonedDateTime deserializeZonedDateTime(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return ZonedDateTime.parse((String) obj, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        } catch (Exception e) {
            return null;
        }
    }
}