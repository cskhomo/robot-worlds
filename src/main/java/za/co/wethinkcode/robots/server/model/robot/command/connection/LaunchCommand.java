package za.co.wethinkcode.robots.server.model.robot.command.connection;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;

import java.util.List;

/**
 * Command that launches a new robot into the world.
 */
public class LaunchCommand extends Command {

    private final String robotName;
    private final List<String> args;

    /**
     * Creates a launch command.
     *
     * @param world the world instance
     * @param robotName the name of the robot to launch
     * @param args list of arguments from the request: [type, shields, ammo]
     */
    public LaunchCommand(World world, String robotName, List<String> args) {
        super(world);
        this.robotName = robotName;
        this.args = args;
    }

    /**
     * Executes the launch command assuming all input is valid.
     *
     * @return JSON response with robot state
     */
    @Override
    public JsonObject execute() {


        if (world.findRobot(robotName) != null) {
            return ProtocolResponse.error("Too many of you in this world");
        }

        Position freePosition = world.freePosition;

        if (freePosition == null) {
            return ProtocolResponse.error("No more space in this world");
        }

        int shields = Integer.parseInt(args.get(1));
        int ammo = Integer.parseInt(args.get(2));

        Robot robot = new Robot(robotName, freePosition, shields, ammo);
        world.addRobot(robot);

        return ProtocolResponse.state(robot, world);
    }

    public String robotName() {
        return robotName;
    }
}
