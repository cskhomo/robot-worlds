package za.co.wethinkcode.robots.server.UnitTests.server.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.cli.ConsoleInput;
import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.SocketHandler;
import za.co.wethinkcode.robots.server.model.world.command.Command;
import za.co.wethinkcode.robots.server.model.world.CommandHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsoleInputTest {

    private ConsoleOutput console;
    private World world;
    private SocketHandler socketHandler;
    private ConsoleInput consoleInput;

    @BeforeEach
    void setUp() throws Exception {
        console = mock(ConsoleOutput.class);
        world = mock(World.class);
        socketHandler = mock(SocketHandler.class);

        // simulate user typing "robots" command
        String input = "robots\n";
        InputStream testInput = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(testInput);

        // create ConsoleInput instance
        consoleInput = new ConsoleInput(console, world, socketHandler);

        // replace the private scanner with our test scanner
        Field scannerField = ConsoleInput.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(consoleInput, scanner);
    }

    @Test
    void testReadInput() throws Exception {
        // access private readInput method
        Method readInput = ConsoleInput.class.getDeclaredMethod("readInput");
        readInput.setAccessible(true);

        String result = (String) readInput.invoke(consoleInput);
        assertEquals("robots", result);
    }

    @Test
    void testExecuteCommand() throws Exception {
        // access private executeCommand method
        Method executeCommand = ConsoleInput.class.getDeclaredMethod("executeCommand", Command.class);
        executeCommand.setAccessible(true);

        // access private commandHandler field
        Field commandHandlerField = ConsoleInput.class.getDeclaredField("commandHandler");
        commandHandlerField.setAccessible(true);
        CommandHandler handler = (CommandHandler) commandHandlerField.get(consoleInput);

        // parse a command
        Command command = handler.parse("robots");

        // execute it and ensure no exceptions
        executeCommand.invoke(consoleInput, command);

        // just verify the console prompt was not called yet (we didn’t run full loop)
        verify(console, never()).printPrompt();
    }
}
