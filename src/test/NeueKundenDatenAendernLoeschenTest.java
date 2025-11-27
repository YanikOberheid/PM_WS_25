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
	
	// Daten in Kunden Löschen nach jeden Test //
	
	/*
	@AfterEach 
	void clearTable() throws SQLException{
		con.createStatement().execute("DELETE FROM Kunde"); 
	}*/
	

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
		
		// Fixe Hausnummer fuer Test
		int hausNummer = 21;
		
		// AlteKundenDaten fuer INSERT
		String vorname = "Max";
		String nachname = "Mustermann";
		String telefonNummer = "0123456789";
		String eMail = "max@mustermann.com";
		
		// Neue KundeDaten (fuer UPDATE)
		String neuVorname = "Erika";
		String neuNachname = "Musterfrau";
		String neuTelefonNummer = "9876543210";
		String neueMail = "erika@musterfrau.com";
		
		
		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		
		//Falls ein Kunde mit der Hausnummer 21 existiert vorher löschen
		PreparedStatement clear = con.prepareStatement("DELETE FROM Kunde WHERE Haus_Hausnr = ?");
		clear.setInt(1, hausNummer);
		clear.executeUpdate();
		clear.close();
		
		// Kunden anlegen (fuer Insert)
		Kunde kunde = new Kunde(hausNummer, vorname, nachname, telefonNummer, eMail);
		kundeDAO.add(kunde);
		
		// idKunde nach Insert aus DB holen
		Kunde bestehenderKunde = kundeDAO.findByHausnummer(hausNummer);
		assertNotNull(bestehenderKunde, "Kunde sollte nach INSERT existieren.");
		
		bestehenderKunde.setVorname(neuVorname);
		bestehenderKunde.setNachname(neuNachname);
		bestehenderKunde.setTelefonnummer(neuTelefonNummer);
		bestehenderKunde.setEmail(neueMail);
		
		kundeDAO.updateKunde(bestehenderKunde);
		
		
		// DB prüfen. Kunde aus der DB holen und überprüfen ob die Namen übereinstimmen
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
	
	@Test
	void testLoescheKundendaten() throws Exception{
		// Fixe Hausnummer fuer Test
		int hausNummer = 22;
				
		// AlteKundenDaten fuer INSERT
		String vorname = "Max";
		String nachname = "Mustermann";
		String telefonNummer = "0123456789";
		String eMail = "max@mustermann.com";
		
		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		
		//Falls ein Kunde mit der Hausnummer 22 existiert vorher löschen
		PreparedStatement clear = con.prepareStatement("DELETE FROM Kunde WHERE Haus_Hausnr = ?");
		clear.setInt(1, hausNummer);
		clear.executeUpdate();
		clear.close();
		
		Kunde kunde = new Kunde(hausNummer, vorname, nachname, telefonNummer, eMail);
		kundeDAO.add(kunde);
		
		Kunde bestehenderKunde = kundeDAO.findByHausnummer(hausNummer);
		assertNotNull(bestehenderKunde, "Kunde sollte vor DELETE existieren");
		
		kundeDAO.deleteKunde(bestehenderKunde.getIdKunde());
		
		PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM Kunde WHERE idKunde = ?");
		
		ps.setInt(1, bestehenderKunde.getIdKunde());
		ResultSet rs = ps.executeQuery();
		
		assertTrue(rs.next());
		
		// Erwartete Werte nach Löschen
		assertEquals("GELOESCHT", rs.getString("Vorname"));
		assertEquals("GELOESCHT", rs.getString("Nachname"));
		assertNull(rs.getString("Telefon"));
		assertNull(rs.getString("email"));
		
		rs.close();
		ps.close();
		
		
	}

}
