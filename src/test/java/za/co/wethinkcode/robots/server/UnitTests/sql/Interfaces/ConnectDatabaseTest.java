package za.co.wethinkcode.robots.server.UnitTests.sql.Interfaces;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.data.sql.ConnectDatabase;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

class ConnectDatabaseTest {

    @AfterEach
    void cleanup() throws Exception {
        // Ensure shared test connection is cleaned up
        Connection conn = DriverManager.getConnection(ConnectDatabase.TEST_DB_URL);
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    void testUsesTestDatabaseUrlWhenNotProduction() throws Exception {
        ConnectDatabase db = new ConnectDatabase(false);
        Connection conn = db.getConnection();

        assertNotNull(conn);
        assertFalse(conn.isClosed());
        assertEquals("jdbc:sqlite:src/test/resources/test.db", ConnectDatabase.TEST_DB_URL);
    }

    @Test
    void testUsesDiskDatabaseUrlWhenProduction() throws Exception {
        ConnectDatabase db = new ConnectDatabase(true);
        Connection conn = db.getConnection();

        assertNotNull(conn);
        assertFalse(conn.isClosed());
        // Production DB should NOT be the same URL as the test DB
        assertNotEquals(ConnectDatabase.TEST_DB_URL, ConnectDatabase.DISK_DB_URL);
    }

    @Test
    void testTestDbReturnsSharedConnection() throws Exception {
        ConnectDatabase db = new ConnectDatabase(false);

        Connection first = db.getConnection();
        Connection second = db.getConnection();

        assertSame(first, second,
                "Test DB should reuse the same shared in-memory connection");
    }

    @Test
    void testProductionDbReturnsNewConnectionEveryTime() throws Exception {
        ConnectDatabase db = new ConnectDatabase(true);

        Connection first = db.getConnection();
        Connection second = db.getConnection();

        assertNotSame(first, second,
                "Production DB must create a new connection each time");
    }
}
