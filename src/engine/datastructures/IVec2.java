package engine.datastructures;

public class IVec2 {
    public int x, y;

    public IVec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static IVec2 from(int x, int y) {
        return new IVec2(x, y);
    }
}
