package za.co.wethinkcode.robots.server.model.world.command.information;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;

/**
 * Represents the "robots" command in the world context.
 * <p>
 * When executed, this command prints a list of all robots currently
 * present in the world to the console.
 * </p>
 */
public class RobotsCommand extends Command {

    /**
     * Constructs a RobotsCommand with console access and a world.
     *
     * @param console The console output for displaying robot information.
     * @param world   The world containing the robots.
     */
    public RobotsCommand(ConsoleOutput console, World world) {
        super(console, world);
    }

    /**
     * Default constructor for cases where console/world are set later.
     */
    public RobotsCommand() {
    }

    /**
     * Executes the robots command by printing all robots in the world.
     */
    @Override
    public void execute() {
        console.printRobots(world.robots(), true);
    }

    /**
     * Returns the name of this command.
     *
     * @return The string "robots".
     */
    @Override
    public String name() {
        return "robots";
    }

    /**
     * Returns a brief description of this command.
     *
     * @return A string describing the command's purpose.
     */
    @Override
    public String description() {
        return "displays a list of all robots in the world.";
    }
}