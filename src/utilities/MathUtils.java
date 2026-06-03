package utilities;

import engine.datastructures.Rectangle;

public class MathUtils {

    public static <T extends Comparable<T>> T min(T a, T b) {
        if (a.compareTo(b) >= 0) return b;
        return a;
    }

    public static <T extends Comparable<T>> T max(T a, T b) {
        if (a.compareTo(b) >= 0) return a;
        return b;
    }

    public static <T extends Comparable<T>> T clamp(T value, T min, T max) {
        return max(min, min(max, value));
    }

    public static boolean contains(
        int x1, 
        int y1, 
        int width, 
        int height, 
        int x2, 
        int y2
    ) {
        return (
            x2 - x1 >= 0 && x2 - x1 <= width &&
            y2 - y1 >= 0 && y2 - y1 <= height
        );
    }

    public static boolean contains(Rectangle rect, int x, int y) {
        return contains(rect.x, rect.y, rect.w, rect.h, x, y);
    }

    public static int midpoint(int x1, int x2) {
        return (x1 + x2) / 2;
    }

    public static int alignCenter(int x1, int w1, int w2) {
        return x1 + ((w1 - w2) / 2);
    }

    public static int alignRight(int x1, int w1, int w2) {
        return x1 + (w1 - w2);
    }

    public static Rectangle centerRect(Rectangle r, int w, int h) {
        int x = alignCenter(r.x, r.w, w);
        int y = alignCenter(r.y, r.h, h);
        return new Rectangle(x, y, w, h);
    }
}
