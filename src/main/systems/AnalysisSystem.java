package main.systems;

import main.JDEventType;
import main.JDGameContext;
import main.analysis.*;
import main.data.*;
import main.gameobjects.tiles.Tile;
import engine.events.*;
import engine.core.GameSystem;

import java.util.*;

public class AnalysisSystem extends GameSystem<JDGameContext> {

    public AnalysisSystem(EventBus<JDGameContext> eventBus) {
        eventBus.subscribeAsync(JDEventType.TILE_DRAWN, this);
        this.addResponsibility(
            JDEventType.TILE_DRAWN, 
            (event, ctx) -> {
                Map<TilePrimitive, List<TilePrimitive>> discardToWaits = Analyzer.discardToTenpai(ctx.handCount);

                if (!discardToWaits.isEmpty()) {
                    ctx.eventBus.emit(new Event<>(JDEventType.TENPAI_AVAILABLE, discardToWaits));
                }
                if (Analyzer.isWinningHand(ctx.handCount)) {
                    ctx.eventBus.emit(new Event<Tile>(JDEventType.TSUMO_AVAILABLE, event.getData(Tile.class)));
                }
            }
        );
    }
}
