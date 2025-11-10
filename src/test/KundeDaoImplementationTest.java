package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import business.kunde.DatabaseConnection;
import business.kunde.Kunde;
import business.kunde.KundeDaoImplementation;

class KundeDaoImplementationTest {
	
	// Datenbank Verbindung fürs Testen herstellen
	static Connection con = DatabaseConnection.getInstance().getConnection();

	// Daten in Kunden Löschen nach jeden Test
	@AfterEach void clearTable() throws SQLException{
		con.createStatement().execute("DELETE FROM Kunde"); 
	}

	// Verbindung zur Datenbank trennen
	@AfterAll
	static void tearDown() throws SQLException {
		con.close();
	}

	// Ueberpruefen ob tatsächlich der uebergebene Kunde hinzugefuegt wurde
	// Aktuell wird nur unter der Hausnummer geschaut 
	// ob Vornamen und Nachnamen übereinstimmen mit dem der zu speichenden Kunde
	@Test
	void testAddKundeErfolgreich() throws SQLException {

		// TestVariablen
		int hausNummer = 1;
		String vorname = "Max";
		String nachname = "Mustermann";

		Kunde kunde = new Kunde();
		kunde.setHausnummer(hausNummer);
		kunde.setVorname(vorname);
		kunde.setNachname(nachname);

		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		kundeDAO.add(kunde);

		// Kunden mit der Hausnummer aus der Datenbank Kunden Tabelle aufgreifen
		// Platzhalter ? mit Hausnummer ersetzen
		PreparedStatement ps = con.prepareStatement("SELECT * FROM Kunde WHERE Haus_Hausnr = ?");
		ps.setInt(1, hausNummer);
		ResultSet rs = ps.executeQuery();

		// Gefundene Kunde mit der uebergebende Hausnummer existiert in der Datenbank
		assertTrue(rs.next(), "Kunde sollte in der DB existieren.");
		assertEquals(vorname, rs.getString("Vorname"));
		assertEquals(nachname, rs.getString("Nachname"));
		rs.close();
		ps.close();

	}
	
	// Testen ob die KundenDaten, AKTUELL nur Vorname, Nachname
	// Leer sind und es dann dementprechend einen SQLException "wirft"
	@Test
	void testAddKundeLeereDaten() throws SQLException {
		// TestVariablen
		int hausNummer = 4;
		String vorname = "";
		String nachname = "";

		Kunde kunde = new Kunde();
		kunde.setHausnummer(hausNummer);
		kunde.setVorname(vorname);
		kunde.setNachname(nachname);

		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		
		assertThrows(SQLException.class, () -> kundeDAO.add(kunde));
	}
	
	// Testen ob es eine Dopplung gibt.
	// quasi ob die Hausnummer schon besetzt ist.
	@Test
	void testAddKundeHausnummerDopplung() throws SQLException {
		// Kunde1
		int hausNummer = 1;
		String vorname = "Max";
		String nachname = "Mustermann";
		
		Kunde kunde1 = new Kunde();
		kunde1.setHausnummer(hausNummer);
		kunde1.setVorname(vorname);
		kunde1.setNachname(nachname);
		
		// Kunde2
		int hausNummer2 = 1;
		String vorname2 = "Erika";
		String nachname2 = "Mustermann";
		
		Kunde kunde2 = new Kunde();
		kunde2.setHausnummer(hausNummer2);
		kunde2.setVorname(vorname2);
		kunde2.setNachname(nachname2);
		
		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		kundeDAO.add(kunde1);
		
		assertThrows(SQLException.class, () -> kundeDAO.add(kunde2));
	}

}
