package za.co.wethinkcode.robots.server.model.world.obstacle;

import za.co.wethinkcode.robots.server.model.geometry.Position;

/**
 * Mountain obstacle that blocks both vision and movement.
 * Represents impassable terrain in the game world.
 */
public class MountainObstacle extends Obstacle {

    /**
     * Creates a mountain obstacle in the specified area.
     * 
     * @param topLeft the top-left corner position
     * @param bottomRight the bottom-right corner position
     */
    public MountainObstacle(Position topLeft, Position bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public boolean blocksVision() {
        return true;
    }

    @Override
    public boolean blocksPath() {
        return true;
    }

    @Override
    public String name() {
        return "Mountain";
    }
}