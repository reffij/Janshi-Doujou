package engine.events;

public interface Listener<C> {
    void handleEvent(Event<?> event, C ctx);
}
