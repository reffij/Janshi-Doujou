package main.gameobjects.pools;

public interface Pool<T> {
    boolean isEmpty();

    void push(T item);

    T pop();

    int size();
}
