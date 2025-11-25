package gui.fliesen;

import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FliesenView extends BasisView {

	private final FliesenControl fliesenControl;

	private static final int SW_FLIESEN_IM_BAD = 9; // idSonderwunsch=9, Kategorie=70

	private static final int SW_7_5 = SW_FLIESEN_IM_BAD;

	private static final int SW_7_1 = -1;
	private static final int SW_7_2 = -1;
	private static final int SW_7_3 = -1;
	private static final int SW_7_4 = -1;
	private static final int SW_7_6 = -1;

	// GUI-Elemente
	private final Label lbl71 = new Label("7.1 Keine Fliesen im Küchenbereich des EG");
	private final CheckBox cb71 = new CheckBox();

	private final Label lbl72 = new Label("7.2 Keine Fliesen im Bad des OG");
	private final CheckBox cb72 = new CheckBox();

	private final Label lbl73 = new Label("7.3 Mehrpreis bei großformatige Fliesen im Küchenbereich des EG");
	private final CheckBox cb73 = new CheckBox();

	private final Label lbl74 = new Label("7.4 Mehrpreis bei großformatige Fliesen im Bad des OG");
	private final CheckBox cb74 = new CheckBox();

	private final Label lbl75 = new Label("7.5 Fliesen im Bad des DG");
	private final CheckBox cb75 = new CheckBox();

	private final Label lbl76 = new Label("7.6 Mehrpreis bei großformatige Fliesen im Bad des DG");
	private final CheckBox cb76 = new CheckBox();

	public FliesenView(FliesenControl fliesenControl, Stage stage) {
		super(stage);
		this.fliesenControl = fliesenControl;
		stage.setTitle("Sonderwünsche zu Fliesen");
		initKomponenten();
	}

	private void hideIfMissing(Label l, CheckBox cb, int id) {
		boolean missing = id < 0;
		l.setManaged(!missing);
		l.setVisible(!missing);
		cb.setManaged(!missing);
		cb.setVisible(!missing);
	}

	// Basis-Layout aufbauen

	@Override
	protected void initKomponenten() {
		super.initKomponenten();
		super.getLblSonderwunsch().setText("Fliesen-Varianten");

		int row = 1;
		getGridPaneSonderwunsch().add(lbl71, 0, row);
		getGridPaneSonderwunsch().add(cb71, 3, row++);
		getGridPaneSonderwunsch().add(lbl72, 0, row);
		getGridPaneSonderwunsch().add(cb72, 3, row++);
		getGridPaneSonderwunsch().add(lbl73, 0, row);
		getGridPaneSonderwunsch().add(cb73, 3, row++);
		getGridPaneSonderwunsch().add(lbl74, 0, row);
		getGridPaneSonderwunsch().add(cb74, 3, row++);
		getGridPaneSonderwunsch().add(lbl75, 0, row);
		getGridPaneSonderwunsch().add(cb75, 3, row++);
		getGridPaneSonderwunsch().add(lbl76, 0, row);
		getGridPaneSonderwunsch().add(cb76, 3, row++);

		// Ausblenden, solange keine DB-IDs existieren
		hideIfMissing(lbl71, cb71, SW_7_1);
		hideIfMissing(lbl72, cb72, SW_7_2);
		hideIfMissing(lbl73, cb73, SW_7_3);
		hideIfMissing(lbl74, cb74, SW_7_4);
		hideIfMissing(lbl76, cb76, SW_7_6);
	}

	/** Öffnet das Fenster (Hilfsmethode, optional). */
	public void oeffneFliesenView() {
		super.oeffneBasisView();
	}

	/** Setzt die Haken in der GUI basierend auf den geladenen Daten aus der DB. */
	public void updateFliesenCheckboxen(int[] ausgewaehlteSw) {
		// Reset
		cb71.setSelected(false);
		cb72.setSelected(false);
		cb73.setSelected(false);
		cb74.setSelected(false);
		cb75.setSelected(false);
		cb76.setSelected(false);

		if (ausgewaehlteSw == null)
			return;

		for (int id : ausgewaehlteSw) {
			if (id == SW_7_1)
				cb71.setSelected(true);
			else if (id == SW_7_2)
				cb72.setSelected(true);
			else if (id == SW_7_3)
				cb73.setSelected(true);
			else if (id == SW_7_4)
				cb74.setSelected(true);
			else if (id == SW_7_5)
				cb75.setSelected(true); // <- ID 9 wird hier sichtbar
			else if (id == SW_7_6)
				cb76.setSelected(true);
		}
	}

	// Öffnen aus Control heraus:
	public void oeffneView() {
		super.oeffneBasisView(); // Wrapper, da BasisView.oeffneBasisView() protected ist
	}

	/**
	 * Wird von BasisView-Button "Speichern" aufgerufen. Übergibt die Auswahl an das
	 * Control.
	 */
	@Override
	protected void speichereSonderwuensche() {
		ArrayList<Integer> auswahl = new ArrayList<>();
		if (cb71.isSelected())
			auswahl.add(SW_7_1);
		if (cb72.isSelected())
			auswahl.add(SW_7_2);
		if (cb73.isSelected())
			auswahl.add(SW_7_3);
		if (cb74.isSelected())
			auswahl.add(SW_7_4);
		if (cb75.isSelected())
			auswahl.add(SW_7_5);
		if (cb76.isSelected())
			auswahl.add(SW_7_6);

		int[] ids = auswahl.stream().mapToInt(Integer::intValue).toArray();
		fliesenControl.speichereSonderwuensche(ids);
	}

	@Override
	protected void berechneUndZeigePreisSonderwuensche() {
		// Optional: Preisberechnung für Fliesen (nicht Teil dieses Tasks)
	}
}
