package engine.builtin.consolecommandparsers;

import engine.commands.Command;
import engine.commands.CommandParser;
import engine.commands.CommandType;
import main.gameobjects.market.Item;

public class CommandParserItemArray<T extends Enum<T> & CommandType<T>> implements CommandParser<T, Item[]>{

    @Override
    public Command<T, Item[]> parse(T cmdType, String[] args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "Unimplemented method 'parse'"
        );
    }
    
}
