package main.gameobjects.market;

import java.util.Map;

import engine.commands.*;
import main.JDCommandType;
import main.gameobjects.tradingcards.TradingCard;

public class TradingCardItem implements Item {

    TradingCard tCard;
    int price;

    TradingCardItem(TradingCard tCard) {
        this.tCard = tCard;
        this.price = tCard.getCost();
    }

    @Override
    public Command<JDCommandType, ?> createSelectCommand() {
        return new Command<>(JDCommandType.ADD_TRADING_CARD, this.tCard);
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public Command<JDCommandType, ?> createPurchaseCommand() {
        return new Command<>(JDCommandType.ADD_TRADING_CARD, this.tCard);
    }

    @Override
    public void addToLootTable(Map<LootTableType, LootTable<Item>> tables) {
        tables.get(LootTableType.TRADING_CARD).addItem(this, tCard.getRarity());
    }

    public String toString() {
        return this.tCard.toString();
    }

    @Override
    public Item onAddToOffers() {
        return this;
    }
}
