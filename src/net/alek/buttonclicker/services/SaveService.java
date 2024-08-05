package net.alek.buttonclicker.services;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.libraries.ATimer;
import net.alek.buttonclicker.libraries.StorageLibrary;

import javax.swing.*;
import java.io.*;
import java.util.Objects;
import java.util.concurrent.*;

public class SaveService {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> scheduledFuture;

    public static void startAutosaveTimer() {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
                SwingUtilities.invokeLater(() -> {
                    if(StorageLibrary.buttonTop.isVisible()){
                        StorageLibrary.logger.info("Saving...");

                        StorageLibrary.titleImage.startSpinning();
                        StorageLibrary.titleImage.setBounds(535, 110, 110, 110);
                        StorageLibrary.titleImage.setVisible(true);

                        StorageLibrary.autoSavingText.setVisible(true);

                        ATimer timer = new ATimer();
                        timer.setDelay(2);
                        timer.setTask(() -> {
                            if(!StorageLibrary.titleText.isVisible()){
                                StorageLibrary.titleImage.stopSpinning();
                                StorageLibrary.titleImage.setBounds(10, 0, 100, 100);
                                StorageLibrary.titleImage.setVisible(false);

                                StorageLibrary.autoSavingText.setVisible(false);
                            }

                            if (Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")) {
                                SaveService.save1();
                            } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")) {
                                SaveService.save2();
                            } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")) {
                                SaveService.save3();
                            }
                        });
                        timer.start();
                    }
                });
            }, 60, 60, TimeUnit.SECONDS);
        }
    }

    public static void createSave(String filePath){
        try {
            File save = new File(filePath);
            save.createNewFile();
        } catch (IOException e) {
            ErrorHandler.IOException("SAVE", 60);
        }
    }

    public static void deleteSave(String filePath){
        File save = new File(filePath);
        save.delete();
    }

    public static void save1() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Saves/save1.bcs"));
            oos.writeLong(StorageLibrary.clicks1);
            oos.writeLong(StorageLibrary.clickPower1);
            oos.writeByte(StorageLibrary.side1);
            oos.writeUTF(StorageLibrary.save1Name);
            oos.writeBoolean(StorageLibrary.playedBefore1);
            oos.close();
        } catch (IOException e) {
        }
    }

    public static void save2() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Saves/save2.bcs"));
            oos.writeLong(StorageLibrary.clicks2);
            oos.writeLong(StorageLibrary.clickPower2);
            oos.writeByte(StorageLibrary.side2);
            oos.writeUTF(StorageLibrary.save2Name);
            oos.writeBoolean(StorageLibrary.playedBefore2);
            oos.close();
        } catch (IOException e) {
        }
    }

    public static void save3() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Saves/save3.bcs"));
            oos.writeLong(StorageLibrary.clicks3);
            oos.writeLong(StorageLibrary.clickPower3);
            oos.writeByte(StorageLibrary.side3);
            oos.writeUTF(StorageLibrary.save3Name);
            oos.writeBoolean(StorageLibrary.playedBefore3);
            oos.close();
        } catch (IOException e) {
        }
    }

    public static void Wipe(int save){
        if(save == 1){
            StorageLibrary.save1Name = "null";
            StorageLibrary.clicks1 = 0L;
            StorageLibrary.clickPower1 = 1L;
            StorageLibrary.side1 = 0;
            StorageLibrary.playedBefore1 = false;
        }else if(save == 2){
            StorageLibrary.save2Name = "null";
            StorageLibrary.clicks2 = 0L;
            StorageLibrary.clickPower2 = 1L;
            StorageLibrary.side2 = 0;
            StorageLibrary.playedBefore2 = false;
        }else if(save == 3){
            StorageLibrary.save3Name = "null";
            StorageLibrary.clicks3 = 0L;
            StorageLibrary.clickPower3 = 1L;
            StorageLibrary.side3 = 0;
            StorageLibrary.playedBefore3 = false;
        }
    }
}