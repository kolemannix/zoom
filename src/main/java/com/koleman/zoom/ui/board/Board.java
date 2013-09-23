package com.koleman.zoom.ui.board;

import com.koleman.zoom.business.event.ActiveWorldChangedEvent;
import com.koleman.zoom.business.event.TileClickedEvent;
import com.koleman.zoom.business.event.WorldUpdatedEvent;
import com.koleman.zoom.business.service.WorldService;
import com.koleman.zoom.logic.model.shape.ZoomShape;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.ui.Resources;
import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import org.dellroad.stuff.vaadin.VaadinApplicationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/11/13
 * Assignment: Homework
 * Other Collaborators: None
 */
@Component
public class Board extends AbsoluteLayout implements LayoutEvents.LayoutClickListener {

    private static final int FRAME_LENGTH = 480;
    private static final int GRID_LENGTH = FRAME_LENGTH / 8;

    private Tile[][] matrix;
    private GridLayout boardLayout;

    private Tile selectedTile;

//    private ZoomWorld activeWorld;

    @Autowired
    WorldService worldService;

    @Autowired
    SimpleApplicationEventMulticaster multicaster;

    private VaadinApplicationListener<WorldUpdatedEvent> worldUpdatedListener;
    private VaadinApplicationListener<ActiveWorldChangedEvent> worldChangedListener;

    @PostConstruct
    private void doInit() {
        // Build Layout
        setWidth(FRAME_LENGTH + "px");
        setHeight(FRAME_LENGTH + "px");
        setImmediate(true);

        Embedded background = new Embedded(null, Resources.board);
        background.setSizeFull();
        addComponent(background);
        boardLayout = new GridLayout(8, 8);
        boardLayout.setSizeFull();
        matrix = new Tile[8][8];
        selectedTile = null;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                matrix[x][y] = new Tile(x, y);
                matrix[x][y].setShape(null);
                matrix[x][y].addListener(this);
                boardLayout.addComponent(matrix[x][y]);
            }
        }
        addComponent(boardLayout);

        worldUpdatedListener = new VaadinApplicationListener<WorldUpdatedEvent>(multicaster, WorldUpdatedEvent.class) {
            @Override
            protected void onApplicationEventInternal(WorldUpdatedEvent event) {
                update();
            }
        };
        multicaster.addApplicationListener(worldUpdatedListener);

        worldChangedListener = new VaadinApplicationListener<ActiveWorldChangedEvent>(multicaster, ActiveWorldChangedEvent.class) {
            @Override
            protected void onApplicationEventInternal(ActiveWorldChangedEvent event) {
                deselectTile();
                update();
            }
        };
        multicaster.addApplicationListener(worldChangedListener);
    }

    public void update() {
        ZoomWorld currentWorld = worldService.getActiveWorld();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                matrix[x][y].setShape(currentWorld.getShape(x, y));
            }
        }
    }

    private void selectTile(Tile tile) {
        if (selectedTile != null) {
            selectedTile.setSelected(false);
        }
        selectedTile = tile;
        selectedTile.setSelected(true);
    }

    public void clearSelectedTile() {
        if (selectedTile != null) {
            selectedTile.setShape(null);
        }
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void updateSelectedTileWithShape(ZoomShape shape) {
        if (selectedTile != null) {
            selectedTile.setShape(shape);
        }
    }

    public void deselectTile() {
        if (selectedTile != null) {
            selectedTile.setSelected(false);
        }
        selectedTile = null;
    }

    /**
     * Layout has been clicked
     *
     * @param event Component click event.
     */
    @Override
    public void layoutClick(LayoutEvents.LayoutClickEvent event) {
        if (worldService.getActiveWorld() == null) {
            return;
        }
        Tile clickedTile = (Tile)event.getComponent();
        TileClickedEvent applicationEvent = new TileClickedEvent(this, clickedTile);
        selectTile(clickedTile);
        multicaster.multicastEvent(applicationEvent);
    }
}
