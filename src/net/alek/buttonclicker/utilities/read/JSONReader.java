package net.alek.buttonclicker.utilities.read;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.services.LoggingService;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class JSONReader {

    private final String json;
    private final Object root;

    private final Map<Class<?>, Function<Object, ?>> deserializers = new ConcurrentHashMap<>();
    private int index;

    public JSONReader(String json) {
        this.json = json;
        this.index = 0;

        deserializers.put(Color.class, JSONReader::deserializeColor);
        deserializers.put(LocalDate.class, JSONReader::deserializeLocalDate);
        deserializers.put(LocalDateTime.class, JSONReader::deserializeLocalDateTime);
        deserializers.put(ZonedDateTime.class, JSONReader::deserializeZonedDateTime);
        deserializers.put(BigInteger.class, JSONReader::deserializeBigInteger);
        deserializers.put(BigDecimal.class, JSONReader::deserializeBigDecimal);

        this.root = parseRoot();
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

    private Object parseRoot() {
        skipWhitespaceAndComments();
        if (peek() == '{') return parseObject();
        if (peek() == '[') return parseArray();

        ErrorHandler.Exception(new RuntimeException("Invalid JSON root element"));
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T castWithDeserialization(Object obj, Class<T> targetClass) {
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
            if (targetClass.isEnum() && obj instanceof String) {
                try {
                    @SuppressWarnings("unchecked")
                    T enumValue = (T) Enum.valueOf((Class<Enum>) targetClass.asSubclass(Enum.class), (String) obj);
                    return enumValue;
                } catch (IllegalArgumentException e) {
                    ErrorHandler.Exception(new RuntimeException("Invalid enum value for " + targetClass.getName(), e));
                    return null;
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

    public <T> T get(String key, Class<T> targetClass) {
        if (!(root instanceof Map<?, ?> map)) return null;
        Object rawValue = map.get(key);
        return castWithDeserialization(rawValue, targetClass);
    }

    public String getString(String key) {
        return get(key, String.class);
    }

    public boolean getBoolean(String key) {
        Object val = get(key, Object.class);
        if (val instanceof Boolean) return (Boolean) val;
        return Boolean.parseBoolean(String.valueOf(val));
    }

    public int getInt(String key) {
        Object val = get(key, Object.class);
        if (val instanceof Number) return ((Number) val).intValue();
        return Integer.parseInt(String.valueOf(val));
    }

    public long getLong(String key) {
        Object val = get(key, Object.class);
        if (val instanceof Number) return ((Number) val).longValue();
        return Long.parseLong(String.valueOf(val));
    }

    public byte getByte(String key) {
        Object val = get(key, Object.class);
        if (val instanceof Number) return ((Number) val).byteValue();
        return Byte.parseByte(String.valueOf(val));
    }

    public float getFloat(String key) {
        Object val = get(key, Object.class);
        if (val instanceof Number) return ((Number) val).floatValue();
        return Float.parseFloat(String.valueOf(val));
    }

    public double getDouble(String key) {
        Object val = get(key, Object.class);
        if (val instanceof Number) return ((Number) val).doubleValue();
        return Double.parseDouble(String.valueOf(val));
    }

    @SuppressWarnings("unchecked")
    public List<Object> getList(String key) {
        Object val = get(key, Object.class);
        return val instanceof List<?> ? (List<Object>) val : new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String key) {
        Object val = get(key, Object.class);
        return val instanceof Map<?, ?> ? (Map<String, Object>) val : new HashMap<>();
    }

    public <T> T getPolymorphic(String key, Class<T> baseClass, Map<String, Class<? extends T>> typeMap) {
        if (!(root instanceof Map<?, ?> map)) return null;
        Object val = map.get(key);
        if (!(val instanceof Map<?, ?> objMap)) return null;

        Object typeRaw = objMap.get("type");
        if (!(typeRaw instanceof String type)) return null;

        Class<? extends T> targetClass = typeMap.get(type);
        if (targetClass == null) return null;

        try {
            T instance = targetClass.getDeclaredConstructor().newInstance();
            for (var field : targetClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object rawValue = objMap.get(field.getName());
                if (rawValue != null) {
                    field.set(instance, castWithDeserialization(rawValue, field.getType()));
                }
            }
            return instance;
        } catch (Exception e) {
            ErrorHandler.Exception(new RuntimeException("Failed to instantiate polymorphic type: " + type, e));
            return null;
        }
    }

    public boolean keyExists(String key) {
        if (!(root instanceof Map<?, ?> map)) return false;
        return map.containsKey(key);
    }

    public Set<String> getAllKeys() {
        if (!(root instanceof Map<?, ?> map)) return Collections.emptySet();
        return (Set<String>) map.keySet();
    }

    public Map<String, Object> readAll() {
        if (root instanceof Map<?, ?> map) return (Map<String, Object>) map;
        return new HashMap<>();
    }

    private Map<String, Object> parseObject() {
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

    private List<Object> parseArray() {
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

    private Object parseValue() {
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

    private String parseString() {
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
                                    sb.append(Character.toChars(Character.toCodePoint((char) codePoint, (char) lowCodePoint)));
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

    private Object parseNumberOrLiteral() {
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
            return Long.parseLong(raw);
        } catch (NumberFormatException e) {
            return raw;
        }
    }

    private void skipWhitespaceAndComments() {
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

    private char peek() {
        if (index >= json.length()) ErrorHandler.Exception(new RuntimeException("Unexpected end of JSON"));
        return json.charAt(index);
    }

    private void expect(char expected) {
        if (peek() != expected) {
            ErrorHandler.Exception(new RuntimeException("Expected '" + expected + "' at position " + index));
        }
        index++;
    }

    private boolean startsWith(String s) {
        return json.startsWith(s, index);
    }

    public void registerDeserializer(Class<?> type, Function<Object, ?> deserializer) {
        deserializers.put(type, deserializer);
    }

    private static Color deserializeColor(Object obj) {
        if (!(obj instanceof String)) return null;
        String[] parts = ((String) obj).split(",");
        if (parts.length != 3) return null;
        try {
            int r = Integer.parseInt(parts[0].trim());
            int g = Integer.parseInt(parts[1].trim());
            int b = Integer.parseInt(parts[2].trim());
            return new Color(r, g, b);
        } catch (NumberFormatException e) {
            return null;
        }
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

    private static BigInteger deserializeBigInteger(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return new BigInteger((String) obj);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static BigDecimal deserializeBigDecimal(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return new BigDecimal((String) obj);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}