package com.koleman.zoom.logic.model.shape;

import com.koleman.zoom.logic.parse.token.Variable;

/**
 * Author Koleman Nix
 * Created On 3/19/13
 */
public interface ZoomShape {
    public Color getColor();
    public Size getSize();
    public Type getType();
    public long getID();
    public Variable getTag();
    public void setTag(Variable tag);
    public String encode();
}
