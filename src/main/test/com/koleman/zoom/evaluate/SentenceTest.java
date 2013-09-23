package com.koleman.zoom.evaluate;

import com.koleman.zoom.logic.parse.Sentence;
import com.koleman.zoom.logic.parse.token.*;
import org.junit.Before;
import org.junit.Test;

import static com.koleman.zoom.logic.parse.token.Misc.CLOSE;
import static com.koleman.zoom.logic.parse.token.Misc.OPEN;
import static com.koleman.zoom.logic.parse.token.Quantifier.EXISTENTIAL;
import static com.koleman.zoom.logic.parse.token.Quantifier.UNIVERSAL;
import static com.koleman.zoom.logic.parse.token.UnaryPredicate.BLUE;
import static com.koleman.zoom.logic.parse.token.UnaryPredicate.GREEN;
import static com.koleman.zoom.logic.parse.token.UnaryPredicate.RED;
import static com.koleman.zoom.logic.parse.token.Variable.X;
import static com.koleman.zoom.logic.parse.token.Variable.Y;
import static org.junit.Assert.assertEquals;

/**
 * Author Koleman Nix
 * Created On 6/18/13
 */
public class SentenceTest {

    private Sentence sentenceOne;
    private Sentence sentenceTwo;


    @Before
    public void setUp() {
        sentenceOne = new Sentence();
        sentenceOne.append(UnaryPredicate.CIRCLE);
        sentenceOne.append(Misc.OPEN); sentenceOne.append(Variable.A);
        sentenceOne.append((Misc.CLOSE));

        sentenceTwo = new Sentence();
        sentenceTwo.append(UNIVERSAL); sentenceTwo.append(X); sentenceTwo.append(OPEN);
        sentenceTwo.append(OPEN); sentenceTwo.append(RED); sentenceTwo.append(OPEN); sentenceTwo.append(X); sentenceTwo.append(CLOSE);
        sentenceTwo.append(BinaryConnective.BICONDITIONAL); sentenceTwo.append(GREEN); sentenceTwo.append(OPEN);
        sentenceTwo.append(X); sentenceTwo.append(CLOSE); sentenceTwo.append(CLOSE);
        sentenceTwo.append(BinaryConnective.CONDITIONAL);
        sentenceTwo.append(OPEN); sentenceTwo.append(EXISTENTIAL); sentenceTwo.append(Y); sentenceTwo.append(OPEN);
        sentenceTwo.append(BinaryPredicate.SMALLER); sentenceTwo.append(OPEN);
        sentenceTwo.append(Y); sentenceTwo.append(X); sentenceTwo.append(CLOSE); sentenceTwo.append(BinaryConnective.OR);
        sentenceTwo.append(BLUE); sentenceTwo.append(OPEN); sentenceTwo.append(Y);
        sentenceTwo.append(CLOSE); sentenceTwo.append(CLOSE); sentenceTwo.append(CLOSE); sentenceTwo.append(CLOSE);
    }

    @Test
    public void testPersistence() {
        String sentenceOneString = sentenceOne.getTokensForPersist();
        System.out.println("Sentence One String: " + sentenceOneString);
        Sentence decoded = new Sentence();
        decoded.setTokensForPersist(sentenceOneString);
        System.out.println("Decoded: " + decoded.getTokensForPersist());
        assertEquals(sentenceOne, decoded);
        
        String sentenceTwoString = sentenceTwo.getTokensForPersist();
        System.out.println("Sentence Two String: " + sentenceTwoString);
        decoded = new Sentence();
        decoded.setTokensForPersist(sentenceTwoString);
        System.out.println("Decoded: " + decoded.getTokensForPersist());
        assertEquals(sentenceTwo, decoded);
    }
}
