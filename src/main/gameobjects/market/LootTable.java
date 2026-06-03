package main.gameobjects.market;

import java.util.*;

import engine.datastructures.SwapBackArrayList;

public class LootTable<T> {
    double[] dropRates;
    Random rng;
    List<ArrayList<T>> tables;


    LootTable(double[] dropRates, long seed) {
        
        this.dropRates = dropRates;
        this.rng = new Random(seed);
        this.tables = new ArrayList<>();
        for (int i = 0; i < dropRates.length; i++) {
            tables.add(new SwapBackArrayList<T>(seed));
        }
    }

    public void addItem(T item, int rarity) {
        this.tables.get(rarity).add(item);
    }

    ArrayList<T> rollTable() {
        double n = rng.nextDouble();
        for (int i = 0; i < dropRates.length; i++) {
            if (n <= dropRates[i]) return this.tables.get(i);
            n -= dropRates[i];
        }
        throw new Error();
    }

    T getRandomItem() {
        List<T> list = rollTable();
        if (list.isEmpty()) throw new Error("Tried to access a loot table's rarity bucket that was empty");
        int i = rng.nextInt(list.size());
        return list.get(i);
    }

    void push(T item, int rarity) {
        this.tables.get(rarity).add(item);
    }

    void changeRates(double[] newRates) {
        this.dropRates = newRates;
    }
}
