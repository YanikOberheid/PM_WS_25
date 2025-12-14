package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.kunde.DatabaseConnection;
import business.kunde.Kunde;
import business.kunde.KundeDaoImplementation;
import business.kunde.SonderwuenscheDAOImplementation;
import business.kunde.Sw;
import business.kunde.SwKategorie;

public class SonderwuenscheFensterUndAußentuerenLadenUndSpeichernTest {

	private SonderwuenscheDAOImplementation swDao;
	private final int TEST_HAUSNUMMER = 22; // Hausnummer für Test
	private final int TEST_ANZAHL = 1;
	private final int FENSTER_AUSSENTUEREN_KATEGORIE_ID = SwKategorie.FENSTER_AUSSENTUEREN.id; // Sonderwunschkategorie FENSTER_AUSSENTUEREN
																
	// Spezifisches Sonderwunsch aus Parkett
	private final int FENSTER_AUSSENTUEREN_SONDERWUNSCH_ID = Sw.STUEREN_TERRASSE.id;
	private final int FENSTER_AUSSENTUEREN_SONDERWUNSCH_ID_2 = Sw.STUEREN_DACHTERRASSE.id;

	/*
	 * Für das Ausführen der Methoden testSpeichernUndLadenFensterAussentueren &
	 * testLadenMitKategorieFensterAussentueren benötigt damit Sie sauber mit einem
	 * nicht vorhanden Datensatz in Sonderwunsch_has_Haus mit der vordefinierten
	 * TEST_HAUSNUMMER arbeiten können
	 */
	@BeforeEach
	public void setUp() throws Exception {
		// TestVariablen
		int hausNummer = 22;
		String vorname = "Max";
		String nachname = "Mustermann";
		String telefonNummer = "0123456789";
		String eMail = "max@mustermann.com";

		Kunde kunde = new Kunde(hausNummer, vorname, nachname, telefonNummer, eMail);

		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		kundeDAO.add(kunde);
		
		swDao = new SonderwuenscheDAOImplementation();

		// AutoCommit aktivieren
		DatabaseConnection.getInstance().getConnection().setAutoCommit(true);

		// Test Daten löschen
		try {
			swDao.delete(TEST_HAUSNUMMER);
		} catch (SQLException ignored) {
		}
	}

	@Test
	public void testSpeichernUndLadenParkett() throws Exception {
		int [][] erwartet = {
				{FENSTER_AUSSENTUEREN_SONDERWUNSCH_ID, TEST_ANZAHL},
				{FENSTER_AUSSENTUEREN_SONDERWUNSCH_ID_2, TEST_ANZAHL}
		};
		
		swDao.update(TEST_HAUSNUMMER, erwartet);
		
		int[][] geladen = swDao.getMitAnzahl(TEST_HAUSNUMMER, FENSTER_AUSSENTUEREN_KATEGORIE_ID);
		
		sortBySwId(erwartet);
		sortBySwId(geladen);
		
		if (erwartet != null && geladen !=null && erwartet.length == geladen.length) {
			for (int i = 0; i < erwartet.length; i++) {
				assertArrayEquals(erwartet[i], 
						geladen[i],
						"Die gespeicherten Fenster-Außentueren-Sonderwünsche sollten nach dem Laden identisch sein"
				);
			}
		}
	}
	
	private static void sortBySwId(int[][] arr) {
        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));
    }
}
