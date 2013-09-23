package com.koleman.zoom.logic.model.world;

import com.koleman.zoom.logic.model.shape.ShapeFactory;
import com.koleman.zoom.logic.model.shape.ZoomShape;
import com.koleman.zoom.logic.parse.Sentence;
import com.koleman.zoom.logic.parse.token.Variable;

import javax.persistence.*;
import java.util.*;

/**
 * Author Koleman Nix
 * Created On 4/17/13
 *
 * We usin' 0-based indices up in here.
 */
@Entity
@Table(name = "worlds")
public class ZoomWorld {

    private ZoomShape[][] matrix;
    private Map<Variable, ZoomShape> currentPointers;
    private List<Sentence> sentences;
    private String name;

    private long id;

    /**
     * Creates an empty world
     */
    public ZoomWorld(String name) {
        this(name, null);
    }

    public ZoomWorld(ZoomShape[][] matrix) {
        this("Untitled", matrix);
    }

    public ZoomWorld(String name, ZoomShape[][] matrix) {
        this.name = name;
        sentences = new ArrayList<Sentence>();
        currentPointers = new HashMap<Variable, ZoomShape>();
        if (matrix == null) {
            this.matrix = new ZoomShape[8][8];
        } else {
            this.matrix = matrix;
        }
    }

    public ZoomWorld() {
        this("Untitled", null);
    }

    @Column(name = "world_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public ZoomShape getObjectForSymbol(Variable symbol) {
        return currentPointers.get(symbol);
    }

    @Transient
    public int getXCoordinate(ZoomShape shape) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (matrix[x][y] != null
                && matrix[x][y].getID() == shape.getID()) return x;
            }
        }
        return -1;
    }

    @Transient
    public int getYCoordinate(ZoomShape shape) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (matrix[x][y] != null
                        && matrix[x][y].getID() == shape.getID()) return y;
            }
        }
        return -1;
    }

    public void updatePointers() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ZoomShape shape = matrix[x][y];
                if (shape != null
                        && shape.getTag() != null) {
                    putPointer(shape.getTag(), shape);
                }
            }
        }
    }

    public void clearPointers() {
        currentPointers.clear();
    }

    public void putPointer(Variable symbol, ZoomShape zoomShape) {
        currentPointers.put(symbol, zoomShape);
    }

    public ZoomShape getShape(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return matrix[x][y];
        } else {
            throw new IndexOutOfBoundsException("Indices must be 0-7");
        }

    }

    public void setShape(ZoomShape shape, int x, int y) {
        matrix[x][y] = shape;
    }

    @Id
    @GeneratedValue
    @Column(name = "world_id")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "world_id")
    public List<Sentence> getSentences() {
        return this.sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    @Transient
    public ZoomShape[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrix(ZoomShape[][] matrix) {
        this.matrix = matrix;
    }

    @Column(name = "matrix", length = 512)
    public String getMatrixAsString() {
        return WorldEncoder.encode(this.matrix);
    }

    public void setMatrixAsString(String matrixAsString) {
        this.matrix = WorldEncoder.decode(matrixAsString);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ZoomWorld)) return false;
        ZoomWorld that = (ZoomWorld)o;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (this.getShape(x, y) == null) {
                    if (that.getShape(x, y) != null) {
                        return false;
                    }
                } else {
                    if (that.getShape(x, y) == null) return false;
                    if (!(that.getShape(x, y).equals(this.getShape(x, y)))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void randomize() {
        Random random = new Random();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (random.nextBoolean()) { // About half the time
                    setShape(ShapeFactory.generateRandomShape(), x, y);
                }
            }
        }
    }

    public static ZoomWorld generateRandomWorld() {
        ZoomWorld result = new ZoomWorld("Random World");
        result.randomize();
        return result;
    }

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
    }

    public boolean removeSentence(Sentence sentence) {
        return sentences.remove(sentence);
    }
}
