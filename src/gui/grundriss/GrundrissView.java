package gui.grundriss;

import java.util.ArrayList;
import java.util.List;

import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Grundrissvarianten
 * bereitstellt.
 */
public class GrundrissView extends BasisView {

	// das Control-Objekt des Grundriss-Fensters
	private GrundrissControl grundrissControl;

	// ---Anfang Attribute der grafischen Oberflaeche---
	private Label lblWandKueche = new Label("Wand zur Abtrennung der Kueche von dem Essbereich");
	private TextField txtPreisWandKueche = new TextField();
	private Label lblWandKuecheEuro = new Label("Euro");
	private CheckBox chckBxWandKueche = new CheckBox();

	private CheckBox chckBxTuerKueche = new CheckBox();
	private CheckBox chckBxGrossesZimmerOG = new CheckBox();
	private CheckBox chckBxTreppenraumDG = new CheckBox();
	private CheckBox chckBxVorrichtungBadOG = new CheckBox();
	private CheckBox chckBxAusfuehrungBadDG = new CheckBox();

	// Neues Feld für den Gesamtpreis
	private Label lblGesamtpreis = new Label("Gesamtpreis:");
	private TextField txtGesamtpreis = new TextField();
	private Label lblGesamtpreisEuro = new Label("Euro");
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
		grundrissStage.setTitle("Sonderw\u00fcnsche zu Grundriss-Varianten");

