package za.co.wethinkcode.robots.server.model.robot.command.information;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;

/**
 * Returns world configuration details.
 */
public class WorldCommand extends Command {

    public WorldCommand(World world) {
        super(world);
    }

    @Override
    public JsonObject execute() {
        return ProtocolResponse.world(world);
    }
}
