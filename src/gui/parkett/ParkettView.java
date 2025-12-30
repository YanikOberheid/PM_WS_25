package gui.parkett;

import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Vector;

import business.kunde.Sw;
import gui.basis.BasisView;

/**
 * View-Klasse für die Anzeige der Parkett-Optionen.
 */
public class ParkettView extends BasisView {

    private ParkettControl parkettControl;

    // --- Definition der GUI-Elemente ---
    // Erdgeschoss - Essbereich
    private Label lblLhdEssEg = new Label(Sw.LHD_M_ESS_EG.bes);
    private TextField txtPreisLhdEssEg = new TextField();
    private Label lblLhdEssEgEuro = new Label("Euro");
    private CheckBox chckBxLhdEssEg = new CheckBox();

    // Erdgeschoss - Küche
    private Label lblLhdKuecheEg = new Label(Sw.LHD_M_KUECHE_EG.bes);
    private TextField txtPreisLhdKuecheEg = new TextField();
    private Label lblLhdKuecheEgEuro = new Label("Euro");
    private CheckBox chckBxLhdKuecheEg = new CheckBox();

    // Stäbchenparkett Varianten EG
    private Label lblSpEssEg = new Label(Sw.SP_ESS_EG.bes);
    private TextField txtPreisSpEssEg = new TextField();
    private Label lblSpEssEgEuro = new Label("Euro");
    private CheckBox chckBxSpEssEg = new CheckBox();

    private Label lblSpKuecheEg = new Label(Sw.SP_KUECHE_EG.bes);
    private TextField txtPreisSpKuecheEg = new TextField();
    private Label lblSpKuecheEgEuro = new Label("Euro");
    private CheckBox chckBxSpKuecheEg = new CheckBox();

    // Obergeschoss
    private Label lblLhdOg = new Label(Sw.LHD_M_OG.bes);
    private TextField txtPreisLhdOg = new TextField();
    private Label lblLhdOgEuro = new Label("Euro");
    private CheckBox chckBxLhdOg = new CheckBox();

    private Label lblSpOg = new Label(Sw.SP_OG.bes);
    private TextField txtPreisSpOg = new TextField();
    private Label lblSpOgEuro = new Label("Euro");
    private CheckBox chckBxSpOg = new CheckBox();

    // Dachgeschoss
    private Label lblLhdDgKomplett = new Label(Sw.LHD_M_KOMPLETT_DG.bes);
    private TextField txtPreisLhdDgKomplett = new TextField();
    private Label lblLhdDgKomplettEuro = new Label("Euro");
    private CheckBox chckBxLhdDgKomplett = new CheckBox();

    private Label lblLhdDgOhneBad = new Label(Sw.LDH_M_OHNE_BAD_DG.bes);
    private TextField txtPreisLhdDgOhneBad = new TextField();
    private Label lblLhdDgOhneBadEuro = new Label("Euro");
    private CheckBox chckBxLhdDgOhneBad = new CheckBox();

    private Label lblSpDgKomplett = new Label(Sw.SP_KOMPLETT_DG.bes);
    private TextField txtPreisSpDgKomplett = new TextField();
    private Label lblSpDgKomplettEuro = new Label("Euro");
    private CheckBox chckBxSpDgKomplett = new CheckBox();

    private Label lblSpDgOhneBad = new Label(Sw.SP_OHNE_BAD_DG.bes);
    private TextField txtPreisSpDgOhneBad = new TextField();
    private Label lblSpDgOhneBadEuro = new Label("Euro");
    private CheckBox chckBxSpDgOhneBad = new CheckBox();

    // Array für Iterationen
    private CheckBox[] allCheckboxes;

    public ParkettView(ParkettControl parkettControl, Stage parkettStage) {
        super(parkettStage);
        this.parkettControl = parkettControl;
        parkettStage.setTitle("Sonderwünsche zu Parkett");
        
        parkettStage.setWidth(650);
        parkettStage.setHeight(600);        

        initKomponenten();
    }

