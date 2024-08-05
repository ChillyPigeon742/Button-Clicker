package net.alek.buttonclicker.libraries;

import javax.swing.*;
import java.awt.*;

public class ALabel extends JLabel {
    private final Timer timer;
    private boolean isSpinning;
    private double angle = 0;

    public ALabel() {
        timer = new Timer(16, e -> {
            angle += 0.05;
            repaint();
        });
    }

    public void startSpinning() {
        timer.start();
        isSpinning = true;
    }

    public void stopSpinning() {
        timer.stop();
        angle = 0;
        isSpinning = false;
        repaint();
    }

    public boolean isSpinning(){
        return isSpinning;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getIcon() != null) {
            ImageIcon icon = (ImageIcon) getIcon();
            Image image = icon.getImage();

            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);

            int x = (getWidth() - imageWidth) / 2;
            int y = (getHeight() - imageHeight) / 2;

            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.translate(x, y);
            g2d.rotate(angle, imageWidth / 2.0, imageHeight / 2.0);

            g2d.drawImage(image, 0, 0, imageWidth, imageHeight, this);
            g2d.dispose();
        } else {
            super.paintComponent(g);
        }
    }
}