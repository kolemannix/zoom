package com.koleman.zoom.logic.parse.token;

/**
 * Author Koleman Nix
 * Created On 3/19/13
 */
public enum Variable implements Token {

    A("a"), B("b"), C("c"), D("d"), E("e"), F("f"),
    U("u"), V("v"), W("w"), X("x"), Y("y"), Z("z");

    private String name;

    private Variable(String name) {
        this.name = name;
    }

    @Override
    public String getSymbol() {
        return name;
    }

    @Override
    public String getPersistentRepresentation() {
        return this.name.toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }
}
