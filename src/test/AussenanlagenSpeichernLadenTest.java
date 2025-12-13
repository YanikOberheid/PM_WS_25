package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import business.kunde.SonderwuenscheDAOImplementation;
import business.kunde.SwKategorie;
import business.kunde.Sw;

class AussenanlagenSpeichernLadenTest {

    private static final int HAUSNUMMER = 1;
    private static final int KATEGORIE_AUSSENANLAGEN = SwKategorie.AUSSENANLAGEN.id;


    // Außenanlagen haben fachlich keine Menge -> wir speichern Default 1
    private static final int[][] TEST_AUSSENANLAGEN_SW = {
        { Sw.ABSTELL_TERRASSE_EG.id, 1 },
        { Sw.VEA_MARKISE_DG.id,      1 },
        { Sw.ST_GARAGENTOR.id,       1 }
    };

    private final SonderwuenscheDAOImplementation dao = new SonderwuenscheDAOImplementation();

    @AfterEach
    void cleanup() throws Exception {
        dao.update(HAUSNUMMER, new int[0][0]);
    }

    @Test
    void testSpeichernUndLadenVonAussenanlagenSonderwuenschenMitAnzahl() throws Exception {
        //  leer
        dao.update(HAUSNUMMER, new int[0][0]);

        // Speichern
        int[][] zuSpeichern = deepCopy(TEST_AUSSENANLAGEN_SW);
        dao.update(HAUSNUMMER, zuSpeichern);

        // Laden (nur Kategorie Außenanlagen)
        int[][] geladen = dao.getMitAnzahl(HAUSNUMMER, KATEGORIE_AUSSENANLAGEN);


        sortBySwId(zuSpeichern);
        sortBySwId(geladen);

        assertArrayEquals(
            zuSpeichern,
            geladen,
            "Gespeicherte und geladene Außenanlagen-Sonderwünsche (inkl. Anzahl) müssen übereinstimmen."
        );
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
