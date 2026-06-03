package main.gameobjects.pools.tilepools;

import engine.datastructures.SwapBackArrayList;
import main.data.TileColor;
import main.gameobjects.pools.Pool;
import main.gameobjects.tiles.Tile;


public class TilePool extends SwapBackArrayList<Tile> implements Pool<Tile>  {


    public TilePool(long seed) {
        super(seed);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 34; j++) {
                Tile newTile = new Tile(j);
                if (i == 0 && newTile.getRank() == 5) newTile.setColor(TileColor.RED);
                this.add(newTile);
            }
        }

    }

    public Tile pop() {
        return this.popRandom();
    }

    public void push(Tile tile) {
        this.add(tile);
    }

    public void consume(Pool<Tile> tilePool) {
        while (!tilePool.isEmpty()) {
            this.push(tilePool.pop());
        }
    }

    /* 
    public void nPushFromPool(Pool<Tile> pool, int n) {
        if (n > pool.size()) throw new IndexOutOfBoundsException();
        for (int i = 0; i < n; i++) this.push(pool.pop());
    }

    public void nPopToPool(Pool<Tile> pool, int n) {
        if (n > this.size()) throw new IndexOutOfBoundsException();
        for (int i = 0; i < n; i++) pool.push(this.pop());
    }*/

}
