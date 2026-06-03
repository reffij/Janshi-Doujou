package main.data;
import java.util.Objects;

public class Score implements Comparable<Score> {
    double mantissa;
    int exponent;

    public Score(Number n) {
        this.mantissa = n.doubleValue();
        this.exponent = 0;
        this.normalize();
    }

    @Override
    public int compareTo(Score other) {
        if (this.exponent == other.exponent) {
            return Double.compare(this.mantissa, other.mantissa);
        }
        return this.exponent - other.exponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score) || o == null) return false;
        Score other = (Score) o;
        return (this.mantissa == other.mantissa) && (this.exponent == other.exponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mantissa, this.exponent);
    }

    @Override
    public String toString() {
        if (this.exponent <= 12) return String.valueOf(mantissa * Math.pow(10, exponent));
        return Double.toString(mantissa) + "e" + Integer.toString(exponent);
    }

    public void add(Number n) {
        this.add(new Score(n));
    }

    public void subtract(Score other) {
        other.multiply(-1);
        this.add(other);
    }

    public void subtract(Number n) {
        Score s = new Score(n);
        s.multiply(-1);
        this.add(s);
    }

    public void add(Score other) {
        Score big;
        Score small;

        if (this.compareTo(other) < 0) {
            big = new Score(other.mantissa);
            big.exponent = other.exponent;
            small = new Score(this.mantissa);
            small.exponent = this.exponent;
        }  else {
            big = new Score(this.mantissa);
            big.exponent = this.exponent;
            small = new Score(other.mantissa);
            small.exponent = other.exponent;
        }


        while (small.exponent < big.exponent) {
            small.exponent++;
            small.mantissa /= 10;
        }

        this.mantissa = big.mantissa + small.mantissa;
        this.exponent = big.exponent;
        this.normalize();
    }

    public void multiply(Number n) {
        this.mantissa *= n.doubleValue();
        normalize();
    }

    private void normalize() {
        while (mantissa >= 10) {
            mantissa /= 10;
            exponent++;
        }
        while (mantissa > 0 && mantissa < 1) {
            mantissa *= 10;
            exponent--;
        }
    }

    public void roundUpByExponent(int exp) {
    int targetExp = exp; // 10^2 = 100

    // if exponent < 2, we need to increase exponent by dividing mantissa
    while (this.exponent < targetExp) {
        this.mantissa /= 10;
        this.exponent++;
    }

    // now exponent >= 2
    // ceil mantissa to nearest integer
    this.mantissa = Math.ceil(this.mantissa);

    // restore exponent to original scale
    while (this.exponent > targetExp) {
        this.mantissa *= 10;
        this.exponent--;
    }

    normalize();
    }
}
