package engine.inputs;

import java.awt.event.MouseEvent;

public class MEvent {
    private int x, y, clickCount;

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getClickCount() {
        return this.clickCount;
    }

    public MEvent(MouseEvent event) {
        this.clickCount = event.getClickCount();
        this.x = event.getX();
        this.y = event.getY();
    }
}
