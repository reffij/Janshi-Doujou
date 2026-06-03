package main.services;

import engine.commands.CommandBus;
import engine.core.Game;
import engine.core.GameService;
import engine.events.Event;
import main.JDCommandType;
import main.JDEventType;
import main.JDGameContext;

public class FundementalServices extends GameService<JDCommandType, JDGameContext>  {

    Game<?, ?> game;
    
    public FundementalServices(CommandBus<JDCommandType, JDGameContext> cmdBus, Game<?, ?> game) {
        
        this.game = game;

        cmdBus.register(JDCommandType.QUIT, this);
        this.addResponsibility(
            JDCommandType.QUIT, 
            (cmd, ctx) -> {
                this.game.quit();
                return true;
            }
        
        );

        cmdBus.register(JDCommandType.PING, this);
        this.addResponsibility(
            JDCommandType.PING, 
            (cmd, ctx) -> {
                ctx.eventBus.emit(new Event<>(JDEventType.PONG));
                return true;
            }
        );

    }
}
