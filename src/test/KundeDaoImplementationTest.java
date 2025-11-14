package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import src.business.kunde.DatabaseConnection;
import src.business.kunde.Kunde;
import src.business.kunde.KundeDaoImplementation;
import src.business.kunde.KundeModel;
import src.gui.kunde.KundeControl;

class KundeDaoImplementationTest {

	// Datenbank Verbindung fürs Testen herstellen
		static Connection con = DatabaseConnection.getInstance().getConnection();

		// Daten in Kunden Löschen nach jeden Test
		@AfterEach 
		void clearTable() throws SQLException{
			con.createStatement().execute("DELETE FROM Kunde"); 
		}

		// Verbindung zur Datenbank trennen
		@AfterAll
		static void tearDown() throws SQLException {
			con.close();
		}

		// Ueberpruefen ob tatsächlich der uebergebene Kunde hinzugefuegt wurde
		// Es wird unter der Hausnummer geschaut 
		// ob Vornamen, Nachnamen etc. übereinstimmen
		@Test
		void testAddKundeErfolgreich() throws SQLException {

			// TestVariablen
			int hausNummer = 2;
			String vorname = "Max";
			String nachname = "Mustermann";
			String telefonNummer = "0123456789";
			String eMail = "max@mustermann.com";
			
			Kunde kunde = new Kunde(hausNummer, vorname, nachname, telefonNummer, eMail);

			KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
			kundeDAO.add(kunde);

			// Kunden mit der Hausnummer aus der Datenbank Kunden Tabelle aufgreifen
			// Platzhalter ? mit Hausnummer ersetzen
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Kunde WHERE Haus_Hausnr = ?");
			ps.setInt(1, hausNummer);
			ResultSet rs = ps.executeQuery();

			// Es existiert ein Kunde mit der uebergebenden Hausnummer
			// Nun die Daten vergleichen
			assertTrue(rs.next(), "Kunde sollte in der DB existieren.");
			assertEquals(vorname, rs.getString("Vorname"));
			assertEquals(nachname, rs.getString("Nachname"));
			assertEquals(telefonNummer, rs.getString("Telefon"));
			assertEquals(eMail, rs.getString("email"));
			rs.close();
			ps.close();

		}
		
		// Testen ob die KundenDaten
		// Leer sind und es dann dementprechend einen SQLException "wirft"
		@Test
		void testAddKundeLeereDaten() throws SQLException {
			// TestVariablen
			int hausNummer = 5;
			String vorname = "Max";
			String nachname = "Mustermann";
			String telefonNummer = "0123456789";
			String eMail = "max@mustermann.com";
			
			KundeModel kundeModel = KundeModel.getInstance();
			
			Kunde kunde = new Kunde(0, vorname, nachname, telefonNummer, eMail);
			Kunde kunde2 = new Kunde(hausNummer, "", nachname, telefonNummer, eMail);
			
			assertFalse(kundeModel.isValidCustomer(kunde), "Kunde sollte ungültig sein");
			assertFalse(kundeModel.isValidCustomer(kunde2), "Kunde sollte ungültig sein");
			
			// Weitere Leere Dateneingabe Tests
			/*
			Kunde kunde3 = new Kunde(hausNummer, vorname, "", telefonNummer, eMail);
			Kunde kunde4 = new Kunde(hausNummer, vorname, nachname, "", eMail);
			Kunde kunde5 = new Kunde(0, vorname, nachname, telefonNummer, "");
			
			assertFalse(kundeModel.isValidCustomer(kunde3), "Kunde sollte ungültig sein");
			assertFalse(kundeModel.isValidCustomer(kunde4), "Kunde sollte ungültig sein");
			assertFalse(kundeModel.isValidCustomer(kunde5), "Kunde sollte ungültig sein");
			*/
		}
		
		// Testen ob es eine Dopplung gibt.
		// quasi ob die Hausnummer schon besetzt ist.
		@Test
		void testAddKundeHausnummerDopplung() throws SQLException {
			// Kunde1
			// TestVariablen
			int hausNummer1 = 1;
			String vorname1 = "Max";
			String nachname1 = "Mustermann";
			String telefonNummer1 = "0123456789";
			String eMail1 = "max@mustermann.com";
			
			Kunde kunde1 = new Kunde(hausNummer1, vorname1, nachname1, telefonNummer1, eMail1);
			
			KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
			kundeDAO.add(kunde1);
			
			// Kunde2
			// TestVariablen
			int hausNummer2 = 1;
			String vorname2 = "Erika";
			String nachname2 = "Mustermann";
			String telefonNummer2 = "0123456789";
			String eMail2 = "Erika@mustermann.com";
			
			Kunde kunde2 = new Kunde(hausNummer2, vorname2, nachname2, telefonNummer2, eMail2);
			
			KundeModel kundeModel = KundeModel.getInstance();
			
			assertFalse(kundeModel.isValidCustomer(kunde2), "Hausnummer ist besetzt");
		}

}
