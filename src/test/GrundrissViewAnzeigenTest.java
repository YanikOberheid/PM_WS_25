
package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import gui.grundriss.GrundrissControl;

import gui.grundriss.GrundrissView;

import javafx.application.Platform;

import javafx.embed.swing.JFXPanel;

/**
 * 
 * 
 * JUnit-Test zu "Ausgewählte Sonderwünsche zu Grundriss-Varianten anzeigen"
 * 
 * 
 * mit Mock-Daten.
 * 
 * 
 */

public class GrundrissViewAnzeigenTest {

	@BeforeAll

	static void initJavaFx() {

		new JFXPanel();

	}

	// Prüfen ob ein (gespeicherter) Sonderwunsch

	// beim Öffnen der Maske korrekt angezeigt wird (mit Mock-Daten)

	@Test

	public void testSonderwunschAnzeigeMitMockdaten() throws Exception {

		CountDownLatch latch = new CountDownLatch(1);

		final boolean[] sonderwunschAngezeigt = new boolean[1];

		Platform.runLater(() -> {

			try {

				// Testobjekte

				GrundrissControl control = new GrundrissControl();

				GrundrissView view = control.getGrundrissView();

				// Mock: so tun, als wäre "Wand Küche" schon gespeichert

				view.getChckBxWandKueche().setSelected(true);

				// Aktion

				control.oeffneGrundrissView();

				// Ergebnis zwischenspeichern

				sonderwunschAngezeigt[0] = view.getChckBxWandKueche().isSelected();

			} finally {

				latch.countDown();

			}

		});

		boolean fertig = latch.await(5, TimeUnit.SECONDS);

		assertTrue(fertig, "JavaFX-Thread hat zu lange gebraucht.");

		// Prüfen
		assertTrue(sonderwunschAngezeigt[0], "Checkbox 'Wand Küche' sollte gesetzt sein.");

	}

}