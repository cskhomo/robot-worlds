package za.co.wethinkcode.robots.server.model.robot.command.information;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;

/**
 * Represents a "state" command issued by a robot to report its current status.
 * <p>
 * When executed, the command retrieves the robot by name and returns
 * a JSON response containing its current state in the world.
 * </p>
 */
public class StateCommand extends Command {

    private final String robotName;

    /**
     * Constructs a StateCommand for a specific robot in a given world.
     *
     * @param world The world where the robot exists.
     * @param name  The name of the robot whose state is being requested.
     */
    public StateCommand(World world, String name) {
        super(world);
        this.robotName = name;
    }

    /**
     * Executes the "state" command.
     * <p>
     * Finds the robot by name and returns its current state as a JSON object
     * according to the protocol.
     * </p>
     *
     * @return A JsonObject representing the robot's current state.
     */
    @Override
    public JsonObject execute() {

        Robot robot = world.findRobot(robotName);
        return ProtocolResponse.state(robot, world);
    }
}
