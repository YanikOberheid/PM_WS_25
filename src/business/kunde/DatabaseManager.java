package business.kunde;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Verwaltet die Verbindung zur Datenbank.
 */
public class DatabaseManager {
	
	private static String URL = "jdbc:mysql://sr-labor.ddns.net:3306/PM_Gruppe_C";
    private static String USER = "PM_Gruppe_C";
    private static String PASSWORD = "123456789";
	
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
        	System.out.println("Verbindung hergestellt!");
            //connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return null;//connection;
    }
}
