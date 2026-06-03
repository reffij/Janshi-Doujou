package main.gameobjects.pools.tilepools;

import engine.datastructures.Visitor;
import main.gameobjects.pools.CappedPool;
import main.gameobjects.pools.OrderedPool;
import main.gameobjects.tiles.Tile;

public class Hand implements CappedPool<Tile>{
    
    Tile[] tiles;
    int size;

    public Hand() {
        this.tiles = new Tile[14];
        for (int i = 0; i < 14; i++) {
            this.tiles[i] = null;
        }
        this.size = 0;
    }

    

    public void push(Tile tile) {
        if (size >= tiles.length) {
            throw new IllegalStateException("Hand is full");
        }
        this.tiles[size] = tile;
        this.size += 1;
    }

    private boolean nullTileComparison(Tile a, Tile b) {
        if (a == null) return false;
        if (b == null) return true;
        return a.compareTo(b) > 0;
    }

    //Sort all but tsumo tile
    public void sort() {
        for (int i = 1; i < 13; i++) {
            Tile key = this.tiles[i];
            int j = i - 1;
            while (j >= 0 && this.nullTileComparison(tiles[j], key)) {
                this.tiles[j + 1] = this.tiles[j];
                j -= 1;
            }

            tiles[j + 1] = key;
        }
    }
    
    public Tile remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Tile res = this.tiles[index];
        for (int i = index; i < this.size - 1; i++) {
            tiles[i] = tiles[i + 1];
        }
        tiles[size - 1] = null;
        this.size -= 1;
        return res;
    }

    public Tile get(int index) {
        return tiles[index];
    }

    public void set(int index, Tile newTile) {
        this.tiles[index] = newTile;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public Tile pop() {
        if (size == 0) {
            throw new IllegalStateException("Hand is empty");
        }
        Tile res = this.tiles[size - 1];
        this.tiles[size - 1] = null;
        this.size -= 1;
        return res;
    }

    public Tile peek() {
        return this.tiles[size - 1];
    }

    public int size() {
        return this.size;
    }

    public Tile[] getTiles() {
        return this.tiles;
    }

    public void accept(Visitor<Tile> visitor) {
        for (int i = 0; i < 14; i++) {
            if (this.tiles[i] == null) continue;
            visitor.visit(this.tiles[i]);
        }
    }



    @Override
    public boolean isFull() {
        return this.size == 14;
    }
}
