package gui.parkett;

import javafx.scene.control.*;
import javafx.stage.Stage;

import business.kunde.Sw;
import gui.basis.BasisView;


public class ParkettView extends BasisView {

    private ParkettControl parkettControl;


    private Label lblLhdEssEg = new Label(Sw.LHD_M_ESS_EG.bes);
    private TextField txtPreisLhdEssEg = new TextField();
    private Label lblLhdEssEgEuro = new Label("Euro");
    private CheckBox chckBxLhdEssEg = new CheckBox();

    private Label lblLhdKuecheEg = new Label(Sw.LHD_M_KUECHE_EG.bes);
    private TextField txtPreisLhdKuecheEg = new TextField();
    private Label lblLhdKuecheEgEuro = new Label("Euro");
    private CheckBox chckBxLhdKuecheEg = new CheckBox();

    private Label lblSpEssEg = new Label(Sw.SP_ESS_EG.bes);
    private TextField txtPreisSpEssEg = new TextField();
    private Label lblSpEssEgEuro = new Label("Euro");
    private CheckBox chckBxSpEssEg = new CheckBox();

    private Label lblSpKuecheEg = new Label(Sw.SP_KUECHE_EG.bes);
    private TextField txtPreisSpKuecheEg = new TextField();
    private Label lblSpKuecheEgEuro = new Label("Euro");
    private CheckBox chckBxSpKuecheEg = new CheckBox();

    private Label lblLhdOg = new Label(Sw.LHD_M_OG.bes);
    private TextField txtPreisLhdOg = new TextField();
    private Label lblLhdOgEuro = new Label("Euro");
    private CheckBox chckBxLhdOg = new CheckBox();

    private Label lblSpOg = new Label(Sw.SP_OG.bes);
    private TextField txtPreisSpOg = new TextField();
    private Label lblSpOgEuro = new Label("Euro");
    private CheckBox chckBxSpOg = new CheckBox();

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


    public ParkettView(ParkettControl parkettControl, Stage parkettStage) {
        super(parkettStage);
        parkettStage.setTitle("Sonderwuensche zu Parkett");       
        this.parkettControl = parkettControl;
        parkettStage.setTitle("Sonderwünsche zu Parkett");

        initKomponenten();
    }

