package za.co.wethinkcode.robots.server.AcceptanceTests.world_1x1;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.AcceptanceTests.BaseRobotTest;

import static za.co.wethinkcode.robots.server.Utils.assertions.ResponseAssertions.*;
import static za.co.wethinkcode.robots.server.Utils.assertions.ExistenceAssertions.*;

/**
 * As a player who has launched a robot into the world
 * I want to check my robot’s state at any time
 * So that I can keep track of its overall status
 */
public class StateCommand1x1Test extends BaseRobotTest {

    /**
     * Scenario: Valid state command should succeed
     */
    @Test
    void testValidState() {

        // Given that I am connected to a Robot Worlds server
        assertConnected(client, true);

        // And that I have successfully launched a robot
        String launchRequest = """
                {"robot": "HOPE", "command": "launch", "arguments": ["generic","1","2"]}""";

        sendCommand(launchRequest);

        // When I send a valid state request
        String stateRequest = """
                {"robot": "HOPE", "command": "state", "arguments": []}""";

        JsonObject response = sendCommand(stateRequest);

        // Then I should receive an OK response
        assertResult(response, "OK");

        // And the state of the robot should be returned
        assertHasState(response, true);

        // And the robot’s state should include:
        assertPosition(response, 0, 0); // Position [x, y]
        assertDirection(response, "NORTH", "EAST", "SOUTH", "WEST");   // Direction
        assertHasShields(response, true);   // Shields exist
        assertHasShots(response, true);     // Shots exist
    }


    /**
     * Scenario: Invalid state command should fail
     */
    @Test
    void testInvalidStateCommands() {

        // Given that I am connected to a Robot Worlds server
        assertConnected(client, true);

        // And that I have successfully launched a robot
        String launchRequest = """
            {"robot": "HOPE", "command": "launch", "arguments": ["generic","1","2"]}""";

        sendCommand(launchRequest);

        // When I send invalid state requests to the server
        String statusRequest = """
            {"robot": "HOPE", "command": "status", "arguments": []}""";

        String infoRequest = """
            {"robot": "HOPE", "command": "info", "arguments": []}""";

        JsonObject statusResponse = sendCommand(statusRequest);
        JsonObject infoResponse = sendCommand(infoRequest);

        // Then I should receive an ERROR response
        assertResult(statusResponse, "ERROR");
        assertResult(infoResponse, "ERROR");

        // And the message "Unsupported command"
        assertMessage(statusResponse, "Unsupported command");
        assertMessage(infoResponse, "Unsupported command");

        // And the robot’s state should not be returned
        assertHasState(statusResponse, false);
        assertHasState(infoResponse, false);
    }
}