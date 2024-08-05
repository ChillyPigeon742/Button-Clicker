package net.alek.buttonclicker.libraries;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class ATextArea extends JTextArea {

    private static final String ELLIPSIS = "...";

    private boolean updating = false;

    public ATextArea() {
        setLineWrap(true);
        setWrapStyleWord(false);

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleTextChange();
            }
        });
    }

    private void handleTextChange() {
        if (updating) return;
        updating = true;

        SwingUtilities.invokeLater(() -> {
            String text = getText();
            String[] lines = text.split("\n");

            StringBuilder newText = new StringBuilder();
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (i > 0) {
                    newText.append("\n");
                }
                newText.append(addEllipsisIfNeeded(line, getWidth()));
            }

            setText(newText.toString());
            updating = false;
        });
    }

    private String addEllipsisIfNeeded(String text, int maxWidth) {
        FontMetrics fm = getFontMetrics(getFont());
        int ellipsisWidth = fm.stringWidth(ELLIPSIS);

        if (fm.stringWidth(text) <= maxWidth) {
            return text;
        } else {
            int length = text.length();
            while (fm.stringWidth(text) + ellipsisWidth > maxWidth && length > 0) {
                length--;
                text = text.substring(0, length);
            }
            return text + ELLIPSIS;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension preferredSize = super.getPreferredSize();
        String text = getText();
        FontMetrics fm = getFontMetrics(getFont());

        int maxWidth = 0;
        String[] lines = text.split("\n");
        for (String line : lines) {
            int lineWidth = fm.stringWidth(line);
            if (lineWidth > maxWidth) {
                maxWidth = lineWidth;
            }
        }

        return new Dimension(Math.min(maxWidth, preferredSize.width), preferredSize.height);
    }
}