package com.koleman.zoom.business.event;

import org.springframework.context.ApplicationEvent;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/17/13
 * Assignment: Homework
 * Other Collaborators: None
 */
public class WorldUpdatedEvent extends ApplicationEvent {

    public WorldUpdatedEvent(Object source) {
        super(source);
    }
}
