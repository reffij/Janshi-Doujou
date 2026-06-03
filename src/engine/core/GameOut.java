package engine.core;

import engine.ui.elements.ConsoleUI;

public class GameOut {
    ConsoleUI<?> consoleUI;

    GameOut(){}

    public void print(String s) {
        System.out.println(s);
        this.consoleUI.print(s);
    }

    public void err(String s) {
        System.err.println(s);
        this.consoleUI.print(s);
    }
}
