package za.co.wethinkcode.robots.server.Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import za.co.wethinkcode.robots.server.data.sql.ConnectDatabase;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.geometry.Position;
import za.co.wethinkcode.robots.server.model.world.World;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class PersistenceTestBase {
    
    protected TestConsole console;
    protected World world;
    protected ConnectDatabase database;
    
    @BeforeEach
    public void setupBase() throws SQLException {
        console = new TestConsole();
        world = createDefaultWorld();
        database = new ConnectDatabase(false);
        setupDatabase();
        cleanDatabase();
    }
    
    @AfterEach
    public void tearDown() throws SQLException {
        cleanDatabase();
    }
    
    protected World createDefaultWorld() {
        return new World(new Dimension(10, 10));
    }
    
    protected World createWorldWithEntities() {
        World world = createDefaultWorld();
        world.addMine(new Position(2, 3));
        world.addMine(new Position(5, 5));
        world.addObstacle("Mountain", new Position(1, 1), new Position(2, 2));
        world.addObstacle("Lake", new Position(4, 4), new Position(1, 1));
        return world;
    }
    
    private void setupDatabase() throws SQLException {
        try (Connection conn = database.getConnection()) {
            za.co.wethinkcode.robots.server.data.sql.CreateDatabase.createTablesIfNotExists(conn);
        }
    }
    
    private void cleanDatabase() throws SQLException {
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM obstacle_positions");
            stmt.execute("DELETE FROM mine_positions");
            stmt.execute("DELETE FROM worlds");
        }
    }
}