package gui.aussenanlagen;

import business.kunde.Sw;
import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * View für die Sonderwünsche zu Außenanlagen.
 */
public class AussenanlagenView extends BasisView {
	
	AussenanlagenControl aussenanlagenControl;
	
	// --- GUI-Elemente ---
	private Label lblAbstellTerrasseEG =
    		new Label(Sw.ABSTELL_TERRASSE_EG.bes);
    private TextField txtAbstellTerrasseEG = new TextField();
    private Label lblAbstellTerrasseEGEuro = new Label("Euro");
    private CheckBox chckBxAbstellTerrasseEG = new CheckBox();
    
    private Label lblVEAMarkiseEG =
    		new Label(Sw.VEA_MARKISE_EG.bes);
    private TextField txtVEAMarkiseEG = new TextField();
    private Label lblVEAMarkiseEGEuro = new Label("Euro");
    private CheckBox chckBxVEAMarkiseEG = new CheckBox();
    
    private Label lblVEAMarkiseDG =
    		new Label(Sw.VEA_MARKISE_DG.bes);
    private TextField txtVEAMarkiseDG = new TextField();
    private Label lblVEAMarkiseDGEuro = new Label("Euro");
    private CheckBox chckBxVEAMarkiseDG = new CheckBox();
    
    private Label lblEMarkiseEG =
    		new Label(Sw.E_MARKISE_EG.bes);
    private TextField txtEMarkiseEG = new TextField();
    private Label lblEMarkiseEGEuro = new Label("Euro");
    private CheckBox chckBxEMarkiseEG = new CheckBox();
    
    private Label lblEMarkiseDG =
    		new Label(Sw.E_MARKISE_DG.bes);
    private TextField txtEMarkiseDG = new TextField();
    private Label lblEMarkiseDGEuro = new Label("Euro");
    private CheckBox chckBxEMarkiseDG = new CheckBox();
    
    private Label lblEAGaragentor =
    		new Label(Sw.EA_GARAGENTOR.bes);
    private TextField txtEAGaragentor = new TextField();
    private Label lblEAGaragentorEuro = new Label("Euro");
    private CheckBox chckBxEAGaragentor = new CheckBox();
    
    private Label lblSTGaragentor =
    		new Label(Sw.ST_GARAGENTOR.bes);
    private TextField txtSTGaragentor = new TextField();
    private Label lblSTGaragentorEuro = new Label("Euro");
    private CheckBox chckBxSTGaragentor = new CheckBox();
    
    public AussenanlagenView(AussenanlagenControl aussenanlagenControl, Stage stage) {
    	super(stage);
    	this.aussenanlagenControl = aussenanlagenControl;
    	stage.setTitle("Sonderwünsche zu Außenanlagen");
    	
    	initKomponenten();
    }
    
