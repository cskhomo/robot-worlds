package za.co.wethinkcode.robots.server.UnitTests.robot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.geometry.Direction;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.state.Status;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    void testRobotInitialization() {
        Position pos = new Position(5, 10);
        Robot robot = new Robot("Robo", pos, 3, 3);

        // Check fields
        assertEquals("Robo", robot.name());
        assertEquals(pos, robot.position());
        assertEquals(Direction.NORTH, robot.direction());
        assertEquals(Status.NORMAL, robot.status());
        assertEquals(3, robot.shield().current());
        assertEquals(3, robot.ammo().current());
    }

    @Test
    void testToStringContainsAllInfo() {
        Position pos = new Position(1, 2);
        Robot robot = new Robot("Alpha", pos,3, 3);
        String str = robot.toString();

        assertTrue(str.contains("Alpha"));
        assertTrue(str.contains(pos.toString())); // Use the actual Position's string
        assertTrue(str.contains("NORMAL"));
        assertTrue(str.contains("NORTH"));
        assertTrue(str.contains("Shields"));
        assertTrue(str.contains("Ammo"));
    }
}
