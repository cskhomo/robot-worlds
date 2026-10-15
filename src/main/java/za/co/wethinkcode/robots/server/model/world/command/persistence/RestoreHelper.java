package za.co.wethinkcode.robots.server.model.world.command.persistence;

import za.co.wethinkcode.robots.server.data.sql.DO.MinePositionsDO;
import za.co.wethinkcode.robots.server.data.sql.DO.ObstaclePositionsDO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Utility class to assist the RestoreCommand by fetching
 * obstacle and mine data from the database.
 * <p>
 * Provides static methods to query the database for a given world ID
 * and return arrays of {@link ObstaclePositionsDO} and {@link MinePositionsDO}.
 * </p>
 */
public class RestoreHelper {

    /**
     * Retrieves all obstacles for a given world from the database.
     *
     * @param conn    The database connection.
     * @param worldId The ID of the world to fetch obstacles for.
     * @return An array of {@link ObstaclePositionsDO} representing the obstacles.
     * @throws SQLException If a database access error occurs.
     */
    public static ObstaclePositionsDO[] getObstacles(Connection conn, int worldId) throws SQLException {
        String sql = "SELECT x, y, width, height, type_code FROM obstacle_positions WHERE world_id = ?";
        ArrayList<ObstaclePositionsDO> list = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, worldId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ObstaclePositionsDO o = new ObstaclePositionsDO();
                    o.x = rs.getInt("x");
                    o.y = rs.getInt("y");
                    o.width = rs.getInt("width");
                    o.height = rs.getInt("height");
                    o.typeCode = rs.getString("type_code");
                    list.add(o);
                }
            }
        }

        return list.toArray(new ObstaclePositionsDO[0]);
    }

    /**
     * Retrieves all mines for a given world from the database.
     *
     * @param conn    The database connection.
     * @param worldId The ID of the world to fetch mines for.
     * @return An array of {@link MinePositionsDO} representing the mines.
     * @throws SQLException If a database access error occurs.
     */
    public static MinePositionsDO[] getMines(Connection conn, int worldId) throws SQLException {
        String sql = "SELECT x, y FROM mine_positions WHERE world_id = ?";
        ArrayList<MinePositionsDO> list = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, worldId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MinePositionsDO m = new MinePositionsDO();
                    m.x = rs.getInt("x");
                    m.y = rs.getInt("y");
                    list.add(m);
                }
            }
        }

        return list.toArray(new MinePositionsDO[0]);
    }
}
