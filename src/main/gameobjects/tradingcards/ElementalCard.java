package main.gameobjects.tradingcards;

import java.util.function.*;

import main.JDGameContext;
import main.data.*;
import main.gameobjects.tiles.Tile;

public enum ElementalCard {

    FIRE("Fire", (tile -> tile.setColor(TileColor.RED))),
    WATER("Water", (tile -> tile.setColor(TileColor.BLUE))),
    WOOD("Wood", (tile -> tile.setColor(TileColor.GREEN))),
    GROUND("Ground", (tile -> tile.setColor(TileColor.YELLOW))),
    METAL("Metal", (tile -> tile.setColor(TileColor.WHITE)));



    TradingCard tradingCard;
    
    ElementalCard(String name, Consumer<Tile> effect) {
        Predicate<JDGameContext> predicate = ctx -> {
            return ctx.onDiscardQueue.add(effect);
        };
        this.tradingCard = new TradingCard(name, predicate, true, TradingCardType.ELEMENTAL_CARD, 4, 1);
    }
}