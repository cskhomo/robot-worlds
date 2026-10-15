package za.co.wethinkcode.robots.server.model.robot.state;

/**
 * Represents the current operational status of a robot.
 * Determines what actions the robot can perform.
 */
public enum Status {
    NORMAL,
    SETMINE,
    RELOADING,
    REPAIRING,
    DEAD
}