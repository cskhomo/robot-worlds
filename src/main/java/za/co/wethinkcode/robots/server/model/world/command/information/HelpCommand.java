package za.co.wethinkcode.robots.server.model.world.command.information;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;
import za.co.wethinkcode.robots.server.model.world.command.management.*;
import za.co.wethinkcode.robots.server.model.world.command.persistence.*;
import za.co.wethinkcode.robots.server.network.SocketHandler;

import java.util.List;

/**
 * Represents the "help" command in the world context.
 * <p>
 * When executed, this command displays a list of all available commands
 * to the console, helping users understand what actions they can perform.
 * </p>
 */
public class HelpCommand extends Command {

    private final List<Command> commands;

    /**
     * Constructs a HelpCommand with access to console output and the world.
     *
     * @param console       The console output for displaying command information.
     * @param world         The world instance in which commands are executed.
     * @param socketHandler The socket handler (not used directly here but may be needed for command context).
     */
    public HelpCommand(ConsoleOutput console, World world, SocketHandler socketHandler) {
        super(console, world);
        this.commands = List.of(
                new DumpCommand(),
                new SaveCommand(),
                new RestoreCommand(),
                new RobotsCommand(),
                new PurgeCommand(),
                this,
                new QuitCommand()
        );
    }

    /**
     * Executes the help command by printing all available commands to the console.
     */
    @Override
    public void execute() {
        console.printHelp(commands);
    }

    /**
     * Returns the name of this command.
     *
     * @return The string "help".
     */
    @Override
    public String name() {
        return "help";
    }

    /**
     * Returns a brief description of this command.
     *
     * @return A string describing the command's purpose.
     */
    @Override
    public String description() {
        return "displays a list of available commands.";
    }
}