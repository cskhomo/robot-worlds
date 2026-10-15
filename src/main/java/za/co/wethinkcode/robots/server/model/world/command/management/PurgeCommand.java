package za.co.wethinkcode.robots.server.model.world.command.management;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;

/**
 * Command that removes a specific robot from the world.
 * Used for server administration and cleanup.
 */
public class PurgeCommand extends Command {

    private String robotName;

    /**
     * Creates a purge command for the specified robot.
     * 
     * @param console the console output handler
     * @param world the game world instance
     * @param robotName the name of the robot to remove
     */
    public PurgeCommand(ConsoleOutput console, World world, String robotName) {
        super(console, world);
        this.robotName = robotName;
    }

    public PurgeCommand() {
    }

    @Override
    public void execute() {

        if (robotName == null) {
            console.printError("No robot name provided for purge.");
            return;
        }

        Robot robot = world.findRobot(robotName);

        if (robot == null) {
            console.printError(robotName + " does not exist.");
        } else {
            world.removeRobot(robot);
            console.printPurge(robotName);
        }
    }

    @Override
    public String name() {
        return "purge";
    }

    @Override
    public String description() {
        return "removes a robot from the world by name.";
    }
}