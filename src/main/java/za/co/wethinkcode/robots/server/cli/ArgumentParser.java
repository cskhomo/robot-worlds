package za.co.wethinkcode.robots.server.cli;

import org.apache.commons.cli.*;
import za.co.wethinkcode.robots.server.data.json.ConfigReader;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;

/**
 * Parses command line arguments for server configuration.
 * Supports port and world size options with default fallbacks.
 */
public class ArgumentParser {

    /**
     * Configuration record containing parsed server settings.
     * 
     * @param port the server port number
     * @param worldSize the dimensions of the game world
     */
    public record Config(int port, Dimension worldSize) {}

    /**
     * Parses command line arguments into a configuration object.
     * 
     * @param args command line arguments
     * @return parsed configuration with port and world size
     * @throws ParseException if arguments cannot be parsed
     */
    public static Config parse(String[] args) throws ParseException {

        Options options = new Options();
        options.addOption("p", "port", true, "Server port");
        options.addOption("s", "size", true, "World size");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        ConfigReader defaults = new ConfigReader();

        int port = readPort(cmd, defaults);
        Dimension size = readSize(cmd, defaults);

        return new Config(port, size);
    }

    private static int readPort(CommandLine cmd, ConfigReader defaults) {
        return cmd.hasOption("p")
                ? Integer.parseInt(cmd.getOptionValue("p"))
                : defaults.port();
    }

    private static Dimension readSize(CommandLine cmd, ConfigReader defaults) {
        return cmd.hasOption("s")
                ? new Dimension(Integer.parseInt(cmd.getOptionValue("s")))
                : defaults.dimension();
    }
}