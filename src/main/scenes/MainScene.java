package main.scenes;

import java.awt.event.KeyEvent;
import java.util.List;

import engine.commands.Command;
import engine.core.Scene;
import engine.datastructures.FString;
import engine.events.Event;
import engine.graphics.render.Renderer;
import engine.ui.node.UINode;
import main.JDEventType;
import main.JDGame;
import main.JDGameContext;
import main.services.RoundService;
import main.JDCommandType;
import main.JDComponents;

public class MainScene implements Scene {
    private JDGame game;
    private JDGameContext gameContext;

    private final UINode sceneLayer;
    private final UINode sceneNode;
    private final UINode sceneGlobalLayer;
    private final UINode scenePhaseLayer;
    private RoundService RoundService;

    public MainScene(JDGame game, long seed, UINode sceneLayer) {
        this.game = game;

        this.gameContext = this.game.getGameContext();
        this.gameContext.init(seed);

        this.sceneLayer = sceneLayer;
        this.sceneNode = JDComponents.createMainScene();
        this.sceneGlobalLayer = JDComponents.createMainSceneGlobalLayer();
        this.scenePhaseLayer = JDComponents.createMainScenePhaseLayer();

        this.RoundService = new RoundService(seed, game.getCMDBus());
    }

    @Override
    public void update(double dt) {}

    @Override
    public void render(Renderer r) {
        r.drawString(
                "Play State\n"
                + "Prevelant Wind: \n"
                + this.gameContext.prevWind + "\n"
                + "Seat Wind: \n" 
                + this.gameContext.seatWind.toString() + "\n"
                + "Player Stats: \n"
                + "  Money: " + gameContext.money + "\n"
                + "  MP: " + gameContext.mp + "/" + gameContext.maxMp + "\n"
                + "  DoraSkill: " + gameContext.doraSkill + "\n"
                + "  SwapSkill: " + gameContext.swapSkill + "\n"
                + "  DrawSkill: " + gameContext.drawSkill + "\n"
                + "  Strength: " + gameContext.strength + "\n"
                , 10, 10);
        r.drawString("tcDrawDeck: " + gameContext.tcDrawPool, 400, 50);
        r.drawString("tcHand: " + gameContext.tcHandPool, 400, 60);
        r.drawString("tcDiscardPool: " + gameContext.tcDiscardPool, 400, 70);
        r.drawString(new FString("market: {}", List.of(gameContext.market)).toString(), 400, 30);
        r.drawString(
            "Heros: " + this.gameContext.heroPool, 400, 10
        );
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
        this.sceneNode.addChild(this.scenePhaseLayer);
        this.sceneNode.addChild(this.sceneGlobalLayer);
        this.gameContext.cmdBus.dispatch(new Command<>(JDCommandType.PING));
        this.game.getEventBus().emit(new Event<>(JDEventType.MAIN_SCENE_ENTERED));
    }

    @Override
    public void onExit() {
        this.sceneLayer.removeChildren();
        this.game.getEventBus().emit(new Event<>(JDEventType.MAIN_SCENE_EXITED));
    }


}
