package engine.graphics.render.text;

import java.util.ArrayList;
import java.util.List;

import engine.builtin.fonts.Fonts;
import engine.graphics.BMPFont;
import engine.graphics.Shader;
import engine.graphics.Sprite;
import engine.graphics.render.Renderer;

public class TextRenderer {

    BMPFont defaultFont;

    public TextRenderer() {
        this.defaultFont = Fonts.JMICKLE;
    }

    private List<Integer> calculate_widths(List<String> words, BMPFont font) {
        List<Integer> res = new ArrayList<>();
        for (String word : words) {
            int width = 0;
            for (int i = 0; i < word.length(); i++) {
                width += font.getXAdvance(word.charAt(i));
            }
            res.add(width);
        }
        return res;
    }

    private List<String> calculate_lines(
        List<String> words, 
        List<Integer> widths, 
        int height,
        int boundWidth,
        int boundHeight
    ) {
        List<String> res = new ArrayList<>();
        res.add("");

        int currentWidth = 0;
        int currentHeight = 0;
        int currentLine = 0;
        for (int i = 0; i < words.size(); i++) {
            //CHECK AVAILABLE Y SPACE, if none break
            if (currentHeight + height > boundHeight) break;


            //CASE can add next word
            if (currentWidth + widths.get(i) < boundWidth) {
                String line = res.get(currentLine);
                line += " " + words.get(i);
                res.set(currentLine, line);
                currentWidth += widths.get(i);
                continue;
            }
            //CASE cannot add next word and word is beyond limit
            //DOSOMETHING
            
            //CASE cannot add next word but less than boundLimit
            else {
                res.add(words.get(i));
                currentLine++;
                currentWidth = widths.get(i);
            }
        }

        return res;
    }

    public void drawString(Renderer r, String s, int x, int y, TextOptions options) {
        List<String> words = List.of(s.split(" "));
        List<Integer> widths = calculate_widths(words, options.font);

        List<String> lines = calculate_lines(
            words, 
            widths, 
            options.font.getFontHeight(), 
            options.boundWidth, 
            options.boundHeight
        );

        List<Integer> lineWidths = calculate_widths(lines, options.font);
        int height = options.font.getFontHeight() * lines.size();

        int cursorY = options.vAlign.computeTop(y, options.boundHeight, height);
        for (int i = 0; i < lines.size(); i++) {
            int cursorX = options.hAlign.computeLeft(x, options.boundWidth, lineWidths.get(i));
            this.drawString(r, lines.get(i), cursorX, cursorY, options.font, options.shader);
            cursorY += options.font.getFontHeight();
        }
    }

    public void drawString(Renderer r, String s, int x, int y, BMPFont font, Shader shader) {

    if (font == null) font = this.defaultFont;

    int cursorX = x;
    int cursorY = y;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '\n') {
                cursorX = x;
                cursorY += font.getFontHeight();
                continue;
            }

            Sprite glyph = font.getChar(c);

            if (glyph != null) {
                r.drawSprite(glyph, cursorX, cursorY, shader);
                cursorX += font.getXAdvance(c);
            }
        }
    }
}
