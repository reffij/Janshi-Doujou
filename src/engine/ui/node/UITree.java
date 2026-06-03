package engine.ui.node;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import engine.core.Config;
import engine.datastructures.Rectangle;
import engine.graphics.render.Renderer;
import engine.ui.elements.UIElement;

public class UITree {

    private UINode root;

    public UITree(UINode root) {
        this.root = root;
    }

    private Context ROOT_CONTEXT = new Context(
        new Rectangle(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT),
        new Rectangle(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT)
    );

    public void setRoot(UINode root) {
        this.root = root;
    }

    public UINode getRoot() {
        return this.root;
    }

    public void dfs(UINode node, Consumer<UINode> consumer) {
        if (node == null) return;

        consumer.accept(node);

        for (UINode child : node.getChildren()) {
            dfs(child, consumer);
        }
    }

    public void update(double dt) {
        this.dfs(this.getRoot(), node -> {node.getElement().update(dt);});
    }

    private void renderDFS(Renderer r, UINode node, Context ctx) {
        if (node == null) return;

        UIElement e = node.getElement();
        UINode parent = node.getParent();

        Context newCtx;
        if (parent == null) newCtx = ROOT_CONTEXT;
        else newCtx = parent.getElement().getLayout().next(e, ctx);

        int globalX = newCtx.borderBounds.x;
        int globalY = newCtx.borderBounds.y;

        e.render(r, globalX, globalY);

        node.getElement().getLayout().reset();

        for (UINode child : node.getChildren()) {
            this.renderDFS(r, child, newCtx);
        }

        node.getElement().getLayout().reset();
    }

    private void mouseMovedDFS(int mouseX, int mouseY, UINode node, Context ctx) {
        if (node == null) return;

        UIElement e = node.getElement();
        UINode parent = node.getParent();

        Context newCtx;
        if (parent == null) newCtx = ROOT_CONTEXT;
        else newCtx = parent.getElement().getLayout().next(e, ctx);

        int globalX = newCtx.borderBounds.x;
        int globalY = newCtx.borderBounds.y;

        e.mouseMoved(globalX, globalY, mouseX, mouseY);

        node.getElement().getLayout().reset();

        for (UINode child : node.getChildren()) {
            this.mouseMovedDFS(mouseX, mouseY, child, newCtx);
        }

        node.getElement().getLayout().reset();
    }

    private void mouseDraggedDFS(int mouseX, int mouseY, UINode node, Context ctx) {
        if (node == null) return;

        UIElement e = node.getElement();
        UINode parent = node.getParent();

        Context newCtx;
        if (parent == null) newCtx = ROOT_CONTEXT;
        else newCtx = parent.getElement().getLayout().next(e, ctx);

        int globalX = newCtx.borderBounds.x;
        int globalY = newCtx.borderBounds.y;

        e.mouseDragged(globalX, globalY, mouseX, mouseY);

        node.getElement().getLayout().reset();

        for (UINode child : node.getChildren()) {
            this.mouseDraggedDFS(mouseX, mouseY, child, newCtx);
        }

        node.getElement().getLayout().reset();
    }

    public void render(Renderer r) {
        if (this.root == null) return;
        this.renderDFS(r, this.root, this.ROOT_CONTEXT);
    }

    public void mouseMoved(int x, int y) {
        if (this.root == null) return;
        this.mouseMovedDFS(x, y, this.root, this.ROOT_CONTEXT);
    }

    public void mousePressed() {
        this.dfs(this.getRoot(), node -> {node.getElement().mousePressed();});
    }
    public void mouseReleased() {
        this.dfs(this.getRoot(), node -> {node.getElement().mouseReleased();});
    }
    public void keyPressed(KeyEvent keyEvent) {
        this.dfs(this.getRoot(), node -> {node.getElement().keyPressed(keyEvent);});
    }
    public void keyTyped(char c) {
        this.dfs(this.getRoot(), node -> {node.getElement().keyTyped(c);});
    }

    public void mouseDragged(int x, int y) {
        if (this.root == null) return;
        this.mouseDraggedDFS(x, y, this.root, this.ROOT_CONTEXT);
    }

}
