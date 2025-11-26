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

	public List<Map<String, Object>> getSonderwunschData(int hausnummer, String kategorie) throws SQLException {
        String query = """
            SELECT sw.name AS Sonderwunsch_Name, 
                   wo.name AS Wunschoption_Name, 
                   wo.preis AS Preis
            FROM Wunschoption wo
            JOIN Wunschoption_haus wh ON wo.wunschoption_id = wh.wunschoption_id
            JOIN Haus h ON wh.hausnummer = h.hausnummer
            JOIN Kunde k ON k.hausnummer = h.hausnummer
            JOIN Sonderwunschkategorie sw ON wo.wunsch_id = sw.wunsch_id
            WHERE k.hausnummer = ? AND sw.name = ?;
        """;

        List<Map<String, Object>> results = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, hausnummer);
            stmt.setString(2, kategorie);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Sonderwunsch_Name", rs.getString("Sonderwunsch_Name"));
                    row.put("Wunschoption_Name", rs.getString("Wunschoption_Name"));
                    row.put("Preis", rs.getDouble("Preis"));
                    results.add(row);
                }
            }
        }
        return results;
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
    // Methode zum Ausführen von INSERT/UPDATE/DELETE-Statements
    public void executeUpdate(String sql) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " Zeilen betroffen.");
        } catch (SQLException e) {
            System.err.println("Fehler beim Update: " + e.getMessage());
        }
    }
    // Prepared Statements (z. B. mit Parametern)
    public void executePreparedUpdate(String sql, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " Zeilen betroffen.");
        } catch (SQLException e) {
            System.err.println("Fehler beim Prepared Update: " + e.getMessage());
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
        String sql = "SELECT name, preis, wunschoption_id FROM " + tableName + " where wunsch_id = "+filter;
        return executeSelect(sql, "name", "preis", "wunschoption_id");
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
        String sql = "SELECT w.wunschoption_id " +
                "FROM Kunde k " +
                "JOIN Haus h ON k.hausnummer = h.hausnummer " +
                "JOIN Wunschoption_haus wh ON h.hausnummer = wh.hausnummer " +
                "JOIN Wunschoption w ON wh.wunschoption_id = w.wunschoption_id " +
                "WHERE k.kundennummer = ? AND w.wunsch_id = ?";

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
            System.err.println("Fehler beim Abrufen der Wunschoptionen: " + e.getMessage());
        }
        // Convert the list of IDs to an int array
        return ids.stream().mapToInt(i -> i).toArray();
    }

}
