package gui.kunde;

import java.io.InputStream;
import java.sql.SQLException;

import javafx.stage.Stage;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.fliesen.FliesenControl;
import gui.grundriss.GrundrissControl;
import gui.heizung.HeizungControl;
import gui.aussenanlagen.AussenanlagenControl;
import gui.fenster.FensterControl;
import gui.innentueren.InnentuerenControl;
import gui.fliesen.FliesenControl;

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
    private HeizungControl heizungControl;
    private FensterControl fensterControl;
    // ...InnentuerenControl
	private InnentuerenControl innentuerenControl;
	private AussenanlagenControl aussenanlagenControl;
    
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
     * Erstellen, falls nicht vorhanden, ein Control-Objekt.
     * Das View wird sichtbar gemacht.
     */
    private String keinKundeTitel = "Kein Kunde ausgewählt";
    private String keinKundeMeldung = "Bitte wählen oder erstellen Sie einen Kunden.";
    
    public void oeffneGrundrissControl(){
    	if (kundeModel.getKunde() == null) {
    		kundeView.zeigeFehlermeldung(keinKundeTitel, keinKundeMeldung);
    		return;
    	}
    	
    	if (this.grundrissControl == null){
    		this.grundrissControl = new GrundrissControl();
      	}
    	this.grundrissControl.leseGrundrissSonderwuensche();
    	this.grundrissControl.oeffneGrundrissView();
    }
    
    public void oeffneHeizungControl() {
    	if (kundeModel.getKunde() == null) {
    		kundeView.zeigeFehlermeldung(keinKundeTitel, keinKundeMeldung);
    		return;
    	}
    	
        if (this.heizungControl == null) {
       
            this.heizungControl = new HeizungControl();
        }
        this.heizungControl.leseHeizungsSonderwuensche();
        this.heizungControl.oeffneHeizungView();
    }
    
    public void oeffneFliesenControl(){
    	if (kundeModel.getKunde() == null) {
    		kundeView.zeigeFehlermeldung(keinKundeTitel, keinKundeMeldung);
    		return;
    	}
        
    	if (this.fliesenControl == null){
            this.fliesenControl = new FliesenControl();
        }
    	this.fliesenControl.leseFliesenSonderwuensche();
        this.fliesenControl.oeffneFliesenView();
    }
    
   //fenster wird geöffnet#
    
    public void oeffneFensterControl() {
        if (this.kundeModel.getKunde() == null) {
            this.kundeView.zeigeFehlermeldung(keinKundeTitel, keinKundeMeldung);
            return;
        }

        if (this.fensterControl == null) {
            this.fensterControl = new FensterControl();
        }
        this.fensterControl.oeffneFensterView();
    }

    // InnentuerenControl
	public void oeffneInnentuerenControl() {
		if (this.kundeModel.getKunde() == null) {
            this.kundeView.zeigeFehlermeldung(keinKundeTitel, keinKundeMeldung);
            return;
        }
		
        if (this.innentuerenControl == null) {
			this.innentuerenControl = new InnentuerenControl();
		}
		this.innentuerenControl.oeffneInnenturenView();
	}
	
	// Außenanlagen
	public void oeffneAussenanlagenControl() {
		if (this.kundeModel.getKunde() == null) {
			this.kundeView.zeigeFehlermeldung(keinKundeTitel, keinKundeMeldung);
			return;
		}
		
		if (this.aussenanlagenControl == null) {
			this.aussenanlagenControl = new AussenanlagenControl();
		}
		this.aussenanlagenControl.oeffneAussenanlagenView();
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
     * Lädt den Kunden zur angegebenen Hausnummer aus dem Model und zeigt ihn in der View an.
     * Falls ein Datenbankfehler auftritt, wird eine Fehlermeldung in der View angezeigt.
     *
     * @param hausnummer die Hausnummer / Plannummer des Kunden, der geladen werden soll
     */
    public void ladeKundenZuHausnummer(int hausnummer) {
        try {
            Kunde kunde = kundeModel.ladeKunde(hausnummer);
            kundeView.zeigeKundeAufGui(kunde);
            kundeView.zeigeHausBildFuerHausnummer(hausnummer);
        } catch (SQLException e) {
            kundeView.zeigeFehlermeldung("Fehler", "Kunde konnte nicht geladen werden.");
        }
    }
    
    public void loescheKunden(int kundenummer, int hausnummer) {
        try {
            boolean erfolg = kundeModel.loescheKunden(kundenummer, hausnummer);
            if (erfolg) {
                kundeView.zeigeErfolgsmeldung("Erfolg", "Kunde wurde gelöscht.");
                kundeView.zeigeKundeAufGui(null); // GUI leeren
            } else {
                kundeView.zeigeFehlermeldung("Fehler", "Kein Kunde unter dieser Hausnummer gefunden.");
            }
        } catch (SQLException e) {
            kundeView.zeigeFehlermeldung("Fehler", "Datenbankfehler beim Löschen.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            kundeView.zeigeFehlermeldung("Fehler", "Unbekannter Fehler beim löschen.");
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
	// Laden des Bildes aus der DB
    public InputStream ladeBildAusDB(int idBild) throws SQLException, Exception {
		    return kundeModel.holBildAusDB(idBild);
	  }
    
	public void setAttributeNull() {
		kundeModel.setAttributesNull();
	}
   
}
