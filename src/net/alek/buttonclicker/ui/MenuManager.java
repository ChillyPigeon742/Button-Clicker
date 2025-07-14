package net.alek.buttonclicker.ui;

import java.util.Objects;

public class MenuManager{

    public static boolean StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu = false;
    public static boolean resetWindow = false;
    
    public static void openMenu(String menuID){
        if(Objects.equals(menuID, "Spark")){
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
        if(Objects.equals(menuID, "Spark")){
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
        if(Objects.equals(menuID, "Spark")){
            if(RenderService.saveManagerButton.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Save Manager")){
            if(RenderService.save1Button.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Choose A Side")){
            if(RenderService.lightSideButton.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Enter A Save Name")){
            if(RenderService.saveNameField.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Save Info")){
            if(RenderService.saveInfoText.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Settings")){
            if(RenderService.masterVolumeSlider.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Wiki")){
            if(RenderService.wikiEditorPane.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Credits")){
            if(RenderService.creditsText.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Loading")){
            if(RenderService.loadingBar.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Game")){
            if(RenderService.buttonTop.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Pause")){
            if(Objects.equals(RenderService.quitButton.getText(), "Return To Spark Menu")){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Shop")){
            if(RenderService.shopItem1.isVisible()){
                return true;
            }else{
                return false;
            }
        }else if(Objects.equals(menuID, "Debug")){
            if(Objects.equals(RenderService.titleText.getText(), "Debug")){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}