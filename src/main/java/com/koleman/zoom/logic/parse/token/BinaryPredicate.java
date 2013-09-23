package com.koleman.zoom.logic.parse.token;

/**
 * Author Koleman Nix
 * Created On 3/19/13
 */
public enum BinaryPredicate implements Predicate {
    SMALLER("Smaller"), LARGER("Larger"),
    SAMESIZE("SameSize"),
    SAMECOLOR("SameColor"),
    SAMESHAPE("SameShape"),
    ABOVE("Above"), BELOW("Below"),
    LEFTOF("LeftOf"), RIGHTOF("RightOf"),
    SAMEROW("SameRow"), SAMECOLUMN("SameColumn");

    @Override
    public int getArity() {
        return 2;
    }

    private String rep;

    private BinaryPredicate(String representation) {
        this.rep = representation;
    }

    @Override
    public String getSymbol() {
        return this.rep;
    }

    @Override
    public String getPersistentRepresentation() {
        return this.rep.toLowerCase();
    }
    @Override
    public String toString() {
        return this.rep;
    }
}
