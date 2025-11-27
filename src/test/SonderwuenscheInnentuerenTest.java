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

public class SonderwuenscheInnentuerenTest {

	private SonderwuenscheDAO dao;
	private final int TEST_HAUSNUMMER = 3;
	private final int INNENTUER_KATEGORIE = 40;
	private final int INNENTUER_ID = 4;

	@BeforeEach
	public void setUp() throws Exception {
		dao = new SonderwuenscheDAOImplementation();

		DatabaseConnection.getInstance().getConnection().setAutoCommit(true);

		try {
			dao.delete(TEST_HAUSNUMMER);
		} catch (SQLException ignored) {
		}
	}

	@Test
	public void testSpeichernUndLadenInnentueren() throws Exception {
		int[] expected = { INNENTUER_ID };

		dao.update(TEST_HAUSNUMMER, expected);

		int[] actual = dao.get(TEST_HAUSNUMMER);
		Arrays.sort(actual);

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testLadenMitKategorieInnentueren() throws Exception {
		int[] daten = { INNENTUER_ID };

		dao.update(TEST_HAUSNUMMER, daten);

		int[] result = dao.get(TEST_HAUSNUMMER, INNENTUER_KATEGORIE);

		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(INNENTUER_ID, result[0]);
	}

	@Test
	public void testDeleteInnentueren() throws Exception {
		int[] daten = { INNENTUER_ID };

		dao.update(TEST_HAUSNUMMER, daten);

		dao.delete(TEST_HAUSNUMMER);

		int[] result = dao.get(TEST_HAUSNUMMER);

		assertEquals(0, result.length);
	}
}