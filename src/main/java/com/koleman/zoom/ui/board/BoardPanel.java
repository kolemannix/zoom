package com.koleman.zoom.ui.board;

import com.vaadin.ui.Panel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author Koleman Nix
 * Created On 7/8/13
 */
@Component
public class BoardPanel extends Panel {

    @Autowired
    private Board board;

    @PostConstruct
    protected void doInit() {
        setCaption("World");
        addComponent(board);
    }
}
