package net.alek.buttonclicker.core;

import com.formdev.flatlaf.FlatLaf;

import net.alek.buttonclicker.read.assets.ImageLoader;
import net.alek.buttonclicker.core.log.Logger;
import net.alek.buttonclicker.transfer.event.type.Event;
import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.ui.RenderService;
import net.alek.buttonclicker.ui.MenuManager;
import net.alek.buttonclicker.util.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ErrorHandler {
    static {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> Throwable(throwable));
    }

    private static String getCallerInfo() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();
            if (!className.equals(ErrorHandler.class.getName())) {
                String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
                return simpleClassName + ":" + element.getLineNumber();
            }
        }
        return "UnknownCaller";
    }

    private static void handleException(String name, String cause, String message, String packageName, String stackTrace){
        String caller = getCallerInfo();

        Logger.Log.error(name+"/"+caller+"/The Program has Suffered an "+name+"!");

        Logger.Log.error("Locking Engine...");
        MenuManager.closeMenu("Spark");
        MenuManager.closeMenu("Save Manager");
        MenuManager.closeMenu("Choose A Side");
        MenuManager.closeMenu("Enter A Save Name");
        MenuManager.closeMenu("Save Info");
        MenuManager.closeMenu("Settings");
        MenuManager.closeMenu("Wiki");
        MenuManager.closeMenu("Credits");
        MenuManager.closeMenu("Loading");
        MenuManager.closeMenu("Game");
        MenuManager.closeMenu("Pause");
        MenuManager.closeMenu("Shop");
        MenuManager.closeMenu("Debug");

        RenderService.titleText.setVisible(false);
        RenderService.titleImage.setVisible(false);
        RenderService.backToButton.setVisible(false);
        RenderService.forwardButton.setVisible(false);
        RenderService.backwardButton.setVisible(false);

        RenderService.frame.getContentPane().setBackground(Color.WHITE);

        Logger.Log.error("Unloading Resources...");
        GUIUtils.setWindowIcon(ImageLoader.getImages().missingIcon().getImage());
        Event.UNLOAD_GAME.publish(null);

        Logger.Log.error("Unloading Themes...");
        FlatLaf.unregisterCustomDefaultsSource("assets.buttonclicker.config");
        GUIUtils.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        Logger.Log.error("Stopping Services...");
        AudioService.Music.stopMusic();

        RenderService.crashText.setVisible(true);
        RenderService.crashText.setText("The game appears to have encountered a\nfatal error!\n\nTo prevent further error the game has \nlocked down the engine\n\nBelow are a few troubleshooting options");

        RenderService.showConsoleButton.setVisible(true);
        RenderService.showConsoleButton.setBounds(250, 335, 100, 20);

        RenderService.showConsoleButton.setFont(new Font("Sans Serif", Font.BOLD, 9));
        RenderService.showConsoleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        RenderService.showConsoleButton.setFocusable(false);
    }

    public static void Exception(Exception e) {
        String name = e.getClass().getSimpleName();
        String packageName = e.getClass().getName();
        String cause = (e.getCause() != null) ? e.getCause().toString() : "No cause";
        String message = e.getMessage();
        String stackTrace = Arrays.toString(e.getStackTrace());

        handleException(name, cause, message, packageName, stackTrace);
    }

    public static void Throwable(Throwable t) {
        String name = t.getClass().getSimpleName();
        String packageName = t.getClass().getName();
        String cause = (t.getCause() != null) ? t.getCause().toString() : "No cause";
        String message = t.getMessage();
        String stackTrace = Arrays.toString(t.getStackTrace());

        handleException(name, cause, message, packageName, stackTrace);
    }
}