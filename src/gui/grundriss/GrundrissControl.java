package gui.grundriss;

import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.KundeModel;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Grundriss-Varianten
 * kontrolliert.
 */
public final class GrundrissControl {
	
	// das View-Objekt des Grundriss-Fensters
	private GrundrissView grundrissView;
	private KundeModel kundeModel;

	/**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
	 * Fenster fuer die Sonderwuensche zum Grundriss.
	 * @param grundrissStage, Stage fuer das View-Objekt zu den Sonderwuenschen zum Grundriss
	 */
	public GrundrissControl(){  
	   	Stage stageGrundriss = new Stage();
    	stageGrundriss.initModality(Modality.APPLICATION_MODAL);
    	this.kundeModel = KundeModel.getInstance();
    	this.grundrissView = new GrundrissView(this, stageGrundriss);
    	// Erstellen von grundrissView updatet indirekt Checkboxen, denn
    	// -> grundrissView ruft seinen Konstruktor auf
	    // -> grundrissView ruft seine leseGrundrissSonderwuensche auf
	    // -> grundrissView ruft grundrissControl's leseGrundrissSonderwuensche auf
	    // -> grundrissControl ruft grundrissView's updateCheckboxen auf (Checkboxen sind private)
	}
	
	/**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView(){ 
		this.leseGrundrissSonderwuensche();
		this.grundrissView.oeffneGrundrissView();
	}

	public void leseGrundrissSonderwuensche(){
		int[] ausgewaehlteSw = kundeModel.gibAusgewaehlteSwAusDb();
		if (ausgewaehlteSw != null && ausgewaehlteSw.length > 0)
			this.grundrissView.updateGrundrissCheckboxen(ausgewaehlteSw);
    } 
	
	public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw){
		return true;
	}
}
