package engine.graphics.render;

import java.awt.image.BufferedImage;

import engine.graphics.Shader;
import engine.graphics.Sprite;
import engine.graphics.render.text.TextOptions;

import org.lwjgl.opengl.GL11;

public class GPURenderer implements Renderer {

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public void drawSprite(Sprite sprite, int x, int y, Shader shader) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawSprite'");
    }

    @Override
    public void drawSprite(Sprite sprite, int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawSprite'");
    }

    @Override
    public void drawRect(int x, int y, int w, int h, int color) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawRect'");
    }

    @Override
    public void drawCircle(int x, int y, int radius, int color) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawCircle'");
    }

    @Override
    public void drawString(String s, int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawString'");
    }

    @Override
    public void drawString(String s, int x, int y, TextOptions options) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawString'");
    }

    @Override
    public BufferedImage getImage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getImage'");
    }
    
}
