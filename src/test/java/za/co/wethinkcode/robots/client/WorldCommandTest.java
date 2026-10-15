package za.co.wethinkcode.robots.client;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.robot.command.information.WorldCommand;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldCommandTest {

    @Test
    void testExecuteReturnsWorldConfig() {
        // Create a real World instance with the required Dimension constructor.
        Dimension dimension = new Dimension(10, 10);
        World world = new World(dimension);

        WorldCommand command = new WorldCommand(world);

        JsonObject result = command.execute();
        JsonObject expected = ProtocolResponse.world(world);

        assertEquals(expected, result,
                "WorldCommand.execute() should return ProtocolResponse.world(world)");
    }
}
