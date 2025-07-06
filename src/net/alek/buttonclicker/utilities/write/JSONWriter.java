package net.alek.buttonclicker.utilities.write;

import net.alek.buttonclicker.data.WriterContext;
import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.utilities.read.JSONReader;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class JSONWriter {
    private static final Map<Class<?>, BiFunction<Object, WriterContext, String>> typeAdapters = new ConcurrentHashMap<>();

    static {
        registerTypeAdapter(Enum.class, (obj, ctx) -> "\"" + ((Enum<?>) obj).name() + "\"");

        registerTypeAdapter(LocalDate.class, (obj, ctx) -> "\"" + ((LocalDate) obj).format(DateTimeFormatter.ISO_LOCAL_DATE) + "\"");
        registerTypeAdapter(LocalDateTime.class, (obj, ctx) -> "\"" + ((LocalDateTime) obj).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\"");
        registerTypeAdapter(ZonedDateTime.class, (obj, ctx) -> "\"" + ((ZonedDateTime) obj).format(DateTimeFormatter.ISO_ZONED_DATE_TIME) + "\"");

        registerTypeAdapter(Color.class, (obj, ctx) -> {
            Color c = (Color) obj;
            return "\"" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + "\"";
        });
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
                data = JSONReader.parse(json.toString());
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

    public static void registerTypeAdapter(Class<?> type, BiFunction<Object, WriterContext, String> adapter) {
        typeAdapters.put(type, adapter);
    }

    public static String toJsonString(Object obj, boolean pretty) {
        return serialize(obj, pretty, 0);
    }

    private static String serialize(Object obj, boolean pretty, int indentLevel) {
        String indent = pretty ? "\t".repeat(indentLevel) : "";
        String nextIndent = pretty ? "\t".repeat(indentLevel + 1) : "";

        if (obj == null) return "null";

        for (var entry : typeAdapters.entrySet()) {
            if (entry.getKey().isAssignableFrom(obj.getClass())) {
                try {
                    return entry.getValue().apply(obj, new WriterContext());
                } catch (Exception e) {
                    ErrorHandler.Exception(new RuntimeException("Failed to serialize custom type: " + obj.getClass().getName(), e));
                }
            }
        }

        if (obj instanceof Map<?, ?> map) {
            StringBuilder sb = new StringBuilder();
            sb.append("{").append(pretty ? "\n" : "");
            List<String> entries = new ArrayList<>();
            for (var entry : map.entrySet()) {
                String serializedEntry = nextIndent +
                        "\"" + escapeString(entry.getKey().toString()) + "\":" + (pretty ? " " : "") +
                        serialize(entry.getValue(), pretty, indentLevel + 1);
                entries.add(serializedEntry);
            }
            sb.append(String.join(pretty ? ",\n" : ",", entries));
            if (pretty) sb.append("\n").append(indent);
            sb.append("}");
            return sb.toString();

        } else if (obj instanceof List<?> list) {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(pretty ? "\n" : "");
            List<String> items = new ArrayList<>();
            for (Object item : list) {
                items.add(nextIndent + serialize(item, pretty, indentLevel + 1));
            }
            sb.append(String.join(pretty ? ",\n" : ",", items));
            if (pretty) sb.append("\n").append(indent);
            sb.append("]");
            return sb.toString();

        } else if (obj instanceof String str) {
            return "\"" + escapeString(str) + "\"";

        } else if (obj instanceof Boolean || obj instanceof Number) {
            return obj.toString();

        } else {
            return "\"" + escapeString(obj.toString()) + "\"";
        }
    }

    private static String escapeString(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20 || c > 0x7E) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }
}