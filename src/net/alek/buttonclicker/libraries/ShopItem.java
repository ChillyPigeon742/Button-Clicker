package net.alek.buttonclicker.libraries;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShopItem extends JPanel {

    private JLabel priceLabel;
    private JLabel iconLabel;
    private JLabel descriptionLabel;
    private boolean isDisabled = false;
    private MouseListener[] mouseListeners;

    public ShopItem() {
        setLayout(null);
        setFocusable(false);
        setBorder(null);
        putClientProperty(FlatClientProperties.STYLE_CLASS, "roundPanel");
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(new Color(60, 60, 60));

        JPanel pricePanel = new JPanel();
        pricePanel.setBounds(10, 10, 89, 40);
        pricePanel.putClientProperty(FlatClientProperties.STYLE_CLASS, "roundPanel");
        pricePanel.setBackground(new Color(70, 70, 70));
        pricePanel.setLayout(new GridBagLayout());

        priceLabel = new JLabel();
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Nunito", Font.PLAIN, 11));
        priceLabel.setHorizontalAlignment(JLabel.CENTER);
        pricePanel.add(priceLabel);

        JPanel iconPanel = new JPanel();
        iconPanel.setBounds(110, 10, 80, 40);
        iconPanel.putClientProperty(FlatClientProperties.STYLE_CLASS, "roundPanel");
        iconPanel.setBackground(new Color(70, 70, 70));
        iconPanel.setLayout(new GridBagLayout());

        iconLabel = new JLabel();
        iconPanel.add(iconLabel);

        JPanel descriptionPanel = new JPanel(new BorderLayout()) {
            @Override
            public Insets getInsets() {
                return new Insets(0, 3, 0, 0);
            }
        };
        descriptionPanel.setBounds(10, 60, 180, 30);
        descriptionPanel.putClientProperty(FlatClientProperties.STYLE_CLASS, "roundPanel");
        descriptionPanel.setBackground(new Color(70, 70, 70));

        descriptionLabel = new JLabel();
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setFont(new Font("Nunito", Font.PLAIN, 11));
        descriptionLabel.setHorizontalAlignment(JLabel.LEFT);
        descriptionLabel.setVerticalAlignment(JLabel.CENTER);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder());
        descriptionPanel.add(descriptionLabel, BorderLayout.CENTER);

        add(pricePanel);
        add(iconPanel);
        add(descriptionPanel);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    setBackground(new Color(70,70,70));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    setBackground(new Color(60,60,60));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void setPrice(String newPrice) {
        priceLabel.setText(newPrice);
    }

    public void setIcon(Icon newIcon) {
        iconLabel.setIcon(newIcon);
    }

    public void setDescription(String newDescription) {
        descriptionLabel.setText(newDescription);
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public Icon getIcon() {
        return iconLabel.getIcon();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public void setDisabled(boolean disabled){
        if(disabled){
            mouseListeners = this.getMouseListeners();
            for (MouseListener listener : mouseListeners) {
                this.removeMouseListener(listener);
            }
            this.setCursor(null);
            isDisabled = true;
        }else if(!disabled){
            if (mouseListeners != null) {
                for (MouseListener listener : mouseListeners) {
                    this.addMouseListener(listener);
                }
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                isDisabled = false;
            }
        }
    }

    public boolean isDisabled(){
        return isDisabled;
    }
}