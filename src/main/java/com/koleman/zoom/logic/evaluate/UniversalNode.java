package com.koleman.zoom.logic.evaluate;

import com.koleman.zoom.logic.model.shape.ZoomShape;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.token.Variable;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public class UniversalNode extends AbstractASTNode {

    private ASTNode child;
    private Variable variable;

    public UniversalNode(Variable variable, ASTNode child) {
        this.variable = variable;
        this.child = child;
    }
    @Override
    public boolean getValue(ZoomWorld world) {
        boolean result = eval(world);
        if (isNegated()) result = !result;
        return result;
    }

    private boolean eval(ZoomWorld world) {
        // Perform a For-All loop on the world
        ZoomShape obj;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                obj = world.getShape(x, y);
                if (obj == null) continue;
                world.putPointer(variable, obj);
                if (!child.getValue(world)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String print() {
        return "For all " + variable.getSymbol() + ", " + child.print();
    }

    @Override
    public boolean putVariable(Variable variable) {
        if (variable == null) {
            this.variable = variable;
            return true;
        }
        return false;
    }

    @Override
    public boolean putChild(ASTNode node) {
        if (this.child == null) {
            child = node;
            return true;
        }
        return false;
    }
}
