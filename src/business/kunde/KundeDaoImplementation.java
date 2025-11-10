package business.kunde;

import java.sql.*;

/**
 * Data Access Object (DAO) für Kunden.
 * Beinhaltet alle Datenbankzugriffe für Kundenobjekte.
 */
public class KundeDaoImplementation implements KundenDAO{
	
	static Connection con = DatabaseConnection.getInstance().getConnection();
	PreparedStatement ps = null;
	
	// Aktuell funktionier das nur für JUnit Test
	// Die Kundendaten von der GUI auslesen und
	// entsprechend einen Kunden erzeugen ist noch nicht implementiert
	@Override
	public int add(Kunde kunde) throws SQLException {
		// GUI auslesen muss noch implementiert werden
		if (kunde == null) {
			System.err.println("Kunde ist NULL\nDas Auslesen der Kunden Daten aus der GUI und\nerstellen des Kunden muss noch implementiert werden!");
			return 0;
		}
		int hausnummer = kunde.getHausnummer();
		String vorname = kunde.getVorname();
		String nachname = kunde.getNachname();
		String sql = "INSERT INTO Kunde (Haus_Hausnr, Vorname, Nachname, Telefon, email) VALUES (?, ?, ?, ?, ?)";
		
		// Überprüfen Daten Leer sind
		if (hausnummer < 0) {
			throw new SQLException("Hausnummer darf nicht leer!");
		}
		if (vorname.isEmpty()) {
			throw new SQLException("Vorname darf nicht leer!");
		}
		if (nachname.isEmpty()) {
			throw new SQLException("Nachname darf nicht leer!");
		}
		// Weitere Kunden Daten überprüfen ob Sie Leer sind
		
		// Auf Dopplung pruefen
		// Existiert bereits ein Kunde mit der uebergebende Hausnummer
		ps = con.prepareStatement("SELECT * FROM Kunde WHERE Haus_Hausnr = ?");
		ps.setInt(1, hausnummer);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			throw new SQLException("Diese Hausnummer ist besetzt!"); 
		}
		
		// Kunden Daten in die Datenbank schreiben, speichern!
		try (PreparedStatement ps = con.prepareStatement(sql)) {
		    ps.setInt(1, hausnummer);
		    ps.setString(2, vorname);
		    ps.setString(3, nachname); // Nachname, die GUI muss noch erweitert werden
		    ps.setNull(4, java.sql.Types.VARCHAR); // Telefon fehlt, die GUI muss noch erweitert werden
		    ps.setNull(5, java.sql.Types.VARCHAR); // Email fehlt, die GUI muss noch erweitert werden

		    ps.executeUpdate();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return 0;
	}
}
