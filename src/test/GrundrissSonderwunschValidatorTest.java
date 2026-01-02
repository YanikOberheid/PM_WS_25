package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Vector;

import org.junit.jupiter.api.Test;

import business.kunde.Sw;
import gui.grundriss.GrundrissControl;

public class GrundrissSonderwunschValidatorTest {

    private boolean pruefeKonstellation(int[][] ausgewaehlteSwMitAnzahl, boolean hausHatDG) {
        Vector<Integer> swOhneAnzahl = new Vector<>();
        for (int[] sw : ausgewaehlteSwMitAnzahl)
            if (sw[1] == 1)
                swOhneAnzahl.add(sw[0]);

        String text = "";
        for (int swId : swOhneAnzahl) {
            switch (Sw.findeMitId(swId)) {
                case TUER_KUECHE: // 2.2 nur wenn 2.1
                    if (!swOhneAnzahl.contains(Sw.WAND_KUECHE.id))
                        text += "Tür Küche kann nicht ohne Wand Küche ausgewählt werden\n";
                    break;
                case TREPPENRAUM_DG: // 2.4 nur wenn DG
                    if (!hausHatDG)
                        text += "Treppenraum DG kann nicht ohne Dachgeschoss ausgewählt werden\n";
                    break;
                case VORRICHTUNG_BAD_DG: // 2.5 nur wenn DG
                    if (!hausHatDG)
                        text += "Vorrichtung Bad DG kann nicht ohne Dachgeschoss ausgewählt werden\n";
                    break;
                case AUSFUEHRUNG_BAD_DG: // 2.6 nur wenn 2.5
                    if (!swOhneAnzahl.contains(Sw.VORRICHTUNG_BAD_DG.id))
                        text += "Ausführung Bad DG kann nicht ohne Vorrichtung Bad DG ausgewählt werden\n";
                    break;
                default:
                    break;
            }
        }
        return text.isEmpty();
    }

    @Test
    public void testValidKonstellation() {
        int[][] swMitAnzahl = {
            { Sw.WAND_KUECHE.id, 1 },
            { Sw.TUER_KUECHE.id, 1 }
        };
        assertTrue(pruefeKonstellation(swMitAnzahl, false));
    }

    @Test
    public void testInvalidKonstellation_TuerOhneWand() {
        int[][] swMitAnzahl = {
            { Sw.TUER_KUECHE.id, 1 }
        };
        assertFalse(pruefeKonstellation(swMitAnzahl, false));
    }

    @Test
    public void testInvalidKonstellation_DGFehlt() {
        int[][] swMitAnzahl = {
            { Sw.TREPPENRAUM_DG.id, 1 }
        };
        assertFalse(pruefeKonstellation(swMitAnzahl, false));
    }

    @Test
    public void testValidDG() {
        int[][] swMitAnzahl = {
            { Sw.TREPPENRAUM_DG.id, 1 }
        };
        assertTrue(pruefeKonstellation(swMitAnzahl, true));
    }
}
