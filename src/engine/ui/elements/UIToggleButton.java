package engine.ui.elements;

import engine.ui.renderable.Renderable;

public class UIToggleButton extends UIControl<Boolean> {
    
    protected Renderable offRable;
    protected Renderable onRable;
    protected Runnable onToggleOn = () -> {};
    protected Runnable onToggleOff = () -> {};

    public UIToggleButton(
        Renderable offRable,
        Renderable onRable
    ) {
        super(offRable.getWidth(), offRable.getHeight());

        this.offRable = offRable;
        this.onRable = onRable;

        super.value = false;
    }

    @Override
    protected void onActivate() {
        super.setValue(!this.value);
        this.onValueChange(this.value);
    }

    @Override
    protected Renderable getCurrentRable() {
        return super.value ? onRable : offRable;
    }

    @Override
    public void update(double dt) {
        offRable.update(dt);
        onRable.update(dt);
    }

    @Override
    protected void onValueChange(Boolean value) {
        if (value) this.onToggleOn.run();
        else this.onToggleOff.run();
    }

    public UIToggleButton setOnToggle(Runnable onToggleOn, Runnable onToggleOff) {
        if (onToggleOn == null) this.onToggleOn = () -> {};
        if (onToggleOff == null) this.onToggleOff = () -> {};
        this.onToggleOn = onToggleOn;
        this.onToggleOff = onToggleOff;
        return this;
    }
}
