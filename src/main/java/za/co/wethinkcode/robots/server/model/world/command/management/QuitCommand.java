package za.co.wethinkcode.robots.server.model.world.command.management;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;
import za.co.wethinkcode.robots.server.network.SocketHandler;

/**
 * Command that gracefully shuts down the server.
 * Closes socket connections and terminates the application.
 */
public class QuitCommand extends Command {

    private SocketHandler socketHandler;

    /**
     * Creates a quit command with socket handler for cleanup.
     * 
     * @param console the console output handler
     * @param world the game world instance
     * @param socketHandler the socket handler to shutdown
     */
    public QuitCommand(ConsoleOutput console, World world, SocketHandler socketHandler) {
        super(console, world);
        this.socketHandler = socketHandler;
    }

    public QuitCommand() {
    }

    /**
     * Executes server shutdown sequence.
     */
    @Override
    public void execute() {
        console.printServerShutdown();
        socketHandler.shutdown();
        System.exit(0);
    }

    @Override
    public String name() {
        return "quit";
    }

    @Override
    public String description() {
        return "gracefully shuts down the server.";
    }
}