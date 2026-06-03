package main;

import engine.commands.Command;
import engine.commands.CommandBus;
import engine.core.Config;
import engine.datastructures.FString;
import engine.datastructures.HAlign;
import engine.datastructures.VAlign;
import engine.events.EventBus;
import engine.graphics.ColorUtils;
import engine.graphics.Sprite;
import engine.graphics.SpriteLoader;
import engine.ui.elements.UIButton;
import engine.ui.elements.UIElement;
import engine.ui.elements.UIDragElement;
import engine.ui.node.UINode;
import engine.ui.renderable.RectRable;
import engine.ui.renderable.SpriteRable;
import engine.ui.renderable.TextRable;
import main.gameobjects.tiles.Tile;

import java.io.IOException;
import java.util.function.Supplier;

public class JDComponents {

    EventBus<JDGameContext> eventBus;
    CommandBus<JDCommandType, JDGameContext> cmdBus;
    JDResources resources;

    public JDComponents(
        EventBus<JDGameContext> eventBus, 
        CommandBus<JDCommandType, JDGameContext> cmdBus
    ) {
        this.eventBus = eventBus;
        this.cmdBus = cmdBus;
        this.resources = new JDResources();

    }

    public static UINode createIntro() {
        UINode node = new UINode(
            new UIElement(
                new RectRable(200, 200)
                .setColor(ColorUtils.rgb(255, 0, 0))
            )
            .alignHCenter()
            .alignVCenter()
        );
        UINode node2 = new UINode(
            new UIElement(
                new RectRable(100, 100)
                .setColor(ColorUtils.rgb(0, 255, 0))
            )
            .alignHCenter()
            .alignVCenter()
        );
        node.addChild(node2);
        return node;
    }

    public static UINode createMenu(
        CommandBus<JDCommandType, JDGameContext> cmdBus
    ) {
        UINode node = createFullScreen();
        node.getElement().alignHCenter().alignVCenter();
        node.addChild(createMenuButtons(cmdBus));
        node.addChild(createMovableDrinkingBird());
        return node;
    }

    public static UINode createFullScreen() {
        UINode node = new UINode(
            new UIElement(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT)
        );
        return node;
    }

    public static UINode createMainScene() {
        UINode node = createFullScreen();
        node.getElement().layoutVStack().alignHCenter();
        node.addChild(
            new UINode(
                new UIElement(
                    new TextRable("Play Scene")
                    .setDimensions(100, 100)
                    .setBackgroundColor(ColorUtils.rgb(74,149,250))
                    .setHAlign(HAlign.CENTER)
                    .setVAlign(VAlign.BOTTOM)
                )
            )
        ).addChild(
            new UINode(
                new UIElement(
                    new RectRable(100, 100)
                    .setColor(ColorUtils.rgb(255,163,206))
                )
            )
        );
        UIDragElement el = new UIDragElement(100, 100);
        el.setRable(new TextRable(new FString("xPos: {}\nyPos: {}", (Supplier<Integer>) () -> el.getValue().a, (Supplier<Integer>) () -> el.getValue().b))
                        .setDimensions(100, 100)
                        .setBackgroundColor(ColorUtils.rgb(165, 61, 20)));
        node.addChild(new UINode(el));
        return node;
    }

    public static UINode createMenuButtons(
        CommandBus<JDCommandType, JDGameContext> cmdBus
    ) {
        UINode node = new UINode(
            new UIElement(
                new RectRable(300, 500)
                .setColor(ColorUtils.rgba(191, 4, 197, 0.51))
            ).alignHCenter().layoutVStack().setPadding(5)
        );

        node.addChild(createStartGameButton(cmdBus));

        return node;
    }

    public static UINode createStartGameButton(
        CommandBus<JDCommandType, JDGameContext> cmdBus
    ) {
        UINode node = new UINode(
            new UIButton(
                new TextRable("Play")
                .setDimensions(200, 100)
                .setBackgroundColor(ColorUtils.rgb(255, 0, 0)), 
                new TextRable("Play")
                .setDimensions(200, 100)
                .setBackgroundColor(ColorUtils.rgb(0, 255, 0)), 
                new TextRable("Play")
                .setDimensions(200, 100)
                .setBackgroundColor(ColorUtils.rgb(0, 0, 255)), 
                () -> {
                    cmdBus.dispatch(new Command<>(JDCommandType.OPEN_GAME));
                }
            ).setPos(10,10)
        );
        return node;
    }

    public static UINode createHeroUI() {
        return new UINode(
            new UIElement(
                new RectRable(400, 20) 
                    .setColor(ColorUtils.rgba(9, 226, 197, 0.12))
            )
        );
    }

    public static UINode createInteractiveTile(Tile tile) {
        UINode node = new UINode(null);

        return node;
    }

    public static UINode createMainSceneGlobalLayer() {
        return createFullScreen();
    }

    public static UINode createMainScenePhaseLayer() {
        return createFullScreen();
    }

    public static UINode createMovableDrinkingBird() {
        try {
            UINode node = new UINode(
                new UIDragElement(
                    new SpriteRable(
                        SpriteLoader.loadSprite("assets/drinking_bird.bmp")
                    )
                )
            );
            return node;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
