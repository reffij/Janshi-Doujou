package engine.builtin.consolecommandparsers;

import engine.commands.Command;
import engine.commands.CommandParser;
import engine.commands.CommandType;

public class CommandParserInt<T extends Enum<T> & CommandType<T>> implements CommandParser<T, Integer>{

    @Override
    public Command<T, Integer> parse(T cmdType, String[] args) {
        String numString = args[0];
        return new Command<T, Integer>(cmdType, Integer.parseInt(numString));
    }
    
}
