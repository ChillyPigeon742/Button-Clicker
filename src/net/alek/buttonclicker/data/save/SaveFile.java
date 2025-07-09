package net.alek.buttonclicker.data.save;

import java.math.BigInteger;

public class SaveFile {
    private int version;
    private SaveData save1;
    private SaveData save2;
    private SaveData save3;

    public SaveFile(int version, SaveData save1, SaveData save2, SaveData save3) {
        this.version = version;
        this.save1 = save1;
        this.save2 = save2;
        this.save3 = save3;
    }

    // Version getters/setters
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    // Save1 getters/setters for full SaveData
    public SaveData getSave1() {
        return save1;
    }

    public void setSave1(SaveData save1) {
        this.save1 = save1;
    }

    // Save2 getters/setters for full SaveData
    public SaveData getSave2() {
        return save2;
    }

    public void setSave2(SaveData save2) {
        this.save2 = save2;
    }

    // Save3 getters/setters for full SaveData
    public SaveData getSave3() {
        return save3;
    }

    public void setSave3(SaveData save3) {
        this.save3 = save3;
    }

    // ------------- Save1 field-level getters/setters -------------

    public String getSave1Name() {
        return save1.saveName();
    }

    public void setSave1Name(String saveName) {
        save1 = new SaveData(saveName, save1.playedBefore(), save1.side(), save1.clicks(), save1.clickPower());
    }

    public boolean getSave1PlayedBefore() {
        return save1.playedBefore();
    }

    public void setSave1PlayedBefore(boolean playedBefore) {
        save1 = new SaveData(save1.saveName(), playedBefore, save1.side(), save1.clicks(), save1.clickPower());
    }

    public byte getSave1Side() {
        return save1.side();
    }

    public void setSave1Side(byte side) {
        save1 = new SaveData(save1.saveName(), save1.playedBefore(), side, save1.clicks(), save1.clickPower());
    }

    public BigInteger getSave1Clicks() {
        return save1.clicks();
    }

    public void setSave1Clicks(BigInteger clicks) {
        save1 = new SaveData(save1.saveName(), save1.playedBefore(), save1.side(), clicks, save1.clickPower());
    }

    public BigInteger getSave1ClickPower() {
        return save1.clickPower();
    }

    public void setSave1ClickPower(BigInteger clickPower) {
        save1 = new SaveData(save1.saveName(), save1.playedBefore(), save1.side(), save1.clicks(), clickPower);
    }

    // ------------- Save2 field-level getters/setters -------------

    public String getSave2Name() {
        return save2.saveName();
    }

    public void setSave2Name(String saveName) {
        save2 = new SaveData(saveName, save2.playedBefore(), save2.side(), save2.clicks(), save2.clickPower());
    }

    public boolean getSave2PlayedBefore() {
        return save2.playedBefore();
    }

    public void setSave2PlayedBefore(boolean playedBefore) {
        save2 = new SaveData(save2.saveName(), playedBefore, save2.side(), save2.clicks(), save2.clickPower());
    }

    public byte getSave2Side() {
        return save2.side();
    }

    public void setSave2Side(byte side) {
        save2 = new SaveData(save2.saveName(), save2.playedBefore(), side, save2.clicks(), save2.clickPower());
    }

    public BigInteger getSave2Clicks() {
        return save2.clicks();
    }

    public void setSave2Clicks(BigInteger clicks) {
        save2 = new SaveData(save2.saveName(), save2.playedBefore(), save2.side(), clicks, save2.clickPower());
    }

    public BigInteger getSave2ClickPower() {
        return save2.clickPower();
    }

    public void setSave2ClickPower(BigInteger clickPower) {
        save2 = new SaveData(save2.saveName(), save2.playedBefore(), save2.side(), save2.clicks(), clickPower);
    }

    // ------------- Save3 field-level getters/setters -------------

    public String getSave3Name() {
        return save3.saveName();
    }

    public void setSave3Name(String saveName) {
        save3 = new SaveData(saveName, save3.playedBefore(), save3.side(), save3.clicks(), save3.clickPower());
    }

    public boolean getSave3PlayedBefore() {
        return save3.playedBefore();
    }

    public void setSave3PlayedBefore(boolean playedBefore) {
        save3 = new SaveData(save3.saveName(), playedBefore, save3.side(), save3.clicks(), save3.clickPower());
    }

    public byte getSave3Side() {
        return save3.side();
    }

    public void setSave3Side(byte side) {
        save3 = new SaveData(save3.saveName(), save3.playedBefore(), side, save3.clicks(), save3.clickPower());
    }

    public BigInteger getSave3Clicks() {
        return save3.clicks();
    }

    public void setSave3Clicks(BigInteger clicks) {
        save3 = new SaveData(save3.saveName(), save3.playedBefore(), save3.side(), clicks, save3.clickPower());
    }

    public BigInteger getSave3ClickPower() {
        return save3.clickPower();
    }

    public void setSave3ClickPower(BigInteger clickPower) {
        save3 = new SaveData(save3.saveName(), save3.playedBefore(), save3.side(), save3.clicks(), clickPower);
    }
}