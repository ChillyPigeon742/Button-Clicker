package net.alek.buttonclicker.core;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import net.alek.buttonclicker.data.model.AppData;
import net.alek.buttonclicker.command.CommandDefinitions;
import net.alek.buttonclicker.event.type.Event;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Spark {
    private static AppData appData;

    public static void main(String[] args){
        eagerClassload();

        boolean debug = args.length > 0 && "-debug".equals(args[0]);
        Path appDataPath = Paths.get(System.getenv("APPDATA"), "_ButtonClicker");

        appData = new AppData(
                debug,
                appDataPath,
                appDataPath.resolve("Data"),
                appDataPath.resolve("Logs"),
                new CommandDefinitions(),
                "0.8.0_INDEV"
        );
        Event.START_APP.publish(null);
    }

    private static void eagerClassload() {
        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages("net.alek.buttonclicker")
                .enableClassInfo()
                .scan()) {

            scanResult.getAllClasses().forEach(classInfo -> {
                try {
                    Class.forName(classInfo.getName());
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    System.exit(-1);
                }
            });
        }
    }

    public static AppData getAppData(){
        return appData;
    }
}