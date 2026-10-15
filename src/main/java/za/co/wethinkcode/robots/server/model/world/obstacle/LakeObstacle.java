package za.co.wethinkcode.robots.server.model.world.obstacle;

import za.co.wethinkcode.robots.server.model.geometry.Position;

/**
 * Lake obstacle that blocks movement but allows vision through.
 * Robots cannot move through lakes but can see across them.
 */
public class LakeObstacle extends Obstacle {

    /**
     * Creates a lake obstacle in the specified area.
     * 
     * @param topLeft the top-left corner position
     * @param bottomRight the bottom-right corner position
     */
    public LakeObstacle(Position topLeft, Position bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public boolean blocksVision() {
        return false;
    }

    @Override
    public boolean blocksPath() {
        return true;
    }

    @Override
    public String name() {
        return "Lake";
    }
}