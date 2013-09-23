package com.koleman.zoom.ui;

import com.koleman.zoom.logic.model.shape.Color;
import com.koleman.zoom.logic.model.shape.Size;
import com.koleman.zoom.logic.model.shape.Type;
import com.koleman.zoom.logic.model.shape.ZoomShape;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/11/13
 * Assignment: Homework
 * Other Collaborators: None
 */
public class Resources {

    public static final String PRE = "img/shapes/";
    public static final String POST = ".png";

    public static final Resource board = new ThemeResource("img/board" + POST);

    private static final Resource SMALL_RED_TRIANGLE = new ThemeResource(PRE + "small_red_triangle" + POST);
    private static final Resource SMALL_RED_SQUARE = new ThemeResource(PRE + "small_red_square" + POST);
    private static final Resource SMALL_RED_CIRCLE = new ThemeResource(PRE + "small_red_circle" + POST);
    private static final Resource SMALL_GREEN_TRIANGLE = new ThemeResource(PRE + "small_green_triangle" + POST);
    private static final Resource SMALL_GREEN_SQUARE = new ThemeResource(PRE + "small_green_square" + POST);
    private static final Resource SMALL_GREEN_CIRCLE = new ThemeResource(PRE + "small_green_circle" + POST);
    private static final Resource SMALL_BLUE_TRIANGLE = new ThemeResource(PRE + "small_blue_triangle" + POST);
    private static final Resource SMALL_BLUE_SQUARE = new ThemeResource(PRE + "small_blue_square" + POST);
    private static final Resource SMALL_BLUE_CIRCLE = new ThemeResource(PRE + "small_blue_circle" + POST);

    private static final Resource MEDIUM_RED_TRIANGLE = new ThemeResource(PRE + "medium_red_triangle" + POST);
    private static final Resource MEDIUM_RED_SQUARE = new ThemeResource(PRE + "medium_red_square" + POST);
    private static final Resource MEDIUM_RED_CIRCLE = new ThemeResource(PRE + "medium_red_circle" + POST);
    private static final Resource MEDIUM_GREEN_TRIANGLE = new ThemeResource(PRE + "medium_green_triangle" + POST);
    private static final Resource MEDIUM_GREEN_SQUARE = new ThemeResource(PRE + "medium_green_square" + POST);
    private static final Resource MEDIUM_GREEN_CIRCLE = new ThemeResource(PRE + "medium_green_circle" + POST);
    private static final Resource MEDIUM_BLUE_TRIANGLE = new ThemeResource(PRE + "medium_blue_triangle" + POST);
    private static final Resource MEDIUM_BLUE_SQUARE = new ThemeResource(PRE + "medium_blue_square" + POST);
    private static final Resource MEDIUM_BLUE_CIRCLE = new ThemeResource(PRE + "medium_blue_circle" + POST);

    private static final Resource LARGE_RED_TRIANGLE = new ThemeResource(PRE + "large_red_triangle" + POST);
    private static final Resource LARGE_RED_SQUARE = new ThemeResource(PRE + "large_red_square" + POST);
    private static final Resource LARGE_RED_CIRCLE = new ThemeResource(PRE + "large_red_circle" + POST);
    private static final Resource LARGE_GREEN_TRIANGLE = new ThemeResource(PRE + "large_green_triangle" + POST);
    private static final Resource LARGE_GREEN_SQUARE = new ThemeResource(PRE + "large_green_square" + POST);
    private static final Resource LARGE_GREEN_CIRCLE = new ThemeResource(PRE + "large_green_circle" + POST);
    private static final Resource LARGE_BLUE_TRIANGLE = new ThemeResource(PRE + "large_blue_triangle" + POST);
    private static final Resource LARGE_BLUE_SQUARE = new ThemeResource(PRE + "large_blue_square" + POST);
    private static final Resource LARGE_BLUE_CIRCLE = new ThemeResource(PRE + "large_blue_circle" + POST);
    private static final Resource BLANK = new ThemeResource(PRE + "blank" + POST);

