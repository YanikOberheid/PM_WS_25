package gui.fliesen;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Vector;

import business.kunde.KundeModel;
import business.kunde.Sw;
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
    @Deprecated
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
    
    // Aktuellste Version
 	public void speichereSonderwuensche(int[][] fliesenSwMitAnzahl) {
 		// Erst Konstellation prüfen
         if (!pruefeKonstellationSonderwuensche(fliesenSwMitAnzahl)) {
             // Konflikt -> nicht speichern
             return;
         }
         
         try {
             kundeModel.speichereSonderwuenscheFuerKategorie(
            		 fliesenSwMitAnzahl,
                     SwKategorie.FLIESEN.id
             );
         } catch (Exception e) {
             System.out.println("Sonderwünsche zu Fliesen-Varianten konnten nicht gespeichert werden.");
             e.printStackTrace();
         }
 	}
 	
    
    @Deprecated
    public boolean pruefeKonstellationFliesen(int[] ausgewaehlteSw) {
    	return true; // Erst alles durchlassen. Implementiation ist Priorität [5]
    }
    
    @Deprecated
    public boolean pruefeKonstellationFliesen(int[] ausgewaehlteSw, int[][] ausgewaehlteSwMMitAnzahl) {
    	return true; // TODO
    }
    
 // Aktuellste Version
 	public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl){
 		// Prüfe, ob DG vorhanden ist
 		boolean hausHatDG;
 		try {
 			hausHatDG = kundeModel.hatKundeDG();
 		} catch (Exception e) {
 			fliesenView.zeigeKonfliktFenster("Fehler beim Prüfen des Haustyps",
					"Es konnte nicht herausgefunden werden, ob das Haus ein Dachgeschoss hat");
 			return false;
 		}
 		
 		int[][] andereSwMitAnzahl = kundeModel.holeAusgewaehlteSwMitAnzahlAusDbOhneKategorie(
 			SwKategorie.FLIESEN.id);
 		
 		String text = "";
 		
 		// Fliesen haben alle Anzahl 0 oder 1
 		Vector<Integer> swOhneAnzahl = new Vector<Integer>();
 		for (int[] swMitAnzahl: ausgewaehlteSwMitAnzahl)
 			if (swMitAnzahl[1] == 1)
 				swOhneAnzahl.add(swMitAnzahl[0]);
 		// 2.6 hat Anzahl 0 oder 1
 		for (int[] swMitAnzahl: andereSwMitAnzahl)
 			if (swMitAnzahl[1] == 1 && swMitAnzahl[0] == Sw.AUSFUEHRUNG_BAD_DG.id)
 				swOhneAnzahl.add(swMitAnzahl[0]);
 		
 		// 7.3 nur wenn nicht 7.1; 7.4 nur wenn nicht 7.2; 7.5 nur wenn DG und 2.6; 7.6 nur wenn 7.5
 		// aus Trello > Backlog > "[Info] Konstellation an Sonderwünschen"
 		
 		// Durch ausgewaehlteSwMitAnzahl iterieren
 		for (int swId: swOhneAnzahl) {
 			switch (Sw.findeMitId(swId)) { // Enum über id an Index 0 holen
 				case F_KUECHE_EG_GROSS: // 7.3 nur wenn nicht 7.1
 					// Pruefe, ob 7.1 vorhanden
 					if (swOhneAnzahl.contains(Sw.F_KUECHE_EG_OHNE.id))
 						text += "" + Sw.idZuText(Sw.F_KUECHE_EG_GROSS) + " kann nicht mit " +
 								Sw.idZuText(Sw.F_KUECHE_EG_OHNE) + "ausgewählt werden\n";
 					break;
 				case F_BAD_OG_GROSS: // 7.4 nur wenn nicht 7.2
 					// Prüefe, ob 7.2 vorhanden
 					if (swOhneAnzahl.contains(Sw.F_BAD_OG_OHNE.id))
 						text += "" + Sw.idZuText(Sw.F_BAD_OG_GROSS) + " kann nicht mit " +
 								Sw.idZuText(Sw.F_BAD_OG_OHNE) + "ausgewählt werden\n";
 					break;
 				case F_BAD_DG: // 7.5 nur wenn DG und 2.6
 					if (!hausHatDG)
 						text += "" + Sw.idZuText(Sw.TREPPENRAUM_DG) + " kann nicht ohne Dachgeschoss ausgewählt werden";
 					// Prüefe, ob 2.6 vorhanden
 					if (!swOhneAnzahl.contains(Sw.AUSFUEHRUNG_BAD_DG.id))
 						text += "" + Sw.idZuText(Sw.F_BAD_DG) + " kann nicht ohne " +
 								Sw.idZuText(Sw.AUSFUEHRUNG_BAD_DG) + "ausgewählt werden\n";
 					break;
 				case F_BAD_DG_GROSS: // 7.6 nur wenn 7.5
 					// Pruefe, ob 7.5 vorhanden
 					if (!swOhneAnzahl.contains(Sw.F_BAD_DG.id))
 						text += "" + Sw.idZuText(Sw.F_BAD_DG_GROSS) + " kann nicht ohne " +
 								Sw.idZuText(Sw.F_BAD_DG) + "ausgewählt werden\n";
 					break;
 				default:
 					System.out.println("Sonderwunsch mit ID " + swId
 							+ " hat keinen Fall beim Prüfen der Konstellation (Fliesen)");
 					break;
 			}
 		}
 		
 		// Wenn mindestens ein Verstoß, zeige alle Verstöße und gebe false zurück
 		if (!text.isEmpty()) {
 			fliesenView.zeigeKonfliktFenster("Konstellation nicht erlaubt", text);
 			return false;
 		}
 		return true;
 	}
}
