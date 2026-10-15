package za.co.wethinkcode.robots.server.UnitTests.sql.Interfaces;

import org.junit.jupiter.api.*;
import za.co.wethinkcode.robots.server.data.sql.interfaces.DatabaseInterface;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.world.World;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class DatabaceInterfaceTest {

    private static Connection conn;
    private DatabaseInterface db;

    @BeforeAll
    static void setupDatabase() throws SQLException {
        // Use in-memory SQLite for tests
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE world_data (width INTEGER, height INTEGER)");
        }
    }

    @AfterAll
    static void closeDatabase() throws SQLException {
        conn.close();
    }

    @BeforeEach
    void setup() {
        // Replace with your real DatabaseInterface implementation
        db = new DatabaseInterface() {
            @Override
            public ResultSet readTable(Connection conn, String worldName) {
                try {
                    Statement stmt = conn.createStatement();
                    return stmt.executeQuery("SELECT * FROM world_data");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void updateTable(Connection conn, World world) {
                try {
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO world_data (width, height) VALUES (?, ?)");
                    ps.setInt(1, world.dimension().width());
                    ps.setInt(2, world.dimension().height());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void deleteTable(Connection conn) {
                try {
                    Statement stmt = conn.createStatement();
                    stmt.execute("DELETE FROM world_data");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Test
    void updateAndReadWorld_passes() throws SQLException {
        Dimension dim = new Dimension(10, 10);
        World world = new World(dim); // Only Dimension

        db.updateTable(conn, world);

        ResultSet rs = db.readTable(conn, "unused_name"); // name ignored
        assertNotNull(rs);
        assertTrue(rs.next());
        assertEquals(10, rs.getInt("width"));
        assertEquals(10, rs.getInt("height"));
    }

    @Test
    void deleteWorld_passes() throws SQLException {
        Dimension dim = new Dimension(5, 5);
        World world = new World(dim);

        db.updateTable(conn, world);

        db.deleteTable(conn);

        ResultSet rs = db.readTable(conn, "unused_name");
        assertFalse(rs.next(), "Table should be empty after delete");
    }

    @Test
    void readEmptyTable_returnsEmpty() throws SQLException {
        db.deleteTable(conn); // ensure empty table

        ResultSet rs = db.readTable(conn, "unused_name");
        assertNotNull(rs);
        assertFalse(rs.next(), "Empty table should have no rows");
    }
}
