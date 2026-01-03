package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import business.kunde.SonderwuenscheDAOImplementation;
import business.kunde.Sw;
import business.kunde.SwKategorie;

/**
 * JUnit-Test zum Speichern und Laden von Sonderwünschen zu Heizkörpern (inkl Anzahl)
 * über den SonderwuenscheDAO / DB.
 * Aus: [3] Heizkörper Sonderwünsche speichern, [3] Heizkörper Sonderwünsche laden
 */
public class HeizkoerperLadenSpeichernTest {

    private static final int HAUSNUMMER = 1;
    private static final int KATEGORIE_HEIZKOERPER = SwKategorie.HEIZKOERPER.id;

    private static final int[][] TEST_HEIZKOERPER_SW = {
        { Sw.STD_HEIZKOERPER.id, 2 },
        { Sw.HANDTUCH.id,        1 }
    };

    private final SonderwuenscheDAOImplementation dao = new SonderwuenscheDAOImplementation();

    @AfterEach
    void cleanup() throws Exception {
        dao.update(HAUSNUMMER, new int[0][0]);
    }

    @Test
    void testSpeichernUndLadenHeizkoerperSonderwuenscheMitAnzahl() throws Exception {
        // leer
        dao.update(HAUSNUMMER, new int[0][0]);

        //Speichern
        int[][] erwartet = deepCopy(TEST_HEIZKOERPER_SW);
        dao.update(HAUSNUMMER, erwartet);

        //Laden
        int[][] geladen = dao.getMitAnzahl(HAUSNUMMER, KATEGORIE_HEIZKOERPER);

        sortBySwId(erwartet);
        sortBySwId(geladen);

        assertArrayEquals(erwartet, geladen,
            "Die gespeicherten Heizkörper-Sonderwünsche (inkl Anzahl) sollten nach dem Laden identisch sein.");
    }

    private static void sortBySwId(int[][] arr) {
        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));
    }

    private static int[][] deepCopy(int[][] src) {
        int[][] copy = new int[src.length][2];
        for (int i = 0; i < src.length; i++) {
            copy[i][0] = src[i][0];
            copy[i][1] = src[i][1];
        }
        return copy;
    }
}
