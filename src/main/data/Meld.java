package main.data;

import java.util.Objects;

public class Meld {
    int index;
    MeldType meldType;

    public Meld(int index, MeldType meldType) {
        this.index = index;
        this.meldType = meldType;
    }

    public MeldType getMeldType() {
        return this.meldType;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean contains(int index) {
        if (this.meldType == MeldType.CHI) {
            return (index - this.index <= 2 && index - this.index >= 0);
        }
        return this.index == index;
    }

    public boolean contains(TilePrimitive tile) {
        return contains(tile.getInt());
    }

    public boolean isChi() {
        return this.meldType == MeldType.CHI;
    }

    public boolean isPon() {
        return this.meldType == MeldType.PON;
    }

    public boolean isKan() {
        return this.meldType == MeldType.KAN;
    }

    public boolean isPonOrKan() {
        return this.isPon() || this.isKan();
    }

    public boolean containsOnlyHonors() {
        if (this.isPonOrKan() && TilePrimitive.isHonor(this.index)) return true;
        return false;
    }

    public boolean containsTerminal() {
        if (this.isPonOrKan() && TilePrimitive.isTerminal(index)) return true;
        if (this.index == 0 || this.index == 6) return true;
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meld other = (Meld) o;
        return this.index == other.index && this.meldType == other.meldType;
    }

    public int hashCode() {
        return Objects.hash(this.index, this.meldType.ordinal());
    }
}
