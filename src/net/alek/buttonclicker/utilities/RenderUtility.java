package net.alek.buttonclicker.utilities;

import com.formdev.flatlaf.FlatClientProperties;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.engine.MenuManager;

import net.alek.buttonclicker.libraries.ATimer;
import net.alek.buttonclicker.libraries.StorageLibrary;

import net.alek.buttonclicker.services.InputService;
import net.alek.buttonclicker.services.LoadService;
import net.alek.buttonclicker.services.SaveService;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RenderUtility{
    public RenderUtility(){

        StorageLibrary.tipsPanel.setVisible(false);
        StorageLibrary.tipsPanel.setBackground(new Color(40,40,40));
        StorageLibrary.tipsPanel.setBounds(24,435,600,200);
        StorageLibrary.tipsPanel.setBorder(null);
        StorageLibrary.tipsPanel.putClientProperty(FlatClientProperties.STYLE_CLASS, "roundPanel");
        StorageLibrary.tipsPanel.add(StorageLibrary.tipsText, BorderLayout.CENTER);
        StorageLibrary.tipsPanel.add(StorageLibrary.nextTipButton, BorderLayout.PAGE_END);

        StorageLibrary.wikiScrollPane.createVerticalScrollBar();
        StorageLibrary.wikiScrollPane.setVisible(false);
        StorageLibrary.wikiScrollPane.setBounds(0,0,1015,595);
        StorageLibrary.wikiScrollPane.getViewport().setBackground(new Color(50, 50, 50));
        StorageLibrary.wikiScrollPane.setBorder(null);
        StorageLibrary.wikiScrollPane.setFocusable(false);

        StorageLibrary.buttonLayeredPane.setVisible(false);
        StorageLibrary.buttonLayeredPane.setBounds(195,200,300,300);
        StorageLibrary.buttonLayeredPane.add(StorageLibrary.buttonBase, JLayeredPane.DEFAULT_LAYER);
        StorageLibrary.buttonLayeredPane.add(StorageLibrary.buttonTop, JLayeredPane.PALETTE_LAYER);

        StorageLibrary.wikiEditorPane.setEditable(false);
        StorageLibrary.wikiEditorPane.setVisible(false);
        StorageLibrary.wikiEditorPane.setOpaque(false);
        StorageLibrary.wikiEditorPane.setCaret(new Caret() {
            @Override
            public void install(JTextComponent c) {

            }

            @Override
            public void deinstall(JTextComponent c) {

            }

            @Override
            public void paint(Graphics g) {

            }

            @Override
            public void addChangeListener(ChangeListener l) {

            }

            @Override
            public void removeChangeListener(ChangeListener l) {

            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public void setVisible(boolean v) {

            }

            @Override
            public boolean isSelectionVisible() {
                return false;
            }

            @Override
            public void setSelectionVisible(boolean v) {

            }

            @Override
            public void setMagicCaretPosition(Point p) {

            }

            @Override
            public Point getMagicCaretPosition() {
                return null;
            }

            @Override
            public void setBlinkRate(int rate) {

            }

            @Override
            public int getBlinkRate() {
                return 0;
            }

            @Override
            public int getDot() {
                return 0;
            }

            @Override
            public int getMark() {
                return 0;
            }

            @Override
            public void setDot(int dot) {

            }

            @Override
            public void moveDot(int dot) {

            }
        });
        StorageLibrary.wikiEditorPane.setBackgroundImageEnabled(false);
        StorageLibrary.wikiEditorPane.setBackgroundImage(StorageLibrary.portal2Whitewall);
        StorageLibrary.wikiEditorPane.setBackground(new Color(50, 50, 50));
        StorageLibrary.wikiEditorPane.setForeground(Color.WHITE);
        StorageLibrary.wikiEditorPane.setFont(new Font("Nunito",Font.BOLD,20));
        StorageLibrary.wikiEditorPane.setContentType("text/html");

        StorageLibrary.titleImage.setVisible(true);
        StorageLibrary.titleImage.setBounds(10,0,100,100);
        StorageLibrary.titleImage.setBackground(new Color(50,50,50));
        StorageLibrary.titleImage.setFocusable(false);
        StorageLibrary.titleImage.setIcon(StorageLibrary.bcIcon3);

        StorageLibrary.buttonBase.setVisible(false);
        StorageLibrary.buttonBase.setBounds(0, 0, 256, 256);
        StorageLibrary.buttonBase.setBackground(new Color(50,50,50));
        StorageLibrary.buttonBase.setFocusable(false);
        StorageLibrary.buttonBase.setIcon(StorageLibrary.buttonBaseIcon);

        StorageLibrary.buttonTop.setVisible(false);
        StorageLibrary.buttonTop.setBounds(30, 30, 198, 198);
        StorageLibrary.buttonTop.setBackground(new Color(50,50,50));
        StorageLibrary.buttonTop.setFocusable(false);
        StorageLibrary.buttonTop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.buttonTop.setIcon(StorageLibrary.buttonTopIcon);
        StorageLibrary.buttonTop.addMouseListener(new InputService.mouseListener());

        StorageLibrary.saveNameField.setVisible(false);
        StorageLibrary.saveNameField.setBounds(0, 300, 647, 50);
        StorageLibrary.saveNameField.setFocusable(true);
        StorageLibrary.saveNameField.setBackground(new Color(40,40,40));
        StorageLibrary.saveNameField.setForeground(Color.WHITE);
        StorageLibrary.saveNameField.setFont(new Font("Nunito",Font.BOLD,40));
        StorageLibrary.saveNameField.setEditable(true);
        StorageLibrary.saveNameField.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.saveNameField.setText("");
        StorageLibrary.saveNameField.setToolTipText("Empty Names Will Not Be Accepted");

        StorageLibrary.clicksField.setVisible(false);
        StorageLibrary.clicksField.setBounds(0, 0, 647, 50);
        StorageLibrary.clicksField.setFocusable(false);
        StorageLibrary.clicksField.setBackground(new Color(50,50,50));
        StorageLibrary.clicksField.setForeground(Color.WHITE);
        StorageLibrary.clicksField.setFont(new Font("Nunito",Font.BOLD,40));
        StorageLibrary.clicksField.setEditable(false);
        StorageLibrary.clicksField.setHorizontalAlignment(JTextField.CENTER);

        StorageLibrary.clickPowerField.setVisible(false);
        StorageLibrary.clickPowerField.setBounds(0, 50, 647, 50);
        StorageLibrary.clickPowerField.setFocusable(false);
        StorageLibrary.clickPowerField.setBackground(new Color(50,50,50));
        StorageLibrary.clickPowerField.setForeground(Color.WHITE);
        StorageLibrary.clickPowerField.setFont(new Font("Nunito",Font.BOLD,34));
        StorageLibrary.clickPowerField.setEditable(false);
        StorageLibrary.clickPowerField.setHorizontalAlignment(JTextField.CENTER);

        StorageLibrary.saveText.setVisible(false);
        StorageLibrary.saveText.setBounds(30, 200, 600, 80);
        StorageLibrary.saveText.setFocusable(false);
        StorageLibrary.saveText.setBackground(new Color(50,50,50));
        StorageLibrary.saveText.setForeground(Color.WHITE);
        StorageLibrary.saveText.setFont(new Font("Nunito",Font.BOLD,40));
        StorageLibrary.saveText.setBorder(null);
        StorageLibrary.saveText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.saveText.setText("Type Between 1-10 Characters");

        StorageLibrary.titleText.setVisible(true);
        StorageLibrary.titleText.setBounds(100, 20, 570, 80);
        StorageLibrary.titleText.setFocusable(false);
        StorageLibrary.titleText.setBackground(new Color(50,50,50));
        StorageLibrary.titleText.setForeground(Color.WHITE);
        StorageLibrary.titleText.setFont(new Font("Nunito",Font.BOLD,70));
        StorageLibrary.titleText.setBorder(null);
        StorageLibrary.titleText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.titleText.setText("Button Clicker");

        StorageLibrary.creditsText.setVisible(false);
        StorageLibrary.creditsText.setBounds(0, 130, 570, 450);
        StorageLibrary.creditsText.setFocusable(false);
        StorageLibrary.creditsText.setBackground(new Color(50,50,50));
        StorageLibrary.creditsText.setForeground(Color.WHITE);
        StorageLibrary.creditsText.setFont(new Font("Nunito",Font.BOLD,40));
        StorageLibrary.creditsText.setBorder(null);
        StorageLibrary.creditsText.setEditable(false);
        StorageLibrary.creditsText.setLineWrap(true);
        StorageLibrary.creditsText.addMouseListener(new InputService.mouseListener());
        StorageLibrary.creditsText.setText("""
                Art - Alek
                Programming - Alek
                Music - Davit
                Cool Theme - FlatLaf 3.4.1
                R/W JSON Files - Gson 2.11.0
                Logs - Log4j 2.23.1
                Audio - JavaFX Media 17.0.6
                Playing The Game - You!""");

        StorageLibrary.saveInfoText.setVisible(false);
        StorageLibrary.saveInfoText.setBounds(50, 160, 550, 400);
        StorageLibrary.saveInfoText.setFocusable(false);
        StorageLibrary.saveInfoText.setBackground(new Color(50,50,50));
        StorageLibrary.saveInfoText.setForeground(Color.WHITE);
        StorageLibrary.saveInfoText.setFont(new Font("Nunito",Font.BOLD,40));
        StorageLibrary.saveInfoText.setBorder(null);
        StorageLibrary.saveInfoText.setEditable(false);
        StorageLibrary.saveInfoText.setLineWrap(false);

        StorageLibrary.lightSideText.setVisible(false);
        StorageLibrary.lightSideText.setBounds(110, 250, 80, 40);
        StorageLibrary.lightSideText.setFocusable(false);
        StorageLibrary.lightSideText.setBackground(new Color(50,50,50));
        StorageLibrary.lightSideText.setForeground(Color.WHITE);
        StorageLibrary.lightSideText.setFont(new Font("Nunito",Font.BOLD,30));
        StorageLibrary.lightSideText.setBorder(null);
        StorageLibrary.lightSideText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.lightSideText.setText("Light");

        StorageLibrary.darkSideText.setVisible(false);
        StorageLibrary.darkSideText.setBounds(460, 250, 80, 40);
        StorageLibrary.darkSideText.setFocusable(false);
        StorageLibrary.darkSideText.setBackground(new Color(50,50,50));
        StorageLibrary.darkSideText.setForeground(Color.WHITE);
        StorageLibrary.darkSideText.setFont(new Font("Nunito",Font.BOLD,30));
        StorageLibrary.darkSideText.setBorder(null);
        StorageLibrary.darkSideText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.darkSideText.setText("Dark");

        StorageLibrary.magicSideText.setVisible(false);
        StorageLibrary.magicSideText.setBounds(110, 450, 90, 40);
        StorageLibrary.magicSideText.setFocusable(false);
        StorageLibrary.magicSideText.setBackground(new Color(50,50,50));
        StorageLibrary.magicSideText.setForeground(Color.WHITE);
        StorageLibrary.magicSideText.setFont(new Font("Nunito",Font.BOLD,30));
        StorageLibrary.magicSideText.setBorder(null);
        StorageLibrary.magicSideText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.magicSideText.setText("Magic");

        StorageLibrary.neutralSideText.setVisible(false);
        StorageLibrary.neutralSideText.setBounds(440, 450, 120, 40);
        StorageLibrary.neutralSideText.setFocusable(false);
        StorageLibrary.neutralSideText.setBackground(new Color(50,50,50));
        StorageLibrary.neutralSideText.setForeground(Color.WHITE);
        StorageLibrary.neutralSideText.setFont(new Font("Nunito",Font.BOLD,30));
        StorageLibrary.neutralSideText.setBorder(null);
        StorageLibrary.neutralSideText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.neutralSideText.setText("Neutral");

        StorageLibrary.masterVolumeSlider.setBounds(70, 310, 500, 20);
        StorageLibrary.masterVolumeSlider.setVisible(false);
        StorageLibrary.masterVolumeSlider.addChangeListener(e -> {
            JSONUtility.writeAIntToJsonFile("Data/settings.json", "master_volume", StorageLibrary.masterVolumeSlider.getValue());
            AudioUtility.SoundManager.setMasterVolume(StorageLibrary.masterVolumeSlider.getValue());
        });

        StorageLibrary.masterVolumeText.setVisible(false);
        StorageLibrary.masterVolumeText.setBounds(180, 260, 300, 50);
        StorageLibrary.masterVolumeText.setFocusable(false);
        StorageLibrary.masterVolumeText.setBackground(new Color(50,50,50));
        StorageLibrary.masterVolumeText.setForeground(Color.WHITE);
        StorageLibrary.masterVolumeText.setFont(new Font("Nunito",Font.BOLD,40));
        StorageLibrary.masterVolumeText.setBorder(null);
        StorageLibrary.masterVolumeText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.masterVolumeText.setText("Master Volume");

        StorageLibrary.musicVolumeSlider.setBounds(70, 420, 250, 20);
        StorageLibrary.musicVolumeSlider.setVisible(false);
        StorageLibrary.musicVolumeSlider.addChangeListener(e -> {
            JSONUtility.writeAIntToJsonFile("Data/settings.json", "music_volume", StorageLibrary.musicVolumeSlider.getValue());
            AudioUtility.SoundManager.refreshMusicVolume();
        });

        StorageLibrary.musicVolumeText.setVisible(false);
        StorageLibrary.musicVolumeText.setBounds(90, 370, 210, 50);
        StorageLibrary.musicVolumeText.setFocusable(false);
        StorageLibrary.musicVolumeText.setBackground(new Color(50,50,50));
        StorageLibrary.musicVolumeText.setForeground(Color.WHITE);
        StorageLibrary.musicVolumeText.setFont(new Font("Nunito",Font.BOLD,30));
        StorageLibrary.musicVolumeText.setBorder(null);
        StorageLibrary.musicVolumeText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.musicVolumeText.setText("Music Volume");

        StorageLibrary.sfxVolumeSlider.setBounds(320, 420, 250, 20);
        StorageLibrary.sfxVolumeSlider.setVisible(false);
        StorageLibrary.sfxVolumeSlider.addChangeListener(e -> {
            JSONUtility.writeAIntToJsonFile("Data/settings.json", "sfx_volume", StorageLibrary.sfxVolumeSlider.getValue());
            AudioUtility.SoundManager.refreshSFXVolume();
        });

        StorageLibrary.sfxVolumeText.setVisible(false);
        StorageLibrary.sfxVolumeText.setBounds(355, 370, 180, 50);
        StorageLibrary.sfxVolumeText.setFocusable(false);
        StorageLibrary.sfxVolumeText.setBackground(new Color(50,50,50));
        StorageLibrary.sfxVolumeText.setForeground(Color.WHITE);
        StorageLibrary.sfxVolumeText.setFont(new Font("Nunito",Font.BOLD,30));
        StorageLibrary.sfxVolumeText.setBorder(null);
        StorageLibrary.sfxVolumeText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.sfxVolumeText.setText("SFX Volume");

        StorageLibrary.buttonClickerVersionText.setVisible(false);
        StorageLibrary.buttonClickerVersionText.setBounds(0, 580, 345, 30);
        StorageLibrary.buttonClickerVersionText.setFocusable(false);
        StorageLibrary.buttonClickerVersionText.setBackground(new Color(50,50,50));
        StorageLibrary.buttonClickerVersionText.setForeground(Color.WHITE);
        StorageLibrary.buttonClickerVersionText.setFont(new Font("Nunito",Font.BOLD,20));
        StorageLibrary.buttonClickerVersionText.setBorder(null);
        StorageLibrary.buttonClickerVersionText.setText("Button Clicker "+StorageLibrary.VERSION);

        StorageLibrary.currentSaveText.setVisible(false);
        StorageLibrary.currentSaveText.setBounds(0, 610, 345, 30);
        StorageLibrary.currentSaveText.setFocusable(false);
        StorageLibrary.currentSaveText.setBackground(new Color(50,50,50));
        StorageLibrary.currentSaveText.setForeground(Color.WHITE);
        StorageLibrary.currentSaveText.setFont(new Font("Nunito",Font.BOLD,20));
        StorageLibrary.currentSaveText.setBorder(null);

        StorageLibrary.musicDelayText.setVisible(false);
        StorageLibrary.musicDelayText.setBounds(350, 455, 180, 40);
        StorageLibrary.musicDelayText.setFocusable(false);
        StorageLibrary.musicDelayText.setBackground(new Color(50,50,50));
        StorageLibrary.musicDelayText.setForeground(Color.WHITE);
        StorageLibrary.musicDelayText.setFont(new Font("Nunito",Font.BOLD,30));
        StorageLibrary.musicDelayText.setBorder(null);
        StorageLibrary.musicDelayText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.musicDelayText.setText("Music Delay");

        StorageLibrary.tipsText.setVisible(false);
        StorageLibrary.tipsText.setBounds(100, 150, 400, 80);
        StorageLibrary.tipsText.setFocusable(false);
        StorageLibrary.tipsText.setBackground(null);
        StorageLibrary.tipsText.setForeground(Color.WHITE);
        StorageLibrary.tipsText.setFont(new Font("Nunito",Font.BOLD,40));
        StorageLibrary.tipsText.setBorder(null);
        StorageLibrary.tipsText.setAlignmentX((float) StorageLibrary.tipsText.getWidth() /2);
        StorageLibrary.tipsText.setEditable(false);
        StorageLibrary.tipsText.setLineWrap(true);

        StorageLibrary.progressText.setVisible(false);
        StorageLibrary.progressText.setBounds(47, 300, 540, 40);
        StorageLibrary.progressText.setFocusable(false);
        StorageLibrary.progressText.setBackground(new Color(50,50,50));
        StorageLibrary.progressText.setForeground(Color.WHITE);
        StorageLibrary.progressText.setFont(new Font("Nunito",Font.BOLD,30));
        StorageLibrary.progressText.setBorder(null);
        StorageLibrary.progressText.setHorizontalAlignment(JTextField.CENTER);

        StorageLibrary.autoSavingText.setVisible(false);
        StorageLibrary.autoSavingText.setBounds(535,210,100,50);
        StorageLibrary.autoSavingText.setFocusable(false);
        StorageLibrary.autoSavingText.setBackground(new Color(40,40,40));
        StorageLibrary.autoSavingText.setForeground(Color.WHITE);
        StorageLibrary.autoSavingText.setFont(new Font("Nunito",Font.BOLD,24));
        StorageLibrary.autoSavingText.setBorder(null);
        StorageLibrary.autoSavingText.setHorizontalAlignment(JTextField.CENTER);
        StorageLibrary.autoSavingText.setText("Saving...");

        StorageLibrary.musicDelaySpinner.setVisible(false);
        StorageLibrary.musicDelaySpinner.setBounds(350, 519, 180, 40);
        StorageLibrary.musicDelaySpinner.setFocusable(true);
        StorageLibrary.musicDelaySpinner.setBackground(new Color(40,40,40));
        StorageLibrary.musicDelaySpinner.setForeground(Color.WHITE);
        StorageLibrary.musicDelaySpinner.setFont(new Font("Nunito",Font.BOLD,30));
        StorageLibrary.musicDelaySpinner.addChangeListener(e -> {
            JSONUtility.writeAIntToJsonFile("Data/settings.json", "music_delay", (Integer) StorageLibrary.musicDelaySpinner.getValue());
        });
        JComponent musicTimerSpinnerEditor = StorageLibrary.musicDelaySpinner.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) musicTimerSpinnerEditor;
        StorageLibrary.musicDelaySpinnerText = spinnerEditor.getTextField();

        StorageLibrary.musicDelaySpinnerText.setEditable(false);
        StorageLibrary.musicDelaySpinnerText.setFocusable(false);
        StorageLibrary.musicDelaySpinnerText.setFormatterFactory(new DefaultFormatterFactory(new DefaultFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text.endsWith(" Secs")) {
                    text = text.substring(0, text.length() - 5).trim();
                }
                return Integer.parseInt(text);
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                return value + " Secs";
            }
        }));
        StorageLibrary.musicDelaySpinner.setEditor(musicTimerSpinnerEditor);

        StorageLibrary.loadingBar.setVisible(false);
        StorageLibrary.loadingBar.setBounds(47, 350, 553, 40);
        StorageLibrary.loadingBar.setValue(0);
        StorageLibrary.loadingBar.setStringPainted(true);
        StorageLibrary.loadingBar.addChangeListener(e -> {
            if(StorageLibrary.loadingBar.getValue()==100){
                ATimer timer = new ATimer();
                timer.setDelay(1);
                timer.setTask(() -> {
                    MenuManager.closeMenu("Loading");

                    StorageLibrary.titleText.setBounds(100, 20, 570, 80);
                    StorageLibrary.titleText.setVisible(false);

                    StorageLibrary.titleImage.stopSpinning();
                    StorageLibrary.titleImage.setBounds(10,0,100,100);
                    StorageLibrary.titleImage.setVisible(false);

                    if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                        StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks1);
                        StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower1);
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                        StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks2);
                        StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower2);
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                        StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks3);
                        StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower3);
                    }

                    MenuManager.openMenu("Game");
                });
                timer.start();
            }
        });

        StorageLibrary.shopItem1.setVisible(false);
        StorageLibrary.shopItem1.setBounds(90, 180, 200, 100);
        StorageLibrary.shopItem1.addMouseListener(new InputService.mouseListener());

        StorageLibrary.shopItem2.setVisible(false);
        StorageLibrary.shopItem2.setBounds(357, 180, 200, 100);
        StorageLibrary.shopItem2.addMouseListener(new InputService.mouseListener());

        StorageLibrary.shopItem3.setVisible(false);
        StorageLibrary.shopItem3.setBounds(90, 320, 200, 100);
        StorageLibrary.shopItem3.addMouseListener(new InputService.mouseListener());

        StorageLibrary.shopItem4.setVisible(false);
        StorageLibrary.shopItem4.setBounds(357, 320, 200, 100);
        StorageLibrary.shopItem4.addMouseListener(new InputService.mouseListener());

        StorageLibrary.shopItem5.setVisible(false);
        StorageLibrary.shopItem5.setBounds(90, 460, 200, 100);
        StorageLibrary.shopItem5.addMouseListener(new InputService.mouseListener());

        StorageLibrary.shopItem6.setVisible(false);
        StorageLibrary.shopItem6.setBounds(357, 460, 200, 100);
        StorageLibrary.shopItem6.addMouseListener(new InputService.mouseListener());

        StorageLibrary.startGameButton.setVisible(false);
        StorageLibrary.startGameButton.setBounds(177,150,305,102);
        StorageLibrary.startGameButton.setForeground(Color.WHITE);
        StorageLibrary.startGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.startGameButton.setBorderPainted(false);
        StorageLibrary.startGameButton.setRolloverEnabled(false);
        StorageLibrary.startGameButton.setFocusPainted(false);
        StorageLibrary.startGameButton.setFocusable(false);
        StorageLibrary.startGameButton.setOpaque(false);
        StorageLibrary.startGameButton.addActionListener(InputService.actionListener);

        StorageLibrary.saveManagerButton.setVisible(false);
        StorageLibrary.saveManagerButton.setBounds(177,249,305,102);
        StorageLibrary.saveManagerButton.setText("Save Manager");
        StorageLibrary.saveManagerButton.setFont(new Font("Nunito", Font.BOLD, 28));
        StorageLibrary.saveManagerButton.setForeground(Color.WHITE);
        StorageLibrary.saveManagerButton.setBackground(Color.CYAN);
        StorageLibrary.saveManagerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.saveManagerButton.setBorderPainted(false);
        StorageLibrary.saveManagerButton.setRolloverEnabled(false);
        StorageLibrary.saveManagerButton.setFocusPainted(false);
        StorageLibrary.saveManagerButton.setFocusable(false);
        StorageLibrary.saveManagerButton.setOpaque(false);
        StorageLibrary.saveManagerButton.addActionListener(InputService.actionListener);

        StorageLibrary.wikiButton.setVisible(false);
        StorageLibrary.wikiButton.setBounds(73,150,250,100);
        StorageLibrary.wikiButton.setText("Wiki");
        StorageLibrary.wikiButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.wikiButton.setForeground(Color.WHITE);
        StorageLibrary.wikiButton.setBackground(Color.ORANGE);
        StorageLibrary.wikiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.wikiButton.setBorderPainted(false);
        StorageLibrary.wikiButton.setRolloverEnabled(false);
        StorageLibrary.wikiButton.setFocusPainted(false);
        StorageLibrary.wikiButton.setFocusable(false);
        StorageLibrary.wikiButton.setOpaque(false);
        StorageLibrary.wikiButton.addActionListener(InputService.actionListener);

        StorageLibrary.creditsButton.setVisible(false);
        StorageLibrary.creditsButton.setBounds(320,150,250,100);
        StorageLibrary.creditsButton.setText("Credits");
        StorageLibrary.creditsButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.creditsButton.setForeground(Color.WHITE);
        StorageLibrary.creditsButton.setBackground(Color.YELLOW);
        StorageLibrary.creditsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.creditsButton.setBorderPainted(false);
        StorageLibrary.creditsButton.setRolloverEnabled(false);
        StorageLibrary.creditsButton.setFocusPainted(false);
        StorageLibrary.creditsButton.setFocusable(false);
        StorageLibrary.creditsButton.setOpaque(false);
        StorageLibrary.creditsButton.addActionListener(InputService.actionListener);

        StorageLibrary.quitButton.setVisible(false);
        StorageLibrary.quitButton.setBounds(177,447,305,102);
        StorageLibrary.quitButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.quitButton.setForeground(Color.WHITE);
        StorageLibrary.quitButton.setBackground(Color.RED);
        StorageLibrary.quitButton.setMargin(new Insets(0, 0, 0, 0));
        StorageLibrary.quitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.quitButton.setBorderPainted(false);
        StorageLibrary.quitButton.setRolloverEnabled(false);
        StorageLibrary.quitButton.setFocusPainted(false);
        StorageLibrary.quitButton.setFocusable(false);
        StorageLibrary.quitButton.setOpaque(false);
        StorageLibrary.quitButton.addActionListener(InputService.actionListener);

        StorageLibrary.backToButton.setVisible(false);
        StorageLibrary.backToButton.setBounds(-10, 600, 667, 38);
        StorageLibrary.backToButton.setText("Back To Main Menu");
        StorageLibrary.backToButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.backToButton.setForeground(Color.WHITE);
        StorageLibrary.backToButton.setBackground(Color.BLACK);
        StorageLibrary.backToButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.backToButton.setBorderPainted(false);
        StorageLibrary.backToButton.setRolloverEnabled(false);
        StorageLibrary.backToButton.setFocusPainted(false);
        StorageLibrary.backToButton.setFocusable(false);
        StorageLibrary.backToButton.setOpaque(false);
        StorageLibrary.backToButton.addActionListener(InputService.actionListener);

        StorageLibrary.save1Button.setVisible(false);
        StorageLibrary.save1Button.setBounds(28, 122, 200, 350);
        StorageLibrary.save1Button.setText("Save 1 Empty");
        StorageLibrary.save1Button.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.save1Button.setForeground(Color.WHITE);
        StorageLibrary.save1Button.setBackground(Color.GRAY);
        StorageLibrary.save1Button.setMargin(new Insets(0, 0, 0, 0));
        StorageLibrary.save1Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.save1Button.setBorderPainted(false);
        StorageLibrary.save1Button.setRolloverEnabled(false);
        StorageLibrary.save1Button.setFocusPainted(false);
        StorageLibrary.save1Button.setFocusable(false);
        StorageLibrary.save1Button.setOpaque(false);
        StorageLibrary.save1Button.addActionListener(InputService.actionListener);

        StorageLibrary.save2Button.setVisible(false);
        StorageLibrary.save2Button.setBounds(225, 122, 200, 350);
        StorageLibrary.save2Button.setText("Save 2 Empty");
        StorageLibrary.save2Button.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.save2Button.setForeground(Color.WHITE);
        StorageLibrary.save2Button.setBackground(Color.GRAY);
        StorageLibrary.save2Button.setMargin(new Insets(0, 0, 0, 0));
        StorageLibrary.save2Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.save2Button.setBorderPainted(false);
        StorageLibrary.save2Button.setRolloverEnabled(false);
        StorageLibrary.save2Button.setFocusPainted(false);
        StorageLibrary.save2Button.setFocusable(false);
        StorageLibrary.save2Button.setOpaque(false);
        StorageLibrary.save2Button.addActionListener(InputService.actionListener);

        StorageLibrary.save3Button.setVisible(false);
        StorageLibrary.save3Button.setBounds(422, 122, 200, 350);
        StorageLibrary.save3Button.setText("Save 3 Empty");
        StorageLibrary.save3Button.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.save3Button.setForeground(Color.WHITE);
        StorageLibrary.save3Button.setBackground(Color.GRAY);
        StorageLibrary.save3Button.setMargin(new Insets(0, 0, 0, 0));
        StorageLibrary.save3Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.save3Button.setBorderPainted(false);
        StorageLibrary.save3Button.setRolloverEnabled(false);
        StorageLibrary.save3Button.setFocusPainted(false);
        StorageLibrary.save3Button.setFocusable(false);
        StorageLibrary.save3Button.setOpaque(false);
        StorageLibrary.save3Button.addActionListener(InputService.actionListener);

        StorageLibrary.lightSideButton.setVisible(false);
        StorageLibrary.lightSideButton.setBounds(100,150,100,100);
        StorageLibrary.lightSideButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.lightSideButton.setForeground(Color.WHITE);
        StorageLibrary.lightSideButton.setBackground(new Color(50, 50, 50));
        StorageLibrary.lightSideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.lightSideButton.setBorderPainted(false);
        StorageLibrary.lightSideButton.setRolloverEnabled(false);
        StorageLibrary.lightSideButton.setFocusPainted(false);
        StorageLibrary.lightSideButton.setFocusable(false);
        StorageLibrary.lightSideButton.setOpaque(false);
        StorageLibrary.lightSideButton.addActionListener(InputService.actionListener);
        StorageLibrary.lightSideButton.setIcon(StorageLibrary.lightSideIcon);

        StorageLibrary.darkSideButton.setVisible(false);
        StorageLibrary.darkSideButton.setBounds(450,150,100,100);
        StorageLibrary.darkSideButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.darkSideButton.setForeground(Color.WHITE);
        StorageLibrary.darkSideButton.setBackground(new Color(50, 50, 50));
        StorageLibrary.darkSideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.darkSideButton.setBorderPainted(false);
        StorageLibrary.darkSideButton.setRolloverEnabled(false);
        StorageLibrary.darkSideButton.setFocusPainted(false);
        StorageLibrary.darkSideButton.setFocusable(false);
        StorageLibrary.darkSideButton.setOpaque(false);
        StorageLibrary.darkSideButton.addActionListener(InputService.actionListener);
        StorageLibrary.darkSideButton.setIcon(StorageLibrary.darkSideIcon);

        StorageLibrary.magicSideButton.setVisible(false);
        StorageLibrary.magicSideButton.setBounds(100,350,100,100);
        StorageLibrary.magicSideButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.magicSideButton.setForeground(Color.WHITE);
        StorageLibrary.magicSideButton.setBackground(new Color(50, 50, 50));
        StorageLibrary.magicSideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.magicSideButton.setBorderPainted(false);
        StorageLibrary.magicSideButton.setRolloverEnabled(false);
        StorageLibrary.magicSideButton.setFocusPainted(false);
        StorageLibrary.magicSideButton.setFocusable(false);
        StorageLibrary.magicSideButton.setOpaque(false);
        StorageLibrary.magicSideButton.addActionListener(InputService.actionListener);
        StorageLibrary.magicSideButton.setIcon(StorageLibrary.magicSideIcon);

        StorageLibrary.neutralSideButton.setVisible(false);
        StorageLibrary.neutralSideButton.setBounds(450,350,100,100);
        StorageLibrary.neutralSideButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.neutralSideButton.setForeground(Color.WHITE);
        StorageLibrary.neutralSideButton.setBackground(new Color(50, 50, 50));
        StorageLibrary.neutralSideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.neutralSideButton.setBorderPainted(false);
        StorageLibrary.neutralSideButton.setRolloverEnabled(false);
        StorageLibrary.neutralSideButton.setFocusPainted(false);
        StorageLibrary.neutralSideButton.setFocusable(false);
        StorageLibrary.neutralSideButton.setOpaque(false);
        StorageLibrary.neutralSideButton.addActionListener(InputService.actionListener);
        StorageLibrary.neutralSideButton.setIcon(StorageLibrary.neutralSideIcon);

        StorageLibrary.deleteSave1Button.setVisible(false);
        StorageLibrary.deleteSave1Button.setBounds(28, 469, 200, 80);
        StorageLibrary.deleteSave1Button.setText("Delete");
        StorageLibrary.deleteSave1Button.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.deleteSave1Button.setForeground(Color.WHITE);
        StorageLibrary.deleteSave1Button.setBackground(Color.GRAY);
        StorageLibrary.deleteSave1Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.deleteSave1Button.setBorderPainted(false);
        StorageLibrary.deleteSave1Button.setRolloverEnabled(false);
        StorageLibrary.deleteSave1Button.setFocusPainted(false);
        StorageLibrary.deleteSave1Button.setFocusable(false);
        StorageLibrary.deleteSave1Button.setOpaque(false);
        StorageLibrary.deleteSave1Button.addActionListener(InputService.actionListener);

        StorageLibrary.deleteSave2Button.setVisible(false);
        StorageLibrary.deleteSave2Button.setBounds(225, 469, 200, 80);
        StorageLibrary.deleteSave2Button.setText("Delete");
        StorageLibrary.deleteSave2Button.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.deleteSave2Button.setForeground(Color.WHITE);
        StorageLibrary.deleteSave2Button.setBackground(Color.GRAY);
        StorageLibrary.deleteSave2Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.deleteSave2Button.setBorderPainted(false);
        StorageLibrary.deleteSave2Button.setRolloverEnabled(false);
        StorageLibrary.deleteSave2Button.setFocusPainted(false);
        StorageLibrary.deleteSave2Button.setFocusable(false);
        StorageLibrary.deleteSave2Button.setOpaque(false);
        StorageLibrary.deleteSave2Button.addActionListener(InputService.actionListener);

        StorageLibrary.deleteSave3Button.setVisible(false);
        StorageLibrary.deleteSave3Button.setBounds(422, 469, 200, 80);
        StorageLibrary.deleteSave3Button.setText("Delete");
        StorageLibrary.deleteSave3Button.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.deleteSave3Button.setForeground(Color.WHITE);
        StorageLibrary.deleteSave3Button.setBackground(Color.GRAY);
        StorageLibrary.deleteSave3Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.deleteSave3Button.setBorderPainted(false);
        StorageLibrary.deleteSave3Button.setRolloverEnabled(false);
        StorageLibrary.deleteSave3Button.setFocusPainted(false);
        StorageLibrary.deleteSave3Button.setFocusable(false);
        StorageLibrary.deleteSave3Button.setOpaque(false);
        StorageLibrary.deleteSave3Button.addActionListener(InputService.actionListener);

        StorageLibrary.settingsButton.setVisible(false);
        StorageLibrary.settingsButton.setBounds(177,348,305,102);
        StorageLibrary.settingsButton.setText("Settings");
        StorageLibrary.settingsButton.setFont(new Font("Nunito", Font.BOLD, 28));
        StorageLibrary.settingsButton.setForeground(Color.WHITE);
        StorageLibrary.settingsButton.setBackground(Color.ORANGE);
        StorageLibrary.settingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.settingsButton.setBorderPainted(false);
        StorageLibrary.settingsButton.setRolloverEnabled(false);
        StorageLibrary.settingsButton.setFocusPainted(false);
        StorageLibrary.settingsButton.setFocusable(false);
        StorageLibrary.settingsButton.setOpaque(false);
        StorageLibrary.settingsButton.addActionListener(InputService.actionListener);

        StorageLibrary.bugReportButton.setVisible(false);
        StorageLibrary.bugReportButton.setBounds(73, 460, 250, 100);
        StorageLibrary.bugReportButton.setText("Report A Bug!");
        StorageLibrary.bugReportButton.setFont(new Font("Nunito", Font.BOLD, 28));
        StorageLibrary.bugReportButton.setForeground(Color.WHITE);
        StorageLibrary.bugReportButton.setBackground(Color.BLUE);
        StorageLibrary.bugReportButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.bugReportButton.setBorderPainted(false);
        StorageLibrary.bugReportButton.setRolloverEnabled(false);
        StorageLibrary.bugReportButton.setFocusPainted(false);
        StorageLibrary.bugReportButton.setFocusable(false);
        StorageLibrary.bugReportButton.setOpaque(false);
        StorageLibrary.bugReportButton.addActionListener(InputService.actionListener);

        StorageLibrary.debugButton.setVisible(false);
        StorageLibrary.debugButton.setBounds(550, 538, 100, 100);
        StorageLibrary.debugButton.setText("Debug");
        StorageLibrary.debugButton.setFont(new Font("Nunito", Font.BOLD, 28));
        StorageLibrary.debugButton.setForeground(Color.WHITE);
        StorageLibrary.debugButton.setBackground(Color.BLUE);
        StorageLibrary.debugButton.setMargin(new Insets(0, 0, 0, 0));
        StorageLibrary.debugButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.debugButton.setBorderPainted(false);
        StorageLibrary.debugButton.setRolloverEnabled(false);
        StorageLibrary.debugButton.setFocusPainted(false);
        StorageLibrary.debugButton.setFocusable(false);
        StorageLibrary.debugButton.setOpaque(false);
        StorageLibrary.debugButton.addActionListener(InputService.actionListener);

        StorageLibrary.infoButton.setVisible(false);
        StorageLibrary.infoButton.setBounds(28, 546, 594, 50);
        StorageLibrary.infoButton.setText("Info");
        StorageLibrary.infoButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.infoButton.setForeground(Color.WHITE);
        StorageLibrary.infoButton.setBackground(Color.GRAY);
        StorageLibrary.infoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.infoButton.setBorderPainted(false);
        StorageLibrary.infoButton.setRolloverEnabled(false);
        StorageLibrary.infoButton.setFocusPainted(false);
        StorageLibrary.infoButton.setFocusable(false);
        StorageLibrary.infoButton.setOpaque(false);
        StorageLibrary.infoButton.addActionListener(InputService.actionListener);

        StorageLibrary.wikiHelpButton.setVisible(false);
        StorageLibrary.wikiHelpButton.setBounds(955, 0, 50, 50);
        StorageLibrary.wikiHelpButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.wikiHelpButton.setForeground(Color.WHITE);
        StorageLibrary.wikiHelpButton.setBackground(new Color(40, 40, 40));
        StorageLibrary.wikiHelpButton.setText("?");
        StorageLibrary.wikiHelpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.wikiHelpButton.setBorderPainted(false);
        StorageLibrary.wikiHelpButton.setRolloverEnabled(false);
        StorageLibrary.wikiHelpButton.setFocusPainted(false);
        StorageLibrary.wikiHelpButton.setFocusable(false);
        StorageLibrary.wikiHelpButton.setOpaque(false);
        StorageLibrary.wikiHelpButton.addActionListener(InputService.actionListener);

        StorageLibrary.backwardButton.setVisible(false);
        StorageLibrary.backwardButton.setBounds(-2, 180, 50, 370);
        StorageLibrary.backwardButton.setBackground(Color.WHITE);
        StorageLibrary.backwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.backwardButton.setBorderPainted(false);
        StorageLibrary.backwardButton.setRolloverEnabled(false);
        StorageLibrary.backwardButton.setFocusPainted(false);
        StorageLibrary.backwardButton.setFocusable(false);
        StorageLibrary.backwardButton.setOpaque(false);
        StorageLibrary.backwardButton.addActionListener(InputService.actionListener);
        StorageLibrary.backwardButton.setIcon(StorageLibrary.backwardArrowIcon);

        StorageLibrary.forwardButton.setVisible(false);
        StorageLibrary.forwardButton.setBounds(599, 180, 50, 370);
        StorageLibrary.forwardButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.forwardButton.setBackground(Color.WHITE);
        StorageLibrary.forwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.forwardButton.setBorderPainted(false);
        StorageLibrary.forwardButton.setRolloverEnabled(false);
        StorageLibrary.forwardButton.setFocusPainted(false);
        StorageLibrary.forwardButton.setFocusable(false);
        StorageLibrary.forwardButton.setOpaque(false);
        StorageLibrary.forwardButton.addActionListener(InputService.actionListener);
        StorageLibrary.forwardButton.setIcon(StorageLibrary.forwardArrowIcon);

        StorageLibrary.nextTipButton.setVisible(false);
        StorageLibrary.nextTipButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.nextTipButton.setBackground(new Color(40,40,40));
        StorageLibrary.nextTipButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.nextTipButton.setBorderPainted(false);
        StorageLibrary.nextTipButton.setRolloverEnabled(false);
        StorageLibrary.nextTipButton.setFocusPainted(false);
        StorageLibrary.nextTipButton.setFocusable(false);
        StorageLibrary.nextTipButton.setOpaque(false);
        StorageLibrary.nextTipButton.addActionListener(InputService.actionListener);
        StorageLibrary.nextTipButton.setText("Next Tip");

        StorageLibrary.shopButton.setVisible(false);
        StorageLibrary.shopButton.setBounds(-3, 568, 329, 70);
        StorageLibrary.shopButton.setText("Shop");
        StorageLibrary.shopButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.shopButton.setForeground(Color.WHITE);
        StorageLibrary.shopButton.setBackground(Color.ORANGE);
        StorageLibrary.shopButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.shopButton.setBorderPainted(false);
        StorageLibrary.shopButton.setRolloverEnabled(false);
        StorageLibrary.shopButton.setFocusPainted(false);
        StorageLibrary.shopButton.setFocusable(false);
        StorageLibrary.shopButton.setOpaque(false);
        StorageLibrary.shopButton.addActionListener(InputService.actionListener);

        StorageLibrary.pauseButton.setVisible(false);
        StorageLibrary.pauseButton.setBounds(323, 568, 327, 70);
        StorageLibrary.pauseButton.setText("Pause");
        StorageLibrary.pauseButton.setFont(new Font("Nunito", Font.BOLD, 30));
        StorageLibrary.pauseButton.setForeground(Color.WHITE);
        StorageLibrary.pauseButton.setBackground(Color.DARK_GRAY);
        StorageLibrary.pauseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        StorageLibrary.pauseButton.setBorderPainted(false);
        StorageLibrary.pauseButton.setRolloverEnabled(false);
        StorageLibrary.pauseButton.setFocusPainted(false);
        StorageLibrary.pauseButton.setFocusable(false);
        StorageLibrary.pauseButton.setOpaque(false);
        StorageLibrary.pauseButton.addActionListener(InputService.actionListener);

        new LoadService();

        StorageLibrary.frame.getContentPane().setBackground(new Color(50,50,50));
        StorageLibrary.frame.getRootPane().setBackground(Color.BLACK);
        StorageLibrary.frame.setSize(663, 675);
        StorageLibrary.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        StorageLibrary.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(!MenuManager.isMenuOpen("Loading")){
                    if(Objects.equals(StorageLibrary.currentSave, "null")){
                        StorageLibrary.logger.info("Quitting...");

                        System.exit(0);
                    }else if(!Objects.equals(StorageLibrary.currentSave, "null")){
                        StorageLibrary.logger.info("Quitting & Saving...");

                        MenuManager.closeMenu("Main");
                        MenuManager.closeMenu("Save Manager");
                        MenuManager.closeMenu("Choose A Side");
                        MenuManager.closeMenu("Enter A Save Name");
                        MenuManager.closeMenu("Save Info");
                        MenuManager.closeMenu("Settings");
                        MenuManager.closeMenu("Wiki");
                        MenuManager.closeMenu("Credits");
                        MenuManager.closeMenu("Resource Packs");
                        MenuManager.closeMenu("Loading");
                        MenuManager.closeMenu("Game");
                        MenuManager.closeMenu("Pause");
                        MenuManager.closeMenu("Shop");

                        StorageLibrary.backToButton.setVisible(false);
                        StorageLibrary.forwardButton.setVisible(false);
                        StorageLibrary.backwardButton.setVisible(false);
                        StorageLibrary.debugButton.setVisible(false);
                        StorageLibrary.titleText.setVisible(false);

                        StorageLibrary.titleImage.setVisible(true);
                        StorageLibrary.titleImage.startSpinning();
                        StorageLibrary.titleImage.setBounds(40,5,110,110);

                        StorageLibrary.autoSavingText.setVisible(true);
                        StorageLibrary.autoSavingText.setBounds(-50,110,300,100);
                        StorageLibrary.autoSavingText.setFont(new Font("Nunito",Font.BOLD,50));

                        StorageLibrary.frame.setSize(215, 230);
                        StorageLibrary.frame.setLocationRelativeTo(null);

                        if (Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")) {
                            SaveService.save1();
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")) {
                            SaveService.save2();
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")) {
                            SaveService.save3();
                        }

                        StorageLibrary.frame.removeWindowListener(this);

                        ATimer timer = new ATimer();
                        timer.setDelay(3);
                        timer.setTask(() -> {
                            System.exit(0);
                        });
                        timer.start();
                    }
                }
            }
        });
        StorageLibrary.frame.setLocationRelativeTo(null);
        StorageLibrary.frame.setLayout(null);
        StorageLibrary.frame.setVisible(true);
        StorageLibrary.frame.addKeyListener(new InputService.keyListener());
        StorageLibrary.frame.addMouseListener(new InputService.mouseListener());
        StorageLibrary.frame.setResizable(false);
        StorageLibrary.frame.setTitle("Button Clicker");
        StorageLibrary.frame.setIconImage(StorageLibrary.bcIcon3.getImage());

        StorageLibrary.frame.add(StorageLibrary.tipsPanel);

        StorageLibrary.frame.add(StorageLibrary.wikiHelpButton);
        StorageLibrary.frame.add(StorageLibrary.backToButton);

        StorageLibrary.frame.add(StorageLibrary.wikiScrollPane, BorderLayout.CENTER);

        StorageLibrary.frame.add(StorageLibrary.buttonLayeredPane);

        StorageLibrary.frame.add(StorageLibrary.titleImage);

        StorageLibrary.frame.add(StorageLibrary.titleText);
        StorageLibrary.frame.add(StorageLibrary.lightSideText);
        StorageLibrary.frame.add(StorageLibrary.darkSideText);
        StorageLibrary.frame.add(StorageLibrary.magicSideText);
        StorageLibrary.frame.add(StorageLibrary.neutralSideText);
        StorageLibrary.frame.add(StorageLibrary.creditsText);
        StorageLibrary.frame.add(StorageLibrary.saveNameField);
        StorageLibrary.frame.add(StorageLibrary.clicksField);
        StorageLibrary.frame.add(StorageLibrary.clickPowerField);
        StorageLibrary.frame.add(StorageLibrary.saveText);
        StorageLibrary.frame.add(StorageLibrary.masterVolumeSlider);
        StorageLibrary.frame.add(StorageLibrary.masterVolumeText);
        StorageLibrary.frame.add(StorageLibrary.musicVolumeSlider);
        StorageLibrary.frame.add(StorageLibrary.musicVolumeText);
        StorageLibrary.frame.add(StorageLibrary.sfxVolumeSlider);
        StorageLibrary.frame.add(StorageLibrary.sfxVolumeText);
        StorageLibrary.frame.add(StorageLibrary.buttonClickerVersionText);
        StorageLibrary.frame.add(StorageLibrary.currentSaveText);
        StorageLibrary.frame.add(StorageLibrary.musicDelayText);
        StorageLibrary.frame.add(StorageLibrary.saveInfoText);
        StorageLibrary.frame.add(StorageLibrary.progressText);
        StorageLibrary.frame.add(StorageLibrary.autoSavingText);

        StorageLibrary.frame.add(StorageLibrary.musicDelaySpinner);

        StorageLibrary.frame.add(StorageLibrary.loadingBar);

        StorageLibrary.frame.add(StorageLibrary.shopItem1);
        StorageLibrary.frame.add(StorageLibrary.shopItem2);
        StorageLibrary.frame.add(StorageLibrary.shopItem3);
        StorageLibrary.frame.add(StorageLibrary.shopItem4);
        StorageLibrary.frame.add(StorageLibrary.shopItem5);
        StorageLibrary.frame.add(StorageLibrary.shopItem6);

        StorageLibrary.frame.add(StorageLibrary.startGameButton);
        StorageLibrary.frame.add(StorageLibrary.saveManagerButton);
        StorageLibrary.frame.add(StorageLibrary.wikiButton);
        StorageLibrary.frame.add(StorageLibrary.creditsButton);
        StorageLibrary.frame.add(StorageLibrary.quitButton);
        StorageLibrary.frame.add(StorageLibrary.save1Button);
        StorageLibrary.frame.add(StorageLibrary.save2Button);
        StorageLibrary.frame.add(StorageLibrary.save3Button);
        StorageLibrary.frame.add(StorageLibrary.lightSideButton);
        StorageLibrary.frame.add(StorageLibrary.darkSideButton);
        StorageLibrary.frame.add(StorageLibrary.magicSideButton);
        StorageLibrary.frame.add(StorageLibrary.neutralSideButton);
        StorageLibrary.frame.add(StorageLibrary.deleteSave1Button);
        StorageLibrary.frame.add(StorageLibrary.deleteSave2Button);
        StorageLibrary.frame.add(StorageLibrary.deleteSave3Button);
        StorageLibrary.frame.add(StorageLibrary.settingsButton);
        StorageLibrary.frame.add(StorageLibrary.bugReportButton);
        StorageLibrary.frame.add(StorageLibrary.debugButton);
        StorageLibrary.frame.add(StorageLibrary.infoButton);
        StorageLibrary.frame.add(StorageLibrary.backwardButton);
        StorageLibrary.frame.add(StorageLibrary.forwardButton);
        StorageLibrary.frame.add(StorageLibrary.shopButton);
        StorageLibrary.frame.add(StorageLibrary.pauseButton);

        StorageLibrary.logger.info("Render Pipeline Initialized!");

        StorageLibrary.logger.info("Opening Main Menu...");
        MenuManager.openMenu("Main");
        StorageLibrary.logger.info("Done!");
    }

    public static void startProgress(JProgressBar progressBar, int delay) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                for (int i = progressBar.getMinimum(); i <= progressBar.getMaximum(); i++) {
                    Thread.sleep(delay);
                    progressBar.setValue(i);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                } catch (ExecutionException e) {
                    ErrorHandler.ExecutionException("RENDER", 1066);
                } catch (InterruptedException e) {
                    ErrorHandler.InterruptedException("RENDER", 1068);
                }
            }
        };
        worker.execute();
    }

    public static void textFieldLimit(JTextField textField, int maxCharacters) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() + string.trim().length()) <= maxCharacters) {
                    super.insertString(fb, offset, string.trim(), attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() - length + text.trim().length()) <= maxCharacters) {
                    super.replace(fb, offset, length, text.trim(), attrs);
                }
            }
        });
    }

    public static boolean isTextFieldEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    public static void setResetWindowTrigger(){
        StorageLibrary.resetWindow = true;
    }

    public static void resetWindow(){
        if(StorageLibrary.resetWindow){
            StorageLibrary.frame.setSize(663, 675);
            StorageLibrary.frame.setLocationRelativeTo(null);

            StorageLibrary.resetWindow = false;
        }
    }
}