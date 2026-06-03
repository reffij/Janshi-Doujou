package engine.ui.elements;

import engine.ui.renderable.Renderable;

public class UIButton extends UIInteractiveElement {

    private Renderable defaultRable;
    private Renderable hoverRable;
    private Renderable pressedRable;
    private Renderable disabledRable;

    protected Runnable onActivate;

    public UIButton(
        Renderable defaultRable,
        Renderable hoverRable,
        Renderable pressedRable,
        Runnable onActivate
    ) {
        super(defaultRable);
        this.defaultRable = defaultRable;
        this.hoverRable = hoverRable;
        this.pressedRable = pressedRable;
        this.onActivate = onActivate;
    }

    public UIButton setOnAcivate(Runnable onActivate) {
        this.onActivate = onActivate;
        return this;
    }


    @Override
    protected void onActivate() {
        onActivate.run();
    }

    @Override
    protected Renderable getCurrentRable() {

        if (super.disabled && this.disabledRable != null) {
            return this.disabledRable;
        }

        if (super.pressed && this.pressedRable != null) {
            return pressedRable;
        }

        if (super.hovered && this.hoverRable != null) {
            return this.hoverRable;
        }

        return this.defaultRable;
    }


    @Override
    public void update(double dt) {
        if (this.defaultRable != null) {
            this.defaultRable.update(dt);
        }
        if (this.hoverRable != null) {
            this.hoverRable.update(dt);
        }
        if (this.pressedRable != null) {
            this.pressedRable.update(dt);
        }
        if (this.disabledRable != null) {
            this.disabledRable.update(dt);
        }
    }
}
