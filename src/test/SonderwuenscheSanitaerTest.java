package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.kunde.DatabaseConnection;
import business.kunde.SonderwuenscheDAO;
import business.kunde.SonderwuenscheDAOImplementation;

public class SonderwuenscheSanitaerTest {

	private SonderwuenscheDAO dao;
	private final int TEST_HAUSNUMMER = 2;

	private final int SANITAER_KATEGORIE = 80; 
	private final int SANITAER_ID = 801;        // ← muss gültige ID für Sanitär sein

	@BeforeEach
	public void setUp() throws Exception {
		dao = new SonderwuenscheDAOImplementation();
		DatabaseConnection.getInstance().getConnection().setAutoCommit(true);
		try {
			dao.delete(TEST_HAUSNUMMER);
		} catch (SQLException ignored) {}
	}

	@Test
	public void testSpeichernUndLadenSanitaer() throws Exception {
		int[] expected = { SANITAER_ID };
		dao.update(TEST_HAUSNUMMER, expected);
		int[] actual = dao.get(TEST_HAUSNUMMER);
		Arrays.sort(expected);
		Arrays.sort(actual);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testLadenMitKategorieSanitaer() throws Exception {
		int[] daten = { SANITAER_ID };
		dao.update(TEST_HAUSNUMMER, daten);
		int[] result = dao.get(TEST_HAUSNUMMER, SANITAER_KATEGORIE);
		assertNotNull(result, "Ergebnis darf nicht null sein");
		assertEquals(1, result.length, "Es sollte genau 1 Sanitär-Sonderwunsch geben");
		assertEquals(SANITAER_ID, result[0], "Die ID muss mit dem Sanitär-SW übereinstimmen");
	}

	@Test
	public void testDeleteSanitaer() throws Exception {
		int[] daten = { SANITAER_ID };
		dao.update(TEST_HAUSNUMMER, daten);
		dao.delete(TEST_HAUSNUMMER);
		int[] result = dao.get(TEST_HAUSNUMMER);
		assertEquals(0, result.length, "Nach dem Löschen muss die Liste leer sein");
	}
}
