package engine.ui.layout;

import engine.datastructures.HAlign;
import engine.datastructures.Rectangle;
import engine.datastructures.VAlign;
import engine.ui.elements.UIElement;
import engine.ui.node.Context;

public class AbsoluteLayout implements Layout {

    HAlign hAlign = HAlign.LEFT;
    VAlign vAlign = VAlign.TOP;

    @Override
    public boolean controlsX() {
        return false;
    }

    @Override
    public boolean controlsY() {
        return false;
    }

    @Override
    public Layout setVSpacing(int gap) {
        return this;
    }

    @Override
    public Layout setHSpacing(int gap) {
        return this;
    }

    @Override
    public Context next(UIElement e, Context parentCtx) {
        int elBoundsWidth = e.getWidth() + e.getPLeft() + e.getPRight();
        int elBoundsHeight = e.getHeight() + e.getPTop() + e.getPBottom();


        int initialX = this.hAlign.computeLeft(parentCtx.contentBounds.x, parentCtx.contentBounds.w, elBoundsWidth);
        int initialY = this.vAlign.computeTop(parentCtx.contentBounds.y, parentCtx.contentBounds.h, elBoundsHeight);

        int finalX = initialX + e.getX();
        int finalY = initialY + e.getY();

        Rectangle borderBounds = new Rectangle(
            finalX, 
            finalY, 
            elBoundsWidth, 
            elBoundsHeight
        );

        Rectangle contentBounds = new Rectangle(
            finalX + e.getPLeft(), 
            finalY + e.getPTop(), 
            e.getWidth(), 
            e.getHeight()
        );
        return new Context(borderBounds, contentBounds);
    }

    @Override
    public void reset() {}

    @Override
    public void HAlign(HAlign hA) {
        this.hAlign = hA;
    }

    @Override
    public void VAlign(VAlign vA) {
        this.vAlign = vA;
    }

}
