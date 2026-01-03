package gui.fliesen;

import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Vector;

import business.kunde.Sw;

public class FliesenView extends BasisView {

    private FliesenControl fliesenControl;

	// --- GUI-Elemente ---
	private Label lblFKuecheEGOhne =
			new Label(Sw.F_KUECHE_EG_OHNE.bes);
    private TextField txtFKuecheEGOhne = new TextField();
    private Label lblFKuecheEGOhneEuro = new Label("Euro");
    private CheckBox chckBxFKuecheEGOhne = new CheckBox();
	
    private Label lblFBadOGOhne =
			new Label(Sw.F_BAD_OG_OHNE.bes);
    private TextField txtFBadOGOhne = new TextField();
    private Label lblFBadOGOhneEuro = new Label("Euro");
    private CheckBox chckBxFBadOGOhne = new CheckBox();
    
    private Label lblFKuecheEGGross =
			new Label(Sw.F_KUECHE_EG_GROSS.bes);
    private TextField txtFKuecheEGGross = new TextField();
    private Label lblFKuecheEGGrossEuro = new Label("Euro");
    private CheckBox chckBxFKuecheEGGross = new CheckBox();
    
    private Label lblFBadOGGross =
			new Label(Sw.F_BAD_OG_GROSS.bes);
    private TextField txtFBadOGGross = new TextField();
    private Label lblFBadOGGrossEuro = new Label("Euro");
    private CheckBox chckBxFBadOGGross = new CheckBox();
    
    private Label lblFBadDG =
			new Label(Sw.F_BAD_DG.bes);
    private TextField txtFBadDG = new TextField();
    private Label lblFBadDGEuro = new Label("Euro");
    private CheckBox chckBxFBadDG = new CheckBox();
    
    private Label lblFBadDGGross =
			new Label(Sw.F_BAD_DG_GROSS.bes);
    private TextField txtFBadDGGross = new TextField();
    private Label lblFBadDGGrossEuro = new Label("Euro");
    private CheckBox chckBxFBadDGGross = new CheckBox();

    public FliesenView(FliesenControl fliesenControl, Stage fliesenStage) {
        super(fliesenStage);
        this.fliesenControl = fliesenControl;
        fliesenStage.setTitle("Sonderwünsche zu Fliesen");
        
        initKomponenten();
    }

    protected void initKomponenten() {
    	super.initKomponenten();
		super.getLblSonderwunsch().setText("Fliesen-Varianten");

		// super.getGridPaneSonderwunsch().add(Element, Spalte, Zeile);
        // Zeile 1
		getGridPaneSonderwunsch().add(lblFKuecheEGOhne, 0, 1);
        getGridPaneSonderwunsch().add(txtFKuecheEGOhne, 1, 1);
        txtFKuecheEGOhne.setEditable(false);
        txtFKuecheEGOhne.setText("" + Sw.F_KUECHE_EG_OHNE.preis);
        getGridPaneSonderwunsch().add(lblFKuecheEGOhneEuro, 2, 1);
        getGridPaneSonderwunsch().add(chckBxFKuecheEGOhne, 3, 1);
        
        // Zeile 2
     	getGridPaneSonderwunsch().add(lblFBadOGOhne, 0, 2);
        getGridPaneSonderwunsch().add(txtFBadOGOhne, 1, 2);
        txtFBadOGOhne.setEditable(false);
        txtFBadOGOhne.setText("" + Sw.F_BAD_OG_OHNE.preis);
        getGridPaneSonderwunsch().add(lblFBadOGOhneEuro, 2, 2);
        getGridPaneSonderwunsch().add(chckBxFBadOGOhne, 3, 2);

        // Zeile 3
     	getGridPaneSonderwunsch().add(lblFKuecheEGGross, 0, 3);
        getGridPaneSonderwunsch().add(txtFKuecheEGGross, 1, 3);
        txtFKuecheEGGross.setEditable(false);
        txtFKuecheEGGross.setText("" + Sw.F_KUECHE_EG_GROSS.preis);
        getGridPaneSonderwunsch().add(lblFKuecheEGGrossEuro, 2, 3);
        getGridPaneSonderwunsch().add(chckBxFKuecheEGGross, 3, 3);
        
        // Zeile 4
     	getGridPaneSonderwunsch().add(lblFBadOGGross, 0, 4);
        getGridPaneSonderwunsch().add(txtFBadOGGross, 1, 4);
        txtFBadOGGross.setEditable(false);
        txtFBadOGGross.setText("" + Sw.F_BAD_OG_GROSS.preis);
        getGridPaneSonderwunsch().add(lblFBadOGGrossEuro, 2, 4);
        getGridPaneSonderwunsch().add(chckBxFBadOGGross, 3, 4);
        
        // Zeile 5
     	getGridPaneSonderwunsch().add(lblFBadDG, 0, 5);
        getGridPaneSonderwunsch().add(txtFBadDG, 1, 5);
        txtFBadDG.setEditable(false);
        txtFBadDG.setText("" + Sw.F_BAD_DG.preis);
        getGridPaneSonderwunsch().add(lblFBadDGEuro, 2, 5);
        getGridPaneSonderwunsch().add(chckBxFBadDG, 3, 5);
        
        // Zeile 6
     	getGridPaneSonderwunsch().add(lblFBadDGGross, 0, 6);
        getGridPaneSonderwunsch().add(txtFBadDGGross, 1, 6);
        txtFBadDGGross.setEditable(false);
        txtFBadDGGross.setText("" + Sw.F_BAD_DG_GROSS.preis);
        getGridPaneSonderwunsch().add(lblFBadDGGrossEuro, 2, 6);
        getGridPaneSonderwunsch().add(chckBxFBadDGGross, 3, 6);
        
        // Gesamtpreis (Zeile 7) - aus BasisView
        getGridPaneSonderwunsch().add(lblGesamt, 0, 7);
        getGridPaneSonderwunsch().add(txtGesamt, 1, 7);
        txtGesamt.setEditable(false);
        getGridPaneSonderwunsch().add(lblGesamtEuro, 2, 7);
    }

