package net.alek.buttonclicker.read.settings;

import net.alek.buttonclicker.read.json.JSONReader;
import net.alek.buttonclicker.services.AudioService;
import net.alek.buttonclicker.ui.RenderService;

import java.awt.*;
import java.util.Map;

public class SettingsReader {

    private static void loadSettings(){
        settings.put("currentSave", JSONReader.fromFile("Data/settings.json").readString("currentSave"));

        Object currentSaveKey = settings.get("currentSave");
        Map<String, Object> currentSave = getCurrentSave();

        if (currentSaveKey == null) {
            RenderService.startGameButton.setText("Select A Save To Continue");
            RenderService.startGameButton.setBackground(Color.GRAY);
            RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 21));
            RenderService.quitButton.setText("Quit");
            RenderService.currentSaveText.setText("Current Save: none");
        } else {
            RenderService.startGameButton.setBackground(Color.GREEN);
            RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 27));
            RenderService.quitButton.setText("Save & Quit");

            boolean playedBefore = currentSave != null && Boolean.TRUE.equals(currentSave.get("playedBefore"));
            RenderService.startGameButton.setText(playedBefore ? "Resume Game" : "Start Game");

            String saveName = currentSave != null ? currentSave.getOrDefault("saveName", "unknown").toString() : "unknown";
            RenderService.currentSaveText.setText("Current Save: " + saveName);
        }

        AudioService.SoundManager.setMasterVolume(JSONReader.fromFile("Data/settings.json").readByte("masterVolume"));
        RenderService.masterVolumeSlider.setValue(AudioService.SoundManager.masterVolumeUSER);

        AudioService.SoundManager.setMusicVolume(JSONReader.fromFile("Data/settings.json").readByte("musicVolume"));
        RenderService.musicVolumeSlider.setValue(AudioService.SoundManager.musicVolumeUSER);

        AudioService.SoundManager.setSFXVolume(JSONReader.fromFile("Data/settings.json").readByte("sfxVolume"));
        RenderService.sfxVolumeSlider.setValue(AudioService.SoundManager.sfxVolumeUSER);

        AudioService.SoundManager.setMusicDelay(JSONReader.fromFile("Data/settings.json").readByte("musicDelay"));
        RenderService.musicDelaySpinner.setValue(AudioService.SoundManager.getMusicDelay());
        RenderService.musicDelaySpinnerText.setText(AudioService.SoundManager.getMusicDelay()+" Secs");
    }
}
