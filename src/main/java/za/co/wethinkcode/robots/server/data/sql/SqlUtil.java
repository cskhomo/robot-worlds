package za.co.wethinkcode.robots.server.data.sql;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SqlUtil {

    private SqlUtil() {} // Prevent instantiation

    /**
     * Reads the content of a SQL resource file as a String.
     *
     * @param resourcePath path inside resources folder, e.g., "sql/save_world.sql"
     * @return SQL text
     */
    public static String readResource(String resourcePath) {
        try (InputStream is = SqlUtil.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("Resource not found on classpath: " + resourcePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read resource: " + resourcePath, e);
        }
    }
}
