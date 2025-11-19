package gui.grundriss;

import javafx.scene.control.*;
import javafx.stage.Stage;
import gui.basis.BasisView;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu 
 * den Grundrissvarianten bereitstellt.
 */
public class GrundrissView extends BasisView{

 	// das Control-Objekt des Grundriss-Fensters
	private GrundrissControl grundrissControl;
   
    //---Anfang Attribute der grafischen Oberflaeche---
    private Label lblWandKueche    	     
        = new Label("Wand zur Abtrennung der Kueche von dem Essbereich");
    private TextField txtPreisWandKueche 	= new TextField();
    private Label lblWandKuecheEuro 		= new Label("Euro");
    private CheckBox chckBxWandKueche 		= new CheckBox();
    
    private CheckBox chckBxTuerKueche 		= new CheckBox(); // TODO: Absprache mit Yaman erforderlich
    private CheckBox chckBxGrossesZimmerOG 	= new CheckBox(); // TODO: Absprache mit Yaman erforderlich
    private CheckBox chckBxTreppenraumDG 	= new CheckBox(); // TODO: Absprache mit Yaman erforderlich
    private CheckBox chckBxVorrichtungBadOG	= new CheckBox(); // TODO: Absprache mit Yaman erforderlich
    private CheckBox chckBxAusfuehrungBadDG = new CheckBox(); // TODO: Absprache mit Yaman erforderlich
    //-------Ende Attribute der grafischen Oberflaeche-------
  
    /**
     * erzeugt ein GrundrissView-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param grundrissControl GrundrissControl, enthaelt das zugehoerige Control
     * @param grundrissStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public GrundrissView (GrundrissControl grundrissControl, Stage grundrissStage){
    	super(grundrissStage);
        this.grundrissControl = grundrissControl;
        grundrissStage.setTitle("Sonderw�nsche zu Grundriss-Varianten");
                
	    this.initKomponenten();
	    this.leseGrundrissSonderwuensche();
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
	public void oeffneGrundrissView(){ 
		super.oeffneBasisView();
	}
    
    private void leseGrundrissSonderwuensche(){
    	this.grundrissControl.leseGrundrissSonderwuensche();
    }
    
    protected void updateGrundrissCheckboxen(int[] ausgewaehlteSw) {
    	chckBxWandKueche.setSelected(false);
    	chckBxTuerKueche.setSelected(false);
    	chckBxGrossesZimmerOG.setSelected(false);
    	chckBxTreppenraumDG.setSelected(false);
    	chckBxVorrichtungBadOG.setSelected(false);
    	chckBxAusfuehrungBadDG.setSelected(false);
    	for (int sw: ausgewaehlteSw) {
    		if (sw < 200 || sw >= 300) continue;
    		switch (sw) {
    			case 201:
    				chckBxWandKueche.setSelected(true);
    			case 202:
    				chckBxTuerKueche.setSelected(true); // TODO: Absprache mit Yaman erforderlich
    			case 203:
    				chckBxGrossesZimmerOG.setSelected(true); // TODO: Absprache mit Yaman erforderlich
    			case 204:
    				chckBxTreppenraumDG.setSelected(true); // TODO: Absprache mit Yaman erforderlich
    			case 205:
    				chckBxVorrichtungBadOG.setSelected(true); // TODO: Absprache mit Yaman erforderlich
    			case 206:
    				chckBxAusfuehrungBadDG.setSelected(true); // TODO: Absprache mit Yaman erforderlich
    			default:
    				System.out.println("Konnte ID " + sw 
    						+ " keiner Sonderwunsch-Checkbox für Grunriss-Varianten zuordnen");
    		}
    	}
    }
    
 	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
  	protected void berechneUndZeigePreisSonderwuensche(){
  		// Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
  		// aus dem Control aufgerufen, dann der Preis berechnet.
  	}
  	
   	/* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
  	protected void speichereSonderwuensche(){
 		// Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
  		// aus dem Control aufgerufen, dann die Sonderwuensche gespeichert.
  	}
  	
 	
 }


