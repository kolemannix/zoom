package com.koleman.zoom.logic.model.shape;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public enum Type implements IndexedEnum {
    TRIANGLE("Triangle", 1), SQUARE("Square", 2), CIRCLE("Circle", 3);

    private String name;
    private int index;

    private Type(String name, int index) {
        this.name = name;
        this.index = index;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
