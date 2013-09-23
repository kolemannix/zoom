package com.koleman.zoom.logic.evaluate;

import com.koleman.zoom.logic.parse.ParseTree;
import com.koleman.zoom.logic.parse.ParseTreeNode;
import com.koleman.zoom.logic.parse.token.*;

import java.util.List;

/**
 * Author Koleman Nix
 * Created On 6/1/13
 */
public class ASTBuilder {

    private ParseTree parseTree;
    private AbstractSyntaxTree resultTree;

    public ASTBuilder(ParseTree parseTree) {
        this.parseTree = parseTree;
    }

    public AbstractSyntaxTree makeTree() {
        resultTree = new AbstractSyntaxTree();
        try {
            resultTree.setRootNode(makeNode(parseTree.getRoot()));
        } catch (BadParseTreeException bpte) {
            // Swallow exception
            System.out.println("NOM NOM NOM");
        }
        return resultTree;
    }

    private ASTNode makeNode(ParseTreeNode node) throws BadParseTreeException {
        Token token = node.getToken();
        List<ParseTreeNode> siblings = node.getSiblings();
        ASTNode nodeToCreate;

        // Terminal Nodes
        if (token.equals(Misc.NEGATION)) {
            nodeToCreate = makeNode(node.getChildren().get(0));
            nodeToCreate.setNegated(true);
            return nodeToCreate;
        }

        if (token.equals(Misc.TAUTOLOGY)) {
            return new TautologyNode();
        }

        if (token.equals(Misc.CONTRADICTION)) {
            return new ContradictionNode();
        }

        if (token == Quantifier.UNIVERSAL) {
            Variable variableForArgument = (Variable)siblings.get(1).getToken();
            ASTNode child = makeNode(siblings.get(3));
            nodeToCreate = new UniversalNode(variableForArgument, child);
            return nodeToCreate;
        }

        if (token == Quantifier.EXISTENTIAL) {
            Variable variableForArgument = (Variable)siblings.get(1).getToken();
            ASTNode child = makeNode(siblings.get(3));
            nodeToCreate = new ExistentialNode(variableForArgument, child);
            return nodeToCreate;
        }

        if (token instanceof UnaryPredicate) {
            Token variableForArgument = siblings.get(2).getToken();
            if (!(variableForArgument instanceof Variable)) {
                throw new BadParseTreeException("Malformed Parse Tree");
            } else {
                nodeToCreate = new UnaryPredicateNode((UnaryPredicate)token);
                nodeToCreate.putVariable((Variable)variableForArgument);
            }
            return nodeToCreate;
        }
        if (token instanceof BinaryPredicate) {
            Token argumentOne = siblings.get(2).getToken();
            Token argumentTwo = siblings.get(3).getToken();
            if (!(argumentOne instanceof Variable)
                    && !(argumentTwo instanceof Variable)) {
                throw new BadParseTreeException("Malformed Parse Tree");
            } else {
                nodeToCreate = new BinaryPredicateNode((BinaryPredicate)token,
                        (Variable)argumentOne,
                        (Variable)argumentTwo);
            }
            return nodeToCreate;
        }
        if (token instanceof BinaryConnective) {
            BinaryConnective type = (BinaryConnective) token;
            ASTNode left = makeNode(siblings.get(0).getChildren().get(0));
            ASTNode right = makeNode(siblings.get(2).getChildren().get(0));
            nodeToCreate = new OperatorNode(type, left, right);
            return nodeToCreate;
        }

        /** Non-Terminal Nodes
         Find out if it has sibling nodes, for example a binary connective. If so, call this method on the binary connective node
         If it has no siblings, drill down into its first child and call this method on it  */
        if (siblings.size() == 3) {
            if (siblings.get(1).getToken() instanceof BinaryConnective) {
                return makeNode(siblings.get(1));
            }
            if ((siblings.get(0).getToken() == Misc.OPEN)
                    && (siblings.get(2).getToken() == Misc.CLOSE)) {
                return makeNode(siblings.get(1).getChildren().get(0));
            }
        } else {
            return makeNode(node.getChildren().get(0));
        }

        throw new BadParseTreeException("Unsupported node type");
    }
}
