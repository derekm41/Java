package DAO_Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** This class stores all database connection information and utility. */
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ06SSc";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    //Driver and Connection Interface references
    private static final String MYSQLJDBCDRIVER = "com.mysql.cj.jdbc.Driver";
    public static Connection conn = null;

    private static final String username = "U06SSc";
    private static final String dbPassword = "53688855439";


    /** This is the startConnection method.
     * This creates a connection to the database.
     * @return Connection variable
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDRIVER);
            conn = DriverManager.getConnection(jdbcURL, username, dbPassword);
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
           //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

/** This is the closeConnection method.
 * This closes the connection when program is terminated. */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection Closed");
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
