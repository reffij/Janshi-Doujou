package engine.ui.elements;

import engine.datastructures.HAlign;
import engine.datastructures.VAlign;
import engine.graphics.render.Renderer;
import engine.ui.layout.AbsoluteLayout;
import engine.ui.layout.GridLayout;
import engine.ui.layout.HStackLayout;
import engine.ui.layout.Layout;
import engine.ui.layout.VStackLayout;
import engine.ui.renderable.Renderable;

import java.awt.event.KeyEvent;

public class UIElement {

    private int x;
    private int y;
    private int width;
    private int height;
    protected Renderable rable;

    private HAlign xAlign = HAlign.LEFT;
    private VAlign yAlign = VAlign.TOP;

    private int pTop, pRight, pLeft, pBottom;
    private int mTop, mRight, mLeft, mBottom;
    private Layout layout = new AbsoluteLayout();


    //Constructors

    public UIElement(int width, int height) {
        this.setDimensions(width, height);
    }

    public UIElement(Renderable rable) {
        this.rable = rable;
        this.setDimensions(rable.getWidth(), rable.getHeight());
    }

    //lifecycle

    public void update(double dt) {
        if (rable == null) return;
        this.rable.update(dt);
    }


    public void render(Renderer r, int globalX, int globalY) {
        Renderable current = this.getCurrentRable();

        if (current == null) return;
        current.render(r, globalX, globalY);
    }

    public void mouseMoved(int globalX, int globalY, int mouseX, int mouseY) {}
    public void mousePressed() {}
    public void mouseReleased() {}
    public void keyPressed(KeyEvent keyCode) {}
    public void keyTyped(char c) {}
    public void mouseDragged(int globalX, int globalY, int mouseX, int mouseY) {}



    //getters

    protected Renderable getCurrentRable() {
        return this.rable;
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}
    public int getPLeft() {return this.pLeft;}
    public int getPRight() {return this.pRight;}
    public int getPTop() {return this.pTop;}
    public int getPBottom() {return this.pBottom;}
    public int getMLeft() {return this.mLeft;}
    public int getMRight() {return this.mRight;}
    public int getMTop() {return this.mTop;}
    public int getMBottom() {return this.mBottom;}


    //Setters

    public UIElement setPos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public UIElement setRable(Renderable rable) {
        this.rable = rable;
        this.setDimensions(rable.getWidth(), rable.getHeight());
        return this;
    }

    protected UIElement setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public UIElement setAlignment(HAlign ha, VAlign va) {
        this.xAlign = ha;
        this.yAlign = va;
        this.layout.HAlign(ha);
        this.layout.VAlign(va);
        return this;
    }

    public UIElement setPadding(int pixels) {
        this.pTop = this.pRight = this.pBottom =  this.pLeft = pixels;
        return this;
    }

    public UIElement setPaddingTop(int pixels) {
        this.pTop = pixels;
        return this;
    }

    public UIElement setPaddingBotton(int pixels) {
        this.pBottom = pixels;
        return this;
    }

    public UIElement setPaddingRight(int pixels) {
        this.pRight = pixels;
        return this;
    }

    public UIElement setPaddingLeft(int pixels) {
        this.pLeft = pixels;
        return this;
    }

    public UIElement setMargin(int pixels) {
        this.mTop = this.mRight = this.mBottom = this.mLeft = pixels;
        return this;
    }

    public UIElement setMarginTop(int pixels) {
        this.pTop = pixels;
        return this;
    }

    public UIElement setMarginBottom(int pixels) {
        this.pBottom = pixels;
        return this;
    }

    public UIElement setMarginRight(int pixels) {
        this.pRight = pixels;
        return this;
    }

    public UIElement setMarginLeft(int pixels) {
        this.pLeft = pixels;
        return this;
    }

    public UIElement setLayout(Layout layout) {
        this.layout = layout;
        this.layout.HAlign(this.xAlign);
        this.layout.VAlign(this.yAlign);
        return this;
    }

    //HAlign

    public UIElement alignHLeft() {
        return this.setAlignment(HAlign.LEFT, this.yAlign);
    }

    public UIElement alignHCenter() {
        return this.setAlignment(HAlign.CENTER, this.yAlign);
    }

    public UIElement alignHRight() {
        return this.setAlignment(HAlign.RIGHT, this.yAlign);
    }

    //VAlign

    public UIElement alignVTop() {
        return this.setAlignment(this.xAlign, VAlign.TOP);
    }

    public UIElement alignVCenter() {
        return this.setAlignment(this.xAlign, VAlign.CENTER);
    }

    public UIElement alignVBottom() {
        return this.setAlignment(this.xAlign, VAlign.BOTTOM);
    }

    //Layout

    public UIElement layoutAbsolute() {
        return this.setLayout(new AbsoluteLayout());
    }

    public UIElement layoutVStack() {
        return this.setLayout(new VStackLayout());
    }

    public UIElement layoutHStack() {
        return this.setLayout(new HStackLayout());
    }

    public UIElement layoutGrid(int rows, int cols) {
        return this.setLayout(new GridLayout(rows, cols));
    }

    public Layout getLayout() {
        return this.layout;
    }
}
