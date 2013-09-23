package com.koleman.zoom.logic.evaluate;

import com.koleman.zoom.logic.model.shape.ZoomShape;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.token.BinaryPredicate;
import com.koleman.zoom.logic.parse.token.Predicate;
import com.koleman.zoom.logic.parse.token.Variable;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public class BinaryPredicateNode extends AbstractASTNode {

    private Predicate type;
    private Variable argumentOne;
    private Variable argumentTwo;


    public BinaryPredicateNode(BinaryPredicate type, Variable argumentOne, Variable argumentTwo) {
        this.type = type;
        this.argumentOne = argumentOne;
        this.argumentTwo = argumentTwo;
    }

    @Override
    public boolean getValue(ZoomWorld world) {
        boolean result = eval(world);
        if (isNegated()) result = !result;
        return result;
    }

    private boolean eval(ZoomWorld world) {
        ZoomShape obj1 = world.getObjectForSymbol(argumentOne);
        ZoomShape obj2 = world.getObjectForSymbol(argumentTwo);
        if (obj1 == null || obj2 == null) return false;
        if (type == BinaryPredicate.SMALLER) return (obj1.getSize().compareTo(obj2.getSize()) < 0);
        if (type == BinaryPredicate.SAMESIZE) return (obj1.getSize().compareTo(obj2.getSize()) == 0);
        if (type == BinaryPredicate.LARGER) return (obj1.getSize().compareTo(obj2.getSize()) > 0);
        if (type == BinaryPredicate.SAMECOLOR) return (obj1.getColor().equals(obj2.getColor()));
        if (type == BinaryPredicate.SAMESHAPE) return obj1.getType().equals(obj2.getType());

        int x1 = world.getXCoordinate(obj1);
        int x2 = world.getXCoordinate(obj2);
        int y1 = world.getYCoordinate(obj1);
        int y2 = world.getYCoordinate(obj2);

        if (type == BinaryPredicate.ABOVE) return y1 < y2;
        if (type == BinaryPredicate.SAMECOLUMN) return x1 == x2;
        if (type == BinaryPredicate.BELOW) return y1 > y2;
        if (type == BinaryPredicate.LEFTOF) return x1 < x2;
        if (type == BinaryPredicate.SAMEROW) return y1 == y2;
        if (type == BinaryPredicate.RIGHTOF) return x1 > x2;

        return false;
    }

    @Override
    public String print() {
        return type.getSymbol()
                + "("
                + argumentOne.getSymbol()
                + ", " + argumentTwo.getSymbol()
                + ")";
    }

    @Override
    public boolean putVariable(Variable variable) {
        if (argumentOne == null) {
            argumentOne = variable;
            return true;
        } else {
            if (argumentTwo == null) {
                argumentTwo = variable;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean putChild(ASTNode node) {
        return false;
    }
}