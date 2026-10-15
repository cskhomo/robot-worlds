package za.co.wethinkcode.robots.server.data.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import za.co.wethinkcode.robots.server.model.geometry.Dimension;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads and parses server and world configuration from JSON files.
 * Loads default settings for port, world dimensions, and obstacles.
 */
public class ConfigReader {

    private int port;
    private int width;
    private int height;

    private final List<JsonObject> obstacles = new ArrayList<>();
    private final Gson gson = new Gson();

    /**
     * Creates a config reader and loads all configuration files.
     */
    public ConfigReader() {
        loadServerConfig();
        loadWorldConfig();
    }


    /*--------------------------------------------------------------------
     * SERVER CONFIG
     *-------------------------------------------------------------------*/

    private void loadServerConfig() {
        JsonObject json = loadJsonObjectFromResource("server/json/server-config.json");
        this.port = json.get("port").getAsInt();
    }

    /*--------------------------------------------------------------------
     * WORLD CONFIG
     *-------------------------------------------------------------------*/

    private void loadWorldConfig() {
        JsonObject json = loadJsonObjectFromResource("server/json/world-config.json");
        extractWorldSize(json);
        extractObstacles(json);
    }

    private void extractWorldSize(JsonObject json) {
        this.width = json.get("width").getAsInt();
        this.height = json.get("height").getAsInt();
    }

    private void extractObstacles(JsonObject json) {
        JsonArray array = json.getAsJsonArray("obstacles");
        if (array == null) return;

        for (JsonElement elem : array) {
            obstacles.add(elem.getAsJsonObject());
        }
    }


    /*--------------------------------------------------------------------
     * CORE LOADING HELPERS
     *-------------------------------------------------------------------*/

    private JsonObject loadJsonObjectFromResource(String path) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(path);

        if (stream == null) {
            throw new RuntimeException("Config resource missing: " + path);
        }

        try (InputStreamReader reader = new InputStreamReader(stream)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException("Error reading config: " + path, e);
        }
    }

    /*--------------------------------------------------------------------
     * GETTERS
     *-------------------------------------------------------------------*/

    /** @return the configured server port */
    public int port() { return port; }
    
    /** @return the world width */
    public int getWidth() { return width; }
    
    /** @return the world height */
    public int getHeight() { return height; }
    
    /** @return the world dimensions */
    public Dimension dimension() { return new Dimension(width, height); }
    
    /** @return the list of configured obstacles */
    public List<JsonObject> getObstacles() { return obstacles; }
}
