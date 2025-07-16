package net.alek.buttonclicker.core;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import net.alek.buttonclicker.transfer.event.type.SubscribeMethod;
import net.alek.buttonclicker.transfer.event.type.Event;
import net.alek.buttonclicker.services.AutoClickerDetectorService;
import net.alek.buttonclicker.ui.RenderService;
import net.alek.buttonclicker.util.GUIUtils;

import javax.swing.*;

public class Glow {
    static {
        Event.INIT_GUI.subscribe(SubscribeMethod.SYNC, ignored -> initializeGUI());
    }

    public static void initializeGUI(){
        FlatLaf.registerCustomDefaultsSource("assets.buttonclicker.config");
        FlatDarculaLaf.setup();

        RenderService.frame.getRootPane().putClientProperty("JRootPane.titleBarShowTitle", false);
        RenderService.frame.getRootPane().putClientProperty("JRootPane.titleBarShowIcon", false);

        RenderService.backwardButton.putClientProperty("JButton.buttonType", "roundRect");
        RenderService.forwardButton.putClientProperty("JButton.buttonType", "roundRect");

        GUIUtils.repaint();

        SwingUtilities.invokeLater(() -> {
            new RenderService();

            WriteUtility.startAutosaveTimer();
            new AutoClickerDetectorService();
        });
    }
}
