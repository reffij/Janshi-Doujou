package engine.ui.renderable;

import engine.graphics.render.Renderer;

public interface Renderable {

    void render(Renderer r, int x, int y);
    void update(double dt);

    int getWidth();
    int getHeight();

}
