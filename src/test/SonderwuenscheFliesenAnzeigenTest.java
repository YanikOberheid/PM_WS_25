package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.fliesen.FliesenView;
import javafx.stage.Stage;


/*
 * Hinweis: Fürs Holen der Ausgewhälten Sonderwünsche zu Fliesen ist die Klasse
 * SonderwuenscheDAOImplementation zuständig, welche hier NICHT getestet wird.
 * FliesenControl ruft zum Anzeigen der ausgewählten Optionen
 * SonderwuenscheDAOImplementation.gibAusgewaehlteSwAusDb(...) für die Daten auf
 * und aktualisiert Checkboxen mit FliesenView.updateFliesenCheckboxen(...).
 * 
 * Nur FliesenView.updateFliesenCheckboxen(...) wird hier getestet.
 *  
 */

class SonderwuenscheFliesenAnzeigenTest {
	

	private Stage stage;
	private FliesenView fliesenView;

	@BeforeEach
	void setUp() throws Exception {
		stage = new Stage();
		fliesenView = new FliesenView(null, stage); // Control nicht benötigt
	}

	@AfterEach
	void tearDown() throws Exception {
		stage = null;
		fliesenView = null;
	}

	@Test
	void anfangsAlleFalse() {
		// Keine Checkbox sollte ausgewählt sein
		for (boolean b: fliesenView.holeFliesenCheckboxenIsSelected())
			assertFalse(b);
	}
	
	@Test
	void zweiNachAuswahlTrue() {
		// 701 und 702 sind ausgewählt
		fliesenView.updateFliesenCheckboxen(new int[] {701, 702});
		assertTrue(fliesenView.holeFliesenCheckboxenIsSelected()[0]);
		assertTrue(fliesenView.holeFliesenCheckboxenIsSelected()[1]);
	}
	
	@Test
	void nachNullAlleFalse() {
		// 701 und 702 sind ausgewählt (aus Test zweiNachAuswahlTrue)
		fliesenView.updateFliesenCheckboxen(new int[] {701, 702});
		
		// null sollte Auwahl aufheben
		fliesenView.updateFliesenCheckboxen(null);
		for (boolean b: fliesenView.holeFliesenCheckboxenIsSelected())
			assertFalse(b);
	}
	
	@Test
	void alleTrue() {
		// Alle Checkboxen auswählen
		fliesenView.updateFliesenCheckboxen(new int[] {701, 702, 703, 704, 705, 706});
		for (boolean b: fliesenView.holeFliesenCheckboxenIsSelected())
			assertTrue(b);
	}
	
	@Test
	void nurNochZweiTrue() {
		// Alle Checkboxen auswählen (aus Test alleTrue)
		fliesenView.updateFliesenCheckboxen(new int[] {701, 702, 703, 704, 705, 706});
		for (boolean b: fliesenView.holeFliesenCheckboxenIsSelected())
			assertTrue(b);
		
		// Nur 703 und 705 auswählen
		fliesenView.updateFliesenCheckboxen(new int[] {703, 705});
		boolean[] b = fliesenView.holeFliesenCheckboxenIsSelected();
		assertFalse(b[0]);
		assertFalse(b[1]);
		assertTrue(b[2]); // 703 an Index 2
		assertFalse(b[3]);
		assertTrue(b[4]); // 705 an Index 4
		assertFalse(b[5]);
	}

}
