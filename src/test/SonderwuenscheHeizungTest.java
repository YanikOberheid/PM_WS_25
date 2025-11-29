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
import business.kunde.Sw;
import business.kunde.SwKategorie;

/**
 * JUnit-Tests für die Heizungs-Sonderwünsche (Kategorie 50).
 * Entspricht Task [3]: "Ausgewählte Sonderwünsche zu Heizkörpern anzeigen".
 */
public class SonderwuenscheHeizungTest {

    private SonderwuenscheDAO dao;

    private final int TEST_HAUSNUMMER = 3; // Hausnummer nur für Tests, frei wählbar
    private final int HEIZUNG_KATEGORIE = SwKategorie.HEIZKOERPER.id;

    // eine konkrete Heizungs-ID 
    private final int HEIZUNG_ID_STD_HEIZKOERPER = Sw.STD_HEIZKOERPER.id;

    // optional: weitere Heizungs-IDs, falls gebraucht
    // private final int HEIZUNG_ID_GLATT = 14;
    // private final int HEIZUNG_ID_HANDTUCH = 15;
    // private final int HEIZUNG_ID_FBH_OHNE_DG = 16;
    // private final int HEIZUNG_ID_FBH_MIT_DG  = 17;

    @BeforeEach
    public void setUp() throws Exception {
        dao = new SonderwuenscheDAOImplementation();

        // AutoCommit aktivieren, damit update()/delete() sauber laufen
        DatabaseConnection.getInstance().getConnection().setAutoCommit(true);

        // Test-Daten für diese Hausnummer löschen
        try {
            dao.delete(TEST_HAUSNUMMER);
        } catch (SQLException ignored) {
        }
    }

    /**
     * Testet, ob ein gespeicherter Heizungs-Sonderwunsch
     * über dao.get(hausnr) korrekt wieder geladen wird.
     */
    @Test
    public void testSpeichernUndLadenHeizung() throws Exception {
        int[] expected = { HEIZUNG_ID_STD_HEIZKOERPER };

        dao.update(TEST_HAUSNUMMER, expected);

        int[] actual = dao.get(TEST_HAUSNUMMER);

        Arrays.sort(expected);
        Arrays.sort(actual);

        assertArrayEquals(expected, actual);
    }

    /**
     * Testet die Variante mit Kategorie-Filter:
     * dao.get(hausnr, 50) soll nur Heizungs-Sonderwünsche liefern.
     */
    @Test
    public void testLadenMitKategorieHeizung() throws Exception {
        // Wir speichern testweise einen Heizungs-SW und z.B. einen Fliesen-SW (ID 705)
        int[] daten = { HEIZUNG_ID_STD_HEIZKOERPER, Sw.F_BAD_DG.id };

        dao.update(TEST_HAUSNUMMER, daten);

        int[] result = dao.get(TEST_HAUSNUMMER, HEIZUNG_KATEGORIE);

        assertNotNull(result, "Ergebnis darf nicht null sein");
        assertEquals(1, result.length, "Es sollte genau 1 Heizungs-Sonderwunsch geben");
        assertEquals(HEIZUNG_ID_STD_HEIZKOERPER, result[0],
                "Die ID muss " + HEIZUNG_ID_STD_HEIZKOERPER + " (Standard-Heizkörper) sein");
    }

    /**
     * Testet, ob delete(hausnr) auch die Heizungs-Sonderwünsche sauber entfernt.
     */
    @Test
    public void testDeleteHeizung() throws Exception {
        int[] daten = { HEIZUNG_ID_STD_HEIZKOERPER };

        dao.update(TEST_HAUSNUMMER, daten);

        dao.delete(TEST_HAUSNUMMER);

        int[] result = dao.get(TEST_HAUSNUMMER);

        assertEquals(0, result.length, "Nach dem Löschen muss die Liste leer sein");
    }
}
