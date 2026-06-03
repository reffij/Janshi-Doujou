package main.scenes;

import java.awt.event.KeyEvent;

import engine.core.Scene;
import engine.events.Event;
import engine.graphics.render.Renderer;
import engine.ui.node.UINode;
import main.JDEventType;
import main.JDGame;
import main.JDComponents;


public class MenuScene implements Scene {

    private final JDGame game;
    private final UINode sceneLayer;
    private final UINode sceneNode;

    public MenuScene(JDGame game, UINode sceneLayer) {
        this.game = game;
        this.sceneLayer = sceneLayer;
        this.sceneNode = JDComponents.createMenu(game.getCMDBus());
    }

    @Override
    public void update(double dt) {}

    @Override
    public void render(Renderer r) {
        r.drawString("MENU - Press Enter", 10, 10);
    }

    @Override
    public void mouseMoved(int x, int y) {}

    @Override
    public void mousePressed() {}

    @Override
    public void mouseReleased() {}

    @Override
    public void mouseDragged(int x, int y) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {}

    @Override
    public void keyTyped(char c) {}

    @Override
    public void onEnter() {
        this.sceneLayer.addChild(this.sceneNode);
        this.game.getEventBus().emit(new Event<>(JDEventType.MENU_ENTERED));
    }

    @Override
    public void onExit() {
        this.sceneLayer.removeChildren();
        this.game.getEventBus().emit(new Event<>(JDEventType.MENU_EXITED));
    }
    
}
