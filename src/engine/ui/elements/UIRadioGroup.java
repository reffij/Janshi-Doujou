package engine.ui.elements;

import java.util.ArrayList;
import java.util.List;

public class UIRadioGroup {
    
    private final List<UIRadioButton> buttons = new ArrayList<>();

    public void add(UIRadioButton button) {
        buttons.add(button);
    }

    public void select(UIRadioButton selected) {
        for (UIRadioButton button : buttons) {
            button.setValue(button == selected);
        }
    }
}
