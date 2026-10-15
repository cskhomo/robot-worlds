package za.co.wethinkcode.robots.server.data.sql;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

import static za.co.wethinkcode.robots.server.data.sql.SqlUtil.readResource;

/**
 * Utility class for creating the SQLite database file and initializing
 * its tables if they do not already exist.
 * <p>
 * This class reads the SQL schema and default data from resource files
 * and applies them to the database. All methods are static and the class
 * cannot be instantiated.
 * </p>
 */
public class CreateDatabase {

    private CreateDatabase() {}

    /**
     * Creates the database file at the specified path if it does not exist.
     *
     * @param dbPath The path to the database file.
     * @throws RuntimeException if the file cannot be created.
     */
    public static void createDatabaseIfNotExists(String dbPath) {

        try {
            Path filePath = Paths.get(dbPath);
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create database file: " + dbPath, e);
        }
    }

    /**
     * Creates the required tables and inserts default data into the database
     * if they do not already exist.
     *
     * @param connection The SQL connection to the database.
     * @throws RuntimeException if applying the schema or default data fails.
     */
    public static void createTablesIfNotExists(Connection connection) {

        String schema = readResource("server/sql/schema.sql");
        String data   = readResource("server/sql/inserts/types.sql");

        try (Statement stmt = connection.createStatement()) {

            for (String sql : (schema + "\n" + data).split(";")) {
                String trimmed = sql.strip();
                if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
                    stmt.execute(trimmed);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply schema", e);
        }
    }
}