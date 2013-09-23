package com.koleman.zoom.evaluate;

import com.koleman.zoom.logic.evaluate.ASTBuilder;
import com.koleman.zoom.logic.evaluate.AbstractSyntaxTree;
import com.koleman.zoom.logic.parse.FirstOrderLogicParser;
import com.koleman.zoom.logic.parse.Sentence;
import com.koleman.zoom.logic.parse.token.BinaryConnective;
import com.koleman.zoom.logic.parse.token.BinaryPredicate;
import org.junit.Before;
import org.junit.Test;

import static com.koleman.zoom.logic.parse.token.Misc.*;
import static com.koleman.zoom.logic.parse.token.Quantifier.EXISTENTIAL;
import static com.koleman.zoom.logic.parse.token.Quantifier.UNIVERSAL;
import static com.koleman.zoom.logic.parse.token.UnaryPredicate.*;
import static com.koleman.zoom.logic.parse.token.Variable.X;
import static com.koleman.zoom.logic.parse.token.Variable.Y;

/**
 * Author Koleman Nix
 * Created On 6/6/13
 */
public class ASTBuilderTest {

    private Sentence input;
    private ASTBuilder instance;
    private FirstOrderLogicParser parser;

    @Before
    public void setup() {
        input = new Sentence();
        parser = new FirstOrderLogicParser(input);

    }

    @Test
    public void basicTest() {
        input.append(BLUE);
        input.append(OPEN); input.append(X); input.append(CLOSE);
        parser.parse();
        instance = new ASTBuilder(parser.getParseTree());
        AbstractSyntaxTree result = instance.makeTree();
        System.out.println(result.print());
    }

    @Test
    public void basicTest2() {
        input.append(EXISTENTIAL); input.append(X);
        input.append(OPEN);
        input.append(SMALL);
        input.append(OPEN); input.append(X); input.append(CLOSE);
        input.append(CLOSE);
        parser.parse();
        parser.getParseTree().print();
        instance = new ASTBuilder(parser.getParseTree());
        AbstractSyntaxTree result = instance.makeTree();
        System.out.println(result.print());
    }

    @Test
    public void biggerTest() {
        // Input: ∀x(∃y(Red(y) -> Smaller(x, y)));
        input.append(UNIVERSAL); input.append(X); input.append(OPEN);
        input.append(EXISTENTIAL); input.append(Y); input.append(OPEN);
        input.append(RED); input.append(OPEN); input.append(Y); input.append(CLOSE); // Red(y)
        input.append(BinaryConnective.CONDITIONAL); // ^
        input.append(BinaryPredicate.SMALLER); // Smaller(x, y)
        input.append(OPEN); input.append(X); input.append(Y);
        input.append(CLOSE); input.append(CLOSE); input.append(CLOSE);
        parser.parse();
        parser.getParseTree().print();
        instance = new ASTBuilder(parser.getParseTree());
        AbstractSyntaxTree result = instance.makeTree();
        System.out.println(result.print());
    }

    @Test
    public void biggestTest() {
        input.append(UNIVERSAL); input.append(X); input.append(OPEN);
        input.append(OPEN); input.append(RED); input.append(OPEN); input.append(X); input.append(CLOSE);
        input.append(BinaryConnective.BICONDITIONAL); input.append(GREEN); input.append(OPEN);
        input.append(X); input.append(CLOSE); input.append(CLOSE);
        input.append(BinaryConnective.CONDITIONAL);
        input.append(OPEN); input.append(EXISTENTIAL); input.append(Y); input.append(OPEN);
        input.append(BinaryPredicate.SMALLER); input.append(OPEN);
        input.append(Y); input.append(X); input.append(CLOSE); input.append(BinaryConnective.OR);
        input.append(BLUE); input.append(OPEN); input.append(Y);
        input.append(CLOSE); input.append(CLOSE); input.append(CLOSE); input.append(CLOSE);
        parser.parse();
        parser.getParseTree().print();
        instance = new ASTBuilder(parser.getParseTree());
        AbstractSyntaxTree result = instance.makeTree();
        System.out.println(result.print());
    }

    @Test
    public void testSomething() {
        input.append(OPEN); input.append(CONTRADICTION); input.append(CLOSE);
        parser.parse();
        parser.getParseTree().print();
        instance = new ASTBuilder(parser.getParseTree());
        AbstractSyntaxTree result = instance.makeTree();
        System.out.println(result.print());
    }

    @Test
    public void testNegation() {
        input.setTokensForPersist("~ T");
        parser.parse();
        parser.getParseTree().print();
        instance = new ASTBuilder(parser.getParseTree());
        AbstractSyntaxTree result = instance.makeTree();
        System.out.println(result.print());
    }
}
