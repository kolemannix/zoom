package com.koleman.zoom.logic.model.shape;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public enum Size implements IndexedEnum {
    SMALL("Small", 1), MEDIUM("Medium", 2), LARGE("Large", 3);

    private String name;
    private int index;

    private Size(String name, int index) {
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
