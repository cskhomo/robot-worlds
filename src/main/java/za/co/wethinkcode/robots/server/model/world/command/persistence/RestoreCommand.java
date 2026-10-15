package za.co.wethinkcode.robots.server.model.world.command.persistence;

import net.lemnik.eodsql.QueryTool;
import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.data.sql.ConnectDatabase;
import za.co.wethinkcode.robots.server.data.sql.DAI.WorldDAI;
import za.co.wethinkcode.robots.server.data.sql.DO.WorldDO;
import za.co.wethinkcode.robots.server.data.sql.DO.MinePositionsDO;
import za.co.wethinkcode.robots.server.data.sql.DO.ObstaclePositionsDO;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents the "restore" command for a world.
 * <p>
 * This command loads a world configuration from the database, including
 * its dimensions, obstacles, and mine positions, and applies them to the
 * current world instance.
 * </p>
 */
public class RestoreCommand extends Command {

    private final String worldName;
    private final ConnectDatabase database;

    /**
     * Constructs a RestoreCommand with console, world, and world name.
     *
     * @param console   The console output for messages.
     * @param world     The world instance to restore.
     * @param worldName The name of the world to restore from the database.
     */
    public RestoreCommand(ConsoleOutput console, World world, String worldName) {
        this(console, world, worldName, new ConnectDatabase(true));
    }

    /**
     * Constructs a RestoreCommand with a custom database connection.
     *
     * @param console   The console output for messages.
     * @param world     The world instance to restore.
     * @param worldName The name of the world to restore from the database.
     * @param database  The database connection utility.
     */
    public RestoreCommand(ConsoleOutput console, World world, String worldName, ConnectDatabase database) {
        super(console, world);
        this.worldName = worldName;
        this.database = database;
        world.rename(worldName);
    }

    /**
     * Default constructor for cases where world and console are set later.
     */
    public RestoreCommand() {
        super(null, null);
        this.worldName = null;
        this.database = null;
    }

    /**
     * Executes the restore command by loading the world configuration,
     * obstacles, and mines from the database.
     */
    @Override
    public void execute() {
        if (isWorldNameInvalid()) return;

        try (Connection conn = database.getConnection()) {

            WorldDAI worldDao = QueryTool.getQuery(conn, WorldDAI.class);
            WorldDO worldData = loadWorld(worldDao);
            if (worldData == null) return;

            restoreObstacles(conn, worldData.id);
            restoreMines(conn, worldData.id);

            console.printRestoreResponse(worldName);

        } catch (SQLException e) {
            console.printError("Error restoring world: " + e.getMessage());
        }
    }


    private boolean isWorldNameInvalid() {
        if (worldName == null || worldName.isBlank()) {
            console.printError("No world name provided for restore.");
            return true;
        }
        return false;
    }

    private WorldDO loadWorld(WorldDAI worldDao) {
        WorldDO worldData = worldDao.getWorld(worldName);

        if (worldData == null) {
            console.printError("World not found in database: " + worldName);
            return null;
        }

        world.rename(worldData.name);
        world.resize(worldData.width, worldData.height);

        return worldData;
    }

    private void restoreObstacles(Connection conn, int worldId) throws SQLException {
        ObstaclePositionsDO[] obstacles = RestoreHelper.getObstacles(conn, worldId);
        for (ObstaclePositionsDO o : obstacles) {
            addObstacle(o);
        }
    }

    private void addObstacle(ObstaclePositionsDO o) {
        String type = mapType(o.typeCode);
        Position topLeft = new Position(o.x, o.y);
        Position bottomRight = new Position(o.x + o.width - 1, o.y + o.height - 1);
        world.addObstacle(type, topLeft, bottomRight);
    }

    private void restoreMines(Connection conn, int worldId) throws SQLException {
        MinePositionsDO[] mines = RestoreHelper.getMines(conn, worldId);
        for (MinePositionsDO m : mines) {
            world.addMine(new Position(m.x, m.y));
        }
    }

    private String mapType(String typeCode) {
        return switch (typeCode) {
            case "M" -> "mountain";
            case "L" -> "lake";
            case "P" -> "pit";
            default -> "unknown";
        };
    }


    @Override
    public String name() {
        return "restore";
    }

    @Override
    public String description() {
        return "Restores world configuration from a database.";
    }
}
