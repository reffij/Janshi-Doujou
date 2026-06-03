package main.data;

import java.util.Arrays;

public class HandCountStack extends HandCount {
    private static final byte ADDNORMAL = 0;
    private static final byte ADDWILD = 1;
    private static final byte REMOVENORMAL = 2;
    private static final byte REMOVEWILD = 3;

    private byte[] opTypeStack;
    private int[] opIntegerStack;
    private int stackSize;

    private static final int INITIAL_CAPACITY = 128;

    public HandCountStack() {
        super();
        this.opIntegerStack = new int[INITIAL_CAPACITY];
        this.opTypeStack = new byte[INITIAL_CAPACITY];
        this.stackSize = 0;
    }

    public void clear() {
        super.clear();
        this.opIntegerStack = new int[INITIAL_CAPACITY];
        this.opTypeStack = new byte[INITIAL_CAPACITY];
        this.stackSize = 0;
    }

    private void push(byte opType, int integer) {
        if (stackSize >= opIntegerStack.length) {
            opTypeStack = Arrays.copyOf(opTypeStack, stackSize * 2);
            opIntegerStack = Arrays.copyOf(opIntegerStack, stackSize * 2);
        }
        opTypeStack[stackSize] = opType;
        opIntegerStack[stackSize] = integer;
        stackSize += 1;
    }

    public void undo() {
        if (stackSize == 0) throw new IllegalStateException("No operations to undo");
        this.stackSize -= 1;
        byte operation = opTypeStack[stackSize];
        int integer = opIntegerStack[stackSize];
        switch(operation) {
            case ADDNORMAL -> super.addNormalTile(integer);
            case ADDWILD -> super.addWildTile(integer);
            case REMOVENORMAL -> super.removeNormalTile(integer);
            case REMOVEWILD -> super.removeWildTile(integer);
        }
    
    }

    public void undo(int count) {
        for (int i = 0; i < count; i++) this.undo();
    }

    public void addNormalTile(int index) {
        super.addNormalTile(index);
        this.push(REMOVENORMAL, index);
    }

    public void removeNormalTile(int index) {
        super.removeNormalTile(index);
        this.push(ADDNORMAL, index);
    }
    
    public void addWildTile(int rank) {
        super.addWildTile(rank);
        this.push(REMOVEWILD, rank);
    }

    public void removeWildTile(int rank) {
        super.removeWildTile(rank);
        this.push(ADDWILD, rank);
    }

}
