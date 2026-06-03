package engine.ui.renderable;

import engine.graphics.render.Renderer;

public class RectRable implements Renderable{

    int width;
    int height;
    int color;

    public RectRable(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public RectRable setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public RectRable setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public void render(Renderer r, int x, int y) {
        r.drawRect(x, y, this.width, this.height, this.color);
    }

    @Override
    public void update(double dt) {
        return;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

}
