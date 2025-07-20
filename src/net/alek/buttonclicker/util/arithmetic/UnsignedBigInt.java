package net.alek.buttonclicker.util.arithmetic;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public final class UnsignedBigInt extends Number implements Comparable<UnsignedBigInt>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int bits;
    private final BigInteger maxValue;
    private final boolean throwOnOverflow;
    private BigInteger value;

    public UnsignedBigInt(int bits) {
        this(bits, false);
    }

    public UnsignedBigInt(int bits, boolean throwOnOverflow) {
        if (bits < 65)
            throw new IllegalArgumentException("Bits must be >= 65");

        this.bits = bits;
        this.throwOnOverflow = throwOnOverflow;
        this.maxValue = BigInteger.ONE.shiftLeft(bits).subtract(BigInteger.ONE);
        this.value = BigInteger.ZERO;
    }

    private UnsignedBigInt(int bits, BigInteger value, boolean throwOnOverflow) {
        this(bits, throwOnOverflow);
        setValue(value);
    }

    public static UnsignedBigInt valueOf(int bits, BigInteger value) {
        return new UnsignedBigInt(bits, value, false);
    }

    public static UnsignedBigInt parseUnsigned(int bits, String input) {
        return parseUnsigned(bits, input, false);
    }

    public static UnsignedBigInt parseUnsigned(int bits, String input, boolean throwOnOverflow) {
        Objects.requireNonNull(input, "Input string cannot be null");
        BigInteger parsed;

        if (input.startsWith("0x") || input.startsWith("0X")) {
            parsed = new BigInteger(input.substring(2), 16);
        } else if (input.startsWith("0b") || input.startsWith("0B")) {
            parsed = new BigInteger(input.substring(2), 2);
        } else {
            parsed = new BigInteger(input);
        }

        return new UnsignedBigInt(bits, parsed, throwOnOverflow);
    }

    private interface BigIntegerOperation {
        BigInteger apply(BigInteger a, BigInteger b);
    }

    private UnsignedBigInt applyOperation(BigInteger val, BigIntegerOperation op) {
        return new UnsignedBigInt(bits, op.apply(value, val), throwOnOverflow);
    }

    private UnsignedBigInt applyOperation(UnsignedBigInt other, BigIntegerOperation op) {
        checkCompatible(other);
        return new UnsignedBigInt(bits, op.apply(value, other.value), throwOnOverflow);
    }

    private UnsignedBigInt mutableApplyOperation(BigInteger val, BigIntegerOperation op) {
        setValue(op.apply(value, val));
        return this;
    }

    private UnsignedBigInt mutableApplyOperation(UnsignedBigInt other, BigIntegerOperation op) {
        checkCompatible(other);
        setValue(op.apply(value, other.value));
        return this;
    }

    public UnsignedBigInt add(BigInteger val) {
        return applyOperation(val, BigInteger::add);
    }

    public UnsignedBigInt subtract(BigInteger val) {
        return applyOperation(val, BigInteger::subtract);
    }

    public UnsignedBigInt multiply(BigInteger val) {
        return applyOperation(val, BigInteger::multiply);
    }

    public UnsignedBigInt divide(BigInteger val) {
        checkZero(val);
        return applyOperation(val, BigInteger::divide);
    }

    public UnsignedBigInt mod(BigInteger val) {
        checkZero(val);
        return applyOperation(val, BigInteger::mod);
    }

    public UnsignedBigInt add(UnsignedBigInt other) {
        return applyOperation(other, BigInteger::add);
    }

    public UnsignedBigInt subtract(UnsignedBigInt other) {
        return applyOperation(other, BigInteger::subtract);
    }

    public UnsignedBigInt multiply(UnsignedBigInt other) {
        return applyOperation(other, BigInteger::multiply);
    }

    public UnsignedBigInt divide(UnsignedBigInt other) {
        checkZero(other.value);
        return applyOperation(other, BigInteger::divide);
    }

    public UnsignedBigInt mod(UnsignedBigInt other) {
        checkZero(other.value);
        return applyOperation(other, BigInteger::mod);
    }

    public UnsignedBigInt mutableAdd(BigInteger val) {
        return mutableApplyOperation(val, BigInteger::add);
    }

    public UnsignedBigInt mutableSubtract(BigInteger val) {
        return mutableApplyOperation(val, BigInteger::subtract);
    }

    public UnsignedBigInt mutableMultiply(BigInteger val) {
        return mutableApplyOperation(val, BigInteger::multiply);
    }

    public UnsignedBigInt mutableDivide(BigInteger val) {
        checkZero(val);
        return mutableApplyOperation(val, BigInteger::divide);
    }

    public UnsignedBigInt mutableMod(BigInteger val) {
        checkZero(val);
        return mutableApplyOperation(val, BigInteger::mod);
    }

    public UnsignedBigInt mutableAdd(UnsignedBigInt other) {
        return mutableApplyOperation(other, BigInteger::add);
    }

    public UnsignedBigInt mutableSubtract(UnsignedBigInt other) {
        return mutableApplyOperation(other, BigInteger::subtract);
    }

    public UnsignedBigInt mutableMultiply(UnsignedBigInt other) {
        return mutableApplyOperation(other, BigInteger::multiply);
    }

    public UnsignedBigInt mutableDivide(UnsignedBigInt other) {
        checkZero(other.value);
        return mutableApplyOperation(other, BigInteger::divide);
    }

    public UnsignedBigInt mutableMod(UnsignedBigInt other) {
        checkZero(other.value);
        return mutableApplyOperation(other, BigInteger::mod);
    }

    public UnsignedBigInt and(BigInteger val) {
        return applyOperation(val, BigInteger::and);
    }

    public UnsignedBigInt or(BigInteger val) {
        return applyOperation(val, BigInteger::or);
    }

    public UnsignedBigInt xor(BigInteger val) {
        return applyOperation(val, BigInteger::xor);
    }

    public UnsignedBigInt and(UnsignedBigInt other) {
        return applyOperation(other, BigInteger::and);
    }

    public UnsignedBigInt or(UnsignedBigInt other) {
        return applyOperation(other, BigInteger::or);
    }

    public UnsignedBigInt xor(UnsignedBigInt other) {
        return applyOperation(other, BigInteger::xor);
    }

    public UnsignedBigInt not() {
        return new UnsignedBigInt(bits, value.not().and(maxValue), throwOnOverflow);
    }

    public UnsignedBigInt shiftLeft(int n) {
        if (n < 0) return shiftRightUnsigned(-n);
        return new UnsignedBigInt(bits, value.shiftLeft(n), throwOnOverflow);
    }

    public UnsignedBigInt shiftRightUnsigned(int n) {
        if (n < 0) return shiftLeft(-n);
        return new UnsignedBigInt(bits, value.shiftRight(n), throwOnOverflow);
    }

    public UnsignedBigInt mutableAnd(BigInteger val) {
        return mutableApplyOperation(val, BigInteger::and);
    }

    public UnsignedBigInt mutableOr(BigInteger val) {
        return mutableApplyOperation(val, BigInteger::or);
    }

    public UnsignedBigInt mutableXor(BigInteger val) {
        return mutableApplyOperation(val, BigInteger::xor);
    }

    public UnsignedBigInt mutableAnd(UnsignedBigInt other) {
        return mutableApplyOperation(other, BigInteger::and);
    }

    public UnsignedBigInt mutableOr(UnsignedBigInt other) {
        return mutableApplyOperation(other, BigInteger::or);
    }

    public UnsignedBigInt mutableXor(UnsignedBigInt other) {
        return mutableApplyOperation(other, BigInteger::xor);
    }

    public UnsignedBigInt mutableNot() {
        setValue(value.not().and(maxValue));
        return this;
    }

    public UnsignedBigInt mutableShiftLeft(int n) {
        if (n < 0) return mutableShiftRightUnsigned(-n);
        setValue(value.shiftLeft(n));
        return this;
    }

    public UnsignedBigInt mutableShiftRightUnsigned(int n) {
        if (n < 0) return mutableShiftLeft(-n);
        setValue(value.shiftRight(n));
        return this;
    }

    public String toHexString() {
        return toHexString(false);
    }

    public String toHexString(boolean fullWidth) {
        String hex = value.toString(16);
        if (fullWidth) {
            int digits = (bits + 3) / 4;
            hex = String.format("%0" + digits + "x", new BigInteger(hex, 16));
        }
        return "0x" + hex;
    }

    public String toBinaryString() {
        return toBinaryString(false);
    }

    public String toBinaryString(boolean fullWidth) {
        String binary = value.toString(2);
        if (fullWidth) {
            binary = String.format("%" + bits + "s", binary).replace(' ', '0');
        }
        return "0b" + binary;
    }

    public static UnsignedBigInt min(UnsignedBigInt a, UnsignedBigInt b) {
        a.checkCompatible(b);
        return a.compareTo(b) <= 0 ? a : b;
    }

    public static UnsignedBigInt max(UnsignedBigInt a, UnsignedBigInt b) {
        a.checkCompatible(b);
        return a.compareTo(b) >= 0 ? a : b;
    }

    public int getBits() {
        return bits;
    }

    private void checkOverflow(BigInteger val) {
        if (throwOnOverflow) {
            if (val.signum() < 0 || val.compareTo(maxValue) > 0) {
                throw new ArithmeticException("Overflow for unsigned " + bits + "-bit value");
            }
        }
    }

    private void checkZero(BigInteger val) {
        if (val.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Divide by zero");
        }
    }

    public void setValue(BigInteger val) {
        checkOverflow(val);
        this.value = val.and(maxValue);
    }

    public void setValue(String val) {
        setValue(parseUnsigned(bits, val).toBigInteger());
    }

    public void setValue(UnsignedBigInt other) {
        checkCompatible(other);
        this.value = other.value;
    }

    public UnsignedBigInt pow(int exp) {
        return new UnsignedBigInt(bits, value.pow(exp), throwOnOverflow);
    }

    public int log2() {
        return value.bitLength() - 1;
    }

    public boolean isPowerOfTwo() {
        return !isZero() && value.and(value.subtract(BigInteger.ONE)).equals(BigInteger.ZERO);
    }

    public String asHex() {
        return "0x" + value.toString(16).toUpperCase();
    }

    public String asBin() {
        return "0b" + value.toString(2);
    }

    public String asDecimal() {
        return value.toString();
    }

    public String toBitString() {
        String bin = value.toString(2);
        int len = bin.length();
        int pad = (4 - len % 4) % 4;
        StringBuilder sb = new StringBuilder();

        sb.append("0".repeat(pad));
        sb.append(bin);

        StringBuilder spaced = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            if (i > 0 && i % 4 == 0) spaced.append('_');
            spaced.append(sb.charAt(i));
        }

        return spaced.toString();
    }

    public void reset() {
        this.value = BigInteger.ZERO;
    }

    public boolean getBit(int index) {
        if (index < 0 || index >= bits)
            throw new IndexOutOfBoundsException("Bit index out of range");
        return value.testBit(index);
    }

    public void setBit(int index, boolean value) {
        if (index < 0 || index >= bits)
            throw new IndexOutOfBoundsException("Bit index out of range");
        this.value = value ? this.value.setBit(index) : this.value.clearBit(index);
    }

    @Override
    public int compareTo(UnsignedBigInt other) {
        checkCompatible(other);
        return this.value.compareTo(other.value);
    }

    private void checkCompatible(UnsignedBigInt other) {
        if (this.bits != other.bits)
            throw new IllegalArgumentException("Bit widths must match: " + this.bits + " != " + other.bits);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UnsignedBigInt other)) return false;
        return bits == other.bits && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return 31 * bits + value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    public byte toByte() {
        return value.byteValue();
    }

    public short toShort() {
        return value.shortValue();
    }

    public int toInt() {
        return value.intValue();
    }

    public long toLong() {
        return value.longValue();
    }

    public BigInteger toBigInteger() {
        return value;
    }

    public float toFloat() {
        return value.floatValue();
    }

    public double toDouble() {
        return value.doubleValue();
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(value);
    }

    public UnsignedBigInt copy() {
        return new UnsignedBigInt(bits, value, throwOnOverflow);
    }

    public boolean isZero() {
        return value.equals(BigInteger.ZERO);
    }

    public boolean isMaxValue() {
        return value.equals(maxValue);
    }

    public UnsignedBigInt increment() {
        return mutableAdd(BigInteger.ONE);
    }

    public UnsignedBigInt decrement() {
        return mutableSubtract(BigInteger.ONE);
    }

    public UnsignedBigInt mutableIncrement() {
        return mutableAdd(BigInteger.ONE);
    }

    public UnsignedBigInt mutableDecrement() {
        return mutableSubtract(BigInteger.ONE);
    }
}