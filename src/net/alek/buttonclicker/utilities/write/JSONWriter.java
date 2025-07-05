package net.alek.buttonclicker.utilities.write;

import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.utilities.read.JSONReader;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONWriter {
    public static void writeJson(String filePath, String key, Object value) {
        Map<String, Object> data = new HashMap<>();

        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                data = JSONReader.parse(json.toString());
            } catch (IOException e) {
                LoggingService.Logger.error("Could not write to the JSON file!");
            }
        }

        data.put(key, value);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(serialize(data, true, 0));
        } catch (IOException e) {
            LoggingService.Logger.error("Could not serialize the data to the JSON file!");
        }
    }

    private static String serialize(Object obj, boolean pretty, int indentLevel) {
        String indent = pretty ? "\t".repeat(indentLevel) : "";
        String nextIndent = pretty ? "\t".repeat(indentLevel + 1) : "";

        if (obj instanceof Map<?, ?> map) {
            StringBuilder sb = new StringBuilder();
            sb.append("{").append(pretty ? "\n" : "");
            for (var entry : map.entrySet()) {
                sb.append(nextIndent).append("\"").append(entry.getKey()).append("\":");
                sb.append(pretty ? " " : "").append(serialize(entry.getValue(), pretty, indentLevel + 1)).append(",");
                if (pretty) sb.append("\n");
            }
            if (!map.isEmpty()) {
                sb.setLength(sb.length() - (pretty ? 2 : 1));
                if (pretty) sb.append("\n").append(indent);
            }
            sb.append("}");
            return sb.toString();
        } else if (obj instanceof List<?> list) {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(pretty ? "\n" : "");
            for (Object item : list) {
                sb.append(nextIndent).append(serialize(item, pretty, indentLevel + 1)).append(",");
                if (pretty) sb.append("\n");
            }
            if (!list.isEmpty()) {
                sb.setLength(sb.length() - (pretty ? 2 : 1));
                if (pretty) sb.append("\n").append(indent);
            }
            sb.append("]");
            return sb.toString();
        } else if (obj instanceof String str) {
            return "\"" + str.replace("\"", "\\\"") + "\"";
        } else if (obj instanceof Boolean || obj instanceof Number) {
            return obj.toString();
        } else {
            return "\"" + obj.toString().replace("\"", "\\\"") + "\"";
        }
    }
}