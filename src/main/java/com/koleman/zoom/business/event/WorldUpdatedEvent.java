package com.koleman.zoom.business.event;

import org.springframework.context.ApplicationEvent;

/**
 * Author Koleman Nix
 * Created On 6/17/13
 */
public class WorldUpdatedEvent extends ApplicationEvent {

    public WorldUpdatedEvent(Object source) {
        super(source);
    }
}
