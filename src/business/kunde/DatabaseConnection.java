package business.kunde;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Verwaltet die Verbindung zur Datenbank.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static DatabaseConnection instance;

	private Connection connection;

	private final String url = "jdbc:mysql://sr-labor.ddns.net:3306/PM_Gruppe_C";
	private final String user = "PM_Gruppe_C";
	private final String pass = "123456789";

	private DatabaseConnection() {
		try {
			System.out.println("Datenbankverbindung herstellen...");

			// Muss in eigenem Projekt unter Projects < Properties < Java Build Path <
			// Libraries < Add Library
			Class.forName("com.mysql.cj.jdbc.Driver");

			this.connection = DriverManager.getConnection(url, user, pass);
			System.out.println("Datenbankverbindung erfolgreich hergestellt!");
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC-Treiber nicht gefunden!");
		} catch (SQLException e) {
			System.err.println("Fehler beim Herstellen der Datenbankverbindung!");
		}
	}

	public static synchronized DatabaseConnection getInstance() {
		if (instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				System.out.println("Verbindung geschlossen - versuche Neuverbindung...");
				connection = DriverManager.getConnection(url, user, pass);
			}
		} catch (SQLException e) {
			System.err.println("Fehler beim Wiederherstellen der Verbindung!");
			e.printStackTrace();
		}
		return connection;
	}
}