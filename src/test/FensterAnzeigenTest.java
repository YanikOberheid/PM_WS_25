package test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import business.kunde.Kunde;
import business.kunde.KundeModel;
import business.kunde.SonderwuenscheDAOImplementation;
import gui.fenster.FensterControl;
import gui.fenster.FensterView;
import javafx.application.Platform;

/**
 * Vorgehen:
 *  - KundeModel mit Test-Kunde + Fake-DAO vorbereiten (Mockdaten, keine echte DB)
 *  - FensterControl erzeugen → FensterView erstellt und liest Sonderwünsche
 *  - Über holeIsSelectedFuerCheckboxen() prüfen, ob die richtigen Checkboxen selektiert sind
 */
class FensterAnzeigenTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            latch.countDown();
        });

        if (!latch.await(10, TimeUnit.SECONDS)) {
            fail("JavaFX konnte nicht initialisiert werden");
        }
    }

    @Test
    void ausgewaehlteFensterSonderwuenscheWerdenAngezeigt() throws Exception {

        // 1) Mockdaten (Sonderwünsche)
        int hausnummer = 20;

        // Alle 9 Fenster-/Außentür-Sonderwünsche auswählen
        int[] ausgewaehlteFensterSw = {
            301, 302, 303, 304, 305, 306, 307, 308, 309
        };

        // 2) KundeModel per Reflection vorbereiten (Mock)
        KundeModel kundeModel = KundeModel.getInstance();

        // Test-Kunde setzen (ohne echte DB-Operation)
        Field kundeField = KundeModel.class.getDeclaredField("kunde");
        kundeField.setAccessible(true);
        kundeField.set(kundeModel, new Kunde(
                hausnummer,
                "Max",
                "Mustermann",
                "0123456789",
                "max@example.com"
        ));

        // Fake DAO, der immer unsere Mock-Sonderwünsche zurückgibt
        class FakeSwDao extends SonderwuenscheDAOImplementation {
            @Override
            public int[] get(int hausnr) {
                assertEquals(hausnummer, hausnr,
                        "DAO sollte mit der Test-Hausnummer aufgerufen werden");
                return ausgewaehlteFensterSw.clone();
            }

            public int[] get(int hausnr, int kategorieId) {
                assertEquals(hausnummer, hausnr,
                        "DAO sollte mit der Test-Hausnummer aufgerufen werden");
                return ausgewaehlteFensterSw.clone();
            }
        }

        // Fake DAO in KundeModel injizieren
        Field swDaoField = KundeModel.class.getDeclaredField("swDao");
        swDaoField.setAccessible(true);
        swDaoField.set(kundeModel, new FakeSwDao());

        // Lokale Sonderwunschliste zurücksetzen, damit neu geladen wird
        Field ausgewaehlteSwField = KundeModel.class.getDeclaredField("ausgewaehlteSw");
        ausgewaehlteSwField.setAccessible(true);
        ausgewaehlteSwField.set(kundeModel, null);

        // 3) FensterControl + FensterView starten
        CountDownLatch latch = new CountDownLatch(1);
        final boolean[][] selectedHolder = new boolean[1][];

        Platform.runLater(() -> {
            try {
                FensterControl control = new FensterControl();

                Field viewField = FensterControl.class.getDeclaredField("fensterView");
                viewField.setAccessible(true);
                FensterView view = (FensterView) viewField.get(control);

                boolean[] selected = view.holeIsSelectedFuerCheckboxen();
                selectedHolder[0] = selected;

            } catch (Exception e) {
                fail(e);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS),
                "FX-Thread ist nicht rechtzeitig fertig geworden");

        // 4) Prüfen der Checkboxen
        boolean[] selected = selectedHolder[0];
        assertNotNull(selected, "Selektions-Array darf nicht null sein");
        assertEquals(9, selected.length,
                "Es sollten 9 Checkboxen in holeIsSelectedFuerCheckboxen() sein");

        // Wir haben alle 9 SW ausgewählt,alle Checkboxen sollen true sein
        for (int i = 0; i < selected.length; i++) {
            assertTrue(selected[i], "Checkbox an Index " + i + " sollte selektiert sein");
        }
    }
}
