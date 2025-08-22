package net.alek.buttonclicker.core;

import net.alek.buttonclicker.core.log.LogType;
import net.alek.buttonclicker.transfer.event.payload.CloseAppPayload;
import net.alek.buttonclicker.transfer.event.payload.LogPayload;
import net.alek.buttonclicker.transfer.event.type.SubscribeMethod;
import net.alek.buttonclicker.transfer.event.type.Event;
import net.alek.buttonclicker.services.AutoClickerDetectorService;
import net.alek.buttonclicker.transfer.request.type.Request;

public class Ignition {
    static {
        Event.START_APP.subscribe(SubscribeMethod.SYNC, ignored -> startApp());
        Event.CLOSE_APP.subscribe(SubscribeMethod.SYNC, (CloseAppPayload p) -> closeApp(p.exitCode()));
        Event.GUI_READY.subscribe(SubscribeMethod.SYNC, ignored -> Event.LOAD_GAME.publish());
    }

    private static void startApp() {
        Event.LOG.publish(new LogPayload(LogType.INFO, "Starting Engine..."));
        Event.LOG.publish(new LogPayload(LogType.INFO, "Button Clicker "+ Spark.getAppData().version()));

        Event.INIT_GUI.publish();
    }

    private static void closeApp(int exitCode){
        Event.LOG.publish(new LogPayload(LogType.WARN, "Closing app..."));
        AutoClickerDetectorService.shutdown();
        Event.shutdown();
        Request.shutdown();
        System.exit(exitCode);
    }
}
