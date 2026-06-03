package main.scenes;

import java.awt.event.KeyEvent;

import engine.core.Scene;
import engine.graphics.render.Renderer;
import engine.ui.node.UINode;
import main.JDGame;
import main.JDComponents;

public class IntroScene implements Scene{

    UINode sceneLayer;
    UINode sceneNode;

    public IntroScene (JDGame game, UINode sceneLayer) {
        this.sceneLayer = sceneLayer;
        this.sceneNode = JDComponents.createIntro();
    }

    @Override
    public void update(double dt) {}

    @Override
    public void render(Renderer r) {}

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
        this.sceneLayer.addChild(
            this.sceneNode
        );
    }

    @Override
    public void onExit() {
        this.sceneLayer.removeChildren();
    }


}
