package main.services;

import engine.commands.*;
import engine.core.GameService;
import engine.events.*;
import main.JDCommandType;
import main.JDEventType;
import main.JDGameContext;
import main.gameobjects.tradingcards.TradingCard;

public class TradingCardService extends GameService<JDCommandType, JDGameContext> {

    public TradingCardService(CommandBus<JDCommandType, JDGameContext> cmdBus) {
        cmdBus.register(JDCommandType.SHUFFLE_DECK, this);
        cmdBus.register(JDCommandType.ADD_TRADING_CARD, this);
        cmdBus.register(JDCommandType.DRAW_TRADING_CARDS, this);
        cmdBus.register(JDCommandType.USE_TRADING_CARD_INDEX, this);
        cmdBus.register(JDCommandType.DISCARD_TC_INDEX, this);
        cmdBus.register(JDCommandType.DUMP_TRADING_CARDS, this);

        this.addResponsibility(
            JDCommandType.SHUFFLE_DECK, 
            (cmd, ctx) -> {
                while (!ctx.tcDiscardPool.isEmpty()) {
                    ctx.tcDrawPool.push(ctx.tcDiscardPool.pop());
                }
                ctx.eventBus.emit(new Event<>(JDEventType.DECK_SHUFFLED));
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.ADD_TRADING_CARD, 
            (cmd, ctx) -> {
                TradingCard tc = cmd.getData(TradingCard.class);
                ctx.tcDrawPool.push(tc);
                ctx.eventBus.emit(new Event<TradingCard>(JDEventType.TRADING_CARD_ADDED, tc));
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.DRAW_TRADING_CARDS, 
            (cmd, ctx) -> {
            if (ctx.tcHandPool.isFull()) return false;
            int price = ctx.tcDrawPrice;
            if (!ctx.cmdBus.dispatch(new Command<JDCommandType, Integer>(JDCommandType.SPEND_MP, price))) {
                return false;
            }
            int n = ctx.tcDrawSkill;
            while (!ctx.tcDrawPool.isEmpty() && n > 0) {
                ctx.tcHandPool.push(ctx.tcDrawPool.pop());
                n--;
            }
            if (n > 0) {
                ctx.cmdBus.dispatch(new Command<>(JDCommandType.SHUFFLE_DECK));
                while (!ctx.tcDrawPool.isEmpty() && n > 0) {
                    ctx.tcHandPool.push(ctx.tcDrawPool.pop());
                    n--;
                }
            }
            return true;
            }
        );
        this.addResponsibility(
            JDCommandType.USE_TRADING_CARD_INDEX, 
            (cmd, ctx) -> {
                int index = cmd.getData(Integer.class);
                TradingCard tc = ctx.tcHandPool.get(index);
                if (tc.precondition(ctx)) {
                    tc.use(ctx);
                    ctx.cmdBus.dispatch(new Command<>(JDCommandType.DISCARD_TC_INDEX, index));
                    return true;
                }
                return false;
            }
        );

        this.addResponsibility(
            JDCommandType.DISCARD_TC_INDEX,
            (cmd, ctx) -> {
                int index = cmd.getData(Integer.class);
                TradingCard tc = ctx.tcHandPool.remove(index);
                ctx.tcDiscardPool.push(tc);
                return true;
            }
        );

        this.addResponsibility(
            JDCommandType.DUMP_TRADING_CARDS,
            (cmd, ctx) -> {
                while(!ctx.tcHandPool.isEmpty()) {
                    ctx.tcDrawPool.push(ctx.tcHandPool.pop());
                }
                while(!ctx.tcDiscardPool.isEmpty()) {
                    ctx.tcDrawPool.push(ctx.tcHandPool.pop());
                }
                return true;
            }
        );
    }
}
