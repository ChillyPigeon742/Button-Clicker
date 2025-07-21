package net.alek.buttonclicker.ui;

import javax.swing.*;
import java.util.function.Supplier;

public enum Menu {
    MAIN_MENU("Button Clicker", MainMenuPanel::new),
    SAVE_MANAGER("Save Manager", SaveManagerPanel::new),
    CHOOSE_A_SIDE("Choose A Side", ChooseASidePanel::new),
    ENTER_A_SAVE_NAME("Enter A Save name", EnterASaveNamePanel::new),
    SAVE_INFO("Save Info", SaveInfoPanel::new),
    SETTINGS("Settings", SettingsPanel::new),
    WIKI("Wiki", WikiPanel::new),
    CREDITS("Credits", CreditsPanel::new),
    LOADING("Button Clicker", LoadingPanel::new),
    GAME("Game", GamePanel::new),
    PAUSE("Button Clicker", PausePanel::new),
    SHOP("Shop", ShopPanel::new),
    DEBUG("Debug", DebugPanel::new);

    public final String title;
    public final Supplier<JPanel> panelSupplier;

    Menu(String title, Supplier<JPanel> panelSupplier) {
        this.title = title;
        this.panelSupplier = panelSupplier;
    }

    @Override
    public String toString() {
        String[] words = name().split("_");
        StringBuilder formatted = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formatted.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    formatted.append(word.substring(1).toLowerCase());
                }
                formatted.append(" ");
            }
        }
        return formatted.toString().trim();
    }
}
