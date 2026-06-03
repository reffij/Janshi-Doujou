package main.gameobjects.tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import engine.commands.Command;
import engine.commands.CommandType;
import main.JDCommandType;
import main.JDGameContext;
import main.data.TileColor;
import main.data.TileModification;
import main.data.TilePrimitive;
import main.data.TileSuit;
import main.data.WinContext;

public class Tile extends TilePrimitive {

    private Consumer<JDGameContext> onDiscard;
    private Consumer<JDGameContext> onDraw;
    private Consumer<WinContext> onScore;

    public void onDiscard(JDGameContext ctx) {
        this.onDiscard.accept(ctx);
    }

    public void onDraw(JDGameContext ctx) {
        this.onDraw.accept(ctx);
    }

    public void onScore(WinContext wCTX) {
        this.onScore.accept(wCTX);
    }

    private TileColor color;
    private TileModification modification;

    public Tile(int rank, TileSuit suit) {
        super(rank, suit);
        this.color = TileColor.NONE;
        this.modification = TileModification.NONE;
    }

    public Tile(int integer) {
        super(integer);
        this.color = TileColor.NONE;
        this.modification = TileModification.NONE;
    }

    public Tile(int integer, TileColor color) {
        super(integer);
        this.color = color;
        this.modification = TileModification.NONE;
    }

    public Tile(int rank, TileSuit suit, TileColor color) {
        super(rank, suit);
        this.color = color;
        this.modification = TileModification.NONE;
    }

    public Tile(int rank, TileSuit suit, TileColor color, TileModification modification) {
        this(rank, suit, color);
        this.setModification(modification);
    }

    public void setTile(Tile other) {
        this.integer = other.getInt();
        this.setColor(other.getColor());
        this.setModification(other.modification);
    }

    public void clearColor() {
        this.color = TileColor.NONE;
        this.onDiscard = null;
        this.onDraw = null;
        this.onScore = null;
    }

    public void setColor(TileColor color) {
        this.clearColor();
        this.color = color;
        switch (this.color) {
            case TileColor.RED:
                this.onScore = (wCTX -> wCTX.red++);
                break;
            case TileColor.BLUE:
                this.onDiscard = (ctx -> ctx.cmdBus.dispatch(new Command<>(JDCommandType.INCREASE_MP, 3)));
            case TileColor.GREEN:
                break;
            case TileColor.YELLOW:
                this.onDiscard = (ctx -> ctx.cmdBus.dispatch(new Command<>(JDCommandType.ADD_NEW_RANDOM_ANIMAL_CARD)));
                break;
            case TileColor.WHITE:
                this.onDraw = (ctx -> ctx.cmdBus.dispatch(new Command<>(JDCommandType.INCREASE_MONEY, 5)));
                break;
            case TileColor.NONE:
                break;
        }
    }

    public TileColor getColor() {
        return this.color;
    }

    public void setModification(TileModification modification) {
        this.modification = modification;
    }

    public TileModification getModification() {
        return this.modification;
    }

    public String toString() {
        String colorString = "";
        switch(this.color) {
            case TileColor.NONE -> colorString = "";
            case TileColor.RED -> colorString = "Rd";
            case TileColor.BLUE -> colorString = "B";
            case TileColor.GREEN -> colorString = "Gr";
            case TileColor.YELLOW -> colorString = "Y";
            case TileColor.WHITE -> colorString = "Wt";
        }
        String modificationString = "";
        switch (this.modification) {
            case NONE:
                break;
            case WILD:
                modificationString = "Wl";
                break;
            default:
                break;
            
        }

        return colorString + super.toString() + modificationString;
    }

    public boolean shallowEquals(Tile other) {
        return (super.compareTo(other) == 0);
    }

    public boolean equals(Tile other) {
        return (this.getInt() == other.getInt() && this.color == other.color && this.modification == other.modification);
    }

    public int hashCode() {
        return Objects.hash(this.integer, this.color.ordinal(), this.modification.ordinal());
    }

    public boolean isSuit(TileSuit suit) {
        return (suit == this.getSuit() || this.modification == TileModification.WILD);
    }




    public static Tile fromString(String s) {
        TileColor color = TileColor.NONE;
        TileModification modification = TileModification.NONE;

        int index = 0;

        // --- Parse Color Prefix ---
        if (s.startsWith("Rd")) {
            color = TileColor.RED;
            index += 2;
        } else if (s.startsWith("Gr")) {
            color = TileColor.GREEN;
            index += 2;
        } else if (s.startsWith("Wt")) {
            color = TileColor.WHITE;
            index += 2;
        } else if (s.startsWith("B")) {
            color = TileColor.BLUE;
            index += 1;
        } else if (s.startsWith("Y")) {
            color = TileColor.YELLOW;
            index += 1;
        }

        // --- Parse Modifications (loop from end) ---
        boolean parsingMods = true;
        while (parsingMods && s.length() > index) {
            parsingMods = false;

            if (s.endsWith("Wl")) {
                modification = TileModification.WILD;
                s = s.substring(0, s.length() - 2);
                parsingMods = true;
            }
        }

        // Remaining string is base
        String base = s.substring(index);

        TileSuit suit;
        int rank;

        // --- Honors ---
        switch (base) {
            case "E":
                suit = TileSuit.KAZE; 
                rank = 1; break;
            case "S":
                suit = TileSuit.KAZE; 
                rank = 2; break;
            case "W":
                suit = TileSuit.KAZE; 
                rank = 3; break;
            case "N":
                suit = TileSuit.KAZE; 
                rank = 4; break;
            case "Wh":
                suit = TileSuit.SANGEN; 
                rank = 1; break;
            case "G":
                suit = TileSuit.SANGEN; 
                rank = 2; break;
            case "R":
                suit = TileSuit.SANGEN; 
                rank = 3; break;
            default:
                base = base.toUpperCase();
                char last = base.charAt(base.length() - 1);
                rank = Integer.parseInt(base.substring(0, base.length() - 1));


                switch (last) {
                    case 'M': suit = TileSuit.MAN; break;
                    case 'P': suit = TileSuit.PIN; break;
                    case 'S': suit = TileSuit.SOU; break;
                    default:
                        throw new IllegalArgumentException("Invalid tile: " + s);
                }
    }

    return new Tile(rank, suit, color, modification);
    }
}
