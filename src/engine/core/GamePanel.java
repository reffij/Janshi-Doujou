package engine.core;

import java.awt.Graphics;

import javax.swing.JPanel;

import engine.graphics.render.Renderer;
import engine.inputs.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Renderer renderer;
    private Game<?, ?> game;


    public GamePanel(Game<?, ?> game, Renderer renderer) {
        
        mouseInputs = new MouseInputs(game);
        this.game = game;
        this.renderer = renderer;
        this.addKeyListener(new KeyboardInputs(this.game));
        this.addMouseListener(mouseInputs);
        this.addMouseMotionListener(mouseInputs);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(renderer.getImage(), 0, 0, getWidth(), getHeight(), null);
    }

}