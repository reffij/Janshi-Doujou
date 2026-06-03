package main.gameobjects.pools.tilepools;

import main.gameobjects.pools.Pool;
import main.gameobjects.tiles.Tile;

public class DoraWall implements Pool<Tile> {
    
    Tile[] tiles;
    int revealed;
    int size;

    public DoraWall() {
        this.tiles = new Tile[5];
        this.revealed = 1;
        this.size = 0;
    }

    public void incrementRevealed() {
        this.revealed += 1;
    }

    public void setRevealed(int n) {
        this.revealed = n;
    }

    public void push(Tile tile) {
        this.tiles[size] = tile;
        this.size += 1;
    }

    public Tile pop() {
        Tile res = this.tiles[size - 1];
        this.tiles[size - 1] = null;
        this.size -= 1;
        return res;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public Tile get(int index) {
        return this.tiles[index];
    }

    public int size() {
        return this.size;
    }

    public Tile[] toList() {
        return this.tiles;
    }
}
