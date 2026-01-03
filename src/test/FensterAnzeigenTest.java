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
import business.kunde.Sw;
import gui.fenster.FensterControl;
import gui.fenster.FensterView;
import javafx.application.Platform;

class FensterAnzeigenTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        assertTrue(latch.await(10, TimeUnit.SECONDS),
                "JavaFX konnte nicht initialisiert werden");
    }

    @Test
    void ausgewaehlteFensterSonderwuenscheWerdenAngezeigt() throws Exception {

        //  Mockdaten (Sonderwünsche)
        int hausnummer = 20;

        // Alle 9 Fenster-/Außentür-SWs über Enum-IDs
        int[] ausgewaehlteFensterSw = {
            Sw.STUEREN_TERRASSE.id,      // 301
            Sw.STUEREN_DACHTERRASSE.id,  // 302
            Sw.EBS_HAUSTUER.id,          // 303
            Sw.VEAR_EG.id,               // 304
            Sw.VEAR_OG.id,               // 305
            Sw.VEAR_DG.id,               // 306
            Sw.ER_EG.id,                 // 307
            Sw.ER_OG.id,                 // 308
            Sw.ER_DG.id                  // 309
        };

        KundeModel kundeModel = KundeModel.getInstance();

        // aktueller Kunde direkt ins private Feld 'kunde' schreien
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
        }

        // swDao im KundeModel ersetzen
        Field swDaoField = KundeModel.class.getDeclaredField("swDao");
        swDaoField.setAccessible(true);
        swDaoField.set(kundeModel, new FakeSwDao());

        // ausgewaehlteSw zurücksetzen, damit wirklich aus dem DAO gelesen wird
        Field ausgewaehlteSwField = KundeModel.class.getDeclaredField("ausgewaehlteSw");
        ausgewaehlteSwField.setAccessible(true);
        ausgewaehlteSwField.set(kundeModel, null);

        // FensterControl + FensterView auf FX-Thread erzeugen
        CountDownLatch latch = new CountDownLatch(1);
        final boolean[][] selectedHolder = new boolean[1][];

        Platform.runLater(() -> {
            try {
                FensterControl control = new FensterControl();

                control.leseFensterSonderwuensche();

                Field viewField = FensterControl.class.getDeclaredField("fensterView");
                viewField.setAccessible(true);
                FensterView view = (FensterView) viewField.get(control);

                selectedHolder[0] = view.holeIsSelectedFuerCheckboxen();

            } catch (Exception e) {
                e.printStackTrace();
                fail(e);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS),
                "FX-Thread ist nicht rechtzeitig fertig geworden");

        //  Prüfen der Checkboxen
        boolean[] selected = selectedHolder[0];
        assertNotNull(selected, "Selektions-Array darf nicht null sein");
        assertEquals(9, selected.length,
                "Es sollten 9 Checkboxen in holeIsSelectedFuerCheckboxen() sein");

        // Wir haben alle 9 SW ausgewählt, alle 9 Checkboxen müssen true sein
        for (int i = 0; i < selected.length; i++) {
            assertTrue(selected[i], "Checkbox an Index " + i + " sollte selektiert sein");
        }
    }
}
