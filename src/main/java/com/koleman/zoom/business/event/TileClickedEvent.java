package com.koleman.zoom.business.event;

import com.koleman.zoom.ui.board.Tile;
import org.springframework.context.ApplicationEvent;

/**
 * Author Koleman Nix
 * Created On 6/13/13
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
