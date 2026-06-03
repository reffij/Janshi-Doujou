package engine.commands;

import java.util.*;


public class ConsoleCommandParser<T extends Enum<T> & CommandType<T>> {

    private final Class<T> commandEnum;

    public ConsoleCommandParser(Class<T> commandEnum) {
        this.commandEnum = commandEnum;
    }


    Command<T, ?> parse(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Empty command");
        }
        String[] split = s.trim().split("\\s+");
        String name = split[0].toUpperCase();
        T type;
        try {
            type = Enum.valueOf(commandEnum, name);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown command: " + name);
        }
        String[] args = Arrays.copyOfRange(split, 1, split.length);
        CommandParser<T, ?> parser = type.getParser();
        return parser.parse(type, args);
    }
}
