package engine.ui.layout;

import engine.datastructures.HAlign;
import engine.datastructures.VAlign;
import engine.ui.elements.UIElement;
import engine.ui.node.Context;

public interface Layout {

    Context next(UIElement e, Context parentCtx);
    void reset();

    boolean controlsX();
    boolean controlsY();

    Layout setVSpacing(int gap);
    Layout setHSpacing(int gap);

    void HAlign(HAlign hA);
    void VAlign(VAlign vA);
}
