package net.alek.buttonclicker.util;

import javax.swing.*;

public class GUIUtils {
    public static void repaint(){
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);
    }
}
