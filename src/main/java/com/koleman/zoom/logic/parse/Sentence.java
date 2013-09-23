package com.koleman.zoom.logic.parse;

import com.koleman.zoom.logic.LogicalSymbols;
import com.koleman.zoom.logic.evaluate.Evaluator;
import com.koleman.zoom.logic.evaluate.SentenceState;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.token.Token;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Koleman Nix
 * Created On 3/22/13
 */
@Entity
@Table(name = "sentences")
public class Sentence {

    private List<Token> tokens;
    private long id;
    private ZoomWorld world;
    private SentenceState state;

    public Sentence() {
        tokens = new ArrayList<Token>();
    }

    @ManyToOne
    @JoinColumn(name = "world_id")
    public ZoomWorld getWorld() {
        return world;
    }

    public void setWorld(ZoomWorld world) {
        this.world = world;
    }

    @Id
    @GeneratedValue
    @Column(name = "sentence_id")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void append(Token token) {
        tokens.add(token);
    }

    public void remove(int index) {
        if (index < tokens.size()) {
            tokens.remove(index);
        }
    }

    public void removeLast() {
        if (!tokens.isEmpty()) tokens.remove(tokens.size() - 1);
    }

    public void clear() {
        tokens.clear();
    }

    @Transient
    public Token get(int index) {
        return tokens.get(index);
    }

    @Column(name = "tokens")
    public String getTokensForPersist() {
        StringBuilder sb = new StringBuilder();
        for (Token t : tokens) {
            sb.append(t.getPersistentRepresentation());
            sb.append(" ");
        }
        return sb.toString();
    }

    @Transient
    public String getTokensForDisplay() {
        if (tokens.isEmpty()) return "(No Tokens)";
        StringBuilder sb = new StringBuilder();
        for (Token t : tokens) {
            sb.append(t.getSymbol());
            sb.append(" ");
        }
        return sb.toString();
    }

    public void setTokensForPersist(String tokenString) {
        if (tokenString.trim().equals("")) {
            return;
        }
        tokens = new ArrayList<Token>();
        String[] tokenStrings = tokenString.split(" ");
        for (String symbol : tokenStrings) {
            tokens.add(LogicalSymbols.getTokenForSymbol(symbol));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Token t : tokens) {
            sb.append(t.getSymbol());
        }
        return sb.toString();
    }

    public int size() {
        return tokens.size();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sentence)) return false;
        Sentence that = (Sentence)o;
        return (that.tokens.equals(this.tokens));
    }

    @Transient
    public SentenceState getState() {
        if (this.world == null) return SentenceState.INVALID;
        if (this.state == null) {
            this.state = Evaluator.evaluate(this);
        }
        return state;
    }

    public void reEvaluate() {
        this.state = Evaluator.evaluate(this);
    }
}
