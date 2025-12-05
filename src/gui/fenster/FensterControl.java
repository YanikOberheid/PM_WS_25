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
    
 // Feste Preiszuordnung für Fenster-/Außentüren-IDs (301–309)
    private static final Map<Integer, Integer> PREISE = Map.of(
        301, 590,   // 3.1 Schiebetüren EG
        302, 590,   // 3.2 Schiebetüren DG
        303, 690,   // 3.3 Einbruchschutz Haustür
        304, 190,   // 3.4 Vorbereitung Rollläden EG
        305, 190,   // 3.5 Vorbereitung Rollläden OG
        306, 190,   // 3.6 Vorbereitung Rollläden DG
        307, 990,   // 3.7 Elektrische Rollläden EG
        308, 990,   // 3.8 Elektrische Rollläden OG
        309, 990    // 3.9 Elektrische Rollläden DG
    );

 
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
        int[] ausgewaehlteSw = this.kundeModel.gibAusgewaehlteSwAusDb(
        		 SwKategorie.FENSTER_AUSSENTUEREN.id
        		
        		);
        if (ausgewaehlteSw != null && ausgewaehlteSw.length > 0) {
            this.fensterView.updateSwCheckboxen(ausgewaehlteSw);
        }
    }

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
    
    public int berechnePreis(int[] ids) {
        if (ids == null || ids.length == 0) return 0;
        int summe = 0;
        for (int id : ids) {
            Integer p = PREISE.get(id);
            if (p != null) summe += p;
        }
        return summe;
    }
    
  
    public boolean pruefeKonstellationFensterAussentueren(int[] ids) {
        return true;
    }
}
