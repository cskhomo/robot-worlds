package za.co.wethinkcode.robots.client.config;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utility class responsible for loading configuration settings from a JSON file.
 */
public class ConfigLoader {

    /**
     * Loads a configuration file from the classpath and parses it into a {@link SimpleConfig} object.
     *
     * @param path the classpath location of the JSON file
     * @return a {@link SimpleConfig} instance containing the loaded configuration
     * @throws IOException if the file cannot be read
     */
    public static SimpleConfig load(String path) throws IOException {
        InputStream inputStream = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream(path);

        if (inputStream == null) {
            throw new IOException("Resource not found: " + path);
        }

        Gson gson = new Gson();
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            return gson.fromJson(reader, SimpleConfig.class);
        }
    }
}
