package main.services;

import java.util.function.Consumer;

import main.JDCommandType;
import main.JDEventType;
import main.JDGameContext;
import main.gameobjects.tiles.Tile;
import engine.commands.*;
import engine.core.GameService;
import engine.events.*;

public class TileService extends GameService<JDCommandType, JDGameContext> {

    public TileService(CommandBus<JDCommandType, JDGameContext> cmdBus) {
        cmdBus.register(JDCommandType.INITIAL_DRAW, this);
        cmdBus.register(JDCommandType.TOGGLE_AUTO_SORT, this);
        cmdBus.register(JDCommandType.DISCARD_TILE_INDEX, this);
        cmdBus.register(JDCommandType.DRAW_TILE, this);
        cmdBus.register(JDCommandType.SET_HAND, this);
        cmdBus.register(JDCommandType.DUMP_TILES, this);
        cmdBus.register(JDCommandType.DRAW_NEW_TILE, this);
        cmdBus.register(JDCommandType.ADD_NEW_TILE, this);

        this.addResponsibility(
            JDCommandType.INITIAL_DRAW, 
            (cmd, ctx) -> {
                if (ctx.tilePool.size() <= 18) {
                    ctx.cmdBus.dispatch(new Command<>(JDCommandType.LOSE));
                    return false;
                }

                while (!(ctx.hand.size() == 13)) {
                    Tile tile = ctx.tilePool.pop();
                    ctx.hand.push(tile);
                    ctx.handCount.addTile(tile);
                }
                for (int i = 0; i < 5; i++) {
                    ctx.doraWall.push(ctx.tilePool.pop());
                }
                ctx.doraWall.setRevealed(ctx.drawSkill);
                for (int i = 0; i < ctx.drawSkill; i++) {
                    if (ctx.tilePool.size() != 0) {
                        ctx.drawPool.push(ctx.tilePool.pop());
                    }
                }
                ctx.drawPool.reveal();
                ctx.tsumoTile = null;

                Tile[] data = ctx.hand.getTiles().clone();
                ctx.eventBus.emit(new Event<>(JDEventType.INITIAL_HAND_DRAWN, data));
                if (ctx.autoSort) ctx.hand.sort();
                ctx.cmdBus.dispatch(new Command<>(JDCommandType.DRAW_TILE));
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.TOGGLE_AUTO_SORT, 
            (cmd, ctx) -> {
                ctx.autoSort = !ctx.autoSort;
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.DISCARD_TILE_INDEX, 
            (cmd, ctx) -> {
                int index = cmd.getData(Integer.class);
                Tile tile = ctx.hand.remove(index);
                ctx.discardPool.push(tile);
                ctx.handCount.removeTile(tile);
                ctx.tsumoTile = null;

                Consumer<Tile> effect = ctx.onDiscardQueue.poll();
                if (effect != null) {
                    effect.accept(tile);
                    ctx.eventBus.emit(new Event<Consumer<Tile>>(JDEventType.DISCARD_QUEUE_EFFECT_APPLIED, effect));
                }

                if (ctx.autoSort) ctx.hand.sort();
                Tile[] data = ctx.hand.getTiles();
                
                ctx.eventBus.emit(new Event<Tile>(JDEventType.TILE_DISCARDED, tile));
                ctx.eventBus.emit(new Event<Tile[]>(JDEventType.HAND_CHANGED, data));
                ctx.cmdBus.dispatch(new Command<>(JDCommandType.DRAW_TILE));
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.DRAW_TILE, 
            (cmd, ctx) -> {
                if (ctx.hand.size() == 14 )throw new IndexOutOfBoundsException();
                if (ctx.drawPool.isEmpty()) {
                    ctx.cmdBus.dispatch(new Command<>(JDCommandType.LOSE));
                    return false;
                }
                Tile drawnTile = ctx.drawPool.pop();
                ctx.hand.push(drawnTile);
                ctx.tsumoTile = drawnTile;
                ctx.handCount.addTile(drawnTile);
                ctx.eventBus.emit(new Event<Tile>(JDEventType.TILE_DRAWN, drawnTile));
                ctx.eventBus.emit(new Event<Tile[]>(JDEventType.HAND_CHANGED, ctx.hand.getTiles()));
                return true;

            }
        );

        this.addResponsibility(
            JDCommandType.SET_HAND, 
            (cmd, ctx) -> {
                Tile[] newHand = cmd.getData(Tile[].class);
                ctx.tilePool.consume(ctx.hand);
                ctx.handCount.clear();
                for (Tile tile : newHand) {
                    ctx.hand.push(tile);
                    ctx.handCount.addTile(tile);
                }
                ctx.tsumoTile = newHand[13];
                ctx.eventBus.emit(new Event<Tile[]>(JDEventType.HAND_CHANGED, ctx.hand.getTiles()));
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.DUMP_TILES, 
            (cmd, ctx) -> {
                ctx.tilePool.consume(ctx.drawPool);
                ctx.tilePool.consume(ctx.discardPool);
                ctx.tilePool.consume(ctx.callPool);
                ctx.tilePool.consume(ctx.doraWall);
                ctx.tilePool.consume(ctx.hand);
                ctx.tsumoTile = null;
                ctx.handCount.clear();
                ctx.eventBus.emit(new Event<>(JDEventType.HAND_DUMPED));
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.DRAW_NEW_TILE, 
            (cmd, ctx) -> {
                if (ctx.hand.size() == 14) throw new IndexOutOfBoundsException();
                Tile tile = cmd.getData(Tile.class);
                ctx.hand.push(tile);
                ctx.tsumoTile = tile;
                ctx.handCount.addTile(tile);
                ctx.eventBus.emit(new Event<Tile>(JDEventType.TILE_DRAWN, tile));
                ctx.eventBus.emit(new Event<Tile[]>(JDEventType.HAND_CHANGED, ctx.hand.getTiles()));
                return true;
            }
        );
        this.addResponsibility(
            JDCommandType.ADD_NEW_TILE, 
            (cmd, ctx) -> {
                Tile tile = cmd.getData(Tile.class);
                ctx.tilePool.push(tile);
                ctx.eventBus.emit(new Event<Tile>(JDEventType.TILE_DRAWN, tile));
                ctx.eventBus.emit(new Event<Tile[]>(JDEventType.HAND_CHANGED, ctx.hand.getTiles()));
                return true;
            }
        );

    }
}
