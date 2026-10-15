package za.co.wethinkcode.robots.server.UnitTests.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.SocketHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SocketHandler class.
 */
class SocketHandlerTest {

    private SocketHandler socketHandler;
    private final World mockWorld = mock(World.class);
    private final ConsoleOutput mockConsole = mock(ConsoleOutput.class);

    @AfterEach
    void tearDown() {
        if (socketHandler != null) {
            socketHandler.shutdown();
        }
    }

    @Test
    void testPortGetter() throws IOException {
        // Create server on an ephemeral port (0 lets OS pick free port)
        socketHandler = new SocketHandler(0, mockWorld, mockConsole);

        int port = socketHandler.port();
        assertTrue(port > 0, "Port should be greater than zero");
    }

    @Test
    void testShutdownStopsServer() throws IOException {
        socketHandler = new SocketHandler(0, mockWorld, mockConsole);
        Thread serverThread = new Thread(socketHandler);
        serverThread.start();

        socketHandler.shutdown();

        // Try connecting to the port should fail after shutdown
        assertThrows(IOException.class, () -> new Socket("localhost", socketHandler.port()));
    }

    @Test
    void testAcceptClientAndLog() throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        socketHandler = new SocketHandler(0, mockWorld, mockConsole);

        Thread serverThread = new Thread(() -> {
            latch.countDown(); // signal that server thread has started
            socketHandler.run();
        });
        serverThread.start();

        // Wait until server thread signals that it started
        latch.await();

        // Connect a test client
        try (Socket client = new Socket("localhost", socketHandler.port())) {
            // Give server a moment to accept the client
            Thread.sleep(100);

            // Verify console was notified about the connection
            verify(mockConsole, atLeastOnce()).printClientConnect(client.getInetAddress());
        } finally {
            socketHandler.shutdown();
        }
    }

}
