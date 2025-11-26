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

public class SonderwuenscheDAOImplementationTest {

	private SonderwuenscheDAO dao;
	private final int TEST_HAUSNUMMER = 1;

	@BeforeEach
	public void setUp() throws Exception {
		dao = new SonderwuenscheDAOImplementation();

		// GLOBAL AutoCommit fix im gesamten Testlauf
		DatabaseConnection.getInstance().getConnection().setAutoCommit(true);

		try {
			dao.delete(TEST_HAUSNUMMER);
		} catch (SQLException e) {
		}
	}

	@Test
	public void testSpeichernUndLaden() throws Exception {
		int[] expected = { 1, 2, 3 };

		// WICHTIG: Hier AutoCommit erneut einschalten
		DatabaseConnection.getInstance().getConnection().setAutoCommit(true);

		dao.update(TEST_HAUSNUMMER, expected);

		int[] actual = dao.get(TEST_HAUSNUMMER);

		Arrays.sort(expected);
		Arrays.sort(actual);

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testDelete() throws Exception {
		int[] daten = { 1, 2 };

		// WICHTIG
		DatabaseConnection.getInstance().getConnection().setAutoCommit(true);

		dao.update(TEST_HAUSNUMMER, daten);

		dao.delete(TEST_HAUSNUMMER);

		int[] result = dao.get(TEST_HAUSNUMMER);

		assertEquals(0, result.length);
	}

	@Test
	public void testLadenMitKategorie() throws Exception {
		int[] daten = { 1, 2, 3 };

		// WICHTIG
		DatabaseConnection.getInstance().getConnection().setAutoCommit(true);

		dao.update(TEST_HAUSNUMMER, daten);

		int[] result = dao.get(TEST_HAUSNUMMER, 1);

		assertNotNull(result);
	}
}
