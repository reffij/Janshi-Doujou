package engine.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.core.Game;

import java.awt.Toolkit;
import java.awt.datatransfer.*;

public class KeyboardInputs implements KeyListener {

    private Game<?, ?> game;

    int panelWidth;
    int panelHeight;
    int screenWidth;
    int screenHeight;


    public KeyboardInputs(Game<?, ?> game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.game.keyTyped(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.game.keyPressed(e);
    }

    public static String pasteFromClipboard() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String res = (String) clipboard.getData(DataFlavor.stringFlavor);
            res = res.replaceAll("[^\\x20-\\x7E]", "");
            return res;
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return null;
    }
}
