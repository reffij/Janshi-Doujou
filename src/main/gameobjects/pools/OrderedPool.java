package main.gameobjects.pools;

public interface OrderedPool<T> extends Pool<T>{
    T get(int index);

    T remove(int index);
}
