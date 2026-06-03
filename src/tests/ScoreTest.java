package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import main.data.Score;

public class ScoreTest {

    // -------------------------
    // Constructor + toString()
    // -------------------------

    @Test
    public void testConstructInteger() {
        Score s = new Score(500);

        assertEquals("500", s.toString());
    }

    @Test
    public void testConstructDecimal() {
        Score s = new Score(12.5);

        assertEquals("12.5", s.toString());
    }

    @Test
    public void testConstructZero() {
        Score s = new Score(0);

        assertEquals("0", s.toString());
    }

    @Test
    public void testConstructNegative() {
        Score s = new Score(-42);

        assertEquals("-42", s.toString());
    }

    // -------------------------
    // toString() exponent rules
    // -------------------------

    @Test
    public void testExponentExactly12() {
        Score s = new Score(1_000_000_000_000L);

        assertEquals("1000000000000", s.toString());
    }

    @Test
    public void testExponentGreaterThan12() {
        Score s = new Score(10_000_000_000_000L);

        assertEquals("1.0000e13", s.toString());
    }

    @Test
    public void testVeryLargeExponent() {
        Score s = new Score(5.5e20);

        assertEquals("5.5e20", s.toString());
    }

    // -------------------------
    // add(Number)
    // -------------------------

    @Test
    public void testAddNumber() {
        Score s = new Score(100);

        s.add(50);

        assertEquals("150", s.toString());
    }

    @Test
    public void testAddDecimal() {
        Score s = new Score(10);

        s.add(2.5);

        assertEquals("12.5", s.toString());
    }

    @Test
    public void testAddNegativeNumber() {
        Score s = new Score(100);

        s.add(-50);

        assertEquals("50", s.toString());
    }

    @Test
    public void testAddZero() {
        Score s = new Score(99);

        s.add(0);

        assertEquals("99", s.toString());
    }

    // -------------------------
    // add(Score)
    // -------------------------

    @Test
    public void testAddScore() {
        Score a = new Score(100);
        Score b = new Score(200);

        a.add(b);

        assertEquals("300", a.toString());
    }

    @Test
    public void testAddLargeScores() {
        Score a = new Score(1e13);
        Score b = new Score(1e13);

        a.add(b);

        assertEquals("2.0000e13", a.toString());
    }

    @Test
    public void testAddPositiveAndNegativeScores() {
        Score a = new Score(100);
        Score b = new Score(-25);

        a.add(b);

        assertEquals("75", a.toString());
    }

    // -------------------------
    // subtract(Number)
    // -------------------------

    @Test
    public void testSubtractNumber() {
        Score s = new Score(100);

        s.subtract(30);

        assertEquals("70", s.toString());
    }

    @Test
    public void testSubtractToZero() {
        Score s = new Score(50);

        s.subtract(50);

        assertEquals("0", s.toString());
    }

    @Test
    public void testSubtractToNegative() {
        Score s = new Score(25);

        s.subtract(100);

        assertEquals("-75", s.toString());
    }

    // -------------------------
    // subtract(Score)
    // -------------------------

    @Test
    public void testSubtractScore() {
        Score a = new Score(500);
        Score b = new Score(125);

        a.subtract(b);

        assertEquals("375", a.toString());
    }

    @Test
    public void testSubtractIdenticalScores() {
        Score a = new Score(500);
        Score b = new Score(500);

        a.subtract(b);

        assertEquals("0", a.toString());
    }

    // -------------------------
    // multiply(Number)
    // -------------------------

    @Test
    public void testMultiplyInteger() {
        Score s = new Score(20);

        s.multiply(5);

        assertEquals("100", s.toString());
    }

    @Test
    public void testMultiplyDecimal() {
        Score s = new Score(10);

        s.multiply(2.5);

        assertEquals("25", s.toString());
    }

    @Test
    public void testMultiplyByZero() {
        Score s = new Score(999);

        s.multiply(0);

        assertEquals("0", s.toString());
    }

    @Test
    public void testMultiplyNegative() {
        Score s = new Score(10);

        s.multiply(-2);

        assertEquals("-20", s.toString());
    }

    // -------------------------
    // roundUpByExponent(int exp)
    // -------------------------

    /*
        exp = 1 -> round up to nearest 10
        exp = 2 -> round up to nearest 100
        exp = 3 -> round up to nearest 1000
     */

    @Test
    public void testRoundUpByExponentAlreadyRounded() {
        Score s = new Score(100);

        s.roundUpByExponent(2);

        assertEquals("100", s.toString());
    }

    @Test
    public void testRoundUpByExponentToHundred() {
        Score s = new Score(101);

        s.roundUpByExponent(2);

        assertEquals("200", s.toString());
    }

    @Test
    public void testRoundUpByExponentToTen() {
        Score s = new Score(21);

        s.roundUpByExponent(1);

        assertEquals("30", s.toString());
    }

    @Test
    public void testRoundUpByExponentToThousand() {
        Score s = new Score(1001);

        s.roundUpByExponent(3);

        assertEquals("2000", s.toString());
    }

    @Test
    public void testRoundUpSmallNumber() {
        Score s = new Score(1);

        s.roundUpByExponent(2);

        assertEquals("100", s.toString());
    }

    @Test
    public void testRoundUpZero() {
        Score s = new Score(0);

        s.roundUpByExponent(2);

        assertEquals("0", s.toString());
    }

    // -------------------------
    // Edge Cases
    // -------------------------

    @Test
    public void testBoundaryAtOneTrillion() {
        Score s = new Score(999_999_999_999L);

        s.add(1);

        assertEquals("1000000000000", s.toString());
    }

    @Test
    public void testBoundaryIntoScientificNotation() {
        Score s = new Score(9_999_999_999_999L);

        s.add(1);

        assertEquals("1.0000e13", s.toString());
    }

    @Test
    public void testFloatingPointPrecision() {
        Score s = new Score(0.1);

        s.add(0.2);

        assertEquals("0.3", s.toString());
    }
}