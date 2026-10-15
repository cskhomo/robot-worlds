package za.co.wethinkcode.robots.server.AcceptanceTests.persistance;

import org.junit.jupiter.api.*;
import za.co.wethinkcode.robots.server.Utils.WorldDatabaseAssertions;
import za.co.wethinkcode.robots.server.data.sql.ConnectDatabase;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.persistence.RestoreCommand;
import za.co.wethinkcode.robots.server.model.world.command.persistence.SaveCommand;
import za.co.wethinkcode.robots.server.Utils.AcceptanceTestBase;

import static org.mockito.Mockito.verify;

/**
 * Acceptance tests for RestoreCommand.
 * Simulates a full restore from database and validates world state.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestoreCommandAcceptanceTest extends AcceptanceTestBase {

    @BeforeEach
    public void setup() throws Exception {
        setupBase();
        world = createWorldWithEntities();
    }

    @Test
    public void testRestoreSuccessfullyLoadsWorldAndEntities() throws Exception {
        String worldName = "AcceptanceRestoreWorld_" + System.currentTimeMillis();
        World originalWorld = world;

        new SaveCommand(mockConsole, world, worldName, database).execute();
        world = new World(new Dimension(5, 5));
        
        new RestoreCommand(mockConsole, world, worldName, database).execute();
        
        verify(mockConsole).printRestoreResponse(worldName);
        
        // Verify against the original world that was saved
        try (var conn = new ConnectDatabase(false).getConnection()) {
            WorldDatabaseAssertions.verifyWorldSaved(conn, originalWorld, worldName);
            WorldDatabaseAssertions.verifyMinesSaved(conn, originalWorld, worldName);
            WorldDatabaseAssertions.verifyObstaclesSaved(conn, originalWorld, worldName);
        }
    }

    @Test
    public void testRestoreWithBlankNameDoesNotTouchWorld() throws Exception {
        new RestoreCommand(mockConsole, world, "").execute();
        
        verify(mockConsole).printError("No world name provided for restore.");
        verifyWorldNotInDatabase("");
    }
}
