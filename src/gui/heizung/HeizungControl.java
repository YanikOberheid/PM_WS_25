package gui.heizung;

import java.util.Arrays;

import business.kunde.KundeModel;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Control-Klasse für die Sonderwünsche zu Heizungen.
 */
public final class HeizungControl {

    private HeizungView heizungView;
    private KundeModel kundeModel;

    /**
     * Erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt
     * zum Fenster für die Sonderwünsche zu Heizungen.
     */
    public HeizungControl(KundeModel kundeModel) {
        Stage stageHeizung = new Stage();
        stageHeizung.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = kundeModel;
        this.heizungView = new HeizungView(this, stageHeizung);
    }

    /**
     * macht das HeizungView-Objekt sichtbar.
     */
    public void oeffneHeizungView() {
    	this.leseHeizungsSonderwuensche();
        this.heizungView.oeffneHeizungView();
    }

    /**
     * liest alle aktuell gespeicherten Sonderwünsche aus der DB und
     * setzt die Checkboxen in der View passend.
     */
    public void leseHeizungsSonderwuensche() {
        int[] ausgewaehlteSw = kundeModel.gibAusgewaehlteSwAusDb();
        if (ausgewaehlteSw != null && ausgewaehlteSw.length > 0) {
            this.heizungView.updateHeizungCheckboxen(ausgewaehlteSw);
        }
    }

    /**
     * speichert die neuen Heizungs-Sonderwünsche gemeinsam mit
     * den bereits vorhandenen anderen Sonderwünschen.
     */
    public void speichereSonderwuensche(int[] heizungSw) {
        // Hole alle aktuell ausgewählten Sonderwünsche aus der DB
        int[] ausgewaehlteSw = this.kundeModel.gibAusgewaehlteSwAusDb();

        if (ausgewaehlteSw == null) {
            ausgewaehlteSw = new int[0];
        }

        // *** WICHTIG: Bereich der IDs für Heizungswünsche anpassen ***
        // Im Beispiel: 501–505 gehören zu "Heizung".
        // Alles aus diesem Bereich wird zuerst herausgefiltert.
        ausgewaehlteSw = Arrays.stream(ausgewaehlteSw)
                .filter(sw -> sw < 500 || sw >= 600)
                .toArray();

        // Alte (ohne Heizung) + neue Heizungswünsche zusammenführen
        int[] neueKonstellation = new int[ausgewaehlteSw.length + heizungSw.length];
        System.arraycopy(ausgewaehlteSw, 0, neueKonstellation, 0, ausgewaehlteSw.length);
        System.arraycopy(heizungSw, 0, neueKonstellation, ausgewaehlteSw.length, heizungSw.length);

        // Prüfen – aktuell Dummy wie bei Grundriss
        if (this.pruefeKonstellationSonderwuensche(neueKonstellation)) {
            try {
                this.kundeModel.updateAusgewaehlteSw(neueKonstellation);
            } catch (Exception exc) {
                System.out.println("Neue Auswahl an Heizungs-Sonderwünschen "
                        + "konnte nicht gespeichert werden");
                exc.printStackTrace();
            }
        }
    }

    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
        // Hier könnte man wieder Konfliktlogik einbauen (z.B. darf 5.4 und 5.5 nicht gleichzeitig).
        return true;
    }
}