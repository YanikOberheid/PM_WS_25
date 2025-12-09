package gui.fliesen;

import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.KundeModel;
import business.kunde.SwKategorie;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*
 * Klasse, welche das Fenster mit den Sonderwünschen zu den Fliesen kontrolliert.
 */
public class FliesenControl {
    
    // Das View-Objekt des Fliesen-Fensters
    private FliesenView fliesenView; 
    private KundeModel kundeModel;

    /**
     * Erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
     * Fenster für die Sonderwünsche zu den Fliesen.
     */
    public FliesenControl() {  
        Stage stageFliesen = new Stage();
        stageFliesen.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = KundeModel.getInstance();
        this.fliesenView = new FliesenView(this, stageFliesen);
    }
        
    /**
     * Macht das FliesenView-Objekt sichtbar.
     */
    public void oeffneFliesenView(){
        this.leseFliesenSonderwuensche();
        this.fliesenView.oeffneFliesenView();
    }

    /**
     * Liest die Sonderwünsche aus der Datenbank und aktualisiert die View.
     */
    public void leseFliesenSonderwuensche(){
        int[][] swFliesen = kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(
        		SwKategorie.FLIESEN.id);
        if (swFliesen != null) {
            this.fliesenView.updateSwInView(swFliesen);
        }
    } 

    /**
     * [3] Speichert die ausgewählten Fliesen-Sonderwünsche in der Datenbank.
     * @param fliesenSw Array der IDs, die in der View ausgewählt wurden.
     */
    @Deprecated
    public void speichereSonderwuensche(int[] fliesenSw) {
    	// Erst Konstellation prüfen
    	if (!pruefeKonstellationFliesen(fliesenSw)) {
    		// Konflikt -> nicht speichern
    		return;
    	}
    	
        try {
        	this.kundeModel.speichereSonderwuenscheFuerKategorie(
        			fliesenSw,
        			SwKategorie.FLIESEN.id);

        } catch(Exception exc) {
            exc.printStackTrace();
            System.out.println("Fehler beim Speichern der Fliesen-Sonderwünsche.");
        }
    }
    
    public void speichereSonderwuensche(int[] fliesenSw, int[][] fliesenSwMitAnzahl) {
    	// Erst Konstellation prüfen
    	if (!pruefeKonstellationFliesen(fliesenSw, fliesenSwMitAnzahl)) {
    		// Konflikt -> nicht speichern
    		return;
    	}
    	
        try {
        	this.kundeModel.speichereSonderwuenscheFuerKategorie(
        			fliesenSw,
        			fliesenSwMitAnzahl,
        			SwKategorie.FLIESEN.id);

        } catch(Exception exc) {
            exc.printStackTrace();
            System.out.println("Fehler beim Speichern der Fliesen-Sonderwünsche.");
        }
    }
    
    @Deprecated
    public boolean pruefeKonstellationFliesen(int[] ausgewaehlteSw) {
    	return true; // Erst alles durchlassen. Implementiation ist Priorität [5]
    }
    
    public boolean pruefeKonstellationFliesen(int[] ausgewaehlteSw, int[][] ausgewaehlteSwMMitAnzahl) {
    	return true; // TODO
    }
}
