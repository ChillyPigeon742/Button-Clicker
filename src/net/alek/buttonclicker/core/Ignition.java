package net.alek.buttonclicker.core;

import javafx.embed.swing.JFXPanel;
import net.alek.buttonclicker.core.log.LogType;
import net.alek.buttonclicker.transfer.event.payload.CloseAppPayload;
import net.alek.buttonclicker.transfer.event.payload.LogPayload;
import net.alek.buttonclicker.transfer.event.type.SubscribeMethod;
import net.alek.buttonclicker.transfer.event.type.Event;
import net.alek.buttonclicker.services.AutoClickerDetectorService;

public class Ignition {
    static {
        Event.START_APP.subscribe(SubscribeMethod.SYNC, ignored -> startApp());
        Event.CLOSE_APP.subscribe(SubscribeMethod.SYNC, (CloseAppPayload p) -> closeApp(p.exitCode()));
        Event.GUI_READY.subscribe(SubscribeMethod.SYNC, ignored -> Event.LOAD_GAME.publish(null));
    }

    private static void startApp() {
        Event.LOG.publish(new LogPayload(LogType.INFO, "Starting Engine..."));
        Event.LOG.publish(new LogPayload(LogType.INFO, "Button Clicker "+ Spark.getAppData().version()));
        Event.LOG.publish(new LogPayload(LogType.INFO, "Initializing JavaFX..."));
        new JFXPanel();

        Event.INIT_GUI.publish(null);
    }

    private static void closeApp(int exitCode){
        Event.LOG.publish(new LogPayload(LogType.WARN, "Closing app..."));
        AutoClickerDetectorService.shutdown();
        Event.shutdown();
        System.exit(exitCode);
    }
}
