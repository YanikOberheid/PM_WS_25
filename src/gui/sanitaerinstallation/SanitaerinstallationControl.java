package gui.sanitaerinstallation;

import business.kunde.KundeModel;
import business.kunde.SwKategorie;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SanitaerinstallationControl {
	
	private final SanitaerinstallationView sanitaerView;
	private final KundeModel kundeModel;
	
	public SanitaerinstallationControl() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		kundeModel = KundeModel.getInstance();
		this.sanitaerView = new SanitaerinstallationView(this, stage);
	}
	
	public void oeffneSanitaerinstallationView() {
		leseSanitaerinstallationSonderwuensche();
		sanitaerView.oeffneSanitaerinstallationView();
	}
	
	public void leseSanitaerinstallationSonderwuensche() {
		int[][] swSanitaer = kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(SwKategorie.SANITAERINSTALLATION.id);
		if (swSanitaer != null)
			sanitaerView.updateSwInView(swSanitaer);
	}
	
	public void speichereSonderwuensche(int[][] sanitaerSwMitAnzahl) {
		// TODO
	}
	
	public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl){
		return true; // TODO
	}
	
	public void exportiereSonderwuenscheAlsCsv(int[][] sanitaerSwMitAnzahl) {
		// TODO
	}
}
