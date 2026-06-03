package main.gameobjects.pools.tilepools;

import java.util.ArrayList;

import main.gameobjects.pools.OrderedPool;
import main.gameobjects.tiles.Tile;

public class CallPool extends ArrayList<Tile> implements OrderedPool<Tile> {

    public void push(Tile tile) {
        this.add(tile);
    }

    public Tile pop() {
        return this.remove(this.size() - 1);
    }
}
