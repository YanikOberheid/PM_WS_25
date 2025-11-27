package gui.grundriss;

import java.util.ArrayList;
import java.util.List;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu 
 * den Grundrissvarianten bereitstellt.
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
        grundrissStage.setTitle("Sonderw�nsche zu Grundriss-Varianten");
                
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
	public void oeffneGrundrissView(){ 
		super.oeffneBasisView();
	}
    
    private void leseGrundrissSonderwuensche(){
    	this.grundrissControl.leseGrundrissSonderwuensche();
    }
    
 	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
  	/*protected void berechneUndZeigePreisSonderwuensche(){
  		// Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
  		// aus dem Control aufgerufen, dann der Preis berechnet.
  	}*/
	
	public void berechneUndZeigePreisSonderwuensche() {

    // 1. Alle ausgewählten Sonderwünsche sammeln
    List<Integer> ausgewaehltListe = new ArrayList<>();

    for (int i = 0; i < chckBxPlatzhalter.length; i++) {
        if (chckBxPlatzhalter[i].isSelected()) {
            ausgewaehltListe.add(i);
        }
    }

    // In Array umwandeln wie gefordert
    int[] ausgewaehlteSw = ausgewaehltListe.stream().mapToInt(Integer::intValue).toArray();

    // 2. Konstellation prüfen
    boolean erlaubt = grundrissControl.pruefeKonstellationSonderwuensche(ausgewaehlteSw);

    if (!erlaubt) {
        System.out.println("Die Kombination der Sonderwünsche ist nicht erlaubt.");
        return;
    }

    // 3. Preis berechnen
    double gesamtpreis = 0.0;

    for (int i : ausgewaehlteSw) {
        try {
            double preis = Double.parseDouble(sonderwuensche[i][1]);
            gesamtpreis += preis;
        } catch (NumberFormatException e) {
            System.err.println("Ungültiger Preis für Sonderwunsch " + i);
        }
    }

    // 4. GUI-Fenster anzeigen
    //zeigePreisFenster(gesamtpreis);
}

  	
   	/* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
  	protected void speichereSonderwuensche(){
 		// Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
  		// aus dem Control aufgerufen, dann die Sonderwuensche gespeichert.
  	}
  	
 	
 }


