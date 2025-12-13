package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.kunde.SonderwuenscheDAOImplementation;
import business.kunde.Sw;
import business.kunde.SwKategorie;

class SonderwunschAussenanlagenPreisBerechnungTest {

	// Zum Referenzieren
	/*
	 * Außenanlagen ABSTELL_TERRASSE_EG (901, "Abstellraum auf der Terrasse des EG",
	 * 3590.0), VEA_MARKISE_EG (902,
	 * "Vorbereitug für elektrische Antriebe Markise EG", 170.0), VEA_MARKISE_DG
	 * (903, "Vorbereitung für elektrische Antriebe Markise DG", 170.0),
	 * E_MARKISE_EG (904, "Elektrische Markise EG", 890.0), E_MARKISE_DG (905,
	 * "Elektrische Markise DG", 890.0), EA_GARAGENTOR (906,
	 * "Elektrischen Antrieb für das Garagentor", 990.0), ST_GARAGENTOR (907,
	 * "Sektionaltor anstatt Schwingtor für die Garage", 790.0);
	 */

	private SonderwuenscheDAOImplementation swDao;

	// Test Variablen
	private final int TEST_HAUSNUMMER = 22; // Hausnummer für Test
	private final int AUSSENANLAGEN_KATEGORIE_ID = SwKategorie.AUSSENANLAGEN.id;
	boolean state_ABSTELL_TERRASSE_EG = true;
	boolean state_VEA_MARKISE_EG = false;
	boolean state_VEA_MARKISE_DG = false;
	boolean state_E_MARKISE_EG = false;
	boolean state_E_MARKISE_DG = false;
	boolean state_EA_GARAGENTOR = false;
	boolean state_ST_GARAGENTOR = true;

	/*
	 * Für das Ausführen der Methoden testSpeichernUndLadenFensterAussentueren &
	 * testLadenMitKategorieFensterAussentueren benötigt damit Sie sauber mit einem
	 * nicht vorhanden Datensatz in Sonderwunsch_has_Haus mit der vordefinierten
	 * TEST_HAUSNUMMER arbeiten können
	 */
	@BeforeEach
	public void setUp() throws Exception {
		resetBooleanValues();

		swDao = new SonderwuenscheDAOImplementation();

		// AutoCommit aktivieren
		business.kunde.DatabaseConnection.getInstance().getConnection().setAutoCommit(true);

		// Test Daten löschen
		try {
			swDao.delete(TEST_HAUSNUMMER);
		} catch (SQLException ignored) {
		}
	}

	private void resetBooleanValues() {
		state_ABSTELL_TERRASSE_EG = false;
		state_VEA_MARKISE_EG = false;
		state_VEA_MARKISE_DG = false;
		state_E_MARKISE_EG = false;
		state_E_MARKISE_DG = false;
		state_EA_GARAGENTOR = false;
		state_ST_GARAGENTOR = false;
	}

	@Test
	public void testAlleSonderwuenscheAusgewaehltPreisBerechnung() throws Exception {
		state_ABSTELL_TERRASSE_EG = true;
		state_VEA_MARKISE_EG = true;
		state_VEA_MARKISE_DG = true;
		state_E_MARKISE_EG = true;
		state_E_MARKISE_DG = true;
		state_EA_GARAGENTOR = true;
		state_ST_GARAGENTOR = true;

		// Vordefinierter Preis
		double gesamtpreis = 7490.0;

		assertEquals(berechneUndZeigePreisSonderwuensche(), gesamtpreis);
	}

	@Test
	public void testDreiSonderwuenscheAusgewaehltPreiBerechnungs() throws Exception {
		state_ABSTELL_TERRASSE_EG = true;
		state_VEA_MARKISE_EG = true;
		state_VEA_MARKISE_DG = false;
		state_E_MARKISE_EG = false;
		state_E_MARKISE_DG = false;
		state_EA_GARAGENTOR = false;
		state_ST_GARAGENTOR = true;

		// Vordefinierter Preis
		double gesamtpreis = 4550.0;

		assertEquals(berechneUndZeigePreisSonderwuensche(), gesamtpreis);
	}

