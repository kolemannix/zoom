package com.koleman.zoom.logic.parse;


import com.koleman.zoom.logic.parse.token.Token;

import java.util.ArrayList;
import java.util.List;


/**
 * Author Koleman Nix
 * Created On 2/26/13
 */
public class ParseTreeNode {
    private ParseTreeNode parent;
    private List<ParseTreeNode> children;
    private Token token;

    public ParseTreeNode(Token value) {
        children = new ArrayList<ParseTreeNode>();
        this.token = value;
    }

    public boolean isRootNode() {
        return parent == null;
    }

    public Token getToken() {
        return this.token;
    }

    public ParseTreeNode getParent() {
        return this.parent;
    }

    public List<ParseTreeNode> getChildren() {
        return this.children;
    }

    public void setParent(ParseTreeNode node) {
        this.parent = node;
    }

    public boolean isLeafNode() {
        return this.children.isEmpty();
    }

    public void addChild(ParseTreeNode node) {
        children.add(node);
    }

    public void removeChild(ParseTreeNode node) {
        children.remove(node);
    }

    public List<ParseTreeNode> getSiblings() {
        if (parent == null) {
            return new ArrayList<ParseTreeNode>();
        }
        List<ParseTreeNode> sibs = parent.getChildren();
        return sibs;
    }

    public void print(String prefix, boolean isLeaf) {
        System.out.println(prefix + (isLeaf ? "└── " : "├── ") + this.token);
        if (children != null) {
            for (int i = 0; i < children.size() - 1; i++) {
                children.get(i).print(prefix + (isLeaf ? "    " : "│   "), false);
            }
            if (children.size() >= 1) {
                children.get(children.size() - 1).print(prefix + (isLeaf ?"    " : "│   "), true);
            }
        }

    }

    public ParseTreeNode copy() {
        ParseTreeNode copy = new ParseTreeNode(getToken());
        copy.setParent(this.parent);
        for (ParseTreeNode child : children) {
            copy.addChild(child.copy());
        }
        return copy;
    }
}
