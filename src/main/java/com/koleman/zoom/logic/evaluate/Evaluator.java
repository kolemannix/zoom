package com.koleman.zoom.logic.evaluate;

import com.koleman.zoom.logic.parse.FirstOrderLogicParser;
import com.koleman.zoom.logic.parse.ParseTree;
import com.koleman.zoom.logic.parse.Sentence;

/**
 * Author Koleman Nix
 * Created On 6/18/13
 */
public class Evaluator {

    private static int COUNT = 0;

    public static SentenceState evaluate(Sentence sentence) {
        System.out.println("The Evaluator has been summoned " + ++COUNT + " times");
        FirstOrderLogicParser parser = new FirstOrderLogicParser(sentence);
        boolean successfulParse = parser.parse();
        if (!successfulParse) {
            return SentenceState.INVALID;
        }
        ParseTree parseTree = parser.getParseTree();
        ASTBuilder astBuilder = new ASTBuilder(parseTree);
        AbstractSyntaxTree abstractSyntaxTree = astBuilder.makeTree();
        boolean result = abstractSyntaxTree.evaluate(sentence.getWorld());
        if (result) return SentenceState.TRUE;
        else return SentenceState.FALSE;
    }
}
