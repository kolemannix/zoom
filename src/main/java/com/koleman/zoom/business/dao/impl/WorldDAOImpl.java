package com.koleman.zoom.business.dao.impl;

import com.koleman.zoom.business.dao.WorldDAO;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.Sentence;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/14/13
 * Assignment: Homework
 * Other Collaborators: None
 */
@Service
public class WorldDAOImpl implements WorldDAO {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createWorld(ZoomWorld world) {
        entityManager.persist(world);
    }

    @Override
    public void deleteWorld(ZoomWorld world) {
        ZoomWorld dbCopy = entityManager.find(ZoomWorld.class, world.getId());
        entityManager.remove(dbCopy);
    }

    @Override
    public List<ZoomWorld> getWorlds() {
        return entityManager.createQuery("select w from ZoomWorld w").getResultList();
    }

    @Override
    public void syncWorld(ZoomWorld world) {
        ZoomWorld dbCopy = entityManager.find(ZoomWorld.class, world.getId());
        dbCopy.setMatrix(world.getMatrix());
        dbCopy.setName(world.getName());
        dbCopy.setSentences(world.getSentences());
        entityManager.merge(dbCopy);
    }

    @Override
    public void syncSentences(ZoomWorld world) {
        ZoomWorld dbCopy = entityManager.find(ZoomWorld.class, world.getId());
        dbCopy.setSentences(world.getSentences());
        entityManager.merge(dbCopy);
    }

    @Override
    public void mergeSentence(Sentence sentence) {
        Sentence dbCopy = entityManager.find(Sentence.class, sentence.getId());
        dbCopy.setTokensForPersist(sentence.getTokensForPersist());
        entityManager.merge(dbCopy);
    }
}
