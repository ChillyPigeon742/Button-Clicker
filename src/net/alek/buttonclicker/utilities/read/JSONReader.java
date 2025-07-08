package net.alek.buttonclicker.utilities.read;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.services.LoggingService;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
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
    private final Set<Object> activeObjects = Collections.newSetFromMap(new IdentityHashMap<>());
    private Class<?> currentTargetClass;

    public JSONReader(String json) {
        this.json = json;
        this.index = 0;

        registerDefaultDeserializers();
        this.root = parseRoot();
    }

    private void registerDefaultDeserializers() {
        deserializers.put(Enum.class, this::deserializeEnum);
        deserializers.put(Color.class, this::deserializeColor);
        deserializers.put(LocalDate.class, this::deserializeLocalDate);
        deserializers.put(LocalDateTime.class, this::deserializeLocalDateTime);
        deserializers.put(ZonedDateTime.class, this::deserializeZonedDateTime);
        deserializers.put(BigInteger.class, this::deserializeBigInteger);
        deserializers.put(BigDecimal.class, this::deserializeBigDecimal);
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
        if (peek() == '{') return parseObject(new HashSet<>());
        if (peek() == '[') return parseArray(new HashSet<>());

        ErrorHandler.Exception(new RuntimeException("Invalid JSON root element"));
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T castWithDeserialization(Object obj, Class<T> targetClass) {
        if (obj == null) return null;

        if (targetClass != null) {
            Class<?> previousTargetClass = this.currentTargetClass;
            this.currentTargetClass = targetClass;

            try {
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
            } finally {
                this.currentTargetClass = previousTargetClass;
            }
        }

        try {
            return (T) obj;
        } catch (ClassCastException e) {
            ErrorHandler.Exception(new RuntimeException("Failed to cast object to " +
                    (targetClass == null ? "unknown" : targetClass.getName()), e));
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

    public <T> List<T> getList(String key, Class<T> elementType) {
        Object val = get(key, Object.class);
        if (!(val instanceof List<?>)) return new ArrayList<>();

        List<T> result = new ArrayList<>();
        for (Object item : (List<?>) val) {
            result.add(castWithDeserialization(item, elementType));
        }
        return result;
    }

    public <T> Map<String, T> getMap(String key, Class<T> valueType) {
        Object val = get(key, Object.class);
        if (!(val instanceof Map<?, ?>)) return new HashMap<>();

        Map<String, T> result = new HashMap<>();
        for (Map.Entry<?, ?> entry : ((Map<?, ?>) val).entrySet()) {
            result.put(entry.getKey().toString(), castWithDeserialization(entry.getValue(), valueType));
        }
        return result;
    }

    private Object deserializePolymorphicObject(Map<String, Object> objMap, Set<Object> seen) {
        if (seen.contains(objMap)) {
            return "[cyclic_reference]";
        }
        seen.add(objMap);

        Object typeRaw = objMap.get("type");
        if (!(typeRaw instanceof String typeName)) return objMap;

        Class<?> targetClass = findClass(typeName);
        if (targetClass == null) return objMap;

        try {
            Object instance = targetClass.getDeclaredConstructor().newInstance();

            for (var field : targetClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object rawValue = objMap.get(field.getName());
                if (rawValue != null) {
                    if (rawValue instanceof Map<?, ?> nestedMap) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> nestedStringMap = (Map<String, Object>) nestedMap;
                        rawValue = deserializePolymorphicObject(nestedStringMap, seen);
                    } else if (rawValue instanceof List<?>) {
                        rawValue = deserializePolymorphicInList((List<Object>) rawValue, seen);
                    }
                    field.set(instance, castWithDeserialization(rawValue, field.getType()));
                }
            }

            return instance;
        } catch (Exception e) {
            ErrorHandler.Exception(new RuntimeException("Failed to instantiate polymorphic type: " + typeName, e));
            return objMap;
        } finally {
            seen.remove(objMap);
        }
    }

    private Class<?> findClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            try {
                String callerPackage = getClass().getPackage().getName();
                return Class.forName(callerPackage + "." + className);
            } catch (ClassNotFoundException e2) {
                return searchAllLoadedClasses(className);
            }
        }
    }

    private Class<?> searchAllLoadedClasses(String className) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader != null) {
                Package[] packages = Package.getPackages();
                for (Package pkg : packages) {
                    try {
                        String fullName = pkg.getName() + "." + className;
                        return Class.forName(fullName);
                    } catch (ClassNotFoundException ignored) {}
                }
            }
        } catch (Exception e) {
            ErrorHandler.Exception(new RuntimeException("Error while searching for class: " + className, e));
        }
        return null;
    }

    private List<Object> deserializePolymorphicInList(List<Object> list, Set<Object> seen) {
        List<Object> result = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof Map<?, ?> mapItem) {
                @SuppressWarnings("unchecked")
                Map<String, Object> stringMapItem = (Map<String, Object>) mapItem;
                result.add(deserializePolymorphicObject(stringMapItem, seen));
            } else if (item instanceof List<?>) {
                result.add(deserializePolymorphicInList((List<Object>) item, seen));
            } else {
                result.add(item);
            }
        }
        return result;
    }

    private Map<String, Object> parseObject(Set<Object> seen) {
        Map<String, Object> map = new HashMap<>();
        expect('{');
        skipWhitespaceAndComments();

        if (activeObjects.contains(map)) {
            throw new RuntimeException("Cyclic reference detected");
        }
        activeObjects.add(map);

        try {
            while (true) {
                skipWhitespaceAndComments();
                if (peek() == '}') {
                    index++;
                    break;
                }

                String key = parseString();
                if (key == null) {
                    throw new RuntimeException("Null keys not allowed in JSON");
                }

                skipWhitespaceAndComments();
                expect(':');
                skipWhitespaceAndComments();
                Object value = parseValue(seen);

                if (value instanceof Map<?, ?> valueMap) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> stringMap = (Map<String, Object>) valueMap;
                    value = deserializePolymorphicObject(stringMap, seen);
                } else if (value instanceof List<?>) {
                    value = deserializePolymorphicInList((List<Object>) value, seen);
                }

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
        } finally {
            activeObjects.remove(map);
        }

        return map;
    }

    private List<Object> parseArray(Set<Object> seen) {
        List<Object> list = new ArrayList<>();
        expect('[');
        skipWhitespaceAndComments();

        if (activeObjects.contains(list)) {
            throw new RuntimeException("Cyclic reference detected");
        }
        activeObjects.add(list);

        try {
            while (true) {
                skipWhitespaceAndComments();
                if (peek() == ']') {
                    index++;
                    break;
                }

                Object value = parseValue(seen);

                if (value instanceof Map<?, ?> valueMap) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> stringMap = (Map<String, Object>) valueMap;
                    value = deserializePolymorphicObject(stringMap, seen);
                } else if (value instanceof List<?>) {
                    value = deserializePolymorphicInList((List<Object>) value, seen);
                }

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
        } finally {
            activeObjects.remove(list);
        }

        return list;
    }

    private Object parseValue(Set<Object> seen) {
        skipWhitespaceAndComments();
        char c = peek();

        if (c == '"') return parseString();
        if (c == '{') return parseObject(seen);
        if (c == '[') return parseArray(seen);
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
                        if (index + 4 > json.length())
                            ErrorHandler.Exception(new RuntimeException("Incomplete unicode escape"));
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

    private Object deserializeEnum(Object obj) {
        if (!(obj instanceof String)) {
            return null;
        }

        if (currentTargetClass != null && currentTargetClass.isEnum()) {
            try {
                Method valueOf = currentTargetClass.getMethod("valueOf", String.class);
                return valueOf.invoke(null, obj);
            } catch (Exception e) {
                ErrorHandler.Exception(new RuntimeException(
                        "Failed to deserialize enum value '" + obj + "' for " + currentTargetClass.getName(), e));
                return null;
            }
        }

        return null;
    }

    private Color deserializeColor(Object obj) {
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

    private LocalDate deserializeLocalDate(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return LocalDate.parse((String) obj, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime deserializeLocalDateTime(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return LocalDateTime.parse((String) obj, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            return null;
        }
    }

    private ZonedDateTime deserializeZonedDateTime(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return ZonedDateTime.parse((String) obj, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        } catch (Exception e) {
            return null;
        }
    }

    private BigInteger deserializeBigInteger(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return new BigInteger((String) obj);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal deserializeBigDecimal(Object obj) {
        if (!(obj instanceof String)) return null;
        try {
            return new BigDecimal((String) obj);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}