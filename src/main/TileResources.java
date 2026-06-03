package main;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import engine.graphics.ColorUtils;
import engine.graphics.Sprite;
import engine.graphics.SpriteLoader;
import engine.graphics.SpriteUtils;
import main.data.TileColor;
import main.data.TilePrimitive;
import main.gameobjects.tiles.Tile;

public class TileResources {

    //TODO CACHING

    Sprite[] tileSprites;
    private int tileWidth;
    private int tileHeight;

    final int BACKCOLOR = ColorUtils.rgb(251, 167, 104);
    final int BACKCOLORSHADE = ColorUtils.rgb(255, 127, 39);
    final int WHITE = ColorUtils.rgb(255, 255, 255);
    final int WHITESHADE = ColorUtils.rgb(127, 127, 127);


    public TileResources() {
        this.tileSprites = new Sprite[34];
        int overlayXPos = 2;
        int overlayYpos = 8;

        try {
            Sprite bottomSprite = SpriteLoader.loadSprite("assets/tiles/front-top.bmp");
            for (int i = 0; i < 34; i++) {
                    String numString = Integer.toString(i);
                    Sprite topSprite = SpriteLoader.loadSprite("assets/tiles/" + numString + ".bmp");

                    Sprite sprite = SpriteUtils.overlay(bottomSprite, topSprite, overlayXPos, overlayYpos);

                    tileSprites[i] = sprite;
            }

        } catch (IOException e) {
            System.err.println("Error loading tile sprites");
        }

        this.tileWidth = this.tileSprites[0].width;
        this.tileHeight = this.tileSprites[0].height;
    }

    public Sprite getTileSprite(int i) {
        return this.tileSprites[i];
    }

    public Sprite getTileSprite(TilePrimitive tile) {
        return this.tileSprites[tile.getInt()];
    }

    public int tileColorToColor(TileColor color) {
        switch(color) {
            case TileColor.NONE:
                return -1;
            case TileColor.RED:
                return ColorUtils.rgb(255, 0, 0);
            case TileColor.YELLOW:
                return ColorUtils.rgb(255, 255, 0);
            case TileColor.WHITE:
                return ColorUtils.rgb(128, 128, 128);
            case TileColor.BLUE:
                return ColorUtils.rgb(0, 0, 255);
            case TileColor.GREEN:
                return ColorUtils.rgb(0, 255, 0);
        }
        return -1;
    }

    public Sprite getColorSprite(Sprite tileSprite, TileColor color) {
        if (color == TileColor.NONE) return tileSprite;
        Set<Number> colorSet = new HashSet<Number>();
        colorSet.add(this.BACKCOLOR);
        colorSet.add(this.BACKCOLORSHADE);
        colorSet.add(this.WHITE);
        colorSet.add(this.WHITESHADE);

        int newColor = tileColorToColor(color);
        Sprite res = SpriteUtils.invertMapColor(tileSprite, colorSet, newColor);
        return res;
    }

    public Sprite getTileSprite(Tile tile) {
        if (tile.getColor() == TileColor.NONE) return this.tileSprites[tile.getInt()];
        return getColorSprite(this.tileSprites[tile.getInt()], tile.getColor());
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

}
