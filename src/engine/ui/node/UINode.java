package engine.ui.node;

import java.util.ArrayList;
import java.util.List;

import engine.datastructures.Visitor;
import engine.ui.elements.UIElement;

public class UINode {

    private UIElement element;
    private UINode parent;
    private List<UINode> children;

    public UINode(UIElement element) {
        this.element = element;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    public UIElement getElement() {
        return this.element;
    }

    public UINode getParent() {
        return this.parent;
    }

    public List<UINode> getChildren() {
        return this.children;
    }

    public UINode addChild(UINode child) {
        if (child == null) return this;

        if (child.parent != null) child.parent.removeChild(child);

        child.parent = this;
        this.children.add(child);
        return this;
    }

    //addchild index and child

    public UINode getChild(int index) {
        return this.children.get(index);
    }

    public UINode getFirstChild() {
        return this.children.get(0);
    }

    public UINode getLastChild() {
        return this.children.get(this.children.size() - 1);
    }

    public boolean removeChild(UINode child) {
        return this.children.remove(child);
    }

    public UINode removeChildren() {
        this.children = new ArrayList<>();
        return this;
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    public boolean accept(Visitor<UINode> visitor) {
        return visitor.visit(this);
    }
}
