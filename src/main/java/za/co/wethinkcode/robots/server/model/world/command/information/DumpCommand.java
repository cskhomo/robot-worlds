package za.co.wethinkcode.robots.server.model.world.command.information;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;

/**
 * Command that displays comprehensive server and world information.
 * Shows configuration, obstacles, and robot status.
 */
public class DumpCommand extends Command {

    private int port;
    private Dimension dimension;

    /**
     * Creates a dump command with server port information.
     * 
     * @param console the console output handler
     * @param world the game world instance
     * @param port the server port number
     */
    public DumpCommand(ConsoleOutput console, World world, int port) {
        super(console, world);
        this.port = port;
        this.dimension = world.dimension();
    }

    public DumpCommand() {
        super();
    }

    /**
     * Executes the dump command, printing all server and world information.
     */
    @Override
    public void execute() {
        console.printServerConfig(port, dimension);
        console.printWorldConfig(world);
        console.printObstacles(world.obstacles());
        console.printRobots(world.robots(), false);
    }

    @Override
    public String name() {
        return "dump";
    }

    @Override
    public String description() {
        return "displays server and world configurations.";
    }
}