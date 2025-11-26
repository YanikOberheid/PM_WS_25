package gui.innentueren;

import business.kunde.KundeModel;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Control für die Innentüren-GUI.
 * Task-Scope: Nur Öffnen/Schließen und Kundenvorprüfung.
 */
public class InnentuerenControl {

    private final KundeModel model;
    private final InnentuerenView view;

    public InnentuerenControl(KundeModel model) {
        this.model = model;
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        this.view = new InnentuerenView(this, stage);
    }

    public void oeffneView() {
        if (model.getKunde() == null) {
            new Alert(Alert.AlertType.WARNING,
                "Bitte zuerst im Hauptfenster einen Kunden (Hausnummer) auswählen oder anlegen."
            ).showAndWait();
            return;
        }
        view.oeffne();
    }
}
