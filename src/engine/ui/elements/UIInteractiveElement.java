package engine.ui.elements;

import engine.datastructures.FString;
import engine.ui.renderable.Renderable;

public abstract class UIInteractiveElement extends UIElement {

    protected MouseHitbox mouseHitbox;
    protected boolean hovered = false;
    protected boolean pressed = false;
    protected boolean focused = true;
    protected boolean disabled = false;
    protected boolean dragging = false;
    
    private Runnable onActivate = () -> {};
    private Runnable onHoverEnter  = () -> {};
    private Runnable onHoverExit  = () -> {};
    private Runnable onPress  = () -> {};


    UIInteractiveElement(int width, int height) {
        super(width, height);
        this.mouseHitbox = new MouseHitbox(width, height);
    }

    UIInteractiveElement(Renderable rable) {
        super(rable);
        this.mouseHitbox = new MouseHitbox(rable.getWidth(), rable.getHeight());
    }

    @Override
    protected UIElement setDimensions(int width, int height) {
        super.setDimensions(width, height);
        if (this.mouseHitbox == null) {
            this.mouseHitbox = new MouseHitbox(width, height);
        }
        this.mouseHitbox.setDimensions(width, height);
        return this;
    }

    @Override
    public void mouseMoved(int globalX, int globalY, int mouseX, int mouseY) {
        int localMouseX = mouseX - globalX;
        int localMouseY = mouseY - globalY;

        boolean oldHovered = this.hovered;
        this.hovered = this.mouseHitbox.contains(localMouseX, localMouseY);
        if (!oldHovered && hovered) {
            this.onHoverEnter();
        }

        if (oldHovered && !hovered) {
            this.onHoverExit();
        }
    }

    @Override
    public void mousePressed() {
        if (this.disabled) return;

        if (this.hovered) {
            this.pressed = true;
            this.onPress();
        }
    }

    @Override
    public void mouseReleased() {
        if (this.disabled) return;
        if (this.pressed && this.hovered) {
            this.onActivate();
        }
        this.pressed = false;
        this.dragging = false;

        this.onRelease();
    }

    public void mouseDragged(int globalX, int globalY, int mouseX, int mouseY) {
        if (this.disabled) return;
        if (this.pressed) {
            this.dragging = true;

            int localMouseX = mouseX - globalX;
            int localMouseY = mouseY - globalY;

            this.onDrag(localMouseX, localMouseY);
        }
    }

    //HOOKS

    protected void onHoverEnter() {}
    protected void onHoverExit() {}
    protected void onPress() {}
    protected void onRelease() {}
    protected void onActivate() {}
    protected void onDrag(int localX, int localY) {}

    //STATE

    public boolean isHovered() {
        return this.hovered;
    }

    public boolean isPressed() {
        return this.pressed;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public UIInteractiveElement setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }
}
