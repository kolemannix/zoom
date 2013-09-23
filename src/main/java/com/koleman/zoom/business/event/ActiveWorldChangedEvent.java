package com.koleman.zoom.business.event;

import com.koleman.zoom.logic.model.world.ZoomWorld;
import org.springframework.context.ApplicationEvent;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/11/13
 * Assignment: Homework
 * Other Collaborators: None
 */
public class ActiveWorldChangedEvent extends ApplicationEvent {

    private final ZoomWorld world;

    public ActiveWorldChangedEvent(Object source, ZoomWorld world) {
        super(source);
        this.world = world;
    }

    public ZoomWorld getWorld() {
        return this.world;
    }
}
