package com.koleman.zoom.logic.parse.token;

/**
 * Author Koleman Nix
 * Created On 3/19/13
 */
public enum NonTerminal implements Token {
    SENTENCE("S"), EXPRESSION("E"), E_PRIME("E'"), TERM("T"),
    PREDICATE("P"), BINARY_PREDICATE("BP"), UNARY_PREDICATE("UP");

    private String rep;

    private NonTerminal(String representation) {
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
