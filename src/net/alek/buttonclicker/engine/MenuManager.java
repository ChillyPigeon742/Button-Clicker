package net.alek.buttonclicker.engine;

import net.alek.buttonclicker.libraries.StorageLibrary;

import java.util.Objects;

public class MenuManager{
    public static void openMenu(String menuID){
        if(Objects.equals(menuID, "Main")){
            StorageLibrary.buttonClickerVersionText.setVisible(true);
            StorageLibrary.currentSaveText.setVisible(true);
            StorageLibrary.startGameButton.setVisible(true);
            StorageLibrary.saveManagerButton.setVisible(true);
            StorageLibrary.settingsButton.setVisible(true);
            StorageLibrary.quitButton.setVisible(true);
        }else if(Objects.equals(menuID, "Save Manager")){
            StorageLibrary.infoButton.setVisible(true);
            StorageLibrary.deleteSave1Button.setVisible(true);
            StorageLibrary.deleteSave2Button.setVisible(true);
            StorageLibrary.deleteSave3Button.setVisible(true);
            StorageLibrary.save1Button.setVisible(true);
            StorageLibrary.save2Button.setVisible(true);
            StorageLibrary.save3Button.setVisible(true);
        }else if(Objects.equals(menuID, "Choose A Side")){
            StorageLibrary.lightSideButton.setVisible(true);
            StorageLibrary.darkSideButton.setVisible(true);
            StorageLibrary.magicSideButton.setVisible(true);
            StorageLibrary.neutralSideButton.setVisible(true);
            StorageLibrary.lightSideText.setVisible(true);
            StorageLibrary.darkSideText.setVisible(true);
            StorageLibrary.magicSideText.setVisible(true);
            StorageLibrary.neutralSideText.setVisible(true);
        }else if(Objects.equals(menuID, "Enter A Save Name")){
            StorageLibrary.saveNameField.setVisible(true);
            StorageLibrary.saveText.setVisible(true);
        }else if(Objects.equals(menuID, "Save Info")){
            StorageLibrary.saveInfoText.setVisible(true);
            StorageLibrary.forwardButton.setVisible(true);
        }else if(Objects.equals(menuID, "Settings")){
            StorageLibrary.wikiButton.setVisible(true);
            StorageLibrary.creditsButton.setVisible(true);
            StorageLibrary.bugReportButton.setVisible(true);
            StorageLibrary.masterVolumeSlider.setVisible(true);
            StorageLibrary.masterVolumeText.setVisible(true);
            StorageLibrary.musicVolumeSlider.setVisible(true);
            StorageLibrary.musicVolumeText.setVisible(true);
            StorageLibrary.sfxVolumeSlider.setVisible(true);
            StorageLibrary.sfxVolumeText.setVisible(true);
            StorageLibrary.musicDelayText.setVisible(true);
            StorageLibrary.musicDelaySpinner.setVisible(true);
        }else if(Objects.equals(menuID, "Wiki")){
            StorageLibrary.wikiHelpButton.setVisible(true);
            StorageLibrary.wikiEditorPane.setVisible(true);
            StorageLibrary.wikiScrollPane.setVisible(true);
        }else if(Objects.equals(menuID, "Credits")){
            StorageLibrary.creditsText.setVisible(true);
        }else if(Objects.equals(menuID, "Loading")){
            StorageLibrary.nextTipButton.setVisible(true);
            StorageLibrary.tipsPanel.setVisible(true);
            StorageLibrary.tipsText.setVisible(true);
            StorageLibrary.progressText.setVisible(true);
            StorageLibrary.loadingBar.setVisible(true);
        }else if(Objects.equals(menuID, "Game")){
            StorageLibrary.buttonLayeredPane.setVisible(true);
            StorageLibrary.buttonBase.setVisible(true);
            StorageLibrary.buttonTop.setVisible(true);
            StorageLibrary.clickPowerField.setVisible(true);
            StorageLibrary.clicksField.setVisible(true);
            StorageLibrary.shopButton.setVisible(true);
            StorageLibrary.pauseButton.setVisible(true);
        }else if(Objects.equals(menuID, "Pause")){
            StorageLibrary.startGameButton.setVisible(true);
            StorageLibrary.settingsButton.setVisible(true);
            StorageLibrary.quitButton.setVisible(true);
        }else if(Objects.equals(menuID, "Shop")){
            StorageLibrary.shopItem1.setVisible(true);
            StorageLibrary.shopItem2.setVisible(true);
            StorageLibrary.shopItem3.setVisible(true);
            StorageLibrary.shopItem4.setVisible(true);
            StorageLibrary.shopItem5.setVisible(true);
            StorageLibrary.shopItem6.setVisible(true);
        }
    }

