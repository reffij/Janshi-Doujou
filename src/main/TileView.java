package main;

import engine.graphics.Sprite;
import engine.ui.elements.UIButton;
import engine.ui.renderable.Renderable;
import engine.ui.renderable.SpriteRable;

public class TileView extends UIButton {

    public TileView(Sprite tileSprite, Runnable onActivate) {
        Renderable tileRable = new SpriteRable(tileSprite);
        super(tileRable, tileRable, tileRable, onActivate);
    }

    @Override
    protected void onHoverEnter() {
        super.setPos(this.getX(), this.getY() - 10);
        super.onHoverEnter();
    }

    @Override
    protected void onHoverExit() {
        super.setPos(this.getX(), this.getY() + 10);
        super.onHoverExit();
    }

}
