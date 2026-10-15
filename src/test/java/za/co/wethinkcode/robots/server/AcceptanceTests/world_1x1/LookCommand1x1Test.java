package za.co.wethinkcode.robots.server.AcceptanceTests.world_1x1;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.AcceptanceTests.BaseRobotTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static za.co.wethinkcode.robots.server.Utils.JsonUtils.*;
import static za.co.wethinkcode.robots.server.Utils.assertions.ResponseAssertions.*;
import static za.co.wethinkcode.robots.server.Utils.assertions.ExistenceAssertions.*;

/**
 * As a player who has launched a robot into the world
 * I want to look around in all four directions (N, E, S, W)
 * So that I can observe everything within the visibility range
 */
public class LookCommand1x1Test extends BaseRobotTest {

    /**
     * Scenario: Looking around an empty world detects all 4 edges
     */
    @Test
    void testLookDetectsEdges() {

        List<String> compass = Arrays.asList("NORTH", "EAST", "SOUTH", "WEST");

        // Given that I am connected to a Robot Worlds server
        // And the world is of size 1×1
        assertConnected(client, true);

        // And I have successfully launched a robot
        String launch = """
                {"robot": "HOPE", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        sendCommand(launch);

        // When I send a valid look request
        String requestJson = """
                {"robot": "HOPE", "command": "look", "arguments": []}""";

        JsonObject response = sendCommand(requestJson);

        // Then I should receive an OK response
        assertResult(response, "OK");

        // And I should receive data containing a list of objects
        assertHasObjects(response, true);
        JsonArray objects = getObjects(response);


        // And each detected object must include:
        for (int i = 0; i < objects.size(); i++) {

            JsonObject obj = objects.get(i).getAsJsonObject();

            // direction: NORTH, EAST, SOUTH, WEST
            assertTrue(compass.contains(getObjectDirection(obj)));

            // type: EDGE
            assertEquals("EDGE", getObjectType(obj));

            // distance: 0.5
            assertEquals(0.5, Math.abs(getObjectDistance(obj) -0.5));
        }
    }


    /**
     * Scenario: Looking around an empty world does not detect obstructions
     */
    @Test
    void testNoObstructions() {

        // Given that I am connected to a Robot Worlds server
        // And the world is of size 1×1
        assertConnected(client, true);

        // And I have successfully launched a robot
        String launch = """
            {"robot": "HOPE", "command": "launch", "arguments": ["generic","1","2"]}""";

        sendCommand(launch);

        // When I send a valid look request
        String look = """
            {"robot": "HOPE", "command": "look", "arguments": []}""";

        JsonObject response = sendCommand(look);

        // Then I should receive an OK response
        assertResult(response, "OK");

        // And I should receive data containing a list of objects
        assertHasObjects(response, true);

        // And the list of objects must not include any OBSTACLE
        assertHasObject(response, "OBSTACLE", false);
    }


    /**
     * Scenario: Looking around an empty world does not detect other robots
     */
    @Test
    void testNoOtherRobots() {

        // Given that I am connected to a Robot Worlds server
        // And the world is of size 1×1
        assertConnected(client, true);

        // And I have successfully launched a robot
        String launch = """
            {"robot": "HOPE", "command": "launch", "arguments": ["generic","1","2"]}""";

        sendCommand(launch);

        // When I send a valid look request
        String look = """
            {"robot": "HOPE", "command": "look", "arguments": []}""";

        JsonObject response = sendCommand(look);

        // Then I should receive an OK response
        assertResult(response, "OK");

        // And I should receive data containing a list of objects
        assertHasObjects(response, true);

        // And the list of objects must not include any other ROBOT
        assertHasObject(response, "ROBOT", false);
    }


    /**
     * Scenario: Invalid look-like commands fail
     */
    @Test
    void testInvalidLook() {

        // Given that I am connected to a Robot Worlds server
        assertConnected(client, true);

        // And that I have successfully launched a robot
        String launchRequest = """
                {"robot": "HOPE", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        sendCommand(launchRequest);

        // When I send invalid look requests to the server
        String scanRequest = """
                {"robot": "HOPE", "command": "scan", "arguments": []}""";

        String observeRequest = """
                {"robot": "HOPE", "command": "observe", "arguments": []}""";

        JsonObject scanResponse = sendCommand(scanRequest);
        JsonObject observeResponse = sendCommand(observeRequest);

        // Then I should receive an ERROR response
        assertResult(scanResponse, "ERROR");
        assertResult(observeResponse, "ERROR");

        // And the message "Unsupported command"
        assertMessage(scanResponse, "Unsupported command");
        assertMessage(observeResponse, "Unsupported command");
    }

}
