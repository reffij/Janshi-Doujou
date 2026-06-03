package engine.commands;

public interface Handler<T extends Enum<T> & CommandType<T>, C> {
    boolean handleCMD(Command<T, ?> cmd, C ctx);
}
