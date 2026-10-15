package za.co.wethinkcode.robots.server.UnitTests.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.persistence.RestoreCommand;
import za.co.wethinkcode.robots.server.model.world.command.persistence.SaveCommand;
import za.co.wethinkcode.robots.server.Utils.PersistenceTestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestoreCommandTest extends PersistenceTestBase {

    @Test
    void blankWorldName() {
        new RestoreCommand(console, world, "").execute();
        assertEquals("No world name provided for restore.", console.lastError);
    }

    @Test
    void worldNotFoundInDatabase() {
        new RestoreCommand(console, world, "MissingWorld").execute();
        assertEquals("World not found in database: MissingWorld", console.lastError);
    }

    @Test
    void fullRestoreSuccess() {
        String worldName = "RestoredWorld_" + System.currentTimeMillis();
        world.resize(20, 15);
        new SaveCommand(console, world, worldName, database).execute();

        world = new World(new Dimension(5, 5));
        console.reset();
        
        new RestoreCommand(console, world, worldName, database).execute();
        assertEquals(worldName, console.lastRestore);
    }
}
