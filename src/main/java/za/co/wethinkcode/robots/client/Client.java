package za.co.wethinkcode.robots.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.JsonObject;

import za.co.wethinkcode.robots.client.config.ConfigLoader;
import za.co.wethinkcode.robots.client.config.SimpleConfig;

/**
 * The Client class handles connecting to the Robot World server,
 * reading user input, sending commands, and displaying server responses.
 */
public class Client {
    /**
     * Loads the client configuration from a JSON file.
     *
     * @return SimpleConfig object containing host and port information,
     *         or null if loading fails.
     */
    private static SimpleConfig loadConfig() {
        try {
            return ConfigLoader.load("client/json/client-config.json");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * The main entry point for the client application.
     * Connects to the Robot World server, sends user commands,
     * and prints server responses.
     *
     * @param args Command line arguments (not used).
     * @throws IOException If an I/O error occurs when connecting or communicating.
     */
    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        Socket socket = null;

        try {
            SimpleConfig config = loadConfig();
            socket = new Socket(config.host, config.port);

            System.out.println("Connected to Robot World server");
    
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    
            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println(response);
                    }
                } 
                
                catch (IOException e) {
                    System.out.println("Disconnected from the server");
                }
            }).start();
    
            String userInput;
            while ((userInput = console.nextLine()) != null) {

                if (userInput.equals("quit")) System.exit(0);

                boolean isValid = CommandHandler.validateUserInput(userInput);
                if (!isValid) continue;
    
                JsonObject request = CommandHandler.handle(userInput);
                if (request == null) continue;
                
                out.println(request);
            }
        } 
        
        catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    
        assert socket != null;
        socket.close();
        console.close();
        System.exit(0);
    }
}
