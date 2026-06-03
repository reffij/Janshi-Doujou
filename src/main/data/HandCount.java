package main.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.gameobjects.tiles.Tile;

public class HandCount {

    int[] tileCounts;
    int[] kazuMask;
    int kazeMask;
    int sangenMask;
    int[] kans;

    public HandCount() {
        this.tileCounts = new int[34];
        this.kazuMask = new int[9];
        this.kazeMask = 0;
        this.sangenMask = 0;
        for (int i = 0; i < 34; i++) this.tileCounts[i] = 0;
        for (int i = 0; i < 9; i++) this.kazuMask[i] = 0;
    }

    public void clear() {
        this.kazeMask = 0;
        this.sangenMask = 0;
        for (int i = 0; i < 34; i++) {
            this.tileCounts[i] = 0;
        }
        for(int i = 0; i < 9; i++) {
            this.kazuMask[i] = 0;
        }
    }

    public void removeTile(Tile tile) {
        if (tile.getModification() == TileModification.WILD) {
            this.removeWildTile(tile);
        } else {
            this.removeNormalTile(tile);
        }
    }

    public void addTile(Tile tile) {
        if (tile.getModification() == TileModification.WILD) {
            this.addWildTile(tile);
        } else {
            this.addNormalTile(tile);
        }
    }

    public void addNormalTile(int index) {
        tileCounts[index] += 1;
    }

    public void addNormalTile(TilePrimitive tile) {
        this.addNormalTile(tile.getInt());
    }

    public void removeNormalTile(int index) {
        tileCounts[index] -= 1;
    } 

    public void removeNormalTile(TilePrimitive tile) {
        this.removeNormalTile(tile.getInt());
    }

    public void addWildTile(int rank) {
        this.kazuMask[rank - 1] += 1;
    }

    public void addWildTile(TilePrimitive tile) {
        if (tile.isKaze()) {
            this.kazeMask++;
            return;
        }
        if (tile.isSangen()) {
            this.sangenMask++;
            return;
        }
        this.addWildTile(tile.getRank());
    }

    public void removeWildTile(int rank) {
        this.kazuMask[rank - 1] -= 1;
    }

    public void removeWildTile(TilePrimitive tile) {
        if (tile.isKaze()) {
            this.kazeMask--;
            return;
        }
        if (tile.isSangen()) {
            this.sangenMask--;
            return;
        }
        this.removeWildTile(tile.getRank());
    }

    public int count(int index) {
        if (index >= 27) return this.tileCounts[index];
        return this.tileCounts[index] + this.kazuMask[index % 9];
    }

    public int count(TilePrimitive tile) {
        return this.count(tile.getInt());
    }

    public void decrease(int index) {
        if (this.tileCounts[index] > 0) removeNormalTile(index);
        else {
            index = TilePrimitive.integerToRank(index) - 1;
            if (this.kazuMask[index] <= 0) throw new IllegalStateException("not enough tiles");
            removeWildTile(index + 1);
        }
    }

    public void decrease(int index, int n) {
        for (int i = 0; i < n; i++) this.decrease(index);
    }

    public void decrease(TilePrimitive tile) {
        this.decrease(tile.getInt());
    }

    public List<TilePrimitive> getTiles() {
        List<TilePrimitive> res = new ArrayList<>();
        for (int i = 0; i < 34; i++) {
            int count = this.count(i);
            for (int j = 0; j < count; j++) {
                res.add(new TilePrimitive(i));
            }
        }
        return res;
    }

    public Set<TilePrimitive> getUniqueTiles() {
        Set<TilePrimitive> res = new HashSet<>();
        for (int i = 0; i < 34; i++) {
            if (this.count(i) >= 1) {
                res.add(new TilePrimitive(i));
            }
        }
        return res;
    }
}
