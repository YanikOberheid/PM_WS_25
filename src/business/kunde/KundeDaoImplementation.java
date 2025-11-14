package src.business.kunde;

import java.sql.*;

/**
 * Data Access Object (DAO) für Kunden. Beinhaltet alle Datenbankzugriffe für
 * Kundenobjekte.
 */
public class KundeDaoImplementation implements KundenDAO {
	
	static Connection con = DatabaseConnection.getInstance().getConnection();
	
	@Override
	public int add(Kunde kunde) throws SQLException {
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
	// Auf Dopplung prüfen
	@Override
	public boolean istHausnummerBesetzt(int hausnummer) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("SELECT * FROM Kunde WHERE Haus_Hausnr = ?");
		ps.setInt(1, hausnummer);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}
}