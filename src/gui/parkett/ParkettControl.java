package gui.parkett;

import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.KundeModel;

public final class ParkettControl {

    private final ParkettView parkettView;
    private final KundeModel kundeModel;

    public ParkettControl() {
        Stage stageParkett = new Stage();
        stageParkett.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = KundeModel.getInstance();
        this.parkettView = new ParkettView(this, stageParkett);
    }

    public void oeffneParkettView() {
        this.parkettView.oeffneParkettView();
    }

    public void leseParkettSonderwuensche() {
    }
    
    public void speichereSonderwuensche(int[] parkettSw, int[][] parkettSwMitAnzahl) {
    }
    
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw, int[][] ausgewaehlteSwMitAnzahl) {
        return true;
    }
}
