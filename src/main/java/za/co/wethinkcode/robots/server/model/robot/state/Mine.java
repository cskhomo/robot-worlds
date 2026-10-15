package za.co.wethinkcode.robots.server.model.robot.state;

import za.co.wethinkcode.robots.server.model.geometry.Position;

/**
 * Represents a mine placed in the game world.
 * Mines are explosive devices that can damage robots.
 */
public class Mine {

    private final Position position;

    /**
     * Creates a mine at the specified position.
     * 
     * @param position the position where the mine is placed
     */
    public Mine(Position position) {
        this.position = position;
    }

    /**
     * Returns the position of this mine.
     * 
     * @return the mine's position
     */
    public Position position() {
        return position;
    }

    @Override
    public String toString() {
        return "Mine at " + position;
    }
}
