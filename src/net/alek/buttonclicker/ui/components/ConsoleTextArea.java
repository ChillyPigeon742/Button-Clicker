package net.alek.buttonclicker.ui.components;

import net.alek.buttonclicker.core.ErrorHandler;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Map;

public class ConsoleTextArea extends JTextArea {

    private final Map<Integer, Color> lineColors = new HashMap<>();
    private int xOffset = 0;
    private int yOffset = -2;

    public ConsoleTextArea() {
        super();
        setOpaque(true);

        UIManager.addPropertyChangeListener(evt -> {
            if ("lookAndFeel".equals(evt.getPropertyName())) {
                setXOffset(2);
                setYOffset(0);

                SwingUtilities.invokeLater(this::repaint);
            }
        });

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                repaint();
            }
        });
    }

    public void setLineColor(int lineNumber, Color color) {
        lineColors.put(lineNumber, color);
        repaint();
    }

    public Color getLineColor(int lineNumber) {
        return lineColors.getOrDefault(lineNumber, null);
    }

    public int getLineFromText(String text) {
        try {
            int lineCount = getLineCount();
            for (int i = 0; i < lineCount; i++) {
                int start = getLineStartOffset(i);
                int end = getLineEndOffset(i);
                String lineText = getText(start, end - start);
                if (lineText.contains(text)) {
                    return i;
                }
            }
        } catch (BadLocationException e) {
            ErrorHandler.Exception(e);
        }
        return -1;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        repaint();
    }

    @Override
    public void append(String text) {
        super.append(text);
        repaint();
    }

    @Override
    public void insert(String text, int pos) {
        super.insert(text, pos);
        repaint();
    }

    public void setFontSize(int size) {
        setFont(getFont().deriveFont((float) size));
        repaint();
    }

    public void setFontStyle(int style) {
        setFont(getFont().deriveFont(style, getFont().getSize()));
        repaint();
    }

    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
        repaint();
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        FontMetrics fm = getFontMetrics(getFont());
        int lineHeight = fm.getHeight();

        try {
            int lineCount = getLineCount();
            for (int i = 0; i < lineCount; i++) {
                Color lineColor = getLineColor(i);
                if (lineColor == null) {
                    continue;
                }

                int start = getLineStartOffset(i);
                int end = getLineEndOffset(i);
                String lineText = getText(start, end - start);

                int y = (i + 1) * lineHeight - fm.getDescent() + yOffset;
                g2d.setColor(getBackground());
                g2d.fillRect(0, y - lineHeight + fm.getDescent(), getWidth(), lineHeight);

                AttributedString attributedString = new AttributedString(lineText);
                attributedString.addAttribute(TextAttribute.FOREGROUND, lineColor);
                attributedString.addAttribute(TextAttribute.FONT, getFont());
                g2d.drawString(attributedString.getIterator(), xOffset, y);
            }
        } catch (BadLocationException e) {
            ErrorHandler.Exception(e);
        }
    }
}