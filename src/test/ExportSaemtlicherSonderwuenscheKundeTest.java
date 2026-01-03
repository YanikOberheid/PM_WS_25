package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import business.kunde.SonderwuenscheDAOImplementation;
import business.kunde.Sw;

public class ExportSaemtlicherSonderwuenscheKundeTest {

	private static final int HAUSNUMMER = 1;

	private final SonderwuenscheDAOImplementation dao = new SonderwuenscheDAOImplementation();

	@AfterEach
	void cleanup() throws Exception {
		dao.update(HAUSNUMMER, new int[0][0]);
	}

	@Test
	void testExportSaemtlicherSonderwuenscheAlsCsv() throws Exception {

		// Arrange
		int[][] sonderwuensche = { { Sw.STD_HEIZKOERPER.id, 2 }, { Sw.E_MARKISE_EG.id, 1 }, { Sw.F_BAD_DG.id, 1 } };

		dao.update(HAUSNUMMER, sonderwuensche);

		File exportDatei = new File("sonderwuensche_test.csv");
		if (exportDatei.exists()) {
			exportDatei.delete();
		}

		// javafx freie export
		exportiereSonderwuensche(sonderwuensche, exportDatei);

		// Assert
		assertTrue(exportDatei.exists());

		String content = Files.readString(exportDatei.toPath());
		assertTrue(content.contains(Sw.STD_HEIZKOERPER.bes));
		assertTrue(content.contains(Sw.E_MARKISE_EG.bes));
		assertTrue(content.contains(Sw.F_BAD_DG.bes));

		exportDatei.delete();
	}

	private void exportiereSonderwuensche(int[][] sw, File file) throws Exception {
		StringBuilder csv = new StringBuilder();
		csv.append("Sonderwunsch;Anzahl;Einzelpreis;Gesamtpreis\n");

		for (int[] pair : sw) {
			Sw s = Sw.findeMitId(pair[0]);
			int anz = pair[1];
			double gesamt = anz * s.preis;

			csv.append(s.bes).append(";").append(anz).append(";").append(s.preis).append(";").append(gesamt)
					.append("\n");
		}

		Files.writeString(file.toPath(), csv.toString());
	}
}
