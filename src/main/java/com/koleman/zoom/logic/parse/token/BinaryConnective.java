package com.koleman.zoom.logic.parse.token;

/**
 * Author Koleman Nix
 * Created On 3/19/13
 */
public enum BinaryConnective implements Token {
    AND("∧", "and"), OR("∨", "or"),
    CONDITIONAL("→", "->"), BICONDITIONAL("⇔", "<=>");

    private String rep;
    private String persistentRep;

    private BinaryConnective(String representation, String persistentRep) {
        this.rep = representation;
        this.persistentRep = persistentRep;
    }

    @Override
    public String getSymbol() {
        return this.rep;
    }

    @Override
    public String getPersistentRepresentation() {
        return this.persistentRep;
    }
    @Override
    public String toString() {
        return this.rep;
    }
}
