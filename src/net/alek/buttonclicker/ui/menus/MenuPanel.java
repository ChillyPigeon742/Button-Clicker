package net.alek.buttonclicker.ui.menus;

import net.alek.buttonclicker.ui.Menu;

import javax.swing.*;
import java.awt.*;

public abstract class MenuPanel extends JPanel {
    protected final JLabel titleLabel;
    protected final JButton backButton;

    public MenuPanel(Menu menu) {
        this.setLayout(new BorderLayout());

        titleLabel = new JLabel(title, SwingConstants.CENTER);
        backButton = new JButton("Back To " + menu.toString());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);

        backButton.addActionListener(e -> MenuManager.goBack());

        this.add(topBar, BorderLayout.NORTH);

        this.add(buildContent(), BorderLayout.CENTER);
    }

    protected abstract Component buildContent();
}
