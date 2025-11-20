package test;

import static org.junit.Assert.assertFalse;
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
import business.kunde.KundeModel;

class NeueKundenDatenAendernLoeschenTest {
	
	static Connection con = DatabaseConnection.getInstance().getConnection();
	
	// Daten in Kunden Löschen nach jeden Test
	
	/*
	@AfterEach 
	void clearTable() throws SQLException{
		con.createStatement().execute("DELETE FROM Kunde"); 
	}
	*/

	// Verbindung zur Datenbank trennen
	@AfterAll
	static void tearDown() throws SQLException {
		con.close();
	}
	
	/* 		Hier wird getestet ob die Kundendaten Vorname, Nachname, E-Mail und 
	 * 		Telefonnummer sich aendern. 
	 * 		Zuerst wird ein Kunde angelegt und die Daten geändert
	 */
	@Test
	void testAendereKundendaten() throws SQLException {
		// AlteVariablen
		
		int hausNummer = 9;
		
		String vorname = "Max";
		String nachname = "Mustermann";
		String telefonNummer = "0123456789";
		String eMail = "max@mustermann.com";
		
		// Neue Variablen
		String neuVorname = "Erika";
		String neuNachname = "Musterfrau";
		String neuTelefonNummer = "9876543210";
		String neueMail = "erika@musterfrau.com";
		
		Kunde kunde = new Kunde(hausNummer, vorname, nachname, telefonNummer, eMail);
		
		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		kundeDAO.add(kunde);
		
		Kunde kunde2 = kundeDAO.findByHausnummer(hausNummer);
		kunde2.setVorname(neuVorname);
		kunde2.setNachname(neuNachname);
		kunde2.setTelefonnummer(neuTelefonNummer);
		kunde2.setEmail(neueMail);
		
		kundeDAO.updateKunde(kunde2);
		
		/* Kunden mit der Hausnummer aus der Datenbank Kunden Tabelle aufgreifen
		 * Platzhalter ? mit Hausnummer ersetzen
		*/
		PreparedStatement ps = con.prepareStatement("SELECT * FROM Kunde WHERE Haus_Hausnr = ?");
		ps.setInt(1, hausNummer);
		ResultSet rs = ps.executeQuery();

		/* Es existiert ein Kunde mit der uebergebenden Hausnummer
		 * Nun die Daten vergleichen, ob sie auch geändert wurden
		 */
		assertTrue(rs.next(), "Kunde sollte in der DB existieren.");
		assertEquals(neuVorname, rs.getString("Vorname"));
		assertEquals(neuNachname, rs.getString("Nachname"));
		assertEquals(neuTelefonNummer, rs.getString("Telefon"));
		assertEquals(neueMail, rs.getString("email"));
		rs.close();
		ps.close();
		
	}

}
