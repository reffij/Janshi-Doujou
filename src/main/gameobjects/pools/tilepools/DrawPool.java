package main.gameobjects.pools.tilepools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import main.gameobjects.pools.OrderedPool;
import main.gameobjects.tiles.Tile;

public class DrawPool extends ArrayList<Tile> implements OrderedPool<Tile> {
    private Random rng;
    private Set<Number> revealedIndexes;

    public DrawPool(long seed) {
        this.rng = new Random(seed);
        this.revealedIndexes = new HashSet<Number>();
    }

    public void push(Tile tile) {
        this.add(tile);
    }

    public Tile pop() {
        return this.remove(this.size() - 1);
    }

    public void reveal() {
        int i = this.rng.nextInt();
        this.revealedIndexes.add(i);
    }

    public void unrevealAll() {
        this.revealedIndexes.clear();
    }
}
