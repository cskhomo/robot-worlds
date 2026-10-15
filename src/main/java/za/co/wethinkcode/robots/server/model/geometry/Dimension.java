package za.co.wethinkcode.robots.server.model.geometry;

import java.text.MessageFormat;
import java.util.Random;

/**
 * Represents 2D dimensions with width and height values.
 * Used for defining world size and rectangular areas.
 */
public record Dimension(int width, int height) {

    private static final Random RANDOM = new Random();

    /**
     * Creates a square dimension with equal width and height.
     * 
     * @param size the size for both width and height
     */
    public Dimension(int size) {
        this(size, size);
    }

    /**
     * Returns a random valid whole-number position within the world bounds.
     * Center is at (0,0), edges are at ±width/2 and ±height/2.
     *
     * @return a random Position within bounds
     */
    public Position randomPosition() {

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        int x = RANDOM.nextInt(halfWidth * 2 + 1) - halfWidth;
        int y = RANDOM.nextInt(halfHeight * 2 + 1) - halfHeight;

        return new Position(x, y);
    }

    /**
     * Returns the total number of valid whole-number positions in this dimension.
     * Positions range from -width/2 to +width/2 and -height/2 to +height/2.
     *
     * @return total capacity (number of possible positions)
     */
    public int capacity() {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        return (halfWidth * 2 + 1) * (halfHeight * 2 + 1);
    }


    /**
     * Returns a formatted string representation of the dimensions.
     * 
     * @return dimensions as "widthxheight" format
     */
    @Override
    public String toString() {
        return MessageFormat.format("{0}x{1}", this.width, this.height);
    }
}
