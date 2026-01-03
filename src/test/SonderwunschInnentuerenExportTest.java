package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SonderwunschInnentuerenExportTest {

	@BeforeAll
	public static void initJavaFx() {

	}

	@Test
	public void testInnentuerenExport() throws Exception {
		final int KLARGLAS_ID = 101;
		final int MILCHGLAS_ID = 102;
		final int HOLZTUER_ID = 103;

		int[][] result = new int[][] { { KLARGLAS_ID, 2 }, { MILCHGLAS_ID, 1 }, { HOLZTUER_ID, 3 } };

		assertEquals(3, result.length);

		assertArrayEquals(new int[] { KLARGLAS_ID, 2 }, result[0]);
		assertArrayEquals(new int[] { MILCHGLAS_ID, 1 }, result[1]);
		assertArrayEquals(new int[] { HOLZTUER_ID, 3 }, result[2]);
	}
}
