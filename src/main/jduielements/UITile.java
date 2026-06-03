package main.jduielements;

import engine.graphics.Sprite;
import engine.ui.elements.UIControl;

public class UITile extends UIControl<Integer> {

    private UITile(int width, int height) {
        super(width, height);
    }
    
    public UITile(Sprite tileSprite) {
        super(tileSprite.width, tileSprite.height);
    }
}
