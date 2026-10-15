package za.co.wethinkcode.robots.server.Utils;

import za.co.wethinkcode.robots.server.model.world.World;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

public class WorldDatabaseAssertions {

    public static void verifyWorldSaved(Connection conn, World world, String worldName) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM worlds WHERE name = ?")) {
            ps.setString(1, worldName);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "World should be saved");
                assertEquals(worldName, ResultSetSafeReader.safeGetString(rs, "name"));
                assertEquals(world.dimension().height(), ResultSetSafeReader.safeGetIntAny(rs, "height"));
                assertEquals(world.dimension().width(), ResultSetSafeReader.safeGetIntAny(rs, "width"));
            }
        }
    }

    public static void verifyMinesSaved(Connection conn, World world, String worldName) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT mp.x, mp.y FROM mine_positions mp " +
                        "JOIN worlds w ON mp.world_id = w.id WHERE w.name = ?")) {
            ps.setString(1, worldName);
            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    int x = ResultSetSafeReader.safeGetIntAny(rs, "x");
                    int y = ResultSetSafeReader.safeGetIntAny(rs, "y");
                    assertTrue(world.mines().stream()
                            .anyMatch(m -> m.position().X() == x && m.position().Y() == y));
                    count++;
                }
                assertEquals(world.mines().size(), count, "All mines should be saved");
            }
        }
    }

    public static void verifyObstaclesSaved(Connection conn, World world, String worldName) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT op.x, op.y, op.width, op.height, op.type_code FROM obstacle_positions op " +
                        "JOIN worlds w ON op.world_id = w.id WHERE w.name = ?")) {
            ps.setString(1, worldName);
            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    int x = ResultSetSafeReader.safeGetIntAny(rs, "x");
                    int y = ResultSetSafeReader.safeGetIntAny(rs, "y");
                    int width = ResultSetSafeReader.safeGetIntAny(rs, "width");
                    int height = ResultSetSafeReader.safeGetIntAny(rs, "height");
                    String typeCode = ResultSetSafeReader.safeGetString(rs, "type_code");

                    assertTrue(world.obstacles().stream().anyMatch(o ->
                            o.topLeft().X() == x &&
                                    o.topLeft().Y() == y &&
                                    o.width() == width &&
                                    o.height() == height &&
                                    o.name().substring(0, 1).equalsIgnoreCase(typeCode)
                    ));
                    count++;
                }
                assertEquals(world.obstacles().size(), count, "All obstacles should be saved");
            }
        }
    }
}
