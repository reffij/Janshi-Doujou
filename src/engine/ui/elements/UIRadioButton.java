package engine.ui.elements;

import engine.ui.renderable.Renderable;

public class UIRadioButton extends UIToggleButton {
    
    private final UIRadioGroup group;

    public UIRadioButton(
        UIRadioGroup group,
        Renderable offRable,
        Renderable onRable
    ) {
        super(offRable, onRable);

        this.group = group;
        
        group.add(this);
    }

    @Override
    protected void onActivate() {
        group.select(this);
    }
}
