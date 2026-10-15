package za.co.wethinkcode.robots.server.UnitTests.server.cli;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.cli.ArgumentParser;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    @Test
    void testParseWithNoArgumentsUsesDefaults() throws ParseException {
        String[] args = {};
        ArgumentParser.Config config = ArgumentParser.parse(args);

        assertEquals(5000, config.port());  // default port
//        assertEquals(new Dimension(1), config.worldSize()); // default size from ConfigReader
    }

    @Test
    void testParseWithCustomPort() throws ParseException {
        String[] args = {"-p", "8080"};
        ArgumentParser.Config config = ArgumentParser.parse(args);

        assertEquals(8080, config.port());
//        assertEquals(new Dimension(1), config.worldSize()); // size still default
    }

    @Test
    void testParseWithCustomSize() throws ParseException {
        String[] args = {"-s", "15"};
        ArgumentParser.Config config = ArgumentParser.parse(args);

        assertEquals(5000, config.port()); // port still default
        assertEquals(new Dimension(15), config.worldSize());
    }

    @Test
    void testParseWithCustomPortAndSize() throws ParseException {
        String[] args = {"-p", "1234", "-s", "20"};
        ArgumentParser.Config config = ArgumentParser.parse(args);

        assertEquals(1234, config.port());
        assertEquals(new Dimension(20), config.worldSize());
    }

    @Test
    void testParseWithInvalidPortThrowsException() {
        String[] args = {"-p", "notanumber"};

        assertThrows(NumberFormatException.class, () -> {
            ArgumentParser.parse(args);
        });
    }

    @Test
    void testParseWithInvalidSizeThrowsException() {
        String[] args = {"-s", "notanumber"};

        assertThrows(NumberFormatException.class, () -> {
            ArgumentParser.parse(args);
        });
    }
}
