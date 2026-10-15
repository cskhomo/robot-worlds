package za.co.wethinkcode.robots.server.AcceptanceTests.world_2x2;//package za.co.wethinkcode.robots.server.AcceptanceTests.world_2x2;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.AcceptanceTests.BaseRobotTest;
import static za.co.wethinkcode.robots.server.Utils.assertions.ResponseAssertions.*;

/**
 As a player who is connected to the server
 I want to launch a robot into the world
 So that I can start exploring the environment
 And interact with other robots
 */
public class LaunchCommand2x2Test extends BaseRobotTest {

    /**
     * Scenario: Launching multiple robots into a world with free space succeeds
     */
     @Test
     void testMultipleValidLaunch(){

         // Given that I am connected to a Robot Worlds server
         // And the world is of size 2×2 with no obstructions
         assertConnected(client, true);

         // When I send multiple valid launch requests to the server
         String firstRobot = """
               {"robot": "HOPE", "command": "launch", "arguments": ["generic", "1", "2"]}""";

         String secondRobot = """
               {"robot": "DESPAIR", "command": "launch", "arguments": ["generic", "1", "2"]}""";

         JsonObject firstResponse = sendCommand(firstRobot);
         JsonObject secondResponse = sendCommand(secondRobot);

         // Then I should receive an OK response for each robot
         assertResult(firstResponse, "OK");
         assertResult(secondResponse, "OK");

         System.out.println(firstResponse);
         System.out.println(secondResponse);

        // And each robot should occupy a unique position
         assertPositionsDifferent(firstResponse, secondResponse);
     }


    /**
     * Scenario: Launching a robot into a world with no obstructions but no free space fails
     */
    @Test
    void testWorldIsFull() {

        // Given that I am connected to a Robot Worlds server
        // And the world is of size 2×2 with no obstructions
        assertConnected(client, true);

        // And all 9 positions are already occupied by robots
        for (int i = 1; i <= 9; i++) {
            String robotJson = """
                {"robot": "HOPE%d", "command": "launch", "arguments": ["generic", "1", "2"]}""".formatted(i);

            sendCommand(robotJson);
        }

        // When I send a valid launch request to the server
        String extraRobot = """
            {"robot": "DESPAIR", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        JsonObject response = sendCommand(extraRobot);

        // Then I should receive an ERROR response
        assertResult(response, "ERROR");

        // And the message “No more space in this world”
        assertMessage(response, "No more space in this world");
    }
}