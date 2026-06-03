package engine.inputs;

import java.awt.event.KeyEvent;

public class KEvent {
    private char c;

    private int keyCode;

    public char getChar() {
        return this.c;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public KEvent(KeyEvent event) {
        this.c = event.getKeyChar();
        this.keyCode = event.getKeyCode();
    }
}
