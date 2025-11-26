package test;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import business.kunde.Kunde;
import business.kunde.KundeModel;

class KundenDatenPruefenTest {

	@Test
	void testKundenEmailPruefen() throws Exception {

		// TestVariablen
		int hausNummer = 20;
		String vorname = "Max";
		String nachname = "Mustermann";
		String telefonNummer = "0123456789";
		String eMail = "eMail";

		Kunde kunde = new Kunde(hausNummer, vorname, nachname, telefonNummer, eMail); // Kein @-enthalten
		Kunde kunde2 = new Kunde(hausNummer, vorname, nachname, telefonNummer, ""); // Leere Daten
		
		
		KundeModel kundeModel = KundeModel.getInstance();                         
        
		assertFalse(kundeModel.isValidCustomer(kunde, false), "Ungültige E-Mail Adresse!"); // Kein @-enthalten
		assertFalse(kundeModel.isValidCustomer(kunde2, false), "Ungültige E-Mail Adresse!"); // Leere Daten
	}
	
	@Test
	void testKundenTelefonNummerPruefen() throws Exception {

		// TestVariablen
		int hausNummer = 21;
		String vorname = "Max";
		String nachname = "Mustermann";
		String telefonNummer = "qwertzu";
		String eMail = "max@mustermann.com";

		Kunde kunde = new Kunde(hausNummer, vorname, nachname, telefonNummer, eMail); // Nur Buchstaben
		Kunde kunde2 = new Kunde(hausNummer, vorname, nachname, "0123456--/", eMail); // Sonderzeichen enthalten
		Kunde kunde3 = new Kunde(hausNummer, vorname, nachname, "", eMail); // Leere Daten
		
		
		KundeModel kundeModel = KundeModel.getInstance();                         
        
		assertFalse(kundeModel.isValidCustomer(kunde, false), "Ungültige Telefonnummer!"); // Nur Buchstaben
		assertFalse(kundeModel.isValidCustomer(kunde2, false), "Ungültige Telefonnummer!"); // Sonderzeichen enthalten
		assertFalse(kundeModel.isValidCustomer(kunde3, false), "Ungültige Telefonnummer!"); // Leere Daten
	}

}