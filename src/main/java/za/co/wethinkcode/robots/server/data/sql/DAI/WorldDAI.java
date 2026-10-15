package za.co.wethinkcode.robots.server.data.sql.DAI;

import net.lemnik.eodsql.*;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.robots.server.data.sql.DO.WorldDO;

import java.util.List;

/**
 * Data access interface for world records.
 */
public interface  WorldDAI extends BaseQuery {

    /** Returns a world by name. */
    @Select("SELECT id, name, width, height FROM worlds WHERE name = ?{1}")
    WorldDO getWorld(String worldName);

    /** Returns all worlds. */
    @Select("SELECT name, width, height FROM worlds")
    List<WorldDO> getAllWorlds();

    /** Inserts a new world. */
    @Update("INSERT INTO worlds (name, width, height) VALUES (?{1}, ?{2}, ?{3})")
    void addWorld(String name, int width, int height);

    /** Deletes a world by name. */
    @Update("DELETE FROM worlds WHERE name = ?{1}")
    void deleteWorld(String worldName);
}
