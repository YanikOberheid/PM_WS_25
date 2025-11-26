package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.kunde.DatabaseConnection;
import business.kunde.SonderwuenscheDAO;
import business.kunde.SonderwuenscheDAOImplementation;

public class SonderwuenscheFliesenTest {

	private SonderwuenscheDAO dao;
	private final int TEST_HAUSNUMMER = 2; // Hausnummer für Test
	private final int FLIESEN_KATEGORIE = 70;
	private final int FLIESEN_ID = 9; // fliesen im bad sonderwunsch

	@BeforeEach
	public void setUp() throws Exception {
		dao = new SonderwuenscheDAOImplementation();

		// AutoCommit aktivieren
		DatabaseConnection.getInstance().getConnection().setAutoCommit(true);

		// Test Daten löschen
		try {
			dao.delete(TEST_HAUSNUMMER);
		} catch (SQLException ignored) {
		}
	}

	@Test
	public void testSpeichernUndLadenFliesen() throws Exception {
		int[] expected = { FLIESEN_ID };

		dao.update(TEST_HAUSNUMMER, expected);

		int[] actual = dao.get(TEST_HAUSNUMMER);

		Arrays.sort(expected);
		Arrays.sort(actual);

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testLadenMitKategorieFliesen() throws Exception {
		int[] daten = { FLIESEN_ID };

		dao.update(TEST_HAUSNUMMER, daten);

		int[] result = dao.get(TEST_HAUSNUMMER, FLIESEN_KATEGORIE);

		assertNotNull(result, "Ergebnis darf nicht null sein");
		assertEquals(1, result.length, "Es sollte genau 1 Fliesen-Sonderwunsch geben");
		assertEquals(FLIESEN_ID, result[0], "Die ID muss 9 sein");
	}

	@Test
	public void testDeleteFliesen() throws Exception {
		int[] daten = { FLIESEN_ID };

		dao.update(TEST_HAUSNUMMER, daten);

		dao.delete(TEST_HAUSNUMMER);

		int[] result = dao.get(TEST_HAUSNUMMER);

		assertEquals(0, result.length, "Nach dem Löschen muss die Liste leer sein");
	}
}
