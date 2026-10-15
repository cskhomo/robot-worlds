package za.co.wethinkcode.robots.server.data.sql.DO;

import net.lemnik.eodsql.ResultColumn;

/**
 * Data object representing a world record stored in the database.
 * <p>
 * A world contains basic metadata such as its name and
 * grid dimensions. Retrieved from the <code>worlds</code> table.
 * </p>
 */
public class WorldDO {
    public WorldDO() {
    }

    /** Unique ID of the world. */
    @ResultColumn("id")
    public int id;

    /** Name of the world. */
    @ResultColumn("name")
    public String name;

    /** Height of the world grid. */
    @ResultColumn("height")
    public int height;

    /** Width of the world grid. */
    @ResultColumn("width")
    public int width;

}
