package com.koleman.zoom.business.service;

import com.koleman.zoom.logic.model.shape.ZoomShape;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.Sentence;
import com.koleman.zoom.logic.parse.token.Token;
import com.koleman.zoom.ui.board.Tile;

import java.util.List;

/**
 * Author Koleman Nix
 * Created On 6/11/13
 */
public interface WorldService {
    public ZoomWorld getActiveWorld();
    public void clearSelectedTile();
    public void createAndActivateWorld(String name, boolean randomized);
    public void createShape();
    public void updateShape(ZoomShape shape);
    public List<ZoomWorld> getAllWorlds();
    public Tile getSelectedTile();
    public void deleteWorld(ZoomWorld worldToDelete);
    public void setActiveWorld(ZoomWorld world);
    public void syncWorld(ZoomWorld world);
    public void newSentence();
    public void deleteSentence(Sentence sentence);


    public void addTokenToSelectedSentence(Token token);
    public void performBackspaceAction();
}
