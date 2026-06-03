package main.data;

public class ScaledInt {

    int data;

    public static ScaledInt fromInt(int value) {
        return new ScaledInt(value * 100);
    }

    public static ScaledInt fromDouble(double value) {
        return new ScaledInt((int) Math.round(value * 100));
    }

    public ScaledInt(int scaledValue) {
        this.data = scaledValue;
    }

    public ScaledInt(double data) {
        this.data = (int) (100 * data);
    }

    public int toInt() {
        return data / 100;
    }

    public double toDouble() {
        return data / 100.0;
    }

    public void add(ScaledInt other) {
        this.data += other.data;
    }

    public void add(double value) {
        this.data += (int) Math.round(value * 100);
    }

    public void add(int value) {
        this.data += value * 100;
    }

    public void subtract(ScaledInt other) {
        this.data -= other.data;
    }

    public void multiply(double factor) {
        this.data = (int) Math.round(this.data * factor);
    }

    @Override
    public String toString() {
        return String.valueOf(toDouble());
    }
}
