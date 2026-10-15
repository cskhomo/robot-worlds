package za.co.wethinkcode.robots.server.data.sql.DO;

import net.lemnik.eodsql.ResultColumn;

/**
 * Data object representing a type of obstacle stored in the database.
 * <p>
 * Each record describes what kind of obstacle it is (e.g., mountain, lake, pit)
 * and how it affects robots (vision blocking or path blocking).
 * </p>
 */
public class ObstacleTypesDO {

    /** Creates an empty ObstacleTypesDO. Required by EoD SQL. */
    public ObstacleTypesDO() {}

    /** Short unique code identifying the obstacle type (e.g., "M", "L", "P"). */
    @ResultColumn("code")
    public String code;

    /** Full readable name of the obstacle (e.g., "mountain"). */
    @ResultColumn("name")
    public String name;

    /** 1 if this obstacle blocks robot vision, 0 if not. */
    @ResultColumn("blocks_vision")
    public int blocksVision;

    /** 1 if this obstacle blocks robot movement, 0 if not. */
    @ResultColumn("blocks_path")
    public int blocksPath;


}
