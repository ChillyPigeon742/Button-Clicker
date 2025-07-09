package net.alek.buttonclicker.utilities.write.json;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.data.json.CustomSerializer;

import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class JSONSerializer {

    private final Map<Class<?>, BiFunction<Object, JSONWriter, String>> typeAdapters = new ConcurrentHashMap<>();
    private final Map<Class<?>, CustomSerializer> customSerializers = new ConcurrentHashMap<>();

    private boolean prettyPrint = true;
    private String indentUnit = "\t";
    private JSONWriter writer;

    public JSONSerializer(JSONWriter writer) {
        this.writer = writer;
        registerDefaultTypeAdapters();
    }

    private void registerDefaultTypeAdapters() {
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

    public void registerCustomSerializer(Class<?> clazz, CustomSerializer serializer) {
        customSerializers.put(clazz, serializer);
    }

    public void registerTypeAdapter(Class<?> type, BiFunction<Object, JSONWriter, String> adapter) {
        typeAdapters.put(type, adapter);
    }

    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public void setIndentUnit(String indentUnit) {
        this.indentUnit = indentUnit;
    }

    public String serialize(Object obj) {
        return serialize(obj, 0, new HashSet<>());
    }

    private String serialize(Object obj, int indentLevel, Set<Object> seen) {
        if (obj == null) return "null";

        if (seen.contains(obj)) return "\"[cyclic_reference]\"";

        boolean addToSeen = obj instanceof Map || obj instanceof Collection;
        if (addToSeen) seen.add(obj);

        StringBuilder sb = new StringBuilder();

        for (var entry : customSerializers.entrySet()) {
            if (entry.getKey().isInstance(obj)) {
                String result = entry.getValue().serialize(obj, writer);
                if (addToSeen) seen.remove(obj);
                return result;
            }
        }

        for (var entry : typeAdapters.entrySet()) {
            if (entry.getKey().isInstance(obj)) {
                String result = entry.getValue().apply(obj, writer);
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