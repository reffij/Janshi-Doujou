package main.systems;

import engine.core.GameSystem;
import engine.events.EventBus;
import engine.ui.node.UINode;
import main.JDEventType;
import main.JDGameContext;

public class UIHeroSystem extends GameSystem<JDGameContext> {

    private UINode heroContainer;

    public UIHeroSystem(
        EventBus<JDGameContext> eventBus,
        UINode heroContainer
    ) {
        this.heroContainer = heroContainer;

        eventBus.subscribeAsync(JDEventType.HERO_ADDED, this);
        eventBus.subscribeAsync(JDEventType.HERO_REMOVED_AT_INDEX, this);
    }
}
