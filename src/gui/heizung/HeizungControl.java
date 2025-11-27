package gui.heizung;

import business.kunde.KundeModel;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Control-Klasse für die Sonderwünsche zu Heizungen.
 */
public class HeizungControl {

    private final HeizungView heizungView;
    private final KundeModel kundeModel;

    public HeizungControl(KundeModel kundeModel) {
        this.kundeModel = kundeModel;

        Stage stageHeizung = new Stage();
        stageHeizung.initModality(Modality.APPLICATION_MODAL);
        this.heizungView = new HeizungView(this, stageHeizung);
    }

    /** Beim Öffnen: gespeicherte Heizungs-Sonderwünsche anzeigen. */
    public void oeffneHeizungView() {
        leseHeizungsSonderwuensche();
        heizungView.oeffneHeizungView();
    }

    /** Holt aus dem Model alle Heizungs-SW (Kategorie 50) und gibt sie an die View. */
    public void leseHeizungsSonderwuensche() {
        int[] swHeizung = kundeModel.gibAusgewaehlteSwAusDb(KundeModel.KATEGORIE_HEIZUNG);
        if (swHeizung != null) {
            heizungView.updateHeizungCheckboxen(swHeizung);
        }
    }

    /** Speichert die neuen Heizungs-SW nur für Kategorie 50. */
    public void speichereSonderwuensche(int[] heizungsSw) {
        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
                    heizungsSw,
                    KundeModel.KATEGORIE_HEIZUNG
            );
        } catch (Exception e) {
            System.out.println("Heizungs-Sonderwünsche konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
    }
}
