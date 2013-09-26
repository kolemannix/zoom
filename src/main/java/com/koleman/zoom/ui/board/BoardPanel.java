package com.koleman.zoom.ui.board;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
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

        TabSheet tabsheet = new TabSheet();
        addComponent(tabsheet);

// Create the first tab
        VerticalLayout tab1 = new VerticalLayout();
        tab1.addComponent(board);
        tabsheet.addTab(tab1, "World 1",
                new ThemeResource("img/shapes/small_red_circle.png"));

// This tab gets its caption from the component caption
        VerticalLayout tab2 = new VerticalLayout();
        tab2.addComponent(new Embedded(null,
                null));
        tab2.setCaption("World 2");
        tabsheet.addTab(tab2).setIcon(
                new ThemeResource("img/shapes/small_blue_circle.png"));
    }
}
