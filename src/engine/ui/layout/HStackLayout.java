package engine.ui.layout;

import engine.datastructures.HAlign;
import engine.datastructures.Rectangle;
import engine.datastructures.VAlign;
import engine.ui.elements.UIElement;
import engine.ui.node.Context;

public class HStackLayout implements Layout {

    private int gap = 0;
    private VAlign vAlign = VAlign.TOP;
    private int xOffset = 0;

    @Override
    public Context next(UIElement e, Context parentCtx) {
        int elWidth = e.getWidth();
        int elHeight = e.getHeight();
        
        int initialY = this.vAlign.computeTop(
            parentCtx.contentBounds.y, 
            parentCtx.contentBounds.h, 
            elHeight
        );

        int finalX = parentCtx.contentBounds.x + this.xOffset + e.getMLeft() + e.getX();
        int finalY = initialY + e.getY();

        Rectangle borderBounds = new Rectangle(finalX, finalY, elWidth, elHeight);
        Rectangle contentBoundsBounds = new Rectangle(
            finalX + e.getPLeft(), 
            finalY + e.getPTop(), 
            e.getWidth(), 
            e.getHeight()
        );

        this.xOffset += elWidth + e.getMLeft() + e.getMRight() + this.gap;

        return new Context(borderBounds, contentBoundsBounds);
    }

    @Override
    public void reset() {
        this.xOffset = 0;
    }

    @Override
    public boolean controlsX() { return true; }

    @Override
    public boolean controlsY() { return false; }

    @Override
    public Layout setVSpacing(int gap) {
        return this;
    }

    @Override
    public Layout setHSpacing(int gap) {
        this.gap = gap;
        return this;
    }

    @Override
    public void HAlign(HAlign hA) {
        return;
    }

    @Override
    public void VAlign(VAlign vA) {
        this.vAlign = vA;
    }


}
