package net.alek.buttonclicker.libraries;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AEditorPane extends JEditorPane {
    private BufferedImage BGImage;
    private boolean bgImageEnabled = false;

    @Override
    protected void paintComponent(Graphics g) {
        if(bgImageEnabled){
            if(BGImage!=null){
                int imgWidth = BGImage.getWidth();
                int imgHeight = BGImage.getHeight();
                for (int x = 0; x < getWidth(); x += imgWidth) {
                    for (int y = 0; y < getHeight(); y += imgHeight) {
                        g.drawImage(BGImage, x, y, this);
                    }
                }
            }
        }
        super.paintComponent(g);
    }

    public void setBackgroundImage(BufferedImage bgImage){
        BGImage = bgImage;
    }

    public void setBackgroundImageEnabled(boolean enabled) {
        bgImageEnabled = enabled;
        repaint();
    }

    public boolean isBackgroundImageEnabled() {
        return bgImageEnabled;
    }

    public BufferedImage getBackgroundImage(){
        return BGImage;
    }
}