    public static Resource getResourceForShape(ZoomShape shape) {
        if (shape == null) return BLANK;
        if (shape.getSize() == Size.SMALL) {
            if (shape.getColor() == Color.RED) {
                if (shape.getType() == Type.TRIANGLE) {
                    return SMALL_RED_TRIANGLE;
                }
                if (shape.getType() == Type.SQUARE) {
                    return SMALL_RED_SQUARE;
                }
                if (shape.getType() == Type.CIRCLE) {
                    return SMALL_RED_CIRCLE;
                }
            }
            if (shape.getColor() == Color.BLUE) {
                if (shape.getType() == Type.TRIANGLE) {
                    return SMALL_BLUE_TRIANGLE;
                }
                if (shape.getType() == Type.SQUARE) {
                    return SMALL_BLUE_SQUARE;
                }
                if (shape.getType() == Type.CIRCLE) {
                    return SMALL_BLUE_CIRCLE;
                }
            }
            if (shape.getColor() == Color.GREEN) {
                if (shape.getType() == Type.TRIANGLE) {
                    return SMALL_GREEN_TRIANGLE;
                }
                if (shape.getType() == Type.SQUARE) {
                    return SMALL_GREEN_SQUARE;
                }
                if (shape.getType() == Type.CIRCLE) {
                    return SMALL_GREEN_CIRCLE;
                }
            }
        }
        if (shape.getSize() == Size.MEDIUM) {
            if (shape.getColor() == Color.RED) {
                if (shape.getType() == Type.TRIANGLE) {
                    return MEDIUM_RED_TRIANGLE;
                }
                if (shape.getType() == Type.SQUARE) {
                    return MEDIUM_RED_SQUARE;
                }
                if (shape.getType() == Type.CIRCLE) {
                    return MEDIUM_RED_CIRCLE;
                }
            }
            if (shape.getColor() == Color.BLUE) {
                if (shape.getType() == Type.TRIANGLE) {
                    return MEDIUM_BLUE_TRIANGLE;
                }
                if (shape.getType() == Type.SQUARE) {
                    return MEDIUM_BLUE_SQUARE;
                }
                if (shape.getType() == Type.CIRCLE) {
                    return MEDIUM_BLUE_CIRCLE;
                }
            }
            if (shape.getColor() == Color.GREEN) {
                if (shape.getType() == Type.TRIANGLE) {
                    return MEDIUM_GREEN_TRIANGLE;
                }
                if (shape.getType() == Type.SQUARE) {
                    return MEDIUM_GREEN_SQUARE;
                }
                if (shape.getType() == Type.CIRCLE) {
                    return MEDIUM_GREEN_CIRCLE;
                }
            }
        }
        if (shape.getSize() == Size.LARGE) {
            if (shape.getColor() == Color.RED) {
                if (shape.getType() == Type.TRIANGLE) {
                    return LARGE_RED_TRIANGLE;
                }
                if (shape.getType() == Type.SQUARE) {
                    return LARGE_RED_SQUARE;
                }
                if (shape.getType() == Type.CIRCLE) {
                    return LARGE_RED_CIRCLE;
                }
            }
            if (shape.getColor() == Color.BLUE) {
                if (shape.getType() == Type.TRIANGLE) {
                    return LARGE_BLUE_TRIANGLE;
                }
                if (shape.getType() == Type.SQUARE) {
                    return LARGE_BLUE_SQUARE;
                }
                if (shape.getType() == Type.CIRCLE) {
                    return LARGE_BLUE_CIRCLE;
                }
            }
            if (shape.getColor() == Color.GREEN) {
                if (shape.getType() == Type.TRIANGLE) {
                    return LARGE_GREEN_TRIANGLE;
                }
                if (shape.getType() == Type.SQUARE) {
                    return LARGE_GREEN_SQUARE;
                }
                if (shape.getType() == Type.CIRCLE) {
                    return LARGE_GREEN_CIRCLE;
                }
            }
        }
        return null;
    }
}
