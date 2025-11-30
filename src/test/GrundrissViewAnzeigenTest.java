package test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import business.kunde.Sw;
import gui.grundriss.GrundrissControl;
import gui.grundriss.GrundrissView;
import javafx.application.Platform;

/**
 * JUnit-Test zu "Ausgewählte Sonderwünsche zu Grundriss-Varianten anzeigen"
 * mit Mock-Daten (keine echte DB-Abfrage).
 */
public class GrundrissViewAnzeigenTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        latch.await(5, TimeUnit.SECONDS);
    }

    /**
     * Testet, ob ausgewählte Sonderwünsche (als IDs übergeben)
     * korrekt in den Checkboxen angezeigt werden.
     */
    @Test
    void testAusgewaehlteSwWerdenAngezeigtMitMockdaten() throws Exception {

        CountDownLatch latch = new CountDownLatch(1);
        final boolean[][] result = new boolean[1][];

        Platform.runLater(() -> {
            try {
                GrundrissControl control = new GrundrissControl();

                GrundrissView view = holeViewAusControl(control);

                // Mock-Daten: so tun, als wären diese SW-IDs aus der DB gekommen
                int[] mockAusgewaehlteSw = new int[] {
                        Sw.WAND_KUECHE.id,
                        Sw.TUER_KUECHE.id
                };

                view.updateSwCheckboxen(mockAusgewaehlteSw);
                result[0] = view.holeIsSelectedFuerCheckboxen();

            } catch (Exception e) {
                e.printStackTrace();
                fail("Es ist eine Exception aufgetreten: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);

        boolean[] selected = result[0];
        assertNotNull(selected, "Checkbox-Zustände sollten nicht null sein.");
        assertEquals(6, selected.length, "Es sollten 6 Checkboxen vorhanden sein.");



        assertTrue(selected[0], "Wand Küche sollte ausgewählt sein.");
        assertTrue(selected[1], "Tür Küche sollte ausgewählt sein.");
        assertFalse(selected[2], "Großes Zimmer OG sollte NICHT ausgewählt sein.");
        assertFalse(selected[3], "Treppenraum DG sollte NICHT ausgewählt sein.");
        assertFalse(selected[4], "Vorrichtung Bad DG sollte NICHT ausgewählt sein.");
        assertFalse(selected[5], "Ausführung Bad DG sollte NICHT ausgewählt sein.");
    }

 
    private GrundrissView holeViewAusControl(GrundrissControl control) throws Exception {
        Field f = GrundrissControl.class.getDeclaredField("grundrissView");
        f.setAccessible(true);
        return (GrundrissView) f.get(control);
    }
}
