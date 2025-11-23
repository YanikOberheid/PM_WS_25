package gui.kunde;

import java.sql.SQLException;

import javafx.stage.Stage;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import gui.grundriss.GrundrissControl;

/**
 * Klasse, welche das Grundfenster mit den Kundendaten kontrolliert.
 */
public class KundeControl {

	// das View-Objekt des Grundfensters mit den Kundendaten
	private KundeView kundeView;
	// das Model-Objekt des Grundfensters mit den Kundendaten
	private KundeModel kundeModel;
	/*
	 * das GrundrissControl-Objekt fuer die Sonderwuensche zum Grundriss zu dem
	 * Kunden
	 */
	private GrundrissControl grundrissControl;

	/**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
	 * Grundfenster mit den Kundendaten.
	 * 
	 * @param primaryStage, Stage fuer das View-Objekt zu dem Grundfenster mit den
	 *                      Kundendaten
	 */
	public KundeControl(Stage primaryStage) {
		this.kundeModel = KundeModel.getInstance();
		this.kundeView = new KundeView(this, primaryStage, kundeModel);
	}

	/*
	 * erstellt, falls nicht vorhanden, ein Grundriss-Control-Objekt. Das
	 * GrundrissView wird sichtbar gemacht.
	 */
	public void oeffneGrundrissControl() {
		if (this.grundrissControl == null) {
			this.grundrissControl = new GrundrissControl();
		}
		this.grundrissControl.oeffneGrundrissView();
	}

	/**
	 * speichert ein Kunde-Objekt in die Datenbank
	 * 
	 * @param kunde, Kunde-Objekt, welches zu speichern ist
	 */
	// Von Yamam alles unten
	public void speichereKunden(Kunde kunde) {

		// 1. Validierung
		if (!istKundeValide(kunde)) {
			return; // bei Fehler NICHT speichern
		}

		// 2. Speichern mit Exception Handling
		try {
			kundeModel.speichereKunden(kunde);

			// Info bei Erfolg (optional)
			this.kundeView.zeigeInfo("Speichern erfolgreich", "Die Kundendaten wurden erfolgreich gespeichert.");
		} catch (java.sql.SQLException e) {

			// MySQL: 1062 = Duplicate entry (z. B. Kunde/Haus bereits vorhanden)
			if (e.getErrorCode() == 1062) {
				this.kundeView.zeigeFehlermeldung("Bereits vorhanden",
						"Für diese Plannummer existiert bereits ein Kunde oder die Kundendaten sind schon gespeichert.");
			} else {
				this.kundeView.zeigeFehlermeldung("Datenbankfehler",
						"Beim Speichern der Kundendaten ist ein Fehler aufgetreten.");
			}
			e.printStackTrace();
		} catch (Exception e) {
			this.kundeView.zeigeFehlermeldung("Unbekannter Fehler", "Es ist ein unerwarteter Fehler aufgetreten.");
			e.printStackTrace();
		}
	}

	private boolean istKundeValide(Kunde kunde) {

		// 1) Pflichtfelder
		if (!kunde.isVollstaendig()) {
			this.kundeView.zeigeFehlermeldung("Ungültige Eingabe",
					"Bitte füllen Sie alle Felder aus (Plannummer, Vorname, Nachname, Telefonnummer und E-Mail).");
			return false;
		}

		// 2) Telefonnummer prüfen
		if (!kunde.isTelefonnummerValid()) {
			this.kundeView.zeigeFehlermeldung("Ungültige Telefonnummer",
					"Die Telefonnummer darf nur aus Ziffern bestehen.");
			return false;
		}

		// 3) E-Mail prüfen
		if (!kunde.isEmailValid()) {
			this.kundeView.zeigeFehlermeldung("Ungültige E-Mail-Adresse", "Die E-Mail-Adresse muss ein '@' enthalten.");
			return false;
		}

		// alles okay
		return true;
	}

}