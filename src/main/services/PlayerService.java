package main.services;

import engine.commands.*;
import engine.core.GameService;
import engine.events.Event;
import main.JDCommandType;
import main.JDEventType;
import main.JDGameContext;

public class PlayerService extends GameService<JDCommandType, JDGameContext> {
    int moneyReserved = 0;

    public PlayerService(CommandBus<JDCommandType, JDGameContext> cmdBus) {
        cmdBus.register(JDCommandType.INCREASE_MONEY, this);
        cmdBus.register(JDCommandType.SPEND_MONEY, this);
        cmdBus.register(JDCommandType.INCREASE_MP, this);
        cmdBus.register(JDCommandType.SPEND_MP, this);
        cmdBus.register(JDCommandType.UPDATE_MP, this);
        cmdBus.register(JDCommandType.INCREASE_MAX_MP, this);
        cmdBus.register(JDCommandType.DECREASE_MAX_MP, this);

        this.addResponsibility(
            JDCommandType.INCREASE_MONEY, 
            (cmd, ctx) -> {
                int dMoney = cmd.getData(Integer.class);
                ctx.money += dMoney;
                ctx.eventBus.emit(new Event<>(JDEventType.MONEY_INCREASED, dMoney));
                return true;
            }
        );

        this.addResponsibility(
            JDCommandType.SPEND_MONEY, 
            (cmd, ctx) -> {
                int dMoney = cmd.getData(Integer.class);
                if (ctx.money >= dMoney) {
                    ctx.money -= dMoney;
                    ctx.eventBus.emit(new Event<>(JDEventType.MONEY_SPENT, dMoney));
                    return true;
                } else {
                    ctx.eventBus.emit(new Event<>(JDEventType.MONEY_SPEND_FAILED));
                    return false;
                }
            }
        );

        this.addResponsibility(
            JDCommandType.INCREASE_MP, 
            (cmd, ctx) -> {
                int dMP = cmd.getData(Integer.class);
                ctx.mp += dMP;
                ctx.eventBus.emit(new Event<>(JDEventType.MP_INCREASED, dMP));
                return true;
            }
        );

        this.addResponsibility(
            JDCommandType.SPEND_MP, 
            (cmd, ctx) -> {
                int dMP = cmd.getData(Integer.class);
                if (ctx.mp >= dMP) {
                    ctx.mp -= dMP;
                    ctx.eventBus.emit(new Event<>(JDEventType.MP_SPENT, dMP));
                    return true;
                } else {
                    ctx.eventBus.emit(new Event<>(JDEventType.MP_SPENT_FAILED));
                    return false;
                }
            }
        );

        this.addResponsibility(
            JDCommandType.UPDATE_MP, 
            (cmd, ctx) -> {
                int oldMp = ctx.mp;
                ctx.mp = Math.max(ctx.maxMp, ctx.mp);
                int dMP = ctx.mp - oldMp;
                ctx.eventBus.emit(new Event<>(JDEventType.MP_UPDATED, dMP));
                return true;
            }
        );

        this.addResponsibility(
            JDCommandType.INCREASE_MAX_MP, 
            (cmd, ctx) -> {
                int dMaxMP = cmd.getData(Integer.class);
                ctx.maxMp += dMaxMP;
                ctx.eventBus.emit(new Event<>(JDEventType.MAX_MP_INCREASED, dMaxMP));
                return true;
            }
        );

        this.addResponsibility(
            JDCommandType.DECREASE_MAX_MP, 
            (cmd, ctx) -> {
                int dMaxMP = cmd.getData(Integer.class);
                int oldMaxMP = ctx.maxMp;
                ctx.maxMp = Math.max(ctx.maxMp, 0);
                dMaxMP = ctx.maxMp - oldMaxMP;
                ctx.eventBus.emit(new Event<>(JDEventType.MAX_MP_DECREASED, dMaxMP));
                return true;
            }
        );
    }
}
