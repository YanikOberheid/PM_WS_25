package gui.kunde;

import java.sql.SQLException;

import javafx.stage.Stage;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.fliesen.FliesenControl;
import gui.grundriss.GrundrissControl;

/**
 * Klasse, welche das Grundfenster mit den Kundendaten kontrolliert.
 */
public class KundeControl {
	
    // das View-Objekt des Grundfensters mit den Kundendaten
	private KundeView kundeView;
    // das Model-Objekt des Grundfensters mit den Kundendaten
    private KundeModel kundeModel;
    /* das GrundrissControl-Objekt fuer die Sonderwuensche
       zum Grundriss zu dem Kunden */
    private GrundrissControl grundrissControl;
 // Referenz auf das neue Control-Objekt für Fliesen
    private FliesenControl fliesenControl;
    
    /**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
	 * Grundfenster mit den Kundendaten.
	 * @param primaryStage, Stage fuer das View-Objekt zu dem Grundfenster mit den Kundendaten
	 */
    public KundeControl(Stage primaryStage) { 
        this.kundeModel = KundeModel.getInstance(); 
        this.kundeView = new KundeView(this, primaryStage, kundeModel);
    }
    
    /*
     * erstellt, falls nicht vorhanden, ein Grundriss-Control-Objekt.
     * Das GrundrissView wird sichtbar gemacht.
     */
    public void oeffneGrundrissControl(){
    	if (this.grundrissControl == null){
    		this.grundrissControl = new GrundrissControl();
      	}
    	this.grundrissControl.oeffneGrundrissView();
    }
    
	/**
	 * speichert ein Kunde-Objekt in die Datenbank
	 * @param kunde, Kunde-Objekt, welches zu speichern ist
	 */
    public void speichereKunden(Kunde kunde){
      	try{
      		// Kundendaten Validieren auf Korrektheit
			if (!kundeModel.isValidCustomer(kunde, false)) {
				kundeView.zeigeFehlermeldung("Ungültige Eingabe", "Bitte prüfen Sie die Kundendaten.");
				return;
			}
			kundeModel.speichereKunden(kunde);
			kundeView.zeigeErfolgsmeldung("Erfolg", "Der Kunde wurde erfolgreich angelegt.");
    	}
    	catch(SQLException exc){
    		exc.printStackTrace();
    		this.kundeView.zeigeFehlermeldung("SQLException",
                "Fehler beim Speichern in die Datenbank");
    	}
    	catch(Exception exc){
    		exc.printStackTrace();
    		this.kundeView.zeigeFehlermeldung("Exception",
                "Unbekannter Fehler");
    	}
    }
    
    /**
     * Erstellt, falls nicht vorhanden, ein Fliesen-Control-Objekt.
     * Das FliesenView wird sichtbar gemacht.
     */
    public void oeffneFliesenControl(){
        if (this.fliesenControl == null){
            this.fliesenControl = new FliesenControl(kundeModel);
        }
        this.fliesenControl.oeffneFliesenView();
    }
    
    /**
     * Lädt den Kunden zur angegebenen Hausnummer aus dem Model und zeigt ihn in der View an.
     * Falls ein Datenbankfehler auftritt, wird eine Fehlermeldung in der View angezeigt.
     *
     * @param hausnummer die Hausnummer / Plannummer des Kunden, der geladen werden soll
     */
    public void ladeKundenZuHausnummer(int hausnummer) {
        try {
            Kunde kunde = kundeModel.ladeKunde(hausnummer);
            kundeView.zeigeKundeAufGui(kunde);
        } catch (SQLException e) {
            kundeView.zeigeFehlermeldung("Fehler", "Kunde konnte nicht geladen werden.");
        }
    }
    
    public void loescheKunden(int hausnummer) {
        try {
            boolean erfolg = kundeModel.loescheKunden(hausnummer);
            if (erfolg) {
                kundeView.zeigeErfolgsmeldung("Erfolg", "Kunde wurde gelöscht.");
                kundeView.zeigeKundeAufGui(null); // GUI leeren
            } else {
                kundeView.zeigeFehlermeldung("Fehler", "Kein Kunde unter dieser Hausnummer gefunden.");
            }
        } catch (SQLException e) {
            kundeView.zeigeFehlermeldung("Fehler", "Datenbankfehler beim Löschen.");
            e.printStackTrace();
        }
    }
    
    public void updateKunde(Kunde kunde) {
        try {
            if (!kundeModel.isValidCustomer(kunde, true)) {
                kundeView.zeigeFehlermeldung("Ungültige Eingabe", "Bitte prüfen Sie die Kundendaten.");
                return;
            }

            kundeModel.updateKunde(kunde);
            kundeView.zeigeErfolgsmeldung("Erfolg", "Kundendaten wurden erfolgreich aktualisiert.");
        } catch (SQLException e) {
            e.printStackTrace();
            kundeView.zeigeFehlermeldung("Fehler", "Kundendaten konnten nicht aktualisiert werden.");
        } catch (Exception e) {
            e.printStackTrace();
            kundeView.zeigeFehlermeldung("Fehler", "Unbekannter Fehler beim Aktualisieren.");
        }
    }
   
}
