package za.co.wethinkcode.robots.server.AcceptanceTests.world_Obstacle;//package za.co.wethinkcode.robots.server.AcceptanceTests.world_2x2_Obs;
//
//import com.google.gson.JsonObject;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robots.server.AcceptanceTests.BaseRobotTest;
//import static za.co.wethinkcode.robots.server.Utils.assertions.ResponseAssertions.*;
//
///**
//As a player who is connected to the server
//I want to launch a robot into the world
//So that I can start exploring the environment
//And interact with other robots
// */
//public class LaunchCommandTest extends BaseRobotTest {
//
//
//    @Test
//    void testLaunchRobotIntoWorldWithObstacle() {
//        JsonObject response = sendCommand("HAL launch 2 2");
//        assertResult(response, "OK");
//
//        // Launch 7 robots
//        for (int i = 1; i <= 7; i++) {
//            String robotName = "R" + i;
//            // Assuming all robots try to launch in the 2x2 world
//            response = sendCommand(robotName + " launch 2 2");
//            assertResult(response, "OK");
//            System.out.println(robotName + " position: " + response.getAsJsonObject("data").getAsJsonArray("position"));
//
//            // Get robot position
//            JsonArray posArray = response.getAsJsonObject("data").getAsJsonArray("position");
//            int x = posArray.get(0).getAsInt();
//            int y = posArray.get(1).getAsInt();
//
//            //robots cannot occupy position [1,1]
//            assertFalse(x == 1 && y == 1);
//            //System.out.println("R1 position: [" + x + "," + y + "]");
//        }
//    }
//
//    @Test
//    void testLaunchWorldWithObstacleIsFull() {
//        JsonObject response = sendCommand("HAL launch 2 2");
//        assertResult(response, "OK");
//
//        // Launch 7 robots
//        for (int i = 1; i <= 7; i++) {
//            String robotName = "R" + i;
//            // Assuming all robots try to launch in the 2x2 world
//            response = sendCommand(robotName + " launch 2 2");
//            assertResult(response, "OK");
//            System.out.println(robotName + " position: " + response.getAsJsonObject("data").getAsJsonArray("position"));
//        }
//
//        // Attempt to launch the 9th robot
//        response = sendCommand("R10 launch 2 2");
//        assertResult(response, "ERROR");
//        assertMessage(response,"No more space in this world");
//
//    }
//}
