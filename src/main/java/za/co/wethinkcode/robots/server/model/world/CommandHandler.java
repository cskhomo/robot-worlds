package za.co.wethinkcode.robots.server.model.world;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.data.sql.ConnectDatabase;
import za.co.wethinkcode.robots.server.model.world.command.Command;
import za.co.wethinkcode.robots.server.model.world.command.information.*;
import za.co.wethinkcode.robots.server.model.world.command.management.*;
import za.co.wethinkcode.robots.server.model.world.command.persistence.RestoreCommand;
import za.co.wethinkcode.robots.server.model.world.command.persistence.SaveCommand;
import za.co.wethinkcode.robots.server.network.SocketHandler;

public class CommandHandler {

    private final ConsoleOutput console;
    private final World world;
    private final SocketHandler socketHandler;
    private final ConnectDatabase database;

    /**
     * Creates a new CommandHandler with required dependencies.
     *
     * @param console the console output handler
     * @param world the game world instance
     * @param socketHandler the network socket handler
     * @param database the database connection
     */
    public CommandHandler(ConsoleOutput console, World world, SocketHandler socketHandler, ConnectDatabase database) {
        this.console = console;
        this.world = world;
        this.socketHandler = socketHandler;
        this.database = database;
    }

    /**
     * Parses input from the console and returns the corresponding Command object.
     *
     * @param input the raw console input
     * @return the parsed Command
     */
    public Command parse(String input) {
        String[] parts = splitInput(input);
        String command = parts[0];
        String name = getName(parts);

        return createCommand(command, name);
    }

    /**
     * Creates a Command object based on the given command string and optional name.
     *
     * @param command the command keyword
     * @param name optional argument (e.g., robot or save name)
     * @return the corresponding Command instance
     */
    private Command createCommand(String command, String name) {
        return switch (command.toLowerCase()) {
            case "dump"     -> new DumpCommand(console, world, socketHandler.port());
            case "robots"   -> new RobotsCommand(console, world);
            case "purge"    -> new PurgeCommand(console, world, name);
            case "help"     -> new HelpCommand(console, world, socketHandler);
            case "quit"     -> new QuitCommand(console, world, socketHandler);
            case "save"     -> new SaveCommand(console, world, name, database);
            case "restore"  -> new RestoreCommand(console, world, name);
            default         -> new UnknownCommand(console, world);
        };
    }

    /**
     * Splits input into parts using whitespace as the delimiter.
     *
     * @param input the raw input string
     * @return array of input parts
     */
    private String[] splitInput(String input) {
        return input.trim().split("\\s+");
    }

    /**
     * Extracts the optional name argument from the input parts.
     *
     * @param parts the input parts array
     * @return the second element if present, otherwise null
     */
    private String getName(String[] parts) {
        return parts.length > 1 ? parts[1] : null;
    }
}
