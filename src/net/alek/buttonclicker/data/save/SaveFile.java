package net.alek.buttonclicker.data.save;

import java.math.BigInteger;

public class SaveFile {
    private String version;
    private String engine;
    private SaveSlot currentSave;
    private SavesHolder savesHolder;

    public SaveFile(String version, String engine, SaveSlot currentSave, SavesHolder savesHolder) {
        this.version = version;
        this.engine = engine;
        this.currentSave = currentSave;
        this.savesHolder = savesHolder;
    }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getEngine() { return engine; }
    public void setEngine(String engine) { this.engine = engine; }

    public SaveSlot getCurrentSave() { return currentSave; }
    public void setCurrentSave(SaveSlot currentSave) { this.currentSave = currentSave; }

    public SavesHolder getSavesHolder() { return savesHolder; }
    public void setSavesHolder(SavesHolder savesHolder) { this.savesHolder = savesHolder; }

    public SaveData getCurrentSaveData() {
        if (currentSave == null) return null;
        return switch (currentSave) {
            case SAVE_1 -> getSave1();
            case SAVE_2 -> getSave2();
            case SAVE_3 -> getSave3();
        };
    }

    public void setCurrentSaveData(SaveData data) {
        if (data == null || currentSave == null) return;
        switch (currentSave) {
            case SAVE_1 -> setSave1(data);
            case SAVE_2 -> setSave2(data);
            case SAVE_3 -> setSave3(data);
        }
    }

    public SaveData getSave1() { return savesHolder != null ? savesHolder.save1() : null; }
    public void setSave1(SaveData save1) {
        savesHolder = new SavesHolder(save1, getSave2(), getSave3());
    }

    public SaveData getSave2() { return savesHolder != null ? savesHolder.save2() : null; }
    public void setSave2(SaveData save2) {
        savesHolder = new SavesHolder(getSave1(), save2, getSave3());
    }

    public SaveData getSave3() { return savesHolder != null ? savesHolder.save3() : null; }
    public void setSave3(SaveData save3) {
        savesHolder = new SavesHolder(getSave1(), getSave2(), save3);
    }

    // --- Generic getters for SaveData fields with default fallbacks ---
    private String getSaveName(SaveData save) {
        return save != null ? save.saveName() : null;
    }

    private boolean getSavePlayedBefore(SaveData save) {
        return save != null && save.playedBefore();
    }

    private Side getSaveSide(SaveData save) {
        return save != null ? save.side() : null;
    }

    private BigInteger getSaveClicks(SaveData save) {
        return save != null ? save.clicks() : BigInteger.ZERO;
    }

    private BigInteger getSaveClickPower(SaveData save) {
        return save != null ? save.clickPower() : BigInteger.ZERO;
    }

    // --- Generic setters helper method ---
    private SaveData createSaveData(String name, boolean playedBefore, Side side, BigInteger clicks, BigInteger clickPower) {
        return new SaveData(name, playedBefore, side, clicks, clickPower);
    }

    // --- CurrentSave field-level getters/setters ---
    public String getCurrentSaveName() {
        return getSaveName(getCurrentSaveData());
    }

    public void setCurrentSaveName(String saveName) {
        updateCurrentSave(save -> createSaveData(
                saveName,
                getSavePlayedBefore(save),
                getSaveSide(save),
                getSaveClicks(save),
                getSaveClickPower(save)
        ));
    }

    public boolean getCurrentSavePlayedBefore() {
        return getSavePlayedBefore(getCurrentSaveData());
    }

    public void setCurrentSavePlayedBefore(boolean playedBefore) {
        updateCurrentSave(save -> createSaveData(
                getSaveName(save),
                playedBefore,
                getSaveSide(save),
                getSaveClicks(save),
                getSaveClickPower(save)
        ));
    }

    public Side getCurrentSaveSide() {
        return getSaveSide(getCurrentSaveData());
    }

    public void setCurrentSaveSide(Side side) {
        updateCurrentSave(save -> createSaveData(
                getSaveName(save),
                getSavePlayedBefore(save),
                side,
                getSaveClicks(save),
                getSaveClickPower(save)
        ));
    }

    public BigInteger getCurrentSaveClicks() {
        return getSaveClicks(getCurrentSaveData());
    }

    public void setCurrentSaveClicks(BigInteger clicks) {
        updateCurrentSave(save -> createSaveData(
                getSaveName(save),
                getSavePlayedBefore(save),
                getSaveSide(save),
                clicks,
                getSaveClickPower(save)
        ));
    }

    public BigInteger getCurrentSaveClickPower() {
        return getSaveClickPower(getCurrentSaveData());
    }

