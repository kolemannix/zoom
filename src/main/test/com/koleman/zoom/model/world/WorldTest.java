package com.koleman.zoom.model.world;

import com.koleman.zoom.logic.model.shape.*;
import com.koleman.zoom.logic.model.world.WorldEncoder;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author Koleman Nix
 * Created On 6/17/13
 */
public class WorldTest {

    @Test
    public void testEncode() {
        ZoomWorld world = new ZoomWorld();
        ZoomShape smallRedTriangle = ShapeFactory.createShape(Size.SMALL, Color.RED, Type.TRIANGLE);
        ZoomShape largeGreenSquare = ShapeFactory.createShape(Size.LARGE, Color.GREEN, Type.SQUARE);
        world.setShape(smallRedTriangle, 0, 0);
        world.setShape(largeGreenSquare, 5, 5);

        String encoded = WorldEncoder.encode(world.getMatrix());
        ZoomWorld decoded = new ZoomWorld(WorldEncoder.decode(encoded));

        assertEquals(world, decoded);
    }

}
