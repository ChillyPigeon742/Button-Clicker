package net.alek.buttonclicker.engine;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatDarculaLaf;

import javafx.embed.swing.JFXPanel;

import net.alek.buttonclicker.data.CommandDefinitions;
import net.alek.buttonclicker.services.AutoClickerDetectorService;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.utilities.read.ReadUtility;
import net.alek.buttonclicker.utilities.write.WriteUtility;
import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.services.RenderService;

import javax.swing.*;

public class Main {

    public static EventBus eventBus = new EventBus();
    public static CommandDefinitions commandDefinitions = new CommandDefinitions();
    public static final String VERSION = "0.7.0_INDEV";

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
        LoggingService.Logger.info("Button Clicker "+VERSION);
        LoggingService.Logger.info("Initializing Engine Pipelines...");

        new JFXPanel();

        ReadUtility.loadGame();
        new AudioService();
        SwingUtilities.invokeLater(() -> {
            new RenderService();

            WriteUtility.startAutosaveTimer();
            new AutoClickerDetectorService();
        });
    }

    public static void closeApp(int exitCode){
        LoggingService.Logger.warn("Closing app...");
        AutoClickerDetectorService.shutdown();
        Main.eventBus.shutdown();
        System.exit(exitCode);
    }
}