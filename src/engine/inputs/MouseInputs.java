package engine.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import engine.core.Game;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private Game<?, ?> game;

    public MouseInputs(Game<?, ?> game) {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        this.game.mouseDragged(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        this.game.mouseMoved(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        this.game.mousePressed();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        this.game.mouseReleased();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}