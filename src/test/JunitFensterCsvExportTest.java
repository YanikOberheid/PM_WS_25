package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import business.kunde.Sw;
import gui.fenster.FensterControl;

public class JunitFensterCsvExportTest {

	@Test
	void csvInhaltWirdKorrektErstellt_mitMockDaten() {

		int[][] mock = { { Sw.STUEREN_TERRASSE.id, 2 }, { Sw.ER_EG.id, 1 } };

		String csv = FensterControl.erstelleCsvInhaltFenster(mock);

		assertTrue(csv.startsWith("Sonderwunsch;Anzahl;Einzelpreis;Gesamtpreis\n"));

		assertTrue(csv.contains(Sw.STUEREN_TERRASSE.bes + ";2;"));
		assertTrue(csv.contains(Sw.ER_EG.bes + ";1;"));

		double p1 = Sw.STUEREN_TERRASSE.preis;
		double g1 = 2 * p1;
		assertTrue(csv.contains(Sw.STUEREN_TERRASSE.bes + ";2;" + p1 + ";" + g1));

		double p2 = Sw.ER_EG.preis;
		double g2 = 1 * p2;
		assertTrue(csv.contains(Sw.ER_EG.bes + ";1;" + p2 + ";" + g2));
	}

	@Test
	void csvInhaltBeiNull_enthaeltNurHeader() {

		String csv = FensterControl.erstelleCsvInhaltFenster(null);

		assertEquals("Sonderwunsch;Anzahl;Einzelpreis;Gesamtpreis\n", csv);
	}
}
