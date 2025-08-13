package net.alek.buttonclicker.audio;

import net.alek.buttonclicker.transfer.request.type.Request;
import net.alek.buttonclicker.transfer.request.payload.SoundManagerClassPayload;

public class Audio {
    private static final Audio INSTANCE = new Audio();
    private static Music music;
    private static SFX sfx;

    private double masterVolume;
    private byte masterUserVolume;
    private byte musicUserVolume;
    private byte sfxUserVolume;

    static {
        Request.GET_SOUND_MANAGER.handle(() -> new SoundManagerClassPayload(INSTANCE));
    }

    public static Audio getInstance() {
        return INSTANCE;
    }

    private Audio() {}

    public void init() {
        music = new Music();
        sfx = new SFX();
    }

    public void setMasterVolume(byte value) {
        masterUserVolume = value;
        masterVolume = value / 100.0;
        updateVolumes();
    }

    public void setMusicVolume(byte value) {
        musicUserVolume = value;
        updateVolumes();
    }

    public void setSFXVolume(byte value) {
        sfxUserVolume = value;
        updateVolumes();
    }

    private void updateVolumes() {
        music.setMusicVolume(masterVolume * (musicUserVolume / 100.0));
        sfx.setSFXVolume(masterVolume * (sfxUserVolume / 100.0));
    }

    public double getMasterVolume() {
        return masterVolume;
    }

    public byte getMusicUserVolume() {
        return musicUserVolume;
    }

    public byte getSFXUserVolume() {
        return sfxUserVolume;
    }

    public Music getMusic() {
        return music;
    }

    public SFX getSFX() {
        return sfx;
    }
}