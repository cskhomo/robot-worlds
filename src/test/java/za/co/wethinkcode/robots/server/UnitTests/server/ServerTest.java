package za.co.wethinkcode.robots.server.UnitTests.server;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.network.SocketHandler;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServerTest {

    private static final int TEST_PORT = 5001;
    private Thread serverThread;
    private SocketHandler handler;

    @BeforeAll
    public void startServer() throws InterruptedException {
        serverThread = new Thread(() -> {
            try {
                Dimension size = new Dimension(5, 5); // Example world size
                World world = new World(size);
                handler = new SocketHandler(TEST_PORT, world, new ConsoleOutput());
                handler.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "ServerThread");

        serverThread.start();

        // Wait until server is ready
        int attempts = 0;
        while (attempts < 10) {
            try (Socket s = new Socket("localhost", TEST_PORT)) {
                return; // server is ready
            } catch (IOException e) {
                Thread.sleep(100);
            }
            attempts++;
        }
        fail("Server did not start within timeout period");
    }

    @AfterAll
    public void stopServer() throws InterruptedException {
        if (handler != null) {
            handler.shutdown();
        }
        serverThread.join(2000);
    }

    @Test
    public void testOneClientConnection() throws IOException {
        try (Socket clientSocket = new Socket("localhost", TEST_PORT)) {
            assertTrue(clientSocket.isConnected());
        }
    }

    @Test
    public void testTwoClientConnections() throws IOException {
        try (Socket client1 = new Socket("localhost", TEST_PORT);
             Socket client2 = new Socket("localhost", TEST_PORT)) {
            assertTrue(client1.isConnected());
            assertTrue(client2.isConnected());
        }
    }
}
