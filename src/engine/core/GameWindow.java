package engine.core;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
    
    public GameWindow() {
        ImageIcon icon = new ImageIcon("assets/icon2.png");
        this.setIconImage(icon.getImage());
        this.setSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setResizable(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

}