    protected void initKomponenten() {
    	super.initKomponenten();
    	super.getLblSonderwunsch().setText("Außenanlagen-Varianten");
    	
    	// super.getGridPaneSonderwunsch().add(Element, Spalte, Zeile);
    	// Zeile 1
        getGridPaneSonderwunsch().add(lblAbstellTerrasseEG, 0, 1);
        getGridPaneSonderwunsch().add(txtAbstellTerrasseEG, 1, 1);
        txtAbstellTerrasseEG.setEditable(false);
        txtAbstellTerrasseEG.setText("" + Sw.ABSTELL_TERRASSE_EG.preis);
        getGridPaneSonderwunsch().add(lblAbstellTerrasseEGEuro, 2, 1);
        getGridPaneSonderwunsch().add(chckBxAbstellTerrasseEG, 3, 1);
        
        // Zeile 2
        getGridPaneSonderwunsch().add(lblVEAMarkiseEG, 0, 2);
        getGridPaneSonderwunsch().add(txtVEAMarkiseEG, 1, 2);
        txtVEAMarkiseEG.setEditable(false);
        txtVEAMarkiseEG.setText("" + Sw.VEA_MARKISE_EG.preis);
        getGridPaneSonderwunsch().add(lblVEAMarkiseEGEuro, 2, 2);
        getGridPaneSonderwunsch().add(chckBxVEAMarkiseEG, 3, 2);
        
        // Zeile 3
        getGridPaneSonderwunsch().add(lblVEAMarkiseDG, 0, 3);
        getGridPaneSonderwunsch().add(txtVEAMarkiseDG, 1, 3);
        txtVEAMarkiseDG.setEditable(false);
        txtVEAMarkiseDG.setText("" + Sw.VEA_MARKISE_DG.preis);
        getGridPaneSonderwunsch().add(lblVEAMarkiseDGEuro, 2, 3);
        getGridPaneSonderwunsch().add(chckBxVEAMarkiseDG, 3, 3);
        
        // Zeile 4
        getGridPaneSonderwunsch().add(lblEMarkiseEG, 0, 4);
        getGridPaneSonderwunsch().add(txtEMarkiseEG, 1, 4);
        txtEMarkiseEG.setEditable(false);
        txtEMarkiseEG.setText("" + Sw.E_MARKISE_EG.preis);
        getGridPaneSonderwunsch().add(lblEMarkiseEGEuro, 2, 4);
        getGridPaneSonderwunsch().add(chckBxEMarkiseEG, 3, 4);
        
        // Zeile 5
        getGridPaneSonderwunsch().add(lblEMarkiseDG, 0, 5);
        getGridPaneSonderwunsch().add(txtEMarkiseDG, 1, 5);
        txtEMarkiseDG.setEditable(false);
        txtEMarkiseDG.setText("" + Sw.E_MARKISE_DG.preis);
        getGridPaneSonderwunsch().add(lblEMarkiseDGEuro, 2, 5);
        getGridPaneSonderwunsch().add(chckBxEMarkiseDG, 3, 5);
        
        // Zeile 6
        getGridPaneSonderwunsch().add(lblEAGaragentor, 0, 6);
        getGridPaneSonderwunsch().add(txtEAGaragentor, 1, 6);
        txtEAGaragentor.setEditable(false);
        txtEAGaragentor.setText("" + Sw.EA_GARAGENTOR.preis);
        getGridPaneSonderwunsch().add(lblEAGaragentorEuro, 2, 6);
        getGridPaneSonderwunsch().add(chckBxEAGaragentor, 3, 6);
        
        // Zeile 7
        getGridPaneSonderwunsch().add(lblSTGaragentor, 0, 7);
        getGridPaneSonderwunsch().add(txtSTGaragentor, 1, 7);
        txtSTGaragentor.setEditable(false);
        txtSTGaragentor.setText("" + Sw.ST_GARAGENTOR.preis);
        getGridPaneSonderwunsch().add(lblSTGaragentorEuro, 2, 7);
        getGridPaneSonderwunsch().add(chckBxSTGaragentor, 3, 7);
        
     // Gesamtpreis (Zeile 8) - aus BasisView
        getGridPaneSonderwunsch().add(lblGesamt, 0, 8);
        getGridPaneSonderwunsch().add(txtGesamt, 1, 8);
        txtGesamt.setEditable(false);
        getGridPaneSonderwunsch().add(lblGesamtEuro, 2, 8);
    }
    
    public void oeffneAussenanlagenView() {
    	super.oeffneBasisView();
    }
    
    private void leseAussenanlagenSonderwuensche() {
    	aussenanlagenControl.leseAussenanlagenSonderwuensche();
    }
    
    /** Checkboxen anhand der gespeicherten IDs setzen. */
    @Override
    public void updateSwCheckboxen(int[] ausgewaehlteSw) {
    	// TODO
    }
    
    @Override
    public boolean[] holeIsSelectedFuerCheckboxen() {
    	return null; // TODO
    }
    
    @Override
    protected int[] checkboxenZuIntArray() {
    	return null; // TODO
    }
    
    /** Gesamtpreis berechnen und anzeigen. Wird bereits von BasisView.btnBerechnen.onClick aufgerufen! */
    @Override
    protected void berechneUndZeigePreisSonderwuensche() {
    	// TODO
    }
    
    /**
	 * Wird von BasisView-Button "Speichern" aufgerufen.
	 * Übergibt die Auswahl zum Speichern an Control.
	 */
    @Override
    protected void speichereSonderwuensche() {
    	// TODO
    }
}
