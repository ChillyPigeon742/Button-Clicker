package net.alek.buttonclicker.util;

public class Unsigned extends Number implements Comparable<Unsigned> {
    private long value;
    private final int bits;
    private final long maxValue;

    public Unsigned(int bits) {
        if (bits < 1 || bits > 64)
            throw new IllegalArgumentException("Bits must be between 1 and 64");

        this.bits = bits;
        this.maxValue = bits == 64 ? -1L : (1L << bits) - 1;
        this.value = 0;
    }

    public Unsigned(int bits, long initialValue) {
        this(bits);
        setValue(initialValue);
    }

    public long getValue() {
        return value;
    }

    public void setValue(long val) {
        value = val & maxValue;
    }

    public int getBits() {
        return bits;
    }

    public Unsigned add(long val) {
        setValue(value + val);
        return this;
    }

    public Unsigned subtract(long val) {
        setValue(value - val);
        return this;
    }

    public Unsigned multiply(long val) {
        setValue(value * val);
        return this;
    }

    public Unsigned divide(long val) {
        if (val == 0) throw new ArithmeticException("Divide by zero");
        setValue(Long.divideUnsigned(value, val));
        return this;
    }

    public Unsigned mod(long val) {
        if (val == 0) throw new ArithmeticException("Modulo by zero");
        setValue(Long.remainderUnsigned(value, val));
        return this;
    }

    public boolean getBit(int index) {
        if (index < 0 || index >= bits)
            throw new IndexOutOfBoundsException("Bit index out of range");
        return ((value >> index) & 1) == 1;
    }

    public void setBit(int index, boolean bit) {
        if (index < 0 || index >= bits)
            throw new IndexOutOfBoundsException("Bit index out of range");
        if (bit)
            value |= (1L << index);
        else
            value &= ~(1L << index);
        value &= maxValue;
    }

    public byte toByte() {
        return (byte) (value & 0xFF);
    }

    public short toShort() {
        return (short) (value & 0xFFFF);
    }

    public int toInt() {
        return (int) (value & 0xFFFFFFFFL);
    }

    public long toLong() {
        return value;
    }

    public float toFloat() {
        return (float) (value & maxValue);
    }

    public double toDouble() {
        return (double) (value & maxValue);
    }

    public void resetValue() {
        setValue(0);
    }

    @Override
    public String toString() {
        return Long.toUnsignedString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unsigned unsigned = (Unsigned) o;
        return bits == unsigned.bits && value == unsigned.value;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(bits);
        result = 31 * result + Long.hashCode(value);
        return result;
    }

    @Override
    public int compareTo(Unsigned other) {
        if (this.equals(other)) return 0;

        int cmp = Long.compareUnsigned(this.value, other.value);
        if (cmp != 0) return cmp;

        return Integer.compare(this.bits, other.bits);
    }

    /**
     * {@link java.lang.Number#intValue()} from Number superclass.
     * <p>
     * Generally, you should use {@link #toInt()} instead, as this method may cut off high bits
     * if the unsigned value exceeds the signed 32-bit range.
     * This returns the raw internal value without masking.
     *
     * @return the int value
     */
    @Override
    public int intValue() {
        return (int) value;
    }

    /**
     * {@link java.lang.Number#longValue()} from Number superclass.
     * <p>
     * Generally, you should use {@link #toLong()} instead, which returns the masked unsigned value explicitly.
     * This returns the raw internal long value without masking.
     *
     * @return the long value
     */
    @Override
    public long longValue() {
        return value;
    }

    /**
     * {@link java.lang.Number#floatValue()} from Number superclass.
     * <p>
     * Returns the masked value as a float.
     * For more accurate or explicit conversion, prefer {@link #toFloat()}.
     * This class isn't designed for precise floating-point arithmetic.
     *
     * @return the float value
     */
    @Override
    public float floatValue() {
        return (float) value;
    }

    /**
     * {@link java.lang.Number#doubleValue()} from Number superclass.
     * <p>
     * Returns the masked value as a double.
     * For more accurate or explicit conversion, prefer {@link #toDouble()}.
     * This class isn't designed for precise floating-point arithmetic.
     *
     * @return the double value
     */
    @Override
    public double doubleValue() {
        return (double) value;
    }
}