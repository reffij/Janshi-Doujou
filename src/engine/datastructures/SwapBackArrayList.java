package engine.datastructures;

import java.util.ArrayList;
import java.util.Random;

public class SwapBackArrayList<T> extends ArrayList<T>{

    Random rng;

    public SwapBackArrayList(long seed) {
        this.rng = new Random(seed);
    }

    public T popRandom() {
        int i = rng.nextInt(this.size());

        if (i == this.size() - 1) return this.remove(i);

        T res = this.get(i);
        T temp = this.remove(this.size() - 1);
        this.set(i, temp);
        return res;
    }
}
