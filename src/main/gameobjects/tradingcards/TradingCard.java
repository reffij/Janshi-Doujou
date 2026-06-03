package main.gameobjects.tradingcards;

import java.util.function.Consumer;
import java.util.function.Predicate;

import main.JDGameContext;

public class TradingCard {
    String name;
    String description;
    Predicate<JDGameContext> predicate;
    Consumer<JDGameContext> consumer;
    boolean consumable;
    TradingCardType type;
    int rarity;
    int cost;

    TradingCard(String name, Predicate<JDGameContext> predicate, boolean consumable, TradingCardType type, int cost, int rarity) {
        this.name = name;
        this.consumable = consumable;
        this.predicate = predicate;
        this.type = type;
        this.cost = cost;
        this.rarity = rarity;
    }

    public boolean precondition(JDGameContext ctx) {
        return predicate.test(ctx);
    }

    public void use(JDGameContext ctx) {
        consumer.accept(ctx);
    }

    boolean isConsumable() {
        return this.consumable;
    }

    public int getCost() {
        return this.cost;
    }

    public int getRarity() {
        return this.rarity;
    }

    public String toString() {
        return this.name;
    }
}
