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
 */
public final class ParkettControl {

    private final ParkettView parkettView;
    private final KundeModel kundeModel;

    public ParkettControl() {
        Stage stageParkett = new Stage();
        stageParkett.initModality(Modality.APPLICATION_MODAL);
        
        this.kundeModel = KundeModel.getInstance();
        this.parkettView = new ParkettView(this, stageParkett);
    }

    public void oeffneParkettView() {
        // Sicherheitsprüfung: Ist ein Kunde da?
        if (this.kundeModel.getKunde() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Kein Kunde ausgewählt");
            alert.setHeaderText(null);
            alert.setContentText("Bitte wählen Sie zuerst einen Kunden aus.");
            alert.showAndWait();
            return;
        }
        
        leseParkettSonderwuensche();
        this.parkettView.oeffneParkettView();
    }

    public void leseParkettSonderwuensche() {
        // FEHLERBEHEBUNG:
        // Das Model hat die Methode gibAusgewaehlteSwMitAnzahlAusDb noch nicht.
        // Wir nutzen die alte Methode und konvertieren manuell.
        
        // 1. Alte Methode aufrufen (gibt int[] zurück)
        int[] swIds = this.kundeModel.gibAusgewaehlteSwAusDb(SwKategorie.PARKETT.id);
        
        // 2. Konvertierung zu int[][] für die View
        int[][] swParkett;
        
        if (swIds != null) {
            swParkett = new int[swIds.length][2];
            for (int i = 0; i < swIds.length; i++) {
                swParkett[i][0] = swIds[i]; // ID
                swParkett[i][1] = 1;        // Anzahl (Standard 1 bei Parkett)
            }
        } else {
            swParkett = new int[0][0];
        }

        // 3. View updaten
        this.parkettView.updateSwInView(swParkett);
        
        // 4. Preis berechnen
        this.parkettView.berechneUndZeigePreisSonderwuensche();
    }

    /**
     * Speichern der Sonderwünsche.
     * Nimmt das neue Format (int[][]) entgegen, muss aber für das Model
     * wahrscheinlich wieder in int[] umwandeln.
     */
    public void speichereSonderwuensche(int[][] parkettSwMitAnzahl) {
        try {
            // FEHLERBEHEBUNG:
            // Wir müssen das 2D-Array wieder in ein einfaches ID-Array flachklopfen,
            // da das KundeModel wahrscheinlich nur 'speichereSonderwuenscheFuerKategorie' kennt.
            
            int[] simpleIds = new int[parkettSwMitAnzahl.length];
            for(int i = 0; i < parkettSwMitAnzahl.length; i++) {
                simpleIds[i] = parkettSwMitAnzahl[i][0]; // Nur die ID nehmen
            }

            // Alte Speichermethode des Models aufrufen
            this.kundeModel.speichereSonderwuenscheFuerKategorie(simpleIds, SwKategorie.PARKETT.id);
             
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Fehler beim Speichern: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Validierung: Prüft auf widersprüchliche Auswahlen.
     */
    public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl) {
        // Extrahiere nur die IDs in ein einfaches Array für die Prüfung
        int[] ids = new int[ausgewaehlteSwMitAnzahl.length];
        for(int i=0; i<ausgewaehlteSwMitAnzahl.length; i++) {
            ids[i] = ausgewaehlteSwMitAnzahl[i][0];
        }
        
        // Prüfen
        boolean hatLhdEssEg = contains(ids, Sw.LHD_M_ESS_EG.id);
        boolean hatSpEssEg  = contains(ids, Sw.SP_ESS_EG.id);
        
        boolean hatLhdKueche = contains(ids, Sw.LHD_M_KUECHE_EG.id);
        boolean hatSpKueche  = contains(ids, Sw.SP_KUECHE_EG.id);

        boolean hatLhdOg = contains(ids, Sw.LHD_M_OG.id);
        boolean hatSpOg  = contains(ids, Sw.SP_OG.id);
        
        if (hatLhdEssEg && hatSpEssEg) {
            zeigeFehler("Widerspruch im EG (Essbereich)", "Landhausdielen und Stäbchenparkett können nicht gleichzeitig gewählt werden.");
            return false;
        }
        
        if (hatLhdKueche && hatSpKueche) {
            zeigeFehler("Widerspruch im EG (Küche)", "Landhausdielen und Stäbchenparkett können nicht gleichzeitig gewählt werden.");
            return false;
        }

        if (hatLhdOg && hatSpOg) {
            zeigeFehler("Widerspruch im OG", "Landhausdielen und Stäbchenparkett können nicht gleichzeitig gewählt werden.");
            return false;
        }
        
        return true;
    }

    private boolean contains(int[] arr, int val) {
        if (arr == null) return false;
        for (int i : arr) {
            if (i == val) return true;
        }
        return false;
    }
    
    private void zeigeFehler(String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Plausibilitätsprüfung");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}