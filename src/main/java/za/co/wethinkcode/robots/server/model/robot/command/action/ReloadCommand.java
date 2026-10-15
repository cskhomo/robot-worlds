package za.co.wethinkcode.robots.server.model.robot.command.action;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.ProtocolResponse;
import za.co.wethinkcode.robots.server.model.robot.state.Status;

/**
 * Command that instructs a robot to reload its ammunition.
 * Reloading takes a configured number of seconds from the World.
 */
public class ReloadCommand extends Command {
    private final String robotName;

    public ReloadCommand(World world, String robotName) {
        super(world);
        this.robotName = robotName;
    }

    /**
     * Executes the reload command:
     * - Sets the robot status to RELOADING
     * - Spawns a thread that waits world.reloadTime() seconds before replenishing ammo
     *
     * @return a JSON response with the robot's current state immediately
     */
    @Override
    public JsonObject execute() {
        Robot robot = world.findRobot(robotName);

        if (robot.ammo().current() < robot.ammo().max()) {
            robot.status(Status.RELOADING);

            new Thread(() -> {
                try {
                    Thread.sleep(world.reloadTime() * 1000L);
                } catch (InterruptedException ignored) {}
                robot.ammo().replenish();
                robot.status(Status.NORMAL);
            }, "reloading").start();
        }

        return ProtocolResponse.replenish(robot);
    }
}