    public static void closeMenu(String menuID){
        if(Objects.equals(menuID, "Main")){
            StorageLibrary.buttonClickerVersionText.setVisible(false);
            StorageLibrary.currentSaveText.setVisible(false);
            StorageLibrary.startGameButton.setVisible(false);
            StorageLibrary.saveManagerButton.setVisible(false);
            StorageLibrary.settingsButton.setVisible(false);
            StorageLibrary.quitButton.setVisible(false);
            StorageLibrary.debugButton.setVisible(false);
        }else if(Objects.equals(menuID, "Save Manager")){
            StorageLibrary.infoButton.setVisible(false);
            StorageLibrary.deleteSave1Button.setVisible(false);
            StorageLibrary.deleteSave2Button.setVisible(false);
            StorageLibrary.deleteSave3Button.setVisible(false);
            StorageLibrary.save1Button.setVisible(false);
            StorageLibrary.save2Button.setVisible(false);
            StorageLibrary.save3Button.setVisible(false);
        }else if(Objects.equals(menuID, "Choose A Side")){
            StorageLibrary.lightSideButton.setVisible(false);
            StorageLibrary.darkSideButton.setVisible(false);
            StorageLibrary.magicSideButton.setVisible(false);
            StorageLibrary.neutralSideButton.setVisible(false);
            StorageLibrary.lightSideText.setVisible(false);
            StorageLibrary.darkSideText.setVisible(false);
            StorageLibrary.magicSideText.setVisible(false);
            StorageLibrary.neutralSideText.setVisible(false);
        }else if(Objects.equals(menuID, "Enter A Save Name")){
            StorageLibrary.saveNameField.setVisible(false);
            StorageLibrary.saveText.setVisible(false);
        }else if(Objects.equals(menuID, "Save Info")){
            StorageLibrary.saveInfoText.setVisible(false);
            StorageLibrary.forwardButton.setVisible(false);
        }else if(Objects.equals(menuID, "Settings")){
            StorageLibrary.wikiButton.setVisible(false);
            StorageLibrary.creditsButton.setVisible(false);
            StorageLibrary.bugReportButton.setVisible(false);
            StorageLibrary.masterVolumeSlider.setVisible(false);
            StorageLibrary.masterVolumeText.setVisible(false);
            StorageLibrary.musicVolumeSlider.setVisible(false);
            StorageLibrary.musicVolumeText.setVisible(false);
            StorageLibrary.sfxVolumeSlider.setVisible(false);
            StorageLibrary.sfxVolumeText.setVisible(false);
            StorageLibrary.musicDelayText.setVisible(false);
            StorageLibrary.musicDelaySpinner.setVisible(false);
        }else if(Objects.equals(menuID, "Wiki")){
            StorageLibrary.wikiHelpButton.setVisible(false);
            StorageLibrary.wikiEditorPane.setVisible(false);
            StorageLibrary.wikiScrollPane.setVisible(false);
        }else if(Objects.equals(menuID, "Credits")){
            StorageLibrary.creditsText.setVisible(false);
        }else if(Objects.equals(menuID, "Loading")){
            StorageLibrary.nextTipButton.setVisible(false);
            StorageLibrary.tipsPanel.setVisible(false);
            StorageLibrary.tipsText.setVisible(false);
            StorageLibrary.progressText.setVisible(false);
            StorageLibrary.loadingBar.setVisible(false);
        }else if(Objects.equals(menuID, "Game")){
            StorageLibrary.buttonLayeredPane.setVisible(false);
            StorageLibrary.buttonBase.setVisible(false);
            StorageLibrary.buttonTop.setVisible(false);
            StorageLibrary.clickPowerField.setVisible(false);
            StorageLibrary.clicksField.setVisible(false);
            StorageLibrary.shopButton.setVisible(false);
            StorageLibrary.pauseButton.setVisible(false);
        }else if(Objects.equals(menuID, "Pause")){
            StorageLibrary.startGameButton.setVisible(false);
            StorageLibrary.settingsButton.setVisible(false);
            StorageLibrary.quitButton.setVisible(false);
        }else if(Objects.equals(menuID, "Shop")){
            StorageLibrary.shopItem1.setVisible(false);
            StorageLibrary.shopItem2.setVisible(false);
            StorageLibrary.shopItem3.setVisible(false);
            StorageLibrary.shopItem4.setVisible(false);
            StorageLibrary.shopItem5.setVisible(false);
            StorageLibrary.shopItem6.setVisible(false);
        }
    }

    public static boolean isMenuOpen(String menuID){
        if(Objects.equals(menuID, "Main")){
            if(StorageLibrary.saveManagerButton.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Save Manager")){
            if(StorageLibrary.save1Button.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Choose A Side")){
            if(StorageLibrary.lightSideButton.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Enter A Save Name")){
            if(StorageLibrary.saveNameField.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Save Info")){
            if(StorageLibrary.saveInfoText.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Settings")){
            if(StorageLibrary.masterVolumeSlider.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Wiki")){
            if(StorageLibrary.wikiEditorPane.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Credits")){
            if(StorageLibrary.creditsText.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Loading")){
            if(StorageLibrary.loadingBar.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Game")){
            if(StorageLibrary.buttonTop.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Pause")){
            if(Objects.equals(StorageLibrary.quitButton.getText(), "Return To Main Menu")){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Shop")){
            if(StorageLibrary.shopItem1.isVisible()){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}