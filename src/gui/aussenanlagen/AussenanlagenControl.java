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
	    // 1. Lokale Sonderwünsche versuchen
	    int[] ausgewaehlteSw = kundeModel.gibAusgewaehlteSwLokal();
	    
	    // 2. Wenn lokal nichts da ist, aus DB holen
	    if (ausgewaehlteSw == null || ausgewaehlteSw.length == 0) {
	        ausgewaehlteSw = kundeModel.gibAusgewaehlteSwAusDb();
	    }
	    if (ausgewaehlteSw != null && ausgewaehlteSw.length > 0) {
	        aussenanlagenView.updateSwCheckboxen(ausgewaehlteSw);
	    }
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
