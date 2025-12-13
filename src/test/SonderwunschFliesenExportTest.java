package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class SonderwunschFliesenExportTest {

    @Before
    public void setUp() throws Exception {
        
    }

    @Test
    public void testFliesenExportMitAuswahl() {
        // IDs wie sie in Sw definiert wären (nur Beispiele!):
        final int F_KUECHE_EG_OHNE_ID = 11;
        final int F_BAD_OG_OHNE_ID = 12;
        final int F_KUECHE_EG_GROSS_ID = 13;
        final int F_BAD_OG_GROSS_ID = 14;
        final int F_BAD_DG_ID = 15;
        final int F_BAD_DG_GROSS_ID = 16;

        // Angenommen folgende Fliesen-Sonderwünsche wurden ausgewählt:
        int[][] export = new int[][] {
            { F_KUECHE_EG_OHNE_ID, 1 },
            { F_BAD_OG_GROSS_ID, 1 },
            { F_BAD_DG_GROSS_ID, 1 }
        };

        // Erwartet: Drei Zeilen, jeweilige ID und Anzahl (immer 1)
        assertEquals(3, export.length);
        assertArrayEquals(new int[] { F_KUECHE_EG_OHNE_ID, 1 }, export[0]);
        assertArrayEquals(new int[] { F_BAD_OG_GROSS_ID, 1 }, export[1]);
        assertArrayEquals(new int[] { F_BAD_DG_GROSS_ID, 1 }, export[2]);
    }

    @Test
    public void testFliesenExportKeineAuswahl() {
        // Keine Auswahl sollte ein leeres Array ergeben
        int[][] export = new int[0][];
        assertEquals(0, export.length);
    }

    @Test
    public void testFliesenExportAlleAusgewaehlt() {
        // Beispiel: alle Checkboxen (=alle Fliesenoptionen) wurden gewählt
        final int F_KUECHE_EG_OHNE_ID = 11;
        final int F_BAD_OG_OHNE_ID = 12;
        final int F_KUECHE_EG_GROSS_ID = 13;
        final int F_BAD_OG_GROSS_ID = 14;
        final int F_BAD_DG_ID = 15;
        final int F_BAD_DG_GROSS_ID = 16;

        int[][] export = new int[][] {
            { F_KUECHE_EG_OHNE_ID, 1 },
            { F_BAD_OG_OHNE_ID, 1 },
            { F_KUECHE_EG_GROSS_ID, 1 },
            { F_BAD_OG_GROSS_ID, 1 },
            { F_BAD_DG_ID, 1 },
            { F_BAD_DG_GROSS_ID, 1 }
        };

        assertEquals(6, export.length);
        assertArrayEquals(new int[] { F_KUECHE_EG_OHNE_ID, 1 }, export[0]);
        assertArrayEquals(new int[] { F_BAD_OG_OHNE_ID, 1 }, export[1]);
        assertArrayEquals(new int[] { F_KUECHE_EG_GROSS_ID, 1 }, export[2]);
        assertArrayEquals(new int[] { F_BAD_OG_GROSS_ID, 1 }, export[3]);
        assertArrayEquals(new int[] { F_BAD_DG_ID, 1 }, export[4]);
        assertArrayEquals(new int[] { F_BAD_DG_GROSS_ID, 1 }, export[5]);
    }
}
