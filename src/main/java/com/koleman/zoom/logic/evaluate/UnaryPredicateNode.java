package com.koleman.zoom.logic.evaluate;

import com.koleman.zoom.logic.model.shape.Color;
import com.koleman.zoom.logic.model.shape.Size;
import com.koleman.zoom.logic.model.shape.Type;
import com.koleman.zoom.logic.model.shape.ZoomShape;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.token.Predicate;
import com.koleman.zoom.logic.parse.token.UnaryPredicate;
import com.koleman.zoom.logic.parse.token.Variable;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public class UnaryPredicateNode extends AbstractASTNode {

    private Predicate type;
    private Variable argument;


    public UnaryPredicateNode(UnaryPredicate type) {
        this.type = type;
    }

    public Variable getArgument() {
        return argument;
    }

    public void setArgument(Variable argument) {
        this.argument = argument;
    }

    @Override
    public boolean getValue(ZoomWorld world) {
        boolean result = eval(world);
        if (isNegated()) result = !result;
        return result;
    }

    private boolean eval(ZoomWorld world) {
        ZoomShape obj = world.getObjectForSymbol(argument);
        if (obj == null) return false;
        if (type == UnaryPredicate.RED) {
            return (obj.getColor() == Color.RED);
        }
        if (type == UnaryPredicate.GREEN) {
            return (obj.getColor() == Color.GREEN);
        }
        if (type == UnaryPredicate.BLUE) {
            return (obj.getColor() == Color.BLUE);
        }
        if (type == UnaryPredicate.SMALL) {
            return (obj.getSize() == Size.SMALL);
        }
        if (type == UnaryPredicate.MEDIUM) {
            return (obj.getSize() == Size.MEDIUM);
        }
        if (type == UnaryPredicate.LARGE) {
            return (obj.getSize() == Size.LARGE);
        }
        if (type == UnaryPredicate.TRIANGLE) {
            return (obj.getType() == Type.TRIANGLE);
        }
        if (type == UnaryPredicate.SQUARE) {
            return (obj.getType() == Type.SQUARE);
        }
        if (type == UnaryPredicate.CIRCLE) {
            return (obj.getType() == Type.CIRCLE);
        }
        return false;
    }



    @Override
    public String print() {
        return type.getSymbol() + "(" + argument.getSymbol() + ")";
    }

    @Override
    public boolean putVariable(Variable variable) {
        if (argument == null) {
            argument = variable;
            return true;
        }
        return false;
    }

    @Override
    public boolean putChild(ASTNode node) {
        return false;
    }
}
