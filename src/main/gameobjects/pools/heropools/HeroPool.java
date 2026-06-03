package main.gameobjects.pools.heropools;

import java.util.ArrayList;

import main.gameobjects.heroes.Hero;
import main.gameobjects.pools.CappedPool;
import main.gameobjects.pools.OrderedPool;

public class HeroPool extends ArrayList<Hero> implements CappedPool<Hero> {

    int capacity;

    public HeroPool(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void push(Hero item) {
        if (this.size() == this.capacity) throw new Error("Hero pool is full");
        this.add(item);
    }

    @Override
    public Hero pop() {
        return this.remove(this.size() - 1);
    }

    public void increaseCapacity() {
        this.capacity++;
    }

    public void decreaseCapacity() {
        this.capacity--;
    }

    @Override
    public boolean isFull() {
        return this.size() == this.capacity;
    }
    
}
