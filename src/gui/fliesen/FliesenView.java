package gui.fliesen;

import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FliesenView extends BasisView {

    private FliesenControl fliesenControl;
    
    private static final int SW_7_1 = 701;
	private static final int SW_7_2 = 702;
	private static final int SW_7_3 = 703;
	private static final int SW_7_4 = 704;
	private static final int SW_7_5 = 705;
	private static final int SW_7_6 = 706;

	// GUI-Elemente
	private final Label lbl701 = new Label("7.1 Keine Fliesen im Küchenbereich des EG");
	private final CheckBox cb701 = new CheckBox();

	private final Label lbl702 = new Label("7.2 Keine Fliesen im Bad des OG");
	private final CheckBox cb702 = new CheckBox();

	private final Label lbl703 = new Label("7.3 Mehrpreis bei großformatige Fliesen im Küchenbereich des EG");
	private final CheckBox cb703 = new CheckBox();

	private final Label lbl704 = new Label("7.4 Mehrpreis bei großformatige Fliesen im Bad des OG");
	private final CheckBox cb704 = new CheckBox();

	private final Label lbl705 = new Label("7.5 Fliesen im Bad des DG");
	private final CheckBox cb705 = new CheckBox();

	private final Label lbl706 = new Label("7.6 Mehrpreis bei großformatige Fliesen im Bad des DG");
	private final CheckBox cb706 = new CheckBox();

    public FliesenView(FliesenControl fliesenControl, Stage stage) {
        super(stage);
        this.fliesenControl = fliesenControl;
        stage.setTitle("Sonderwünsche zu Fliesen");
        
        this.initKomponenten();
    }

    protected void initKomponenten() {
    	super.initKomponenten();
		super.getLblSonderwunsch().setText("Fliesen-Varianten");

		int row = 1;
		getGridPaneSonderwunsch().add(lbl701, 0, row);
		getGridPaneSonderwunsch().add(cb701, 3, row++);
		getGridPaneSonderwunsch().add(lbl702, 0, row);
		getGridPaneSonderwunsch().add(cb702, 3, row++);
		getGridPaneSonderwunsch().add(lbl703, 0, row);
		getGridPaneSonderwunsch().add(cb703, 3, row++);
		getGridPaneSonderwunsch().add(lbl704, 0, row);
		getGridPaneSonderwunsch().add(cb704, 3, row++);
		getGridPaneSonderwunsch().add(lbl705, 0, row);
		getGridPaneSonderwunsch().add(cb705, 3, row++);
		getGridPaneSonderwunsch().add(lbl706, 0, row);
		getGridPaneSonderwunsch().add(cb706, 3, row++);
    }

    public void oeffneFliesenView() {
        super.oeffneBasisView();
    }

    /** Setzt die Haken in der GUI basierend auf den geladenen Daten aus der DB. */
	public void updateFliesenCheckboxen(int[] ausgewaehlteSw) {
		// Reset
		cb701.setSelected(false);
		cb702.setSelected(false);
		cb703.setSelected(false);
		cb704.setSelected(false);
		cb705.setSelected(false);
		cb706.setSelected(false);

		if (ausgewaehlteSw == null)
			return;

		for (int id : ausgewaehlteSw) {
			if (id == SW_7_1)
				cb701.setSelected(true);
			else if (id == SW_7_2)
				cb702.setSelected(true);
			else if (id == SW_7_3)
				cb703.setSelected(true);
			else if (id == SW_7_4)
				cb704.setSelected(true);
			else if (id == SW_7_5)
				cb705.setSelected(true);
			else if (id == SW_7_6)
				cb706.setSelected(true);
		}
	}
	
	protected boolean[] holeIsSelectedFuerCheckboxen() {
		return new boolean[] {
				cb701.isSelected(),
				cb702.isSelected(),
				cb703.isSelected(),
				cb704.isSelected(),
				cb705.isSelected(),
				cb706.isSelected(),
				};
	}

   
    /**
	 * Wird von BasisView-Button "Speichern" aufgerufen. Übergibt die Auswahl an das
	 * Control.
	 */
	@Override
	protected void speichereSonderwuensche() {
		ArrayList<Integer> auswahl = new ArrayList<>();
		if (cb701.isSelected())
			auswahl.add(SW_7_1);
		if (cb702.isSelected())
			auswahl.add(SW_7_2);
		if (cb703.isSelected())
			auswahl.add(SW_7_3);
		if (cb704.isSelected())
			auswahl.add(SW_7_4);
		if (cb705.isSelected())
			auswahl.add(SW_7_5);
		if (cb706.isSelected())
			auswahl.add(SW_7_6);
		
		int[] auswahlArray = auswahl.stream().mapToInt(i -> i).toArray();
		fliesenControl.speichereSonderwuensche(auswahlArray);
	}

    @Override
    protected void berechneUndZeigePreisSonderwuensche() {
        // Optional: Preisberechnung hier implementieren
    }
}