package za.co.wethinkcode.robots.server.UnitTests.server.model.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.geometry.Position;

class DimensionTest {

    @Test
    void testConstructor_widthHeight() {
        Dimension dim = new Dimension(10, 20);
        assertEquals(10, dim.width());
        assertEquals(20, dim.height());
    }

    @Test
    void testConstructor_square() {
        Dimension dim = new Dimension(15);
        assertEquals(15, dim.width());
        assertEquals(15, dim.height());
    }

    @RepeatedTest(50)
    void testRandomPosition_withinBounds() {
        Dimension dim = new Dimension(10, 8);
        Position pos = dim.randomPosition();

        int halfWidth = dim.width() / 2;
        int halfHeight = dim.height() / 2;

        // Use X() and Y() because Position is a record
        assertTrue(pos.X() >= -halfWidth && pos.X() <= halfWidth,
                "X position out of bounds");
        assertTrue(pos.Y() >= -halfHeight && pos.Y() <= halfHeight,
                "Y position out of bounds");
    }

    @Test
    void testCapacity() {
        Dimension dim = new Dimension(10, 8);
        int expectedCapacity = (10 / 2 * 2 + 1) * (8 / 2 * 2 + 1);
        assertEquals(expectedCapacity, dim.capacity());
    }

    @Test
    void testToString() {
        Dimension dim = new Dimension(12, 7);
        String expected = "12x7";
        assertEquals(expected, dim.toString());
    }
}