    @Override
    protected void initKomponenten() {
        super.initKomponenten();
        getLblSonderwunsch().setText("Parkett");

        addRow(1, lblLhdEssEg, txtPreisLhdEssEg, Sw.LHD_M_ESS_EG, lblLhdEssEgEuro, chckBxLhdEssEg);
        addRow(2, lblLhdKuecheEg, txtPreisLhdKuecheEg, Sw.LHD_M_KUECHE_EG, lblLhdKuecheEgEuro, chckBxLhdKuecheEg);
        addRow(3, lblSpEssEg, txtPreisSpEssEg, Sw.SP_ESS_EG, lblSpEssEgEuro, chckBxSpEssEg);
        addRow(4, lblSpKuecheEg, txtPreisSpKuecheEg, Sw.SP_KUECHE_EG, lblSpKuecheEgEuro, chckBxSpKuecheEg);
        addRow(5, lblLhdOg, txtPreisLhdOg, Sw.LHD_M_OG, lblLhdOgEuro, chckBxLhdOg);
        addRow(6, lblSpOg, txtPreisSpOg, Sw.SP_OG, lblSpOgEuro, chckBxSpOg);
        addRow(7, lblLhdDgKomplett, txtPreisLhdDgKomplett, Sw.LHD_M_KOMPLETT_DG, lblLhdDgKomplettEuro, chckBxLhdDgKomplett);
        addRow(8, lblLhdDgOhneBad, txtPreisLhdDgOhneBad, Sw.LDH_M_OHNE_BAD_DG, lblLhdDgOhneBadEuro, chckBxLhdDgOhneBad);
        addRow(9, lblSpDgKomplett, txtPreisSpDgKomplett, Sw.SP_KOMPLETT_DG, lblSpDgKomplettEuro, chckBxSpDgKomplett);
        addRow(10, lblSpDgOhneBad, txtPreisSpDgOhneBad, Sw.SP_OHNE_BAD_DG, lblSpDgOhneBadEuro, chckBxSpDgOhneBad);

        getGridPaneSonderwunsch().add(lblGesamt, 0, 11);
        getGridPaneSonderwunsch().add(txtGesamt, 1, 11);
        txtGesamt.setEditable(false);
        getGridPaneSonderwunsch().add(lblGesamtEuro, 2, 11);
        
        allCheckboxes = new CheckBox[] {
            chckBxLhdEssEg, chckBxLhdKuecheEg, chckBxSpEssEg, chckBxSpKuecheEg,
            chckBxLhdOg, chckBxSpOg,
            chckBxLhdDgKomplett, chckBxLhdDgOhneBad, chckBxSpDgKomplett, chckBxSpDgOhneBad
        };
        
        chckBxLhdEssEg.setUserData(Sw.LHD_M_ESS_EG);
        chckBxLhdKuecheEg.setUserData(Sw.LHD_M_KUECHE_EG);
        chckBxSpEssEg.setUserData(Sw.SP_ESS_EG);
        chckBxSpKuecheEg.setUserData(Sw.SP_KUECHE_EG);
        chckBxLhdOg.setUserData(Sw.LHD_M_OG);
        chckBxSpOg.setUserData(Sw.SP_OG);
        chckBxLhdDgKomplett.setUserData(Sw.LHD_M_KOMPLETT_DG);
        chckBxLhdDgOhneBad.setUserData(Sw.LDH_M_OHNE_BAD_DG);
        chckBxSpDgKomplett.setUserData(Sw.SP_KOMPLETT_DG);
        chckBxSpDgOhneBad.setUserData(Sw.SP_OHNE_BAD_DG);
    }
    
    private void addRow(int row, Label lbl, TextField txt, Sw sw, Label lblEuro, CheckBox cb) {
        getGridPaneSonderwunsch().add(lbl, 0, row);
        getGridPaneSonderwunsch().add(txt, 1, row);
        txt.setEditable(false);
        txt.setText("" + sw.preis);
        getGridPaneSonderwunsch().add(lblEuro, 2, row);
        getGridPaneSonderwunsch().add(cb, 3, row);
    }

    public void oeffneParkettView() {
        super.oeffneBasisView();
    }
    

    public void updateSwInView(int[][] ausgewaehlteSw) {
        for (CheckBox cb : allCheckboxes) {
            cb.setSelected(false);
        }
        
        if (ausgewaehlteSw == null) return;

        for (int[] tupel : ausgewaehlteSw) {
            int id = tupel[0];
            for (CheckBox cb : allCheckboxes) {
                Sw sw = (Sw) cb.getUserData();
                if (sw.id == id) {
                    cb.setSelected(true);
                }
            }
        }
    }

    @Override
    public void updateSwCheckboxen(int[] ausgewaehlteSw) {
        if(ausgewaehlteSw == null) return;
        
        int[][] temp = new int[ausgewaehlteSw.length][2];
        for(int i=0; i<ausgewaehlteSw.length; i++) {
            temp[i][0] = ausgewaehlteSw[i];
            temp[i][1] = 1;
        }
        // Delegiere an die neue Logik
        updateSwInView(temp);
    }

    @Override
    public boolean[] holeIsSelectedFuerCheckboxen() {
        boolean[] selected = new boolean[allCheckboxes.length];
        for (int i = 0; i < allCheckboxes.length; i++) {
            selected[i] = allCheckboxes[i].isSelected();
        }
        return selected;
    }
    
    @Override
    protected int[] checkboxenZuIntArray() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (CheckBox cb : allCheckboxes) {
            if (cb.isSelected()) {
                Sw sw = (Sw) cb.getUserData();
                ids.add(sw.id);
            }
        }
        return ids.stream().mapToInt(i -> i).toArray();
    }
    
    /**
     * WICHTIG: @Override ENTFERNT, falls BasisView dies nicht kennt.
     */
    protected int[][] checkboxenZuAnzahlSonderwuensche() {
        int[] ids = checkboxenZuIntArray();
        int[][] result = new int[ids.length][2];
        for (int i = 0; i < ids.length; i++) {
            result[i][0] = ids[i]; 
            result[i][1] = 1;     
        }
        return result;
    }

    @Override
    public void berechneUndZeigePreisSonderwuensche() {
        double gesamt = 0;
        for (CheckBox cb : allCheckboxes) {
            if (cb.isSelected()) {
                Sw sw = (Sw) cb.getUserData();
                gesamt += sw.preis;
            }
        }
        txtGesamt.setText(String.format("%.2f", gesamt));
    }

    @Override
    protected void speichereSonderwuensche() {
        int[][] selectedData = checkboxenZuAnzahlSonderwuensche();
        
        if (this.parkettControl.pruefeKonstellationSonderwuensche(selectedData)) {
            this.parkettControl.speichereSonderwuensche(selectedData);
            ((Stage) getGridPaneSonderwunsch().getScene().getWindow()).close();
        }
    }
    

    protected void exportiereSonderwuenscheAlsCsv() {
        // TODO: Implementierung
    }

	@Override
	protected int[][] getAlleTupel(Vector<int[]> v) {
		// TODO Auto-generated method stub
		return null;
	}
}