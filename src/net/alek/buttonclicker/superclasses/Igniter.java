package net.alek.buttonclicker.superclasses;

import net.alek.buttonclicker.services.AutoClickerDetectorService;
import net.alek.buttonclicker.services.LoggingService;

public class Igniter {
    public static void closeApp(int exitCode){
        LoggingService.Logger.warn("Closing app...");
        AutoClickerDetectorService.shutdown();
        System.exit(exitCode);
    }
}
