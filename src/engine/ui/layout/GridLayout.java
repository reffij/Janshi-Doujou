package engine.ui.layout;

import engine.datastructures.Rectangle;
import engine.ui.elements.UIElement;
import engine.ui.node.Context;
import engine.datastructures.HAlign;
import engine.datastructures.VAlign;


public class GridLayout implements Layout {

    private final int rows;
    private final int cols;


    private int i = 0;

    private int xOffset = 0;
    private int yOffset = 0;

    private int colGap;
    private int rowGap;

    public GridLayout(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("rows and columns must be > 0");
        }
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public boolean controlsX() { return true; }

    @Override
    public boolean controlsY() { return true; }

    @Override
    public Layout setVSpacing(int gap) {
        this.rowGap = gap;
        return this;
    }

    @Override
    public Layout setHSpacing(int gap) {
        this.colGap = gap;
        return this;
    }

    private void updateOffsets(int cellWidth, int cellHeight) {
        xOffset += cellWidth + colGap;
        i++;
        if (i % this.cols == 0) {
            xOffset = 0;
            yOffset += cellHeight + rowGap;
        }
    }

    @Override
    public Context next(UIElement e, Context parentCtx) {
        int elWidth = e.getWidth();
        int elHeight = e.getHeight();

        int finalX = parentCtx.contentBounds.x + this.xOffset + e.getMLeft() + e.getX();
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

        int cellWidth = (parentCtx.contentBounds.w - (this.colGap * (this.cols - 1))) / this.cols;
        int cellHeight = (parentCtx.contentBounds.h - (this.rowGap * (this.rows - 1))) / this.rows;

        this.updateOffsets(cellWidth, cellHeight);

        return new Context(borderBounds, contentBounds);
    }

    @Override
    public void reset() {
        this.i = 0;
        this.xOffset = 0;
        this.yOffset = 0;
    }

    @Override
    public void HAlign(HAlign hA) {
        return;
    }

    @Override
    public void VAlign(VAlign vA) {
        return;
    }
}