package za.co.wethinkcode.robots.client.custom_types;

/**
 * Sealed interface for command arguments.
 * Allows only IntArgument and StringArgument implementations.
 */
public sealed interface Argument permits IntArgument, StringArgument {
    /** @return the argument value */
    Object value();
}