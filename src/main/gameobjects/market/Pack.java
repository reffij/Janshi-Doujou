package main.gameobjects.market;

import java.util.*;

import engine.commands.*;
import main.JDCommandType;

public class Pack implements Item {
    List<Item> items;
    int price;
    LootTable<Item> lootTable;
    int capacity;
    ReservedItemSet reserved;

    Pack(LootTable<Item> lootTable, int capacity, int price, ReservedItemSet reserved) {
        this.lootTable = lootTable;
        this.capacity = capacity;
        this.price = price;
        this.reserved = reserved;
        this.items = new ArrayList<>();
    }

    void addItem(Item item) {
        if (this.capacity == this.items.size()) throw new Error("Cannot add new item because this pack has reached its capacity");
        this.items.add(item);
    }

    boolean isFull() {
        return (this.items.size() == this.capacity);
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public Command<JDCommandType, ?> createPurchaseCommand() {
        return new Command<>(JDCommandType.OPEN_PACK, this.items);
    }

    @Override
    public void addToLootTable(Map<LootTableType, LootTable<Item>> tables) {
        tables.get(LootTableType.PACK).addItem(this, 0);
    }

    @Override
    public Command<JDCommandType, ?> createSelectCommand() {
        throw new UnsupportedOperationException("Unimplemented method 'createSelectCommand'");
    }

    @Override
    public Item onAddToOffers() {
        Pack res = new Pack(this.lootTable, this.capacity, this.price, this.reserved);
        int attemps = 0;
        int maxAttemps = 500;

        while(!res.isFull()) {
            Item item = res.lootTable.getRandomItem();
            if (!res.reserved.isReserved(item) || attemps++ > maxAttemps) {
                res.addItem(item);
                res.reserved.add(item);
            }
        }
        return res;
    }
}
