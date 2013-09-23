package com.koleman.zoom.ui;

import com.koleman.zoom.business.event.ActiveWorldChangedEvent;
import com.koleman.zoom.business.service.WorldService;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import org.dellroad.stuff.vaadin.VaadinApplicationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Author Koleman Nix
 * Created On 6/17/13
 */
@Component
public class WorldsPanel extends Panel {
    private Table worldsTable;
    private Button editDetailButton;
    private Button deleteGameButton;
    private Button newGameButton;
    BeanItemContainer<ZoomWorld> container;
    private VerticalLayout mainLayout;

    @Autowired
    WorldService worldService;

    @Autowired
    SimpleApplicationEventMulticaster multicaster;

    @PostConstruct
    protected void buildLayout() {
        setCaption("Worlds");
        setWidth("200px");
        setHeight("400px");
        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        List list = worldService.getAllWorlds();
        container = new BeanItemContainer<ZoomWorld>(ZoomWorld.class, list);
        worldsTable = new Table();
        worldsTable.setMultiSelect(false);
        worldsTable.setWidth("100%");
        worldsTable.setHeight("180px");
        worldsTable.setSelectable(true);
        worldsTable.setContainerDataSource(container);
        worldsTable.setVisibleColumns(new String[]{"id", "name"});
        worldsTable.setColumnHeaders(new String[]{"#", "Name"});
        worldsTable.setColumnWidth("id", 10);
        worldsTable.setColumnWidth("name", 100);

        worldsTable.addListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                ZoomWorld worldClicked = container.getItem(itemClickEvent.getItemId()).getBean();
                worldService.setActiveWorld(worldClicked);
            }
        });

        worldsTable.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (worldsTable.getValue() == null) {
                    editDetailButton.setEnabled(false);
                    deleteGameButton.setEnabled(false);
                } else {
                    editDetailButton.setEnabled(true);
                    deleteGameButton.setEnabled(true);
                }
            }
        });

        HorizontalLayout buttonLayout = new HorizontalLayout();
        editDetailButton = new Button("Edit");
        deleteGameButton = new Button("Delete");
        newGameButton = new Button("New");
        newGameButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                newWorldAction();
            }
        });
        deleteGameButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                deleteWorldAction();
            }
        });
        editDetailButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                editWorldAction();
            }
        });

        deleteGameButton.setEnabled(false);
        editDetailButton.setEnabled(false);

        buttonLayout.addComponent(newGameButton);
        buttonLayout.addComponent(editDetailButton);
        buttonLayout.addComponent(deleteGameButton);
        buttonLayout.setSpacing(true);

        mainLayout.addComponent(buttonLayout);
        mainLayout.addComponent(worldsTable);

        addComponent(mainLayout);
    }

    public void updateContainer() {
        worldsTable.select(worldsTable.getNullSelectionItemId());
        container.removeAllItems();
        container.addAll(worldService.getAllWorlds());
        worldsTable.select(worldService.getActiveWorld());
    }
    private void newWorldAction() {
        getApplication().getMainWindow().addWindow(new WorldDetailsDialog(null, worldService));
    }

    private void deleteWorldAction() {
        ZoomWorld selectedWorld = (ZoomWorld) worldsTable.getValue();
        worldService.deleteWorld(selectedWorld);
        if (container.firstItemId() != null) {
            worldsTable.select(container.firstItemId());
            multicaster.multicastEvent(new ActiveWorldChangedEvent(this, container.firstItemId()));
        }
    }

    private void editWorldAction() {
        ZoomWorld selectedWorld = (ZoomWorld) worldsTable.getValue();
        WorldDetailsDialog worldDetailsDialog= new WorldDetailsDialog(selectedWorld, worldService);
        getApplication().getMainWindow().addWindow(worldDetailsDialog);
    }
}
