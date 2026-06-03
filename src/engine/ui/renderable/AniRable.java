package engine.ui.renderable;

import engine.graphics.Animation;
import engine.graphics.render.Renderer;

public class AniRable implements Renderable{

    private Animation animation;

    public AniRable(Animation animation) {
        this.animation = animation;
    }

    @Override
    public void render(Renderer r, int x, int y) {
        animation.render(r, x, y);
    }

    @Override
    public void update(double dt) {
        animation.update(dt);
    }

    @Override
    public int getWidth() {
        return animation.getWidth();
    }

    @Override
    public int getHeight() {
        return animation.getHeight();
    }
    
}
