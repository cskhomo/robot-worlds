package za.co.wethinkcode.robots.server.UnitTests.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.state.Mine;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.obstacle.Obstacle;
import za.co.wethinkcode.robots.server.model.world.obstacle.MountainObstacle;
import za.co.wethinkcode.robots.server.model.world.obstacle.LakeObstacle;
import za.co.wethinkcode.robots.server.model.world.obstacle.PitObstacle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    private World world;

    @BeforeEach
    void setUp() {
        world = new World(new Dimension(10, 10));
    }

    @Test
    void testAddAndFindRobot() {
        Robot robot = new Robot("TestBot", new Position(1, 1), 3, 3);
        world.addRobot(robot);

        Robot found = world.findRobot("TestBot");
        assertNotNull(found);
        assertEquals("TestBot", found.name());
    }

    @Test
    void testRemoveRobot() {
        Robot robot = new Robot("RemoveBot", new Position(2, 2), 3, 3);
        world.addRobot(robot);

        assertNotNull(world.findRobot("RemoveBot"));
        world.removeRobot(robot);
        assertNull(world.findRobot("RemoveBot"));
    }

    @Test
    void testListRobots() {
        world.addRobot(new Robot("Alpha", new Position(0, 0), 3, 3));
        world.addRobot(new Robot("Beta", new Position(1, 1), 3, 3));
        world.addRobot(new Robot("Gamma", new Position(2, 2), 3, 3));

        List<Robot> robots = world.robots();
        assertNotNull(robots);
        assertEquals(3, robots.size());
    }

    @Test
    void testAddObstacles() {
        world.addObstacle("mountain", new Position(0, 0), new Position(1, 1));
        world.addObstacle("lake", new Position(2, 2), new Position(3, 3));
        world.addObstacle("pit", new Position(4, 4), new Position(5, 5));

        List<Obstacle> obstacles = world.obstacles();
        assertEquals(3, obstacles.size());
        assertTrue(obstacles.stream().anyMatch(o -> o instanceof MountainObstacle));
        assertTrue(obstacles.stream().anyMatch(o -> o instanceof LakeObstacle));
        assertTrue(obstacles.stream().anyMatch(o -> o instanceof PitObstacle));
    }

    @Test
    void testAddMines() {
        world.addMine(new Position(6, 6));
        world.addMine(new Position(7, 7));

        List<Mine> mines = world.mines();
        assertEquals(2, mines.size());
    }

    @Test
    void testRenameAndGetName() {
        assertNull(world.getName());
        world.rename("MyWorld");
        assertEquals("MyWorld", world.getName());
    }

    @Test
    void testResize() {
        world.resize(20, 30);
        assertEquals(20, world.dimension().width());
        assertEquals(30, world.dimension().height());
    }

    @Test
    void testVisibilityAndLimits() {
        assertEquals(3, world.visibility());
        assertEquals(4, world.maxAmmo());
        assertEquals(5, world.maxShields());
        assertEquals(3, world.repairTime());
        assertEquals(3, world.reloadTime());
    }
}
