package business.kunde;

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

	@Override
	public boolean istHausnummerBesetzt(int hausnummer) throws SQLException {
	    String sql = "SELECT 1 FROM Kunde WHERE Haus_Hausnr = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, hausnummer);
	        try (ResultSet rs = ps.executeQuery()) {
	            return rs.next(); // true, wenn ein Eintrag existiert
	        }
	    }
	}
	
	public Kunde findByHausnummer(int hausnummer) throws SQLException {
	    String sql = "SELECT Haus_Hausnr, Vorname, Nachname, Telefon, email FROM Kunde WHERE Haus_Hausnr = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, hausnummer);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return new Kunde(
	                    rs.getInt("Haus_Hausnr"),
	                    rs.getString("Vorname"),
	                    rs.getString("Nachname"),
	                    rs.getString("Telefon"),
	                    rs.getString("email")
	                );
	            }
	        }
	    }
	    return null; // kein Kunde gefunden
	}
	
	// Löscht den Kunden mit der angegebenen Hausnummer
	public boolean deleteKunde(int hausnummer) throws SQLException {
	    String sql = "DELETE FROM Kunde WHERE Haus_Hausnr = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, hausnummer);
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0; // true, wenn ein Datensatz gelöscht wurde
	    }
	}
	
	public void updateKunde (Kunde kunde) throws SQLException {
	    String sql = "UPDATE Kunde SET Vorname = ?, Nachname = ?, Telefon = ?, email = ? WHERE Haus_Hausnr = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, kunde.getVorname());
	        ps.setString(2, kunde.getNachname());
	        ps.setString(3, kunde.getTelefonnummer());
	        ps.setString(4, kunde.getEmail());
	        ps.setInt(5, kunde.getHausnummer());

	        int rows = ps.executeUpdate();
	        if (rows > 0) {
	            System.out.println("✅ Kunde erfolgreich aktualisiert.");
	        } else {
	            System.out.println("ℹ Kein Kunde unter dieser Hausnummer gefunden.");
	        }
	    }
	}

	// Fürs Laden des Bild aus der Datenbank
	@Override
	public InputStream loadImage(int id) throws SQLException, Exception {
		InputStream is = null;
		
		String sql = "SELECT data FROM images WHERE id = ?";
		
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				is = rs.getBinaryStream("data");
			}
		return is;
		}
	}
}
