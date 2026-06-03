package engine.ui.renderable;

import engine.graphics.Shader;
import engine.graphics.Sprite;
import engine.graphics.render.Renderer;

public class SpriteRable implements Renderable {
    Sprite sprite;
    Shader shader;

    public SpriteRable(Sprite sprite) {
        this.sprite = sprite;
        this.shader = null;
    }

    public SpriteRable(Sprite sprite, Shader shader) {
        this.sprite = sprite;
        this.shader = shader;
    }

    public SpriteRable setSprite(Sprite sprite) {
        this.sprite = sprite;
        return this;
    }

    public SpriteRable setShader(Shader shader) {
        this.shader = shader;
        return this;
    }

    @Override
    public void render(Renderer r, int x, int y) {
        r.drawSprite(this.sprite, x, y, this.shader);
    }

    @Override
    public void update(double dt) {
        if (this.shader == null) return;
        this.shader.update(dt);
    }

    @Override
    public int getWidth() {
        return this.sprite.width;
    }

    @Override
    public int getHeight() {
        return this.sprite.height;
    }



}
