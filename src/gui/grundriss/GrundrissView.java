package gui.grundriss;

import javafx.scene.control.*;
import javafx.stage.Stage;
import gui.basis.BasisView;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Grundrissvarianten
 * bereitstellt.
 */
public class GrundrissView extends BasisView {

	// das Control-Objekt des Grundriss-Fensters
	private GrundrissControl grundrissControl;

	// ---Anfang Attribute der grafischen Oberflaeche---
	private Label lblWandKueche = new Label("2.1 Wand zur Abtrennung der Kueche vom Essbereich");
	private TextField txtPreisWandKueche = new TextField();
	private Label lblWandKuecheEuro = new Label("Euro");
	private CheckBox chckBxWandKueche = new CheckBox();

	private Label lblTuerKueche = new Label("2.2 Tuer zur Abtrennung der Kueche");
	private CheckBox chckBxTuerKueche = new CheckBox();

	private Label lblGrossesZimmerOG = new Label("2.3 Grosses Zimmer im OG");
	private CheckBox chckBxGrossesZimmerOG = new CheckBox();

	private Label lblTreppenraumDG = new Label("2.4 Treppenraum bis ins Dachgeschoss (DG)");
	private CheckBox chckBxTreppenraumDG = new CheckBox();

	private Label lblVorrichtungBadOG = new Label("2.5 Vorrichtung Bad im OG");
	private CheckBox chckBxVorrichtungBadOG = new CheckBox();

	private Label lblAusfuehrungBadDG = new Label("2.6 Ausfuehrung Bad im DG");
	private CheckBox chckBxAusfuehrungBadDG = new CheckBox();
	// -------Ende Attribute der grafischen Oberflaeche-------

	/**
	 * erzeugt ein GrundrissView-Objekt, belegt das zugehoerige Control mit dem
	 * vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
	 *
	 * @param grundrissControl GrundrissControl, enthaelt das zugehoerige Control
	 * @param grundrissStage   Stage, enthaelt das Stage-Objekt fuer diese View
	 */
	public GrundrissView(GrundrissControl grundrissControl, Stage grundrissStage) {
		super(grundrissStage);
		this.grundrissControl = grundrissControl;
		grundrissStage.setTitle("Sonderwuensche zu Grundriss-Varianten");

		this.initKomponenten();
		this.leseGrundrissSonderwuensche();
	}

	/* initialisiert die Steuerelemente auf der Maske */
	@Override
	protected void initKomponenten() {
		super.initKomponenten();
		super.getLblSonderwunsch().setText("Grundriss-Varianten");

		// GridPane aus BasisView
		var grid = super.getGridPaneSonderwunsch();

		// Zeile 1: 2.1 Wand / Preis / Euro / Checkbox
		grid.add(lblWandKueche, 0, 1);
		grid.add(txtPreisWandKueche, 1, 1);
		txtPreisWandKueche.setEditable(false);
		grid.add(lblWandKuecheEuro, 2, 1);
		grid.add(chckBxWandKueche, 3, 1);

		// Zeile 2: 2.2 Tuer
		grid.add(lblTuerKueche, 0, 2);
		grid.add(chckBxTuerKueche, 3, 2);

		// Zeile 3: 2.3 Grosses Zimmer OG
		grid.add(lblGrossesZimmerOG, 0, 3);
		grid.add(chckBxGrossesZimmerOG, 3, 3);

		// Zeile 4: 2.4 Treppenraum DG
		grid.add(lblTreppenraumDG, 0, 4);
		grid.add(chckBxTreppenraumDG, 3, 4);

		// Zeile 5: 2.5 Vorrichtung Bad OG
		grid.add(lblVorrichtungBadOG, 0, 5);
		grid.add(chckBxVorrichtungBadOG, 3, 5);

		// Zeile 6: 2.6 Ausfuehrung Bad DG
		grid.add(lblAusfuehrungBadDG, 0, 6);
		grid.add(chckBxAusfuehrungBadDG, 3, 6);
	}

