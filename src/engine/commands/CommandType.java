package engine.commands;

public interface CommandType<T extends Enum<T> & CommandType<T>> {
    public CommandParser<T, ?> getParser();
}