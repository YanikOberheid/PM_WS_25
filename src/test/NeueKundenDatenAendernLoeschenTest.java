package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import business.kunde.Kunde;
import business.kunde.KundeDaoImplementation;

class NeueKundenDatenAendernLoeschenTest {

	static Connection con = business.kunde.DatabaseConnection.getInstance().getConnection();

	// Daten in Kunden Löschen nach jeden Test //

	/*
	 * @AfterEach void clearTable() throws SQLException{
	 * con.createStatement().execute("DELETE FROM Kunde"); }
	 */

	// Verbindung zur Datenbank trennen
	@AfterAll
	static void tearDown() throws SQLException {
		con.close();
	}

	/*
	 * Hier wird getestet ob die Kundendaten Vorname, Nachname, E-Mail und
	 * Telefonnummer sich aendern. Zuerst wird ein Kunde angelegt und die Daten
	 * geändert
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

		// Falls ein Kunde mit der Hausnummer 21 existiert vorher löschen
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

	}
}
