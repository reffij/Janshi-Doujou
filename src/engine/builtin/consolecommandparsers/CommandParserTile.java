package engine.builtin.consolecommandparsers;

import engine.commands.Command;
import engine.commands.CommandParser;
import engine.commands.CommandType;
import main.gameobjects.tiles.Tile;

public class CommandParserTile<T extends Enum<T> & CommandType<T>> implements CommandParser<T, Tile> {

    @Override
    public Command<T, Tile> parse(T cmdType, String[] args) {
        Tile tile = Tile.fromString(args[0]);
        return new Command<>(cmdType, tile);
    }
    
}
