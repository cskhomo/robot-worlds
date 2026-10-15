package za.co.wethinkcode.robots.server.model.world.obstacle;

import za.co.wethinkcode.robots.server.model.geometry.Rectangle;
import za.co.wethinkcode.robots.server.model.geometry.Position;

/**
 * Abstract base class for all obstacles in the game world.
 * Obstacles can block vision and/or movement depending on their type.
 */
public abstract class Obstacle {
    private final Rectangle shape;

    /**
     * Creates an obstacle with the specified rectangular area.
     * 
     * @param topLeft the top-left corner position
     * @param bottomRight the bottom-right corner position
     */
    protected Obstacle(Position topLeft, Position bottomRight) {
        this.shape = new Rectangle(topLeft, bottomRight);
    }

    /** @return the top-left corner position */
    public Position topLeft() { return shape.topLeft(); }
    
    /** @return the bottom-right corner position */
    public Position bottomRight() { return shape.bottomRight(); }

    /** @return the width of the obstacle */
    public int width() { return shape.width(); }
    
    /** @return the height of the obstacle */
    public int height() { return shape.height(); }

    /** @return true if this obstacle blocks vision */
    public abstract boolean blocksVision();
    
    /** @return true if this obstacle blocks movement */
    public abstract boolean blocksPath();

    /** @return the name of this obstacle type */
    public abstract String name();

    @Override
    public String toString() {
        return " • %-8s : %-6s → %s".formatted(name(), topLeft(), bottomRight());
    }

}

//    public boolean isPositionInside(Position position) {
//        Position topLeftCorner = getTopLeftCorner();
//        Position bottomRightCorner = getBottomRightCorner();
//
//        // Check if the X and Y coordinates are within the obstacle's boundaries
//        boolean inBetweenX = (position.getX() >= topLeftCorner.getX()) && (position.getX() <= bottomRightCorner.getX());
//        boolean inBetweenY = (position.getY() >= topLeftCorner.getY()) && (position.getY() <= bottomRightCorner.getY());
//        return inBetweenX && inBetweenY;
//    }
//
//    /**
//     * Checks if a movement from one point to another goes through this obstacle.
//     *
//     * @param src - starting position
//     * @param dst - ending position
//     * @return true if the line from src to dst crosses the obstacle
//     */
//    public boolean interceptsPath(Position src, Position dst) {
//        Position pointOfIntersectionA;
//        Position pointOfIntersectionB;
//
//        // If movement is horizontal (same Y), we check X direction.
//        if (src.getY() == dst.getY()) {
//            pointOfIntersectionA = new Position(this.TOP_LEFT_CORNER.getX(), src.getY());
//            pointOfIntersectionB = new Position(this.BOTTOM_RIGHT_CORNER.getX(), src.getY());
//        } else {
//            // Otherwise, we assume movement is vertical and check Y direction.
//            pointOfIntersectionA = new Position(src.getX(), this.TOP_LEFT_CORNER.getY());
//            pointOfIntersectionB = new Position(src.getX(), this.BOTTOM_RIGHT_CORNER.getY());
//        }
//
//        // Check if either of those points fall inside the obstacle.
//        boolean pointAIntersect = pointOfIntersectionA.isIn(this.TOP_LEFT_CORNER, this.BOTTOM_RIGHT_CORNER);
//        boolean pointBIntersect = pointOfIntersectionB.isIn(this.TOP_LEFT_CORNER, this.BOTTOM_RIGHT_CORNER);
//
//        return pointAIntersect || pointBIntersect;
//    }
//
//    /**
//     * Tells us whether we can see through this obstacle.
//     * Default is false (we cannot see through).
//     */
//    public boolean isSeeThrough() { return false; }
//
//    /**
//     * Tells us whether we can walk over this obstacle.
//     * Default is false (we cannot walk over).
//     */
//    public boolean canWalkOver() { return false; }
//
//    // These methods return the corners of the obstacle.
//    public Position getTopLeftCorner() { return this.TOP_LEFT_CORNER; }
//    public Position getBottomRightCorner() { return this.BOTTOM_RIGHT_CORNER; }
//}
