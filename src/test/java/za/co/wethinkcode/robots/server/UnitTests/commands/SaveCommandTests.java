package za.co.wethinkcode.robots.server.UnitTests.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.world.command.persistence.SaveCommand;
import za.co.wethinkcode.robots.server.Utils.PersistenceTestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveCommandTests extends PersistenceTestBase {

    @Test
    void blankWorldName() {
        new SaveCommand(console, world, "", database).execute();
        assertEquals("No world name provided for save.", console.lastError);
    }

    @Test
    void normalSave() {
        String worldName = "TestWorld_" + System.currentTimeMillis();
        new SaveCommand(console, world, worldName, database).execute();
        assertEquals(worldName, console.lastSave);
    }
}
