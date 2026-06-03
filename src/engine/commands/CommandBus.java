package engine.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.core.Game;

public class CommandBus<T extends Enum<T> & CommandType<T>, C> {

    private boolean DEBUG_MODE = true;

    private double gameTime;

    private Game<T, C> game;
    private C ctx;
    private Map<T, Handler<T, C>> handlers;

    public CommandBus(Game<T, C> game, C ctx) {
        this.game = game;
        this.ctx = ctx;
        this.handlers = new HashMap<>();
    }

    public void register(T type, Handler<T, C> handler) {
        handlers.put(type, handler);
    }

    public void unregister(T type) {
        this.handlers.remove(type);
    }

    public void unregister(Handler<T, C> handler) {
        List<T> toRemove = new ArrayList<>();
        for (T type : handlers.keySet()) {
            if (handlers.get(type) == handler) {
                toRemove.add(type);
            }
        }
        for (T type : toRemove) {
            handlers.remove(type);
        }
    } 

    public boolean dispatch(Command<T, ?> cmd) {
        return this.command(cmd.withTime(this.gameTime));
    }

    private boolean command(Command<T, ?> cmd) {
        Handler<T, C> handler = handlers.get(cmd.getType());
        
        if (DEBUG_MODE) game.print("(t="+cmd.getTimeStamp()+"): " + "[Command]" + cmd);
        
        if (handler == null) {
            game.print("No handler found for " + cmd.toString());
            return false;
        }

        if (DEBUG_MODE) game.print(("  ->" + handler.getClass().getSimpleName()));
        return handler.handleCMD(cmd, ctx);
        
    }

    public void update(double dt) {
        this.gameTime += dt;
    }
}
