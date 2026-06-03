package engine.core;

import java.awt.event.KeyEvent;

import engine.builtin.components.Components;
import engine.commands.CommandBus;
import engine.commands.CommandType;
import engine.commands.Console;
import engine.datastructures.FString;
import engine.events.EventBus;
import engine.graphics.render.SoftwareRenderer;
import engine.ui.elements.ConsoleUI;
import engine.ui.elements.UIElement;
import engine.ui.node.UINode;
import engine.ui.node.UITree;

public abstract class Game<T extends Enum<T> & CommandType<T>, C> {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private boolean running;
    private SoftwareRenderer r;
    private Scene currentScene;

    private EventBus<C> eventBus;
    private CommandBus<T, C> cmdBus;

    private C gameContext;
    private UITree uiTree;
    private UINode sceneLayer;
    private UINode globalLayer;

    private ConsoleUI<T> consoleUI;

    private int fps;
    private double tps;

    @SuppressWarnings("unchecked")
    public Game(Class<T> commandEnumClass, C gameContext) {
        this.running = true;
        this.r = new SoftwareRenderer(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        this.gameWindow = new GameWindow();
        this.gamePanel = new GamePanel(this, r);
        this.gameWindow.add(this.gamePanel);
        this.gamePanel.requestFocus();
        this.eventBus = new EventBus<C>(this, gameContext);
        this.cmdBus = new CommandBus<T, C>(this, gameContext);

        UINode rootNode = new UINode(new UIElement(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        this.uiTree = new UITree(rootNode);
        this.sceneLayer = new UINode(new UIElement(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        this.globalLayer = new UINode(new UIElement(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
        rootNode.addChild(sceneLayer).addChild(globalLayer);
        UINode consoleNode = Components.createConsole(new Console<T>(cmdBus, commandEnumClass));
        if (consoleNode.getElement() instanceof ConsoleUI) this.consoleUI = (ConsoleUI<T>) consoleNode.getElement();
        this.globalLayer.addChild(consoleNode);
        this.globalLayer.addChild(Components.createFPSCounter(() -> this.getFPS()));
        UINode tpsNode = Components.createTPSCounter(() -> this.getTPS());
        tpsNode.getElement().setPos(50, 0);
        this.globalLayer.addChild(tpsNode);


        this.gameContext = gameContext;
        this.currentScene = null;
    }

    public C getGameContext() {
        return this.gameContext;
    }

    private int getFPS() {
        return this.fps;
    }

    private double getTPS() {
        return this.tps;
    }

    public UINode getSceneLayer() {
        return this.sceneLayer;
    }

    public void setScene(Scene newScene) {
        if (this.currentScene != null) {
            this.currentScene.onExit();
        }
        this.currentScene = newScene;
        this.currentScene.onEnter();
    }

    public Scene getScene() {
        return this.currentScene;
    }

    public EventBus<C> getEventBus() {
        return this.eventBus;
    }

    public CommandBus<T, C> getCMDBus() {
        return this.cmdBus;
    }

    private void update(double dt) {
        this.tps = 1.0 / dt;
        if (this.currentScene != null) {
            this.currentScene.update(dt);
        }
        this.eventBus.update(dt);
        this.cmdBus.update(dt);
        this.uiTree.update(dt);
    }

    
    private void render() {
        this.r.clear();

        if (this.currentScene != null) {
            this.currentScene.render(this.r);
        }

        this.uiTree.render(r);

        r.proceedImage();

        this.gamePanel.repaint();
    }

    public void start() {
        long FPS = Config.FPS;
        long TARGET_FRAME_TIME = 1_000_000_000L / FPS;

        int frames = 0;
        long fpsTimer = 0;

        long tLast = System.nanoTime();
        long tNow;
        double dt;
        double tAccumulator = 0.0;
        final double STEP = 1.0 / Config.TPS;

        while (this.running) {
            long frameStart = System.nanoTime();
            tNow = System.nanoTime();
            dt = (tNow - tLast) / 1_000_000_000.0;
            dt = Math.min(dt, 0.25);
            tLast = tNow;
            tAccumulator += dt;

            while (tAccumulator >= STEP) {
                this.update(STEP);
                tAccumulator -= STEP;
            }

            this.render();

            frames++;

            long frameTime = System.nanoTime() - frameStart;
            long sleepTime = TARGET_FRAME_TIME - frameTime;

            if (System.currentTimeMillis() - fpsTimer >= 1000) {
                this.fps = frames;
                frames = 0;
                fpsTimer = System.currentTimeMillis();
            }

            if (sleepTime > 0) {
                try{
                    Thread.sleep(sleepTime / 1_000_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void keyTyped(char c) {
        this.uiTree.keyTyped(c);
        this.currentScene.keyTyped(c);
    }

    public void keyPressed(KeyEvent e) {
        this.uiTree.keyPressed(e);
        this.currentScene.keyPressed(e);
    }

    public void mouseDragged(int x, int y) {
        this.uiTree.mouseDragged(x, y);
        this.currentScene.mouseDragged(x, y);
    }

    public void mouseMoved(int x, int y) {
        this.uiTree.mouseMoved(x, y);
        this.currentScene.mouseMoved(x, y);
    }

    public void mousePressed() {
        this.uiTree.mousePressed();
        this.currentScene.mousePressed();
    }

    public void mouseReleased() {
        this.uiTree.mouseReleased();
        this.currentScene.mouseReleased();
    }

    public void quit() {
        this.gameWindow.dispose();
        this.running = false;
        System.exit(0);
    }

    public void print(String s) {
        System.out.println(s);
        this.consoleUI.print(s);
    }

}
