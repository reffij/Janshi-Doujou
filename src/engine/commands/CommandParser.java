package engine.commands;

public interface CommandParser<T extends Enum<T> & CommandType<T>, V> {
    Command<T, V> parse(T cmdType, String[] args);
}
