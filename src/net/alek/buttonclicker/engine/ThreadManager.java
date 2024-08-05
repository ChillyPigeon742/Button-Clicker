package net.alek.buttonclicker.engine;

import net.alek.buttonclicker.libraries.ATimer;
import net.alek.buttonclicker.utilities.AudioUtility;
import net.alek.buttonclicker.utilities.MessageUtility;

public class ThreadManager {
    public static Thread exitThread = new Thread(new Runnable() {
        @Override
        public void run() {
            ATimer timer = new ATimer();
            timer.setDelay(2);
            timer.setTask(() -> {
                System.exit(0);
            });
            timer.start();
        }
    });

    public static Thread integerLimitMessageThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                AudioUtility.SFX.playSFX("declined");
                MessageUtility.IntegerLimitMessage();
            }
        });
    }

    public static Thread alreadyDeletedMessageThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                MessageUtility.AlreadyDeletedMessage();
            }
        });
    }

    public static Thread trollMessageThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                MessageUtility.TrollMessage();
            }
        });
    }

    public static Thread notAllowedSaveNames() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                AudioUtility.SFX.playSFX("declined");
                MessageUtility.NotAllowedSaveNames();
            }
        });
    }
}