    @Override
    protected void initKomponenten() {
        super.initKomponenten();

        getLblSonderwunsch().setText("Parkett");

        getGridPaneSonderwunsch().add(lblLhdEssEg, 0, 1);
        getGridPaneSonderwunsch().add(txtPreisLhdEssEg, 1, 1);
        txtPreisLhdEssEg.setEditable(false);
        txtPreisLhdEssEg.setText("" + Sw.LHD_M_ESS_EG.preis);
        getGridPaneSonderwunsch().add(lblLhdEssEgEuro, 2, 1);
        getGridPaneSonderwunsch().add(chckBxLhdEssEg, 3, 1);

        getGridPaneSonderwunsch().add(lblLhdKuecheEg, 0, 2);
        getGridPaneSonderwunsch().add(txtPreisLhdKuecheEg, 1, 2);
        txtPreisLhdKuecheEg.setEditable(false);
        txtPreisLhdKuecheEg.setText("" + Sw.LHD_M_KUECHE_EG.preis);
        getGridPaneSonderwunsch().add(lblLhdKuecheEgEuro, 2, 2);
        getGridPaneSonderwunsch().add(chckBxLhdKuecheEg, 3, 2);

        getGridPaneSonderwunsch().add(lblSpEssEg, 0, 3);
        getGridPaneSonderwunsch().add(txtPreisSpEssEg, 1, 3);
        txtPreisSpEssEg.setEditable(false);
        txtPreisSpEssEg.setText("" + Sw.SP_ESS_EG.preis);
        getGridPaneSonderwunsch().add(lblSpEssEgEuro, 2, 3);
        getGridPaneSonderwunsch().add(chckBxSpEssEg, 3, 3);

        getGridPaneSonderwunsch().add(lblSpKuecheEg, 0, 4);
        getGridPaneSonderwunsch().add(txtPreisSpKuecheEg, 1, 4);
        txtPreisSpKuecheEg.setEditable(false);
        txtPreisSpKuecheEg.setText("" + Sw.SP_KUECHE_EG.preis);
        getGridPaneSonderwunsch().add(lblSpKuecheEgEuro, 2, 4);
        getGridPaneSonderwunsch().add(chckBxSpKuecheEg, 3, 4);

        getGridPaneSonderwunsch().add(lblLhdOg, 0, 5);
        getGridPaneSonderwunsch().add(txtPreisLhdOg, 1, 5);
        txtPreisLhdOg.setEditable(false);
        txtPreisLhdOg.setText("" + Sw.LHD_M_OG.preis);
        getGridPaneSonderwunsch().add(lblLhdOgEuro, 2, 5);
        getGridPaneSonderwunsch().add(chckBxLhdOg, 3, 5);

        getGridPaneSonderwunsch().add(lblSpOg, 0, 6);
        getGridPaneSonderwunsch().add(txtPreisSpOg, 1, 6);
        txtPreisSpOg.setEditable(false);
        txtPreisSpOg.setText("" + Sw.SP_OG.preis);
        getGridPaneSonderwunsch().add(lblSpOgEuro, 2, 6);
        getGridPaneSonderwunsch().add(chckBxSpOg, 3, 6);

        getGridPaneSonderwunsch().add(lblLhdDgKomplett, 0, 7);
        getGridPaneSonderwunsch().add(txtPreisLhdDgKomplett, 1, 7);
        txtPreisLhdDgKomplett.setEditable(false);
        txtPreisLhdDgKomplett.setText("" + Sw.LHD_M_KOMPLETT_DG.preis);
        getGridPaneSonderwunsch().add(lblLhdDgKomplettEuro, 2, 7);
        getGridPaneSonderwunsch().add(chckBxLhdDgKomplett, 3, 7);

        getGridPaneSonderwunsch().add(lblLhdDgOhneBad, 0, 8);
        getGridPaneSonderwunsch().add(txtPreisLhdDgOhneBad, 1, 8);
        txtPreisLhdDgOhneBad.setEditable(false);
        txtPreisLhdDgOhneBad.setText("" + Sw.LDH_M_OHNE_BAD_DG.preis);
        getGridPaneSonderwunsch().add(lblLhdDgOhneBadEuro, 2, 8);
        getGridPaneSonderwunsch().add(chckBxLhdDgOhneBad, 3, 8);

        getGridPaneSonderwunsch().add(lblSpDgKomplett, 0, 9);
        getGridPaneSonderwunsch().add(txtPreisSpDgKomplett, 1, 9);
        txtPreisSpDgKomplett.setEditable(false);
        txtPreisSpDgKomplett.setText("" + Sw.SP_KOMPLETT_DG.preis);
        getGridPaneSonderwunsch().add(lblSpDgKomplettEuro, 2, 9);
        getGridPaneSonderwunsch().add(chckBxSpDgKomplett, 3, 9);

        getGridPaneSonderwunsch().add(lblSpDgOhneBad, 0, 10);
        getGridPaneSonderwunsch().add(txtPreisSpDgOhneBad, 1, 10);
        txtPreisSpDgOhneBad.setEditable(false);
        txtPreisSpDgOhneBad.setText("" + Sw.SP_OHNE_BAD_DG.preis);
        getGridPaneSonderwunsch().add(lblSpDgOhneBadEuro, 2, 10);
        getGridPaneSonderwunsch().add(chckBxSpDgOhneBad, 3, 10);

        getGridPaneSonderwunsch().add(lblGesamt, 0, 11);
        getGridPaneSonderwunsch().add(txtGesamt, 1, 11);
        txtGesamt.setEditable(false);
        getGridPaneSonderwunsch().add(lblGesamtEuro, 2, 11);
    }
    
    public void oeffneParkettView() {
        super.oeffneBasisView();
    }
    
    //die methode muss noch in Control implementiert werden.
    private void leseParkettSonderwuensche(){
    	this.parkettControl.leseParkettSonderwuensche();
    }

    @Override
    public void updateSwCheckboxen(int[] ausgewaehlteSw) {
    }

    @Override
    public boolean[] holeIsSelectedFuerCheckboxen() {
    	return new boolean[] {
    			chckBxLhdEssEg.isSelected(),
    			chckBxLhdKuecheEg.isSelected(),
    			chckBxSpEssEg.isSelected(),
    			chckBxSpKuecheEg.isSelected(),
    			chckBxLhdOg.isSelected(),
    			chckBxSpOg.isSelected(),
    	    	chckBxLhdDgKomplett.isSelected(),
    	    	chckBxLhdDgOhneBad.isSelected(),
    	    	chckBxSpDgKomplett.isSelected(),
    	    	chckBxSpDgOhneBad .isSelected()

    	};
    }
    

    @Override
    protected int[] checkboxenZuIntArray() {
        return new int[0];
    }

    @Override
    protected void berechneUndZeigePreisSonderwuensche() {
    }

    @Override
    protected void speichereSonderwuensche() {
    }
    
    // TODO: CSV-Export für Parkett-Sonderwünsch implementieren.
	@Override
	protected void exportiereSonderwuenscheAlsCsv() {
	}
   

}
