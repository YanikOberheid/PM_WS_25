package business.kunde;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
<<<<<<< HEAD
	
	// Zudem noch die idKunde laden
	@Override
	public Kunde findByHausnummer(int hausnummer) throws SQLException {
	    String sql = "SELECT idKunde, Haus_Hausnr, Vorname, Nachname, Telefon, email FROM Kunde WHERE Haus_Hausnr = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, hausnummer);
=======
>>>>>>> refs/remotes/origin/dev

<<<<<<< HEAD
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                Kunde k = new Kunde(
	                	rs.getInt("idKunde"),
	                    rs.getInt("Haus_Hausnr"),
	                    rs.getString("Vorname"),
	                    rs.getString("Nachname"),
	                    rs.getString("Telefon"),
	                    rs.getString("email")
	                );
	                return k;
	            }
	        }
	    }
	    return null; // kein Kunde gefunden
=======
	public Kunde findByHausnummer(int hausnummer) throws SQLException {
		String sql = "SELECT Haus_Hausnr, Vorname, Nachname, Telefon, email FROM Kunde WHERE Haus_Hausnr = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, hausnummer);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Kunde(rs.getInt("Haus_Hausnr"), rs.getString("Vorname"), rs.getString("Nachname"),
							rs.getString("Telefon"), rs.getString("email"));
				}
			}
		}
		return null; // kein Kunde gefunden
>>>>>>> refs/remotes/origin/dev
	}
<<<<<<< HEAD
	
	@Override
	public Kunde findByKundennummer(int idKunde) throws SQLException {
	    String sql = "SELECT idKunde, Haus_Hausnr, Vorname, Nachname, Telefon, email FROM Kunde WHERE idKunde = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, idKunde);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                Kunde k = new Kunde(
	                	rs.getInt("idKunde"),
	                    rs.getInt("Haus_Hausnr"),
	                    rs.getString("Vorname"),
	                    rs.getString("Nachname"),
	                    rs.getString("Telefon"),
	                    rs.getString("email")
	                );
	                return k;
	            }
	        }
	    }
	    return null; // kein Kunde gefunden
	}
	
	// Delte und Update Kunde Methode geaendert!
	// Der Kunde wird nach idKunde geändert, nicht nach Kunde mit jeweilige Hausnummer
	@Override
	public boolean deleteKunde(int kundennummer) throws SQLException {
		String sql = "UPDATE Kunde SET Vorname = 'GELOESCHT', Nachname = 'GELOESCHT', Telefon = NULL, email = NULL, Haus_Hausnr = NULL " +
                "WHERE idKunde = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, kundennummer);
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;
	    }
	    
	    //deleteSonderwunsch_has_Haus();
	    
	}
	
	// Der Kunde wird nach idKunde geändert, nicht nach Kunde mit jeweilige Hausnummer
	@Override
	public void updateKunde(Kunde kunde) throws SQLException {
		System.out.println(kunde.getIdKunde());
		String sql = "UPDATE Kunde SET Vorname = ?, Nachname = ?, Telefon = ?, email = ? WHERE idKunde = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, kunde.getVorname());
	        ps.setString(2, kunde.getNachname());
	        ps.setString(3, kunde.getTelefonnummer());
	        ps.setString(4, kunde.getEmail());
	        ps.setInt(5, kunde.getIdKunde());
	        System.out.println("idKunde: " + kunde.getIdKunde());
	        int rows = ps.executeUpdate();
	        if (rows > 0) {
	            System.out.println("✅ Kunde erfolgreich aktualisiert.");
	        } else {
	            System.out.println("ℹ Kein Kunde unter dieser Hausnummer gefunden.");
	        }
	    }
=======

	// Löscht den Kunden mit der angegebenen Hausnummer
	public boolean deleteKunde(int hausnummer) throws SQLException {
		String sql = "DELETE FROM Kunde WHERE Haus_Hausnr = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, hausnummer);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0; // true, wenn ein Datensatz gelöscht wurde
		}
>>>>>>> refs/remotes/origin/dev
	}
<<<<<<< HEAD
=======

	public void updateKunde(Kunde kunde) throws SQLException {
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

>>>>>>> refs/remotes/origin/dev
}