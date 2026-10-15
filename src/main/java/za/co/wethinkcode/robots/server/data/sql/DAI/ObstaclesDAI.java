package za.co.wethinkcode.robots.server.data.sql.DAI;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.robots.server.data.sql.DO.ObstaclePositionsDO;

/**
 * Data Access Interface for reading and writing obstacle data
 * within a specific world using the EoD SQL mapping framework.
 * <p>
 * This interface provides operations for retrieving obstacle
 * definitions, inserting or updating obstacle positions, and
 * managing related database tables.
 */

public interface ObstaclesDAI extends BaseQuery {

    /**
     * Retrieves all obstacle records for the specified world.
     * <p>
     * The query currently selects a single row using {@code LIMIT 1},
     * so the method returns an array containing zero or one obstacle.
     * Multi-row queries must be handled elsewhere.
     *
     * @param worldId the ID of the world whose obstacles should be retrieved.
     * @return an array of {@link ObstaclePositionsDO}; may be empty if no obstacles exist.
     */
    // Only single-row queries (multi-row will be done manually)
    @Select(
            "SELECT x, y, width, height, type_code " +
                    "FROM obstacle_positions " +
                    "WHERE world_id = ?{1} LIMIT 1"
    )
    ObstaclePositionsDO[] getObstaclesInWorld(int worldId);

    /**
     * Inserts a new obstacle or replaces an existing one at the same position.
     * <p>
     * Uses {@code INSERT OR REPLACE} to allow idempotent updates.
     *
     * @param worldId   the world to which this obstacle belongs.
     * @param x         the X-coordinate of the obstacle.
     * @param y         the Y-coordinate of the obstacle.
     * @param typeCode  the obstacle type code (e.g., "M", "L", "P").
     * @param width     the obstacle width.
     * @param height    the obstacle height.
     */
    @Update(
            "INSERT OR REPLACE INTO obstacle_positions " +
                    "(world_id, x, y, type_code, width, height) " +
                    "VALUES (?{1}, ?{2}, ?{3}, ?{4}, ?{5}, ?{6})"
    )
    void addOrReplaceObstacle(
            int worldId, int x, int y, String typeCode, int width, int height
    );

    /**
     * Inserts default obstacle types into the {@code obstacle_types} table
     * if they do not already exist.
     * <p>
     * The default types are:
     * <ul>
     *   <li><strong>M – mountain</strong> (blocks vision and path)</li>
     *   <li><strong>L – lake</strong> (blocks path only)</li>
     *   <li><strong>P – pit</strong> (does not block vision or path)</li>
     * </ul>
     */
    @Update(
            "INSERT OR IGNORE INTO obstacle_types (code, name, blocks_vision, blocks_path) " +
                    "VALUES  ('M', 'mountain', 1, 1), " +
                    "        ('L', 'lake',     0, 1), " +
                    "        ('P', 'pit',      0, 0)"
    )
    void insertDefaultObstacleTypes();


    /**
     * Drops the {@code obstacle_positions} table if it exists.
     * This is typically used during schema resets or migrations.
     */
    @Update("DROP TABLE IF EXISTS obstacle_positions")
    void dropObstaclePositionsTable();

    /**
     * Drops the {@code obstacle_types} table if it exists.
     * This is typically used during schema resets or migrations.
     */
    @Update("DROP TABLE IF EXISTS obstacle_types")
    void dropObstacleTypesTable();
}
