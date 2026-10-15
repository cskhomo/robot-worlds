package za.co.wethinkcode.robots.server.AcceptanceTests.world_Obstacle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.AcceptanceTests.BaseRobotTest;

import static za.co.wethinkcode.robots.server.Utils.JsonUtils.getObjects;
import static za.co.wethinkcode.robots.server.Utils.assertions.ExistenceAssertions.assertHasObjects;
import static za.co.wethinkcode.robots.server.Utils.assertions.ResponseAssertions.*;

/**
 As a player who is connected to the server
 I want to look around in a world
 So that I can detect obstacles in my field of view
 */
public class LookCommandObsTest extends BaseRobotTest {

    /**
     * Scenario: Looking around world with obstruction detects obstructions
     */
    @Test
    void testLookDetectsObstacle() {

        // Given that I am connected to a Robot Worlds server
        // And the world is of size 2×2
        // And has an obstacle at coordinate [0,1]
        assertConnected(client, true);

        // And I have successfully launched a robot
        String launchRequest = """
                {"robot": "HOPE", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        JsonObject launchResponse = sendCommand(launchRequest);

        // When I send a valid look request
        String lookRequest = """
                {"robot": "HOPE", "command": "look", "arguments": []}""";

        JsonObject lookResponse = sendCommand(lookRequest);

        // Then I should receive an OK response
        assertResult(lookResponse, "OK");

        // And I should receive data containing objects
        // And I should receive data containing a list of objects
        assertHasObjects(lookResponse, true);
        JsonArray objects = getObjects(lookResponse);

        // And the detected object must include::
//        for (int i = 0; i < objects.size(); i++) {
//
//            JsonObject obj = objects.get(i).getAsJsonObject();
//
//            // direction: NORTH
//            String direction = obj.get("direction").getAsString();
//            assertEquals("NORTH", direction);
//
//            // type: OBSTACLE
//            String type = obj.get("type").getAsString();
//            assertEquals("OBSTACLE", type);
//
//            // distance: 1
//            int distance = obj.get("distance").getAsInt();
//            assertEquals(1, distance);
//        }
    }
}
