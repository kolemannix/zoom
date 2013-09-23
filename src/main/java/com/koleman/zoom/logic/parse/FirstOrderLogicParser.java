package com.koleman.zoom.logic.parse;

import com.koleman.zoom.logic.parse.token.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.koleman.zoom.logic.parse.token.BinaryConnective.*;
import static com.koleman.zoom.logic.parse.token.Misc.*;
import static com.koleman.zoom.logic.parse.token.NonTerminal.*;
import static com.koleman.zoom.logic.parse.token.Quantifier.EXISTENTIAL;
import static com.koleman.zoom.logic.parse.token.Quantifier.UNIVERSAL;

/**
 * Author Koleman Nix
 * Created On 3/19/13
 */
public class FirstOrderLogicParser {

    private Sentence input;
    private ParseTree tree;
    private ParseTreeNode parseTreeCursor;
    private int current;

    public static final Map<NonTerminal, String> GRAMMAR = new TreeMap<NonTerminal, String>();
    static {
        GRAMMAR.put(SENTENCE,
                "S  -> ∃x(S)\n" +
                "    | ∀x(S)\n" +
                "    | E");
        GRAMMAR.put(EXPRESSION,
                "E  -> T E'");
        GRAMMAR.put(E_PRIME,
                "E' -> ^   T E`\n" +
                "    | v   T E`\n" +
                "    | ->  T E`\n" +
                "    | <-> T E`\n" +
                "    | epsilon");
        GRAMMAR.put(TERM,
                "T  -> UP | BP\n" +
                "    | ~S     \n" +
                "    | (S)    \n" +
                "    | T | F  ");
        GRAMMAR.put(UNARY_PREDICATE,
                "UP -> Pred(x)\n"
              + "      where x is a valid variable");
        GRAMMAR.put(BINARY_PREDICATE,
                "BP -> Pred(xy)\n"
              + "      where x is a valid variable\n"
              + "      and   y is a valid variable");

    }

    private static final Map<NonTerminal, Production> productions = new HashMap<NonTerminal, Production>();
    /**
     * Performs the "Sentence" production as well as modifying the tree transactionally
     * (i.e., if this method ever were to return false, it will undo all changes that it made directly
     * or indirectly (through calling other methods) to the parse tree.
     * @return The result of the sentence production
     * S -> ∃x(S)
     *    | ∀x(S)
     *    | E
     * where x is a valid variable token
     */
    private final Production sentenceProduction;
    /**
     * Performs the "Expression" production transactionally.
     * (i.e., if this method ever were to return false, it will undo all changes that it made directly
     * or indirectly (through calling other methods) to the parse tree.
     * @return The result of the expression production
     */
    private final Production expressionProduction;
    /**
     * Represents the following production:
     * E` -> ^   T E`
     *     | v   T E`
     *     | ->  T E`
     *     | <-> T E`
     *     | epsilon
     */
    private final Production ePrimeProduction;
    /**
     * Represents the following production:
     * T -> UP | BP
     *    | ~S
     *    | (S)
     *    | T | F
     */
    private final Production termProduction;
    private final Production unaryPredicateProduction;
    private final Production binaryPredicateProduction;

