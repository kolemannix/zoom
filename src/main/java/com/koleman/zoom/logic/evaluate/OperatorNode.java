package com.koleman.zoom.logic.evaluate;

import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.token.BinaryConnective;
import com.koleman.zoom.logic.parse.token.Variable;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public class OperatorNode extends AbstractASTNode {

    private final BinaryConnective type;
    private ASTNode left;
    private ASTNode right;

    public OperatorNode(BinaryConnective type, ASTNode left, ASTNode right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean getValue(ZoomWorld world) {
        boolean result = eval(world);
        if (isNegated()) result = !result;
        return result;
    }

    private boolean eval(ZoomWorld world) {
        boolean leftEval = left.getValue(world);
        boolean rightEval = right.getValue(world);
        switch (type) {
            case AND:           // p ^ q
                return leftEval && rightEval;
            case OR:            // p v q
                return leftEval || rightEval;
            case CONDITIONAL:    // p -> q
                return !(!leftEval && rightEval);
            case BICONDITIONAL:  // p <-> q
                return leftEval == rightEval;
        }
        return false;
    }

    @Override
    public String print() {
        return left.print() + " " + type.getSymbol() + " " + right.print();
    }

    @Override
    public boolean putVariable(Variable variable) {
        return false;
    }

    @Override
    public boolean putChild(ASTNode node) {
        if (left == null) {
            left = node;
            return true;
        } else if (right == null) {
            right = node;
            return true;
        }
        return false;
    }
}
