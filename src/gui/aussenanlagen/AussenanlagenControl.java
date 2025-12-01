package gui.aussenanlagen;

import business.kunde.KundeModel;
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
		// TODO
	}
	
	public void speichereSonderwuensche(int[] aussenanlagenSw) {
		// TODO
	}
	
	public boolean pruefeKonstellationAussenanlagen(int[] ausgewaehlteSw) {
		return true; // TODO
	}
}
