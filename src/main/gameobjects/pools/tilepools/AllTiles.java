package main.gameobjects.pools.tilepools;

import java.util.ArrayList;

import main.gameobjects.pools.Pool;
import main.gameobjects.tiles.Tile;

public class AllTiles implements Pool<Tile> {
    
    ArrayList<Tile> tiles;
    
    public AllTiles() {
        this.tiles = new ArrayList<Tile>();
    }

    public void push(Tile tile) {
        this.tiles.add(tile);
    }

    public Tile pop() {
        return this.tiles.remove(tiles.size() - 1);
    }

    public boolean isEmpty() {
        return this.tiles.size() == 0;
    }

    public int size() {
        return this.tiles.size();
    }
}
