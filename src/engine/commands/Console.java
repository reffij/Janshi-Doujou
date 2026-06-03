package engine.commands;

import java.util.Arrays;
import java.util.List;

public class Console<T extends Enum<T> & CommandType<T>> {

    CommandBus<T, ?> cmdBus;

    private final Class<T> commandEnumClass;
    private ConsoleCommandParser<T> ccp;

    public Console(CommandBus<T, ?> cmdBus, Class<T> commandEnumClass) {
        this.cmdBus = cmdBus;
        this.commandEnumClass = commandEnumClass;
        this.ccp = new ConsoleCommandParser<>(commandEnumClass);
    }

    private String help() {
        String res = "";

        List<String> names = Arrays.stream(
            this.commandEnumClass.getEnumConstants()
        ).map(Enum::name).toList();

        for (String name : names) {
            res += name + " ";
        }
        return res;
    }

    public String command(String s) {

        if (s.toLowerCase().equals("help")) return help();
        try {

            Command<T, ?> cmd = this.ccp.parse(s);

            if (cmdBus.dispatch(cmd)) {
                return "Success.";
            } else {
                return "Failed.";
            }

        } catch (Exception e) {
            return "(Error)" + e.getMessage();
        }
    }
}
