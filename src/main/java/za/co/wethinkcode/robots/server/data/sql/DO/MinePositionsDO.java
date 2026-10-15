package za.co.wethinkcode.robots.server.data.sql.DO;

import net.lemnik.eodsql.ResultColumn;

/**
 * Data object representing a stored mine position in a world.
 */
public class MinePositionsDO {
    public MinePositionsDO() {
    }

    /** X coordinate of the mine. */
    @ResultColumn("world_id")
    public int world_id;

    /** X coordinate of the mine. */
    @ResultColumn("x")
    public int x;

    /** Y coordinate of the mine. */
    @ResultColumn("y")
    public int y;
}
