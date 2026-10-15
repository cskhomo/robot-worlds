package za.co.wethinkcode.robots.server.data.sql.DAI;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.robots.server.data.sql.DO.MinePositionsDO;

/**
 * Data Access Interface (DAI) for interacting with the `mine_positions` table in the database.
 * <p>
 * This interface uses EoD SQL annotations to define SQL queries for retrieving,
 * inserting, and managing mine positions within a specific world.
 * </p>
 */
public interface MinePositionsDAI extends BaseQuery {

    /**
     * Retrieves all mine positions for a given world ID.
     * <p>
     * Executes a SQL SELECT query to fetch all (x, y) coordinates from the
     * `mine_positions` table where the `world_id` matches the specified world.
     * </p>
     *
     * @param id The ID of the world whose mine positions are to be retrieved.
     * @return An array of {@link MinePositionsDO} representing the positions of mines.
     */

    // Original single-mine method (unchanged)
    @Select("SELECT x, y FROM mine_positions WHERE world_id = ( SELECT id FROM worlds WHERE name = ?{1} )")
    MinePositionsDO[] getMinePositionsById(int id);

    /**
     * Adds a new mine position to a specified world.
     * <p>
     * Inserts a new record into the `mine_positions` table with the provided world ID and
     * coordinates (x, y).
     * </p>
     *
     * @param worldId The ID of the world where the mine will be added.
     * @param x       The X-coordinate of the mine position.
     * @param y       The Y-coordinate of the mine position.
     */
    @Update("INSERT INTO mine_positions (world_id, x, y) VALUES (?{1}, ?{2}, ?{3})")
    void addMinePosition(int worldId, int x, int y);

    /**
     * Deletes the entire `mine_positions` table if it exists.
     * <p>
     * This operation removes all mine positions from the database and drops the table.
     * Use with caution.
     * </p>
     */
    @Update("DROP TABLE IF EXISTS mine_positions")
    void deleteMinePositionsTable();
}
