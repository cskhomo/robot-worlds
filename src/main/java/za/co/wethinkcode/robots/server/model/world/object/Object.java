package za.co.wethinkcode.robots.server.model.world.object;

import za.co.wethinkcode.robots.server.model.geometry.Direction;

/**
 * Represents an object in the world that can be detected by a robot.
 * Stores the type, direction, and distance from the observing robot.
 */
public record Object(ObjectType type, Direction direction, int distance) { }
