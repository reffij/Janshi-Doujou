package main.gameobjects.pools.tradingcardpools;

import java.util.ArrayList;

import main.gameobjects.pools.CappedPool;
import main.gameobjects.tradingcards.TradingCard;

public class TradingCardHandPool extends ArrayList<TradingCard> implements CappedPool<TradingCard> {

    int capacity;

    public TradingCardHandPool(int capacity) {
        this.capacity = capacity;
    }

    public void increaseCapacity(int n) {
        this.capacity += n;
    }

    public void decreaseCapacity(int n) {
        this.capacity -= n;
    }

    @Override
    public void push(TradingCard item) {
        if (!isFull()) this.add(item);
    }

    @Override
    public TradingCard pop() {
        return this.remove(this.size() - 1);
    }

    public boolean isFull() {
        return this.size() == this.capacity;
    }
    
}
