package main.services;

import java.util.Random;

import engine.commands.*;
import engine.core.GameService;
import engine.events.Event;
import main.GamePhase;
import main.JDCommandType;
import main.JDEventType;
import main.JDGameContext;
import main.data.TilePrimitive;
import main.data.TileSuit;

public class RoundService extends GameService<JDCommandType, JDGameContext> {
    
    Random rng;

    public RoundService(long seed, CommandBus<JDCommandType, JDGameContext> cmdBus) {
        this.rng = new Random(seed);

        cmdBus.register(JDCommandType.NEXT_ROUND, this);

        /*
        this.addResponsibility(
            JDCommandType.OPEN_GAME, 
            (cmd, ctx) -> {
                GamePhase phase = ctx.phase;
                switch(phase) {
                    case MARKET_PHASE:
                        ctx.eventBus.emit(new Event<>(JDEventType.MARKET_ENTERED));
                        return true;
                    case PLAY_PHASE:
                        ctx.eventBus.emit(new Event<>(JDEventType.PLAY_PHASE_ENTERED));
                        return true;
                    case SELECT_PHASE:
                        ctx.eventBus.emit(new Event<>(JDEventType.ROUNDSELECT_ENTERED));
                        return true;
                    default:
                        return false;
                }
            }
        );*/

        this.addResponsibility(
            JDCommandType.NEXT_ROUND, 
            (cmd, ctx) -> {
                if (ctx.phase == GamePhase.SELECT_PHASE) {
                    ctx.phase = GamePhase.PLAY_PHASE;
                    ctx.eventBus.emit(new Event<>(JDEventType.ROUNDSELECT_EXITED));
                    ctx.eventBus.emit(new Event<>(JDEventType.PLAY_PHASE_ENTERED));
                    ctx.cmdBus.dispatch(new Command<>(JDCommandType.INITIAL_DRAW));
                    ctx.cmdBus.dispatch(new Command<>(JDCommandType.DRAW_TRADING_CARDS_FREE));
                }
                else if (ctx.phase == GamePhase.PLAY_PHASE) {
                    ctx.phase = GamePhase.MARKET_PHASE;
                    ctx.eventBus.emit(new Event<>(JDEventType.PLAY_PHASE_EXITED));
                    ctx.cmdBus.dispatch(new Command<>(JDCommandType.OPEN_MARKET));
                    ctx.eventBus.emit(new Event<>(JDEventType.MARKET_ENTERED));
                }
                else if (ctx.phase == GamePhase.MARKET_PHASE) {
                    ctx.phase = GamePhase.SELECT_PHASE;
                    ctx.cmdBus.dispatch(new Command<>(JDCommandType.CLOSE_MARKET));
                    ctx.eventBus.emit(new Event<>(JDEventType.MARKET_EXITED));
                    ctx.eventBus.emit(new Event<>(JDEventType.ROUNDSELECT_ENTERED));
                
                    ctx.round++;
                    
                    ctx.seatWind.rotate();
                    if ((ctx.round - 1) % 4 == 0) {
                        ctx.round = 1;
                        ctx.rotation++;
                        ctx.prevWind.rotate();
                        ctx.seatWind = new TilePrimitive(this.rng.nextInt(1, 5), TileSuit.KAZE);
                    }
                    
                }

                return true;
            }
        );

    }
}
