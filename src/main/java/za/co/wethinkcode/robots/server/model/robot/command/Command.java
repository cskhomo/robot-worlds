package za.co.wethinkcode.robots.server.model.robot.command;

import za.co.wethinkcode.robots.server.model.world.World;
import com.google.gson.JsonObject;

/**
 * Base class for all robot commands executed in the world.
 */
public abstract class Command {

    protected final World world;

    /**
     * Creates a new Command with a reference to the world.
     *
     * @param world the world instance where the command operates
     */
    public Command(World world) {
        this.world = world;
    }

    /**
     * Executes the command and returns a JSON response.
     *
     * @return the JSON object representing the result of execution
     */
    public abstract JsonObject execute();
}
