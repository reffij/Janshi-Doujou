package engine.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import engine.datastructures.Rectangle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import utilities.MathUtils;

public class SpriteUtils {
    
    public static int TRANSPARENTCOLORKEY = 0xFFFF00FF;
    public static byte NULLCOLORINDEX = -1;

    public static Sprite copySprite(Sprite sprite) {
        Sprite res = new Sprite();
        res.height = sprite.height;
        res.width = sprite.width;
        res.pixels = new byte[res.height * res.width];
        res.palette = new int[sprite.palette.length];
        for (int i = 0; i < res.pixels.length; i++) {
            res.pixels[i] = sprite.pixels[i];
        }
        for (int i = 0; i < res.palette.length; i++) {
            res.palette[i] = sprite.palette[i];
        }
        return res;
    }

    public static Sprite mapColor(Sprite sprite, int colorKey, int newColor) {
        Sprite res = copySprite(sprite);
        for (int i = 0; i < res.palette.length; i++) {
            if (res.palette[i] == colorKey) {
                res.palette[i] = newColor;
            }
        }
        return res;
    }

    public static Sprite invertMapColor(Sprite sprite, int colorKey, int newColor) {
        Sprite res = copySprite(sprite);
        for (int i = 0; i < res.palette.length; i++) {
            if (res.palette[i] != colorKey) {
                res.palette[i] = newColor;
            }
        }
        return res;
    }

    public static Sprite invertMapColor(Sprite sprite, Set<Number> colorKeys, int newColor) {
        Sprite res = copySprite(sprite);
        for (int i = 0; i < res.palette.length; i++) {
            if (!colorKeys.contains(res.palette[i])) {
                res.palette[i] = newColor;
            }
        }
        return res;
    }

    public static Sprite overlay(Sprite bottomSprite, Sprite topSprite, int xPos, int yPos) {
        if (topSprite.height + yPos > bottomSprite.height ||
            topSprite.width + xPos > bottomSprite.width
            ) throw new IllegalStateException("top sprite must fit in bottom sprite");
        
        Sprite res = new Sprite();
        byte[] resPixels = new byte[bottomSprite.width * bottomSprite.height];
        res.pixels = resPixels;
        res.height = bottomSprite.height;
        res.width = bottomSprite.width;

        //create palette combine two palettes remove duplicates.
        HashMap<Integer, Number> palleteHashSet = new HashMap<>();
        byte index = 0;
        for (int color : bottomSprite.palette) {
            if (!palleteHashSet.containsKey(color)) {
                palleteHashSet.put(color, index);
                index++;
            }
        }
        for (int color : topSprite.palette) {
            if (!palleteHashSet.containsKey(color)) {
                palleteHashSet.put(color, index);
                index++;
            }
        }

        int[] resPalette = new int[index];
        res.palette = resPalette;
        for (Integer key : palleteHashSet.keySet()) {
            resPalette[(byte) palleteHashSet.get(key)] = (int) key;
        }

        //now place the res image with new pallete
        for (int i = 0; i < resPixels.length; i++) {
            int colorIndex = bottomSprite.pixels[i];
            if (colorIndex == NULLCOLORINDEX) {
                resPixels[i] = NULLCOLORINDEX;
                continue;
            }
            Integer color = bottomSprite.palette[colorIndex];
            resPixels[i] = (byte) palleteHashSet.get(color);
        }
        
        //now overwrite pixel data with top image
        for (int y = 0; y < topSprite.height; y++) {
            int rowOffset = y * topSprite.width;
            for (int x = 0; x < topSprite.width; x++) {
                int i = x + rowOffset;
                int colorIndex = topSprite.pixels[i];
                if (colorIndex == NULLCOLORINDEX) continue;
                Integer color = topSprite.palette[colorIndex];
                int j = xPos + x + ((yPos + y) * bottomSprite.width);
                resPixels[j] = (byte) palleteHashSet.get(color);
            }
        }
        return res;
    }

    public static Sprite crop(Sprite baseSprite, Rectangle rect) {
        return crop(baseSprite, rect.x, rect.y, rect.w, rect.h);
    }

    public static Sprite crop(Sprite baseSprite, int xPos, int yPos, int width, int height) {

        if (xPos < 0 || yPos < 0 ||
            xPos + width > baseSprite.width ||
            yPos + height > baseSprite.height) {
            throw new IllegalArgumentException("Crop out of bounds");
        }


        Sprite res = new Sprite();
        res.pixels = new byte[width * height];
        res.width = width;
        res.height = height;

        //color to index
        HashMap<Integer, Byte> palleteMap = new HashMap<>();

        //map color to index then fill pixels to that index
        int index = 0;
        for (int y = 0; y < height; y++) {
            
            int rowOffset = y * width;

            int baseY = y + yPos;
            int baseRowOffset = baseY * baseSprite.width;

            for (int x = 0; x < width; x++) {
                int baseX = x + xPos;

                byte colorIndex = baseSprite.pixels[baseX + baseRowOffset];

                if (colorIndex == NULLCOLORINDEX) {
                    res.pixels[x + rowOffset] = SpriteUtils.NULLCOLORINDEX;
                    continue;
                };
                int color = baseSprite.palette[colorIndex];
                if (!palleteMap.containsKey(color)) {
                    palleteMap.put(color, (byte) index);
                    index++;
                }
                res.pixels[x + rowOffset] = palleteMap.get(color);
            }
        }

        res.palette = new int[palleteMap.size()];
        for (int color : palleteMap.keySet()) {
            byte colorIndex = palleteMap.get(color);
            res.palette[colorIndex] = color;
        }


        return res;
    }

}
