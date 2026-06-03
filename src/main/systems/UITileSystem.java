package main.systems;

import java.util.List;

import engine.core.GameSystem;
import engine.events.*;
import engine.ui.elements.UIElement;
import engine.ui.node.UINode;
import main.JDComponents;
import main.JDEventType;
import main.JDGameContext;
import main.gameobjects.tiles.Tile;

public class UITileSystem extends GameSystem<JDGameContext> {

    UIElement nextRoundButton;
    UIElement handView;

    UINode tileContainer;

    public UITileSystem(
        EventBus<JDGameContext> eventBus,
        UINode tileContainer
    ) {
        this.tileContainer = tileContainer;


        eventBus.subscribeAsync(JDEventType.INITIAL_HAND_DRAWN, this);
        eventBus.subscribeAsync(JDEventType.TILE_DISCARDED, this);
        eventBus.subscribeAsync(JDEventType.TILE_DRAWN, this);
        eventBus.subscribeAsync(JDEventType.HAND_CHANGED, this);


        this.addResponsibility(
            JDEventType.INITIAL_HAND_DRAWN, 
            (event, ctx) -> {
                List<Tile> tiles = List.of(event.getData(Tile[].class));
                for (Tile tile : tiles) {
                    UINode tileNode = JDComponents.createInteractiveTile(tile);
                    this.tileContainer.addChild(tileNode);
                }
            }
        );

        this.addResponsibility(
            JDEventType.TILE_DISCARDED,
            (event, ctx) -> {
                this.tileContainer.removeChild(this.tileContainer.getLastChild());
            }
        );
        
        this.addResponsibility(
            JDEventType.TILE_DRAWN, 
            (event, ctx) -> {
                Tile tile = event.getData(Tile.class);
                this.tileContainer.addChild(JDComponents.createInteractiveTile(tile));
            }
        );

        this.addResponsibility(
            JDEventType.TENPAI_AVAILABLE, 
            (event, ctx) -> {
                //TODO
            }
        );

    }



}
