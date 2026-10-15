package za.co.wethinkcode.robots.config;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.client.config.SimpleConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    // Helper method to simulate loading from an InputStream instead of a file
    private SimpleConfig loadFromStream(InputStream inputStream) throws IOException {
        Gson gson = new Gson();
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            return gson.fromJson(reader, SimpleConfig.class);
        }
    }

    @Test
    void testLoadValidConfig() throws IOException {
        String json = "{ \"host\": \"localhost\", \"port\": 8080 }";
        InputStream stream = new ByteArrayInputStream(json.getBytes());
        SimpleConfig config = loadFromStream(stream);

        assertNotNull(config);
        assertEquals("localhost", config.host);
        assertEquals(8080, config.port);
    }

    @Test
    void testLoadMalformedJson() {
        String json = "{ \"host\": \"localhost\", \"port\": 8080 "; // missing closing brace
        InputStream stream = new ByteArrayInputStream(json.getBytes());

        assertThrows(com.google.gson.JsonSyntaxException.class, () -> {
            loadFromStream(stream);
        });
    }
}
