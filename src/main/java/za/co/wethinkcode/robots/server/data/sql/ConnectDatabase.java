package za.co.wethinkcode.robots.server.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages database connections for the Robot World server.
 * Handles both production (file-based) and test (in-memory) databases.
 * Automatically creates database schema if needed.
 */
public class ConnectDatabase {

    public static final String TEST_DB_URL = "jdbc:sqlite:src/test/resources/test.db";
    public static final String DISK_DB_URL = "jdbc:sqlite:robotworld.db";
    private static Connection sharedMemoryConnection = null;
    private String dbUrl = null;

    /**
     * Initializes database connection and creates schema if needed.
     * 
     * @param useProduction true for file-based DB, false for test DB
     */
    public ConnectDatabase(boolean useProduction) {

        setUrl(useProduction);

        try(final Connection connection = DriverManager.getConnection(dbUrl)){

            if (useProduction) {
                String database = dbUrl.substring("jdbc:sqlite:".length());
                CreateDatabase.createDatabaseIfNotExists(database);
                CreateDatabase.createTablesIfNotExists(connection);
            }
        }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }
    }


    private void setUrl( boolean use_production_db ){
        dbUrl = use_production_db ? DISK_DB_URL : TEST_DB_URL;
    }

    /**
     * Gets a database connection.
     * Uses shared connection for test DB, new connection for production.
     * 
     * @return database connection
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws  SQLException{
        if (dbUrl.equals(TEST_DB_URL)) {
            if (sharedMemoryConnection == null || sharedMemoryConnection.isClosed()) {
                sharedMemoryConnection = DriverManager.getConnection(TEST_DB_URL);
            }
            return sharedMemoryConnection;
        }


        return DriverManager.getConnection(dbUrl);
    }
    }

