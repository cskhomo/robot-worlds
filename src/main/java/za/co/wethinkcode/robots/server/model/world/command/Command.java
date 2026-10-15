package za.co.wethinkcode.robots.server.model.world.command;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.world.World;

/**
 * Abstract base class for all server console commands.
 * Provides common structure for command execution and metadata.
 */
public abstract class Command {

    protected ConsoleOutput console;
    protected World world;

    /**
     * Creates a command with console output and world context.
     * 
     * @param console the console output handler
     * @param world the game world instance
     */
    protected Command(ConsoleOutput console, World world) {
        this.console = console;
        this.world = world;
    }

    public Command() {
    }

    /** Executes the command logic. */
    public abstract void execute();

    /** @return the command name */
    public abstract String name();

    /** @return a brief description of what the command does */
    public abstract String description();
}