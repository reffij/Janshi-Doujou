package engine.graphics.render;

import java.awt.image.BufferedImage;

import engine.graphics.Shader;
import engine.graphics.Sprite;
import engine.graphics.render.text.TextOptions;

public interface Renderer {

    void clear();

    void drawSprite(Sprite sprite, int x, int y, Shader shader);

    void drawSprite(Sprite sprite, int x, int y);

    void drawRect(int x, int y, int w, int h, int color);

    void drawCircle(int x, int y, int radius, int color);

    void drawString(String s, int x, int y);

    void drawString(String s, int x, int y, TextOptions options);

    BufferedImage getImage();
}