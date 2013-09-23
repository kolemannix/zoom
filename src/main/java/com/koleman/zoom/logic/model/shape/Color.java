package com.koleman.zoom.logic.model.shape;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public enum Color implements IndexedEnum {
    RED("Red", 1), GREEN("Green", 2), BLUE("Blue", 3);

    private String name;
    private int index;

    private Color(String name, int index) {
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
