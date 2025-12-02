package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.kunde.DatabaseConnection;
import business.kunde.SonderwuenscheDAOImplementation;
import business.kunde.Sw;
import business.kunde.SwKategorie;

public class SonderwuenscheFensterUndAußentuerenLadenUndSpeichernTest {

	private SonderwuenscheDAOImplementation swDao;
	private final int TEST_HAUSNUMMER = 21; // Hausnummer für Test
	private final int FENSTER_AUSSENTUEREN_KATEGORIE_ID = SwKategorie.FENSTER_AUSSENTUEREN.id;	// Sonderwunschkategorie Fenster und Aussentueren 
	private final int FENSTER_AUSSENTUEREN_SONDERWUNSCH_ID = Sw.STUEREN_TERRASSE.id; // Spezifisches Sonderwunsch aus der Sonderwunschkategorie Fenster und Aussentueren 
	
	/* 	
	 *  Für das Ausführen der Methoden testSpeichernUndLadenFensterAussentueren & 
	 * 	testLadenMitKategorieFensterAussentueren benötigt damit 
	 * 	Sie sauber mit einem nicht vorhanden Datensatz in Sonderwunsch_has_Haus 
	 *  mit der vordefinierten TEST_HAUSNUMMER arbeiten können
	*/
	@BeforeEach
	public void setUp() throws Exception {
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
	public void testSpeichernUndLadenFensterAussentueren() throws Exception {
		int[] expected = { FENSTER_AUSSENTUEREN_SONDERWUNSCH_ID };

		swDao.update(TEST_HAUSNUMMER, expected);

		int[] actual = swDao.get(TEST_HAUSNUMMER);

		Arrays.sort(expected);
		Arrays.sort(actual);

		assertArrayEquals(expected, actual);
	}
	
	
	@Test
	public void testLadenMitKategorieFensterAussentueren() throws Exception {
		int[] daten = { FENSTER_AUSSENTUEREN_SONDERWUNSCH_ID };

		swDao.update(TEST_HAUSNUMMER, daten);

		int[] result = swDao.get(TEST_HAUSNUMMER, FENSTER_AUSSENTUEREN_KATEGORIE_ID);

		assertNotNull(result, "Ergebnis darf nicht null sein");
		assertEquals(1, result.length, "Es sollte genau 1 Fenster und Aussentueren-Sonderwunsch geben");
		assertEquals(FENSTER_AUSSENTUEREN_SONDERWUNSCH_ID, result[0], "Die ID muss "+ Integer.toString(Sw.STUEREN_TERRASSE.id) + " sein");
	}
	
	/*
	@Test
	public void testDeleteFensterAussentueren() throws Exception {
		int[] daten = { FENSTER_AUSSENTUEREN_SONDERWUNSCH_ID };

		swDao.update(TEST_HAUSNUMMER, daten);

		swDao.delete(TEST_HAUSNUMMER);

		int[] result = swDao.get(TEST_HAUSNUMMER);

		assertEquals(0, result.length, "Nach dem Löschen muss die Liste leer sein");
	}*/
}
