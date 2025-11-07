package business.kunde;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Verwaltet die Verbindung zur Datenbank.
 */

public class DatabaseConnection {

	private static Connection con = null;

	static {
		String url = "jdbc:mysql://sr-labor.ddns.net:3306/PM_Gruppe_C";
		String user = "PM_Gruppe_C";
		String pass = "123456789";
		try {
			System.out.println("Datenbankverbindung herstellen...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("Datenbankverbindung erfolgreich hergestellt!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return con;
	}
}
