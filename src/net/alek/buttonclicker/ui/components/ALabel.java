package net.alek.buttonclicker.ui.components;

import net.alek.buttonclicker.util.Unsigned;

import javax.swing.*;
import java.awt.*;

public class ALabel extends JLabel {
    private final Timer timer;
    private boolean isSpinning;
    private double rotationSpeed = 0.05;
    private double angle = 0;
    private Unsigned fps = new Unsigned(9, 60);

    public ALabel() {
        setDoubleBuffered(true);
        timer = new Timer(1000 / fps.toShort(), e -> {
            angle += rotationSpeed;
            angle %= 2 * Math.PI;
            repaint();
        });
    }

    private void updateTimerDelay() {
        int delay = 1000 / fps.toShort();
        timer.setDelay(delay);
    }

    public void startSpinning() {
        if (!timer.isRunning()) {
            timer.start();
            isSpinning = true;
        }
    }

    public void stopSpinning() {
        timer.stop();
        angle = 0;
        isSpinning = false;
        repaint();
    }

    public void toggleSpinning(){
        if (!timer.isRunning()) {
            startSpinning();
        }else{
            stopSpinning();
        }
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setAngle(double angle) {
        this.angle = angle % (2 * Math.PI);
        repaint();
    }

    public void setFPS(Unsigned fps) {
        if (fps.toShort() == 0) {
            stopSpinning();
        }
        this.fps = fps;
        updateTimerDelay();
        if (timer.isRunning()) {
            timer.restart();
        }
    }

    public boolean isSpinning(){
        return isSpinning;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public double getAngle() {
        return angle;
    }

    public Unsigned getFPS() {
        return fps;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Icon rawIcon = getIcon();
        if (rawIcon instanceof ImageIcon icon) {
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