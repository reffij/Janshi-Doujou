package main.services;


import engine.commands.*;
import engine.core.GameService;
import engine.events.Event;
import main.JDCommandType;
import main.JDEventType;
import main.JDGameContext;
import main.gameobjects.market.*;

public class MarketService extends GameService<JDCommandType, JDGameContext> {

    public MarketService(CommandBus<JDCommandType, JDGameContext> cmdBus) {
        cmdBus.register(JDCommandType.OPEN_MARKET, this);
        cmdBus.register(JDCommandType.CLOSE_MARKET, this);
        cmdBus.register(JDCommandType.PURCHASE_OFFER_INDEX, this);
        cmdBus.register(JDCommandType.REROLL, this);

        this.addResponsibility(
            JDCommandType.OPEN_MARKET, 
            (cmd, ctx) -> {
                ctx.market.openShop();
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.CLOSE_MARKET, 
            (cmd, ctx) -> {
                ctx.market.closeShop();
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.PURCHASE_OFFER_INDEX, 
            (cmd, ctx) -> {
                int index = cmd.getData(Integer.class);
                Item item = ctx.market.getOffer(index);
                Command<JDCommandType, ?> purchaseCommand = item.createPurchaseCommand();
                int cost = item.getPrice();
                boolean affordable = ctx.money >= cost;
                if (!affordable) {
                    ctx.eventBus.emit(new Event<>(JDEventType.MONEY_SPEND_FAILED));
                    return false;
                }
                else {
                    if (ctx.cmdBus.dispatch(purchaseCommand)) {
                        return ctx.cmdBus.dispatch(new Command<>(JDCommandType.SPEND_MONEY, cost));
                    } else {
                        return false;
                    }
                }
            }
        );

        this.addResponsibility(
            JDCommandType.REROLL, 
            (cmd, ctx) -> {
                int rerollPrice = ctx.market.getRerollPrice();
                if (ctx.cmdBus.dispatch(new Command<>(JDCommandType.SPEND_MONEY, rerollPrice))) {
                    ctx.market.reroll();
                    return true;
                }
                return false;
            }
        );

    }
}