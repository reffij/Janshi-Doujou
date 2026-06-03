package engine.builtin.components;

import java.util.function.Supplier;

import engine.builtin.fonts.Fonts;
import engine.commands.CommandType;
import engine.commands.Console;
import engine.datastructures.FString;
import engine.graphics.ColorUtils;
import engine.ui.elements.ConsoleUI;
import engine.ui.elements.UIElement;
import engine.ui.node.UINode;
import engine.ui.renderable.TextRable;

public class Components {

    public static <T extends Enum<T> & CommandType<T>> UINode createConsole(Console<T> console) {
        return new UINode(new ConsoleUI<>(
            500, 
            Fonts.JMICKLE,
            60, 
            ColorUtils.argb(150, 0, 0, 255), 
            ColorUtils.rgb(255, 255, 255), 
            console
        ));
    }

    public static UINode createFPSCounter(Supplier<Integer> s) {
        return new UINode(
            new UIElement(
                new TextRable(
                    new FString("FPS: {}", s)
                ).setDimensions(30, 10)
                .setColor(ColorUtils.rgb(0, 255, 0))
            )
        );
    }

    public static UINode createTPSCounter(Supplier<Double> s) {
        return new UINode(
            new UIElement(
                new TextRable(
                    new FString("TPS: {}", s)
                ).setDimensions(30, 10)
                .setColor(ColorUtils.rgb(0, 255, 0))
            )
        );
    }
}
