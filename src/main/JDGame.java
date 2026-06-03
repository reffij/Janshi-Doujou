/* 
* Copyright (c) 2026, Matthew Anderson
* All rights reserved.
*/
package main;

import engine.core.Game;
import engine.core.Scene;
import engine.core.GameService;
import main.scenes.MenuScene;
import main.scenes.MainScene;
import main.services.FundementalServices;
import main.services.SceneService;

public class JDGame extends Game<JDCommandType, JDGameContext> {
    private JDGameContext gameContext;
    private GameService<JDCommandType, JDGameContext> sceneService;

    private GameService<JDCommandType, JDGameContext> fundementalService;

    private Scene introScene;
    private Scene menuScene;
    private Scene playScene;

    public JDGame() {
        JDGameContext JDGameContext = new JDGameContext();
        super(JDCommandType.class, JDGameContext);
        JDGameContext.setEventCMDbus(super.getEventBus(), super.getCMDBus());
        this.gameContext = JDGameContext;

        this.menuScene = new MenuScene(this, super.getSceneLayer());
        this.playScene = null;
        this.sceneService = new SceneService(this, this.getCMDBus());
        this.fundementalService = new FundementalServices(this.getCMDBus(), this);
        this.menuScene = new MenuScene(this, super.getSceneLayer());

        super.setScene(this.menuScene);
    }

    public void openGame() {
        if (this.playScene == null) {
            this.playScene = new MainScene(this, System.currentTimeMillis(), super.getSceneLayer());
        }
        super.setScene(this.playScene);
    }

    public void openMenu() {
        super.setScene(this.menuScene);
    }

}
