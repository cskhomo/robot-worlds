package za.co.wethinkcode.robots.server.cli;

import za.co.wethinkcode.robots.server.model.world.CommandHandler;
import za.co.wethinkcode.robots.server.data.sql.ConnectDatabase;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.*;
import za.co.wethinkcode.robots.server.model.world.command.information.*;
import za.co.wethinkcode.robots.server.network.SocketHandler;

import java.util.Scanner;

/**
 * Handles console input for server administration commands.
 * Runs in a separate thread to process admin commands like dump, robots, purge, etc.
 */
public class ConsoleInput implements Runnable {

    private final ConsoleOutput console;
    private final World world;
    private final SocketHandler socketHandler;
    private final Scanner scanner;
    private final ConnectDatabase database;
    private final CommandHandler commandHandler;

    /**
     * Creates a console input handler for server administration.
     *
     * @param console the console output handler
     * @param world the game world instance
     * @param socketHandler the network socket handler
     */
    public ConsoleInput(ConsoleOutput console, World world, SocketHandler socketHandler) {
        this.console = console;
        this.world = world;
        this.socketHandler = socketHandler;
        this.scanner = new Scanner(System.in);
        this.database = new ConnectDatabase(true);
        this.commandHandler = new CommandHandler(console, world, socketHandler, database);
    }

    /**
     * Main execution loop for processing console commands.
     * Displays help initially, then continuously processes user input.
     */
    @Override
    public void run() {
        new HelpCommand(console, world, socketHandler).execute();

        while (true) {
            console.printPrompt();
            String input = readInput();
            if (input.isEmpty()) continue;

            Command command = commandHandler.parse(input);
            executeCommand(command);
        }
    }

    /**
     * Reads a line of input from the console and trims whitespace.
     *
     * @return the trimmed input string
     */
    private String readInput() {
        return scanner.nextLine().trim();
    }

    /**
     * Executes a given command.
     *
     * @param command the command to execute
     */
    private void executeCommand(Command command) {
        command.execute();
    }
}
