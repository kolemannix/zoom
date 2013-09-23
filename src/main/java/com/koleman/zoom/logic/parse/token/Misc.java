package com.koleman.zoom.logic.parse.token;

/**
 * Author Koleman Nix
 * Created On 3/19/13
 */
public enum Misc implements Token {
    NEGATION("¬", "~"),
    OPEN("(", "("),
    CLOSE(")", ")"),
    TAUTOLOGY("⊤", "T"),
    CONTRADICTION("⊥", "F");

    private String rep;
    private String persistentRep;

    private Misc(String representation, String persistentRep) {
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
