package engine.graphics.render;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import engine.builtin.shaders.*;
import engine.datastructures.Rectangle;
import engine.graphics.ColorUtils;
import engine.graphics.Shader;
import engine.graphics.Sprite;
import engine.graphics.SpriteUtils;
import engine.graphics.render.text.TextOptions;
import engine.graphics.render.text.TextRenderer;

public class SoftwareRenderer implements Renderer {

    private int[] pixelBuffer;
    private int screenWidth;
    private int screenHeight;
    private BufferedImage image;

    private TextRenderer textRenderer;

    private Shader defaultShader;

    private final int BACKGROUND_COLOR = ColorUtils.rgb(40,40,40);



    public SoftwareRenderer(int screenWidth, int screenHeight) {
        this.defaultShader = new DefaultShader();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        this.pixelBuffer = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        this.textRenderer = new TextRenderer();
    }

    public void clear() {
        for (int y = 0; y < this.screenHeight; y++) {
            int rowOffset = y * this.screenWidth;
            for (int x = 0; x < this.screenWidth; x++) {
                this.pixelBuffer[x + rowOffset] = BACKGROUND_COLOR;
            }
        }
    }

    public void drawSprite(Sprite sprite, int xPos, int yPos, Shader shader) {
        byte[] pixels = sprite.pixels;
        int[] palette = sprite.palette;
        int width = sprite.width;
        int height = sprite.height;

        if (shader == null) shader = this.defaultShader;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int screenX = xPos + shader.sampleX(x, y);
                int screenY = yPos + shader.sampleY(x, y);

                if (screenX < 0 || screenX >= this.screenWidth) continue;
                if (screenY < 0 || screenY >= this.screenHeight) continue;

                byte index = pixels[x + y * sprite.width];
                if (index == SpriteUtils.NULLCOLORINDEX) continue;

                int color = palette[index];
                color = shader.shade(color, x, y);

                int indexBuffer = screenX + screenY * this.screenWidth;
                int dstColor = this.pixelBuffer[indexBuffer];
                this.pixelBuffer[indexBuffer] = ColorUtils.blend(color, dstColor);
            }
        }
    }

    public void drawSprite(Sprite sprite, int xPos, int yPos) {
        this.drawSprite(sprite, xPos, yPos, this.defaultShader);
    }

    public void drawCircle(int xPos, int yPos, int radius, int color) {
        int xStart = xPos - radius;
        int yStart = yPos - radius;
        int width = 2 * radius;
        int height = 2 * radius;

        for (int y = 0; y <= height; y++) {
            int screenY = yStart + y;
            if (screenY < 0 || screenY >= this.screenHeight) continue;
            int rowOffset = screenY * this.screenWidth;
            for (int x = 0; x <= width; x++) {
                int screenX = xStart + x;
                if (x < 0 || x >= this.screenWidth) continue;
                if (x * x + y * y < radius * radius) {
                    int indexBuffer = screenX + rowOffset;
                    int dstColor = this.pixelBuffer[indexBuffer];
                    this.pixelBuffer[indexBuffer] = ColorUtils.blend(color, dstColor);
                }
            }
        }
    }

    public void drawRect(Rectangle rect, int color) {
        this.drawRect(rect.x, rect.y, rect.w, rect.h, color);
    }

    public void drawRect(int xPos, int yPos, int width, int height, int color) {
        for (int y = 0; y < height; y++) {
            int screenY = yPos + y;
            if (screenY < 0 || screenY >= this.screenHeight) continue;
            int rowOffset = screenY * this.screenWidth;
            for (int x = 0; x < width; x++) {
                int screenX = xPos + x;
                if (screenX < 0 || screenX >= this.screenWidth) continue;
                int indexBuffer = screenX + rowOffset;
                int dstColor = this.pixelBuffer[indexBuffer];
                this.pixelBuffer[indexBuffer] = ColorUtils.blend(color, dstColor);
            }
        }
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void drawString(String s, int x, int y) {
        this.textRenderer.drawString(this, s, x, y, null, defaultShader);;
    }

    public void drawString(String s, int x, int y, TextOptions txtOptions) {
        Shader shader = new CombinationShader()
                            .add(new ConstantColorShader(txtOptions.color))
                            .add(txtOptions.shader);
        this.drawRect(
            x, 
            y, 
            txtOptions.boundWidth, 
            txtOptions.boundHeight, 
            txtOptions.backgroundColor
        );
        this.textRenderer.drawString(this, s, x, y, txtOptions.font, shader);
        //this.textRenderer.drawString(this, s, x, y, txtOptions);
    }

    public void proceedImage() {
        return;
    }

}
