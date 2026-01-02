package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Vector;

import org.junit.jupiter.api.Test;

import business.kunde.Sw;

public class FliesenSonderwunschValidatorTest {

    /**
     * Vereinfachte Version der Logik aus FliesenControl.pruefeKonstellationSonderwuensche
     * für Testzwecke ohne JavaFX und KundeModel.
     */
    private boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl, boolean hausHatDG, int[][] andereSwMitAnzahl) {
        Vector<Integer> swOhneAnzahl = new Vector<>();
        for (int[] sw : ausgewaehlteSwMitAnzahl)
            if (sw[1] == 1)
                swOhneAnzahl.add(sw[0]);
        for (int[] sw : andereSwMitAnzahl)
            if (sw[1] == 1 && sw[0] == Sw.AUSFUEHRUNG_BAD_DG.id)
                swOhneAnzahl.add(sw[0]);

        String text = "";

        for (int swId : swOhneAnzahl) {
            switch (Sw.findeMitId(swId)) {
                case F_KUECHE_EG_GROSS:
                    if (swOhneAnzahl.contains(Sw.F_KUECHE_EG_OHNE.id))
                        text += "Konflikt F_KUECHE_EG_GROSS + F_KUECHE_EG_OHNE\n";
                    break;
                case F_BAD_OG_GROSS:
                    if (swOhneAnzahl.contains(Sw.F_BAD_OG_OHNE.id))
                        text += "Konflikt F_BAD_OG_GROSS + F_BAD_OG_OHNE\n";
                    break;
                case F_BAD_DG:
                    if (!hausHatDG)
                        text += "F_BAD_DG ohne DG\n";
                    if (!swOhneAnzahl.contains(Sw.AUSFUEHRUNG_BAD_DG.id))
                        text += "F_BAD_DG ohne AUSFUEHRUNG_BAD_DG\n";
                    break;
                case F_BAD_DG_GROSS:
                    if (!swOhneAnzahl.contains(Sw.F_BAD_DG.id))
                        text += "F_BAD_DG_GROSS ohne F_BAD_DG\n";
                    break;
                default:
                    break;
            }
        }

        return text.isEmpty();
    }

    @Test
    public void testValidKonstellation() {
        int[][] sw = {
                {Sw.F_KUECHE_EG_GROSS.id, 1},
                {Sw.F_BAD_OG_OHNE.id, 1}
        };
        int[][] andere = {};
        boolean hausHatDG = false;

        assertTrue(pruefeKonstellationSonderwuensche(sw, hausHatDG, andere));
    }

    @Test
    public void testKonfliktKueche() {
        int[][] sw = {
                {Sw.F_KUECHE_EG_GROSS.id, 1},
                {Sw.F_KUECHE_EG_OHNE.id, 1}
        };
        int[][] andere = {};
        boolean hausHatDG = false;

        assertFalse(pruefeKonstellationSonderwuensche(sw, hausHatDG, andere));
    }

    @Test
    public void testKonfliktBadDGOhneDG() {
        int[][] sw = {
                {Sw.F_BAD_DG.id, 1}
        };
        int[][] andere = {
                {Sw.AUSFUEHRUNG_BAD_DG.id, 1}
        };
        boolean hausHatDG = false;

        assertFalse(pruefeKonstellationSonderwuensche(sw, hausHatDG, andere));
    }

    @Test
    public void testKonfliktBadDGGrossOhneF_BAD_DG() {
        int[][] sw = {
                {Sw.F_BAD_DG_GROSS.id, 1}
        };
        int[][] andere = {
                {Sw.F_BAD_DG.id, 0} // nicht ausgewählt
        };
        boolean hausHatDG = true;

        assertFalse(pruefeKonstellationSonderwuensche(sw, hausHatDG, andere));
    }
}
