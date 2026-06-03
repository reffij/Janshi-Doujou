package main.systems;

import engine.core.GameSystem;
import engine.events.Event;
import engine.events.EventBus;
import engine.ui.node.UINode;
import main.JDEventType;
import main.JDGameContext;

public class UIPhaseSystem extends GameSystem<JDGameContext> {

    UINode playScenePhaseLayer;

    public UIPhaseSystem(
        EventBus<JDGameContext> eventBus,
        UINode playScenePhaseLayer
    ) {
        this.playScenePhaseLayer = playScenePhaseLayer;

        eventBus.subscribeAsync(JDEventType.MAIN_SCENE_ENTERED, this);

        eventBus.subscribeAsync(JDEventType.ROUNDSELECT_ENTERED, this);
        eventBus.subscribeAsync(JDEventType.ROUNDSELECT_EXITED, this);
        eventBus.subscribeAsync(JDEventType.PLAY_PHASE_ENTERED, this);
        eventBus.subscribeAsync(JDEventType.PLAY_PHASE_EXITED, this);
        eventBus.subscribeAsync(JDEventType.MARKET_ENTERED, this);
        eventBus.subscribeAsync(JDEventType.MARKET_EXITED, this);

        this.addResponsibility(
            JDEventType.MAIN_SCENE_ENTERED, 
            (event, ctx) -> {
                switch(ctx.phase) {
                    case MARKET_PHASE:
                        ctx.eventBus.emit(new Event<>(JDEventType.MARKET_ENTERED));
                        break;
                    case PLAY_PHASE:
                        ctx.eventBus.emit(new Event<>(JDEventType.PLAY_PHASE_ENTERED));
                        break;
                    case SELECT_PHASE:
                        ctx.eventBus.emit(new Event<>(JDEventType.ROUNDSELECT_ENTERED));
                        break;
                }
            }
        );

        

    }
}
