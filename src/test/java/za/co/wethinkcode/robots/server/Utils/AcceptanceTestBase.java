package za.co.wethinkcode.robots.server.Utils;

import org.junit.jupiter.api.BeforeEach;
import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.data.sql.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public abstract class AcceptanceTestBase extends PersistenceTestBase {
    
    protected ConsoleOutput mockConsole;
    
    @BeforeEach
    @Override
    public void setupBase() throws SQLException {
        super.setupBase();
        mockConsole = mock(ConsoleOutput.class);
    }
    
    protected void verifyWorldPersistence(String worldName) throws Exception {
        try (Connection conn = new ConnectDatabase(false).getConnection()) {
            WorldDatabaseAssertions.verifyWorldSaved(conn, world, worldName);
            WorldDatabaseAssertions.verifyMinesSaved(conn, world, worldName);
            WorldDatabaseAssertions.verifyObstaclesSaved(conn, world, worldName);
        }
    }
    
    protected void verifyWorldNotInDatabase(String worldName) throws SQLException {
        try (Connection conn = new ConnectDatabase(false).getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM worlds WHERE name = ?")) {
            
            ps.setString(1, worldName);
            try (ResultSet rs = ps.executeQuery()) {
                assertFalse(rs.next(), "World should not exist in database: " + worldName);
            }
        }
    }
}