package main.data;


public class TilePrimitive implements Comparable<TilePrimitive> {
    public int integer;

    public TilePrimitive(int rank, TileSuit suit) {
        this.integer = rank - 1;
        switch(suit) {
            case TileSuit.MAN -> this.integer += 0;
            case TileSuit.PIN -> this.integer += 9;
            case TileSuit.SOU -> this.integer += 18;
            case TileSuit.KAZE -> this.integer += 27;
            case TileSuit.SANGEN -> this.integer += 31;
        }
    }

    public TilePrimitive(int integer) {
        this.integer = integer;
    }

    public int getInt() {
        return this.integer;
    }

    public static int integerToRank(int integer) {
        if (integer <= 8) return integer + 1;
        if (integer <= 17) return integer - 8;
        if (integer <= 26) return integer - 17;
        if (integer <= 30) return integer - 26;
        if (integer <= 33) return integer - 30;
        return -1; 
    }

    public static int rankAndSuitToInt(int rank, TileSuit suit) {
        int res = rank - 1;
        switch(suit) {
            case TileSuit.MAN -> res += 0;
            case TileSuit.PIN -> res += 9;
            case TileSuit.SOU -> res += 18;
            case TileSuit.KAZE -> res += 27;
            case TileSuit.SANGEN -> res += 31;
        }
        return res;
    }

    public int getRank() {
        return integerToRank(this.integer);
    }

    public static TileSuit integerToSuit(int integer) {
        if (integer <= 8) return TileSuit.MAN;
        if (integer <= 17) return TileSuit.PIN;
        if (integer <= 26) return TileSuit.SOU;
        if (integer <= 30) return TileSuit.KAZE;
        return TileSuit.SANGEN;
    }
    
    public TileSuit getSuit() {
        return integerToSuit(this.integer);
    }

    public int compareTo(TilePrimitive other) {
        return this.getInt() - other.getInt();
    }

    public boolean isSangen() {
        return this.getSuit() == TileSuit.SANGEN;
    }

    public boolean isKaze() {
        return this.getSuit() == TileSuit.KAZE;
    }

    public boolean isHonor() {
        return this.isSangen() || this.isKaze();
    }

    public static boolean isSangen(int integer) {
        return integerToSuit(integer) == TileSuit.SANGEN;
    }

    public static boolean isKaze(int integer) {
        return integerToSuit(integer) == TileSuit.KAZE;
    }

    public static boolean isHonor(int integer) {
        return isKaze(integer) || isSangen(integer);
    }

    public static boolean isTerminal(int integer) {
        if (isHonor(integer)) return false;
        return (integerToRank(integer) == 1 || integerToRank(integer) == 9);
    }

    public boolean isTerminal() {
        if (this.isHonor()) return false;
        return (this.getRank() == 1 || this.getRank() == 9);
    }

    public boolean isSimple() {
        return !this.isTerminal();
    }

    public String toString() {
        String res = Integer.toString(this.getRank());
        switch(this.getSuit()) {
            case TileSuit.KAZE:
                switch(this.getRank()) {
                    case 1 -> res = "E";
                    case 2 -> res = "S";
                    case 3 -> res = "W";
                    case 4 -> res = "N";
                }
                break;
            case TileSuit.SANGEN:
                switch(this.getRank()) {
                    case 1 -> res = "Wh";
                    case 2 -> res = "G";
                    case 3 -> res = "R";
                }
                break;
            case TileSuit.MAN:
                res += "M";
                break;
            case TileSuit.PIN:
                res += "P";
                break;
            case TileSuit.SOU:
                res += "S";
                break;
            
        }
        return res;
    }

    private int range() {
        if (!isHonor()) {
            return 9;
        }
        if (isKaze()) {
            return 4;
        }
        return 3;
    }

    public int getRotationRank() {
        return ((this.getRank()) % this.range()) + 1;
    }

    public void rotate() {
        this.integer = TilePrimitive.rankAndSuitToInt(getRotationRank(), getSuit());
    }
}
