package main.gameobjects.market;

import java.util.Map;

import engine.commands.*;
import main.JDCommandType;

public interface Item {
    int getPrice();

    Command<JDCommandType, ?> createPurchaseCommand();

    Command<JDCommandType, ?> createSelectCommand();

    void addToLootTable(Map<LootTableType, LootTable<Item>> tables);

    Item onAddToOffers();
}
