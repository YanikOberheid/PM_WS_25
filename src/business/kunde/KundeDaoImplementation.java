package business.kunde;

import java.sql.*;

/**
 * Data Access Object (DAO) für Kunden. Beinhaltet alle Datenbankzugriffe für
 * Kundenobjekte.
 */
public class KundeDaoImplementation implements KundenDAO {

	static Connection con = DatabaseConnection.getInstance().getConnection();
	PreparedStatement ps = null;

	@Override
	public int add(Kunde kunde) throws SQLException {

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
		String sql = "INSERT INTO Kunde (Haus_Hausnr, Vorname, Nachname, Telefon, email) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, kunde.getHausnummer());
			pstmt.setString(2, kunde.getVorname());
			pstmt.setString(3, kunde.getNachname());
			pstmt.setString(4, kunde.getTelefonnummer());
			pstmt.setString(5, kunde.getEmail());

			int rowsAffected = pstmt.executeUpdate();
			System.out.println("✅ Kunde erfolgreich hinzugefügt: " + kunde.getVorname() + " " + kunde.getNachname());
			return rowsAffected;
		} catch (SQLException e) {
			System.err.println("❌ Fehler beim Hinzufügen des Kunden!");
			e.printStackTrace();
			throw e;
		}
	}
}
