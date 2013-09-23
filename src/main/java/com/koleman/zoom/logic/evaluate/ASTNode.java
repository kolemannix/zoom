package com.koleman.zoom.logic.evaluate;

import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.token.Variable;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public interface ASTNode {
    public boolean getValue(ZoomWorld world);
    public void setNegated(boolean flag);
    public boolean isNegated();
    public String print();
    public boolean putVariable(Variable variable);
    public boolean putChild(ASTNode node);
}
