package gui.fenster;

import java.util.Vector;

import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu
 * Fenstern und Außentueren bereitstellt.
 */
public class FensterView extends BasisView {

    // Control-Objekt des Fenster-Fensters
    private FensterControl fensterControl;

    // GUI-Elemente
    private Label lblSchiebetueren = new Label("Schiebetueren und Haustuer");
    private CheckBox chckBxSchiebetuerEg = new CheckBox("3.1) Schiebetueren im EG zur Terrasse (590 Euro)");
    private CheckBox chckBxSchiebetuerDg = new CheckBox("3.2) Schiebetueren im DG zur Dachterrasse (590 Euro)");
    private CheckBox chckBxEinbruchschutz = new CheckBox("3.3) Erhoehter Einbruchschutz an der Haustuer (690 Euro)");

    private Label lblVorbereitung = new Label("Vorbereitung fuer elektrische Antriebe Rolllaeden");
    private CheckBox chckBxVorbereitungEg = new CheckBox("3.4) EG (190 Euro)");
    private CheckBox chckBxVorbereitungOg = new CheckBox("3.5) OG (190 Euro)");
    private CheckBox chckBxVorbereitungDg = new CheckBox("3.6) DG (190 Euro)");

    private Label lblRolllaeden = new Label("Elektrische Rolllaeden");
    private CheckBox chckBxRollladenEg = new CheckBox("3.7) EG (990 Euro)");
    private CheckBox chckBxRollladenOg = new CheckBox("3.8) OG (990 Euro)");
    private CheckBox chckBxRollladenDg = new CheckBox("3.9) DG (990 Euro)");

   
    public FensterView(FensterControl fensterControl, Stage fensterStage) {
        super(fensterStage);
        this.fensterControl = fensterControl;
        fensterStage.setTitle("Sonderwuensche zu Fenstern und Außentueren");

        this.initKomponenten();
        this.leseFensterSonderwuensche();
    }

    @Override
    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Fenster und Außentueren");

        int row = 1;

        // Schiebetueren und Haustuer
        super.getGridPaneSonderwunsch().add(lblSchiebetueren, 0, row++);
        super.getGridPaneSonderwunsch().add(chckBxSchiebetuerEg, 0, row++);
        super.getGridPaneSonderwunsch().add(chckBxSchiebetuerDg, 0, row++);
        super.getGridPaneSonderwunsch().add(chckBxEinbruchschutz, 0, row++);

        row++;

        // Vorbereitung Rolllaeden
        super.getGridPaneSonderwunsch().add(lblVorbereitung, 0, row++);
        super.getGridPaneSonderwunsch().add(chckBxVorbereitungEg, 0, row++);
        super.getGridPaneSonderwunsch().add(chckBxVorbereitungOg, 0, row++);
        super.getGridPaneSonderwunsch().add(chckBxVorbereitungDg, 0, row++);

        row++;

        // Elektrische Rolllaeden
        super.getGridPaneSonderwunsch().add(lblRolllaeden, 0, row++);
        super.getGridPaneSonderwunsch().add(chckBxRollladenEg, 0, row++);
        super.getGridPaneSonderwunsch().add(chckBxRollladenOg, 0, row++);
        super.getGridPaneSonderwunsch().add(chckBxRollladenDg, 0, row++);
    }


    public void oeffneFensterView() {
        super.oeffneBasisView();
    }

    private void leseFensterSonderwuensche() {
        this.fensterControl.leseFensterSonderwuensche();
    }

    /**
     * Setzt die Checkboxen entsprechend der uebergebenen Sonderwunsch-IDs.
     */
    protected void updateFensterCheckboxen(int[] ausgewaehlteSw) {

        // alle auf false setzen
        chckBxSchiebetuerEg.setSelected(false);
        chckBxSchiebetuerDg.setSelected(false);
        chckBxEinbruchschutz.setSelected(false);
        chckBxVorbereitungEg.setSelected(false);
        chckBxVorbereitungOg.setSelected(false);
        chckBxVorbereitungDg.setSelected(false);
        chckBxRollladenEg.setSelected(false);
        chckBxRollladenOg.setSelected(false);
        chckBxRollladenDg.setSelected(false);

        if (ausgewaehlteSw == null) {
            return;
        }

        for (int sw : ausgewaehlteSw) {
            if (sw < 300 || sw >= 400) {
                continue;
            }
            switch (sw) {
                case 301:
                    chckBxSchiebetuerEg.setSelected(true);
                    break;
                case 302:
                    chckBxSchiebetuerDg.setSelected(true);
                    break;
                case 303:
                    chckBxEinbruchschutz.setSelected(true);
                    break;
                case 304:
                    chckBxVorbereitungEg.setSelected(true);
                    break;
                case 305:
                    chckBxVorbereitungOg.setSelected(true);
                    break;
                case 306:
                    chckBxVorbereitungDg.setSelected(true);
                    break;
                case 307:
                    chckBxRollladenEg.setSelected(true);
                    break;
                case 308:
                    chckBxRollladenOg.setSelected(true);
                    break;
                case 309:
                    chckBxRollladenDg.setSelected(true);
                    break;
                default:
                    System.out.println("Konnte ID " + sw
                            + " keiner Fenster-Checkbox zuordnen");
            }
        }
    }

 

    @Override
    protected void speichereSonderwuensche() {

        // Sammle Sonderwunsch-IDs angekreuzter Checkboxen (Fensterbereich 301–309)
        Vector<Integer> v = new Vector<Integer>();

        if (chckBxSchiebetuerEg.isSelected()) {
            v.add(301);
        }
        if (chckBxSchiebetuerDg.isSelected()) {
            v.add(302);
        }
        if (chckBxEinbruchschutz.isSelected()) {
            v.add(303);
        }
        if (chckBxVorbereitungEg.isSelected()) {
            v.add(304);
        }
        if (chckBxVorbereitungOg.isSelected()) {
            v.add(305);
        }
        if (chckBxVorbereitungDg.isSelected()) {
            v.add(306);
        }
        if (chckBxRollladenEg.isSelected()) {
            v.add(307);
        }
        if (chckBxRollladenOg.isSelected()) {
            v.add(308);
        }
        if (chckBxRollladenDg.isSelected()) {
            v.add(309);
        }

        int[] fensterSw = new int[v.size()];
        for (int i = 0; i < v.size(); i++) {
            fensterSw[i] = v.get(i);
        }

        this.fensterControl.speichereSonderwuensche(fensterSw);
    }

	@Override
	protected void berechneUndZeigePreisSonderwuensche() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean[] holeIsSelectedFuerCheckboxen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void updateSwCheckboxen(int[] ausgewaehlteSw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int[] checkboxenZuIntArray() {
		// TODO Auto-generated method stub
		return null;
	}
}
