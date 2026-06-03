package main.yaku;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import main.data.*;

public enum Yaku {
    TANYOU(
        "All Simples",
        (ctx) -> {
            for (Meld meld : ctx.melds) {
                if (meld.containsOnlyHonors()) return false;
                if (meld.containsTerminal()) return false;
            }
            return true;
        },
        1
    ),
    CHUUN(
        "Red Dragon",
        (ctx) -> {
            for (Meld meld : ctx.melds) {
                if (meld.isPonOrKan() && meld.contains(33)) return true;
            }
            return false;
        },
        1
    ),
    HATSU(
        "Green Dragon",
        (ctx) -> {
            for (Meld meld : ctx.melds) {
                if (meld.isPonOrKan() && meld.contains(32)) return true;
            }
            return false;
        },
        1
    ),
    HAKU(
        "White Dragon",
        (ctx) -> {
            for (Meld meld : ctx.melds) {
                if (meld.isPonOrKan() && meld.contains(31)) return true;
            }
            return false;
        },
        1
    ),
    PREVELANT_WIND(
        "Prevelant Wind",
        (ctx) -> {
            for (Meld meld : ctx.melds) {
                if (meld.isPonOrKan() && meld.contains(ctx.prevelantWind)) return true;
            }
            return false;
        },
        1
    ),
    SEAT_WIND(
        "Seat Wind",
        (ctx) -> {
            for (Meld meld : ctx.melds) {
                if (meld.isPonOrKan() && meld.contains(ctx.seatWind)) return true;
            }
            return false;
        },
        1
    ),
    PINFU(
        "Pinfu",
        (ctx) -> {
            if (ctx.waitType != WaitType.RYANMEN) return false;
            for (Meld meld : ctx.melds) {
                if (meld.getMeldType() != MeldType.CHI) return false;
            }
            if (ctx.pair.isSangen()) return false;
            if (ctx.pair.isKaze() && 
                (ctx.pair.equals(ctx.prevelantWind) ||
                ctx.pair.equals(ctx.seatWind))) {
                    return false;
                }
            return true;
        },
        1
    ),
    IIPEIKOU(
        "Pure Double Sequence",
        (ctx) -> {
            Set<Meld> discoveredMelds = new HashSet<Meld>();
            for (Meld meld : ctx.melds) {
                if (meld.isChi()) {
                    if (discoveredMelds.contains(meld)) return true;
                    discoveredMelds.add(meld);
                }
            }
            return false;
        },
        1
    ),
    HONCHANTAIYAOCHUU(
        "Mixed Outside Hand",
        (ctx) -> {
            for (Meld meld : ctx.melds) {
                if (!(meld.containsTerminal() || meld.containsOnlyHonors())) return false;
            }
            return true;
        },
        2
    ),
    TOITOI(
        "All Triplets",
        (ctx) -> {
            for (Meld meld : ctx.melds) {
                if (!meld.isPonOrKan()) return false;
            }
            return true;            
        },
        2
    )


    
    
    
    
    
    ;


    String name;
    Predicate<WinContext> test;
    int han;

    Yaku(String name, Predicate<WinContext> test, int han) {
        this.name = name;
        this.test = test;
        this.han = han;
    }

    public boolean test(WinContext ctx) {
        return this.test.test(ctx);
        
    }

    public int getHan() {
        return this.han;
    }

    public String toString() {
        return this.name;
    }


}
