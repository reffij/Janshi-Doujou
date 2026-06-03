package engine.ui.elements;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import engine.commands.CommandType;
import engine.commands.Console;
import engine.datastructures.ForgetfulStack;
import engine.graphics.*;
import engine.graphics.render.Renderer;
import engine.inputs.KeyboardInputs;

public class ConsoleUI<T extends Enum<T> & CommandType<T>> extends UIElement {

    private boolean isVisible;

    private int x, y, width, height;
    private int lines;

    private BMPFont font;

    private int backgroundColor, textColor;

    private StringBuilder currentLine;

    private ForgetfulStack<String> consoleText;

    private ArrayList<String> commandHistory;
    private int commandHistoryIdx = -1;

    private Console<T> console;

    public ConsoleUI(
        int width,
        BMPFont font,
        int lines,
        int backgroundColor,
        int textColor,
        Console<T> console
    ) {
        super(width, font.getFontHeight() * (lines + 1));
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.font = font;
        this.lines = lines;
        this.height = this.font.getFontHeight() * (lines + 1);
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.console = console;
        this.isVisible = false;
        this.currentLine = new StringBuilder();
        this.consoleText = new ForgetfulStack<String>(lines);
        this.commandHistory = new ArrayList<>();
    }

    @Override
    public void render(Renderer r, int globalX, int globalY) {
        if (!isVisible) return;
        r.drawRect(x, y, width, height, backgroundColor);

        int fontHeight = this.font.getFontHeight();
        int yCursor = y + height - fontHeight;
        r.drawString(this.currentLine.toString(), x, yCursor);
        for (String s : consoleText) {
            yCursor -= fontHeight;
            r.drawString(s, x, yCursor);
        }
    }

    public void print(String s) {
        List<String> strings = this.font.splitByWidth(s, this.width);
        for (String s2 : strings) {
            this.consoleText.push(s2);
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_BACK_QUOTE) {
            this.isVisible = !this.isVisible;
        }

        if (!this.isVisible) return;

        if (keyCode == KeyEvent.VK_ENTER) {
            String s = this.currentLine.toString();
            this.currentLine.setLength(0);
            this.print(s);

            this.commandHistory.add(s);
            this.commandHistoryIdx = this.commandHistory.size() - 1;

            String response = this.console.command(s);
            this.print(response);
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (this.currentLine.length() > 0) {
                this.currentLine.deleteCharAt(this.currentLine.length() - 1);
            }
        } else if (keyCode == KeyEvent.VK_CONTROL) {
            this.currentLine.append(KeyboardInputs.pasteFromClipboard());
        } else if (keyCode == KeyEvent.VK_UP) {
            if (this.commandHistoryIdx == -1) return;

            String s = this.commandHistory.get(this.commandHistoryIdx);
            this.commandHistoryIdx--;
            this.currentLine.setLength(0);
            this.currentLine.append(s);

        } else if (keyCode == KeyEvent.VK_DOWN) {
            if (this.commandHistoryIdx == this.commandHistory.size() - 1) return;

            String s = this.commandHistory.get(this.commandHistoryIdx);
            this.commandHistoryIdx++;
            this.currentLine.setLength(0);
            this.currentLine.append(s);
            
        }
    }

    @Override
    public void keyTyped(char c) {
        if (!this.isVisible || c == '`') return;
        if (c >= 26 && c <= 126) {
            currentLine.append(c);
        }
    }
}
