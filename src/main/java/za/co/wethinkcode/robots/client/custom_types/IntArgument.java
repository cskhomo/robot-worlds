package za.co.wethinkcode.robots.client.custom_types;

/**
 * Represents an integer argument for robot commands.
 * Used for numeric parameters like steps, coordinates, etc.
 */
public final class IntArgument implements Argument {
    private final int value;

    /**
     * Creates an integer argument with the specified value.
     * 
     * @param value the integer value
     */
    public IntArgument(int value) {
        this.value = value;
    }

    @Override
    public Integer value() {
        return this.value;
    }
}
