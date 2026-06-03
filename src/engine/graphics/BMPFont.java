package engine.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * {@code BMPFont} represents a bitmap font resource, mapping ASCII characters
 * to individual sprites. This class is compatible with 
 * {@link engine.graphics.renderer} for rendering text.
 *
 * <p>
 * It loads font configuration from a JSON file and character metrics from a CSV 
 * file located in {@code assets/fonts/}.
 * </p>
 *
 * <h3>JSON format (required)</h3>
 * <pre>
 * {
 *      bmp: "yourbmpfile.bmp",
 *      csv: "yourcsvfile.csv",
 *      width: 8,
 *      height: 12
 * }
 * </pre>
 * - <b>bmp</b> : name of the bitmap file containing the font sprites.  
 * - <b>csv</b> : name of the CSV file containing character widths, offsets, and advances.  
 * - <b>width</b> : width of a single character cell in the bitmap.  
 * - <b>height</b> : height of a single character cell in the bitmap.  
 * 
 * <h3>Bitmap format (required)</h3>
 * <ul>
 *     <li>The bitmap file ({@code bmp}) should contain an 8x16 grid of ASCII characters (128 total).</li>
 *     <li>The characters are arranged in order: ASCII 0–127, left-to-right, top-to-bottom.</li>
 *     <li>Magenta (RGB 255,0,255) is treated as a transparent color when cropping sprites.</li>
 * </ul>
 * 
 * <h3>CSV format (required)</h3>
 * <pre>
 * ASCII,width,xOffset,xAdvance
 * ...
 * 65,5,1,6
 * 66,5,1,6
 * ...
 * </pre>
 * - Each row represents a character.  
 * - <b>ASCII</b> : ASCII code of the character.  
 * - <b>width</b> : actual width of the character sprite.  
 * - <b>xOffset</b> : horizontal offset inside the bitmap.  
 * - <b>xAdvance</b> : horizontal advance when rendering text.
 * 
 * <p>
 * Preconditions: the JSON, BMP, and CSV files must exist in {@code assets/fonts/}.
 * </p>
 * 
 */
public class BMPFont {
    
    private final String dirPath = "assets/fonts/";

    private Sprite[] asciiSprites;
    private int[] charWidths;
    private int[] xOffsets;
    private int[] xAdvances;
    private int fontHeight;

    /**
     * Loads a bitmap font from the specified JSON configuration.
     *
     * @param fontName the name of the font (without the .json extension)
     * @throws RuntimeException if the font cannot be loaded due to missing or malformed files
     */
    public BMPFont(String fontName) {
        try {
            Map<String, String> config = this.parseJSON(dirPath + fontName + ".json");


            this.asciiSprites = new Sprite[128];

            int width = Integer.parseInt(config.get("width"));
            int height = Integer.parseInt(config.get("height"));
            this.fontHeight = height;

            this.parseCSV(dirPath + config.get("csv"));

        
            //16x8 grid of ascii chars
            Sprite bottomSprite = SpriteLoader.loadSprite(dirPath + config.get("bmp"));
            for (int y = 0; y < 8; y++) {
                int bottomSpriteY = y * height;
                for (int x = 0; x < 16; x++) {
                    int charCode = x + y * 16;
                    int bottomSpriteX = (x * width) + this.xOffsets[charCode];
                    int charWidth = this.charWidths[charCode];
                    if (charWidth > 0) {
                        this.asciiSprites[charCode] = SpriteUtils.crop(bottomSprite, bottomSpriteX, bottomSpriteY, charWidth, height);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load bitmap font: " + fontName, e);
        }
    }

    private Map<String, String> parseJSON(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
            Map<String, String> res = new HashMap<>();
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.equals("{") || line.equals("}")) {
                    continue;
                }

                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1);
                }

                String[] parts = line.split(":", 2);

                String key = parts[0]
                    .trim()
                    .replace("\"", "");
                String val = parts[1]
                    .trim()
                    .replace("\"", "");

                res.put(key, val);
            }
            br.close();
            return res;
    }

    private void parseCSV(String path) throws IOException {
        this.charWidths = new int[128];
        this.xOffsets = new int[128];
        this.xAdvances = new int[128];

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        line = br.readLine();

        if (line == null) {
            br.close();
            return;
        }

        if (line.toLowerCase().contains("ascii")) line = br.readLine();

        while (line != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                String[] values = line.split(",");
                int charCode = Integer.parseInt(values[0].trim());
                int width = Integer.parseInt(values[1].trim());
                int xOffset = Integer.parseInt(values[2].trim());
                int xAdvance = Integer.parseInt(values[3].trim());
                if (charCode >= 0 && charCode < 128) {
                    this.charWidths[charCode] = width;
                    this.xOffsets[charCode] = xOffset;
                    this.xAdvances[charCode] = xAdvance;
                }
            }
            
            line = br.readLine();
        }
        br.close();
    }

    /**
     * Returns the {@link Sprite} for the given character.
     *
     * @param c ASCII character
     * @return the sprite, or null if the character is not supported
     */
    public Sprite getChar(char c) {
        if (c < 0 || c >= asciiSprites.length) return null;
        return asciiSprites[c];
    }

    /**
     * Returns the width of the specified character.
     *
     * @param c ASCII character
     * @return the width, or 0 if character is unsupported
     */
    public int getCharWidth(char c) {
        if (c < 0 || c >= asciiSprites.length) return 0;
        return this.charWidths[c];
    }

    public int getStringWidth(String s) {
        int res = 0;
        for (char c : s.toCharArray()) {
            res += this.getXAdvance(c);
        }
        return res;
    }

    public List<String> splitByWidth(String s, int width) {
        int currentXAdvance = 0;
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (currentXAdvance < width && c != '\n') {
                sb.append(c);
                currentXAdvance += this.getXAdvance(c);
            } else {
                res.add(sb.toString());
                sb.setLength(0);;
                currentXAdvance = 0;
            }
        }
        if (sb.length() != 0) res.add(sb.toString());
        return res;
    }

    /**
     * Returns the horizontal advance of the specified character.
     *
     * @param c ASCII character
     * @return the xAdvance, or 0 if character is unsupported
     */
    public int getXAdvance(char c) {
        if (c < 0 || c >= asciiSprites.length) return 0;
        return this.xAdvances[c];
    }

    /**
     * Returns the height of the font (all characters share the same height).
     *
     * @return font height in pixels
     */
    public int getFontHeight() {
        return this.fontHeight;
    }

}