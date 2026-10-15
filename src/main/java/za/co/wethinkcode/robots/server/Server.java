package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.flow.Recorder;
import org.apache.commons.cli.ParseException;
import za.co.wethinkcode.robots.server.cli.*;
import za.co.wethinkcode.robots.server.data.sql.ConnectDatabase;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.SocketHandler;

import java.io.IOException;

/**
 * Main server class for the Robot World multiplayer game.
 * Handles server startup, world creation, database initialization,
 * and manages client connections through socket handling.
 */
public class Server {

    private final ConsoleOutput console = new ConsoleOutput();

    /**
     * Entry point for the Robot World server application.
     * 
     * @param args command line arguments for server configuration
     */
    public static void main(String[] args) {
        new Server().run(args);
    }

    /**
     * Main server execution method that initializes and starts the server.
     * Parses arguments, creates world, sets up database, and starts networking.
     * 
     * @param args command line arguments
     */
    private void run(String[] args) {

        ArgumentParser.Config config = parseArgs(args);
        World world = buildWorld(config.worldSize());
        createDatabase();

        console.printWelcome();
        startServer(config.port(), world);
    }

    /**
     * Parses command line arguments for server configuration.
     * 
     * @param args command line arguments
     * @return parsed configuration object
     */
    private ArgumentParser.Config parseArgs(String[] args) {
        try {
            return ArgumentParser.parse(args);
        } catch (ParseException e) {
            console.printError("Invalid arguments: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    /**
     * Creates a new world instance with the specified dimensions.
     * 
     * @param size the dimensions for the world
     * @return new World instance
     */
    private World buildWorld(Dimension size) {
        return new World(size);
    }

    /**
     * Initializes the database connection for world persistence.
     */
    private void createDatabase() {
        new ConnectDatabase(true);
    }

    /**
     * Starts the server with network and console input handling.
     * Creates separate threads for network communication and console commands.
     * 
     * @param port the port number to listen on
     * @param world the world instance to manage
     */
    private void startServer(int port, World world) {
        try {
            SocketHandler handler = new SocketHandler(port, world, console);
            new Thread(handler, "Network").start();

            ConsoleInput input = new ConsoleInput(console, world, handler);
            new Thread(input, "Console").start();

            Thread.currentThread().join();

        } catch (IOException | InterruptedException e) {
            console.printError("Server failed: " + e.getMessage());
        }
    }

    static {
        // Used for logging or tracking that the server has started
        new Recorder().logRun();
    }
}