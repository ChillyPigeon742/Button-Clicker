package net.alek.buttonclicker.util;

import net.alek.buttonclicker.core.ErrorHandler;
import net.alek.buttonclicker.ui.RenderService;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
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

    public static boolean isTextFieldEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    public DocumentFilter textFieldLimit(int maxCharacters) {
        return new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() + string.trim().length()) <= maxCharacters) {
                    super.insertString(fb, offset, string.trim(), attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() - length + text.trim().length()) <= maxCharacters) {
                    super.replace(fb, offset, length, text.trim(), attrs);
                }
            }
        };
    }

    public static void repaint(){
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);
    }
}
