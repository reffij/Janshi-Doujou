package main.services;

import engine.commands.CommandBus;
import engine.core.GameService;
import main.JDCommandType;
import main.JDGame;
import main.JDGameContext;

public class SceneService extends GameService<JDCommandType, JDGameContext>{

    JDGame game;

    public SceneService(JDGame game, CommandBus<JDCommandType, JDGameContext> cmdBus) {
        this.game = game;

        cmdBus.register(JDCommandType.OPEN_GAME, this);
        cmdBus.register(JDCommandType.OPEN_MENU, this);

        this.addResponsibility(
            JDCommandType.OPEN_GAME, 
            (cmd, ctx) -> {
                game.openGame();
                return true;
            }
        );

        this.addResponsibility(
            JDCommandType.OPEN_MENU,
            (cmd, ctx) -> {
                game.openMenu();
                return true;
            }
        );
    }
}
