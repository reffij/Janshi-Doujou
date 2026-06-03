package main.gameobjects.heroes;

import engine.events.*;
import main.JDGameContext;
import main.data.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class Hero implements Listener<JDGameContext> {
    private String name;
    private Map<EventType, BiConsumer<JDGameContext, Event<?>>> behaviorMap;
    private Consumer<JDGameContext> onBuy, onSell = (ctx) -> {};

    private int cost;
    private int rarity;

    public ScaledInt value;

    public Hero(String name, int cost, int rarity, ScaledInt value) {
        if (rarity > 3 || rarity < 0) throw new IllegalStateException(
            "rarity must be between 0 and 3 inclusive"
        );
        this.name = name;
        this.behaviorMap = new HashMap<>();
        this.cost = cost;
        this.rarity = rarity;
        this.value = value;
    }

    public Hero(String name, int cost, int rarity) {
        this(name, cost, rarity, ScaledInt.fromInt(0));
    }

    public void setOnBuy(Consumer<JDGameContext> consumer) {
        this.onBuy = consumer;
    }

    public void setOnSell(Consumer<JDGameContext> consumer) {
        this.onSell = consumer;
    }

    public void putBehavior(EventType eventType, BiConsumer<JDGameContext, Event<?>> behavior) {
        this.behaviorMap.put(eventType, behavior);
    }

    public void handleEvent(Event<?> event, JDGameContext ctx) {
        BiConsumer<JDGameContext, Event<?>> behavior = this.behaviorMap.get(event.getType());
        if (behavior != null) {
            behavior.accept(ctx, event);
        }
    }

    public void handleOnBuy(JDGameContext ctx) {
        this.onBuy.accept(ctx);
    }

    public void handleOnSell(JDGameContext ctx) {
        this.onSell.accept(ctx);
    }

    public Set<EventType> eventTypeSet() {
        return this.behaviorMap.keySet();
    }

    public int getCost() {
        return this.cost;
    }

    public int getRarity() {
        return this.rarity;
    }

    public String toString() {
        return this.name;
    }
}
