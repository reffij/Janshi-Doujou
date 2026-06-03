package main.gameobjects.pools.tradingcardpools;

import java.util.ArrayList;

import main.gameobjects.pools.Pool;
import main.gameobjects.tradingcards.TradingCard;

public class TradingCardDiscardPool extends ArrayList<TradingCard> implements Pool<TradingCard> {

    @Override
    public void push(TradingCard item) {
        this.add(item);
    }

    @Override
    public TradingCard pop() {
        return this.remove(this.size() - 1);
    }


    
}
