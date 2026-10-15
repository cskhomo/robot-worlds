package za.co.wethinkcode.robots.server.AcceptanceTests.persistance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.model.world.command.persistence.SaveCommand;
import za.co.wethinkcode.robots.server.Utils.AcceptanceTestBase;

import static org.mockito.Mockito.verify;

public class SaveCommandAcceptanceTests extends AcceptanceTestBase {

    @BeforeEach
    public void setup() throws Exception {
        setupBase();
        world = createWorldWithEntities();
    }

    @Test
    public void testSavePersistsWorldAndEntities() throws Exception {
        String name = "AcceptanceWorld_" + System.currentTimeMillis();
        new SaveCommand(mockConsole, world, name, database).execute();
        
        verify(mockConsole).printSaveResponse(name);
        verifyWorldPersistence(name);
    }
    @Test
    public void testSavingWithoutNameDoesNotTouchDB() throws Exception {
        new SaveCommand(mockConsole, world, "", database).execute();
        
        verify(mockConsole).printError("No world name provided for save.");
        verifyWorldNotInDatabase("");
    }

}
