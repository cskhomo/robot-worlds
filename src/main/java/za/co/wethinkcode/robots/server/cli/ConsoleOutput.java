package za.co.wethinkcode.robots.server.cli;

import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;
import za.co.wethinkcode.robots.server.model.world.obstacle.Obstacle;

import java.net.InetAddress;
import java.util.List;

/**
 * Handles all console output for the server.
 * Provides formatted printing methods for various server events and information.
 */
public class ConsoleOutput {

    public ConsoleOutput() {}

    /** Prints the server startup welcome message. */
    public void printWelcome() {
        System.out.println("Robot Worlds Server is running.");
        System.out.println("Waiting for robots to connect...");
    }

    public void printServerShutdown() {
        System.out.println("Server shutting down...");
    }

    /**
     * Prints a client connection message.
     * 
     * @param address the client's IP address
     */
    public void printClientConnect(InetAddress address) {
        System.out.println("• Client connected: " + address.toString());
    }

    public void printClientDisconnect(InetAddress address) {
        System.out.println("• Client disconnected: " + address.toString());
    }

    public void printPrompt() {
        System.out.print("\nEnter Command: ");
    }

    public void printHelp(List<Command> commands) {
        System.out.println("\nAvailable commands:");
        for (Command cmd : commands) {
            System.out.printf(" • %-10s : %s%n", cmd.name(), cmd.description());
        }
    }

    public void printServerConfig(int port, Dimension dimension) {
        System.out.println("\nServer Configuration:");
        System.out.println(" • " + "Port               : " + port);
        System.out.println(" • " + "World Size         : " + dimension);
    }

    public void printWorldConfig(World world) {
        System.out.println("\nWorld Configuration:");
        System.out.println(" • " + "Visibility Range   : " + world.visibility() + " steps");
        System.out.println(" • " + "Maximum Ammo       : " + world.maxAmmo() + " shots/mines");
        System.out.println(" • " + "Maximum Shields    : " + world.maxShields() + " hits");
        System.out.println(" • " + "Reload Time        : " + world.reloadTime() + " seconds");
        System.out.println(" • " + "Set Mine Time      : " + world.setMineTime() + " seconds");
        System.out.println(" • " + "Repair Time        : " + world.repairTime() + " seconds") ;
    }

    public void printObstacles(List<Obstacle> obstacles) {
        System.out.println("\nObstacles in world:");
        if (obstacles.isEmpty()) {
            System.out.println(" None");
        } else {
            for (Obstacle obs : obstacles) {
                System.out.println(obs);
            }
        }
    }

    public  void printSaveResponse(String worldName){
        System.out.println("\n • World Configuration Saved for: " + worldName);
    }

    public  void printRestoreResponse(String worldName){
        System.out.println("\n • World Configuration Restored for: " + worldName);
    }

    public void printLaunch(String robotName) {
        System.out.println("\n • Robot Launched: " + robotName);
    }

    public void printPurge(String robotName) {
        System.out.println("\n • Robot Purged: " + robotName);
    }

    public void printRobots(List<Robot> robots, boolean detailed) {

        System.out.println("\nRobots in world:");

        if (robots.isEmpty()) {
            System.out.println(" None");

        } else {
            for (Robot robot : robots) {
                if (!detailed) {
                    System.out.printf(" • %-8s : %s%n", robot.name(), robot.position());
                } else {
                    System.out.println(robot);
                }
            }
        }
    }

    public void printUnknown() {
        System.out.println("Unknown command. Type 'help' for available commands.");
    }

    /**
     * Prints an error message to the console.
     * 
     * @param message the error message to display
     */
    public void printError(String message) {
        System.out.println("\nERROR: " + message);
    }

}