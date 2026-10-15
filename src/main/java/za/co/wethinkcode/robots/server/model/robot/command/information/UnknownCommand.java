package za.co.wethinkcode.robots.server.model.robot.command.information;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;

/**
 * Represents a command that the server does not recognize.
 * Returns a standardized error response indicating an unsupported command.
 */
public class UnknownCommand extends Command {

    /**
     * Creates a new UnknownCommand with a reference to the world.
     *
     * @param world the world instance where the command operates
     */
    public UnknownCommand(World world) {
        super(world);
    }

    /**
     * Executes the command.
     *
     * @return a JsonObject containing an ERROR response with a message
     *         indicating the command is unsupported
     */
    @Override
    public JsonObject execute() {
        return ProtocolResponse.error("Unsupported command");
    }
}
