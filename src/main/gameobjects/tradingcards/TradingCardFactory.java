package main.gameobjects.tradingcards;

import java.util.ArrayList;
import java.util.List;

import engine.commands.*;

public class TradingCardFactory {
    

    public static List<TradingCard> allRelics() {

        List<TradingCard> res = new ArrayList<>();
        TradingCardType TYPE = TradingCardType.RELIC;

        

        return res;
    }

    public static List<TradingCard> allAbilities() {
        List<TradingCard> res = new ArrayList<>();


        return res;
    }

    public static List<TradingCard> allTradingCards() {
        List<TradingCard> res = new ArrayList<>();
        for (AnimalCard animalCard : AnimalCard.values()) {
            res.add(animalCard.tradingCard);
        }
        for (ElementalCard elementalCard : ElementalCard.values()) {
            res.add(elementalCard.tradingCard);
        }

        res.add(new TradingCard("Rarity 0 1", null, false, null, 0, 0));
        res.add(new TradingCard("Rarity 0 2", null, false, null, 0, 0));
        res.add(new TradingCard("Rarity 0 3", null, false, null, 0, 0));
        res.add(new TradingCard("Rarity 0 4", null, false, null, 0, 0));
        res.add(new TradingCard("Rarity 0 5", null, false, null, 0, 0));
        res.add(new TradingCard("Rarity 0 6", null, false, null, 0, 0));
        res.add(new TradingCard("Rarity 1 1", null, false, null, 0, 1));
        res.add(new TradingCard("Rarity 1 2", null, false, null, 0, 1));
        res.add(new TradingCard("Rarity 1 3", null, false, null, 0, 1));
        res.add(new TradingCard("Rarity 1 4", null, false, null, 0, 1));
        res.add(new TradingCard("Rarity 1 5", null, false, null, 0, 1));
        res.add(new TradingCard("Rarity 1 6", null, false, null, 0, 1));
        res.add(new TradingCard("Rarity 2 1", null, false, null, 0, 2));
        res.add(new TradingCard("Rarity 2 2", null, false, null, 0, 2));
        res.add(new TradingCard("Rarity 2 3", null, false, null, 0, 2));
        res.add(new TradingCard("Rarity 2 4", null, false, null, 0, 2));
        res.add(new TradingCard("Rarity 2 5", null, false, null, 0, 2));
        res.add(new TradingCard("Rarity 2 6", null, false, null, 0, 2));
        res.add(new TradingCard("Rarity 3 1", null, false, null, 0, 3));
        res.add(new TradingCard("Rarity 3 2", null, false, null, 0, 3));
        res.add(new TradingCard("Rarity 3 3", null, false, null, 0, 3));
        res.add(new TradingCard("Rarity 3 4", null, false, null, 0, 3));
        res.add(new TradingCard("Rarity 3 5", null, false, null, 0, 3));
        res.add(new TradingCard("Rarity 3 6", null, false, null, 0, 3));

        return res;
    }
}
