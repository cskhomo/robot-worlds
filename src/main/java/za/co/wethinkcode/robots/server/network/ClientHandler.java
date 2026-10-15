package za.co.wethinkcode.robots.server.network;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.robot.CommandHandler;
import za.co.wethinkcode.robots.server.model.robot.command.Command;
import za.co.wethinkcode.robots.server.model.robot.command.connection.LaunchCommand;
import za.co.wethinkcode.robots.server.model.world.World;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles communication with a single robot client.
 * Reads JSON requests, parses them into commands, executes them, and sends back JSON responses.
 */
public class ClientHandler implements Runnable {

    private final Socket socket;
    private final World world;
    private final Set<String> robotNames = new HashSet<>();
    private final ConsoleOutput console;
    private final CommandHandler commandHandler;

    /**
     * Creates a client handler for a connected robot.
     *
     * @param socket  the client socket connection
     * @param world   the game world instance
     * @param console the console output for logging
     */
    public ClientHandler(Socket socket, World world, ConsoleOutput console) {
        this.socket = socket;
        this.world = world;
        this.console = console;
        this.commandHandler = new CommandHandler(world);
    }

    /**
     * Main execution loop for reading, parsing, executing commands, and returning responses.
     */
    @Override
    public void run() {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                JsonObject request = JsonParser.parseString(line).getAsJsonObject();
                Command command = commandHandler.parse(request);

                if (command instanceof LaunchCommand lc) {
                    robotNames.add(lc.robotName());
                }

                JsonObject response = command.execute();
                out.println(response.toString());
            }

        } catch (IOException e) {
            console.printClientDisconnect(socket.getInetAddress());
        } finally {
            for (String name : robotNames) {
                world.disconnectRobot(name);
            }
        }
        try {
            socket.close();
        } catch (IOException ignored) {}
    }
}