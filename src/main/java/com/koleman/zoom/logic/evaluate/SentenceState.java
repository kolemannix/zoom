package com.koleman.zoom.logic.evaluate;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/18/13
 * Assignment: Homework
 * Other Collaborators: None
 */
public enum SentenceState {
    TRUE("T"), FALSE("F"), INVALID("X");

    private String rep;

    private SentenceState(String rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return this.rep;
    }
}
