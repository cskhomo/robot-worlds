package za.co.wethinkcode.robots.server.data.sql.DO;

import net.lemnik.eodsql.ResultColumn;

/**
 * Data object representing a single obstacle record stored in the database.
 * <p>
 * Each field in this class maps directly to a column in the
 * <code>obstacle_positions</code> table. It is used by EoD SQL to load
 * obstacle data into Java objects.
 * </p>
 */
public class ObstaclePositionsDO {

    /** Default constructor required by EoD SQL. */
    public ObstaclePositionsDO() {}

    /** ID of the world this obstacle belongs to. */
    @ResultColumn("world_id")
    public int worldId;

    /** X-coordinate of the obstacle's position. */
    @ResultColumn("x")
    public int x;

    /** Y-coordinate of the obstacle's position. */
    @ResultColumn("y")
    public int y;

    /** Short code representing the obstacle type (e.g., 'M', 'L', 'P'). */
    @ResultColumn("type_code")
    public String typeCode;

    /** Width of the obstacle. */
    @ResultColumn("width")
    public int width;

    /** Height of the obstacle. */
    @ResultColumn("height")
    public int height;


}
