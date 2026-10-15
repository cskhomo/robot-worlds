package za.co.wethinkcode.robots.server.model.geometry;

/**
 * Represents a rectangular area defined by top-left and bottom-right corners.
 * Used for defining obstacle boundaries and world regions.
 */
public record Rectangle(Position topLeft, Position bottomRight) {


    /**
     * Checks if a position is inside the rectangle using world coordinates.
     *
     * @param pos the position to check
     * @return true if inside, false if outside
     */
    public boolean contains(Position pos) {
        int x = pos.X();
        int y = pos.Y();

        boolean withinX = x >= topLeft.X() && x <= bottomRight.X();
        boolean withinY = y <= topLeft.Y() && y >= bottomRight.Y();

        return withinX && withinY;
    }

    /**
     * Calculates the width of the rectangle.
     * 
     * @return the absolute width between left and right edges
     */
    public int width() {
        return Math.abs(bottomRight.X() - topLeft.X());
    }
    
    /**
     * Calculates the height of the rectangle.
     * 
     * @return the absolute height between top and bottom edges
     */
    public int height() {
        return Math.abs(bottomRight.Y() - topLeft.Y());
    }

}

//
//    /**
//     * Checks if a position is inside this rectangle (inclusive).
//     */
//    public boolean contains(Position pos) {
//        return pos.getX() >= topLeft.getX() &&
//               pos.getX() <= bottomRight.getX() &&
//               pos.getY() <= topLeft.getY() &&
//               pos.getY() >= bottomRight.getY();
//    }
//
//    /**
//     * Optional helper: returns the total number of integer positions in this rectangle.
//     */
//    public int area() {
//        return (getWidth() + 1) * (getHeight() + 1);
//    }
//}
