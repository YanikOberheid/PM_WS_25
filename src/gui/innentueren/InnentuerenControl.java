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
    
    
 // --- Ergänzung am Kopf der Klasse ---
    private static final int KATEGORIE_INNENTUEREN = 40; // <-- ggf. auf echte Kategorie-ID anpassen
    // Echte idSonderwunsch-Werte aus eurer Tabelle eintragen:
 // HIER die echten idSonderwunsch-Werte aus der DB eintragen
    public static final int SW_4_1_KLARGLAS  = 18; // <-- aus DB ablesen
    public static final int SW_4_2_MILCHGLAS = 19; // <-- aus DB ablesen
    public static final int SW_4_3_GARAGE    = 20; // <-- aus DB ablesen
    
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
    
 // --- Ergänzung innerhalb der Klasse ---
 // Lädt gespeicherte IDs dieser Kategorie und setzt die View.
 public void ladeAuswahl() {
     try {
         int[] ids = model.gibAusgewaehlteSwAusDb(KATEGORIE_INNENTUEREN);
         view.updateInnentuerenAuswahl(ids); // kleine View-API, s.u.
     } catch (Exception e) {
         e.printStackTrace();
         view.zeigeFehler("Fehler", "Innentüren-Auswahl konnte nicht geladen werden.");
     }
 }

 // Speichert aktuelle Auswahl (IDs) für diese Kategorie.
 public void speichereSonderwuensche(int[] ids) {
     try {
         model.speichereSonderwuenscheFuerKategorie(ids, KATEGORIE_INNENTUEREN);
         view.zeigeInfo("Gespeichert", "Innentüren-Sonderwünsche wurden gespeichert.");
     } catch (Exception e) {
         e.printStackTrace();
         view.zeigeFehler("Fehler", "Speichern der Innentüren-Sonderwünsche ist fehlgeschlagen.");
     }
 }
}