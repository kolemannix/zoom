package com.koleman.zoom.logic.parse.token;

/**
 * Author Koleman Nix
 * Created On 3/19/13
 */
public enum UnaryPredicate implements Predicate {
    RED("Red"), BLUE("Blue"), GREEN("Green"),
    TRIANGLE("Triangle"), SQUARE("Square"), CIRCLE("Circle"),
    SMALL("Small"), MEDIUM("Medium"), LARGE("Large");

    @Override
    public int getArity() {
        return 1;
    }

    private String rep;

    private UnaryPredicate(String representation) {
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
