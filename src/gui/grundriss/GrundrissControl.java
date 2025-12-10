package gui.grundriss;

import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.KundeModel;
import business.kunde.SwKategorie;

/*
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Grundriss-Varianten
 * kontrolliert.
 */
public final class GrundrissControl {
	
	// das View-Objekt des Grundriss-Fensters
	private final GrundrissView grundrissView;
	private final KundeModel kundeModel;

	/**
	 * Fenster fuer die Sonderwuensche zum Grundriss.
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
	 * @param grundrissStage, Stage fuer das View-Objekt zu den Sonderwuenschen zum Grundriss
	 */
	public GrundrissControl(){  
	   	Stage stageGrundriss = new Stage();
    	stageGrundriss.initModality(Modality.APPLICATION_MODAL);
    	this.kundeModel = KundeModel.getInstance();
    	this.grundrissView = new GrundrissView(this, stageGrundriss);
	}
	    
	/**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView(){ 
		this.leseGrundrissSonderwuensche();
		this.grundrissView.oeffneGrundrissView();
	}

	public void leseGrundrissSonderwuensche(){
		int[][] swGrundriss = kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(SwKategorie.GRUNDRISS.id);
		if (swGrundriss != null)
			this.grundrissView.updateSwInView(swGrundriss);
    } 
	
	/*
	@Deprecated
	public void speichereSonderwuensche(int[] grundrissSw) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationSonderwuensche(grundrissSw)) {
            // Konflikt -> nicht speichern
            return;
        }
        
        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
                    grundrissSw,
                    SwKategorie.GRUNDRISS.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Grundriss-Varianten konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	}*/
	
	public void speichereSonderwuensche(int[][] grundrissSwMitAnzahl) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationSonderwuensche(grundrissSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }
        
        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
                    grundrissSwMitAnzahl,
                    SwKategorie.GRUNDRISS.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Grundriss-Varianten konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	}
	
	
	public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSw) {
		return true;
	}
	
	public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw, int[][] ausgewaehlteSwMitAnzahl){
		/* 
		 * - Prüfe, ob ein Sw in ausgewaehlteSw und ausgewaehlteSwMitAnzahl doppelt vorkommt.
		 * - Hole mit KundeModel.holeAusgewaehlteSwAusDbOhneKategorie() oder
		 * KundeModel.holeAusgewaehlteSwMitAnzahlAusDbOhneKategorie() die Auswahl der anderen
		 * Kategorien.
		 * - Prüfe, ob neue Auswahl mit der alten vereinbar ist und die Anzahlen erlaubt. 
		 */ 
		return true;
	}
}
