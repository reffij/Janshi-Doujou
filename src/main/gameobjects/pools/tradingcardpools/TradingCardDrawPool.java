package main.gameobjects.pools.tradingcardpools;

import java.util.ArrayDeque;

import main.gameobjects.pools.*;
import main.gameobjects.tradingcards.TradingCard;

public class TradingCardDrawPool extends ArrayDeque<TradingCard> implements Pool<TradingCard> {

    public TradingCardDrawPool(long seed) {
    }

    @Override
    public void push(TradingCard item) {
        this.add(item);
    }

    @Override
    public TradingCard pop() {
        return this.poll();
    }
}
