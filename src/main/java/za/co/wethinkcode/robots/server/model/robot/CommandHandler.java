package za.co.wethinkcode.robots.server.model.robot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.robot.command.action.*;
import za.co.wethinkcode.robots.server.model.robot.command.connection.*;
import za.co.wethinkcode.robots.server.model.robot.command.information.*;
import za.co.wethinkcode.robots.server.model.robot.command.movement.*;
import za.co.wethinkcode.robots.server.model.world.World;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Handles parsing and creation of robot commands from client requests.
 */
public class CommandHandler {

    private final World world;

    public CommandHandler(World world) {
        this.world = world;
    }

    /**
     * Parses a JSON request and returns the corresponding Command.
     *
     * @param request JSON object containing robot command and arguments
     * @return a Command instance or null if unsupported
     */
    public Command parse(JsonObject request) {
        String command = getCommand(request);
        String name = getRobotName(request);
        List<String> args = getArguments(request);

        return createCommand(command, name, args);
    }

    private Command createCommand(String command, String robot, List<String> args) {
        return switch (command.toLowerCase()) {
            case "launch" -> new LaunchCommand(world, robot, args);
            case "look" -> new LookCommand(world, robot);
            case "state" -> new StateCommand(world, robot);
            case "world" -> new WorldCommand(world);

            case "forward"  -> new ForwardCommand(world, robot, args);
            case "back"     -> new BackCommand(world, robot, args);
            case "turn"     -> new TurnCommand(world, robot, args);
            case "fire"     -> new FireCommand(world, robot);
//            case "mine"     -> new MineCommand(world, robot);
            case "reload"   -> new ReloadCommand(world, robot);
            case "repair"   -> new RepairCommand(world, robot);

            default -> new UnknownCommand(world);
        };
    }

    private String getCommand(JsonObject request) {
        System.out.println(request);
        return request.get("command").getAsString();
    }

    private String getRobotName(JsonObject request) {
        return request.get("robot").getAsString();
    }

    private List<String> getArguments(JsonObject request) {
        JsonArray jsonArgs = request.getAsJsonArray("arguments");
        return StreamSupport.stream(jsonArgs.spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());
    }
}
