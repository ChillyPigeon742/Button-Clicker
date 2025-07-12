package net.alek.buttonclicker.read.json;

import net.alek.buttonclicker.core.ErrorHandler;

import java.awt.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class JSONDeserializer {
    public final Map<Class<?>, Function<Object, ?>> deserializers = new ConcurrentHashMap<>();
    public Class<?> currentTargetClass;

    public JSONDeserializer() {
        registerDefaultDeserializers();
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

    @SuppressWarnings("unchecked")
    public <T> T castWithDeserialization(Object obj, Class<T> targetClass) {
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

    public Object deserializePolymorphicObject(Map<String, Object> objMap, Set<Object> seen) {
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
                    } else if (rawValue instanceof java.util.List<?>) {
                        rawValue = deserializePolymorphicInList((java.util.List<Object>) rawValue, seen);
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

    public java.util.List<Object> deserializePolymorphicInList(java.util.List<Object> list, Set<Object> seen) {
        java.util.List<Object> result = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof Map<?, ?> mapItem) {
                @SuppressWarnings("unchecked")
                Map<String, Object> stringMapItem = (Map<String, Object>) mapItem;
                result.add(deserializePolymorphicObject(stringMapItem, seen));
            } else if (item instanceof java.util.List<?>) {
                result.add(deserializePolymorphicInList((List<Object>) item, seen));
            } else {
                result.add(item);
            }
        }
        return result;
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
