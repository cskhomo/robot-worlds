package za.co.wethinkcode.robots.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.client.utilities.FuzzySearch;

public class CommandHandler {

    // List of all available commands
    private static final List<String> COMMANDS = List.of(
            "launch", "state", "look", "forward", "back",
            "turn", "repair", "reload", "fire",
            "mine", "world", "help"
    );

    /** Checks if a string is an integer */
    public static boolean isInteger(String input) {
        try { Integer.parseInt(input); return true; }
        catch (NumberFormatException e) { return false; }
    }

    /** Prints usage and available commands */
    public static void printUsage() {
        System.out.println("""
            Usage: <robot's name> <command> [args...]
            Example: josh forward 5
            """);
        System.out.println("Try one of these commands: " + COMMANDS);
    }

    /** Prints similar commands if command not found */
    public static void printAvailableCommands(String command) {
        List<String> similar = FuzzySearch.find(COMMANDS, command, 50.0);
        if (similar.isEmpty())
            System.out.println("\nCommand " + command + " not found\n");
        else
            System.out.println("\nCommand " + command + " not found. Did you mean: " + similar + "?\n");
    }

    /** Validates user input for correct command and arguments */
    public static boolean validateUserInput(String userInput) {
        String[] args = userInput.trim().split(" ");
        if (args.length < 2) { printUsage(); return false; }

        String command = args[1].toLowerCase();

        // Handle quit/disconnect
        if (args.length == 1 && (command.equals("quit") || command.equals("disconnect"))) {
            System.out.println("Disconnected from server");
            System.exit(0);
        }

        switch (command) {
            case "launch":
                if (args.length != 4 || !isInteger(args[2]) || !isInteger(args[3])) {
                    System.out.println("Usage: <name> launch <shields> <ammo>");
                    return false;
                }
                break;

            case "forward":
            case "back":
                if (args.length != 3 || !isInteger(args[2])) {
                    System.out.println("Usage: <name> " + command + " <steps>");
                    return false;
                }
                break;

            case "turn":
                if (args.length != 3) {
                    System.out.println("Usage: <name> turn <left/right>");
                    return false;
                }
                String dir = args[2].toLowerCase();
                if (!dir.equals("left") && !dir.equals("right")) {
                    System.out.println("Argument for turn must be 'left' or 'right'");
                    return false;
                }
                break;

            case "state":
            case "look":
            case "repair":
            case "reload":
            case "fire":
            case "mine":
            case "world":
            case "help":
                if (args.length != 2) {
                    printUsage();
                    return false;
                }
                break;

            default:
                printAvailableCommands(command);
                return false;
        }
        return true;
    }

    /** Handles a validated command and returns a JSON request */
    public static JsonObject handle(String userInput) {
        String[] args = userInput.trim().toLowerCase().split(" ");
        String robotName = args[0];
        String command = args[1];

        return switch (command) {
            case "launch" -> Request.launch(robotName, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            case "state"  -> Request.state(robotName);
            case "look"   -> Request.look(robotName);
            case "forward"-> Request.forward(robotName, Integer.parseInt(args[2]));
            case "back"   -> Request.backward(robotName, Integer.parseInt(args[2]));
            case "turn"   -> Request.turn(robotName, args[2]);
            case "repair" -> Request.repair(robotName);
            case "reload" -> Request.reload(robotName);
            case "fire"   -> Request.fire(robotName);
            case "mine"   -> Request.mine(robotName);
            case "world"  -> Request.world(robotName);
            case "help"   -> { printUsage(); yield null; }
            default       -> null;
        };
    }
}
