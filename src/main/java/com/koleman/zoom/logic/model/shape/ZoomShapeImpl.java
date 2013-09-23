package com.koleman.zoom.logic.model.shape;

import com.koleman.zoom.logic.parse.token.Variable;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public class ZoomShapeImpl implements ZoomShape {

    private final Color color;
    private final Size size;
    private final Type shape;
    private Variable tag;
    private long id;


    protected ZoomShapeImpl(Size size, Color color, Type type, long id) {
        this.size = size;
        this.color = color;
        this.shape = type;
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    public Type getType() {
        return shape;
    }

    public long getID() {
        return id;
    }

    @Override
    public Variable getTag() {
        return this.tag;
    }

    public void setTag(Variable tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZoomShapeImpl that = (ZoomShapeImpl) o;

        if (color != that.color) return false;
        if (shape != that.shape) return false;
        if (size != that.size) return false;
        if (tag != that.tag) return false;
        return true;
    }

    @Override
    public String toString() {
        return this.size + " " + this.color + " " + this.shape;
    }

    @Override
    public String encode() {
        return ("" + size.getIndex() + color.getIndex() + shape.getIndex() + ((tag == null) ? "0" : tag.getSymbol()));
    }
}
