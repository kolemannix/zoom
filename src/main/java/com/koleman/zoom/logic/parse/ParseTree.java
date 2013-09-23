package com.koleman.zoom.logic.parse;

/**
 * Author Koleman Nix
 * Created On 2/26/13
 */
public class ParseTree {
    private ParseTreeNode root;

    public ParseTreeNode getRoot() {
        return root;
    }

    public void setRoot(ParseTreeNode root) {
        this.root = root;
    }

    public void print() {
        root.print("", false);
    }
}
