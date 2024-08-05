package net.alek.buttonclicker.libraries;

import net.alek.buttonclicker.engine.Main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StorageLibrary {
    public static JFrame frame = new JFrame();

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

    public static JTextField saveNameField = new JTextField();
    public static JTextField clicksField = new JTextField();
    public static JTextField clickPowerField = new JTextField();

    public static ATextArea saveInfoText = new ATextArea();
    public static JTextArea creditsText = new JTextArea();
    public static JTextArea tipsText = new JTextArea();

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

    public static ImageIcon bcIcon;
    public static ImageIcon bcIcon2;
    public static ImageIcon bcIcon3;
    public static ImageIcon lightSideIcon;
    public static ImageIcon darkSideIcon;
    public static ImageIcon magicSideIcon;
    public static ImageIcon neutralSideIcon;
    public static ImageIcon errorIcon;
    public static ImageIcon warningIcon;
    public static ImageIcon infoIcon;
    public static ImageIcon questionIcon;
    public static ImageIcon backwardArrowIcon;
    public static ImageIcon forwardArrowIcon;
    public static ImageIcon trollIcon;
    public static ImageIcon buttonBaseIcon;
    public static ImageIcon buttonTopIcon;
    public static ImageIcon clickPowerIcon;

    public static BufferedImage portal2Whitewall;

    public static final String VERSION = "0.3.0_INDEV";

    public static long clicks1 = 0;
    public static long clickPower1 = 1;
    public static byte side1 = 0;
    public static String save1Name = "null";
    public static boolean playedBefore1 = false;

    public static long clicks2 = 0;
    public static long clickPower2 = 1;
    public static byte side2 = 0;
    public static String save2Name = "null";
    public static boolean playedBefore2 = false;

    public static long clicks3 = 0;
    public static long clickPower3 = 1;
    public static byte side3 = 0;
    public static String save3Name = "null";
    public static boolean playedBefore3 = false;

    public static String currentSave = "null";

    public static byte saveBeingCreated = 0;

    public static boolean StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu = false;

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static boolean resetWindow = false;
}