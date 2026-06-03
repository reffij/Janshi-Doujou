package engine.ui.renderable;

import java.util.function.Supplier;

import engine.datastructures.HAlign;
import engine.datastructures.VAlign;
import engine.graphics.BMPFont;
import engine.graphics.ColorUtils;
import engine.graphics.Shader;
import engine.graphics.render.Renderer;
import engine.graphics.render.text.TextOptions;

public class TextRable implements Renderable{

    Supplier<String> content;
    int color;
    int backgroundColor;
    int width;
    int height;
    BMPFont font;
    Shader shader;
    HAlign hAlign = HAlign.LEFT;
    VAlign vAlign = VAlign.TOP;

    public TextRable(String s) {
        this.color = ColorUtils.rgb(255, 255, 255);
        this.content = () -> s;
    }

    public TextRable(Supplier<String> content) {
        this.color = ColorUtils.rgb(255, 255, 255);
        this.content = content;
    }

    public TextRable(Object o) {
        this.color = ColorUtils.rgb(255, 255, 255);
        this.content = () -> String.valueOf(o);
    }

    public TextRable setColor(int color) {
        this.color = color;
        return this;
    }

    public TextRable setHAlign(HAlign hA) {
        this.hAlign = hA;
        return this;
    }

    public TextRable setVAlign(VAlign vA) {
        this.vAlign = vA;
        return this;
    }

    public TextRable setBackgroundColor(int color) {
        this.backgroundColor = color;
        return this;
    }

    public TextRable setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public TextRable setFont(BMPFont font) {
        this.font = font;
        return this;
    }

    public TextRable setShader(Shader shader) {
        this.shader = shader;
        return this;
    }

    @Override
    public void render(Renderer r, int x, int y) {
        r.drawString(
            content.get(), x, y, 
            new TextOptions()
                .setFont(this.font)
                .setShader(this.shader)
                .setColor(this.color)
                .setBackgroundColor(this.backgroundColor)
                .setBound(this.width, this.height)
                .setHAlign(this.hAlign)
                .setVAlign(this.vAlign)
            );
    }

    @Override
    public void update(double dt) {
        if (shader == null) return;
        this.shader.update(dt);
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
