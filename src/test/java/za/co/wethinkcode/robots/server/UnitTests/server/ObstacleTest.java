package za.co.wethinkcode.robots.server.UnitTests.server;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.world.obstacle.Obstacle;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    // Concrete subclass for testing purposes
    static class TestObstacle extends Obstacle {
        public TestObstacle(Position topLeft, Position bottomRight) {
            super(topLeft, bottomRight);
        }

        @Override
        public boolean blocksVision() {
            return true;
        }

        @Override
        public boolean blocksPath() {
            return false;
        }

        @Override
        public String name() {
            return "TestObs";
        }
    }

    @Test
    void testPositionAndDimensions() {
        Position topLeft = new Position(1, 2);
        Position bottomRight = new Position(4, 6);
        Obstacle obs = new TestObstacle(topLeft, bottomRight);

        assertEquals(topLeft, obs.topLeft());
        assertEquals(bottomRight, obs.bottomRight());
        assertEquals(4 - 1, obs.width());
        assertEquals(6 - 2, obs.height());
    }

    @Test
    void testBlockProperties() {
        Obstacle obs = new TestObstacle(new Position(0, 0), new Position(2, 3));
        assertTrue(obs.blocksVision());
        assertFalse(obs.blocksPath());
    }

    @Test
    void testNameAndToString() {
        Obstacle obs = new TestObstacle(new Position(0, 0), new Position(2, 3));

        // Check name
        assertEquals("TestObs", obs.name());

        // Check toString contains the name and both positions
        String str = obs.toString();
        assertTrue(str.contains("TestObs"), "toString should contain obstacle name");
        assertTrue(str.contains("0") && str.contains("2"), "toString should contain top-left and bottom-right X values");
        assertTrue(str.contains("0") && str.contains("3"), "toString should contain top-left and bottom-right Y values");
    }

}
