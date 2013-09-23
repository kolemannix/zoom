package com.koleman.zoom.business.dao;

import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.Sentence;

import java.util.List;

/**
 * Author Koleman Nix
 * Created On 6/14/13
 */
public interface WorldDAO {
    public void createWorld(ZoomWorld world);
    public void deleteWorld(ZoomWorld world);
    public List<ZoomWorld> getWorlds();
    public void syncWorld(ZoomWorld world);
    public void syncSentences(ZoomWorld world);
    public void mergeSentence(Sentence sentence);
}
