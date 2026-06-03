package main;


import engine.builtin.fonts.Fonts;
import engine.graphics.BMPFont;

public class JDResources {
    public TileResources tileResources;
    public BMPFont jmickle_font = Fonts.JMICKLE;

    public JDResources() {
        this.tileResources = new TileResources();
    }
}