    public void oeffneFliesenView() {
        super.oeffneBasisView();
    }
    
    private void leseFliesenSonderwuensche() {
    	fliesenControl.leseFliesenSonderwuensche();
    }

    /** Checkboxen anhand der gespeicherten IDs setzen. */
    @Deprecated
    @Override
    public void updateSwCheckboxen(int[] ausgewaehlteSw) {
    	// Alles zurücksetzen
    	chckBxFKuecheEGOhne.setSelected(false);
    	chckBxFBadOGOhne.setSelected(false);
    	chckBxFKuecheEGGross.setSelected(false);
    	chckBxFBadOGGross.setSelected(false);
    	chckBxFBadDG.setSelected(false);
    	chckBxFBadDGGross.setSelected(false);

    	// Checkboxen für vorkommende IDs ankreuzen
        if (ausgewaehlteSw == null) return;
        for (int sw : ausgewaehlteSw) {
            switch (Sw.findeMitId(sw)) {
            	case F_KUECHE_EG_OHNE:
            		chckBxFKuecheEGOhne.setSelected(true);
            		break;
            	case F_BAD_OG_OHNE:
            		chckBxFBadOGOhne.setSelected(true);
            		break;
            	case F_KUECHE_EG_GROSS:
            		chckBxFKuecheEGGross.setSelected(true);
            		break;
            	case F_BAD_OG_GROSS:
            		chckBxFBadOGGross.setSelected(true);
            		break;
            	case F_BAD_DG:
            		chckBxFBadDG.setSelected(true);
            		break;
            	case F_BAD_DG_GROSS:
            		chckBxFBadDGGross.setSelected(true);
            		break;
            	default:
            		System.out.println("Unbekannte Sonderwunsch-ID zu Fliesen: " + sw);
            		break;
            }
		}
	}
    
    @Override
    public void updateSwInView(int[][] ausgewaehlteSw) {
    	// Alles zurücksetzen
    	chckBxFKuecheEGOhne.setSelected(false);
    	chckBxFBadOGOhne.setSelected(false);
    	chckBxFKuecheEGGross.setSelected(false);
    	chckBxFBadOGGross.setSelected(false);
    	chckBxFBadDG.setSelected(false);
    	chckBxFBadDGGross.setSelected(false);

    	// Checkboxen für vorkommende IDs ankreuzen
        if (ausgewaehlteSw == null) return;
        for (int[] sw : ausgewaehlteSw) {
        	if (sw == null || sw.length != 2) continue;
            switch (Sw.findeMitId(sw[0])) {
            	case F_KUECHE_EG_OHNE:
            		chckBxFKuecheEGOhne.setSelected(true);
            		break;
            	case F_BAD_OG_OHNE:
            		chckBxFBadOGOhne.setSelected(true);
            		break;
            	case F_KUECHE_EG_GROSS:
            		chckBxFKuecheEGGross.setSelected(true);
            		break;
            	case F_BAD_OG_GROSS:
            		chckBxFBadOGGross.setSelected(true);
            		break;
            	case F_BAD_DG:
            		chckBxFBadDG.setSelected(true);
            		break;
            	case F_BAD_DG_GROSS:
            		chckBxFBadDGGross.setSelected(true);
            		break;
            	default:
            		System.out.println("Unbekannte Sonderwunsch-ID zu Fliesen: " + sw[0]);
            		break;
            }
		}
    }
	