		this.initKomponenten();
		this.leseGrundrissSonderwuensche();
	}

	/* initialisiert die Steuerelemente auf der Maske */
	protected void initKomponenten() {
		super.initKomponenten();
		super.getLblSonderwunsch().setText("Grundriss-Varianten");
		super.getGridPaneSonderwunsch().add(lblWandKueche, 0, 1);
		super.getGridPaneSonderwunsch().add(getTxtPreisWandKueche(), 1, 1);
		getTxtPreisWandKueche().setEditable(false);
		super.getGridPaneSonderwunsch().add(lblWandKuecheEuro, 2, 1);
		super.getGridPaneSonderwunsch().add(getChckBxWandKueche(), 3, 1);

		// Gesamtpreis unten hinzufügen
		super.getGridPaneSonderwunsch().add(lblGesamtpreis, 0, 8);
		super.getGridPaneSonderwunsch().add(getTxtGesamtpreis(), 1, 8);
		getTxtGesamtpreis().setEditable(false);
		super.getGridPaneSonderwunsch().add(lblGesamtpreisEuro, 2, 8);
	}

	/**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView() {
		super.oeffneBasisView();
	}

	private void leseGrundrissSonderwuensche() {
		this.grundrissControl.leseGrundrissSonderwuensche();
	}

	protected void updateGrundrissCheckboxen(int[] ausgewaehlteSw) {
		// Setze alle auf false
		getChckBxWandKueche().setSelected(false);
		getChckBxTuerKueche().setSelected(false);
		getChckBxGrossesZimmerOG().setSelected(false);
		getChckBxTreppenraumDG().setSelected(false);
		getChckBxVorrichtungBadOG().setSelected(false);
		getChckBxAusfuehrungBadDG().setSelected(false);

		// Setze diejenigen auf true, die in ausgewaehlteSw vorkommen
		if (ausgewaehlteSw == null) {
			return;
		}
		for (int sw : ausgewaehlteSw) {
			if (sw < 200 || sw >= 300)
				continue;
			switch (sw) {
			case 201:
				getChckBxWandKueche().setSelected(true);
				break;
			case 202:
				getChckBxTuerKueche().setSelected(true);
				break;
			case 203:
				getChckBxGrossesZimmerOG().setSelected(true);
				break;
			case 204:
				getChckBxTreppenraumDG().setSelected(true);
				break;
			case 205:
				getChckBxVorrichtungBadOG().setSelected(true);
				break;
			case 206:
				getChckBxAusfuehrungBadDG().setSelected(true);
				break;
			default:
				System.out
						.println("Konnte ID " + sw + " keiner Sonderwunsch-Checkbox fuer Grundriss-Varianten zuordnen");
			}
		}
	}

	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
	@Override
	public void berechneUndZeigePreisSonderwuensche() {
		double gesamtpreis = 0.0;

		// Beispielpreise:
		double preisWandKueche = 1500.0;
		double preisTuerKueche = 600.0;
		double preisGrossesZimmerOG = 2000.0;
		double preisTreppenraumDG = 1200.0;
		double preisVorrichtungBadOG = 1700.0;
		double preisAusfuehrungBadDG = 2100.0;

		if (getChckBxWandKueche().isSelected()) {
			gesamtpreis += preisWandKueche;
		}
		if (getChckBxTuerKueche().isSelected()) {
			gesamtpreis += preisTuerKueche;
		}
		if (getChckBxGrossesZimmerOG().isSelected()) {
			gesamtpreis += preisGrossesZimmerOG;
		}
		if (getChckBxTreppenraumDG().isSelected()) {
			gesamtpreis += preisTreppenraumDG;
		}
		if (getChckBxVorrichtungBadOG().isSelected()) {
			gesamtpreis += preisVorrichtungBadOG;
		}
		if (getChckBxAusfuehrungBadDG().isSelected()) {
			gesamtpreis += preisAusfuehrungBadDG;
		}

		getTxtGesamtpreis().setText(String.format("%.2f", gesamtpreis));
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

		if (getChckBxWandKueche().isSelected()) {
			ids.add(201);
		}
		if (getChckBxTuerKueche().isSelected()) {
			ids.add(202);
		}
		if (getChckBxGrossesZimmerOG().isSelected()) {
			ids.add(203);
		}
		if (getChckBxTreppenraumDG().isSelected()) {
			ids.add(204);
		}
		if (getChckBxVorrichtungBadOG().isSelected()) {
			ids.add(205);
		}
		if (getChckBxAusfuehrungBadDG().isSelected()) {
			ids.add(206);
		}

		int[] result = new int[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			result[i] = ids.get(i);
		}
		return result;
	}

	public CheckBox getChckBxWandKueche() {
		return chckBxWandKueche;
	}

	public void setChckBxWandKueche(CheckBox chckBxWandKueche) {
		this.chckBxWandKueche = chckBxWandKueche;
	}

	public CheckBox getChckBxTuerKueche() {
		return chckBxTuerKueche;
	}

	public void setChckBxTuerKueche(CheckBox chckBxTuerKueche) {
		this.chckBxTuerKueche = chckBxTuerKueche;
	}

	public CheckBox getChckBxGrossesZimmerOG() {
		return chckBxGrossesZimmerOG;
	}

	public void setChckBxGrossesZimmerOG(CheckBox chckBxGrossesZimmerOG) {
		this.chckBxGrossesZimmerOG = chckBxGrossesZimmerOG;
	}

	public CheckBox getChckBxTreppenraumDG() {
		return chckBxTreppenraumDG;
	}

	public void setChckBxTreppenraumDG(CheckBox chckBxTreppenraumDG) {
		this.chckBxTreppenraumDG = chckBxTreppenraumDG;
	}

	public CheckBox getChckBxVorrichtungBadOG() {
		return chckBxVorrichtungBadOG;
	}

	public void setChckBxVorrichtungBadOG(CheckBox chckBxVorrichtungBadOG) {
		this.chckBxVorrichtungBadOG = chckBxVorrichtungBadOG;
	}

	public CheckBox getChckBxAusfuehrungBadDG() {
		return chckBxAusfuehrungBadDG;
	}

	public void setChckBxAusfuehrungBadDG(CheckBox chckBxAusfuehrungBadDG) {
		this.chckBxAusfuehrungBadDG = chckBxAusfuehrungBadDG;
	}

	public TextField getTxtPreisWandKueche() {
		return txtPreisWandKueche;
	}

	public void setTxtPreisWandKueche(TextField txtPreisWandKueche) {
		this.txtPreisWandKueche = txtPreisWandKueche;
	}

	public TextField getTxtGesamtpreis() {
		return txtGesamtpreis;
	}

	public void setTxtGesamtpreis(TextField txtGesamtpreis) {
		this.txtGesamtpreis = txtGesamtpreis;
	}

	// Yamam

}
