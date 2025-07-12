package net.alek.buttonclicker.write.json;

public interface CustomSerializer {
    String serialize(Object obj, JSONWriter writer);
}