package com.koleman.zoom.logic.evaluate;

import com.koleman.zoom.logic.model.world.ZoomWorld;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public class AbstractSyntaxTree {

    private ASTNode rootNode;
    private ZoomWorld world;
    private ASTNode cursor;


    public ASTNode getRootNode() {
        return this.rootNode;
    }

    public void setRootNode(ASTNode node) {
        this.rootNode = node;
    }

    public ASTNode getCursor() {
        return cursor;
    }

    public void setCursor(ASTNode cursor) {
        this.cursor = cursor;
    }

    public ZoomWorld getWorld() {
        return this.world;
    }

    public boolean isEmpty() {
        return rootNode == null;
    }

    public boolean evaluate(ZoomWorld world) {
        world.clearPointers();
        world.updatePointers();
        return rootNode.getValue(world);
    }

    public String print() {
        return rootNode.print();
    }
}
