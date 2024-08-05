package net.alek.buttonclicker.engine;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatDarculaLaf;

import javafx.embed.swing.JFXPanel;

import net.alek.buttonclicker.libraries.StorageLibrary;

import net.alek.buttonclicker.services.SaveService;
import net.alek.buttonclicker.utilities.RenderUtility;

import javax.swing.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args){
        FlatLaf.registerCustomDefaultsSource("assets.buttonclicker.config");
        FlatDarculaLaf.setup();

        System.setProperty("log4j.configurationFile", Objects.requireNonNull(ResourceManager.class.getResource("/assets/buttonclicker/config/log4j2.xml")).toString());

        StorageLibrary.frame.getRootPane().putClientProperty("JRootPane.titleBarShowTitle", false);
        StorageLibrary.frame.getRootPane().putClientProperty("JRootPane.titleBarShowIcon", false);

        StorageLibrary.backwardButton.putClientProperty("JButton.buttonType", "roundRect");
        StorageLibrary.forwardButton.putClientProperty("JButton.buttonType", "roundRect");

        StorageLibrary.logger.info("Starting Engine...");
        StorageLibrary.logger.info("Initializing Engine Pipelines...");

        new JFXPanel();

        new ResourceManager();
        SwingUtilities.invokeLater(RenderUtility::new);

        SaveService.startAutosaveTimer();
    }
}