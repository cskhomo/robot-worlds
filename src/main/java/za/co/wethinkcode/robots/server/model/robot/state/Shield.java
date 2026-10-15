package za.co.wethinkcode.robots.server.model.robot.state;

/**
 * Represents the robot's shields which absorb hits.
 */
public class Shield {

    private int current;
    private final int max;

    /**
     * Creates a Shield object with a maximum strength.
     *
     * @param max the maximum number of hits the shield can absorb
     */
    public Shield(int max) {
        this.max = max;
        this.current = max;
    }

    /**
     * Returns the current shield strength.
     *
     * @return the current number of hits the shield can absorb
     */
    public int current() {
        return current;
    }

    /**
     * Returns the maximum shield strength.
     *
     * @return the maximum number of hits the shield can absorb
     */
    public int max() {
        return max;
    }

    /**
     * Replenishes the shield to its maximum strength.
     */
    public void replenish() {
        this.current = max;
    }

    /**
     * Absorbs a hit, reducing the current shield strength by one.
     *
     * @return true if the shield absorbed the hit, false if already depleted
     */
    public boolean takeHit() {
        if (current > 0) {
            current--;
            return true;
        }
        return false;
    }
}
