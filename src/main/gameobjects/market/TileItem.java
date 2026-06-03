package main.gameobjects.market;

import java.util.Map;

import engine.commands.*;
import main.JDCommandType;
import main.gameobjects.tiles.Tile;

public class TileItem implements Item {

    Tile tile;
    int price;

    TileItem(Tile tile) {
        this.tile = tile;
        this.price = 1;
    }

    @Override
    public Command<JDCommandType, ?> createSelectCommand() {
        return new Command<>(JDCommandType.ADD_NEW_TILE, this.tile);
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public Command<JDCommandType, ?> createPurchaseCommand() {
        return new Command<>(JDCommandType.ADD_NEW_TILE, this.tile);
    }

    @Override
    public void addToLootTable(Map<LootTableType, LootTable<Item>> tables) {
        tables.get(LootTableType.TILE).addItem(this, 0);
    }

    public String toString() {
        return this.tile.toString();
    }

    @Override
    public Item onAddToOffers() {
        return this;
    }
}
