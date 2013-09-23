package com.koleman.zoom.logic.parse.token;

/**
 * Author Koleman Nix
 * Created On 3/19/13
 */
public enum Quantifier implements Token {
    EXISTENTIAL("∃", "EXIST"), UNIVERSAL("∀", "FORALL");

    private String rep;
    private String persistentRep;

    private Quantifier(String representation, String persistentRep) {
        this.rep = representation;
        this.persistentRep = persistentRep;
    }

    @Override
    public String getSymbol() {
        return this.rep;
    }

    @Override
    public String getPersistentRepresentation() {
        return this.persistentRep.toLowerCase();
    }

    @Override
    public String toString() {
        return this.rep;
    }
}