    public void setCurrentSaveClickPower(BigInteger clickPower) {
        updateCurrentSave(save -> createSaveData(
                getSaveName(save),
                getSavePlayedBefore(save),
                getSaveSide(save),
                getSaveClicks(save),
                clickPower
        ));
    }

    private void updateCurrentSave(java.util.function.Function<SaveData, SaveData> updater) {
        SaveData current = getCurrentSaveData();
        if (current == null || currentSave == null) return;
        SaveData updated = updater.apply(current);
        setCurrentSaveData(updated);
    }

    // --- Save1 field-level getters/setters ---
    public String getSave1Name() { return getSaveName(getSave1()); }
    public void setSave1Name(String name) { setSave1(createSaveData(name, getSave1PlayedBefore(), getSave1Side(), getSave1Clicks(), getSave1ClickPower())); }

    public boolean getSave1PlayedBefore() { return getSavePlayedBefore(getSave1()); }
    public void setSave1PlayedBefore(boolean playedBefore) { setSave1(createSaveData(getSave1Name(), playedBefore, getSave1Side(), getSave1Clicks(), getSave1ClickPower())); }

    public Side getSave1Side() { return getSaveSide(getSave1()); }
    public void setSave1Side(Side side) { setSave1(createSaveData(getSave1Name(), getSave1PlayedBefore(), side, getSave1Clicks(), getSave1ClickPower())); }

    public BigInteger getSave1Clicks() { return getSaveClicks(getSave1()); }
    public void setSave1Clicks(BigInteger clicks) { setSave1(createSaveData(getSave1Name(), getSave1PlayedBefore(), getSave1Side(), clicks, getSave1ClickPower())); }

    public BigInteger getSave1ClickPower() { return getSaveClickPower(getSave1()); }
    public void setSave1ClickPower(BigInteger clickPower) { setSave1(createSaveData(getSave1Name(), getSave1PlayedBefore(), getSave1Side(), getSave1Clicks(), clickPower)); }

    // --- Save2 field-level getters/setters ---
    public String getSave2Name() { return getSaveName(getSave2()); }
    public void setSave2Name(String name) { setSave2(createSaveData(name, getSave2PlayedBefore(), getSave2Side(), getSave2Clicks(), getSave2ClickPower())); }

    public boolean getSave2PlayedBefore() { return getSavePlayedBefore(getSave2()); }
    public void setSave2PlayedBefore(boolean playedBefore) { setSave2(createSaveData(getSave2Name(), playedBefore, getSave2Side(), getSave2Clicks(), getSave2ClickPower())); }

    public Side getSave2Side() { return getSaveSide(getSave2()); }
    public void setSave2Side(Side side) { setSave2(createSaveData(getSave2Name(), getSave2PlayedBefore(), side, getSave2Clicks(), getSave2ClickPower())); }

    public BigInteger getSave2Clicks() { return getSaveClicks(getSave2()); }
    public void setSave2Clicks(BigInteger clicks) { setSave2(createSaveData(getSave2Name(), getSave2PlayedBefore(), getSave2Side(), clicks, getSave2ClickPower())); }

    public BigInteger getSave2ClickPower() { return getSaveClickPower(getSave2()); }
    public void setSave2ClickPower(BigInteger clickPower) { setSave2(createSaveData(getSave2Name(), getSave2PlayedBefore(), getSave2Side(), getSave2Clicks(), clickPower)); }

    // --- Save3 field-level getters/setters ---
    public String getSave3Name() { return getSaveName(getSave3()); }
    public void setSave3Name(String name) { setSave3(createSaveData(name, getSave3PlayedBefore(), getSave3Side(), getSave3Clicks(), getSave3ClickPower())); }

    public boolean getSave3PlayedBefore() { return getSavePlayedBefore(getSave3()); }
    public void setSave3PlayedBefore(boolean playedBefore) { setSave3(createSaveData(getSave3Name(), playedBefore, getSave3Side(), getSave3Clicks(), getSave3ClickPower())); }

    public Side getSave3Side() { return getSaveSide(getSave3()); }
    public void setSave3Side(Side side) { setSave3(createSaveData(getSave3Name(), getSave3PlayedBefore(), side, getSave3Clicks(), getSave3ClickPower())); }

    public BigInteger getSave3Clicks() { return getSaveClicks(getSave3()); }
    public void setSave3Clicks(BigInteger clicks) { setSave3(createSaveData(getSave3Name(), getSave3PlayedBefore(), getSave3Side(), clicks, getSave3ClickPower())); }

    public BigInteger getSave3ClickPower() { return getSaveClickPower(getSave3()); }
    public void setSave3ClickPower(BigInteger clickPower) { setSave3(createSaveData(getSave3Name(), getSave3PlayedBefore(), getSave3Side(), getSave3Clicks(), clickPower)); }
}