package gui.innentueren;

import business.kunde.KundeModel;
import business.kunde.SwKategorie;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Control für die Innentüren-GUI.
 * Task-Scope: Nur Öffnen/Schließen und Kundenvorprüfung.
 */
public class InnentuerenControl {

    private final InnentuerenView innentuerenView;
    private final KundeModel kundeModel;

    public InnentuerenControl() {
        Stage stageInnentueren = new Stage();
        stageInnentueren.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = KundeModel.getInstance();
        this.innentuerenView = new InnentuerenView(this, stageInnentueren);
    }
    
    public void oeffneInnenturenView() {
        leseInnentuerenSonderwuensche();
        innentuerenView.oeffneInnentuerenView();
    }

    public void leseInnentuerenSonderwuensche() {
        int[] swInnentueren = kundeModel.gibAusgewaehlteSwAusDb(SwKategorie.INNENTUEREN.id);
        if (swInnentueren != null)
            innentuerenView.updateSwCheckboxen(swInnentueren);
    }
    
    // Entsprechende Task: Auswahl für die Kategorie „Innentüren“ persistieren
    public void speichereSonderwuensche(int[] innentuerenSw) {
        try {
            // Speichert ausschließlich für die Zielkategorie, ersetzt dort die Auswahl
            kundeModel.speichereSonderwuenscheFuerKategorie(
                innentuerenSw, 
                SwKategorie.INNENTUEREN.id
            );
            innentuerenView.zeigeInfo("Gespeichert", "Innentüren-Sonderwünsche wurden gespeichert.");
        } catch (Exception e) {
            e.printStackTrace();
            innentuerenView.zeigeFehler("Fehler", "Speichern der Innentüren-Sonderwünsche ist fehlgeschlagen.");
        }
    }
    
    // Spätere Implementierung
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
        return true;
    }
}
