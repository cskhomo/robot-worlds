package za.co.wethinkcode.robots.server.UnitTests.server.model.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.geometry.Direction;
import za.co.wethinkcode.robots.server.model.geometry.Position;

class PositionTest {

    @Test
    void testConstructor() {
        Position pos = new Position(5, 10);
        assertEquals(5, pos.X());
        assertEquals(10, pos.Y());
    }

    @Test
    void testStepNorth() {
        Position pos = new Position(2, 3);
        Position next = pos.step(Direction.NORTH);
        assertEquals(2, next.X());
        assertEquals(4, next.Y());
    }

    @Test
    void testStepSouth() {
        Position pos = new Position(2, 3);
        Position next = pos.step(Direction.SOUTH);
        assertEquals(2, next.X());
        assertEquals(2, next.Y());
    }

    @Test
    void testStepEast() {
        Position pos = new Position(2, 3);
        Position next = pos.step(Direction.EAST);
        assertEquals(3, next.X());
        assertEquals(3, next.Y());
    }

    @Test
    void testStepWest() {
        Position pos = new Position(2, 3);
        Position next = pos.step(Direction.WEST);
        assertEquals(1, next.X());
        assertEquals(3, next.Y());
    }

    @Test
    void testToString() {
        Position pos = new Position(7, 8);
        assertEquals("[7,8]", pos.toString());
    }
}
