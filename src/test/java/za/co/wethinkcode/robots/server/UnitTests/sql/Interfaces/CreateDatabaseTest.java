package za.co.wethinkcode.robots.server.UnitTests.sql.Interfaces;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.data.sql.CreateDatabase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

class CreateDatabaseTest {

    @Test
    void testCreateDatabaseIfNotExistsCreatesFile() throws Exception {
        Path tempDb = Files.createTempFile("robotworld-test-", ".db");
        Files.deleteIfExists(tempDb);

        assertFalse(Files.exists(tempDb));
        CreateDatabase.createDatabaseIfNotExists(tempDb.toString());
        assertTrue(Files.exists(tempDb));
    }

    @Test
    void testCreateTablesIfNotExistsDoesNotThrow() throws Exception {
        Path tempDb = Files.createTempFile("robotworld-schema-", ".db");
        String url = "jdbc:sqlite:" + tempDb;

        try (Connection conn = DriverManager.getConnection(url)) {
            assertDoesNotThrow(() ->
                            CreateDatabase.createTablesIfNotExists(conn),
                    "Schema execution should not throw errors"
            );
        }
    }

    @Test
    void testCreateTablesIfNotExistsRunsSomeStatements() throws Exception {
        Path tempDb = Files.createTempFile("robotworld-schema2-", ".db");
        String url = "jdbc:sqlite:" + tempDb;

        try (Connection conn = DriverManager.getConnection(url)) {
            assertDoesNotThrow(() -> CreateDatabase.createTablesIfNotExists(conn),
                    "Schema execution should not throw errors"
            );
        }
    }
}
