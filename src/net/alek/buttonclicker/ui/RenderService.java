package net.alek.buttonclicker.ui;

import com.formdev.flatlaf.FlatClientProperties;

import net.alek.buttonclicker.core.Spark;
import net.alek.buttonclicker.ui.components.*;
import net.alek.buttonclicker.core.ErrorHandler;
import net.alek.buttonclicker.services.InputService;
import net.alek.buttonclicker.core.log.Logger;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RenderService {
    public static GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

    public static JFrame frame = new JFrame();

    public static JLayeredPane contentPane = new JLayeredPane();

    public static JPanel tipsPanel = new JPanel(new BorderLayout());

    public static AEditorPane wikiEditorPane = new AEditorPane();

    public static JScrollPane wikiScrollPane = new JScrollPane(wikiEditorPane);

    public static JLayeredPane buttonLayeredPane = new JLayeredPane();

    public static JButton startGameButton = new JButton();
    public static JButton saveManagerButton = new JButton();
    public static JButton wikiButton = new JButton();
    public static JButton creditsButton = new JButton();
    public static JButton quitButton = new JButton();
    public static JButton backToButton = new JButton();
    public static JButton save1Button = new JButton();
    public static JButton save2Button = new JButton();
    public static JButton save3Button = new JButton();
    public static JButton lightSideButton = new JButton();
    public static JButton darkSideButton = new JButton();
    public static JButton magicSideButton = new JButton();
    public static JButton neutralSideButton = new JButton();
    public static JButton deleteSave1Button = new JButton();
    public static JButton deleteSave2Button = new JButton();
    public static JButton deleteSave3Button = new JButton();
    public static JButton settingsButton = new JButton();
    public static JButton bugReportButton = new JButton();
    public static JButton debugButton = new JButton();
    public static JButton infoButton = new JButton();
    public static JButton wikiHelpButton = new JButton();
    public static JButton backwardButton = new JButton();
    public static JButton forwardButton = new JButton();
    public static JButton nextTipButton = new JButton();
    public static JButton shopButton = new JButton();
    public static JButton pauseButton = new JButton();
    public static JButton showConsoleButton = new JButton();
    public static JButton crashButton = new JButton();
    public static JButton consoleSettingsButton = new JButton();

    public static JTextField saveNameField = new JTextField();
    public static JTextField clicksField = new JTextField();
    public static JTextField clickPowerField = new JTextField();
    public static JTextField commandBar = new JTextField();

    public static ATextArea saveInfoText = new ATextArea();
    public static JTextArea creditsText = new JTextArea();
    public static JTextArea tipsText = new JTextArea();
    public static ConsoleTextArea console = new ConsoleTextArea();
    public static JTextArea crashText = new JTextArea();

    public static JScrollPane consoleScrollPane = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    public static JLabel saveText = new JLabel();
    public static JLabel titleText = new JLabel();
    public static JLabel lightSideText = new JLabel();
    public static JLabel darkSideText = new JLabel();
    public static JLabel magicSideText = new JLabel();
    public static JLabel neutralSideText = new JLabel();
    public static JLabel masterVolumeText = new JLabel();
    public static JLabel sfxVolumeText = new JLabel();
    public static JLabel musicVolumeText = new JLabel();
    public static JLabel buttonClickerVersionText = new JLabel();
    public static JLabel currentSaveText = new JLabel();
    public static JLabel musicDelayText = new JLabel();
    public static JLabel progressText = new JLabel();
    public static JLabel autoSavingText = new JLabel();
    public static JLabel buttonBase = new JLabel();
    public static JLabel buttonTop = new JLabel();
    public static ALabel titleImage = new ALabel();

    public static JSlider masterVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
    public static JSlider sfxVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
    public static JSlider musicVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);

    public static JSpinner musicDelaySpinner = new JSpinner(new SpinnerNumberModel(20, 0, 60, 1));
    public static JFormattedTextField musicDelaySpinnerText = new JFormattedTextField("0 Secs");

    public static JProgressBar loadingBar = new JProgressBar(0,100);

    public static ShopItem shopItem1 = new ShopItem();
    public static ShopItem shopItem2 = new ShopItem();
    public static ShopItem shopItem3 = new ShopItem();
    public static ShopItem shopItem4 = new ShopItem();
    public static ShopItem shopItem5 = new ShopItem();
    public static ShopItem shopItem6 = new ShopItem();
    
    public RenderService(){

        contentPane.setVisible(true);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(50,50,50));
        contentPane.setBounds(0,0,1263,675);

        tipsPanel.setVisible(false);
        tipsPanel.setBackground(new Color(40,40,40));
        tipsPanel.setBounds(24,435,600,200);
        tipsPanel.setBorder(null);
        tipsPanel.putClientProperty(FlatClientProperties.STYLE_CLASS, "roundPanel");
        tipsPanel.add(tipsText, BorderLayout.CENTER);
        tipsPanel.add(nextTipButton, BorderLayout.PAGE_END);

        wikiScrollPane.createVerticalScrollBar();
        wikiScrollPane.setVisible(false);
        wikiScrollPane.setBounds(0,0,1015,595);
        wikiScrollPane.getViewport().setBackground(new Color(50, 50, 50));
        wikiScrollPane.setBorder(null);
        wikiScrollPane.setFocusable(false);

        consoleScrollPane.createVerticalScrollBar();
        consoleScrollPane.setVisible(false);
        consoleScrollPane.setBounds(647, 0, 600, 587);
        consoleScrollPane.getViewport().setBackground(Color.BLACK);
        consoleScrollPane.setBorder(null);
        consoleScrollPane.setFocusable(false);

        buttonLayeredPane.setVisible(false);
        buttonLayeredPane.setBounds(195,200,300,300);
        buttonLayeredPane.add(buttonBase, JLayeredPane.DEFAULT_LAYER);
        buttonLayeredPane.add(buttonTop, JLayeredPane.PALETTE_LAYER);

        wikiEditorPane.setEditable(false);
        wikiEditorPane.setVisible(false);
        wikiEditorPane.setOpaque(false);
        wikiEditorPane.setFocusable(false);
        wikiEditorPane.setBackgroundImageEnabled(false);
        wikiEditorPane.setBackgroundImage(ReadUtility.portalWhitewall);
        wikiEditorPane.setBackground(new Color(50, 50, 50));
        wikiEditorPane.setForeground(Color.WHITE);
        wikiEditorPane.setFont(new Font("Nunito",Font.BOLD,20));
        wikiEditorPane.setCaret(new Caret() {
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

        titleImage.setVisible(true);
        titleImage.setBounds(10,0,100,100);
        titleImage.setBackground(new Color(50,50,50));
        titleImage.setFocusable(false);
        titleImage.setIcon(ReadUtility.bcIcon3);

        buttonBase.setVisible(false);
        buttonBase.setBounds(0, 0, 256, 256);
        buttonBase.setBackground(new Color(50,50,50));
        buttonBase.setFocusable(false);
        buttonBase.setIcon(ReadUtility.buttonBaseIcon);

        buttonTop.setVisible(false);
        buttonTop.setBounds(30, 30, 198, 198);
        buttonTop.setBackground(new Color(50,50,50));
        buttonTop.setFocusable(false);
        buttonTop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonTop.setIcon(ReadUtility.buttonTopIcon);

        saveNameField.setVisible(false);
        saveNameField.setBounds(0, 300, 647, 50);
        saveNameField.setFocusable(true);
        saveNameField.setBackground(new Color(40,40,40));
        saveNameField.setForeground(Color.WHITE);
        saveNameField.setFont(new Font("Nunito",Font.BOLD,40));
        saveNameField.setEditable(true);
        saveNameField.setHorizontalAlignment(JTextField.CENTER);
        saveNameField.setText("");
        saveNameField.setToolTipText("Empty Names Will Not Be Accepted");

        commandBar.setVisible(false);
        commandBar.setBounds(645, 585, 554, 53);
        commandBar.setFocusable(true);
        commandBar.setBackground(new Color(40,40,40));
        commandBar.setForeground(Color.GRAY);
        commandBar.setFont(new Font("Nunito",Font.BOLD,14));
        commandBar.setHorizontalAlignment(JTextField.LEFT);
        commandBar.setEditable(true);
        commandBar.setText("Enter any command, type help for a list of all the commands");
        commandBar.addKeyListener(new InputService.keyListener());
        commandBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                commandBar.setBounds(647, 587, 550, 49);

                if(Objects.equals(commandBar.getForeground(), Color.GRAY)){
                    commandBar.setForeground(Color.WHITE);
                    commandBar.setText("");
                    commandBar.setFont(new Font("Nunito",Font.BOLD,32));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                commandBar.setBounds(645, 585, 554, 53);

                if(commandBar.getText().isEmpty()){
                    commandBar.setForeground(Color.GRAY);
                    commandBar.setText("Enter any command, type help for a list of all the commands");
                    commandBar.setFont(new Font("Nunito",Font.BOLD,14));
                }
            }
        });

        clicksField.setVisible(false);
        clicksField.setBounds(0, 0, 647, 50);
        clicksField.setFocusable(false);
        clicksField.setBackground(new Color(50,50,50));
        clicksField.setForeground(Color.WHITE);
        clicksField.setFont(new Font("Nunito",Font.BOLD,40));
        clicksField.setEditable(false);
        clicksField.setHorizontalAlignment(JTextField.CENTER);

        clickPowerField.setVisible(false);
        clickPowerField.setBounds(0, 50, 647, 51);
        clickPowerField.setFocusable(false);
        clickPowerField.setBackground(new Color(50,50,50));
        clickPowerField.setForeground(Color.WHITE);
        clickPowerField.setFont(new Font("Nunito",Font.BOLD,34));
        clickPowerField.setEditable(false);
        clickPowerField.setHorizontalAlignment(JTextField.CENTER);

        saveText.setVisible(false);
        saveText.setBounds(30, 200, 600, 80);
        saveText.setFocusable(false);
        saveText.setBackground(new Color(50,50,50));
        saveText.setForeground(Color.WHITE);
        saveText.setFont(new Font("Nunito",Font.BOLD,40));
        saveText.setBorder(null);
        saveText.setHorizontalAlignment(JTextField.CENTER);
        saveText.setText("Type Between 1-10 Characters");

        titleText.setVisible(true);
        titleText.setBounds(100, 20, 570, 80);
        titleText.setFocusable(false);
        titleText.setBackground(new Color(50,50,50));
        titleText.setForeground(Color.WHITE);
        titleText.setFont(new Font("Nunito",Font.BOLD,70));
        titleText.setBorder(null);
        titleText.setHorizontalAlignment(JTextField.CENTER);
        titleText.setText("Button Clicker");

        creditsText.setVisible(false);
        creditsText.setBounds(0, 130, 570, 450);
        creditsText.setFocusable(false);
        creditsText.setBackground(new Color(50,50,50));
        creditsText.setForeground(Color.WHITE);
        creditsText.setFont(new Font("Nunito",Font.BOLD,40));
        creditsText.setBorder(null);
        creditsText.setEditable(false);
        creditsText.setLineWrap(true);
        creditsText.addMouseListener(new InputService.mouseListener());
        creditsText.setText("""
                Art - Alek
                Programming - Alek
                Music - Davit
                Cool Theme - FlatLaf 3.6
                Audio - JavaFX Media 21.0.2
                Playing The Game - You!""");

        saveInfoText.setVisible(false);
        saveInfoText.setBounds(50, 160, 550, 400);
        saveInfoText.setFocusable(false);
        saveInfoText.setBackground(new Color(50,50,50));
        saveInfoText.setForeground(Color.WHITE);
        saveInfoText.setFont(new Font("Nunito",Font.BOLD,40));
        saveInfoText.setBorder(null);
        saveInfoText.setEditable(false);
        saveInfoText.setLineWrap(false);

        lightSideText.setVisible(false);
        lightSideText.setBounds(110, 250, 80, 40);
        lightSideText.setFocusable(false);
        lightSideText.setBackground(new Color(50,50,50));
        lightSideText.setForeground(Color.WHITE);
        lightSideText.setFont(new Font("Nunito",Font.BOLD,30));
        lightSideText.setBorder(null);
        lightSideText.setHorizontalAlignment(JTextField.CENTER);
        lightSideText.setText("Light");

        darkSideText.setVisible(false);
        darkSideText.setBounds(460, 250, 80, 40);
        darkSideText.setFocusable(false);
        darkSideText.setBackground(new Color(50,50,50));
        darkSideText.setForeground(Color.WHITE);
        darkSideText.setFont(new Font("Nunito",Font.BOLD,30));
        darkSideText.setBorder(null);
        darkSideText.setHorizontalAlignment(JTextField.CENTER);
        darkSideText.setText("Dark");

        magicSideText.setVisible(false);
        magicSideText.setBounds(110, 450, 90, 40);
        magicSideText.setFocusable(false);
        magicSideText.setBackground(new Color(50,50,50));
        magicSideText.setForeground(Color.WHITE);
        magicSideText.setFont(new Font("Nunito",Font.BOLD,30));
        magicSideText.setBorder(null);
        magicSideText.setHorizontalAlignment(JTextField.CENTER);
        magicSideText.setText("Magic");

        neutralSideText.setVisible(false);
        neutralSideText.setBounds(440, 450, 120, 40);
        neutralSideText.setFocusable(false);
        neutralSideText.setBackground(new Color(50,50,50));
        neutralSideText.setForeground(Color.WHITE);
        neutralSideText.setFont(new Font("Nunito",Font.BOLD,30));
        neutralSideText.setBorder(null);
        neutralSideText.setHorizontalAlignment(JTextField.CENTER);
        neutralSideText.setText("Neutral");

        masterVolumeSlider.setBounds(70, 310, 500, 20);
        masterVolumeSlider.setVisible(false);
        masterVolumeSlider.addChangeListener(e -> {
            WriteUtility.JSONWriter.writeJson("Data/settings.json", "master_volume", masterVolumeSlider.getValue());
            AudioService.SoundManager.setMasterVolume(masterVolumeSlider.getValue());
        });

        masterVolumeText.setVisible(false);
        masterVolumeText.setBounds(180, 260, 300, 50);
        masterVolumeText.setFocusable(false);
        masterVolumeText.setBackground(new Color(50,50,50));
        masterVolumeText.setForeground(Color.WHITE);
        masterVolumeText.setFont(new Font("Nunito",Font.BOLD,40));
        masterVolumeText.setBorder(null);
        masterVolumeText.setHorizontalAlignment(JTextField.CENTER);
        masterVolumeText.setText("Master Volume");

        musicVolumeSlider.setBounds(70, 420, 250, 20);
        musicVolumeSlider.setVisible(false);
        musicVolumeSlider.addChangeListener(e -> {
            WriteUtility.JSONWriter.writeJson("Data/settings.json", "music_volume", musicVolumeSlider.getValue());
            AudioService.SoundManager.setMusicVolume(masterVolumeSlider.getValue());
        });

        musicVolumeText.setVisible(false);
        musicVolumeText.setBounds(90, 370, 210, 50);
        musicVolumeText.setFocusable(false);
        musicVolumeText.setBackground(new Color(50,50,50));
        musicVolumeText.setForeground(Color.WHITE);
        musicVolumeText.setFont(new Font("Nunito",Font.BOLD,30));
        musicVolumeText.setBorder(null);
        musicVolumeText.setHorizontalAlignment(JTextField.CENTER);
        musicVolumeText.setText("Music Volume");

        sfxVolumeSlider.setBounds(320, 420, 250, 20);
        sfxVolumeSlider.setVisible(false);
        sfxVolumeSlider.addChangeListener(e -> {
            WriteUtility.JSONWriter.writeJson("Data/settings.json", "sfx_volume", sfxVolumeSlider.getValue());
            AudioService.SoundManager.setSFXVolume(masterVolumeSlider.getValue());
        });

        sfxVolumeText.setVisible(false);
        sfxVolumeText.setBounds(355, 370, 180, 50);
        sfxVolumeText.setFocusable(false);
        sfxVolumeText.setBackground(new Color(50,50,50));
        sfxVolumeText.setForeground(Color.WHITE);
        sfxVolumeText.setFont(new Font("Nunito",Font.BOLD,30));
        sfxVolumeText.setBorder(null);
        sfxVolumeText.setHorizontalAlignment(JTextField.CENTER);
        sfxVolumeText.setText("SFX Volume");

        buttonClickerVersionText.setVisible(false);
        buttonClickerVersionText.setBounds(0, 580, 345, 30);
        buttonClickerVersionText.setFocusable(false);
        buttonClickerVersionText.setBackground(new Color(50,50,50));
        buttonClickerVersionText.setForeground(Color.WHITE);
        buttonClickerVersionText.setFont(new Font("Nunito",Font.BOLD,20));
        buttonClickerVersionText.setBorder(null);
        buttonClickerVersionText.setText("Button Clicker "+ Spark.VERSION);

        currentSaveText.setVisible(false);
        currentSaveText.setBounds(0, 610, 345, 30);
        currentSaveText.setFocusable(false);
        currentSaveText.setBackground(new Color(50,50,50));
        currentSaveText.setForeground(Color.WHITE);
        currentSaveText.setFont(new Font("Nunito",Font.BOLD,20));
        currentSaveText.setBorder(null);

        musicDelayText.setVisible(false);
        musicDelayText.setBounds(350, 455, 180, 40);
        musicDelayText.setFocusable(false);
        musicDelayText.setBackground(new Color(50,50,50));
        musicDelayText.setForeground(Color.WHITE);
        musicDelayText.setFont(new Font("Nunito",Font.BOLD,30));
        musicDelayText.setBorder(null);
        musicDelayText.setHorizontalAlignment(JTextField.CENTER);
        musicDelayText.setText("Music Delay");

        tipsText.setVisible(false);
        tipsText.setBounds(100, 150, 400, 80);
        tipsText.setFocusable(false);
        tipsText.setBackground(null);
        tipsText.setForeground(Color.WHITE);
        tipsText.setFont(new Font("Nunito",Font.BOLD,40));
        tipsText.setBorder(null);
        tipsText.setAlignmentX((float) tipsText.getWidth() /2);
        tipsText.setEditable(false);
        tipsText.setLineWrap(true);

        console.setVisible(false);
        console.setBounds(647, 0, 600, 587);
        console.setFocusable(false);
        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);
        console.setFont(new Font("Consolas",Font.BOLD,12));
        console.setBorder(null);
        console.setEditable(false);
        console.setLineWrap(false);

        crashText.setVisible(false);
        crashText.setBounds(80, 40, 500, 275);
        crashText.setFocusable(false);
        crashText.setBackground(Color.WHITE);
        crashText.setForeground(Color.BLACK);
        crashText.setFont(new Font("Sans Serif",Font.BOLD,20));
        crashText.setBorder(null);
        crashText.setEditable(false);
        crashText.setLineWrap(false);

        progressText.setVisible(false);
        progressText.setBounds(47, 300, 540, 40);
        progressText.setFocusable(false);
        progressText.setBackground(new Color(50,50,50));
        progressText.setForeground(Color.WHITE);
        progressText.setFont(new Font("Nunito",Font.BOLD,30));
        progressText.setBorder(null);
        progressText.setHorizontalAlignment(JTextField.CENTER);

        autoSavingText.setVisible(false);
        autoSavingText.setBounds(535,210,100,50);
        autoSavingText.setFocusable(false);
        autoSavingText.setBackground(new Color(40,40,40));
        autoSavingText.setForeground(Color.WHITE);
        autoSavingText.setFont(new Font("Nunito",Font.BOLD,24));
        autoSavingText.setBorder(null);
        autoSavingText.setHorizontalAlignment(JTextField.CENTER);
        autoSavingText.setText("Saving...");

        musicDelaySpinner.setVisible(false);
        musicDelaySpinner.setBounds(350, 519, 180, 40);
        musicDelaySpinner.setFocusable(true);
        musicDelaySpinner.setBackground(new Color(40,40,40));
        musicDelaySpinner.setForeground(Color.WHITE);
        musicDelaySpinner.setFont(new Font("Nunito",Font.BOLD,30));
        musicDelaySpinner.addChangeListener(e -> {
            WriteUtility.JSONWriter.writeJson("Data/settings.json", "music_delay", musicDelaySpinner.getValue());
            AudioService.SoundManager.musicDelay = (Byte) musicDelaySpinner.getValue();
        });
        JComponent musicTimerSpinnerEditor = musicDelaySpinner.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) musicTimerSpinnerEditor;
        musicDelaySpinnerText = spinnerEditor.getTextField();

        musicDelaySpinnerText.setEditable(false);
        musicDelaySpinnerText.setFocusable(false);
        musicDelaySpinnerText.setFormatterFactory(new DefaultFormatterFactory(new DefaultFormatter() {
            @Override
            public Object stringToValue(String text) {
                if (text.endsWith(" Secs")) {
                    text = text.substring(0, text.length() - 5).trim();
                }
                return Integer.parseInt(text);
            }

            @Override
            public String valueToString(Object value) {
                return value + " Secs";
            }
        }));
        musicDelaySpinner.setEditor(musicTimerSpinnerEditor);

        loadingBar.setVisible(false);
        loadingBar.setBounds(47, 350, 553, 40);
        loadingBar.setValue(0);
        loadingBar.setStringPainted(true);
        loadingBar.addChangeListener(e -> {
            if(loadingBar.getValue()==100){
                ATimer timer = new ATimer();
                timer.setDelay(1);
                timer.setTask(() -> {
                    MenuManager.closeMenu("Loading");

                    titleText.setBounds(100, 20, 570, 80);
                    titleText.setVisible(false);

                    titleImage.stopSpinning();
                    titleImage.setBounds(10,0,100,100);
                    titleImage.setVisible(false);

                    if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                        clicksField.setText("Clicks: "+ WriteUtility.clicks1);
                        clickPowerField.setText("Click Power: "+ WriteUtility.clickPower1);
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                        clicksField.setText("Clicks: "+ WriteUtility.clicks2);
                        clickPowerField.setText("Click Power: "+ WriteUtility.clickPower2);
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                        clicksField.setText("Clicks: "+ WriteUtility.clicks3);
                        clickPowerField.setText("Click Power: "+ WriteUtility.clickPower3);
                    }

                    MenuManager.openMenu("Game");
                });
                timer.start();
            }
        });

        shopItem1.setVisible(false);
        shopItem1.setBounds(90, 180, 200, 100);
        shopItem1.addMouseListener(new InputService.mouseListener());

        shopItem2.setVisible(false);
        shopItem2.setBounds(357, 180, 200, 100);
        shopItem2.addMouseListener(new InputService.mouseListener());

        shopItem3.setVisible(false);
        shopItem3.setBounds(90, 320, 200, 100);
        shopItem3.addMouseListener(new InputService.mouseListener());

        shopItem4.setVisible(false);
        shopItem4.setBounds(357, 320, 200, 100);
        shopItem4.addMouseListener(new InputService.mouseListener());

        shopItem5.setVisible(false);
        shopItem5.setBounds(90, 460, 200, 100);
        shopItem5.addMouseListener(new InputService.mouseListener());

        shopItem6.setVisible(false);
        shopItem6.setBounds(357, 460, 200, 100);
        shopItem6.addMouseListener(new InputService.mouseListener());

        startGameButton.setVisible(false);
        startGameButton.setBounds(177,150,305,102);
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startGameButton.setBorderPainted(false);
        startGameButton.setRolloverEnabled(false);
        startGameButton.setFocusPainted(false);
        startGameButton.setFocusable(false);
        startGameButton.setOpaque(false);
        startGameButton.addActionListener(InputService.actionListener);

        saveManagerButton.setVisible(false);
        saveManagerButton.setBounds(177,249,305,102);
        saveManagerButton.setText("Save Manager");
        saveManagerButton.setFont(new Font("Nunito", Font.BOLD, 28));
        saveManagerButton.setForeground(Color.WHITE);
        saveManagerButton.setBackground(Color.CYAN);
        saveManagerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveManagerButton.setBorderPainted(false);
        saveManagerButton.setRolloverEnabled(false);
        saveManagerButton.setFocusPainted(false);
        saveManagerButton.setFocusable(false);
        saveManagerButton.setOpaque(false);
        saveManagerButton.addActionListener(InputService.actionListener);

        wikiButton.setVisible(false);
        wikiButton.setBounds(73,150,250,100);
        wikiButton.setText("Wiki");
        wikiButton.setFont(new Font("Nunito", Font.BOLD, 30));
        wikiButton.setForeground(Color.WHITE);
        wikiButton.setBackground(Color.ORANGE);
        wikiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        wikiButton.setBorderPainted(false);
        wikiButton.setRolloverEnabled(false);
        wikiButton.setFocusPainted(false);
        wikiButton.setFocusable(false);
        wikiButton.setOpaque(false);
        wikiButton.addActionListener(InputService.actionListener);

        creditsButton.setVisible(false);
        creditsButton.setBounds(320,150,250,100);
        creditsButton.setText("Credits");
        creditsButton.setFont(new Font("Nunito", Font.BOLD, 30));
        creditsButton.setForeground(Color.WHITE);
        creditsButton.setBackground(Color.YELLOW);
        creditsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        creditsButton.setBorderPainted(false);
        creditsButton.setRolloverEnabled(false);
        creditsButton.setFocusPainted(false);
        creditsButton.setFocusable(false);
        creditsButton.setOpaque(false);
        creditsButton.addActionListener(InputService.actionListener);

        quitButton.setVisible(false);
        quitButton.setBounds(177,447,305,102);
        quitButton.setFont(new Font("Nunito", Font.BOLD, 30));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(Color.RED);
        quitButton.setMargin(new Insets(0, 0, 0, 0));
        quitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        quitButton.setBorderPainted(false);
        quitButton.setRolloverEnabled(false);
        quitButton.setFocusPainted(false);
        quitButton.setFocusable(false);
        quitButton.setOpaque(false);
        quitButton.addActionListener(InputService.actionListener);

        backToButton.setVisible(false);
        backToButton.setBounds(-10, 600, 667, 38);
        backToButton.setText("Back To Spark Menu");
        backToButton.setFont(new Font("Nunito", Font.BOLD, 30));
        backToButton.setForeground(Color.WHITE);
        backToButton.setBackground(Color.BLACK);
        backToButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backToButton.setBorderPainted(false);
        backToButton.setRolloverEnabled(false);
        backToButton.setFocusPainted(false);
        backToButton.setFocusable(false);
        backToButton.setOpaque(false);
        backToButton.addActionListener(InputService.actionListener);

        save1Button.setVisible(false);
        save1Button.setBounds(28, 122, 200, 350);
        save1Button.setText("Save 1 Empty");
        save1Button.setFont(new Font("Nunito", Font.BOLD, 30));
        save1Button.setForeground(Color.WHITE);
        save1Button.setBackground(Color.GRAY);
        save1Button.setMargin(new Insets(0, 0, 0, 0));
        save1Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        save1Button.setBorderPainted(false);
        save1Button.setRolloverEnabled(false);
        save1Button.setFocusPainted(false);
        save1Button.setFocusable(false);
        save1Button.setOpaque(false);
        save1Button.addActionListener(InputService.actionListener);

        save2Button.setVisible(false);
        save2Button.setBounds(225, 122, 200, 350);
        save2Button.setText("Save 2 Empty");
        save2Button.setFont(new Font("Nunito", Font.BOLD, 30));
        save2Button.setForeground(Color.WHITE);
        save2Button.setBackground(Color.GRAY);
        save2Button.setMargin(new Insets(0, 0, 0, 0));
        save2Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        save2Button.setBorderPainted(false);
        save2Button.setRolloverEnabled(false);
        save2Button.setFocusPainted(false);
        save2Button.setFocusable(false);
        save2Button.setOpaque(false);
        save2Button.addActionListener(InputService.actionListener);

        save3Button.setVisible(false);
        save3Button.setBounds(422, 122, 200, 350);
        save3Button.setText("Save 3 Empty");
        save3Button.setFont(new Font("Nunito", Font.BOLD, 30));
        save3Button.setForeground(Color.WHITE);
        save3Button.setBackground(Color.GRAY);
        save3Button.setMargin(new Insets(0, 0, 0, 0));
        save3Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        save3Button.setBorderPainted(false);
        save3Button.setRolloverEnabled(false);
        save3Button.setFocusPainted(false);
        save3Button.setFocusable(false);
        save3Button.setOpaque(false);
        save3Button.addActionListener(InputService.actionListener);

        lightSideButton.setVisible(false);
        lightSideButton.setBounds(100,150,100,100);
        lightSideButton.setFont(new Font("Nunito", Font.BOLD, 30));
        lightSideButton.setForeground(Color.WHITE);
        lightSideButton.setBackground(new Color(50, 50, 50));
        lightSideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lightSideButton.setBorderPainted(false);
        lightSideButton.setRolloverEnabled(false);
        lightSideButton.setFocusPainted(false);
        lightSideButton.setFocusable(false);
        lightSideButton.setOpaque(false);
        lightSideButton.addActionListener(InputService.actionListener);
        lightSideButton.setIcon(ReadUtility.lightSideIcon);

        darkSideButton.setVisible(false);
        darkSideButton.setBounds(450,150,100,100);
        darkSideButton.setFont(new Font("Nunito", Font.BOLD, 30));
        darkSideButton.setForeground(Color.WHITE);
        darkSideButton.setBackground(new Color(50, 50, 50));
        darkSideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        darkSideButton.setBorderPainted(false);
        darkSideButton.setRolloverEnabled(false);
        darkSideButton.setFocusPainted(false);
        darkSideButton.setFocusable(false);
        darkSideButton.setOpaque(false);
        darkSideButton.addActionListener(InputService.actionListener);
        darkSideButton.setIcon(ReadUtility.darkSideIcon);

        magicSideButton.setVisible(false);
        magicSideButton.setBounds(100,350,100,100);
        magicSideButton.setFont(new Font("Nunito", Font.BOLD, 30));
        magicSideButton.setForeground(Color.WHITE);
        magicSideButton.setBackground(new Color(50, 50, 50));
        magicSideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        magicSideButton.setBorderPainted(false);
        magicSideButton.setRolloverEnabled(false);
        magicSideButton.setFocusPainted(false);
        magicSideButton.setFocusable(false);
        magicSideButton.setOpaque(false);
        magicSideButton.addActionListener(InputService.actionListener);
        magicSideButton.setIcon(ReadUtility.magicSideIcon);

        neutralSideButton.setVisible(false);
        neutralSideButton.setBounds(450,350,100,100);
        neutralSideButton.setFont(new Font("Nunito", Font.BOLD, 30));
        neutralSideButton.setForeground(Color.WHITE);
        neutralSideButton.setBackground(new Color(50, 50, 50));
        neutralSideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        neutralSideButton.setBorderPainted(false);
        neutralSideButton.setRolloverEnabled(false);
        neutralSideButton.setFocusPainted(false);
        neutralSideButton.setFocusable(false);
        neutralSideButton.setOpaque(false);
        neutralSideButton.addActionListener(InputService.actionListener);
        neutralSideButton.setIcon(ReadUtility.neutralSideIcon);

        deleteSave1Button.setVisible(false);
        deleteSave1Button.setBounds(28, 469, 200, 80);
        deleteSave1Button.setText("Delete");
        deleteSave1Button.setFont(new Font("Nunito", Font.BOLD, 30));
        deleteSave1Button.setForeground(Color.WHITE);
        deleteSave1Button.setBackground(Color.GRAY);
        deleteSave1Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteSave1Button.setBorderPainted(false);
        deleteSave1Button.setRolloverEnabled(false);
        deleteSave1Button.setFocusPainted(false);
        deleteSave1Button.setFocusable(false);
        deleteSave1Button.setOpaque(false);
        deleteSave1Button.addActionListener(InputService.actionListener);

        deleteSave2Button.setVisible(false);
        deleteSave2Button.setBounds(225, 469, 200, 80);
        deleteSave2Button.setText("Delete");
        deleteSave2Button.setFont(new Font("Nunito", Font.BOLD, 30));
        deleteSave2Button.setForeground(Color.WHITE);
        deleteSave2Button.setBackground(Color.GRAY);
        deleteSave2Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteSave2Button.setBorderPainted(false);
        deleteSave2Button.setRolloverEnabled(false);
        deleteSave2Button.setFocusPainted(false);
        deleteSave2Button.setFocusable(false);
        deleteSave2Button.setOpaque(false);
        deleteSave2Button.addActionListener(InputService.actionListener);

        deleteSave3Button.setVisible(false);
        deleteSave3Button.setBounds(422, 469, 200, 80);
        deleteSave3Button.setText("Delete");
        deleteSave3Button.setFont(new Font("Nunito", Font.BOLD, 30));
        deleteSave3Button.setForeground(Color.WHITE);
        deleteSave3Button.setBackground(Color.GRAY);
        deleteSave3Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteSave3Button.setBorderPainted(false);
        deleteSave3Button.setRolloverEnabled(false);
        deleteSave3Button.setFocusPainted(false);
        deleteSave3Button.setFocusable(false);
        deleteSave3Button.setOpaque(false);
        deleteSave3Button.addActionListener(InputService.actionListener);

        settingsButton.setVisible(false);
        settingsButton.setBounds(177,348,305,102);
        settingsButton.setText("Settings");
        settingsButton.setFont(new Font("Nunito", Font.BOLD, 28));
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setBackground(Color.ORANGE);
        settingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        settingsButton.setBorderPainted(false);
        settingsButton.setRolloverEnabled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setFocusable(false);
        settingsButton.setOpaque(false);
        settingsButton.addActionListener(InputService.actionListener);

        bugReportButton.setVisible(false);
        bugReportButton.setBounds(73, 460, 250, 100);
        bugReportButton.setText("Report A Bug!");
        bugReportButton.setFont(new Font("Nunito", Font.BOLD, 28));
        bugReportButton.setForeground(Color.WHITE);
        bugReportButton.setBackground(Color.BLUE);
        bugReportButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bugReportButton.setBorderPainted(false);
        bugReportButton.setRolloverEnabled(false);
        bugReportButton.setFocusPainted(false);
        bugReportButton.setFocusable(false);
        bugReportButton.setOpaque(false);
        bugReportButton.addActionListener(InputService.actionListener);

        debugButton.setVisible(false);
        debugButton.setBounds(549, 538, 100, 100);
        debugButton.setText("Debug");
        debugButton.setFont(new Font("Nunito", Font.BOLD, 28));
        debugButton.setForeground(Color.WHITE);
        debugButton.setBackground(Color.BLUE);
        debugButton.setMargin(new Insets(0, 0, 0, 0));
        debugButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        debugButton.setBorderPainted(false);
        debugButton.setRolloverEnabled(false);
        debugButton.setFocusPainted(false);
        debugButton.setFocusable(false);
        debugButton.setOpaque(false);
        debugButton.addActionListener(InputService.actionListener);

        infoButton.setVisible(false);
        infoButton.setBounds(28, 546, 594, 50);
        infoButton.setText("Info");
        infoButton.setFont(new Font("Nunito", Font.BOLD, 30));
        infoButton.setForeground(Color.WHITE);
        infoButton.setBackground(Color.GRAY);
        infoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        infoButton.setBorderPainted(false);
        infoButton.setRolloverEnabled(false);
        infoButton.setFocusPainted(false);
        infoButton.setFocusable(false);
        infoButton.setOpaque(false);
        infoButton.addActionListener(InputService.actionListener);

        wikiHelpButton.setVisible(false);
        wikiHelpButton.setBounds(955, 0, 50, 50);
        wikiHelpButton.setFont(new Font("Nunito", Font.BOLD, 30));
        wikiHelpButton.setForeground(Color.WHITE);
        wikiHelpButton.setBackground(new Color(40, 40, 40));
        wikiHelpButton.setText("?");
        wikiHelpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        wikiHelpButton.setBorderPainted(false);
        wikiHelpButton.setRolloverEnabled(false);
        wikiHelpButton.setFocusPainted(false);
        wikiHelpButton.setFocusable(false);
        wikiHelpButton.setOpaque(false);
        wikiHelpButton.addActionListener(InputService.actionListener);

        backwardButton.setVisible(false);
        backwardButton.setBounds(-2, 180, 50, 370);
        backwardButton.setBackground(Color.WHITE);
        backwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backwardButton.setBorderPainted(false);
        backwardButton.setRolloverEnabled(false);
        backwardButton.setFocusPainted(false);
        backwardButton.setFocusable(false);
        backwardButton.setOpaque(false);
        backwardButton.addActionListener(InputService.actionListener);
        backwardButton.setIcon(ReadUtility.backwardArrowIcon);

        forwardButton.setVisible(false);
        forwardButton.setBounds(599, 180, 50, 370);
        forwardButton.setFont(new Font("Nunito", Font.BOLD, 30));
        forwardButton.setBackground(Color.WHITE);
        forwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forwardButton.setBorderPainted(false);
        forwardButton.setRolloverEnabled(false);
        forwardButton.setFocusPainted(false);
        forwardButton.setFocusable(false);
        forwardButton.setOpaque(false);
        forwardButton.addActionListener(InputService.actionListener);
        forwardButton.setIcon(ReadUtility.forwardArrowIcon);

        nextTipButton.setVisible(false);
        nextTipButton.setFont(new Font("Nunito", Font.BOLD, 30));
        nextTipButton.setBackground(new Color(40,40,40));
        nextTipButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextTipButton.setBorderPainted(false);
        nextTipButton.setRolloverEnabled(false);
        nextTipButton.setFocusPainted(false);
        nextTipButton.setFocusable(false);
        nextTipButton.setOpaque(false);
        nextTipButton.addActionListener(InputService.actionListener);
        nextTipButton.setText("Next Tip");

        shopButton.setVisible(false);
        shopButton.setBounds(-3, 568, 329, 70);
        shopButton.setText("Shop");
        shopButton.setFont(new Font("Nunito", Font.BOLD, 30));
        shopButton.setForeground(Color.WHITE);
        shopButton.setBackground(Color.ORANGE);
        shopButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        shopButton.setBorderPainted(false);
        shopButton.setRolloverEnabled(false);
        shopButton.setFocusPainted(false);
        shopButton.setFocusable(false);
        shopButton.setOpaque(false);
        shopButton.addActionListener(InputService.actionListener);

        pauseButton.setVisible(false);
        pauseButton.setBounds(323, 568, 327, 70);
        pauseButton.setText("Pause");
        pauseButton.setFont(new Font("Nunito", Font.BOLD, 30));
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setBackground(Color.DARK_GRAY);
        pauseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pauseButton.setBorderPainted(false);
        pauseButton.setRolloverEnabled(false);
        pauseButton.setFocusPainted(false);
        pauseButton.setFocusable(false);
        pauseButton.setOpaque(false);
        pauseButton.addActionListener(InputService.actionListener);

        showConsoleButton.setVisible(false);
        showConsoleButton.setBounds(100, 300, 327, 70);
        showConsoleButton.setText("Show Console");
        showConsoleButton.setFont(new Font("Nunito", Font.BOLD, 30));
        showConsoleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showConsoleButton.setFocusable(false);
        showConsoleButton.addActionListener(InputService.actionListener);

        crashButton.setVisible(false);
        crashButton.setBounds(100, 500, 327, 70);
        crashButton.setText("Crash");
        crashButton.setFont(new Font("Nunito", Font.BOLD, 30));
        crashButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        crashButton.setFocusable(false);
        crashButton.addActionListener(InputService.actionListener);

        consoleSettingsButton.setVisible(false);
        consoleSettingsButton.setBounds(1195, 585, 54, 53);
        consoleSettingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        consoleSettingsButton.setFocusable(false);
        consoleSettingsButton.addActionListener(InputService.actionListener);
        consoleSettingsButton.setIcon(ReadUtility.cogIcon);

        new ReadUtility();

        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setSize(663, 675);
        frame.getRootPane().setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(!MenuManager.isMenuOpen("Loading")){
                    if(Objects.equals(WriteUtility.currentSave, "null")){
                        Logger.Log.info("Quitting...");

                        System.exit(0);
                    }else if(!Objects.equals(WriteUtility.currentSave, "null")){
                        Logger.Log.info("Quitting & Saving...");

                        if (Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")) {
                            WriteUtility.save1();
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")) {
                            WriteUtility.save2();
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")) {
                            WriteUtility.save3();
                        }

                        frame.removeWindowListener(this);

                        System.exit(0);
                    }
                }else if(MenuManager.isMenuOpen("Loading")){
                    AudioService.SFX.playSFX("declined");
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.addKeyListener(new InputService.keyListener());
        frame.addMouseListener(new InputService.mouseListener());
        frame.setResizable(false);
        frame.setTitle("Button Clicker");
        frame.setIconImage(ReadUtility.bcIcon3.getImage());

        frame.add(contentPane);

        contentPane.add(tipsPanel, 0);

        contentPane.add(wikiHelpButton, 1);
        contentPane.add(backToButton, 0);

        contentPane.add(wikiScrollPane, BorderLayout.CENTER, -1);
        contentPane.add(consoleScrollPane, 0);

        contentPane.add(buttonLayeredPane, 0);

        contentPane.add(titleImage, 0);

        contentPane.add(titleText, 0);
        contentPane.add(lightSideText, 0);
        contentPane.add(darkSideText, 0);
        contentPane.add(magicSideText, 0);
        contentPane.add(neutralSideText, 0);
        contentPane.add(creditsText, 0);
        contentPane.add(saveNameField, 0);
        contentPane.add(clicksField, 0);
        contentPane.add(clickPowerField, 0);
        contentPane.add(saveText, 0);
        contentPane.add(masterVolumeSlider, 0);
        contentPane.add(masterVolumeText, 0);
        contentPane.add(musicVolumeSlider, 0);
        contentPane.add(musicVolumeText, 0);
        contentPane.add(sfxVolumeSlider, 0);
        contentPane.add(sfxVolumeText, 0);
        contentPane.add(buttonClickerVersionText, 0);
        contentPane.add(currentSaveText, 0);
        contentPane.add(musicDelayText, 0);
        contentPane.add(saveInfoText, -1);
        contentPane.add(progressText, 0);
        contentPane.add(autoSavingText, 0);
        contentPane.add(crashText, 0);
        contentPane.add(commandBar, 1);

        contentPane.add(musicDelaySpinner, 0);

        contentPane.add(loadingBar, 0);

        contentPane.add(shopItem1, 0);
        contentPane.add(shopItem2, 0);
        contentPane.add(shopItem3, 0);
        contentPane.add(shopItem4, 0);
        contentPane.add(shopItem5, 0);
        contentPane.add(shopItem6, 0);

        contentPane.add(startGameButton, 0);
        contentPane.add(saveManagerButton, 0);
        contentPane.add(wikiButton, 0);
        contentPane.add(creditsButton, 0);
        contentPane.add(quitButton, 0);
        contentPane.add(save1Button, 0);
        contentPane.add(save2Button, 0);
        contentPane.add(save3Button, 0);
        contentPane.add(lightSideButton, 0);
        contentPane.add(darkSideButton, 0);
        contentPane.add(magicSideButton, 0);
        contentPane.add(neutralSideButton, 0);
        contentPane.add(deleteSave1Button, 0);
        contentPane.add(deleteSave2Button, 0);
        contentPane.add(deleteSave3Button, 0);
        contentPane.add(settingsButton, 0);
        contentPane.add(bugReportButton, 0);
        contentPane.add(debugButton, 0);
        contentPane.add(infoButton, 0);
        contentPane.add(backwardButton, 0);
        contentPane.add(forwardButton, 0);
        contentPane.add(shopButton, 0);
        contentPane.add(pauseButton, 0);
        contentPane.add(showConsoleButton, 0);
        contentPane.add(crashButton, 0);
        contentPane.add(consoleSettingsButton, 0);

        Logger.Log.info("Render Pipeline Initialized!");

        MenuManager.openMenu("Spark");
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
                    ErrorHandler.ExecutionException();
                } catch (InterruptedException e) {
                    ErrorHandler.InterruptedException();
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
        MenuManager.resetWindow = true;
    }

    public static void resetWindow(){
        if(MenuManager.resetWindow){
            frame.setSize(663, 675);
            frame.setLocationRelativeTo(null);

            MenuManager.resetWindow = false;
        }
    }

    public static BufferedImage generateMissingTexture(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        int tileSize = size / 4;
        Color purple = new Color(128, 0, 128);
        Color black = Color.BLACK;

        for (int y = 0; y < size; y += tileSize) {
            for (int x = 0; x < size; x += tileSize) {
                boolean isPurple = ((x + y) / tileSize) % 2 == 0;
                g.setColor(isPurple ? purple : black);
                g.fillRect(x, y, tileSize, tileSize);
            }
        }

        g.dispose();
        return img;
    }
}