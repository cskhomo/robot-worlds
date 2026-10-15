package za.co.wethinkcode.robots.server.model.robot.command.information;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;

/**
 * Represents a "look" command issued by a robot to scan its surroundings.
 * <p>
 * When executed, the command finds the robot by name, performs a scan
 * of the world, and returns a JSON response with the scan results.
 * </p>
 */
public class LookCommand extends Command {

    private final String robotName;

    /**
     * Constructs a LookCommand for a given robot in a specific world.
     *
     * @param world     The world in which the robot exists.
     * @param robotName The name of the robot performing the scan.
     */
    public LookCommand(World world, String robotName) {
        super(world);
        this.robotName = robotName;
    }

    /**
     * Executes the "look" command.
     * <p>
     * Finds the robot by name, scans the world, and returns a JSON object
     * containing the scan results according to the protocol.
     * </p>
     *
     * @return A JsonObject representing the scan results.
     */
    @Override
    public JsonObject execute() {

        Robot robot = world.findRobot(robotName);
        robot.scan(world);

        return ProtocolResponse.look(robot, world);
    }
}
