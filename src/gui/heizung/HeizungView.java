package gui.heizung;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Vector;

import business.kunde.Sw;

/**
 * View für die Sonderwünsche zu Heizungen.
 */
public class HeizungView extends BasisView {

    private HeizungControl heizungControl;

    // --- GUI-Elemente ---
    private Label lblStdHeizkoerper =
    		new Label(Sw.STD_HEIZKOERPER.bes);
    private TextField txtStdHeizkoerper = new TextField();
    private Label lblStdHeizkoerperEuro = new Label("Euro");
    private CheckBox chckBxStdHeizkoerper = new CheckBox();

    private Label lblGlattHeizkoerper =
            new Label(Sw.GLATT_HEIZKOERPER.bes);
    private TextField txtGlattHeizkoerper = new TextField();
    private Label lblGlattHeizkoerperEuro = new Label("Euro");
    private CheckBox chckBxGlattHeizkoerper = new CheckBox();

    private Label lblHandtuchHeizkoerper =
            new Label(Sw.HANDTUCH.bes);
    private TextField txtHandtuchHeizkoerper = new TextField();
    private Label lblHandtuchHeizkoerperEuro = new Label("Euro");
    private CheckBox chckBxHandtuchHeizkoerper = new CheckBox();

    private Label lblFbhOhneDG =
            new Label(Sw.FBH_OHNE_DG.bes);
    private TextField txtFbhOhneDG = new TextField();
    private Label lblFbhOhneDGEuro = new Label("Euro");
    private CheckBox chckBxFbhOhneDG = new CheckBox();

    private Label lblFbhMitDG =
            new Label(Sw.FBH_OHNE_DG.bes);
    private TextField txtFbhMitDG = new TextField();
    private Label lblFbhMitDGEuro = new Label("Euro");
    private CheckBox chckBxFbhMitDG = new CheckBox();

    public HeizungView(HeizungControl heizungControl, Stage heizungStage) {
        super(heizungStage);
        this.heizungControl = heizungControl;
        heizungStage.setTitle("Sonderwünsche zu Heizungen");

        initKomponenten();
        leseHeizungsSonderwuensche();
    }

    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Heizungs-Varianten");
        
        // super.getGridPaneSonderwunsch().add(Element, Spalte, Zeile);
        // Zeile 1
        super.getGridPaneSonderwunsch().add(lblStdHeizkoerper, 0, 1);
        super.getGridPaneSonderwunsch().add(txtStdHeizkoerper, 1, 1);
        txtStdHeizkoerper.setEditable(false);
        txtStdHeizkoerper.setText("" + Sw.STD_HEIZKOERPER.preis);
        super.getGridPaneSonderwunsch().add(lblStdHeizkoerperEuro, 2, 1);
        super.getGridPaneSonderwunsch().add(chckBxStdHeizkoerper, 3, 1);

        // Zeile 2
        super.getGridPaneSonderwunsch().add(lblGlattHeizkoerper, 0, 2);
        super.getGridPaneSonderwunsch().add(txtGlattHeizkoerper, 1, 2);
        txtGlattHeizkoerper.setEditable(false);
        txtGlattHeizkoerper.setText("" + Sw.GLATT_HEIZKOERPER.preis);
        super.getGridPaneSonderwunsch().add(lblGlattHeizkoerperEuro, 2, 2);
        super.getGridPaneSonderwunsch().add(chckBxGlattHeizkoerper, 3, 2);

        // Zeile 3
        super.getGridPaneSonderwunsch().add(lblHandtuchHeizkoerper, 0, 3);
        super.getGridPaneSonderwunsch().add(txtHandtuchHeizkoerper, 1, 3);
        txtHandtuchHeizkoerper.setEditable(false);
        txtHandtuchHeizkoerper.setText("" + Sw.HANDTUCH.preis);
        super.getGridPaneSonderwunsch().add(lblHandtuchHeizkoerperEuro, 2, 3);
        super.getGridPaneSonderwunsch().add(chckBxHandtuchHeizkoerper, 3, 3);

        // Zeile 4
        super.getGridPaneSonderwunsch().add(lblFbhOhneDG, 0, 4);
        super.getGridPaneSonderwunsch().add(txtFbhOhneDG, 1, 4);
        txtFbhOhneDG.setEditable(false);
        txtFbhOhneDG.setText("" + Sw.FBH_OHNE_DG.preis);
        super.getGridPaneSonderwunsch().add(lblFbhOhneDGEuro, 2, 4);
        super.getGridPaneSonderwunsch().add(chckBxFbhOhneDG, 3, 4);

        // Zeile 5
        super.getGridPaneSonderwunsch().add(lblFbhMitDG, 0, 5);
        super.getGridPaneSonderwunsch().add(txtFbhMitDG, 1, 5);
        txtFbhMitDG.setEditable(false);
        txtFbhMitDG.setText("" + Sw.FBH_MIT_DG.preis);
        super.getGridPaneSonderwunsch().add(lblFbhMitDGEuro, 2, 5);
        super.getGridPaneSonderwunsch().add(chckBxFbhMitDG, 3, 5);

