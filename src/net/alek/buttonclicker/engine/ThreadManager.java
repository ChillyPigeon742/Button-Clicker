package net.alek.buttonclicker.engine;

import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.utilities.MessageUtility;

public class ThreadManager {
    public static Thread integerLimitMessageThread() {
        return new Thread(() -> {
            AudioService.SFX.playSFX("declined");
            MessageUtility.IntegerLimitMessage();
        });
    }

    public static Thread alreadyDeletedMessageThread() {
        return new Thread(MessageUtility::AlreadyDeletedMessage);
    }

    public static Thread notAllowedSaveNames() {
        return new Thread(() -> {
            AudioService.SFX.playSFX("declined");
            MessageUtility.NotAllowedSaveNames();
        });
    }
}