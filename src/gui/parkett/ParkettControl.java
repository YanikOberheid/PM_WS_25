package gui.parkett;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.KundeModel;
import business.kunde.Sw;
import business.kunde.SwKategorie;

/**
 * Controller-Klasse für die Parkett-Sonderwünsche.
 * Vermittelt zwischen der ParkettView und dem KundeModel.
 */
public final class ParkettControl {

    private final ParkettView parkettView;
    private final KundeModel kundeModel;

    public ParkettControl() {
        // Initialisierung der Stage als modales Fenster (blockiert das Hauptfenster, bis es geschlossen wird)
        Stage stageParkett = new Stage();
        stageParkett.initModality(Modality.APPLICATION_MODAL);
        
        // Zugriff auf das zentrale Datenmodel (Singleton)
        this.kundeModel = KundeModel.getInstance();
        this.parkettView = new ParkettView(this, stageParkett);
    }

    /**
     * Öffnet die Parkett-View, aber nur, wenn zuvor ein Kunde ausgewählt wurde.
     */
    public void oeffneParkettView() {
        // Prüfung: Ist überhaupt ein Kunde im Model gesetzt? 
        // Falls nicht, verhindern wir den Absturz und zeigen einen Hinweis.
        if (this.kundeModel.getKunde() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Kein Kunde ausgewählt");
            alert.setHeaderText(null);
            alert.setContentText("Bitte wählen Sie zuerst einen Kunden aus.");
            alert.showAndWait();
            return;
        }
        
        // Daten aus der DB laden, bevor das Fenster angezeigt wird
        leseParkettSonderwuensche();
        this.parkettView.oeffneParkettView();
    }

    /**
     * Liest die vorhandenen Sonderwünsche speziell für die Kategorie "Parkett" aus der Datenbank.
     */
    public void leseParkettSonderwuensche() {
        // Wir nutzen die ID der Enum-Konstante PARKETT (ID: 80), um nur diese Kategorie zu laden.
        // Siehe business.kunde.SwKategorie
        int[] swIds = this.kundeModel.gibAusgewaehlteSwAusDb(SwKategorie.PARKETT.id);
        
        // Die View aktualisieren: Haken bei den geladenen IDs setzen
        this.parkettView.updateSwCheckboxen(swIds);
        
        // Preis direkt neu berechnen, damit die Anzeige beim Öffnen stimmt
        this.parkettView.berechneUndZeigePreisSonderwuensche();
    }

    /**
     * Speichert die ausgewählten Parkett-Optionen in die Datenbank.
     * @param parkettSw Array mit den IDs der ausgewählten Checkboxen
     */
    public void speichereSonderwuensche(int[] parkettSw) {
        try {
            // Ruft die Methode auf, die nur diese Kategorie (ID 80) überschreibt, 
            // ohne andere Sonderwünsche (z.B. Grundriss) zu löschen.
            this.kundeModel.speichereSonderwuenscheFuerKategorie(parkettSw, SwKategorie.PARKETT.id);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Fehler beim Speichern: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Validierungslogik: Prüft auf widersprüchliche Auswahlen.
     * Beispiel: Man kann nicht "Landhausdielen" UND "Stäbchenparkett" im selben Raum haben.
     * * @param ausgewaehlteSw Array der IDs, die der Benutzer ausgewählt hat
     * @return true, wenn alles okay ist; false, wenn ein Widerspruch vorliegt
     */
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
        // Hilfsvariablen zur besseren Lesbarkeit
        // Prüfen, ob bestimmte IDs im Auswahl-Array enthalten sind
        boolean hatLhdEssEg = contains(ausgewaehlteSw, Sw.LHD_M_ESS_EG.id);
        boolean hatSpEssEg  = contains(ausgewaehlteSw, Sw.SP_ESS_EG.id);
        
        boolean hatLhdKueche = contains(ausgewaehlteSw, Sw.LHD_M_KUECHE_EG.id);
        boolean hatSpKueche  = contains(ausgewaehlteSw, Sw.SP_KUECHE_EG.id);

        boolean hatLhdOg = contains(ausgewaehlteSw, Sw.LHD_M_OG.id);
        boolean hatSpOg  = contains(ausgewaehlteSw, Sw.SP_OG.id);
        
        // Regel 1: Widerspruch im Essbereich EG
        if (hatLhdEssEg && hatSpEssEg) {
            zeigeFehler("Widerspruch im EG (Essbereich)", "Landhausdielen und Stäbchenparkett können nicht gleichzeitig gewählt werden.");
            return false;
        }
        
        // Regel 2: Widerspruch in der Küche
        if (hatLhdKueche && hatSpKueche) {
            zeigeFehler("Widerspruch im EG (Küche)", "Landhausdielen und Stäbchenparkett können nicht gleichzeitig gewählt werden.");
            return false;
        }

        // Regel 3: Widerspruch im Obergeschoss
        if (hatLhdOg && hatSpOg) {
            zeigeFehler("Widerspruch im OG", "Landhausdielen und Stäbchenparkett können nicht gleichzeitig gewählt werden.");
            return false;
        }
        
        return true;
    }

    // Hilfsmethode: Sucht einen int-Wert in einem Array
    private boolean contains(int[] arr, int val) {
        if (arr == null) return false;
        for (int i : arr) {
            if (i == val) return true;
        }
        return false;
    }
    
    // Hilfsmethode: Zeigt eine Fehlermeldung an
    private void zeigeFehler(String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Plausibilitätsprüfung");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}