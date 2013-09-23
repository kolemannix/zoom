package com.koleman.zoom.ui.board;

import com.koleman.zoom.logic.model.shape.ZoomShape;
import com.koleman.zoom.logic.parse.token.Variable;
import com.koleman.zoom.ui.Resources;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/11/13
 * Assignment: Homework
 * Other Collaborators: None
 */
public class Tile extends AbsoluteLayout {
    private final int x, y;
    private boolean isSelected;
    private Variable variable;
    private Embedded embedded;
    private Label label;
    private static final String STYLE_NAME_SELECTED = "selected";

    private ZoomShape shape;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        embedded = new Embedded(null, Resources.getResourceForShape(null));
        label = new Label((String)null);
        setSizeFull();
        addComponent(embedded);
        addComponent(label, "left:20px;\n" +
                "top:20px;");


        isSelected = false;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public ZoomShape getShape() {
        return shape;
    }

    public void setShape(ZoomShape shape) {
        this.shape = shape;
        if (shape != null && shape.getTag() != null) label.setValue(shape.getTag().getSymbol().toUpperCase());
        else label.setValue(null);
        embedded.setSource(Resources.getResourceForShape(this.shape));
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        if (isSelected) {
            embedded.addStyleName(STYLE_NAME_SELECTED);
        } else {
            embedded.removeStyleName(STYLE_NAME_SELECTED);
        }
    }

    public boolean isSelected() {
        return this.isSelected;
    }
}
