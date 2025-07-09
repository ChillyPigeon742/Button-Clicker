package net.alek.buttonclicker.utilities.write.saves;

import net.alek.buttonclicker.components.ATimer;
import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.engine.MenuManager;
import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.services.RenderService;
import net.alek.buttonclicker.utilities.read.ReadUtility;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SaveWriter {
    public static Byte saveBeingCreated = null;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> scheduledFuture;

    public static void startAutosaveTimer() {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            scheduledFuture = scheduler.scheduleAtFixedRate(() -> SwingUtilities.invokeLater(() -> {
                if(MenuManager.isMenuOpen("Game")){
                    LoggingService.Logger.info("Saving...");

                    RenderService.titleImage.startSpinning();
                    RenderService.titleImage.setBounds(535, 110, 110, 110);
                    RenderService.titleImage.setVisible(true);
                    RenderService.autoSavingText.setVisible(true);

                    ATimer timer = new ATimer();
                    timer.setInterval(2);
                    timer.setTask(() -> {
                        if(MenuManager.isMenuOpen("Game")){
                            RenderService.titleImage.stopSpinning();
                            RenderService.titleImage.setBounds(10, 0, 100, 100);
                            RenderService.titleImage.setVisible(false);
                            RenderService.autoSavingText.setVisible(false);
                        }
                        saveCurrentSave();
                    });
                    timer.start();
                }
            }), 60, 60, TimeUnit.SECONDS);
        }
    }

    public static void createSave(byte saveNumber){
        String filePath = "Saves/save" + saveNumber + ".bcs";
        try {
            File save = new File(filePath);
            save.createNewFile();
        } catch (IOException e) {
            ErrorHandler.Exception(e);
        }
    }

    public static void deleteSave(byte saveNumber){
        String filePath = "Saves/save" + saveNumber + ".bcs";
        File save = new File(filePath);
        save.delete();
    }

    public static void writeSave(Map<String, Object> saveData, int saveNumber){
        LoggingService.Logger.info("Writing map to save file "+saveNumber);
        String filePath = "Saves/save" + saveNumber + ".bcs";

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(saveData);
        } catch (IOException e) {
            ErrorHandler.Exception(e);
        }
        LoggingService.Logger.info("Finished writing successfully!");
    }

    public static void saveCurrentSave(){
        if (ReadUtility.getCurrentSave() == null) {
            LoggingService.Logger.error("No save has been selected to write to!");
        } else {
            writeSave(ReadUtility.getCurrentSave(), ReadUtility.getCurrentSaveNumber());
        }
    }

    public static void Wipe(int save){
        if(save == 1){
            ReadUtility.save1 = null;
        }else if(save == 2){
            ReadUtility.save2 = null;
        }else if(save == 3){
            ReadUtility.save3 = null;
        }
    }
}
