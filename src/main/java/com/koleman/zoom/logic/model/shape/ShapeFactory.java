package com.koleman.zoom.logic.model.shape;

import com.koleman.zoom.logic.LogicalSymbols;
import com.koleman.zoom.logic.parse.token.Variable;

import java.util.Random;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public class ShapeFactory {

    private static int uidCounter = 0;

    public static ZoomShape createShape(Size size, Color color, Type type) {
        return new ZoomShapeImpl(size, color, type, uidCounter++);
    }

    public static ZoomShape decode(String encodedShape) {
        ZoomShape decodedShape = createShape(getSize(Character.getNumericValue(encodedShape.charAt(0))),
                getColor(Character.getNumericValue(encodedShape.charAt(1))),
                getType(Character.getNumericValue(encodedShape.charAt(2))));
        decodedShape.setTag((Variable)LogicalSymbols.getTokenForSymbol(Character.toString(encodedShape.charAt(3))));
        return decodedShape;
    }

    public static ZoomShape generateRandomShape() {
        Random random = new Random();
        return new ZoomShapeImpl(getSize(random.nextInt(3) + 1),
                getColor(random.nextInt(3) + 1),
                        getType(random.nextInt(3) + 1), uidCounter++);
    }

    public static Size getSize(int index) {
        switch (index) {
            case 1: return Size.SMALL;
            case 2: return Size.MEDIUM;
            case 3: return Size.LARGE;
        }
        return null;
    }

    public static Color getColor(int index) {
        switch (index) {
            case 1: return Color.RED;
            case 2: return Color.GREEN;
            case 3: return Color.BLUE;
        }
        return null;
    }

    public static Type getType(int index) {
        switch (index) {
            case 1: return Type.TRIANGLE;
            case 2: return Type.SQUARE;
            case 3: return Type.CIRCLE;
        }
        return null;
    }
}
