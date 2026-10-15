package za.co.wethinkcode.robots.server.model.world;

import com.google.gson.*;

import za.co.wethinkcode.robots.server.cli.ArgumentParser;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.world.obstacle.*;

/**
 * The CreateWorld class is responsible for loading and creating
 * a World instance from a JSON configuration file.
 * It reads the configuration file, parses the JSON content,
 * builds the World object with its properties,
 * and loads obstacles into the world.
 */
public class CreateWorld {

    /**
     * Loads the world configuration from a JSON file.
     *
     *         or a default World if an error occurs
     */
    public static void loadFrom(ArgumentParser.Config config) {
        try {
//            World world = buildWorld(config);
//            loadObstacles(world, config);
//            return world;
        } catch (Exception e) {
            System.err.println("Config error: " + e.getMessage());
//            return new World(1,1,3,4,4,5,3,3); // fallback to default empty world
        }
    }


    // Builds the World instance using values from the JSON object
    private static void buildWorld(JsonObject json) {

        int
            width = json.get("width").getAsInt(),
            height = json.get("height").getAsInt(),

            visibility = json.get("visibility").getAsInt(),

            shots = json.get("shots").getAsInt(),
            mines = json.get("mines").getAsInt(),
            shields = json.get("shields").getAsInt(),

            reload = json.get("reload").getAsInt(),
            repair = json.get("repair").getAsInt();

//        return new World(width, height, visibility, shots, mines, shields, reload, repair);
    }

    // Loads obstacles from the JSON config and adds them to the World
    private static void loadObstacles(World world, JsonObject json) {
        if (!json.has("obstacles")) return;  // no obstacles specified

        JsonArray obstacles = json.getAsJsonArray("obstacles");
        for (JsonElement element : obstacles) {
            JsonObject obj = element.getAsJsonObject();
//            Obstacle obstacle = createObstacle(obj);
//            world.addObstacle(obstacle);
        }
    }

    // Creates an obstacle object based on the type and coordinates
    private static void createObstacle(JsonObject obj) {
        String type = obj.get("type").getAsString();
        Position topLeft = getPosition(obj.getAsJsonArray("topLeft"));
        Position bottomRight = getPosition(obj.getAsJsonArray("bottomRight"));

//        return switch (type.toLowerCase()) {
//            case "lake" -> new Lake(topLeft, bottomRight);
//            case "bottomlesspit" -> new BottomLessPit(topLeft, bottomRight);
//            default -> new Mountain(topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY());
//        };
    }

    // Converts a JsonArray of two integers into a Position object
    private static Position getPosition(JsonArray arr) {
        return new Position(arr.get(0).getAsInt(), arr.get(1).getAsInt());
    }
}

