package za.co.wethinkcode.robots.server.AcceptanceTests.world_1x1;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.AcceptanceTests.BaseRobotTest;

import static za.co.wethinkcode.robots.server.Utils.assertions.ResponseAssertions.*;

/**
 As a player who has launched a robot
 I want the robot to move forward
 So that I can explore the world
 */
public class ForwardCommand1x1Test extends BaseRobotTest {

    /**
     * Scenario: Moving robot at edge of the world remains stationary
     */
    @Test
    void testForwardAtNorthEdge() {

        // Given that I am connected to a Robot Worlds server
        // And the world is of size 1×1 with no obstructions
        assertConnected(client, true);

        // And I have successfully launched a robot at [0,0]
        String launch = """
            {"robot": "Hope", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        JsonObject launchResponse = sendCommand(launch);

        assertResult(launchResponse, "OK");
        assertPosition(launchResponse, 0, 0);

        // When I send a valid forward request with a number of steps
        String forward = """
            {"robot": "Hope", "command": "forward", "arguments": ["5"]}""";

        JsonObject forwardResponse = sendCommand(forward);

        // Then I should receive an OK response
        assertResult(forwardResponse, "OK");

        // And the message "At the NORTH edge"
        assertMessage(forwardResponse, "At the NORTH edge");

        // And the position should remain at [0,0]
        assertPosition(forwardResponse, 0, 0);
    }
}
