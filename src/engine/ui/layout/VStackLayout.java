package engine.ui.layout;

import engine.datastructures.HAlign;
import engine.datastructures.Rectangle;
import engine.ui.elements.UIElement;
import engine.ui.node.Context;

public class VStackLayout implements Layout {
    
    private int gap;
    private int yOffset = 0;
    private HAlign hAlign = HAlign.LEFT;



    @Override
    public boolean controlsX() { return false; }

    @Override
    public boolean controlsY() { return true; }

    @Override
    public Layout setVSpacing(int gap) {
        this.gap = gap;
        return this;
    }

    @Override
    public Layout setHSpacing(int gap) {
        return this;
    }

    @Override
    public Context next(UIElement e, Context parentCtx) {
        int elWidth = e.getWidth();
        int elHeight = e.getHeight();

        int initialX = this.hAlign.computeLeft(
            parentCtx.contentBounds.x, 
            parentCtx.contentBounds.w, 
            elWidth
        );

        int finalX = initialX + e.getX();
        int finalY = parentCtx.contentBounds.y + this.yOffset + e.getMTop() + e.getY();

        Rectangle borderBounds = new Rectangle(
            finalX,
            finalY,
            elWidth,
            elHeight
        );
        Rectangle contentBounds = new Rectangle(
            finalX + e.getPLeft(), 
            finalY + e.getPTop(), 
            e.getWidth(), 
            e.getHeight()
        );

        this.yOffset += elHeight + e.getMTop() + e.getMBottom() + this.gap;

        return new Context(borderBounds, contentBounds);
    }

    @Override
    public void reset() {
        this.yOffset = 0;
    }

    @Override
    public void HAlign(HAlign hA) {
        this.hAlign = hA;
    }

    @Override
    public void VAlign(engine.datastructures.VAlign vA) {
        return;
    }

}
