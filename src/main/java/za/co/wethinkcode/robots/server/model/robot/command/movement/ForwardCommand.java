package za.co.wethinkcode.robots.server.model.robot.command.movement;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;

import java.util.List;

/**
 * Command that moves a robot forward.
 * Assumes the robot exists and the argument is a valid step count.
 */
public class ForwardCommand extends Command {

    private final String robotName;
    private final int steps;

    public ForwardCommand(World world, String robotName, List<String> args) {
        super(world);
        this.robotName = robotName;
        this.steps = Integer.parseInt(args.getFirst());
    }

    @Override
    public JsonObject execute() {
        Robot robot = world.findRobot(robotName);

        String message = robot.moveForward(steps, world);

        return ProtocolResponse.move(robot, message);
    }
}
