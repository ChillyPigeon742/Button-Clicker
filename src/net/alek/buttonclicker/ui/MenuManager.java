package net.alek.buttonclicker.ui;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MenuManager{
    private static JPanel container;
    private static CardLayout layout;
    private static Map<Menu, JPanel> menuPanels = new EnumMap<>(Menu.class);
    private static Deque<Menu> menuHistory = new ArrayDeque<>();

    static {
        for(Menu menu : Menu.values()){
            menuPanels.put(menu, menu.panelSupplier.get());

            if(Objects.equals(menu.name(), "MENU")){
                menuPanels.get(menu).setVisible(true);
            }
        }
    }
    
    public static void openMenu(Menu menu){
        if(Objects.equals(menuID, "Main")){
            RenderService.buttonClickerVersionText.setVisible(true);
            RenderService.currentSaveText.setVisible(true);
            RenderService.startGameButton.setVisible(true);
            RenderService.saveManagerButton.setVisible(true);
            RenderService.settingsButton.setVisible(true);
            RenderService.quitButton.setVisible(true);
        }else if(Objects.equals(menuID, "Save Manager")){
            RenderService.infoButton.setVisible(true);
            RenderService.deleteSave1Button.setVisible(true);
            RenderService.deleteSave2Button.setVisible(true);
            RenderService.deleteSave3Button.setVisible(true);
            RenderService.save1Button.setVisible(true);
            RenderService.save2Button.setVisible(true);
            RenderService.save3Button.setVisible(true);
        }else if(Objects.equals(menuID, "Choose A Side")){
            RenderService.lightSideButton.setVisible(true);
            RenderService.darkSideButton.setVisible(true);
            RenderService.magicSideButton.setVisible(true);
            RenderService.neutralSideButton.setVisible(true);
            RenderService.lightSideText.setVisible(true);
            RenderService.darkSideText.setVisible(true);
            RenderService.magicSideText.setVisible(true);
            RenderService.neutralSideText.setVisible(true);
        }else if(Objects.equals(menuID, "Enter A Save Name")){
            RenderService.saveNameField.setVisible(true);
            RenderService.saveText.setVisible(true);
        }else if(Objects.equals(menuID, "Save Info")){
            RenderService.saveInfoText.setVisible(true);
            RenderService.forwardButton.setVisible(true);
        }else if(Objects.equals(menuID, "Settings")){
            RenderService.wikiButton.setVisible(true);
            RenderService.creditsButton.setVisible(true);
            RenderService.bugReportButton.setVisible(true);
            RenderService.masterVolumeSlider.setVisible(true);
            RenderService.masterVolumeText.setVisible(true);
            RenderService.musicVolumeSlider.setVisible(true);
            RenderService.musicVolumeText.setVisible(true);
            RenderService.sfxVolumeSlider.setVisible(true);
            RenderService.sfxVolumeText.setVisible(true);
            RenderService.musicDelayText.setVisible(true);
            RenderService.musicDelaySpinner.setVisible(true);
        }else if(Objects.equals(menuID, "Wiki")){
            RenderService.wikiHelpButton.setVisible(true);
            RenderService.wikiEditorPane.setVisible(true);
            RenderService.wikiScrollPane.setVisible(true);
        }else if(Objects.equals(menuID, "Credits")){
            RenderService.creditsText.setVisible(true);
        }else if(Objects.equals(menuID, "Loading")){
            RenderService.nextTipButton.setVisible(true);
            RenderService.tipsPanel.setVisible(true);
            RenderService.tipsText.setVisible(true);
            RenderService.progressText.setVisible(true);
            RenderService.loadingBar.setVisible(true);
        }else if(Objects.equals(menuID, "Game")){
            RenderService.buttonLayeredPane.setVisible(true);
            RenderService.buttonBase.setVisible(true);
            RenderService.buttonTop.setVisible(true);
            RenderService.clickPowerField.setVisible(true);
            RenderService.clicksField.setVisible(true);
            RenderService.shopButton.setVisible(true);
            RenderService.pauseButton.setVisible(true);
        }else if(Objects.equals(menuID, "Pause")){
            RenderService.startGameButton.setVisible(true);
            RenderService.settingsButton.setVisible(true);
            RenderService.quitButton.setVisible(true);
        }else if(Objects.equals(menuID, "Shop")){
            RenderService.shopItem1.setVisible(true);
            RenderService.shopItem2.setVisible(true);
            RenderService.shopItem3.setVisible(true);
            RenderService.shopItem4.setVisible(true);
            RenderService.shopItem5.setVisible(true);
            RenderService.shopItem6.setVisible(true);
        }else if(Objects.equals(menuID, "Debug")){
            RenderService.showConsoleButton.setVisible(true);
            RenderService.crashButton.setVisible(true);
        }
    }

    public static void closeMenu(String menuID){
        if(Objects.equals(menuID, "Main")){
            RenderService.buttonClickerVersionText.setVisible(false);
            RenderService.currentSaveText.setVisible(false);
            RenderService.startGameButton.setVisible(false);
            RenderService.saveManagerButton.setVisible(false);
            RenderService.settingsButton.setVisible(false);
            RenderService.quitButton.setVisible(false);
            RenderService.debugButton.setVisible(false);
        }else if(Objects.equals(menuID, "Save Manager")){
            RenderService.infoButton.setVisible(false);
            RenderService.deleteSave1Button.setVisible(false);
            RenderService.deleteSave2Button.setVisible(false);
            RenderService.deleteSave3Button.setVisible(false);
            RenderService.save1Button.setVisible(false);
            RenderService.save2Button.setVisible(false);
            RenderService.save3Button.setVisible(false);
        }else if(Objects.equals(menuID, "Choose A Side")){
            RenderService.lightSideButton.setVisible(false);
            RenderService.darkSideButton.setVisible(false);
            RenderService.magicSideButton.setVisible(false);
            RenderService.neutralSideButton.setVisible(false);
            RenderService.lightSideText.setVisible(false);
            RenderService.darkSideText.setVisible(false);
            RenderService.magicSideText.setVisible(false);
            RenderService.neutralSideText.setVisible(false);
        }else if(Objects.equals(menuID, "Enter A Save Name")){
            RenderService.saveNameField.setVisible(false);
            RenderService.saveText.setVisible(false);
        }else if(Objects.equals(menuID, "Save Info")){
            RenderService.saveInfoText.setVisible(false);
            RenderService.forwardButton.setVisible(false);
        }else if(Objects.equals(menuID, "Settings")){
            RenderService.wikiButton.setVisible(false);
            RenderService.creditsButton.setVisible(false);
            RenderService.bugReportButton.setVisible(false);
            RenderService.masterVolumeSlider.setVisible(false);
            RenderService.masterVolumeText.setVisible(false);
            RenderService.musicVolumeSlider.setVisible(false);
            RenderService.musicVolumeText.setVisible(false);
            RenderService.sfxVolumeSlider.setVisible(false);
            RenderService.sfxVolumeText.setVisible(false);
            RenderService.musicDelayText.setVisible(false);
            RenderService.musicDelaySpinner.setVisible(false);
        }else if(Objects.equals(menuID, "Wiki")){
            RenderService.wikiHelpButton.setVisible(false);
            RenderService.wikiEditorPane.setVisible(false);
            RenderService.wikiScrollPane.setVisible(false);
        }else if(Objects.equals(menuID, "Credits")){
            RenderService.creditsText.setVisible(false);
        }else if(Objects.equals(menuID, "Loading")){
            RenderService.nextTipButton.setVisible(false);
            RenderService.tipsPanel.setVisible(false);
            RenderService.tipsText.setVisible(false);
            RenderService.progressText.setVisible(false);
            RenderService.loadingBar.setVisible(false);
        }else if(Objects.equals(menuID, "Game")){
            RenderService.buttonLayeredPane.setVisible(false);
            RenderService.buttonBase.setVisible(false);
            RenderService.buttonTop.setVisible(false);
            RenderService.clickPowerField.setVisible(false);
            RenderService.clicksField.setVisible(false);
            RenderService.shopButton.setVisible(false);
            RenderService.pauseButton.setVisible(false);
        }else if(Objects.equals(menuID, "Pause")){
            RenderService.startGameButton.setVisible(false);
            RenderService.settingsButton.setVisible(false);
            RenderService.quitButton.setVisible(false);
        }else if(Objects.equals(menuID, "Shop")){
            RenderService.shopItem1.setVisible(false);
            RenderService.shopItem2.setVisible(false);
            RenderService.shopItem3.setVisible(false);
            RenderService.shopItem4.setVisible(false);
            RenderService.shopItem5.setVisible(false);
            RenderService.shopItem6.setVisible(false);
        }else if(Objects.equals(menuID, "Debug")){
            RenderService.showConsoleButton.setVisible(false);
            RenderService.crashButton.setVisible(false);
        }
    }

    public static boolean isMenuOpen(String menuID){
        if(Objects.equals(menuID, "Main")){
            return RenderService.saveManagerButton.isVisible();
        }else if(Objects.equals(menuID, "Save Manager")){
            return RenderService.save1Button.isVisible();
        }else if(Objects.equals(menuID, "Choose A Side")){
            return RenderService.lightSideButton.isVisible();
        }else if(Objects.equals(menuID, "Enter A Save Name")){
            return RenderService.saveNameField.isVisible();
        }else if(Objects.equals(menuID, "Save Info")){
            return RenderService.saveInfoText.isVisible();
        }else if(Objects.equals(menuID, "Settings")){
            return RenderService.masterVolumeSlider.isVisible();
        }else if(Objects.equals(menuID, "Wiki")){
            return RenderService.wikiEditorPane.isVisible();
        }else if(Objects.equals(menuID, "Credits")){
            return RenderService.creditsText.isVisible();
        }else if(Objects.equals(menuID, "Loading")){
            return RenderService.loadingBar.isVisible();
        }else if(Objects.equals(menuID, "Game")){
            return RenderService.buttonTop.isVisible();
        }else if(Objects.equals(menuID, "Pause")){
            return Objects.equals(RenderService.quitButton.getText(), "Return To Spark Menu");
        }else if(Objects.equals(menuID, "Shop")){
            return RenderService.shopItem1.isVisible();
        }else if(Objects.equals(menuID, "Debug")){
            return Objects.equals(RenderService.titleText.getText(), "Debug");
        }
        return false;
    }
}