package za.co.wethinkcode.robots.server.model.robot.command.movement;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;

import java.util.List;

/**
 * Command that turns a robot left or right.
 * Assumes the robot exists and argument is always "left" or "right".
 */
public class TurnCommand extends Command {

    private final String robotName;
    private final String direction;

    public TurnCommand(World world, String robotName, List<String> args) {
        super(world);
        this.robotName = robotName;
        this.direction = args.getFirst().toLowerCase(); // assume valid
    }

    @Override
    public JsonObject execute() {
        Robot robot = world.findRobot(robotName);

        if (direction.equals("left")) {
            robot.turnLeft();
        } else {
            robot.turnRight();
        }

        return ProtocolResponse.state(robot, world);
    }
}
