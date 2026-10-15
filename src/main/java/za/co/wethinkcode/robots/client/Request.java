package za.co.wethinkcode.robots.client;

import java.util.List;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import za.co.wethinkcode.robots.client.custom_types.Argument;
import za.co.wethinkcode.robots.client.custom_types.IntArgument;
import za.co.wethinkcode.robots.client.custom_types.StringArgument;

public class Request {

    /** Builds a JSON command message for the server */
    private static JsonObject message(String robotName, String command, List<Argument> arguments) {
        JsonObject msg = new JsonObject();
        msg.addProperty("robot", robotName);
        msg.addProperty("command", command);

        JsonArray args = new JsonArray();
        for (Argument arg : arguments) {
            if (arg instanceof IntArgument i) args.add(i.value());
            if (arg instanceof StringArgument s) args.add(s.value());
        }
        msg.add("arguments", args);
        return msg;
    }

    // Commands without arguments
    public static JsonObject state(String robotName) { return message(robotName, "state", List.of()); }
    public static JsonObject look(String robotName)  { return message(robotName, "look", List.of()); }
    public static JsonObject repair(String robotName){ return message(robotName, "repair", List.of()); }
    public static JsonObject reload(String robotName){ return message(robotName, "reload", List.of()); }
    public static JsonObject fire(String robotName)  { return message(robotName, "fire", List.of()); }
    public static JsonObject mine(String robotName)  { return message(robotName, "mine", List.of()); }
    public static JsonObject world(String robotName) { return message(robotName, "world", List.of()); }

    // Commands with arguments
    public static JsonObject launch(String robotName, int shields, int shots) {
        return message(robotName, "launch", List.of(
                new StringArgument("Normal"),
                new IntArgument(shields),
                new IntArgument(shots)
        ));
    }

    public static JsonObject forward(String robotName, int steps) {
        return message(robotName, "forward", List.of(new IntArgument(steps)));
    }

    public static JsonObject backward(String robotName, int steps) {
        return message(robotName, "back", List.of(new IntArgument(steps)));
    }

    public static JsonObject turn(String robotName, String direction) {
        return message(robotName, "turn", List.of(new StringArgument(direction)));
    }

}
