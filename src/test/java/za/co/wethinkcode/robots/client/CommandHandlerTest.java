package za.co.wethinkcode.robots.client;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.*;

import com.google.gson.JsonObject;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for validating command parsing and input validation logic
 * handled by the {@link CommandHandler} class.
 */
public class CommandHandlerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    @DisplayName("Validates launch with correct args")
    void testLaunchValidation() {
        boolean valid = CommandHandler.validateUserInput("Optimus launch 3 4");
        assertTrue(valid);

        JsonObject request = CommandHandler.handle("Optimus launch 3 4");
        assertNotNull(request);
        assertEquals("optimus", request.get("robot").getAsString());
        assertEquals("launch", request.get("command").getAsString());
    }

    @Test
    @DisplayName("Validates forward command with numeric steps")
    void testForwardValid() {
        boolean valid = CommandHandler.validateUserInput("BumbleBee forward 5");
        assertTrue(valid);

        JsonObject request = CommandHandler.handle("BumbleBee forward 5");
        assertEquals("forward", request.get("command").getAsString());
        assertEquals(5, request.getAsJsonArray("arguments").get(0).getAsInt());
    }

    @Test
    @DisplayName("Rejects invalid argument for forward/back command")
    void testForwardInvalidArg() {
        boolean valid = CommandHandler.validateUserInput("BumbleBee forward three");
        assertFalse(valid);
    }

    @Test
    @DisplayName("Rejects not enough args")
    void testNotEnoughArgs() {
        boolean valid = CommandHandler.validateUserInput("Megatron");
        assertFalse(valid);
    }

    @Test
    @DisplayName("Validates turn commands")
    void testTurnCommands() {
        // Validation
        assertTrue(CommandHandler.validateUserInput("Jazz turn left"));
        assertTrue(CommandHandler.validateUserInput("Jazz turn right"));

        // Handle requests
        JsonObject leftRequest = CommandHandler.handle("Jazz turn left");
        assertEquals("turn", leftRequest.get("command").getAsString());
        assertEquals("left", leftRequest.getAsJsonArray("arguments").get(0).getAsString());

        JsonObject rightRequest = CommandHandler.handle("Jazz turn right");
        assertEquals("turn", rightRequest.get("command").getAsString());
        assertEquals("right", rightRequest.getAsJsonArray("arguments").get(0).getAsString());
    }


    @Test
    @DisplayName("Validates repair, reload, fire, state, look")
    void testSimpleCommands() {
        String[] commands = {"repair", "reload", "fire", "state", "look"};
        for (String cmd : commands) {
            assertTrue(CommandHandler.validateUserInput("Optimus " + cmd));

            JsonObject request = CommandHandler.handle("Optimus " + cmd);
            assertEquals(cmd.equals("back") ? "back" : cmd.equals("forward") ? "forward" : cmd.equals("left") || cmd.equals("right") ? "turn" : cmd, request.get("command").getAsString());
        }
    }
}
