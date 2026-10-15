package za.co.wethinkcode.robots.config;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.client.config.SimpleConfig;

import static org.junit.jupiter.api.Assertions.*;

class SimpleConfigTest {

    @Test
    void testHostAndPortAssignment() {
        SimpleConfig config = new SimpleConfig();
        config.host = "localhost";
        config.port = 8080;

        assertEquals("localhost", config.host);
        assertEquals(8080, config.port);
    }

    @Test
    void testDefaultValues() {
        SimpleConfig config = new SimpleConfig();
        // By default, host should be null and port 0 for a new object
        assertNull(config.host);
        assertEquals(0, config.port);
    }

    @Test
    void testChangingValues() {
        SimpleConfig config = new SimpleConfig();
        config.host = "127.0.0.1";
        config.port = 3000;

        config.host = "example.com";
        config.port = 9000;

        assertEquals("example.com", config.host);
        assertEquals(9000, config.port);
    }
}
