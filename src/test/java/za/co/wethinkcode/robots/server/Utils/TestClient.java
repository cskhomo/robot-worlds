package za.co.wethinkcode.robots.server.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * A simple client for connecting to the Robot Worlds server and sending JSON requests.
 * Designed for use in automated tests.
 */
public class TestClient {

    private Socket socket;
    private PrintStream out;
    private BufferedReader in;

    /**
     * Connects to the Robot Worlds server at the specified IP and port.
     *
     * @param ipAddress server IP (e.g., "localhost")
     * @param port      server port (e.g., 5000)
     */
    public void connect(String ipAddress, int port) {
        try {
            socket = new Socket(ipAddress, port);
            out = new PrintStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to Robot Worlds server.", e);
        }
    }

    /**
     * Returns whether the client is currently connected to the server.
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    /**
     * Disconnects from the server and closes resources.
     */
    public void disconnect() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error disconnecting from Robot Worlds server.", e);
        }
    }

    /**
     * Sends a JSON request to the server and returns the response as a JsonObject.
     *
     * @param requestJsonString the JSON request to send
     * @return the server response as a JsonObject
     */
    public JsonObject sendRequest(String requestJsonString) {
        try {
            out.println(requestJsonString);
            out.flush();
            String response = in.readLine();
            System.out.println(response+"!!!");
            return JsonParser.parseString(response).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException("Error sending request or reading server response.", e);
        }
    }
}