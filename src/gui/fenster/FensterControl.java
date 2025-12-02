package gui.fenster;

import java.util.Arrays;

import business.kunde.KundeModel;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        int[] ausgewaehlteSw = this.kundeModel.gibAusgewaehlteSwAusDb();
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
        if (this.pruefeKonstellationSonderwuensche(zuPruefendeSwKonstellation)) {
            try {
                this.kundeModel.updateAusgewaehlteSw(zuPruefendeSwKonstellation);
            } catch (Exception exc) {
                System.out.println("Neue Auswahl an Sonderwuenschen zu Fenstern und Außentueren "
                        + "konnte nicht gespeichert werden");
            }
        }
    }
  
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
        return true;
    }
}
