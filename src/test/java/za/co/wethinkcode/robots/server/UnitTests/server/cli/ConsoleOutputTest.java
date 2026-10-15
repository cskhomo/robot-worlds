package za.co.wethinkcode.robots.server.UnitTests.server.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.cli.ConsoleOutput;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;
import za.co.wethinkcode.robots.server.model.robot.Robot;
import za.co.wethinkcode.robots.server.model.world.World;
import za.co.wethinkcode.robots.server.model.world.command.Command;
import za.co.wethinkcode.robots.server.model.world.obstacle.Obstacle;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsoleOutputTest {

    private ConsoleOutput console;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        console = new ConsoleOutput();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testPrintWelcome() {
        console.printWelcome();
        String output = outContent.toString();
        assertTrue(output.contains("Robot Worlds Server is running."));
        assertTrue(output.contains("Waiting for robots to connect..."));
    }

    @Test
    void testPrintClientConnect() throws Exception {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        console.printClientConnect(address);
        assertTrue(outContent.toString().contains("• Client connected: /127.0.0.1"));
    }

    @Test
    void testPrintServerConfig() {
        Dimension dim = new Dimension(5);
        console.printServerConfig(5000, dim);
        String output = outContent.toString();
        assertTrue(output.contains("Port               : 5000"));
        assertTrue(output.contains("World Size         : " + dim));
    }

    @Test
    void testPrintObstaclesEmpty() {
        console.printObstacles(Collections.emptyList());
        String output = outContent.toString();
        assertTrue(output.contains("None"));
    }

    @Test
    void testPrintRobotsEmpty() {
        console.printRobots(Collections.emptyList(), false);
        String output = outContent.toString();
        assertTrue(output.contains("None"));
    }

    @Test
    void testPrintError() {
        console.printError("Something went wrong");
        String output = outContent.toString();
        assertTrue(output.contains("ERROR: Something went wrong"));
    }

    @Test
    void testPrintPrompt() {
        console.printPrompt();
        String output = outContent.toString();
        assertTrue(output.contains("Enter Command:"));
    }
}
