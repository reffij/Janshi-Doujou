package engine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class SpriteLoader {
    public static Sprite loadSprite(String path) throws IOException {

        BufferedImage img = ImageIO.read(new File(path));

        int width = img.getWidth();
        int height = img.getHeight();

        byte[] pixels = new byte[width * height];

        Map<Integer, Integer> paletteMap = new LinkedHashMap<>();
        List<Integer> paletteList = new ArrayList<>();

        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int color = img.getRGB(x, y);

                
                Integer paletteIndex = paletteMap.get(color);

                if (color == SpriteUtils.TRANSPARENTCOLORKEY) {
                    paletteIndex = (Integer) (int) SpriteUtils.NULLCOLORINDEX;
                }
                else if (paletteIndex == null) {
                    paletteIndex = paletteList.size();

                    if (paletteIndex >= 256) {
                        throw new RuntimeException("Sprite exceeds 256 colors");
                    }

                    paletteMap.put(color, paletteIndex);
                    paletteList.add(color);
                }

                pixels[index++] = (byte)(paletteIndex & 0xFF);
            }
        }

        Sprite sprite = new Sprite();
        sprite.width = width;
        sprite.height = height;
        sprite.pixels = pixels;

        sprite.palette = paletteList.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        return sprite;
    }
}
