package za.co.wethinkcode.robots.server.AcceptanceTests;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import za.co.wethinkcode.robots.server.Utils.TestClient;

/**
 * Base class for Robot Worlds acceptance tests.
 * Manages a TestClient connection to the server and provides utility to send JSON requests.
 */
public abstract class BaseRobotTest {

    /** Default server IP */
    protected static final String DEFAULT_IP = "localhost";

    /** Default server port */
    protected static final int DEFAULT_PORT = 5000;

    /** TestClient used to communicate with the Robot Worlds server */
    protected TestClient client;

    /**
     * Connects TestClient to the server before each test.
     */
    @BeforeEach
    public void setup() {
        client = new TestClient();
        client.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    /**
     * Disconnects TestClient from the server after each test.
     */
    @AfterEach
    public void tearDown() {
        if (client != null && client.isConnected()) {
            client.disconnect();
        }
    }

    /**
     * Sends a JSON request to the server using TestClient.
     *
     * @param request the JSON request to send
     * @return the server's response as a JsonObject
     */
    protected JsonObject sendCommand(String request) {
        return client.sendRequest(request);
    }
}
