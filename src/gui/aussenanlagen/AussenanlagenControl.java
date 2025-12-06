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
		int[][] swAussenanlagen = kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(SwKategorie.AUSSENANLAGEN.id);
        if (swAussenanlagen != null)
            aussenanlagenView.updateSwInView(swAussenanlagen);
	}
	
	@Deprecated
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
	
	public void speichereSonderwuensche(int[] aussenanlagenSw, int[][] aussenanlagenSwMitAnzahl) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationAussenanlagen(aussenanlagenSw, aussenanlagenSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }

        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
            		aussenanlagenSw,
            		aussenanlagenSwMitAnzahl,
                    SwKategorie.AUSSENANLAGEN.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Außenanlagen konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	}
	
	@Deprecated
	public boolean pruefeKonstellationAussenanlagen(int[] ausgewaehlteSw) {
		return true; // TODO
	}
	
	public boolean pruefeKonstellationAussenanlagen(int[] ausgewaehlteSw, int[][] aussenanlagenSwMitAnzahl) {
		return true; // TODO
	}
}
