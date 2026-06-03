package engine.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ForgetfulStack<T> implements Iterable<T> {
    private final Object[] buffer;
    private final int capacity;

    private int top, size;

    public ForgetfulStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.capacity = capacity;
        this.buffer = new Object[capacity];
        this.top = -1;
        this.size = 0;
    }

    public void push(T value) {
        this.top = (top + 1) % this.capacity;
        buffer[top] = value;

        if (this.size < this.capacity) this.size++;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (this.size == 0) return null;

        return (T) buffer[top];
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (this.size == 0) return null;

        T res = (T) buffer[top];
        buffer[top] = null;

        this.top = (this.top - 1 + this.capacity) % this.capacity;
        this.size--;
        return res;
    }

    public int size() {
        return this.size;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int count = 0;
            private int currentIndex = top;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @SuppressWarnings("unchecked")
            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();

                T value = (T) buffer[currentIndex];

                currentIndex = (currentIndex - 1 + capacity) % capacity;
                count++;

                return value;
            }
        };
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

}
