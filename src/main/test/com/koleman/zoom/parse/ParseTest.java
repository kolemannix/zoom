package com.koleman.zoom.parse;

import com.koleman.zoom.logic.parse.FirstOrderLogicParser;
import com.koleman.zoom.logic.parse.Sentence;
import com.koleman.zoom.logic.parse.token.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.koleman.zoom.logic.parse.token.Misc.*;
import static com.koleman.zoom.logic.parse.token.Quantifier.EXISTENTIAL;
import static com.koleman.zoom.logic.parse.token.Quantifier.UNIVERSAL;
import static com.koleman.zoom.logic.parse.token.UnaryPredicate.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Author Koleman Nix
 * Created On 3/21/13
 */
public class ParseTest {

    private Sentence input;
    private FirstOrderLogicParser instance;
    private Variable x;
    private Variable y;

    @Before
    public void setup() {
        input = new Sentence();
        instance = new FirstOrderLogicParser(input);
        x = Variable.X;
        y = Variable.Y;
    }

    public void printGrammar() {
        Map<NonTerminal, String> grammar = FirstOrderLogicParser.GRAMMAR;
        StringBuilder sb = new StringBuilder();
        for (NonTerminal key : grammar.keySet()) {
            sb.append(grammar.get(key));
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void testBasicCase() {
        // Input: Blue(x)
        input.append(BLUE);
        input.append(OPEN); input.append(x); input.append(CLOSE);
        assertTrue(instance.parse());

        // Input: ∃x(Small(x))
        input.clear();
        input.append(EXISTENTIAL); input.append(Variable.X);
        input.append(OPEN);
        input.append(SMALL);
        input.append(OPEN); input.append(x); input.append(CLOSE);
        input.append(CLOSE);
        assertTrue(instance.parse());
        instance.getParseTree().print();

        input.clear();

        // Input: Blue(x) v Red(x)
        input.append(BLUE); input.append(OPEN); input.append(x); input.append(CLOSE);
        input.append(BinaryConnective.OR);
        input.append(RED); input.append(OPEN); input.append(y); input.append(CLOSE);
        assertTrue(instance.parse());

    }

    @Test
    public void testNegationHandling() {
        // Input: ~Green(x)
        input.append(Misc.NEGATION); input.append(GREEN);
        input.append(OPEN); input.append(x); input.append(CLOSE);
        assertTrue(instance.parse());
        instance.getParseTree().print();

        input.clear();
        input.setTokensForPersist("~ T");
        assertTrue(instance.parse());
        instance.getParseTree().print();
    }

    @Test
    public void testComplexCase() {
        // Input: ∀x(∃y(Red(y) -> Smaller(x, y)));
        // "If a shape is red, it is smaller than all other shapes.

        input.append(UNIVERSAL); input.append(x); input.append(OPEN);
        input.append(EXISTENTIAL); input.append(y); input.append(OPEN);
        input.append(RED); input.append(OPEN); input.append(y); input.append(CLOSE); // Red(y)
        input.append(BinaryConnective.CONDITIONAL); // ^
        input.append(BinaryPredicate.SMALLER); // Smaller(x, y)
        input.append(OPEN); input.append(x); input.append(y);
        input.append(CLOSE); input.append(CLOSE); input.append(CLOSE);
        assertTrue(instance.parse());
        instance.getParseTree().print();
    }

    @Test
    public void testRidiculousCase() {
        // Input: ∀x((Red(x) <-> Green(x)) -> (∃y(Smaller(y,x) v Blue(y))));
        input.append(UNIVERSAL); input.append(x); input.append(OPEN);
        input.append(OPEN); input.append(RED); input.append(OPEN); input.append(x); input.append(CLOSE);
        input.append(BinaryConnective.BICONDITIONAL); input.append(GREEN); input.append(OPEN);
        input.append(x); input.append(CLOSE); input.append(CLOSE);
        input.append(BinaryConnective.CONDITIONAL);
        input.append(OPEN); input.append(EXISTENTIAL); input.append(y); input.append(OPEN);
        input.append(BinaryPredicate.SMALLER); input.append(OPEN);
        input.append(y); input.append(x); input.append(CLOSE); input.append(BinaryConnective.OR);
        input.append(BLUE); input.append(OPEN); input.append(y);
        input.append(CLOSE); input.append(CLOSE); input.append(CLOSE); input.append(CLOSE);

        assertTrue(instance.parse());
        instance.getParseTree().print();
    }

    @Test
    public void makeItOverflow() {
        input.setTokensForPersist("red ( x");
        System.out.println(input.getTokensForPersist());
        assertFalse(instance.parse());
    }

    @Test
    public void trueAnd() {
        input.setTokensForPersist("T and");
        boolean parsed = instance.parse();
        instance.getParseTree().print();
        assertFalse(parsed);

        input.clear();
        input.append(EXISTENTIAL); input.append(TAUTOLOGY);
        assertFalse(instance.parse());
    }

    @Test
    public void anotherTest() {
        input.setTokensForPersist("blue ( a ) and ( exist w ( blue ( w ) and small ( w ) ) )");
        assertTrue(instance.parse());
        instance.getParseTree().print();
    }

    @Test
    public void anotherTestAgain() {
        printGrammar();
        input.setTokensForPersist("exist x ( small ( x ) and blue ( x ) ) and ( exist y ( blue ( y ) and medium ( y ) ) ) exist y");
        assertFalse(instance.parse());
        instance.getParseTree().print();

        input.clear();
        input.setTokensForPersist("( exist x ( small ( x ) and blue ( x ) ) and ( exist y ( blue ( y ) ) and medium ( y ) ) ) exist y");
        assertFalse(instance.parse());
    }
}