    public boolean[] holeIsSelectedFuerCheckboxen() {
		return new boolean[] {
				chckBxFKuecheEGOhne.isSelected(),
		    	chckBxFBadOGOhne.isSelected(),
		    	chckBxFKuecheEGGross.isSelected(),
		    	chckBxFBadOGGross.isSelected(),
		    	chckBxFBadDG.isSelected(),
		    	chckBxFBadDGGross.isSelected(),
				};
	}
    
    /*
    @Override
	public int[][] spinnerZu2DIntArray() {
		return null;
	}*/
	
	@Override
    protected int[] checkboxenZuIntArray() {
    	Vector<Integer> v = new Vector<>();

        if (chckBxFKuecheEGOhne.isSelected())
            v.add(Sw.F_KUECHE_EG_GROSS.id);
        if (chckBxFBadOGOhne.isSelected())
            v.add(Sw.F_BAD_OG_OHNE.id);
        if (chckBxFKuecheEGGross.isSelected())
            v.add(Sw.F_KUECHE_EG_GROSS.id);
        if (chckBxFBadOGGross.isSelected())
            v.add(Sw.F_BAD_OG_GROSS.id);
        if (chckBxFBadDG.isSelected())
            v.add(Sw.F_BAD_DG.id);
        if (chckBxFBadDGGross.isSelected())
        	v.add(Sw.F_BAD_DG_GROSS.id);

        int[] fliesenSw = new int[v.size()];
        for (int i = 0; i < v.size(); i++)
            fliesenSw[i] = v.get(i);
        
        return fliesenSw;
    }
	

    @Override
    protected void berechneUndZeigePreisSonderwuensche() {
    	if (!fliesenControl.pruefeKonstellationSonderwuensche(checkboxenZuAnzahlSonderwuensche()))
    		return;
        
    	double preis = 0.0;

        if (chckBxFKuecheEGOhne.isSelected())   preis += Sw.F_KUECHE_EG_OHNE.preis;
        if (chckBxFBadOGOhne.isSelected()) 		preis += Sw.F_BAD_OG_OHNE.preis;
        if (chckBxFKuecheEGGross.isSelected()) 	preis += Sw.F_KUECHE_EG_GROSS.preis;
        if (chckBxFBadOGGross.isSelected())     preis += Sw.F_BAD_OG_GROSS.preis;
        if (chckBxFBadDG.isSelected())       	preis += Sw.F_BAD_DG.preis;
        if (chckBxFBadDGGross.isSelected())		preis += Sw.F_BAD_DG_GROSS.preis;
        
        txtGesamt.setText(String.format("%.2f", preis));
    }
    
    protected int[][] checkboxenZuAnzahlSonderwuensche() {
    	Vector<int[]> v = new Vector<>();

    	if (chckBxFKuecheEGOhne.isSelected()) {
    	    v.add(new int[]{ Sw.F_KUECHE_EG_OHNE.id, 1 });
    	}
    	if (chckBxFBadOGOhne.isSelected()) {
    	    v.add(new int[]{ Sw.F_BAD_OG_OHNE.id, 1 });
    	}
    	if (chckBxFKuecheEGGross.isSelected()) {
    	    v.add(new int[]{ Sw.F_KUECHE_EG_GROSS.id, 1 });
    	}
    	if (chckBxFBadOGGross.isSelected()) {
    	    v.add(new int[]{ Sw.F_BAD_OG_GROSS.id, 1 });
    	}
    	if (chckBxFBadDG.isSelected()) {
    	    v.add(new int[]{ Sw.F_BAD_DG.id, 1 });
    	}
    	if (chckBxFBadDGGross.isSelected()) {
    	    v.add(new int[]{ Sw.F_BAD_DG_GROSS.id, 1 });
    	}
        
        return getAlleTupel(v);
    }
    
    protected int[][] getAlleTupel(Vector<int[]> v) {
        int[][] result = new int[v.size()][];

        for (int i = 0; i < v.size(); i++) {
            result[i] = v.get(i);
        }

        return result;
    }
  	
  	/**
	 * Wird von BasisView-Button "Speichern" aufgerufen.
	 * Übergibt die Auswahl zum Speichern an Control.
	 */
  	@Override
  	protected void speichereSonderwuensche() {
  		// Speichere Sonderwünsche (Prüfung in Control, da das Feld kundeModel private ist)
  		this.fliesenControl.speichereSonderwuensche(checkboxenZuAnzahlSonderwuensche());
  	}
	
    // TODO: CSV-Export für Fenster-Sonderwünsch implementieren.
	@Override
	protected void exportiereSonderwuenscheAlsCsv() {
	}
}
