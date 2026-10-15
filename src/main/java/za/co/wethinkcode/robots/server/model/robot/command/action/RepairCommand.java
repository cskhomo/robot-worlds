package za.co.wethinkcode.robots.server.model.robot.command.action;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;
import za.co.wethinkcode.robots.server.model.robot.state.Status;

/**
 * Command that instructs a robot to repair its shields.
 * Repairing takes a configured number of seconds from the World.
 */
public class RepairCommand extends Command {
    private final String robotName;

    public RepairCommand(World world, String robotName) {
        super(world);
        this.robotName = robotName;
    }

    /**
     * Executes the repair command:
     * - Sets the robot status to REPAIRING
     * - Spawns a thread that waits world.repairTime() seconds before replenishing shields
     *
     * @return a JSON response with the robot's current state immediately
     */
    @Override
    public JsonObject execute() {
        Robot robot = world.findRobot(robotName);

        if (robot.shield().current() < robot.shield().max()) {
            robot.status(Status.REPAIRING);

            new Thread(() -> {
                try {
                    Thread.sleep(world.repairTime() * 1000L);
                } catch (InterruptedException ignored) {}
                robot.shield().replenish();
                robot.status(Status.NORMAL);
            }, "repairing").start();
        }

        return ProtocolResponse.replenish(robot);
    }
}
