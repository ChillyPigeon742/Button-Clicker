package net.alek.buttonclicker.core;

import net.alek.buttonclicker.data.model.AppData;
import net.alek.buttonclicker.transfer.event.type.Event;

import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

public class Spark {
    private static AppData appData;

    public static void main(String[] args){
        eagerClassload();

        boolean debug = args.length > 0 && "-debug".equals(args[0]);
        Path appDataPath = Path.of(System.getenv("APPDATA"), "_ButtonClicker");
        String version;

        InputStream in = Spark.class.getResourceAsStream("/buttonclicker/config/Maven.properties");
        if (in == null) {
            System.err.println("Resource not found: Maven.properties");
            version = "null";
        } else {
            try (InputStream autoCloseIn = in) {
                Properties props = new Properties();
                props.load(autoCloseIn);
                version = props.getProperty("app.version");
            } catch (IOException e) {
                System.err.println("Failed to read version tag!");
                System.err.println(e.getMessage() != null ? e.getMessage() : e.toString());
                version = "null";
            }
        }

        appData = new AppData(
                debug,
                appDataPath,
                appDataPath.resolve("Data"),
                appDataPath.resolve("Logs"),
                version
        );
        Event.START_APP.publish(null);
    }

    public static void eagerClassload() {
        final String basePackage = "net.alek.buttonclicker";
        final String basePath = basePackage.replace('.', '/');
        final String moduleName = "ButtonClicker";

        ModuleLayer bootLayer = ModuleLayer.boot();
        Optional<ModuleReference> modRefOpt = bootLayer.configuration()
                .findModule(moduleName)
                .map(ResolvedModule::reference);

        if (modRefOpt.isEmpty()) {
            System.err.println("Module reference not found for: " + moduleName);
            System.exit(moduleName.hashCode());
            return;
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) classLoader = ClassLoader.getSystemClassLoader();

        try (ModuleReader reader = modRefOpt.get().open()) {
            ClassLoader finalClassLoader = classLoader;
            reader.list()
                    .filter(name -> name.endsWith(".class") && name.startsWith(basePath))
                    .map(name -> name.substring(0, name.length() - 6).replace('/', '.'))
                    .parallel()
                    .forEach(className -> {
                        try {
                            Class.forName(className, true, finalClassLoader);
                        } catch (ClassNotFoundException | NoClassDefFoundError e) {
                            System.err.printf("Failed to load class: %s%n", className);
                            System.err.println(e.getCause().toString());
                            System.exit(className.hashCode());
                        }
                    });
        } catch (IOException e) {
            System.err.printf("Failed to open ModuleReader for module: %s%n", moduleName);
            System.err.println(e.getCause().toString());
            System.exit(moduleName.hashCode());
        }
    }

    public static AppData getAppData(){
        return appData;
    }
}