package net.alek.buttonclicker.utilities;

import net.alek.buttonclicker.libraries.StorageLibrary;

import javax.swing.*;

public class MessageUtility {
    public static void IntegerLimitMessage() {
        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "Cannot Surpass 9223372036854775807, Pressing Back One Billion Clicks To Prevent Loop Back",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                StorageLibrary.errorIcon,
                responses,
                responses[0]);
    }

    public static int DeleteMessage(){
        String[] responses = {"Ok", "Cancel"};
        return JOptionPane.showOptionDialog(
                null,
                "Are You Sure?",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                StorageLibrary.questionIcon,
                responses,
                responses[0]);
    }

    public static int DeleteMessage2(){
        String[] responses = {"Ok", "Cancel"};
        return JOptionPane.showOptionDialog(
                null,
                "This will delete the save for a long time! (forever)",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                StorageLibrary.warningIcon,
                responses,
                responses[0]);
    }

    public static void AlreadyDeletedMessage(){
        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "That Save is Already Deleted Silly!",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                StorageLibrary.infoIcon,
                responses,
                responses[0]);
    }

    public static void TrollMessage(){
        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "you really thought huh",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                StorageLibrary.trollIcon,
                responses,
                responses[0]);
    }

    public static void NotAllowedSaveNames(){
        String[] responses = {"Ok"};
        JOptionPane.showOptionDialog(
                null,
                "Disallowed Save Names: null and (empty)",
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                StorageLibrary.errorIcon,
                responses,
                responses[0]);
    }
}