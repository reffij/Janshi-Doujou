package engine.core;
import java.awt.event.KeyEvent;

import engine.graphics.render.Renderer;

public interface Scene {
    public void update(double dt);
    public void render(Renderer r) ;
    public void mouseMoved(int x, int y);
    public void mousePressed();
    public void mouseReleased();
    public void mouseDragged(int x, int y);
    public void keyPressed(KeyEvent keyEvent);
    public void keyTyped(char c);
    public void onEnter();
    public void onExit();
}
