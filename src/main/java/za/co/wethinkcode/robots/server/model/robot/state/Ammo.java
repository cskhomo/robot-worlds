package za.co.wethinkcode.robots.server.model.robot.state;

/**
 * Represents the robot's ammunition for firing bullets.
 */
public class Ammo {

    private int current;
    private final int max;

    /**
     * Creates a Bullet object with a maximum number of shots.
     *
     * @param max the maximum number of bullets the robot can carry
     */
    public Ammo(int max) {
        this.max = max;
        this.current = max;
    }

    /**
     * Returns the current number of bullets available.
     *
     * @return the current ammo count
     */
    public int current() {
        return current;
    }

    /**
     * Returns the maximum number of bullets.
     *
     * @return the maximum ammo count
     */
    public int max() {
        return max;
    }

    /**
     * Replenishes bullets to the maximum number.
     */
    public void replenish() {
        this.current = max;
    }

    /**
     * Uses one bullet if available.
     *
     * @return true if a bullet was fired, false if out of ammo
     */
    public boolean fire() {
        if (current > 0) {
            current--;
            return true;
        }
        return false;
    }
}
