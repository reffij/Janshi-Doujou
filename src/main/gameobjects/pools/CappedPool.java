package main.gameobjects.pools;

public interface CappedPool<T> extends OrderedPool<T>{

    boolean isFull();
} 
