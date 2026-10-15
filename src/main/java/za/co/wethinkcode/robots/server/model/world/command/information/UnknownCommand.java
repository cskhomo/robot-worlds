package za.co.wethinkcode.robots.server.model.world.command.information;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;

/**
 * Represents an "unknown" command in the world context.
 * <p>
 * This command is executed when a user enters a command that is not recognized.
 * It informs the user that the command is invalid.
 * </p>
 */
public class UnknownCommand extends Command {

    /**
     * Constructs an UnknownCommand with access to console output and the world.
     *
     * @param console The console output for displaying messages.
     * @param world   The world context (not used directly but required by superclass).
     */
    public UnknownCommand(ConsoleOutput console, World world) {
        super(console, world);
    }

    /**
     * Executes the unknown command by printing an "unknown command" message to the console.
     */
    @Override
    public void execute() {
        console.printUnknown();
    }

    /**
     * Returns the name of this command.
     *
     * @return The string "unknown".
     */
    @Override
    public String name() {
        return "unknown";
    }

    /**
     * Returns a brief description of this command.
     *
     * @return A string indicating that the command is not recognized.
     */
    @Override
    public String description() {
        return "command not recognized";
    }
}