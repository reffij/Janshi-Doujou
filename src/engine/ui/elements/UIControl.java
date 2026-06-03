package engine.ui.elements;

import engine.ui.renderable.Renderable;

public class UIControl<T> extends UIInteractiveElement {

    protected T value;

    public UIControl(int width, int height) {
        super(width, height);
    }

    public UIControl(Renderable rable) {
        super(rable);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.onValueChange(value);
    }

    protected void onValueChange(T value) {}
}
