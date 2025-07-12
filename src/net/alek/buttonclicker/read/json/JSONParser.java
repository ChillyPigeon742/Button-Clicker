package net.alek.buttonclicker.read.json;

import net.alek.buttonclicker.core.ErrorHandler;

import java.util.*;

public class JSONParser {
    private final Object root;
    private final String json;
    private int index;
    private final Set<Object> activeObjects = Collections.newSetFromMap(new IdentityHashMap<>());
    private final JSONDeserializer deserializer;

    public JSONParser(String json, JSONDeserializer deserializer) {
        this.json = json;
        this.index = 0;
        this.deserializer = deserializer;
        this.root = parseRoot();
    }

    public Object getRoot() {
        return root;
    }

    private Object parseRoot() {
        skipWhitespaceAndComments();
        if (peek() == '{') return parseObject(new HashSet<>());
        if (peek() == '[') return parseArray(new HashSet<>());

        ErrorHandler.Exception(new RuntimeException("Invalid JSON root element"));
        return null;
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
                    value = deserializer.deserializePolymorphicObject(stringMap, seen);
                } else if (value instanceof List<?>) {
                    value = deserializer.deserializePolymorphicInList((List<Object>) value, seen);
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
                    value = deserializer.deserializePolymorphicObject(stringMap, seen);
                } else if (value instanceof List<?>) {
                    value = deserializer.deserializePolymorphicInList((List<Object>) value, seen);
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
}
