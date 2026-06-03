package engine.core;

import java.util.Map;
import java.util.HashMap;
import java.util.function.BiConsumer;

import engine.events.Listener;
import engine.events.Event;
import engine.events.EventBus;
import engine.events.EventType;

public abstract class GameSystem<C> implements Listener<C> {

    private Map<EventType, BiConsumer<Event<?>, C>> responsibilities = new HashMap<>();

    public GameSystem<C> addResponsibility(
        EventType eventType, 
        BiConsumer<Event<?>, C> consumer
    ) {
        this.responsibilities.put(eventType, consumer);
        return this;
    }

    public void destroy(EventBus<C> eventBus) {
        eventBus.unsubscribeAll(this);
    }

    @Override
    public void handleEvent(Event<?> event, C ctx) {
        responsibilities.get(event.getType()).accept(event, ctx);
    }
}
