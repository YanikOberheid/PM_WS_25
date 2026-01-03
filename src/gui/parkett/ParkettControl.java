package gui.parkett;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.KundeModel;
import business.kunde.Sw;
import business.kunde.SwKategorie;

/**
 * Controller für die Parkett-Sonderwünsche.
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

    /**
     * Öffnet die View und lädt vorhandene Daten.
     */
    public void oeffneParkettView() {
        if (this.kundeModel.getKunde() == null) {
            zeigeFehler("Kein Kunde ausgewählt", "Bitte wählen Sie zuerst einen Kunden aus.");
            return;
        }
        
        leseParkettSonderwuensche();
        this.parkettView.oeffneParkettView();
    }

    /**
     * Lädt die Sonderwünsche aus der Datenbank in die View.
     */
    public void leseParkettSonderwuensche() {
        try {
            // Versuch: Neues Format (mit Anzahl)
            int[][] swIds = this.kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(SwKategorie.PARKETT.id);
            if (swIds != null) {
                this.parkettView.updateSwInView(swIds);
                this.parkettView.berechneUndZeigePreisSonderwuensche();
                return;
            }
        } catch (Exception e) {
            // Ignorieren, Fallback nutzen
        }

        // Fallback: Altes Format (nur IDs)
        int[] swIds = this.kundeModel.gibAusgewaehlteSwAusDb(SwKategorie.PARKETT.id);
        int[][] swParkett;
        
        if (swIds != null) {
            swParkett = new int[swIds.length][2];
            for (int i = 0; i < swIds.length; i++) {
                swParkett[i][0] = swIds[i]; 
                swParkett[i][1] = 1;        
            }
        } else {
            swParkett = new int[0][0];
        }

        this.parkettView.updateSwInView(swParkett);
        this.parkettView.berechneUndZeigePreisSonderwuensche();
    }

    /**
     * Speichert die Auswahl nach erfolgreicher Validierung.
     */
    public void speichereSonderwuensche(int[][] parkettSwMitAnzahl) {
        if (!pruefeKonstellationSonderwuensche(parkettSwMitAnzahl)) {
            return;
        }

        try {
            this.kundeModel.speichereSonderwuenscheFuerKategorie(parkettSwMitAnzahl, SwKategorie.PARKETT.id);
        } catch (Exception e) {
            // Fallback für alte Model-Methoden
            try {
                int[] simpleIds = new int[parkettSwMitAnzahl.length];
                for(int i = 0; i < parkettSwMitAnzahl.length; i++) {
                    simpleIds[i] = parkettSwMitAnzahl[i][0];
                }
                this.kundeModel.speichereSonderwuenscheFuerKategorie(simpleIds, SwKategorie.PARKETT.id);
            } catch (Exception ex) {
                ex.printStackTrace();
                zeigeFehler("Fehler", "Fehler beim Speichern: " + ex.getMessage());
            }
        }
    }

    /**
     * Prüft auf widersprüchliche Auswahlen (EG, OG, DG).
     */
    public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl) {
        int[] ids = new int[ausgewaehlteSwMitAnzahl.length];
        for(int i=0; i<ausgewaehlteSwMitAnzahl.length; i++) {
            ids[i] = ausgewaehlteSwMitAnzahl[i][0];
        }
        
        // --- EG Prüfung ---
        if (contains(ids, Sw.LHD_M_ESS_EG.id) && contains(ids, Sw.SP_ESS_EG.id)) {
            zeigeFehler("Widerspruch im EG (Essbereich)", "Landhausdielen und Stäbchenparkett gleichzeitig nicht möglich.");
            return false;
        }
        
        if (contains(ids, Sw.LHD_M_KUECHE_EG.id) && contains(ids, Sw.SP_KUECHE_EG.id)) {
            zeigeFehler("Widerspruch im EG (Küche)", "Landhausdielen und Stäbchenparkett gleichzeitig nicht möglich.");
            return false;
        }

        // --- OG Prüfung ---
        if (contains(ids, Sw.LHD_M_OG.id) && contains(ids, Sw.SP_OG.id)) {
            zeigeFehler("Widerspruch im OG", "Landhausdielen und Stäbchenparkett gleichzeitig nicht möglich.");
            return false;
        }
        
        // --- DG Prüfung ---
        int countDg = 0;
        if (contains(ids, Sw.LHD_M_KOMPLETT_DG.id)) countDg++;
        if (contains(ids, Sw.LDH_M_OHNE_BAD_DG.id)) countDg++;
        if (contains(ids, Sw.SP_KOMPLETT_DG.id)) countDg++;
        if (contains(ids, Sw.SP_OHNE_BAD_DG.id)) countDg++;
        
        if (countDg > 1) {
            zeigeFehler("Widerspruch im DG", "Im Dachgeschoss darf nur eine Option gewählt werden.");
            return false;
        }
        
        return true;
    }
    
    /**
     * Exportiert die aktuelle Auswahl als CSV-Datei.
     */
    public void exportiereSonderwuenscheAlsCsv(int[][] parkettSwMitAnzahl) {
        if (!pruefeKonstellationSonderwuensche(parkettSwMitAnzahl)) {
            return;
        }
        
        if (parkettSwMitAnzahl == null || parkettSwMitAnzahl.length == 0) {
            zeigeFehler("Export", "Keine Sonderwünsche ausgewählt.");
            return;
        }

        if (kundeModel.getKunde() == null) {
            zeigeFehler("Export Fehler", "Kein Kunde ausgewählt.");
            return;
        }

        try {
            int kundennummer = kundeModel.getKunde().getIdKunde();
            String nachname = kundeModel.getKunde().getNachname();
            String dateiname = kundennummer + "_" + nachname + "_Parkett.csv";
            File file = new File(dateiname);

            StringBuilder sb = new StringBuilder();
            sb.append("Sonderwunsch;Anzahl;Einzelpreis;Gesamtpreis\n");

            for (int[] tupel : parkettSwMitAnzahl) {
                Sw sw = Sw.findeMitId(tupel[0]);
                int anzahl = tupel[1];
                
                if (sw != null) {
                    double einzel = sw.preis;
                    double gesamt = einzel * anzahl;
                    sb.append(sw.bes).append(";")
                      .append(anzahl).append(";")
                      .append(einzel).append(";")
                      .append(gesamt).append("\n");
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(sb.toString());
            }
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Exportiert nach: " + file.getAbsolutePath());
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            zeigeFehler("Export Fehler", "Datei konnte nicht geschrieben werden.");
        }
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
        alert.setTitle("Hinweis");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}