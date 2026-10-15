package za.co.wethinkcode.robots.server.UnitTests.data.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.data.json.ConfigReader;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigReaderTests {

    private static ConfigReader reader;

    @BeforeAll
    static void setup() {
        // Initialize ConfigReader once for all tests
        reader = new ConfigReader();
    }

    @Test
    void testServerPortIsLoaded() {
        int port = reader.port();
        assertTrue(port > 0, "Port should be greater than 0");
        System.out.println("Server port loaded: " + port);
    }
    @Test
    void testWorldDimensionsAreLoaded() {
        int width = reader.getWidth();
        int height = reader.getHeight();
        Dimension dim = reader.dimension();


//            assertEquals(10, dim.width(), "World width should be 10");
//            assertEquals(10, dim.height(), "World height should be 10");
        }



    @Test
    void testObstaclesAreLoaded() {
        List<JsonObject> obstacles = reader.getObstacles();
        assertFalse(obstacles.isEmpty(), "Obstacles list should not be empty");

        for (JsonObject obstacle : obstacles) {
            assertTrue(obstacle.has("topLeft") && obstacle.has("bottomRight"),
                    "Obstacle must have topLeft and bottomRight coordinates");

            // Optional: check that arrays have 2 elements
//            assertEquals(2, obstacle.getAsJsonArray("topLeft").size());
//            assertEquals(2, obstacle.getAsJsonArray("bottomRight").size());
        }
    }


}
