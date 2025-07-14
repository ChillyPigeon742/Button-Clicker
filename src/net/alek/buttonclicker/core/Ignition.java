package net.alek.buttonclicker.core;

import javafx.embed.swing.JFXPanel;
import net.alek.buttonclicker.event.payload.CloseAppPayload;
import net.alek.buttonclicker.event.type.DeliveryMode;
import net.alek.buttonclicker.event.type.Event;
import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.services.AutoClickerDetectorService;
import net.alek.buttonclicker.services.LoggingService;

public class Ignition {
    static {
        Event.START_APP.subscribe(DeliveryMode.SYNC, ignored -> startApp());
        Event.CLOSE_APP.subscribe(DeliveryMode.SYNC, (CloseAppPayload p) -> closeApp(p.exitCode(), p.exitJVM()));

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                Event.CLOSE_APP.publish(new CloseAppPayload(0, false))));
    }

    private static void startApp() {
        LoggingService.Logger.info("Starting Engine...");
        LoggingService.Logger.info("Button Clicker "+ Spark.getAppData().version());
        LoggingService.Logger.info("Initializing JavaFX...");
        new JFXPanel();

        Event.INIT_GUI.publish(null);

        AudioService.chooseMusic();
    }

    private static void closeApp(int exitCode, boolean exitJVM){
        LoggingService.Logger.warn("Closing app...");
        AutoClickerDetectorService.shutdown();
        Event.shutdown();
        if (exitJVM){
            System.exit(exitCode);
        }
    }
}
