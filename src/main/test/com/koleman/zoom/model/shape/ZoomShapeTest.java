package com.koleman.zoom.model.shape;

import com.koleman.zoom.logic.model.shape.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Author Koleman Nix
 * Created On 4/16/13
 */
public class ZoomShapeTest {

    private ZoomShape smallRedSquare;
    private ZoomShape bigGreenTriangle;

    @Before
    public void setup() {
        smallRedSquare = ShapeFactory.createShape(Size.SMALL, Color.RED, Type.SQUARE);
        bigGreenTriangle = ShapeFactory.createShape(Size.LARGE, Color.GREEN, Type.TRIANGLE);
    }

    @Test
    public void testGetters() {
        assertEquals(smallRedSquare.getColor(), Color.RED);
        assertEquals(smallRedSquare.getType(), Type.SQUARE);
        assertEquals(smallRedSquare.getSize(), Size.SMALL);

        assertEquals(bigGreenTriangle.getColor(), Color.GREEN);
        assertEquals(bigGreenTriangle.getType(), Type.TRIANGLE);
        assertEquals(bigGreenTriangle.getSize(), Size.LARGE);
    }

    @Test
    public void testEquals() {
        ZoomShape newOne = ShapeFactory.createShape(Size.LARGE, Color.GREEN, Type.TRIANGLE);
        assertTrue(newOne.equals(bigGreenTriangle));

        newOne = ShapeFactory.createShape(Size.LARGE, Color.GREEN, Type.SQUARE);
        assertFalse(bigGreenTriangle.equals(newOne));
    }

    @Test
    public void testEncode() {
        String smallRedSquareString = "1120";
        assertEquals(smallRedSquare, ShapeFactory.decode(smallRedSquareString));
        assertEquals(smallRedSquareString, smallRedSquare.encode());

        String bigGreenTriangleString = "3210";
        assertEquals(bigGreenTriangle, ShapeFactory.decode(bigGreenTriangleString));
        assertEquals(bigGreenTriangleString, bigGreenTriangle.encode());
    }
}
