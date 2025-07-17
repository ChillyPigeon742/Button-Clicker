package net.alek.buttonclicker.core;

import net.alek.buttonclicker.data.model.AppData;
import net.alek.buttonclicker.transfer.event.type.Event;

import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class Spark {
    private static AppData appData;

    public static void main(String[] args){
        boolean debug = args.length > 0 && "-debug".equals(args[0]);
        Path appDataPath = Path.of(System.getenv("APPDATA"), "_ButtonClicker");
        String version = "null";

        String resourcePath = "/assets/buttonclicker/config/Maven.properties";
        try (InputStream in = Spark.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                System.err.println("Resource not found: " + resourcePath);
            } else {
                Properties props = new Properties();
                props.load(in);
                version = props.getProperty("app.version", "null");
            }
        } catch (IOException e) {
            System.err.println("Failed to read version tag!");
            System.err.println(e.getMessage() != null ? e.getMessage() : e.toString());
        }

        appData = new AppData(
                debug,
                version,
                appDataPath,
                appDataPath.resolve("Data"),
                appDataPath.resolve("Logs")
        );
        setupAppDataFolder();
        eagerClassload();

        Event.START_APP.publish(null);
    }

    private static void setupAppDataFolder() {
        Path appDataDir = appData.APPDATA_PATH();
        Path dataDir = appData.DATA_PATH();
        Path logDir = appData.LOGS_PATH();

        if (Files.notExists(appDataDir)) {
            try {
                Files.createDirectories(appDataDir);
            } catch (IOException e) {
                System.err.println("Failed to create app data directory: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }

        if (Files.notExists(dataDir)) {
            try {
                Files.createDirectories(dataDir);
            } catch (IOException e) {
                System.err.println("Failed to create data directory: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }

        if (Files.notExists(logDir)) {
            try {
                Files.createDirectories(logDir);
            } catch (IOException e) {
                System.err.println("Failed to create logs directory: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }

        String defaultsPath = "/assets/buttonclicker/config/default/";

        Path targetSettings = dataDir.resolve("settings.json");
        if (Files.notExists(targetSettings)) {
            try (var inSettings = Spark.class.getResourceAsStream(defaultsPath + "settings.json")) {
                Objects.requireNonNull(inSettings, "Default settings.json resource not found");
                Files.copy(inSettings, targetSettings, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException | NullPointerException e) {
                System.err.println("Failed to copy default settings.json: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }

        Path targetSaves = dataDir.resolve("saves.bcs");
        if (Files.notExists(targetSaves)) {
            try (var inSaves = Spark.class.getResourceAsStream(defaultsPath + "saves.bcs")) {
                Objects.requireNonNull(inSaves, "Default saves.bcs resource not found");
                Files.copy(inSaves, targetSaves, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException | NullPointerException e) {
                System.err.println("Failed to copy default saves.bcs: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private static void eagerClassload() {
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