package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.kunde.KundeModel;
import gui.heizung.HeizungControl;

/**
 * JUnit-Tests für pruefeKonstellationHeizkoerper().
 * Gehört zu Task [3]: Heizkörper Sonderwünsche Preis berechnen / Konstellation prüfen.
 */
public class HeizungKonstellationTest_old {

    private HeizungControl heizungControl;

    // IDs wie in HeizungControl
    private static final int SW_STD_HEIZKOERPER   = 13;
    private static final int SW_GLATT_HEIZKOERPER = 14;
    private static final int SW_FBH_OHNE_DG       = 16;
    private static final int SW_FBH_MIT_DG        = 17;

    @BeforeEach
    public void setUp() {
        // Wir brauchen ein KundeModel-Objekt, weil der Konstruktor es verlangt
        KundeModel kundeModel = KundeModel.getInstance();
        heizungControl = new HeizungControl(kundeModel);
    }

    @Test
    public void testGueltigeKonstellation_keineFBHKollision() {
        int[] auswahl = {
            SW_STD_HEIZKOERPER,
            SW_GLATT_HEIZKOERPER
        };

        boolean ok = heizungControl.pruefeKonstellationHeizkoerper(auswahl);

        assertTrue(ok, "Standard + glatt sollte eine gültige Konstellation sein");
    }

    @Test
    public void testUngueltigeKonstellation_FbhOhneUndMitDG() {
        int[] auswahl = {
            SW_FBH_OHNE_DG,
            SW_FBH_MIT_DG
        };

        boolean ok = heizungControl.pruefeKonstellationHeizkoerper(auswahl);

        assertFalse(ok,
            "Fußbodenheizung ohne DG und mit DG gleichzeitig sollte als ungültig erkannt werden");
    }

    @Test
    public void testLeereAuswahlIstGueltig() {
        int[] auswahl = {};

        boolean ok = heizungControl.pruefeKonstellationHeizkoerper(auswahl);

        assertTrue(ok, "Leere Auswahl sollte als gültig gelten");
    }

    @Test
    public void testNullEingabeIstGueltig() {
        boolean ok = heizungControl.pruefeKonstellationHeizkoerper(null);

        assertTrue(ok, "Null-Eingabe sollte kein Konflikt sein");
    }
}
