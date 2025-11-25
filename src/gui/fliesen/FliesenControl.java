package gui.fliesen;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import business.kunde.KundeModel;

/**
 * Control für das Fenster "Sonderwünsche zu Fliesen". Lädt die gespeicherten
 * Fliesen-Optionen und zeigt sie in der View, sowie Speichern der Auswahl für
 * die Fliesen-Kategorie (ID = 70).
 */
public class FliesenControl {

	private static final int KATEGORIE_FLIESEN = 70;

	private final KundeModel kundeModel;
	private final FliesenView view;

	public FliesenControl(KundeModel kundeModel) {
		this.kundeModel = kundeModel;

		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		this.view = new FliesenView(this, stage);
	}

	/** Öffnet die View; lädt vorher ggf. die bestehende Auswahl. */
	public void oeffneFliesenView() {
		if (kundeModel.getKunde() == null) {
			new Alert(Alert.AlertType.WARNING, "Bitte zuerst im Hauptfenster einen Kunden auswählen oder anlegen.")
					.showAndWait();
			return;
		}
		try {
			int[] ids = kundeModel.gibAusgewaehlteSwAusDb(KATEGORIE_FLIESEN);
			view.updateFliesenCheckboxen(ids);
		} catch (Exception e) {
			e.printStackTrace();
			//view.zeigeFehlermeldung("Fehler", "Fliesen-Auswahl konnte nicht geladen werden.");
		}
		view.oeffneView();
	}



	/**
	 * Lädt die in der DB gespeicherten Fliesen-Sonderwünsche und aktualisiert die
	 * View.
	 */
	public void ladeAuswahl() {
		try {
			int[] ausgewaehlteIds = kundeModel.gibAusgewaehlteSwAusDb(KATEGORIE_FLIESEN);
			if (ausgewaehlteIds != null) {
				view.updateFliesenCheckboxen(ausgewaehlteIds);
			} else {
				// nichts gesetzt -> alles abwählen
				view.updateFliesenCheckboxen(new int[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Alert(AlertType.ERROR, "Fliesen-Auswahl konnte nicht geladen werden.").showAndWait();
		}
	}

	/** Wird von der View aufgerufen und speichert die übergebene Auswahl. */
	public void speichereSonderwuensche(int[] ausgewaehlteIds) {
		try {
			kundeModel.speichereSonderwuenscheFuerKategorie(ausgewaehlteIds, KATEGORIE_FLIESEN);
			new Alert(AlertType.INFORMATION, "Fliesen-Sonderwünsche wurden gespeichert.").showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			new Alert(AlertType.ERROR, "Speichern der Fliesen-Sonderwünsche ist fehlgeschlagen.").showAndWait();
		}
	}
}
