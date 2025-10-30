package business.kunde;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) für Kunden.
 * Beinhaltet alle Datenbankzugriffe für Kundenobjekte.
 */
public class KundeDAO {

    public void insertKunde(Kunde kunde) throws SQLException {
    	/*
        String sql = "INSERT INTO kunde (hausnummer, vorname, nachname, telefonnummer, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, kunde.getHausnummer());
            stmt.setString(2, kunde.getVorname());
            stmt.setString(3, kunde.getNachname());
            stmt.setString(4, kunde.getTelefonnummer());
            stmt.setString(5, kunde.getEmail());
            stmt.executeUpdate();
        }
        */
    }

    public List<Kunde> getAllKunden() throws SQLException {
    	/*
        List<Kunde> kunden = new ArrayList<>();
        String sql = "SELECT * FROM kunde";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Kunde k = new Kunde();
                k.setHausnummer(rs.getInt("hausnummer"));
                k.setVorname(rs.getString("vorname"));
                k.setNachname(rs.getString("nachname"));
                k.setTelefonnummer(rs.getString("telefonnummer"));
                k.setEmail(rs.getString("email"));
                kunden.add(k);
            }
        }*/
        return null;//kunden;
        
    }
}
