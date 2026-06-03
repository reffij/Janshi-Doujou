package main.gameobjects.market;

import java.util.Map;

import engine.commands.*;
import main.JDCommandType;
import main.gameobjects.heroes.Hero;

public class HeroItem implements Item {
    Hero hero;
    int price;
    HeroItem(Hero hero) {
        this.hero = hero;
        this.price = hero.getCost();
    }
    @Override
    public int getPrice() {
        return this.price;
    }
    @Override
    public Command<JDCommandType, ?> createPurchaseCommand() {
        return new Command<>(JDCommandType.ADD_HERO, this.hero);
    }
    @Override
    public Command<JDCommandType, ?> createSelectCommand() {
        return new Command<>(JDCommandType.ADD_HERO, this.hero);
    }

    public void addToLootTable(Map<LootTableType, LootTable<Item>> tables) {
        tables.get(LootTableType.HERO).addItem(this, this.hero.getRarity());
    }

    public String toString() {
        return this.hero.toString();
    }
    @Override
    public Item onAddToOffers() {
        return this;
    }
}