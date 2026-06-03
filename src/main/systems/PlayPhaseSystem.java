package main.systems;

import engine.commands.Command;
import engine.core.GameSystem;
import engine.events.Event;
import engine.events.EventBus;
import main.JDCommandType;
import main.JDEventType;
import main.JDGameContext;

public class PlayPhaseSystem extends GameSystem<JDGameContext> {
    
    public PlayPhaseSystem(EventBus<JDGameContext> eventBus) {

        eventBus.subscribeAsync(JDEventType.SCORED, this);
        eventBus.subscribeAsync(JDEventType.ROUND_WON, this);

        this.addResponsibility(
            JDEventType.SCORED,
            (event, ctx) -> {
                if (ctx.currentScore.compareTo(ctx.targetScore) <= 0) {
                    ctx.eventBus.emit(new Event<>(JDEventType.ROUND_WON));
                }
            }
        );

        this.addResponsibility(JDEventType.ROUND_WON, 
            (event, ctx) -> {
                ctx.cmdBus.dispatch(new Command<>(JDCommandType.DUMP_TILES));
                ctx.cmdBus.dispatch(new Command<>(JDCommandType.DUMP_TRADING_CARDS));
            }
        );
    }
}
