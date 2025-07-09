package net.alek.buttonclicker.data.json;

import net.alek.buttonclicker.utilities.write.json.JSONWriter;

public interface CustomSerializer {
    String serialize(Object obj, JSONWriter writer);
}