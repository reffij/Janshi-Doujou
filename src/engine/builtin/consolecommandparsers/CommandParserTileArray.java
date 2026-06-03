package engine.builtin.consolecommandparsers;

import engine.commands.Command;
import engine.commands.CommandParser;
import engine.commands.CommandType;
import main.gameobjects.tiles.Tile;

public class CommandParserTileArray<T extends Enum<T> & CommandType<T>> implements CommandParser<T, Tile[]> {

    @Override
    public Command<T, Tile[]> parse(T cmdType, String[] args) {
        Tile[] payload = new Tile[args.length];
        int i = 0;
        for (String arg : args) {
            Tile tile = Tile.fromString(arg);
            payload[i] = tile;
            i++;
        }
        return new Command<>(cmdType, payload);
    }

}
