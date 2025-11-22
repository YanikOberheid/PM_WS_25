package gui.grundriss;

import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Vector;

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
    
    private CheckBox chckBxTuerKueche 		= new CheckBox();
    private CheckBox chckBxGrossesZimmerOG 	= new CheckBox();
    private CheckBox chckBxTreppenraumDG 	= new CheckBox();
    private CheckBox chckBxVorrichtungBadOG	= new CheckBox();
    private CheckBox chckBxAusfuehrungBadDG = new CheckBox();
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
    	// Setze alle auf false
    	chckBxWandKueche.setSelected(false);
    	chckBxTuerKueche.setSelected(false);
    	chckBxGrossesZimmerOG.setSelected(false);
    	chckBxTreppenraumDG.setSelected(false);
    	chckBxVorrichtungBadOG.setSelected(false);
    	chckBxAusfuehrungBadDG.setSelected(false);
    	
    	// Setze diejenigen auf true, die in ausgewaehlteSw vorkommen 
    	for (int sw: ausgewaehlteSw) {
    		if (sw < 200 || sw >= 300) continue;
    		switch (sw) {
    			case 201:
    				chckBxWandKueche.setSelected(true);
    			case 202:
    				chckBxTuerKueche.setSelected(true);
    			case 203:
    				chckBxGrossesZimmerOG.setSelected(true);
    			case 204:
    				chckBxTreppenraumDG.setSelected(true);
    			case 205:
    				chckBxVorrichtungBadOG.setSelected(true);
    			case 206:
    				chckBxAusfuehrungBadDG.setSelected(true);
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
  		// Beispielhaft: Wir simulieren, dass ein Konfliktfall vorliegt.
  		// Normalerweise: ausgewaehlteSonderwuensche auslesen und an Control geben.
  		int[] ausgewaehlteSw = new int[] {1}; // Platzhalter, normalerweise aus UI gelesen

  		boolean konstellationOk = grundrissControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw);
  		if (!konstellationOk) {
  			// Mock-Konflikt: Fenster anzeigen
  			Alert alert = new Alert(Alert.AlertType.ERROR);
  			alert.setTitle("Konflikt");
  			alert.setHeaderText("Fehler bei der Auswahl der Sonderwünsche");
  			alert.setContentText("Dieser Test schlägt fehl.");
  			alert.showAndWait();
  			return;
  		}

  		// Annahme: Preisberechnung, falls keine Konflikte (hier: Platzhalterwert)
  		double preis = 0.0;
  		for (int sw : ausgewaehlteSw) {
  			// Beispielsweise könnte jeder Sonderwunsch 100 Euro kosten (nur als Demo)
  			// sw wird hier benutzt, um zu zeigen, dass er individuell getestet werden kann
  			// Man könnte z.B. abhängig von sw verschiedene Preise berechnen
  			if (sw == 1) {
  				preis += 150.0; // Für sw == 1, z.B. Wand zur Küche, 150 Euro
  			} else {
  				preis += 100.0; // Standardpreis für andere Sonderwünsche
  			}
  		}
  		txtPreisWandKueche.setText(String.format("%.2f", preis));
  	}
  	
   	/* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
  	protected void speichereSonderwuensche(){
 		// Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
  		// aus dem Control aufgerufen, dann die Sonderwuensche gespeichert.
  		
  		// Sammle Sonderwunsch-IDs angekreuzter Checkboxen
  		Vector<Integer> v = new Vector<Integer>();
  		if (chckBxWandKueche.isSelected())
  			v.add(201);
  		if (chckBxTuerKueche.isSelected())
  			v.add(202);
  		if (chckBxGrossesZimmerOG.isSelected())
  			v.add(203);
  		if (chckBxTreppenraumDG.isSelected())
  			v.add(204);
  		if (chckBxVorrichtungBadOG.isSelected())
  			v.add(205);
  		if (chckBxAusfuehrungBadDG.isSelected())
  			v.add(206);
  		
  		// Kopiere in int[]
  		int[] grundrissSw = new int[v.size()];
  		for (int i = 0; i < v.size(); i++)
  			grundrissSw[i] = v.get(i);
  		
  		// Speichere Sonderwünsche (Prüfung in Control, da das Feld kundeModel private ist)
  		this.grundrissControl.speichereSonderwuensche(grundrissSw);
  	}
 }


