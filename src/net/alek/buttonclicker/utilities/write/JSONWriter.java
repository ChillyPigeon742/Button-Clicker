package net.alek.buttonclicker.utilities.write;

import net.alek.buttonclicker.data.WriterContext;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class JSONWriter {

    public interface CustomSerializer {
        String serialize(Object obj, WriterContext ctx, boolean pretty, int indentLevel);
    }

    private static final Map<Class<?>, BiFunction<Object, WriterContext, String>> typeAdapters = new ConcurrentHashMap<>();
    private static final Map<Class<?>, CustomSerializer> customSerializers = new ConcurrentHashMap<>();
    private static String indentUnit = "\t";

    static {
        registerTypeAdapter(Enum.class, (obj, ctx) -> "\"" + ((Enum<?>) obj).name() + "\"");

        registerTypeAdapter(LocalDate.class, (obj, ctx) -> "\"" + ((LocalDate) obj).format(DateTimeFormatter.ISO_LOCAL_DATE) + "\"");
        registerTypeAdapter(LocalDateTime.class, (obj, ctx) -> "\"" + ((LocalDateTime) obj).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\"");
        registerTypeAdapter(ZonedDateTime.class, (obj, ctx) -> "\"" + ((ZonedDateTime) obj).format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + "\"");

        registerTypeAdapter(Color.class, (obj, ctx) -> {
            Color c = (Color) obj;
            return "\"" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + "\"";
        });

        registerTypeAdapter(BigInteger.class, (obj, ctx) -> "\"" + obj.toString() + "\"");
        registerTypeAdapter(BigDecimal.class, (obj, ctx) -> "\"" + obj.toString() + "\"");
    }

    public static void registerCustomSerializer(Class<?> clazz, CustomSerializer serializer) {
        customSerializers.put(clazz, serializer);
    }

    public static void registerTypeAdapter(Class<?> type, BiFunction<Object, WriterContext, String> adapter) {
        typeAdapters.put(type, adapter);
    }

    public static void setIndentUnit(String unit) {
        indentUnit = unit;
    }

    public static synchronized void writeJson(String filePath, String key, Object value) {
        Map<String, Object> data = new HashMap<>();
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                JSONReader readerInstance = new JSONReader(json.toString());
                data = readerInstance.readAll();

            } catch (IOException e) {
                LoggingService.Logger.error("Could not write to the JSON file! " + e.getMessage());
                ErrorHandler.Exception(e);
            }
        }

        data.put(key, value);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            writer.write(serialize(data, true, 0));
        } catch (IOException e) {
            LoggingService.Logger.error("Could not serialize the data to the JSON file! " + e.getMessage());
            ErrorHandler.Exception(e);
        }
    }

    public static String toJsonString(Object obj, boolean pretty) {
        return serialize(obj, pretty, 0);
    }

    private static String serialize(Object obj, boolean pretty, int indentLevel) {
        return serialize(obj, pretty, indentLevel, new HashSet<>());
    }

    private static String serialize(Object obj, boolean pretty, int indentLevel, Set<Object> seen) {
        if (obj == null) {
            return "null";
        }

        if (seen.contains(obj)) {
            return "\"[cyclic_reference]\"";
        }

        boolean addToSeen = obj instanceof Map || obj instanceof Collection;
        if (addToSeen) seen.add(obj);

        StringBuilder sb = new StringBuilder();
        WriterContext ctx = new WriterContext();

        for (var entry : customSerializers.entrySet()) {
            if (entry.getKey().isInstance(obj)) {
                String result = entry.getValue().serialize(obj, ctx, pretty, indentLevel);
                if (addToSeen) seen.remove(obj);
                return result;
            }
        }

        for (var entry : typeAdapters.entrySet()) {
            if (entry.getKey().isInstance(obj)) {
                String result = entry.getValue().apply(obj, ctx);
                if (addToSeen) seen.remove(obj);
                return result;
            }
        }

        if (obj instanceof Map<?, ?> map) {
            sb.append("{");
            if (pretty) sb.append('\n');
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
                    if (pretty) sb.append('\n');
                }

                if (pretty) sb.append(indent(indentLevel + 1));
                sb.append("\"").append(escapeString(key.toString())).append("\": ");

                if (value != null && !isPrimitiveOrWrapper(value)) {
                    sb.append("{");
                    if (pretty) sb.append('\n');

                    if (pretty) sb.append(indent(indentLevel + 2));
                    sb.append("\"type\": \"").append(escapeString(value.getClass().getSimpleName())).append("\"");

                    for (var field : value.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        try {
                            Object fieldVal = field.get(value);
                            if (fieldVal == null) continue;
                            sb.append(",");
                            if (pretty) sb.append('\n');
                            if (pretty) sb.append(indent(indentLevel + 2));
                            sb.append("\"").append(escapeString(field.getName())).append("\": ");
                            sb.append(serialize(fieldVal, pretty, indentLevel + 2, seen));
                        } catch (IllegalAccessException e) {
                            ErrorHandler.Exception(e);
                        }
                    }

                    if (pretty) {
                        sb.append('\n').append(indent(indentLevel + 1));
                    }
                    sb.append("}");
                } else {
                    sb.append(serialize(value, pretty, indentLevel + 1, seen));
                }
            }

            if (pretty) sb.append('\n').append(indent(indentLevel));
            sb.append("}");
            if (addToSeen) seen.remove(obj);
            return sb.toString();
        }

        if (obj instanceof Collection<?> coll) {
            sb.append("[");
            if (pretty) sb.append('\n');
            int count = 0;
            for (Object item : coll) {
                if (count++ > 0) {
                    sb.append(",");
                    if (pretty) sb.append('\n');
                }
                if (pretty) sb.append(indent(indentLevel + 1));
                sb.append(serialize(item, pretty, indentLevel + 1, seen));
            }
            if (pretty) sb.append('\n').append(indent(indentLevel));
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

        sb.append("{");
        if (pretty) sb.append('\n');
        if (pretty) sb.append(indent(indentLevel + 1));
        sb.append("\"type\": \"").append(escapeString(obj.getClass().getSimpleName())).append("\"");

        for (var field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldVal = field.get(obj);
                if (fieldVal == null) continue;
                sb.append(",");
                if (pretty) sb.append('\n');
                if (pretty) sb.append(indent(indentLevel + 1));
                sb.append("\"").append(escapeString(field.getName())).append("\": ");
                sb.append(serialize(fieldVal, pretty, indentLevel + 1, seen));
            } catch (IllegalAccessException e) {
                ErrorHandler.Exception(e);
            }
        }

        if (pretty) sb.append('\n').append(indent(indentLevel));
        sb.append("}");
        if (addToSeen) seen.remove(obj);
        return sb.toString();
    }

    private static boolean isPrimitiveOrWrapper(Object obj) {
        return obj instanceof String ||
                obj instanceof Number ||
                obj instanceof Boolean;
    }

    private static String indent(int level) {
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