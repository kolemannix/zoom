package com.koleman.zoom.logic.evaluate;

/**
 * Author Koleman Nix
 * Created On 6/10/13
 */
public abstract class AbstractASTNode implements ASTNode {

    private boolean negated;

    @Override
    public void setNegated(boolean flag) {
        this.negated = flag;
    }

    @Override
    public boolean isNegated() {
        return negated;
    }
}
