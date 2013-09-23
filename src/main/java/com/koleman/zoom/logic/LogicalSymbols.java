package com.koleman.zoom.logic;

import com.koleman.zoom.logic.parse.token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author Koleman Nix
 * Created On 6/11/13
 */
public class LogicalSymbols {

    private static final Map<String, Token> masterList;

    static {
        masterList = new TreeMap<String, Token>();
        for (BinaryConnective binaryConnective : BinaryConnective.values()) {
            masterList.put(binaryConnective.getPersistentRepresentation(), binaryConnective);
        }
        for (Quantifier quantifier : Quantifier.values()) {
            masterList.put(quantifier.getPersistentRepresentation(), quantifier);
        }
        for (Misc misc : Misc.values()) {
            masterList.put(misc.getPersistentRepresentation(), misc);
        }
        for (Variable variable : Variable.values()) {
            masterList.put(variable.getPersistentRepresentation(), variable);
        }
        for (UnaryPredicate unaryPredicate : UnaryPredicate.values()) {
            masterList.put(unaryPredicate.getPersistentRepresentation(), unaryPredicate);
        }
        for (BinaryPredicate binaryPredicate : BinaryPredicate.values()) {
            masterList.put(binaryPredicate.getPersistentRepresentation(), binaryPredicate);
        }
    }

    public static Token getTokenForSymbol(String symbol) {
        return masterList.get(symbol);
    }

    public static List<Token> getAllTokens() {
        return new ArrayList<Token>(masterList.values());
    }
}
