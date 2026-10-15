package za.co.wethinkcode.robots.server.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.object.Object;

/**
 * Utility class for building protocol responses.
 */
public class ProtocolResponse {

    /**
     * Builds a JSON object representing an error response.
     * @param message the error message to include
     * @return a JsonObject with "result" set to "ERROR" and "data" containing the message
     */
    public static JsonObject error(String message) {
        JsonObject response = new JsonObject();
        response.addProperty("result", "ERROR");
        response.add("data", buildData(message));
        return response;
    }

    /**
     * Builds a JSON object representing a successful robot launch.
     * @param robot the robot that was launched
     * @param world the world the robot was launched into
     * @return a JsonObject with "result" set to "OK" and "data" and "state" sections
     */
    public static JsonObject state(Robot robot, World world) {
        JsonObject response = new JsonObject();
        response.addProperty("result", "OK");
        response.add("data", buildData(robot, world));
        response.add("state", buildState(robot));
        return response;
    }

    /**
     * Builds a JSON response for a robot's "look" command.
     * Reads the robot's scanned visible objects and world info.
     */
    public static JsonObject look(Robot robot, World world) {
        JsonObject response = new JsonObject();
        response.addProperty("result", "OK");

        JsonArray objectsArray = new JsonArray();
        for (Object obj : robot.objects()) {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("direction", obj.direction().toString());
            jsonObj.addProperty("type", obj.type().toString());
            jsonObj.addProperty("distance", obj.distance());
            objectsArray.add(jsonObj);
        }

        JsonObject data = new JsonObject();
        data.add("objects", objectsArray);
        response.add("data", data);

        response.add("state", buildState(robot));

        return response;
    }

    private static JsonObject buildData(String message) {
        JsonObject data = new JsonObject();
        data.addProperty("message", message);
        return data;
    }

    private static JsonObject buildData(Robot robot, World world) {
        JsonObject data = new JsonObject();
        data.add("position", positionArray(robot));
        data.addProperty("visibility", world.visibility());
        data.addProperty("reload", world.reloadTime());
        data.addProperty("repair", world.repairTime());
        data.addProperty("shields", world.maxShields());
        return data;
    }

    private static JsonObject buildState(Robot robot) {
        JsonObject state = new JsonObject();
        state.add("position", positionArray(robot));
        state.addProperty("direction", robot.direction().toString());
        state.addProperty("shields", robot.shield().current());
        state.addProperty("shots", robot.ammo().current());
        state.addProperty("status", robot.status().toString());
        return state;
    }

    private static JsonArray positionArray(Robot robot) {
        JsonArray position = new JsonArray();
        position.add(robot.position().X());
        position.add(robot.position().Y());
        return position;
    }

    public static JsonObject world(World world) {

        JsonObject response = new JsonObject();
        response.addProperty("result", "OK");

        JsonObject data = new JsonObject();
        JsonArray dimensions = new JsonArray();
        dimensions.add(world.dimension().width());
        dimensions.add(world.dimension().height());
        data.add("dimensions", dimensions);

        data.addProperty("visibility", world.visibility());
        data.addProperty("reload", world.reloadTime());
        data.addProperty("repair", world.repairTime());
        data.addProperty("mine", world.setMineTime());
        data.addProperty("max-shields", world.maxShields());
        data.addProperty("max-shots", world.maxAmmo());

        response.add("data", data);

        return response;
    }

    public static JsonObject replenish(Robot robot) {
        JsonObject response = new JsonObject();
        response.addProperty("result", "OK");
        response.add("data", buildData("Done"));
        response.add("state", buildState(robot));
        return response;
    }

    public static JsonObject move(Robot robot, String message) {

        JsonObject response = new JsonObject();
        response.addProperty("result", "OK");

        JsonObject data = new JsonObject();
        data.addProperty("message", message);
        data.add("position", positionArray(robot));
        response.add("data", data);

        response.add("state", buildState(robot));

        return response;
    }
}
