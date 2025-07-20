package net.alek.buttonclicker.util.arithmetic;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public final class UnsignedLong extends Number implements Comparable<UnsignedLong>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int bits;
    private final long maxValue;
    private final boolean throwOnOverflow;
    private long value;

    public UnsignedLong(int bits) {
        this(bits, false);
    }

    public UnsignedLong(int bits, boolean throwOnOverflow) {
        if (bits < 1 || bits > 64)
            throw new IllegalArgumentException("Bits must be between 1 and 64");

        this.bits = bits;
        this.throwOnOverflow = throwOnOverflow;
        this.maxValue = bits == 64 ? -1L : (1L << bits) - 1;
        this.value = 0L;
    }

    public UnsignedLong(int bits, long value, boolean throwOnOverflow) {
        this(bits, throwOnOverflow);
        setValue(value);
    }

    public static UnsignedLong valueOf(int bits, long value) {
        return new UnsignedLong(bits, value, false);
    }

    public static UnsignedLong parseUnsigned(int bits, String input) {
        return parseUnsigned(bits, input, false);
    }

    public static UnsignedLong parseUnsigned(int bits, String input, boolean throwOnOverflow) {
        Objects.requireNonNull(input, "Input string cannot be null");
        BigInteger parsed;

        if (input.startsWith("0x") || input.startsWith("0X")) {
            parsed = new BigInteger(input.substring(2), 16);
        } else if (input.startsWith("0b") || input.startsWith("0B")) {
            parsed = new BigInteger(input.substring(2), 2);
        } else {
            parsed = new BigInteger(input);
        }

        return new UnsignedLong(bits, parsed.longValue(), throwOnOverflow);
    }

    public UnsignedLong add(long val) {
        return new UnsignedLong(bits, value + val, throwOnOverflow);
    }

    public UnsignedLong subtract(long val) {
        return new UnsignedLong(bits, value - val, throwOnOverflow);
    }

    public UnsignedLong multiply(long val) {
        return new UnsignedLong(bits, value * val, throwOnOverflow);
    }

    public UnsignedLong divide(long val) {
        checkZero(val);
        return new UnsignedLong(bits, Long.divideUnsigned(value, val), throwOnOverflow);
    }

    public UnsignedLong mod(long val) {
        checkZero(val);
        return new UnsignedLong(bits, Long.remainderUnsigned(value, val), throwOnOverflow);
    }

    public UnsignedLong add(UnsignedLong other) {
        checkCompatible(other);
        return new UnsignedLong(bits, value + other.value, throwOnOverflow);
    }

    public UnsignedLong subtract(UnsignedLong other) {
        checkCompatible(other);
        return new UnsignedLong(bits, value - other.value, throwOnOverflow);
    }

    public UnsignedLong multiply(UnsignedLong other) {
        checkCompatible(other);
        return new UnsignedLong(bits, value * other.value, throwOnOverflow);
    }

    public UnsignedLong divide(UnsignedLong other) {
        checkCompatible(other);
        checkZero(other.value);
        return new UnsignedLong(bits, Long.divideUnsigned(value, other.value), throwOnOverflow);
    }

    public UnsignedLong mod(UnsignedLong other) {
        checkCompatible(other);
        checkZero(other.value);
        return new UnsignedLong(bits, Long.remainderUnsigned(value, other.value), throwOnOverflow);
    }

    public UnsignedLong mutableAdd(long val) {
        setValue(value + val);
        return this;
    }

    public UnsignedLong mutableSubtract(long val) {
        setValue(value - val);
        return this;
    }

    public UnsignedLong mutableMultiply(long val) {
        setValue(value * val);
        return this;
    }

    public UnsignedLong mutableDivide(long val) {
        checkZero(val);
        setValue(Long.divideUnsigned(value, val));
        return this;
    }

    public UnsignedLong mutableMod(long val) {
        checkZero(val);
        setValue(Long.remainderUnsigned(value, val));
        return this;
    }

    public UnsignedLong mutableAdd(UnsignedLong other) {
        checkCompatible(other);
        setValue(value + other.value);
        return this;
    }

    public UnsignedLong mutableSubtract(UnsignedLong other) {
        checkCompatible(other);
        setValue(value - other.value);
        return this;
    }

    public UnsignedLong mutableMultiply(UnsignedLong other) {
        checkCompatible(other);
        setValue(value * other.value);
        return this;
    }

    public UnsignedLong mutableDivide(UnsignedLong other) {
        checkCompatible(other);
        checkZero(other.value);
        setValue(Long.divideUnsigned(value, other.value));
        return this;
    }

    public UnsignedLong mutableMod(UnsignedLong other) {
        checkCompatible(other);
        checkZero(other.value);
        setValue(Long.remainderUnsigned(value, other.value));
        return this;
    }

    public UnsignedLong and(long val) {
        return new UnsignedLong(bits, value & val, throwOnOverflow);
    }

    public UnsignedLong or(long val) {
        return new UnsignedLong(bits, value | val, throwOnOverflow);
    }

    public UnsignedLong xor(long val) {
        return new UnsignedLong(bits, value ^ val, throwOnOverflow);
    }

    public UnsignedLong and(UnsignedLong other) {
        checkCompatible(other);
        return new UnsignedLong(bits, value & other.value, throwOnOverflow);
    }

    public UnsignedLong or(UnsignedLong other) {
        checkCompatible(other);
        return new UnsignedLong(bits, value | other.value, throwOnOverflow);
    }

    public UnsignedLong xor(UnsignedLong other) {
        checkCompatible(other);
        return new UnsignedLong(bits, value ^ other.value, throwOnOverflow);
    }

    public UnsignedLong not() {
        return new UnsignedLong(bits, (~value) & maxValue, throwOnOverflow);
    }

    public UnsignedLong shiftLeft(int n) {
        if (n < 0) return shiftRightUnsigned(-n);
        return new UnsignedLong(bits, (value << n) & maxValue, throwOnOverflow);
    }

    public UnsignedLong shiftRightUnsigned(int n) {
        if (n < 0) return shiftLeft(-n);
        return new UnsignedLong(bits, value >>> n, throwOnOverflow);
    }

    public UnsignedLong mutableAnd(long val) {
        setValue(value & val);
        return this;
    }

    public UnsignedLong mutableOr(long val) {
        setValue(value | val);
        return this;
    }

    public UnsignedLong mutableXor(long val) {
        setValue(value ^ val);
        return this;
    }

    public UnsignedLong mutableAnd(UnsignedLong other) {
        checkCompatible(other);
        setValue(value & other.value);
        return this;
    }

    public UnsignedLong mutableOr(UnsignedLong other) {
        checkCompatible(other);
        setValue(value | other.value);
        return this;
    }

    public UnsignedLong mutableXor(UnsignedLong other) {
        checkCompatible(other);
        setValue(value ^ other.value);
        return this;
    }

    public UnsignedLong mutableNot() {
        setValue((~value) & maxValue);
        return this;
    }

    public UnsignedLong mutableShiftLeft(int n) {
        if (n < 0) return mutableShiftRightUnsigned(-n);
        setValue((value << n) & maxValue);
        return this;
    }

    public UnsignedLong mutableShiftRightUnsigned(int n) {
        if (n < 0) return mutableShiftLeft(-n);
        setValue(value >>> n);
        return this;
    }

    public String toHexString() {
        return toHexString(false);
    }

    public String toHexString(boolean fullWidth) {
        String hex = Long.toHexString(value);
        if (fullWidth) {
            int digits = (bits + 3) / 4;
            hex = String.format("%0" + digits + "x", value);
        }
        return "0x" + hex;
    }

    public String toBinaryString() {
        return toBinaryString(false);
    }

    public String toBinaryString(boolean fullWidth) {
        String binary = Long.toBinaryString(value);
        if (fullWidth) {
            binary = String.format("%" + bits + "s", binary).replace(' ', '0');
        }
        return "0b" + binary;
    }

    public static UnsignedLong min(UnsignedLong a, UnsignedLong b) {
        a.checkCompatible(b);
        return a.compareTo(b) <= 0 ? a : b;
    }

    public static UnsignedLong max(UnsignedLong a, UnsignedLong b) {
        a.checkCompatible(b);
        return a.compareTo(b) >= 0 ? a : b;
    }

    public int getBits() {
        return bits;
    }

    private void checkOverflow(long val) {
        if (throwOnOverflow) {
            if (val < 0 || (bits < 64 && val > maxValue)) {
                throw new ArithmeticException("Overflow for unsigned " + bits + "-bit value");
            }
        }
    }

    private void checkZero(long val) {
        if (val == 0L) {
            throw new ArithmeticException("Divide by zero");
        }
    }

    public void setValue(long val) {
        checkOverflow(val);
        this.value = val & maxValue;
    }

    public void setValue(String val) {
        setValue(parseUnsigned(bits, val).longValue());
    }

    public void setValue(UnsignedLong other) {
        checkCompatible(other);
        this.value = other.value;
    }

    public UnsignedLong pow(int exp) {
        return new UnsignedLong(bits, (long) Math.pow(value, exp), throwOnOverflow);
    }

    public int log2() {
        return Long.SIZE - Long.numberOfLeadingZeros(value) - 1;
    }

    public boolean isPowerOfTwo() {
        return value != 0 && (value & (value - 1)) == 0;
    }

    public String asHex() {
        return "0x" + Long.toHexString(value).toUpperCase();
    }

    public String asBin() {
        return "0b" + Long.toBinaryString(value);
    }

    public String asDecimal() {
        return Long.toUnsignedString(value);
    }

    public String toBitString() {
        String bin = Long.toBinaryString(value);
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
        this.value = 0L;
    }

    public boolean getBit(int index) {
        if (index < 0 || index >= bits)
            throw new IndexOutOfBoundsException("Bit index out of range");
        return (value & (1L << index)) != 0;
    }

    public void setBit(int index, boolean value) {
        if (index < 0 || index >= bits)
            throw new IndexOutOfBoundsException("Bit index out of range");
        if (value) {
            this.value |= (1L << index);
        } else {
            this.value &= ~(1L << index);
        }
    }

    @Override
    public int compareTo(UnsignedLong other) {
        checkCompatible(other);
        return Long.compareUnsigned(this.value, other.value);
    }

    private void checkCompatible(UnsignedLong other) {
        if (this.bits != other.bits)
            throw new IllegalArgumentException("Bit widths must match: " + this.bits + " != " + other.bits);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UnsignedLong other)) return false;
        return bits == other.bits && value == other.value;
    }

    @Override
    public int hashCode() {
        return 31 * bits + Long.hashCode(value);
    }

    @Override
    public String toString() {
        return Long.toUnsignedString(value);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    public byte toByte() {
        return (byte) value;
    }

    public short toShort() {
        return (short) value;
    }

    public int toInt() {
        return (int) value;
    }

    public long toLong() {
        return value;
    }

    public BigInteger toBigInteger() {
        return BigInteger.valueOf(value & maxValue);
    }

    public float toFloat() {
        return value;
    }

    public double toDouble() {
        return value;
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(value & maxValue);
    }

    public UnsignedLong copy() {
        return new UnsignedLong(bits, value, throwOnOverflow);
    }

    public boolean isZero() {
        return value == 0L;
    }

    public boolean isMaxValue() {
        return value == maxValue;
    }

    public UnsignedLong increment() {
        return mutableAdd(1);
    }

    public UnsignedLong decrement() {
        return mutableSubtract(1);
    }

    public UnsignedLong mutableIncrement() {
        return mutableAdd(1);
    }

    public UnsignedLong mutableDecrement() {
        return mutableSubtract(1);
    }
}