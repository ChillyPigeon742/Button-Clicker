package net.alek.buttonclicker.core;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatDarculaLaf;

import javafx.embed.swing.JFXPanel;

import net.alek.buttonclicker.data.model.AppData;
import net.alek.buttonclicker.command.CommandDefinitions;
import net.alek.buttonclicker.event.type.Event;
import net.alek.buttonclicker.services.AutoClickerDetectorService;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.read.ReadUtility;
import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.ui.RenderService;

import javax.swing.*;
import java.nio.file.Paths;

public class Main {

    public static AppData appData = new AppData(
            Paths.get(System.getenv("APPDATA"), "_ButtonClicker"),
            Paths.get(System.getenv("APPDATA"), "_ButtonClicker/Data"),
            Paths.get(System.getenv("APPDATA"), "_ButtonClicker/Logs"),
            new CommandDefinitions(),
            "0.8.0_INDEV"
    );

    public static void main(String[] args){
        ErrorHandler.setupCrashHandler();
        LoggingService.setupLogger();

        FlatLaf.registerCustomDefaultsSource("assets.buttonclicker.config");
        FlatDarculaLaf.setup();

        RenderService.frame.getRootPane().putClientProperty("JRootPane.titleBarShowTitle", false);
        RenderService.frame.getRootPane().putClientProperty("JRootPane.titleBarShowIcon", false);

        RenderService.backwardButton.putClientProperty("JButton.buttonType", "roundRect");
        RenderService.forwardButton.putClientProperty("JButton.buttonType", "roundRect");

        LoggingService.Logger.info("Starting Engine...");
        LoggingService.Logger.info("Button Clicker "+appData.version());
        LoggingService.Logger.info("Initializing Engine Pipelines...");

        new JFXPanel();

        ReadUtility.loadGame();
        AudioService.chooseMusic();

        SwingUtilities.invokeLater(() -> {
            new RenderService();

            WriteUtility.startAutosaveTimer();
            new AutoClickerDetectorService();
        });
    }

    public static void closeApp(int exitCode){
        LoggingService.Logger.warn("Closing app...");
        AutoClickerDetectorService.shutdown();
        Event.shutdown();
        System.exit(exitCode);
    }
}