package gui.aussenanlagen;

import business.kunde.KundeModel;
import business.kunde.SwKategorie;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AussenanlagenControl {
	
	private final AussenanlagenView aussenanlagenView;
	private final KundeModel kundeModel;
	
	public AussenanlagenControl() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		kundeModel = KundeModel.getInstance();
		aussenanlagenView = new AussenanlagenView(this, stage);
	}
	
	public void oeffneAussenanlagenView() {
		leseAussenanlagenSonderwuensche();
		aussenanlagenView.oeffneAussenanlagenView();
	}
	
	public void leseAussenanlagenSonderwuensche() {
		 int[] swAussenanlagen = kundeModel.gibAusgewaehlteSwAusDb(SwKategorie.AUSSENANLAGEN.id);
	        if (swAussenanlagen != null)
	        	aussenanlagenView.updateSwCheckboxen(swAussenanlagen);
	}
	
	public void speichereSonderwuensche(int[] aussenanlagenSw) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationAussenanlagen(aussenanlagenSw)) {
            // Konflikt -> nicht speichern
            return;
        }

        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
            		aussenanlagenSw,
                    SwKategorie.AUSSENANLAGEN.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Heizungen konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	}
	
	public boolean pruefeKonstellationAussenanlagen(int[] ausgewaehlteSw) {
		return true; // TODO
	}
}
