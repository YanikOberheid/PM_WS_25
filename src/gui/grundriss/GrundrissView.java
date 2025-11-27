package gui.grundriss;

import java.util.ArrayList;
import java.util.List;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Vector;

import gui.basis.BasisView;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Grundrissvarianten
 * bereitstellt.
 */
public class GrundrissView extends BasisView{

 	// das Control-Objekt des Grundriss-Fensters
	private GrundrissControl grundrissControl;
   
     private String[][] sonderwuensche;
    //---Anfang Attribute der grafischen Oberflaeche---
    private Label lblWandKueche    	     
        = new Label("Wand zur Abtrennung der Kueche von dem Essbereich");
    private TextField txtPreisWandKueche 	= new TextField();
    private Label lblWandKuecheEuro 		= new Label("Euro");
    private CheckBox chckBxWandKueche 		= new CheckBox();
    
    private CheckBox chckBxTuerKueche 		= new CheckBox();
    private CheckBox chckBxGrossesZimmerOG 	= new CheckBox();
    private CheckBox chckBxTreppenraumDG 	= new CheckBox();
    private CheckBox chckBxVorrichtungBadOG	= new CheckBox();
    private CheckBox chckBxAusfuehrungBadDG = new CheckBox();
    //-------Ende Attribute der grafischen Oberflaeche-------
    private Label[] lblPlatzhalterEuro = new Label[6]; // Array für Euro Labels
    private CheckBox[] chckBxPlatzhalter = new CheckBox[6]; // Array für Checkboxes
  
    /**
     * erzeugt ein GrundrissView-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param grundrissControl GrundrissControl, enthaelt das zugehoerige Control
     * @param grundrissStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public GrundrissView (GrundrissControl grundrissControl, Stage grundrissStage){
    	super(grundrissStage);
        this.grundrissControl = grundrissControl;
        grundrissStage.setTitle("Sonderw\u00fcnsche zu Grundriss-Varianten");
                
	    this.initKomponenten();
	    this.leseGrundrissSonderwuensche();
    }
  
    public String [][] getsonderwuensche(){
        return sonderwuensche;
    }
    public CheckBox[] getChckBxPlatzhalter() {
        return chckBxPlatzhalter;

    }
    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
    	super.initKomponenten(); 
       	super.getLblSonderwunsch().setText("Grundriss-Varianten");
       	super.getGridPaneSonderwunsch().add(lblWandKueche, 0, 1);
    	super.getGridPaneSonderwunsch().add(txtPreisWandKueche, 1, 1);
    	txtPreisWandKueche.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblWandKuecheEuro, 2, 1);
    	super.getGridPaneSonderwunsch().add(chckBxWandKueche, 3, 1);
    }  
    
    /**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView() {
		super.oeffneBasisView();
	}
    
    private void leseGrundrissSonderwuensche(){
    	this.grundrissControl.leseGrundrissSonderwuensche();
    }
    
    protected void updateGrundrissCheckboxen(int[] ausgewaehlteSw) {
    	// Setze alle auf false
    	chckBxWandKueche.setSelected(false);
    	chckBxTuerKueche.setSelected(false);
    	chckBxGrossesZimmerOG.setSelected(false);
    	chckBxTreppenraumDG.setSelected(false);
    	chckBxVorrichtungBadOG.setSelected(false);
    	chckBxAusfuehrungBadDG.setSelected(false);
    	
    	// Setze diejenigen auf true, die in ausgewaehlteSw vorkommen 
    	if (ausgewaehlteSw == null) {
    		return;
    	}
    	for (int sw: ausgewaehlteSw) {
    		if (sw < 200 || sw >= 300) continue;
    		switch (sw) {
    			case 201:
    				chckBxWandKueche.setSelected(true);
    				break;
    			case 202:
    				chckBxTuerKueche.setSelected(true);
    				break;
    			case 203:
    				chckBxGrossesZimmerOG.setSelected(true);
    				break;
    			case 204:
    				chckBxTreppenraumDG.setSelected(true);
    				break;
    			case 205:
    				chckBxVorrichtungBadOG.setSelected(true);
    				break;
    			case 206:
    				chckBxAusfuehrungBadDG.setSelected(true);
    				break;
    			default:
    				System.out.println("Konnte ID " + sw 
    						+ " keiner Sonderwunsch-Checkbox fuer Grundriss-Varianten zuordnen");
    		}
    	}
    }
    
 	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
    @Override
  	protected void berechneUndZeigePreisSonderwuensche(){
		// Preislogik ist (laut Aufgabenstand) noch ein eigener Task.
		// Hier nur Platzhalter, damit der Button keine Exception wirft.
		System.out.println("Preisberechnung fuer Grundriss-Sonderwuensche ist noch nicht implementiert.");
  	}
  	
   	/* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
    @Override
  	protected void speichereSonderwuensche(){
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
