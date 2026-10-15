package za.co.wethinkcode.robots.server.data.sql.interfaces;

import za.co.wethinkcode.robots.server.model.world.World;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Defines low-level database operations for reading, updating, and deleting
 * world-related tables. This interface abstracts direct SQL interactions so
 * that different database implementations can handle how world data is
 * stored and retrieved.
 *
 * <p>The methods require a {@link Connection} object, allowing the caller
 * to control transaction handling, connection pooling, or error management.</p>
 */
public interface DatabaseInterface {

    /**
     * Reads the table associated with the given world name.
     * <p>
     * Implementations should query the database and return all rows
     * that belong to the specified world. The caller can then iterate
     * through the {@link ResultSet} to construct the required objects.
     * </p>
     *
     * @param conn       an open database connection.
     * @param worldName  the name of the world whose table/data to read.
     * @return a {@link ResultSet} containing all matching rows.
     */
    ResultSet readTable(Connection conn, String worldName);

    /**
     * Updates the database table using information from the given {@link World} object.
     * <p>
     * Implementations may insert, update, or replace rows depending on how the world
     * data is structured. This call is usually used when a world is saved or modified.
     * </p>
     *
     * @param conn   an open database connection.
     * @param world  the world whose data should be written to the database.
     */
    void updateTable(Connection conn, World world);

    /**
     * Deletes the entire table or all data associated with it.
     * <p>
     * Typically used when resetting a world, removing old data,
     * or reconstructing the table structure.
     * </p>
     *
     * @param conn  an open database connection.
     */
    void deleteTable(Connection conn);
}
