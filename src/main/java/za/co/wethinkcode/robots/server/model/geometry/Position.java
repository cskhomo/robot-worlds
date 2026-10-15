package za.co.wethinkcode.robots.server.model.geometry;

import java.text.MessageFormat;

/**
 * Represents a 2D coordinate position with X and Y values.
 * Used for robot positions, obstacle placement, and world boundaries.
 */
public record Position(int X, int Y) {

    /**
     * Returns a new Position one step in the given direction.
     *
     * @param direction the direction to move
     * @return a new Position one step away
     */
    public Position step(Direction direction) {
        return switch (direction) {
            case NORTH -> new Position(X, Y + 1);
            case SOUTH -> new Position(X, Y - 1);
            case EAST  -> new Position(X + 1, Y);
            case WEST  -> new Position(X - 1, Y);
        };
    }

    /**
     * Returns a formatted string representation of the position.
     * 
     * @return position as "[X,Y]" format
     */
    @Override
    public String toString() {
        return MessageFormat.format("[{0},{1}]", this.X, this.Y);
    }
}