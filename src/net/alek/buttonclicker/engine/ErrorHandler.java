package net.alek.buttonclicker.engine;

import com.formdev.flatlaf.FlatLaf;
import net.alek.buttonclicker.libraries.StorageLibrary;
import net.alek.buttonclicker.utilities.AudioUtility;

import javax.swing.*;

public class ErrorHandler {
    public static void IOException(String clas, int line) {
        StorageLibrary.logger.error("IOEXCP#"+clas+"#"+line+": The Program has Suffered a IO Exception!");

        FlatLaf.unregisterCustomDefaultsSource("assets.buttonclicker.config");

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);

        StorageLibrary.frame.setVisible(false);
        AudioUtility.Music.stopMusic();

        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "IOEXCP#"+clas+"#"+line+": The Program has Suffered a IO Exception!\n\nPlease press the report a bug button in the settings and describe the bug, give detailed steps on how to reproduce the bug, and the error code ie. IOEXCP#INPUT#174",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                UIManager.getIcon("OptionPane.errorIcon"),
                responses,
                responses[0]);

        System.exit(0);
    }

    public static void URISyntaxException(String clas, int line) {
        StorageLibrary.logger.error("URISYNTEXCP#"+clas+"#"+line+": The Passed URI Has A Invalid Syntax!");

        FlatLaf.unregisterCustomDefaultsSource("assets.buttonclicker.config");

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);

        StorageLibrary.frame.setVisible(false);
        AudioUtility.Music.stopMusic();

        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "URISYNTEXCP#"+clas+"#"+line+": The Passed URI Has A Invalid Syntax!\n\nPlease press the report a bug button in the settings and describe the bug, give detailed steps on how to reproduce the bug, and the error code ie. IOEXCP#INPUT#174",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                UIManager.getIcon("OptionPane.errorIcon"),
                responses,
                responses[0]);

        System.exit(0);
    }

    public static void InterruptedException(String clas, int line) {
        StorageLibrary.logger.error("INTEREXCP#"+clas+"#"+line+": The Thread has been Interrupted!");

        FlatLaf.unregisterCustomDefaultsSource("assets.buttonclicker.config");

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);

        StorageLibrary.frame.setVisible(false);
        AudioUtility.Music.stopMusic();

        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "INTEREXCP#"+clas+"#"+line+": The Thread has been Interrupted!\n\nPlease press the report a bug button in the settings and describe the bug, give detailed steps on how to reproduce the bug, and the error code ie. IOEXCP#INPUT#174",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                UIManager.getIcon("OptionPane.errorIcon"),
                responses,
                responses[0]);

        System.exit(0);
    }

    public static void JsonIOException(String clas, int line) {
        StorageLibrary.logger.error("JSONIOEXCP#"+clas+"#"+line+": The Json Reader/Writer has Suffered a IO Exception!");

        FlatLaf.unregisterCustomDefaultsSource("assets.buttonclicker.config");

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);

        StorageLibrary.frame.setVisible(false);
        AudioUtility.Music.stopMusic();

        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "JSONIOEXCP#"+clas+"#"+line+": The Json Reader/Writer has Suffered a IO Exception!\n\nPlease press the report a bug button in the settings and describe the bug, give detailed steps on how to reproduce the bug, and the error code ie. IOEXCP#INPUT#174",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                UIManager.getIcon("OptionPane.errorIcon"),
                responses,
                responses[0]);

        System.exit(0);
    }

    public static void JsonSyntaxException(String clas, int line) {
        StorageLibrary.logger.error("JSONSYNEXCP#"+clas+"#"+line+": The Json Reader/Writer has Encountered Invalid Json Syntax!");

        FlatLaf.unregisterCustomDefaultsSource("assets.buttonclicker.config");

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);

        StorageLibrary.frame.setVisible(false);
        AudioUtility.Music.stopMusic();

        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "JSONSYNEXCP#"+clas+"#"+line+": The Json Reader/Writer has Encountered Invalid Json Syntax!\n\nPlease press the report a bug button in the settings and describe the bug, give detailed steps on how to reproduce the bug, and the error code ie. IOEXCP#INPUT#174",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                UIManager.getIcon("OptionPane.errorIcon"),
                responses,
                responses[0]);

        System.exit(0);
    }

    public static void FontFormatException(String clas, int line) {
        StorageLibrary.logger.error("FONTFORMEXCP#"+clas+"#"+line+": The Given Font Has A Invalid Format!");

        FlatLaf.unregisterCustomDefaultsSource("assets.buttonclicker.config");

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);

        StorageLibrary.frame.setVisible(false);
        AudioUtility.Music.stopMusic();

        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "FONTFORMEXCP#"+clas+"#"+line+": The Given Font Has A Invalid Format!\n\nPlease press the report a bug button in the settings and describe the bug, give detailed steps on how to reproduce the bug, and the error code ie. IOEXCP#INPUT#174",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                UIManager.getIcon("OptionPane.errorIcon"),
                responses,
                responses[0]);

        System.exit(0);
    }

    public static void ExecutionException(String clas, int line) {
        StorageLibrary.logger.error("EXEEXCP#"+clas+"#"+line+": A Execution Error Has Occurred!");

        FlatLaf.unregisterCustomDefaultsSource("assets.buttonclicker.config");

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);

        StorageLibrary.frame.setVisible(false);
        AudioUtility.Music.stopMusic();

        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "EXEEXCP#"+clas+"#"+line+": A Execution Error Has Occurred!\n\nPlease press the report a bug button in the settings and describe the bug, give detailed steps on how to reproduce the bug, and the error code ie. IOEXCP#INPUT#174",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                UIManager.getIcon("OptionPane.errorIcon"),
                responses,
                responses[0]);

        System.exit(0);
    }
}