    public FirstOrderLogicParser(Sentence tokens) {
        this.input = tokens;
        sentenceProduction = new Production() {
            @Override
            public boolean perform() {
                int saveIndex = current;
                // Condition One
                if (inputMatchesToken(EXISTENTIAL) && inputMatchesVariable() && inputMatchesToken(OPEN)
                        && performProduction(SENTENCE) && inputMatchesToken(CLOSE)) {
                    return true;
                }
                current = saveIndex;

                // Condition Two
                if (inputMatchesToken(UNIVERSAL) && inputMatchesVariable() && inputMatchesToken(OPEN)
                        && performProduction(SENTENCE) && inputMatchesToken(CLOSE)) {
                    return true;
                }
                current = saveIndex;

                // Condition Three
                if (performProduction(EXPRESSION)) {
                    return true;
                }
                current = saveIndex;
                return false;
            }
        };
        expressionProduction = new Production() {
            @Override
            public boolean perform() {
                int saveIndex = current;
                if (performProduction(TERM) && ePrimeProduction.perform()) {
                    return true;
                }
                // Unsuccessful parse attempt. Rollback the changes, resetting pointer
                current = saveIndex;
                return false;
            }
        };
        ePrimeProduction = new Production() {
            @Override
            public boolean perform() {
                int save = current;
                // Condition One
                if (inputMatchesToken(AND) && performProduction(TERM) && ePrimeProduction.perform()) {
                    return true;
                }
                current = save;
                // Condition Two
                if (inputMatchesToken(OR) && performProduction(TERM) && ePrimeProduction.perform()) {
                    return true;
                }
                current = save;
                // Condition Three
                if (inputMatchesToken(CONDITIONAL) && performProduction(TERM) && ePrimeProduction.perform()) {
                    return true;
                }
                current = save;
                // Condition Four
                if (inputMatchesToken(BICONDITIONAL) && performProduction(TERM) && ePrimeProduction.perform()) {
                    return true;
                }
                current = save;
                return true;
            }
        };
        termProduction = new Production() {
            @Override
            public boolean perform() {
                int saveIndex = current;
                if (performProduction(UNARY_PREDICATE)) {
                    return true;
                }
                current = saveIndex;
                if (performProduction(BINARY_PREDICATE)) {
                    return true;
                }
                current = saveIndex;
                if (inputMatchesToken(NEGATION) && performProduction(SENTENCE)) {
                    return true;
                }
                current = saveIndex;
                if (inputMatchesToken(OPEN) && performProduction(SENTENCE) && inputMatchesToken(CLOSE)) {
                    return true;
                }
                current = saveIndex;
                if (inputMatchesToken(TAUTOLOGY)) {
                    return true;
                }
                current = saveIndex;
                if (inputMatchesToken(CONTRADICTION)) {
                    return true;
                }
                current = saveIndex;
                return false;
            }
        };
        unaryPredicateProduction = new Production() {
            @Override
            public boolean perform() {
                int saveIndex = current;
                if (inputMatchesPredicate()
                        && inputMatchesToken(OPEN) && inputMatchesVariable()
                        && inputMatchesToken(CLOSE)) {
                    return true;
                }
                current = saveIndex;
                return false;
            }
        };
        binaryPredicateProduction = new Production() {
            @Override
            public boolean perform() {
                int saveIndex = current;
                if (inputMatchesPredicate()
                        && inputMatchesToken(OPEN) && inputMatchesVariable() && inputMatchesVariable()
                        && inputMatchesToken(CLOSE)) {
                    return true;
                }
                current = saveIndex;
                return false;
            }
        };
        productions.put(SENTENCE, sentenceProduction);
        productions.put(EXPRESSION, expressionProduction);
        productions.put(E_PRIME, ePrimeProduction);
        productions.put(TERM, termProduction);
        productions.put(UNARY_PREDICATE, unaryPredicateProduction);
        productions.put(BINARY_PREDICATE, binaryPredicateProduction);
    }

    public void setInput(Sentence input) {
        this.input = input;
    }

    public ParseTree getParseTree() {
        return this.tree;
    }

    /**
     *
     */
    public boolean parse() {
        tree = new ParseTree();
        tree.setRoot(new ParseTreeNode(SENTENCE));

        current = 0;
        parseTreeCursor = tree.getRoot();

        boolean validTreeConstructed = performProduction(SENTENCE);
        boolean endOfInputReached = current == input.size();
        boolean finalAnswer = validTreeConstructed && endOfInputReached;

        System.out.println("Parsed Input: " + input.toString()
                + " with result (" + current + "/" + input.size()
                + ") " + finalAnswer);

        return finalAnswer;
    }

    private boolean performProduction(NonTerminal nonTerminal) {
        ParseTreeNode save = parseTreeCursor;
        ParseTreeNode newNode = new ParseTreeNode(nonTerminal);
        newNode.setParent(parseTreeCursor);
        parseTreeCursor.addChild(newNode);
        parseTreeCursor = newNode;
        boolean result = productions.get(nonTerminal).perform();
        if (!result) { // Roll back changes to parse tree
            save.removeChild(newNode);
            parseTreeCursor = save;
        } else {
            parseTreeCursor = parseTreeCursor.getParent();
        }
        return result;
    }

    private boolean inputMatchesPredicate() {
        if (current >= input.size()) {
            return false;
        }
        boolean val = ((input.get(current)) instanceof Predicate);
        if (val) {
            advancePointer();
        }
        return val;
    }

    private boolean inputMatchesVariable() {
        if (current >= input.size()) {
            return false;
        }
        boolean val = input.get(current) instanceof Variable;
        if (val) {
            advancePointer();
        }
        return val;
    }

    private void advancePointer() {
        ParseTreeNode newNode = new ParseTreeNode((input.get(current)));
        newNode.setParent(parseTreeCursor);
        parseTreeCursor.addChild(newNode);
        if (input.get(current).equals(Misc.NEGATION)) {
            // This serves to put negation on a branch of its own
            // The resulting parse tree is worlds easier to evaluate. This way
            // everything that should be negated by the negation symbol is
            // a child of the negation node, making evaluation a breeze
            parseTreeCursor = newNode;
        }
        current++;
    }

    public int getPointer() {
        return this.current;
    }

    public void setPointer(int index) {
        this.current = index;
    }

    private boolean inputMatchesToken(Token t) {
        if (current >= input.size()) {
            return false;
        }
        boolean val = t.equals(input.get(current));
        if (val) {
            advancePointer();
        }
        return val;
    }

    private interface Production {
        boolean perform();
    }

}
