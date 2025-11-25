package gui.grundriss;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;

import business.kunde.KundeModel;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Grundriss-Varianten
 * kontrolliert.
 */
public final class GrundrissControl {
	
	// das View-Objekt des Grundriss-Fensters
	private GrundrissView grundrissView;
	private KundeModel kundeModel;

	/**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
	 * Fenster fuer die Sonderwuensche zum Grundriss.
	 */
	public GrundrissControl(){  
	   	Stage stageGrundriss = new Stage();
    	stageGrundriss.initModality(Modality.APPLICATION_MODAL);
    	this.kundeModel = KundeModel.getInstance();
    	this.grundrissView = new GrundrissView(this, stageGrundriss);
    	// Erstellen von grundrissView updatet indirekt Checkboxen, denn
<<<<<<< HEAD
    	// -> grundrissView ruft seinen Konstruktor auf
=======
	    // -> grundrissView ruft seinen Konstruktor auf
>>>>>>> refs/remotes/origin/dev
	    // -> grundrissView ruft seine leseGrundrissSonderwuensche auf
	    // -> grundrissView ruft grundrissControl's leseGrundrissSonderwuensche auf
	    // -> grundrissControl ruft grundrissView's updateCheckboxen auf (Checkboxen sind private)
	}
	
	/**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView(){ 
		this.leseGrundrissSonderwuensche();
		this.grundrissView.oeffneGrundrissView();
	}

	public void leseGrundrissSonderwuensche(){
		int[] ausgewaehlteSw = kundeModel.gibAusgewaehlteSwAusDb();
		if (ausgewaehlteSw != null && ausgewaehlteSw.length > 0)
			this.grundrissView.updateGrundrissCheckboxen(ausgewaehlteSw);
    } 
	
	public void speichereSonderwuensche(int[] grundrissSw) {
		// Hole Kopie der aktuell ausgewählten Sw aus der Datenbank
		int[] ausgewaehlteSw = this.kundeModel.gibAusgewaehlteSwAusDb();
<<<<<<< HEAD
		// Filtere Sonderwünsche zu Grundriss-Varianten raus
		ausgewaehlteSw = Arrays.stream(ausgewaehlteSw).filter(sw -> sw < 200 || sw >= 300).toArray();
		
		// Führe mit neuer Auswahl an Sonderwünschen zu Grundriss-Varianten zusammen
		int[] zuPruefendeSwKonstellation = new int[ausgewaehlteSw.length + grundrissSw.length];
		for (int i = 0; i < zuPruefendeSwKonstellation.length; i++) {
			if (i < ausgewaehlteSw.length) {
				zuPruefendeSwKonstellation[i] = ausgewaehlteSw[i];
			} else {
				zuPruefendeSwKonstellation[i] = grundrissSw[i];
			}
		}
		
		// Prüfe neue Konstellation
		if (this.pruefeKonstellationSonderwuensche(zuPruefendeSwKonstellation)) {
			try {
				this.kundeModel.updateAusgewaehlteSw(zuPruefendeSwKonstellation);
			} catch(Exception exc) {
				System.out.println("Neue Auswahl an Sonderwünschen zu Grundriss-Varianten"
						+ "konnte nicht gespeichert werden");
=======
		if (ausgewaehlteSw == null) {
			ausgewaehlteSw = new int[0];
		}

		// Filtere Sonderwünsche zu Grundriss-Varianten raus (IDs 200–299)
		ausgewaehlteSw = Arrays.stream(ausgewaehlteSw)
				.filter(sw -> sw < 200 || sw >= 300)
				.toArray();
		
		// Führe mit neuer Auswahl an Sonderwünschen zu Grundriss-Varianten zusammen
		int[] zuPruefendeSwKonstellation = new int[ausgewaehlteSw.length + grundrissSw.length];
		for (int i = 0; i < zuPruefendeSwKonstellation.length; i++) {
			if (i < ausgewaehlteSw.length) {
				zuPruefendeSwKonstellation[i] = ausgewaehlteSw[i];
			} else {
				// WICHTIG: Index in grundrissSw ist i - ausgewaehlteSw.length
				zuPruefendeSwKonstellation[i] = grundrissSw[i - ausgewaehlteSw.length];
			}
		}
		
		// Prüfe neue Konstellation
		if (this.pruefeKonstellationSonderwuensche(zuPruefendeSwKonstellation)) {
			try {
				this.kundeModel.updateAusgewaehlteSw(zuPruefendeSwKonstellation);
			} catch(Exception exc) {
				System.out.println("Neue Auswahl an Sonderwünschen zu Grundriss-Varianten"
						+ " konnte nicht gespeichert werden");
				exc.printStackTrace();
>>>>>>> refs/remotes/origin/dev
			}
		}
	}
	
	public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw){
		// Hier kommen später eure komplexen Regeln.
		// Für Task 2 kann das (falls vom Dozenten erlaubt) erstmal true bleiben.
		return true;
	}
}
