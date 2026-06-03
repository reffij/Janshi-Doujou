package main.services;

import engine.commands.*;
import engine.core.GameService;
import engine.events.*;
import main.JDCommandType;
import main.JDEventType;
import main.JDGameContext;
import main.gameobjects.heroes.*;

public class HeroService extends GameService<JDCommandType, JDGameContext> {


    public HeroService(CommandBus<JDCommandType, JDGameContext> cmdBus) {

        cmdBus.register(JDCommandType.ADD_HERO, this);
        cmdBus.register(JDCommandType.REMOVE_HERO_INDEX, this);

        this.addResponsibility(
            JDCommandType.ADD_HERO, 
            (cmd, ctx) -> {
                if (ctx.heroPool.isFull()) return false;
                Hero hero = cmd.getData(Hero.class);
                ctx.heroPool.push(hero);
                for (EventType eventType : hero.eventTypeSet()) {
                    ctx.eventBus.subscribeSync(eventType, hero);
                }
                ctx.eventBus.emit(new Event<Hero>(JDEventType.HERO_ADDED, hero));
                return true;
            }
        );

        this.addResponsibility(
            JDCommandType.REMOVE_HERO_INDEX, 
            (cmd, ctx) -> {
                int index = cmd.getData(Integer.class);
                Hero hero = ctx.heroPool.remove(index);
                ctx.eventBus.unsubscribeAll(hero);
                ctx.eventBus.emit(new Event<Integer>(JDEventType.HERO_REMOVED_AT_INDEX, index));
                return true;
            }
        );

    }

}
