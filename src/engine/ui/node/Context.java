package engine.ui.node;

import engine.datastructures.Rectangle;


public class Context {
    public Rectangle borderBounds;
    public Rectangle contentBounds;

    public Context(Rectangle borderBounds, Rectangle contentBounds) {
        this.borderBounds = borderBounds;
        this.contentBounds = contentBounds;
    }
}