	/**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView() {
		super.oeffneBasisView();
	}

	/* vorhandene Sonderwuensche fuer aktuellen Kunden aus DB laden */
	private void leseGrundrissSonderwuensche() {
		this.grundrissControl.leseGrundrissSonderwuensche();
	}

	/**
	 * Setzt die Checkboxen entsprechend der in der DB gespeicherten IDs (nur
	 * Bereich 200-299 wird betrachtet).
	 */
	protected void updateGrundrissCheckboxen(int[] ausgewaehlteSw) {
		// alles zurücksetzen
		chckBxWandKueche.setSelected(false);
		chckBxTuerKueche.setSelected(false);
		chckBxGrossesZimmerOG.setSelected(false);
		chckBxTreppenraumDG.setSelected(false);
		chckBxVorrichtungBadOG.setSelected(false);
		chckBxAusfuehrungBadDG.setSelected(false);

		if (ausgewaehlteSw == null) {
			return;
		}

		for (int sw : ausgewaehlteSw) {
			if (sw < 200 || sw >= 300)
				continue;

			switch (sw) {
			case 201: // 2.1 Wand Kueche
				chckBxWandKueche.setSelected(true);
				break;
			case 202: // 2.2 Tuer Kueche
				chckBxTuerKueche.setSelected(true);
				break;
			case 203: // 2.3 Grosses Zimmer OG
				chckBxGrossesZimmerOG.setSelected(true);
				break;
			case 204: // 2.4 Treppenraum DG
				chckBxTreppenraumDG.setSelected(true);
				break;
			case 205: // 2.5 Vorrichtung Bad OG
				chckBxVorrichtungBadOG.setSelected(true);
				break;
			case 206: // 2.6 Ausfuehrung Bad DG
				chckBxAusfuehrungBadDG.setSelected(true);
				break;
			default:
				System.out
						.println("Konnte ID " + sw + " keiner Sonderwunsch-Checkbox fuer Grundriss-Varianten zuordnen");
			}
		}
	}
	
	public boolean[] holeGrundrissCheckboxenIsSelected() {
		return new boolean[] {
				chckBxWandKueche.isSelected(),
				chckBxTuerKueche.isSelected(),
				chckBxGrossesZimmerOG.isSelected(),
				chckBxTreppenraumDG.isSelected(),
				chckBxVorrichtungBadOG.isSelected(),
				chckBxAusfuehrungBadDG.isSelected(),
				};
	}

	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
	@Override
	protected void berechneUndZeigePreisSonderwuensche() {
		// Preislogik ist (laut Aufgabenstand) noch ein eigener Task.
		// Hier nur Platzhalter, damit der Button keine Exception wirft.
		System.out.println("Preisberechnung fuer Grundriss-Sonderwuensche ist noch nicht implementiert.");
	}

	/* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
	@Override
	protected void speichereSonderwuensche() {
		int[] grundrissSw = ermittleAusgewaehlteGrundrissSonderwuensche();
		grundrissControl.speichereSonderwuensche(grundrissSw);
	}

	/**
	 * Liest alle gesetzten Checkboxen aus und liefert die entsprechenden
	 * Sonderwunsch-IDs (201-206) als int-Array zurueck.
	 */
	private int[] ermittleAusgewaehlteGrundrissSonderwuensche() {
		List<Integer> ids = new ArrayList<>();

		if (chckBxWandKueche.isSelected()) {
			ids.add(201);
		}
		if (chckBxTuerKueche.isSelected()) {
			ids.add(202);
		}
		if (chckBxGrossesZimmerOG.isSelected()) {
			ids.add(203);
		}
		if (chckBxTreppenraumDG.isSelected()) {
			ids.add(204);
		}
		if (chckBxVorrichtungBadOG.isSelected()) {
			ids.add(205);
		}
		if (chckBxAusfuehrungBadDG.isSelected()) {
			ids.add(206);
		}

		int[] result = new int[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			result[i] = ids.get(i);
		}
		return result;
	}
	
	//Yamam

}
