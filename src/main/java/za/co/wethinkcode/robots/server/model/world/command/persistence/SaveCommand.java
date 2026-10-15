package za.co.wethinkcode.robots.server.model.world.command.persistence;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.data.sql.ConnectDatabase;
import za.co.wethinkcode.robots.server.data.sql.DAI.*;
import za.co.wethinkcode.robots.server.data.sql.DO.MinePositionsDO;
import za.co.wethinkcode.robots.server.data.sql.DO.ObstaclePositionsDO;
import za.co.wethinkcode.robots.server.data.sql.DO.ObstacleTypesDO;
import za.co.wethinkcode.robots.server.data.sql.DO.WorldDO;
import za.co.wethinkcode.robots.server.model.robot.state.Mine;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.lemnik.eodsql.QueryTool;

/**
 * Represents the "save" command for a world.
 * <p>
 * This command saves the current world configuration to the database,
 * including its dimensions, obstacles, and mine positions.
 * </p>
 */
public class SaveCommand extends Command {

    private final String worldName;
    private final ConnectDatabase database;

    /**
     * Constructs a SaveCommand with console, world, world name, and database.
     *
     * @param console   The console output for messages.
     * @param world     The world instance to save.
     * @param worldName The name to use for saving the world.
     * @param database  The database connection utility.
     */
    public SaveCommand(ConsoleOutput console, World world, String worldName, ConnectDatabase database) {
        super(console, world);
        this.worldName = worldName;
        world.rename(worldName);
        this.database = database;
    }

    /**
     * Default constructor for cases where world and console are set later.
     */
    public SaveCommand() {
        super(null, null);
        this.database = null;
        this.worldName = null;
    }

    /**
     * Executes the save command by writing the world configuration,
     * obstacles, and mines to the database.
     */
    @Override
    public void execute() {

        if (worldName == null || worldName.isBlank()) {
            console.printError("No world name provided for save.");
            return;
        }

        try (Connection conn = database.getConnection()) {

            WorldDAI worldDao = QueryTool.getQuery(conn, WorldDAI.class);
            ObstaclesDAI obstacleDao = QueryTool.getQuery(conn, ObstaclesDAI.class);
            MinePositionsDAI mineDao = QueryTool.getQuery(conn, MinePositionsDAI.class);

            worldDao.addWorld(worldName, world.dimension().width(),  world.dimension().height());
            int worldId = worldDao.getWorld(worldName).id;
            obstacleDao.insertDefaultObstacleTypes();

            for (Mine mine : world.mines()) {
                mineDao.addMinePosition(
                        worldId,
                        mine.position().X(),
                        mine.position().Y()
                );
            }

            world.obstacles().forEach(obstacle -> {
                String typeCode = obstacle.name().substring(0, 1).toUpperCase();
                obstacleDao.addOrReplaceObstacle(
                        worldId,
                        obstacle.topLeft().X(),
                        obstacle.topLeft().Y(),
                        typeCode,
                        obstacle.width(),
                        obstacle.height()
                );
            });

            console.printSaveResponse(worldName);

        } catch (SQLException e) {
            console.printError("Error saving world: " + e.getMessage());
        }
    }

    @Override
    public String name() {
        return "save";
    }

    @Override
    public String description() {
        return "Saves world configuration into the database.";
    }
}
