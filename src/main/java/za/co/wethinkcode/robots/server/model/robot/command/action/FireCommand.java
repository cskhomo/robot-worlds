package za.co.wethinkcode.robots.server.model.robot.command.action;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;

public class FireCommand extends Command {

    private final String robotName;

    public FireCommand(World world, String robotName) {
        super(world);
        this.robotName = robotName;
    }

    @Override
    public JsonObject execute() {
        Robot robot = world.findRobot(robotName);
        robot.ammo().fire();
        return ProtocolResponse.state(robot, world);
    }
}
