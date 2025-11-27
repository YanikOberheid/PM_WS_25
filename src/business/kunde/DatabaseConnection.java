package business.kunde;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Verwaltet die Verbindung zur Datenbank.
 */

public class DatabaseConnection {
	private static DatabaseConnection instance;
	
	private Connection connection;
	
	private final String url = "jdbc:mysql://sr-labor.ddns.net:3306/PM_Gruppe_C";
	private final String user = "PM_Gruppe_C";
	private final String pass = "123456789";
	
	private DatabaseConnection() {
		try {
			System.out.println("Datenbankverbindung herstellen...");
			
			// Muss in eigenem Projekt unter Projects < Properties < Java Build Path < Libraries < Add Library
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
		} catch (SQLException e){
			System.err.println("Fehler beim Wiederherstellen der Verbindung!");
			e.printStackTrace();
		}
		return connection;
	}

    // Methode zum Ausführen eines SELECT-Statements
	public void executeSelect(String sql) {
	    try (PreparedStatement stmt = connection.prepareStatement(sql);
	         ResultSet resultSet = stmt.executeQuery()) {

	        // Metadaten abrufen, um Spaltennamen und Anzahl der Spalten zu erhalten
	        java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
	        int columnCount = metaData.getColumnCount();

	        // Spaltennamen ausgeben
	        for (int i = 1; i <= columnCount; i++) {
	            System.out.print(metaData.getColumnName(i) + "\t");
	        }
	        System.out.println();

	        // Zeilen ausgeben
	        while (resultSet.next()) {
	            for (int i = 1; i <= columnCount; i++) {
	                System.out.print(resultSet.getString(i) + "\t");
	            }
	            System.out.println();
	        }

	    } catch (SQLException e) {
	        System.err.println("Fehler beim SELECT: " + e.getMessage());
	    }
	}


    public String[][] executeSelect(String sql, String... columns) {
        try (PreparedStatement stmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = stmt.executeQuery()) {

            // Zählen der Zeilen (ResultSet)
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
            }

            // Ergebnis-Array (Zeilen x Spalten)
            String[][] results = new String[rowCount][columns.length];

            // Zurück zum Anfang des ResultSets
            resultSet.beforeFirst();
            int rowIndex = 0;

            // Ergebnisse in das Array einfügen
            while (resultSet.next()) {
                for (int colIndex = 0; colIndex < columns.length; colIndex++) {
                    results[rowIndex][colIndex] = resultSet.getString(columns[colIndex]);
                }
                rowIndex++;
            }

            return results;

        } catch (SQLException e) {
            System.err.println("Fehler beim SELECT: " + e.getMessage());
            return new String[0][0]; // Leeres Array im Fehlerfall
        }
    }

    public String[][] executeSelectNameAndPrice(String tableName, int filter) {
        String sql = "SELECT Beschreibung, Preis, idSonderwunsch  FROM " + tableName + " where idSonderwunsch = "+filter;
        return executeSelect(sql, "Beschreibung", "preis", "idSonderwunsch");
    }

    public String[][] loadSonderwunschNameAndPrice(int sonderwunschId) {

    String sql = "SELECT Beschreibung, Preis FROM Sonderwunsch WHERE idSonderwunsch = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, sonderwunschId);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new String[][] {
                        {
                                rs.getString("Beschreibung"),
                                String.valueOf(rs.getInt("Preis"))
                        }
                };
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return new String[0][0]; // Leeres 2D-Array wenn nichts gefunden
}


    /**
     * Liest die Extrawünsche eines gegebenen Kunden für eine gegebene Kategorie
     *
     * @param customerNumber - die gegebene Kundennummer
     * @param wishCategory - die gegebene Kategorie. Wertebereich 1-8
     * @return - Alle wunschoption_ids, die der mit dem Haus des Kunden assoziiert werden.
     */
    public int[] executeSelectCustomerWishes(int customerNumber, int wishCategory) {
        // SQL query to fetch the IDs of Wunschoption
            String sql =
        "SELECT s.idSonderwunsch " +
        "FROM Kunde k " +
        "JOIN Haus h ON k.Haus_Hausnr = h.Hausnr " +
        "JOIN Sonderwunsch_has_Haus sh ON h.Hausnr = sh.Haus_Hausnr " +
        "JOIN Sonderwunsch s ON sh.Sonderwunsch_idSonderwunsch = s.idSonderwunsch " +
        "WHERE k.idKunde = ?";

        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Setting the parameters for the prepared statement
            stmt.setInt(1, customerNumber);
            stmt.setInt(2, wishCategory);

            try (ResultSet rs = stmt.executeQuery()) {
                // Loop through the result set and add each Wunschoption ID to the list
                while (rs.next()) {
                    ids.add(rs.getInt("wunschoption_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen der Sonderwunschkategorie: " + e.getMessage());
        }
        // Convert the list of IDs to an int array
        return ids.stream().mapToInt(i -> i).toArray();
    }

}
