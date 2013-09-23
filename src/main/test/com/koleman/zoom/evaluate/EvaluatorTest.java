package com.koleman.zoom.evaluate;

import com.koleman.zoom.logic.evaluate.Evaluator;
import com.koleman.zoom.logic.evaluate.SentenceState;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.Sentence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author Koleman Nix
 * Created On 6/25/13
 */
public class EvaluatorTest {
    @Test
    public void testBasics() {
        Sentence sentence = new Sentence();
        sentence.setWorld(new ZoomWorld());
        sentence.setTokensForPersist("~ T");
        assertEquals(SentenceState.FALSE, Evaluator.evaluate(sentence));

        sentence = new Sentence();
        sentence.setWorld(new ZoomWorld());
        sentence.setTokensForPersist("~ F");
        assertEquals(SentenceState.TRUE, Evaluator.evaluate(sentence));
    }
}
