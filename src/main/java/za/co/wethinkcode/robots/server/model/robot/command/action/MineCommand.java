package za.co.wethinkcode.robots.server.model.robot.command.action;//package za.co.wethinkcode.robots.server.robot.command.action;
//
//import com.google.gson.JsonObject;
//
//import za.co.wethinkcode.robots.protocol.Response;
//import za.co.wethinkcode.robots.server.robot.Robot;
//import za.co.wethinkcode.robots.server.robot.command.Command;
//import za.co.wethinkcode.robots.server.world.World;
//
///**
// * Command that allows a robot to fire a shot.
// * If a robot is hit, its status and distance are reported;
// * otherwise, the result is a miss.
// */
//public class FireCommand extends Command {
//    public FireCommand(World world, JsonObject jsonObject) {
//        super(world, jsonObject);
//    }
//
//    /**
//     * Executes the "fire" command. Attempts to shoot another robot in the
//     * firing line. Includes information about whether a hit occurred, which
//     * robot was hit (if any), the distance, and the state of both shooter and target.
//     *
//     * @return a JsonObject containing the result of the fire command
//     */
//    @Override
//    public JsonObject execute() {
//        String robotName = this.jsonObject.get("robot").getAsString();
//        Robot robot = this.world.getRobotWithName(robotName);
//
//        // Fire the weapon and get hit information
//        HitInfo hitInfo = robot.fire();
//
//        Response protocol = new Response();
//        JsonObject data = new JsonObject();
//
//        if (hitInfo.hit()) {
//            // A robot was hit
//            data.addProperty("message", "Hit");
//            data.addProperty("distance", hitInfo.distance());
//            data.addProperty("robot", hitInfo.hitObjectName());
//
//            String hitRobotName = hitInfo.hitObjectName();
//            Robot hitRobot = this.world.getRobotWithName(hitRobotName);
//
//            JsonObject hitRobotState = new JsonObject();
//
//            if (hitRobot != null && hitRobot.isAlive()) {
//                // If the robot is still alive, return its current state
//                JsonObject state = protocol.getRobotState(hitRobot);
//                hitRobotState = state;
//            } else {
//                // Otherwise, mark it as dead
//                hitRobotState.addProperty("status", "DEAD");
//            }
//
//            data.add("state", hitRobotState);
//        } else {
//            // No robot was hit
//            data.addProperty("message", "Miss");
//        }
//
//        // Report remaining bullets of the firing robot
//        JsonObject state = new JsonObject();
//        state.addProperty("shots", robot.getBulletsAvail());
//
//        // Construct and return the response
//        JsonObject response = new JsonObject();
//        response.addProperty("result", "OK");
//        response.add("data", data);
//        response.add("state", state);
//
//        return response;
//    }
//}
