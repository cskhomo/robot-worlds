package za.co.wethinkcode.robots.server.model.geometry;

/**
 * Represents the four cardinal directions for robot movement and orientation.
 * Used to determine robot facing direction and movement calculations.
 */
public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }
}