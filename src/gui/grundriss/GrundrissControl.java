package gui.grundriss;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Vector;

import business.kunde.KundeModel;
import business.kunde.Sw;
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
	}
	
	@Deprecated
	public void speichereSonderwuensche(int[] grundrissSw, int[][] grundrissSwMitAnzahl) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationSonderwuensche(grundrissSw, grundrissSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }
        
        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
                    grundrissSw,
                    grundrissSwMitAnzahl,
                    SwKategorie.GRUNDRISS.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Grundriss-Varianten konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	}
	
	// Aktuellste Version
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
	
	@Deprecated
	public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
		return true;
	}
	
	@Deprecated
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
	
	// Aktuellste Version
	public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl){
		// Prüfe, ob DG vorhanden ist
		boolean hausHatDG;
		try {
			hausHatDG = kundeModel.hatKundeDG();
		} catch (Exception e) {
			grundrissView.zeigeKonfliktFenster("Fehler beim Prüfen des Haustyps",
					"Es konnte nicht herausgefunden werden, ob das Haus ein Dachgeschoss hat");
			return false;
		}
		
		/* int[][] andereSwMitAnzahl = kundeModel.holeAusgewaehlteSwMitAnzahlAusDbOhneKategorie(
			SwKategorie.GRUNDRISS.id);*/
		// Grundriss muss nicht mit Sonderwünschen aus anderen Kategorien abgleichen
		
		String text = "";
		
		// Grundriss-Varianten haben alle Anzahl 0 oder 1
		Vector<Integer> swOhneAnzahl = new Vector<Integer>();
		for (int[] swMitAnzahl: ausgewaehlteSwMitAnzahl)
			if (swMitAnzahl[1] == 1)
				swOhneAnzahl.add(swMitAnzahl[0]);
		
		// 2.2 nur wenn 2.1; 2.4 nur wenn DG; 2.5 nur wenn DG; 2.6 nur wenn 2.5
		// aus Trello > Backlog > "[Info] Konstellation an Sonderwünschen"
		
		// Durch ausgewaehlteSwMitAnzahl iterieren
		for (int swId: swOhneAnzahl) {
			switch (Sw.findeMitId(swId)) { // Enum über id an Index 0 holen
				case TUER_KUECHE: // 2.2 nur wenn 2.1
					// Pruefe, ob 2.1 vorhanden
					if (!swOhneAnzahl.contains(Sw.WAND_KUECHE.id))
						text += "" + Sw.idZuText(Sw.TUER_KUECHE) + " kann nicht ohne " +
								Sw.idZuText(Sw.WAND_KUECHE) + "ausgewählt werden\n";
					break;
				case TREPPENRAUM_DG: // 2.4 nur wenn DG
					if (!hausHatDG)
						text += "" + Sw.idZuText(Sw.TREPPENRAUM_DG) + " kann nicht ohne Dachgeschoss ausgewählt werden";
					break;
				case VORRICHTUNG_BAD_DG: // 2.5 nur wenn DG
					if (!hausHatDG)
						text += "" + Sw.idZuText(Sw.TREPPENRAUM_DG) + " kann nicht ohne Dachgeschoss ausgewählt werden";
					break;
				case AUSFUEHRUNG_BAD_DG: // 2.6 nur wenn 2.5
					// Pruefe, ob 2.5 vorhanden
					if (!swOhneAnzahl.contains(Sw.VORRICHTUNG_BAD_DG.id))
						text += "" + Sw.idZuText(Sw.AUSFUEHRUNG_BAD_DG) + " kann nicht ohne " +
								Sw.idZuText(Sw.VORRICHTUNG_BAD_DG) + "ausgewählt werden\n";
					break;
				default:
					System.out.println("Sonderwunsch mit ID " + swId
							+ " hat keinen Fall beim Prüfen der Konstellation (Grundriss-Varianten)");
					break;
			}
		}
		
		// Wenn mindestens ein Verstoß, zeige alle Verstöße und gebe false zurück
		if (!text.isEmpty()) {
			grundrissView.zeigeKonfliktFenster("Konstellation nicht erlaubt", text);
			return false;
		}
		return true;
	}
}