        // Gesamtpreis (Zeile 6) - aus BasisView
        super.getGridPaneSonderwunsch().add(lblGesamt, 0, 6);
        super.getGridPaneSonderwunsch().add(txtGesamt, 1, 6);
        txtGesamt.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblGesamtEuro, 2, 6);
    }

    public void oeffneHeizungView() {
        super.oeffneBasisView();
    }

    private void leseHeizungsSonderwuensche() {
        heizungControl.leseHeizungsSonderwuensche();
    }

    /** Checkboxen anhand der gespeicherten IDs setzen. */
    @Override
    protected void updateSwCheckboxen(int[] ausgewaehlteSw) {
        // Alles zurücksetzen
        chckBxStdHeizkoerper.setSelected(false);
        chckBxGlattHeizkoerper.setSelected(false);
        chckBxHandtuchHeizkoerper.setSelected(false);
        chckBxFbhOhneDG.setSelected(false);
        chckBxFbhMitDG.setSelected(false);

        // Checkboxen für vorkommende IDs ankreuzen
        if (ausgewaehlteSw == null) return;
        for (int sw : ausgewaehlteSw) {
            switch (Sw.findeMitId(sw)) {
                case STD_HEIZKOERPER:
                    chckBxStdHeizkoerper.setSelected(true);
                    break;
                case GLATT_HEIZKOERPER:
                    chckBxGlattHeizkoerper.setSelected(true);
                    break;
                case HANDTUCH:
                    chckBxHandtuchHeizkoerper.setSelected(true);
                    break;
                case FBH_OHNE_DG:
                    chckBxFbhOhneDG.setSelected(true);
                    break;
                case FBH_MIT_DG:
                    chckBxFbhMitDG.setSelected(true);
                    break;
                default:
                    System.out.println(
                    		"Unbekannte Sonderwunsch-ID zu Heizkörpern: " + sw);
            }
        }
    }
    
    @Override
    protected boolean[] holeIsSelectedFuerCheckboxen() {
    	return new boolean[] {
    			chckBxStdHeizkoerper.isSelected(),
    			chckBxGlattHeizkoerper.isSelected(),
    			chckBxHandtuchHeizkoerper.isSelected(),
    			chckBxFbhOhneDG.isSelected(),
    			chckBxFbhMitDG.isSelected()
    	};
    }
    
    @Override
    protected int[] checkboxenZuIntArray() {
    	Vector<Integer> v = new Vector<>();

        if (chckBxStdHeizkoerper.isSelected())
            v.add(Sw.STD_HEIZKOERPER.id);
        if (chckBxGlattHeizkoerper.isSelected())
            v.add(Sw.GLATT_HEIZKOERPER.id);
        if (chckBxHandtuchHeizkoerper.isSelected())
            v.add(Sw.HANDTUCH.id);
        if (chckBxFbhOhneDG.isSelected())
            v.add(Sw.FBH_OHNE_DG.id);
        if (chckBxFbhMitDG.isSelected())
            v.add(Sw.FBH_MIT_DG.id);

        int[] heizungSw = new int[v.size()];
        for (int i = 0; i < v.size(); i++)
            heizungSw[i] = v.get(i);
        
        return heizungSw;
    }

    /** Gesamtpreis berechnen und anzeigen. Wird bereits von BasisView.btnBerechnen.onClick aufgerufen! */
    protected void berechneUndZeigePreisSonderwuensche() {
    	if (!heizungControl.pruefeKonstellationHeizkoerper(checkboxenZuIntArray()))
    		return;
        
    	double preis = 0.0;

        if (chckBxStdHeizkoerper.isSelected())   preis += Sw.STD_HEIZKOERPER.preis;
        if (chckBxGlattHeizkoerper.isSelected()) preis += Sw.GLATT_HEIZKOERPER.preis;
        if (chckBxHandtuchHeizkoerper.isSelected()) preis += Sw.HANDTUCH.preis;
        if (chckBxFbhOhneDG.isSelected())        preis += Sw.FBH_OHNE_DG.preis;
        if (chckBxFbhMitDG.isSelected())         preis += Sw.FBH_MIT_DG.preis;

        txtGesamt.setText(String.format("%.2f", preis));
    }

    /** Auswahl speichern (IDs aus den Checkboxen sammeln). */
    protected void speichereSonderwuensche() {
        Vector<Integer> v = new Vector<>();

        if (chckBxStdHeizkoerper.isSelected())
            v.add(Sw.STD_HEIZKOERPER.id);
        if (chckBxGlattHeizkoerper.isSelected())
            v.add(Sw.GLATT_HEIZKOERPER.id);
        if (chckBxHandtuchHeizkoerper.isSelected())
            v.add(Sw.HANDTUCH.id);
        if (chckBxFbhOhneDG.isSelected())
            v.add(Sw.FBH_OHNE_DG.id);
        if (chckBxFbhMitDG.isSelected())
            v.add(Sw.FBH_MIT_DG.id);

        int[] heizungSw = new int[v.size()];
        for (int i = 0; i < v.size(); i++)
            heizungSw[i] = v.get(i);
        
        // Control kontrolliert Konstellation
        heizungControl.speichereSonderwuensche(heizungSw);
    }
}
