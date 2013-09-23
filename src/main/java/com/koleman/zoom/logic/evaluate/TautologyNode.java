package com.koleman.zoom.logic.evaluate;

import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.token.Variable;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/10/13
 * Assignment: Homework
 * Other Collaborators: None
 */
public class TautologyNode extends AbstractASTNode {

    @Override
    public boolean getValue(ZoomWorld world) {
        return !isNegated();
    }

    @Override
    public String print() {
        return "T";
    }

    @Override
    public boolean putVariable(Variable variable) {
        return false;
    }

    @Override
    public boolean putChild(ASTNode node) {
        return false;
    }
}
