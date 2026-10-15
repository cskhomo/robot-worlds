package za.co.wethinkcode.robots.server.AcceptanceTests.world_1x1;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.AcceptanceTests.BaseRobotTest;
import static za.co.wethinkcode.robots.server.Utils.assertions.ResponseAssertions.*;
import static za.co.wethinkcode.robots.server.Utils.assertions.ExistenceAssertions.*;

/**
 As a player who is connected to the server
 I want to launch a robot into the world
 So that I can start exploring the environment
 And interact with other robots
 */
public class LaunchCommand1x1Test extends BaseRobotTest {

    /**
     * Scenario: Launching a robot with a valid command succeeds
     */
    @Test
    void testValidLaunch() {

        // Given that I am connected to a Robot Worlds server
        // And the world is of size 1×1
        assertConnected(client, true);

        // When I send a valid launch request to the server
        String requestJson = """
               {"robot": "HOPE", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        JsonObject response = sendCommand(requestJson);

        // Then I should receive an OK response
        assertResult(response, "OK");

        // And the position should be (x: 0, y: 0)
        assertPosition(response, 0, 0);

        // And the state of the robot should be returned
        assertHasState(response, true);
    }


    /**
     * Scenario: Launching a robot with an invalid command fails
     */
    @Test
    void testInvalidLaunch() {

        // Given that I am connected to a Robot Worlds server
        assertConnected(client, true);


        // When I send invalid launch requests to the server
        String spawnRequest = """
               {"robot": "HOPE", "command": "spawnRequest", "arguments": ["1", "1"]}""";

        String activateRequest = """
               {"robot": "HOPE", "command": "activate", "arguments": ["1", "1"]}""";

        JsonObject spawnResponse = sendCommand(spawnRequest);
        JsonObject activateResponse = sendCommand(activateRequest);

        // Then I should receive an ERROR response
        assertResult(spawnResponse, "ERROR");
        assertResult(activateResponse, "ERROR");

        // And the message "Unsupported command"
        assertMessage(spawnResponse, "Unsupported command");
        assertMessage(activateResponse, "Unsupported command");
    }


    /**
     * Scenario: Launching a robot into a world with no free space fails
     */
    @Test
    void testWorldIsFull() {

        // Given that I am connected to a Robot Worlds server
        // And the world is of size 1×1
        assertConnected(client, true);

        // And there is already a robot launched in the world
        String firstRequest= """
                {"robot": "HOPE", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        sendCommand(firstRequest);

        // When I send a valid launch request
        String secondRequest = """
                {"robot": "DESPAIR", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        JsonObject response = sendCommand(secondRequest);

        // Then I should receive an ERROR response
        assertResult(response, "ERROR");

        // And the message "No more space in this world"
        assertMessage(response, "No more space in this world");
    }


    /**
     * Scenario: Launching a robot with a duplicate name fails
     */
    @Test
    void testDuplicateLaunch() {

        // Given that I am connected to a Robot Worlds server
        assertConnected(client, true);

        // And a robot with the same name already exists in the world
        String firstRequest = """
                {"robot": "HOPE", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        sendCommand(firstRequest);

        // When I send a valid launch request with the same name
        String duplicateRequest = """
                {"robot": "HOPE", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        JsonObject response = sendCommand(duplicateRequest);

        // Then I should receive an ERROR response
        assertResult(response, "ERROR");

        // And the message "Too many of you in this world"
        assertMessage(response, "Too many of you in this world");
    }
}