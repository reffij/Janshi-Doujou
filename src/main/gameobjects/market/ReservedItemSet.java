package main.gameobjects.market;

import java.util.HashSet;

public class ReservedItemSet extends HashSet<Item>{
    boolean check = true;

    boolean isReserved(Item item) {
        if (!check) return false;
        return this.contains(item);
    }

    void setCheck(boolean check) {
        this.check = check;
    }
}