	@Test
	public void testEinSonderwuenscheAusgewaehltPreisBerechnung() throws Exception {
		state_ABSTELL_TERRASSE_EG = true;
		state_VEA_MARKISE_EG = false;
		state_VEA_MARKISE_DG = false;
		state_E_MARKISE_EG = false;
		state_E_MARKISE_DG = false;
		state_EA_GARAGENTOR = false;
		state_ST_GARAGENTOR = false;

		// Vordefinierter Preis
		double gesamtpreis = 3590.0;

		assertEquals(berechneUndZeigePreisSonderwuensche(), gesamtpreis);
	}

	// Fuer den Test benoetigten Methoden
	public double berechneUndZeigePreisSonderwuensche() {
		double preis = 0.0;

		if (state_ABSTELL_TERRASSE_EG)
			preis += Sw.ABSTELL_TERRASSE_EG.preis;
		if (state_VEA_MARKISE_EG)
			preis += Sw.VEA_MARKISE_EG.preis;
		if (state_VEA_MARKISE_DG)
			preis += Sw.VEA_MARKISE_DG.preis;
		if (state_E_MARKISE_EG)
			preis += Sw.E_MARKISE_EG.preis;
		if (state_E_MARKISE_DG)
			preis += Sw.E_MARKISE_DG.preis;
		if (state_EA_GARAGENTOR)
			preis += Sw.EA_GARAGENTOR.preis;
		if (state_ST_GARAGENTOR)
			preis += Sw.ST_GARAGENTOR.preis;

		return preis;
	}

	/*
	 * @Test public void testAlleSonderwuenscheAusgewaehltPreis() throws Exception {
	 * state_ABSTELL_TERRASSE_EG = true; state_VEA_MARKISE_EG = true;
	 * state_VEA_MARKISE_DG = true; state_E_MARKISE_EG = true; state_E_MARKISE_DG =
	 * true; state_EA_GARAGENTOR = true; state_ST_GARAGENTOR = true;
	 * 
	 * // Vordefinierter Preis double gesamtpreis = 7490.0;
	 * 
	 * 
	 * int[] expected = checkboxenZuIntArray();
	 * 
	 * 
	 * swDao.update(TEST_HAUSNUMMER, expected);
	 * 
	 * 
	 * int[] actual = swDao.get(TEST_HAUSNUMMER, AUSSENANLAGEN_KATEGORIE_ID);
	 * 
	 * Arrays.sort(expected); Arrays.sort(actual);
	 * 
	 * 
	 * if (expected.length == actual.length) {
	 * System.out.println("\n Expected\t      Actual"); for(int i = 0; i <
	 * expected.length; i++) { System.out.println("Expected--> " + expected[i] +
	 * "\t" + actual[i] + " <--Actual"); } }
	 * 
	 * assertArrayEquals(expected, actual);
	 * 
	 * 
	 * assertEquals(berechneUndZeigePreisSonderwuensche(), gesamtpreis); }
	 */

	/*
	 * public int[] checkboxenZuIntArray() { Vector<Integer> v = new Vector<>();
	 * 
	 * if (state_ABSTELL_TERRASSE_EG) v.add(Sw.ABSTELL_TERRASSE_EG.id); if
	 * (state_VEA_MARKISE_EG) v.add(Sw.VEA_MARKISE_EG.id); if (state_VEA_MARKISE_DG)
	 * v.add(Sw.VEA_MARKISE_DG.id); if (state_E_MARKISE_EG)
	 * v.add(Sw.E_MARKISE_EG.id); if (state_E_MARKISE_DG) v.add(Sw.E_MARKISE_DG.id);
	 * if (state_EA_GARAGENTOR) v.add(Sw.EA_GARAGENTOR.id); if (state_ST_GARAGENTOR)
	 * v.add(Sw.ST_GARAGENTOR.id);
	 * 
	 * int[] grundrissSw = new int[v.size()]; for (int i = 0; i < v.size(); i++)
	 * grundrissSw[i] = v.get(i);
	 * 
	 * return grundrissSw; }
	 */

}