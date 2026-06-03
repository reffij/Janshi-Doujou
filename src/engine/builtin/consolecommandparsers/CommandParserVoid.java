package engine.builtin.consolecommandparsers;

import engine.commands.Command;
import engine.commands.CommandParser;
import engine.commands.CommandType;

public class CommandParserVoid<T extends Enum<T> & CommandType<T>> implements CommandParser<T, Void>{

    @Override
    public Command<T, Void> parse(T cmdType, String[] args) {
        return new Command<>(cmdType);
    }

}
