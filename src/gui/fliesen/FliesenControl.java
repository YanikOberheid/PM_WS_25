package gui.fliesen;
<<<<<<< HEAD
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import business.kunde.*;
import gui.kunde.KundeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Fliesen-Varianten
 * kontrolliert.
 */
public final class FliesenControl {

    // das View-Objekt des Fliesen-Fensters
    private FliesenView fliesenView;
    private KundeModel kundeModel;
    private DatabaseConnection connection;

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zum Fliesen.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public FliesenControl(KundeModel kundeModel, DatabaseConnection connection){
        Stage stageFliesen = new Stage();
        stageFliesen.initModality(Modality.APPLICATION_MODAL);
        this.fliesenView = new FliesenView(this, stageFliesen);
        this.kundeModel = kundeModel;
        this.connection = connection;
    }
    
    /**
     * macht das FliesenView-Objekt sichtbar.
     */
    public void oeffneFliesenView(){
        this.fliesenView.oeffneFliesenView();
    }

    //Fliesen aus Datenbankladen
    public String[][] leseFliesenSonderwuensche(){
    	this.connection = DatabaseConnection.getInstance();
    	return connection.executeSelectNameAndPrice("Wunschoption", 6);
    }

}
=======

import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.KundeModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
// Importiere hier deine FliesenView Klasse, sobald sie erstellt ist
// import gui.fliesen.FliesenView; 

/**
 * Klasse, welche das Fenster mit den Sonderwünschen zu den Fliesen kontrolliert.
 */
public class FliesenControl {
    
    // Das View-Objekt des Fliesen-Fensters
    private FliesenView fliesenView; 
    private KundeModel kundeModel;

    /**
     * Erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
     * Fenster für die Sonderwünsche zu den Fliesen.
     */
    public FliesenControl(KundeModel kundeModel) {  
        Stage stageFliesen = new Stage();
        stageFliesen.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = kundeModel;
        this.fliesenView = new FliesenView(this, stageFliesen);
    }
        
    /**
     * Macht das FliesenView-Objekt sichtbar.
     */
    public void oeffneFliesenView(){ 
        // 1. Check: Is a customer loaded?
        if (kundeModel.getKunde() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warnung");
            alert.setHeaderText("Kein Kunde ausgewählt");
            alert.setContentText("Bitte wählen oder erstellen Sie zuerst einen Kunden im Hauptfenster.");
            alert.showAndWait();
            return; // Stop here, do not open the window
        }

        // 2. Load data and open window
        this.leseFliesenSonderwuensche();
        this.fliesenView.oeffneFliesenView();
    }

    /**
     * Liest die Sonderwünsche aus der Datenbank und aktualisiert die View.
     */
    public void leseFliesenSonderwuensche(){
        // ID für die Kategorie "Fliesen" festlegen (Beispiel: 1)
        int kategorieID = 70; 
        
        // Bestehende Auswahl aus der DB laden
        int[] ausgewaehlteSw = kundeModel.gibAusgewaehlteSwAusDb(kategorieID);
        
        if (ausgewaehlteSw != null) {
            this.fliesenView.updateFliesenCheckboxen(ausgewaehlteSw);
        }
    } 

    /**
     * [3] Speichert die ausgewählten Fliesen-Sonderwünsche in der Datenbank.
     * @param fliesenSw Array der IDs, die in der View ausgewählt wurden.
     */
    public void speichereSonderwuensche(int[] fliesenSw) {
        try {
            // Kategorie-ID für Fliesen (muss mit der DB-Tabelle übereinstimmen)
            int fliesenKategorieID = 70;

            // Aufruf der neuen "Smart Save" Methode im Model
            // Dies verhindert, dass Grundriss- oder andere Sonderwünsche gelöscht werden.
            this.kundeModel.speichereSonderwuenscheFuerKategorie(fliesenSw, fliesenKategorieID);
            
            // Optional: Erfolgsmeldung oder Fenster schließen
            // this.fliesenView.schliesseFenster(); 

        } catch(Exception exc) {
            exc.printStackTrace();
            System.out.println("Fehler beim Speichern der Fliesen-Sonderwünsche.");
        }
    }
}
>>>>>>> refs/heads/main
