package engine.ui.elements;

import java.util.ArrayList;
import java.util.List;

public class UIDragGroup {
    
    private final List<UIDragElement> elements = new ArrayList<>();

    private UIElement dragging;

    public void add(UIDragElement e) {
        elements.add(e);
    }

    public void add(int index, UIDragElement e) {
        elements.add(index, e);
    }

    public void drag(UIElement e) {
        this.dragging = e;
    }

    public void move(int index, UIDragElement e) {
        if (!elements.remove(e)) return;
        this.add(index, e);
    }

    public void startDrag(UIElement e) {
        this.dragging = e;
    }

    public void endDrag() {
        this.dragging = null;
    }
}
