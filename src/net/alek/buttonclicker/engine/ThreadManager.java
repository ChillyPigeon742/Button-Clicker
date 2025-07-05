package net.alek.buttonclicker.engine;

import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.utilities.notify.MessageUtility;

public class ThreadManager {
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