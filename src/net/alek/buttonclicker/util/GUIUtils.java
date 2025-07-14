package net.alek.buttonclicker.util;

import net.alek.buttonclicker.core.ErrorHandler;
import net.alek.buttonclicker.ui.RenderService;

import javax.swing.*;
import java.awt.*;

public class GUIUtils {
    public static void setWindowIcon(Image windowIcon){
        RenderService.frame.setIconImage(windowIcon);
    }

    public static void setLookAndFeel(String lookAndFeel){
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            ErrorHandler.Exception(e);
        }
       repaint();
    }

    public static void repaint(){
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);
    }
}
