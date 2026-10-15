package za.co.wethinkcode.robots.server.UnitTests.server.model.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.geometry.Rectangle;

class RectangleTest {

    @Test
    void testWidthHeight() {
        Position topLeft = new Position(0, 10);
        Position bottomRight = new Position(5, 0);
        Rectangle rect = new Rectangle(topLeft, bottomRight);

        assertEquals(5, rect.width());
        assertEquals(10, rect.height());
    }

    @Test
    void testContainsInside() {
        Rectangle rect = new Rectangle(new Position(0, 10), new Position(5, 0));
        Position inside = new Position(3, 5);
        assertTrue(rect.contains(inside));
    }

    @Test
    void testContainsOnEdges() {
        Rectangle rect = new Rectangle(new Position(0, 10), new Position(5, 0));
        Position topLeftEdge = new Position(0, 10);
        Position bottomRightEdge = new Position(5, 0);
        Position topRightEdge = new Position(5, 10);
        Position bottomLeftEdge = new Position(0, 0);

        assertTrue(rect.contains(topLeftEdge));
        assertTrue(rect.contains(bottomRightEdge));
        assertTrue(rect.contains(topRightEdge));
        assertTrue(rect.contains(bottomLeftEdge));
    }

    @Test
    void testContainsOutside() {
        Rectangle rect = new Rectangle(new Position(0, 10), new Position(5, 0));
        Position outside1 = new Position(-1, 5);
        Position outside2 = new Position(3, 11);
        Position outside3 = new Position(6, 5);
        Position outside4 = new Position(3, -1);

        assertFalse(rect.contains(outside1));
        assertFalse(rect.contains(outside2));
        assertFalse(rect.contains(outside3));
        assertFalse(rect.contains(outside4));
    }
}
