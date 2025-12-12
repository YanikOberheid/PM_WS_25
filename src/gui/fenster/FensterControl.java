package gui.fenster;

import java.util.Arrays;
import java.util.Map;

import business.kunde.KundeModel;
import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.SwKategorie;

/*
 * Klasse, welche das Fenster mit den Sonderwuenschen zu Fenstern und
 * Außentueren kontrolliert.
 */
public final class FensterControl {

    private FensterView fensterView;
    private KundeModel kundeModel;
 
    public FensterControl() {
        Stage stageFenster = new Stage();
        stageFenster.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = KundeModel.getInstance();
        this.fensterView = new FensterView(this, stageFenster);
    }
  
    public void oeffneFensterView() {
        this.leseFensterSonderwuensche();
        this.fensterView.oeffneFensterView();
    }

    public void leseFensterSonderwuensche() {
        int[][] ausgewaehlteSw = kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(
        		SwKategorie.FENSTER_AUSSENTUEREN.id);
        if (ausgewaehlteSw != null) {
            this.fensterView.updateSwInView(ausgewaehlteSw);
        }
    }
    
    @Deprecated
    public void speichereSonderwuensche(int[] fensterSw) {
        int[] ausgewaehlteSw = this.kundeModel.gibAusgewaehlteSwAusDb();
        if (ausgewaehlteSw == null) {
            ausgewaehlteSw = new int[0];
        }

        // Sonderwuensche zu Fenstern und Außentueren aus der alten Auswahl entfernen
        // Fenster-Bereich: 300–399
        ausgewaehlteSw = Arrays.stream(ausgewaehlteSw)
                .filter(sw -> sw < 300 || sw >= 400)
                .toArray();

        // mit neuer Auswahl zusammenführen
        int[] zuPruefendeSwKonstellation = new int[ausgewaehlteSw.length + fensterSw.length];
        for (int i = 0; i < ausgewaehlteSw.length; i++) {
            zuPruefendeSwKonstellation[i] = ausgewaehlteSw[i];
        }
        for (int i = 0; i < fensterSw.length; i++) {
            zuPruefendeSwKonstellation[ausgewaehlteSw.length + i] = fensterSw[i];
        }

        // Konstellation pruefen (Logik kommt später, hier immer true)
        if (this.pruefeKonstellationFensterAussentueren(zuPruefendeSwKonstellation)) {
            try {
                this.kundeModel.updateAusgewaehlteSw(zuPruefendeSwKonstellation);
            } catch (Exception exc) {
                System.out.println("Neue Auswahl an Sonderwuenschen zu Fenstern und Außentueren "
                        + "konnte nicht gespeichert werden");
            }
        }
    }
    @Deprecated
    public void speichereSonderwuensche(int[] fensterSw, int[][] fensterSwMitAnzahl) {
    	// Erst Konstellation prüfen
        if (!pruefeKonstellationFensterAussentueren(fensterSw, fensterSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }

        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
            		fensterSw,
            		fensterSwMitAnzahl,
                    SwKategorie.FENSTER_AUSSENTUEREN.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Heizungen konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
    }
    
    // Aktuellste Version
 	public void speichereSonderwuensche(int[][] fensterSwMitAnzahl) {
 		// Erst Konstellation prüfen
         if (!pruefeKonstellationFensterAussentueren(fensterSwMitAnzahl)) {
             // Konflikt -> nicht speichern
             return;
         }
         
         try {
             kundeModel.speichereSonderwuenscheFuerKategorie(
            		 fensterSwMitAnzahl,
                     SwKategorie.FENSTER_AUSSENTUEREN.id
             );
         } catch (Exception e) {
             System.out.println("Sonderwünsche zu Fenster und Aussentueren-Varianten konnten nicht gespeichert werden.");
             e.printStackTrace();
         }
 	}
    
    @Deprecated
    public boolean pruefeKonstellationFensterAussentueren(int[] ids) {
        return true;
    }
    @Deprecated
    public boolean pruefeKonstellationFensterAussentueren(int[] ausgewaehlteSw,
    		int[][] ausgewaehlteSwMitAnzahl) {
        return true; // TODO
    }
    
    // Aktuellste Version
 	public boolean pruefeKonstellationFensterAussentueren(int[][] ausgewaehlteSwMitAnzahl){
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
