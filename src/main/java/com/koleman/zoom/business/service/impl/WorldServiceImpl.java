package com.koleman.zoom.business.service.impl;

import com.koleman.zoom.business.dao.WorldDAO;
import com.koleman.zoom.business.event.ActiveWorldChangedEvent;
import com.koleman.zoom.business.event.TileClickedEvent;
import com.koleman.zoom.business.service.WorldService;
import com.koleman.zoom.logic.model.shape.*;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.Sentence;
import com.koleman.zoom.logic.parse.token.Token;
import com.koleman.zoom.ui.WorldsPanel;
import com.koleman.zoom.ui.board.Board;
import com.koleman.zoom.ui.board.Tile;
import com.koleman.zoom.ui.inspector.Inspector;
import com.koleman.zoom.ui.sentence.SentenceTable;
import org.dellroad.stuff.vaadin.VaadinApplicationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/11/13
 * Assignment: Homework
 * Other Collaborators: None
 */
@Service
public class WorldServiceImpl implements WorldService {

    private ZoomWorld activeWorld;
    private Tile selectedTile;

    @Autowired
    WorldDAO worldDAO;

    @Autowired
    Inspector inspector;

    @Autowired
    Board board;

    @Autowired
    WorldsPanel worldsPanel;

    @Autowired
    SentenceTable sentenceTable;

    @Autowired
    SimpleApplicationEventMulticaster multicaster;

    private VaadinApplicationListener<TileClickedEvent> tileClickedListener;

    @PostConstruct
    protected void doInit() {
        tileClickedListener = new VaadinApplicationListener<TileClickedEvent>(multicaster, TileClickedEvent.class) {
            @Override
            protected void onApplicationEventInternal(TileClickedEvent event) {
                selectedTile = event.getTile();
            }
        };
        multicaster.addApplicationListener(tileClickedListener);
    }

    @Override
    public ZoomWorld getActiveWorld() {
        return this.activeWorld;
    }

    @Override
    @Transactional
    public void clearSelectedTile() {
        activeWorld.setShape(null, selectedTile.getX(), selectedTile.getY());
        board.update();
        worldDAO.syncWorld(activeWorld);
    }

    @Override
    @Transactional
    public void createAndActivateWorld(String name, boolean randomized) {
        ZoomWorld newWorld = new ZoomWorld(name);
        if (randomized) newWorld.randomize();
        this.activeWorld = newWorld;
        worldDAO.createWorld(newWorld);
        ActiveWorldChangedEvent activeWorldChangedEvent = new ActiveWorldChangedEvent(this, activeWorld);
        multicaster.multicastEvent(activeWorldChangedEvent);
        worldsPanel.updateContainer();
    }

    @Override
    @Transactional
    public void createShape() {
        ZoomShape newShape = ShapeFactory.createShape(Size.SMALL, Color.RED, Type.SQUARE);
        activeWorld.setShape(newShape, selectedTile.getX(), selectedTile.getY());
        worldDAO.syncWorld(activeWorld);

        selectedTile.setShape(newShape);
        inspector.setShape(newShape);
    }

    @Override
    @Transactional
    public void updateShape(ZoomShape shape) {
        activeWorld.setShape(shape, selectedTile.getX(), selectedTile.getY());
        worldDAO.syncWorld(activeWorld);
        selectedTile.setShape(shape);
        sentenceTable.repopulate(activeWorld);
    }

    @Override
    public List<ZoomWorld> getAllWorlds() {
        return worldDAO.getWorlds();
    }

    @Override
    public Tile getSelectedTile() {
        return this.selectedTile;
    }

    @Override
    @Transactional
    public void deleteWorld(ZoomWorld worldToDelete) {
        worldDAO.deleteWorld(worldToDelete);
        worldsPanel.updateContainer();
    }

    @Override
    public void setActiveWorld(ZoomWorld world) {
        this.activeWorld = world;
        multicaster.multicastEvent(new ActiveWorldChangedEvent(this, world));
    }

    @Override
    @Transactional
    public void syncWorld(ZoomWorld world) {
        worldDAO.syncWorld(world);
        worldsPanel.updateContainer();
    }

    @Override
    @Transactional
    public void newSentence() {
        Sentence sentence = new Sentence();
        sentence.setWorld(activeWorld);
        activeWorld.addSentence(sentence);
        worldDAO.syncSentences(activeWorld);
        sentenceTable.repopulate(activeWorld);
    }

    @Override
    @Transactional
    public void deleteSentence(Sentence sentence) {
        activeWorld.removeSentence(sentence);
        worldDAO.syncSentences(activeWorld);
        sentenceTable.repopulate(activeWorld);
    }

    @Override
    @Transactional
    public void addTokenToSelectedSentence(Token token) {
        Sentence selectedSentence = sentenceTable.getSelectedSentence();
        if (selectedSentence != null) {
            selectedSentence.append(token);
            worldDAO.mergeSentence(selectedSentence);
            sentenceTable.refreshSentence(selectedSentence);
        }
    }

    @Override
    @Transactional
    public void performBackspaceAction() {
        Sentence selectedSentence = sentenceTable.getSelectedSentence();
        if (selectedSentence != null) {
            selectedSentence.removeLast();
            worldDAO.mergeSentence(selectedSentence);
            sentenceTable.refreshSentence(selectedSentence);
        }
    }
}
