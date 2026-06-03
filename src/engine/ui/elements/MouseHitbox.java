package engine.ui.elements;


import utilities.MathUtils;

public class MouseHitbox {
    protected int width, height;

    public MouseHitbox(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public MouseHitbox setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }


    public boolean contains(int x, int y) {
        return MathUtils.contains(0, 0, this.width, this.height, x, y);
    }
}
