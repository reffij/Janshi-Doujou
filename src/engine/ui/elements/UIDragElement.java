package engine.ui.elements;

import engine.datastructures.FString;
import engine.datastructures.Pair;
import engine.ui.renderable.Renderable;

public class UIDragElement extends UIControl<Pair<Integer, Integer>>{

    boolean wasDragging;

    public UIDragElement(Renderable rable) {
        super(rable);
        this.setValue(new Pair<Integer, Integer>(0, 0));
    }

    public UIDragElement(int width, int height) {
        super(width, height);
        this.setValue(new Pair<Integer, Integer>(0, 0));
    }

    @Override
    protected void onDrag(int localX, int localY) {
        int newMouseX = super.getX() + localX;
        int newMouseY = super.getY() + localY;
        if (!this.wasDragging) {
            this.value.a = newMouseX;
            this.value.b = newMouseY;
        }
        int dx = newMouseX - this.value.a;
        int dy = newMouseY - this.value.b;

        super.setPos(this.getX() + dx, this.getY() + dy);
        this.value.a = newMouseX;
        this.value.b = newMouseY;
        this.wasDragging = true;
    }

    @Override
    protected void onRelease() {
        super.onRelease();
        this.wasDragging = false;
        this.value.a = 0;
        this.value.b = 0;
    }
}
