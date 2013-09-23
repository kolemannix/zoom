package com.koleman.zoom.ui;

import com.koleman.zoom.business.service.WorldService;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.vaadin.ui.*;

/**
 * Author Koleman Nix
 * Created On 6/17/13
 */
public class WorldDetailsDialog extends Window {

    WorldService worldService;
    private ZoomWorld world;

    public WorldDetailsDialog(ZoomWorld world, WorldService service) {
        super("World Details");
        this.world = world;
        this.worldService = service;
        initLayout();
    }

    private void initLayout() {
        setWidth("200px");
        setHeight("200px");
        final TextField nameField = new TextField("Name");
        if (this.world != null) {
            nameField.setValue(world.getName());
        }
        HorizontalLayout buttons = new HorizontalLayout();

        final CheckBox randomizeButton = new CheckBox("Randomize World");
        Button saveButton = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (world == null) {
                    worldService.createAndActivateWorld((String)nameField.getValue(), randomizeButton.booleanValue());
                } else {
                    world.setName((String)nameField.getValue());
                    if (randomizeButton.booleanValue()) {
                        world.randomize();
                    }
                    worldService.syncWorld(world);
                }
                close();
            }
        });
        Button cancelButton = new Button("Cancel", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });

        addComponent(nameField);
        addComponent(randomizeButton);
        buttons.addComponent(cancelButton);
        buttons.addComponent(saveButton);
        buttons.setComponentAlignment(saveButton, com.vaadin.ui.Alignment.TOP_RIGHT);
        buttons.setComponentAlignment(cancelButton, com.vaadin.ui.Alignment.TOP_RIGHT);
        addComponent(buttons);
    }

}
