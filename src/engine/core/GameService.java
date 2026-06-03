package engine.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import engine.commands.Command;
import engine.commands.CommandBus;
import engine.commands.CommandType;
import engine.commands.Handler;

public abstract class GameService<T extends Enum<T> & CommandType<T>, C> implements Handler<T, C> {

    private Map<T, BiPredicate<Command<T, ?>, C>> responsibilities = new HashMap<>();

    public GameService<T, C> addResponsibility(T cmdType, BiPredicate<Command<T, ?>, C> predicate) {
        this.responsibilities.put(cmdType, predicate);
        return this;
    }

    public void destroy(CommandBus<T, C> cmdBus) {
        cmdBus.unregister(this);
    }

    @Override
    public boolean handleCMD(Command<T, ?> cmd, C ctx) {
        return responsibilities.get(cmd.getType()).test(cmd, ctx);
    }
}
