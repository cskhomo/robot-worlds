package za.co.wethinkcode.robots.server.network;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.world.World;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Handles server socket connections and manages client connections.
 * Runs in a separate thread to accept incoming client connections.
 */
public class SocketHandler implements Runnable {

    private final ServerSocket serverSocket;
    private final ConsoleOutput console;
    private final World world;
    private volatile boolean running = true;

    /**
     * Creates a socket handler that listens on the specified port.
     * 
     * @param port the port number to listen on
     * @param world the game world instance
     * @param console the console output for logging
     * @throws IOException if the server socket cannot be created
     */
    public SocketHandler(int port, World world, ConsoleOutput console) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.console = console;
        this.world = world;
    }

    /**
     * Returns the actual port number the server is listening on.
     * 
     * @return the server port number
     */
    public int port() {
        return serverSocket.getLocalPort();
    }


    /**
     * Main execution loop that accepts client connections.
     * Creates a new ClientHandler thread for each connected client.
     */
    @Override
    public void run() {

        while (running) {
            try {
                Socket client = serverSocket.accept();
                console.printClientConnect(client.getInetAddress());
                new Thread(new ClientHandler(client, world, console)).start();
            } catch (IOException e) {
                if (running) {
                    console.printError("Accept failed: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Shuts down the server socket and stops accepting new connections.
     */
    public void shutdown() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException ignored) {}
    }
}