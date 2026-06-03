package engine.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import engine.core.Game;

public class EventBus<C> {
    boolean DEBUG_MODE = true;
    
    double gameTime;

    Game<?, C> game;
    C ctx;
    Queue<Event<?>> events;
    Map<EventType, List<Listener<C>>> asyncListeners;
    Map<EventType, List<Listener<C>>> syncListeners;

    public EventBus(Game<?, C> game, C ctx) {
        this.game = game;
        this.ctx = ctx;
        this.events = new LinkedList<>();
        this.asyncListeners = new HashMap<>();
        this.syncListeners = new HashMap<>();
        this.gameTime = 0;
    }


    //----------------------
    //SUBSCRIPTIONS
    //----------------------

    public void subscribeSync(EventType type, Listener<C> listener) {
        if (!syncListeners.containsKey(type)) {
            syncListeners.put(type, new ArrayList<>());
        }
        syncListeners.get(type).add(listener);
    }

    public void subscribeAsync(EventType type, Listener<C> listener) {
        if (!asyncListeners.containsKey(type)) {
            asyncListeners.put(type, new ArrayList<>());
        }
        asyncListeners.get(type).add(listener);
    }

    public void unsubscribeSync(EventType type, Listener<C> listener) {
        List<Listener<C>> list = syncListeners.get(type);
        if (list != null) list.remove(listener);
    }

    public void unsubscribeAsync(EventType type, Listener<C> listener) {
        List<Listener<C>> list = asyncListeners.get(type);
        if (list != null) list.remove(listener);
    }

    public void unsubscribeAll(Listener<C> listener) {
        for (EventType type : syncListeners.keySet()) {
            syncListeners.get(type).remove(listener);
        }
        for (EventType type : asyncListeners.keySet()) {
            asyncListeners.get(type).remove(listener);
        }
    }

    //----------------------
    //EMIT
    //----------------------

    public void emit(Event<?> event) {
        event = event.withTime(this.gameTime);
        events.add(event);
        this.dispatchSync(event);
    }

    //----------------------
    //Dispatch
    //----------------------


    private void dispatchSync(Event<?> event) {
        if (DEBUG_MODE) game.print("(t=" + event.getTimeStamp() + "): "+ "[Sync Event]" + event);
        
        List<Listener<C>> list = syncListeners.get(event.getType());
        if (list != null) {
            for (Listener<C> listener : list) {
                if (DEBUG_MODE) game.print("  ->" + listener.getClass().getSimpleName());
                listener.handleEvent(event, this.ctx);
            }
        }
    
    }

    private void dispatchAsync(Event<?> event) {
        if (DEBUG_MODE) game.print("(t=" + event.getTimeStamp() + "): "+ "[Async Event]" + event);

        List<Listener<C>> list = asyncListeners.get(event.getType());
        if (list != null) {
            for (Listener<C> listener : list) {
                if (DEBUG_MODE) game.print("  ->" + listener.getClass().getSimpleName());
                listener.handleEvent(event, this.ctx);
            }
        }
    }

    public void update(double dt) {

        this.gameTime += dt;

        while (!events.isEmpty()) {
            Event<?> event = events.poll();
            dispatchAsync(event);
        }
    }
}
