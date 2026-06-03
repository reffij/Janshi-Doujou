package engine.graphics.render.text;

import engine.builtin.fonts.Fonts;
import engine.builtin.shaders.DefaultShader;
import engine.datastructures.HAlign;
import engine.datastructures.SplitMode;
import engine.datastructures.VAlign;
import engine.graphics.BMPFont;
import engine.graphics.ColorUtils;
import engine.graphics.Shader;

public class TextOptions {

    public BMPFont font = Fonts.JMICKLE;
    public Shader shader = new DefaultShader();
    public int color = ColorUtils.rgb(255, 255, 255);
    public int backgroundColor = ColorUtils.argb(0, 0, 0, 0);

    public HAlign hAlign = HAlign.LEFT;
    public VAlign vAlign = VAlign.TOP;
    public SplitMode splitMode = SplitMode.WORD;


    public int boundWidth = 2 ^ 16;
    public int boundHeight = 2 ^ 16;


    public TextOptions() {}

    public TextOptions setFont(BMPFont font) {
        if (font == null) return this;
        this.font = font;
        return this;
    }

    public TextOptions setShader(Shader shader) {
        if (shader == null) return this;
        this.shader = shader;
        return this;
    }

    public TextOptions setColor(int color) {
        this.color = color;
        return this;
    }

    public TextOptions setBackgroundColor(int color) {
        this.backgroundColor = color;
        return this;
    }

    public TextOptions setBound(int width, int height) {
        this.boundWidth = width;
        this.boundHeight = height;
        return this;
    }

    public TextOptions setHAlign(HAlign hAlign) {
        if (hAlign == null) return this;
        this.hAlign = hAlign;
        return this;
    }

    public TextOptions setVAlign(VAlign vAlign) {
        if (vAlign == null) return this;
        this.vAlign = vAlign;
        return this;
    }

    public TextOptions setSplitMode(SplitMode splitMode) {
        this.splitMode = splitMode;
        return this;
    }
}
