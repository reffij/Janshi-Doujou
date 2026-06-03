/*
* Copyright (c) 2026 Matthew Anderson
* Licensed under the MIT License
*/

package engine.datastructures;

public interface Visitor<T> {
    public boolean visit(T item);
}
