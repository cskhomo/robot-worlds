package za.co.wethinkcode.robots.server.model.world.obstacle;

import za.co.wethinkcode.robots.server.model.geometry.Position;

/**
 * Pit obstacle that allows both vision and movement.
 * Represents dangerous terrain that robots can fall into.
 */
public class PitObstacle extends Obstacle {

    /**
     * Creates a pit obstacle in the specified area.
     * 
     * @param topLeft the top-left corner position
     * @param bottomRight the bottom-right corner position
     */
    public PitObstacle(Position topLeft, Position bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public boolean blocksVision() {
        return false;
    }

    @Override
    public boolean blocksPath() {
        return false;
    }

    @Override
    public String name() {
        return "Pit";
    }
}

//    /**
//     * If a robot walks over the pit, it "dies" (is removed or deactivated).
//     *
//     * @param robot - the robot that walked over the pit
//     */
//    public static void walkOver(Robot robot) {
//        robot.die(); // This kills the robot.
//    }
//}
//
