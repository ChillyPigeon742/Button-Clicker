package net.alek.buttonclicker.data.save;

import java.math.BigInteger;

public record SaveData(String saveName, boolean playedBefore, byte side, BigInteger clicks, BigInteger clickPower) {}