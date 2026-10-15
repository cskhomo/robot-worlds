package za.co.wethinkcode.robots.server.AcceptanceTests.world_2x2;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.AcceptanceTests.BaseRobotTest;

import static za.co.wethinkcode.robots.server.Utils.assertions.ResponseAssertions.*;

/**
 As a player who has launched a robot
 I want the robot to move forward
 So that I can explore the world
 */
public class ForwardCommand2x2Test extends BaseRobotTest {

    /**
     * Scenario: Moving robot forward in an open 2×2 world succeeds
     */
    @Test
    void testForwardMovement() {

        // Given that I am connected to a Robot Worlds server
        // And the world is of size 2×2 with no obstructions
        assertConnected(client, true);

        // And I have successfully launched a robot at [0,0]
        String launch = """
            {"robot": "Hope", "command": "launch", "arguments": ["generic", "1", "2"]}""";

        JsonObject launchResponse = sendCommand(launch);

        assertResult(launchResponse, "OK");
        assertPosition(launchResponse, 0, 0);

        // When I send a valid forward request with a single steps
        String forward = """
            {"robot": "Hope", "command": "forward", "arguments": ["1"]}""";

        JsonObject forwardResponse = sendCommand(forward);

        // Then I should receive an OK response
        assertResult(forwardResponse, "OK");

        // And the position should become at [0,1]
        assertPosition(forwardResponse, 0, 1);
    }
}
