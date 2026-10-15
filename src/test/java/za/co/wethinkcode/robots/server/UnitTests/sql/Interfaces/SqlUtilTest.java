package za.co.wethinkcode.robots.server.UnitTests.sql.Interfaces;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.data.sql.SqlUtil;

import static org.junit.jupiter.api.Assertions.*;

class SqlUtilTest {

    @Test
    void readExistingResource_returnsContent() {
        // Arrange
        String resourcePath = "server/sql/schema.sql"; // make sure this file exists in src/main/resources

        // Act
        String content = SqlUtil.readResource(resourcePath);

        // Assert
        assertNotNull(content, "Content should not be null");
        assertTrue(content.length() > 0, "Content should not be empty");
        assertTrue(content.contains("CREATE TABLE") || content.contains("create table"),
                "Content should contain SQL statements");
    }
//    @Test
//    void readNonExistingResource_throwsRuntimeException() {
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            SqlUtil.readResource("nonexistent.sql");
//        });
//
//        // Check for "Resource not found" instead of the full string
//        String msg = exception.getMessage();
//        System.out.println("Exception message: " + msg); // optional, helps debugging
//        assertTrue(msg.contains("Resource not found"),
//                "Exception message should indicate resource not found");
//    }





}

