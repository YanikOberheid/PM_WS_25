package gui.heizung;

import business.kunde.KundeModel;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.application.Platform;

public class HeizungControl {

    private final HeizungView heizungView;
    private final KundeModel kundeModel;

    // IDs aus der DB (Heizung)
    private static final int SW_STD_HEIZKOERPER   = 13;
    private static final int SW_GLATT_HEIZKOERPER = 14;
    private static final int SW_HANDTUCH          = 15;
    private static final int SW_FBH_OHNE_DG       = 16;
    private static final int SW_FBH_MIT_DG        = 17;

    public HeizungControl(KundeModel kundeModel) {
        this.kundeModel = kundeModel;

        Stage stageHeizung = new Stage();
        stageHeizung.initModality(Modality.APPLICATION_MODAL);
        this.heizungView = new HeizungView(this, stageHeizung);
    }

    public void oeffneHeizungView() {
        leseHeizungsSonderwuensche();
        heizungView.oeffneHeizungView();
    }

    public void leseHeizungsSonderwuensche() {
        int[] swHeizung = kundeModel.gibAusgewaehlteSwAusDb(KundeModel.KATEGORIE_HEIZUNG);
        if (swHeizung != null) {
            heizungView.updateHeizungCheckboxen(swHeizung);
        }
    }

    public void speichereSonderwuensche(int[] heizungsSw) {
        // Erst Konstellation prüfen
        if (!pruefeKonstellationHeizkoerper(heizungsSw)) {
            // Konflikt -> nicht speichern
            return;
        }

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

    /**
     * Prüft, ob die Kombination der Heizkörper-Sonderwünsche erlaubt ist.
     * - Gibt true zurück, wenn alles okay ist.
     * - Gibt false zurück, wenn ein Konflikt vorliegt.
     * 
     * Aktuell: Mock-Konflikt:
     *  -> Wenn sowohl "Fußbodenheizung ohne DG" als auch "mit DG" gewählt sind,
     *     wird ein Fehlerfenster mit "Dieser Test schlägt fehl." angezeigt.
     */
    public boolean pruefeKonstellationHeizkoerper(int[] ausgewaehlteSw) {
        if (ausgewaehlteSw == null || ausgewaehlteSw.length == 0) {
            return true; // keine Auswahl -> kein Konflikt
        }

        boolean hatFbhOhne = false;
        boolean hatFbhMit = false;

        for (int sw : ausgewaehlteSw) {
            if (sw == SW_FBH_OHNE_DG) {
                hatFbhOhne = true;
            } else if (sw == SW_FBH_MIT_DG) {
                hatFbhMit = true;
            }
        }

        // Mock-Konflikt: beide FBH-Varianten gleichzeitig
        if (hatFbhOhne && hatFbhMit) {
            zeigeKonfliktFenster(
                "Fehler bei der Auswahl der Heizungs-Sonderwünsche",
                "Dieser Test schlägt fehl."
            );
            return false;
        }

        // sonst aktuell noch alles erlaubt
        return true;
    }

    /** Zeigt ein Fehlerfenster; in Unit-Tests (ohne FX-Thread) wird nur geloggt. */
    private void zeigeKonfliktFenster(String header, String text) {
        if (Platform.isFxApplicationThread()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Konflikt");
            alert.setHeaderText(header);
            alert.setContentText(text);
            alert.showAndWait();
        } else {
            // In Tests ohne JavaFX-Thread vermeiden wir Fehler:
            System.out.println("Konflikt (ohne FX-UI): " + header + " - " + text);
        }
    }
}
