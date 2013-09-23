package com.koleman.zoom.business.event;

import com.koleman.zoom.ui.board.Tile;
import org.springframework.context.ApplicationEvent;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/13/13
 * Assignment: Homework
 * Other Collaborators: None
 */
public class TileClickedEvent extends ApplicationEvent {

    private final Tile tile;

    public TileClickedEvent(Object source, Tile tile) {
        super(source);
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}
