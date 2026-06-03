package main.gameobjects.tradingcards;

import java.util.function.Consumer;
import java.util.function.Predicate;

import main.JDCommandType;
import main.JDGameContext;
import main.data.*;
import main.gameobjects.tiles.Tile;
import engine.commands.*;

public enum AnimalCard {
    

    OX(
        "Ox",
        (ctx) -> {
            Consumer<Tile> effect = (
                (tile) -> tile.rotate()
            );
            return ctx.cmdBus.dispatch(new Command<>(JDCommandType.ENQUEUE_ON_DISCARD, effect));
        }
    ),

    TIGER(
        "Tiger",
        (ctx) -> {
            return ctx.cmdBus.dispatch(new Command<>(JDCommandType.INCREASE_STRENGTH, 1));
        }
    ),



    DRAGON(
        "Dragon",
        (ctx) -> {
            Consumer<Tile> effect = (
                (tile) -> tile.setModification(TileModification.WILD)
            );
            return ctx.cmdBus.dispatch(new Command<>(JDCommandType.ENQUEUE_ON_DISCARD, effect));
        }
    ),


    MONKEY(
        "Monkey",
        (ctx) -> {
            return ctx.cmdBus.dispatch(new Command<>(JDCommandType.FLIP_DORA));
            }
    ),
    
    DOG(
        "Dog",
        (ctx) -> {
            Consumer<Tile> effect = (
                (tile) -> tile.setTile(ctx.tsumoTile)
            );
            return ctx.cmdBus.dispatch(new Command<>(JDCommandType.ENQUEUE_ON_DISCARD, effect));
        }
    ),

    HORSE(
        "Horse",
        (ctx) -> {
            return true;
        }
    )

    /*
    PIG
    ROOSTER,
    RAT,
    RABBIT,
    SNAKE,
    HORSE,
    GOAT,
     */
    ;

    TradingCard tradingCard;

    AnimalCard(String name, Predicate<JDGameContext> predicate) {
        this.tradingCard = new TradingCard(name, predicate, true, TradingCardType.ANIMAL_CARD, 2, 0);
    }

    public TradingCard getTradingCard() {
        return this.tradingCard;
    }
}
