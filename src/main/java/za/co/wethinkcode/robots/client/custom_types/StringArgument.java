package za.co.wethinkcode.robots.client.custom_types;

/**
 * Represents a string argument for robot commands.
 * Used for text parameters like directions, robot types, etc.
 */
public final class StringArgument implements Argument {
    private final String value;

    /**
     * Creates a string argument with the specified value.
     * 
     * @param value the string value
     */
    public StringArgument(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return this.value;
    }
}
