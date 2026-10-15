package za.co.wethinkcode.robots.server.UnitTests.commands;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.world.World;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private World testWorld;

    @BeforeEach
    void setUp() {
        testWorld = new World(new Dimension(10, 10));
    }

    @Test
    void testExecuteReturnsJsonObject() {
        // Create anonymous subclass of Command
        Command command = new Command(testWorld) {
            @Override
            public JsonObject execute() {
                JsonObject json = new JsonObject();
                json.addProperty("success", true);
                return json;
            }
        };

        // Call execute() and verify the result
        JsonObject result = command.execute();
        assertNotNull(result, "Execute should not return null");
        assertTrue(result.has("success"), "Result should have 'success' property");
        assertTrue(result.get("success").getAsBoolean(), "Success property should be true");
    }
}
