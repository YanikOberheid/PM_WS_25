package gui.parkett;

import javafx.stage.Modality;
import javafx.stage.Stage;
import business.kunde.KundeModel;
import business.kunde.SwKategorie;

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
    	leseParkettSonderwuensche();
        parkettView.oeffneParkettView();
        
    }

    public void leseParkettSonderwuensche() {

        int[][] swParkett =
            kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(SwKategorie.PARKETT.id);

        parkettView.updateSwInView(swParkett);
    }



    
    @Deprecated
    public void speichereSonderwuensche(int[] parkettSw) {
    }
    
    @Deprecated
    public void speichereSonderwuensche(int[] parkettSw, int[][] parkettSwMitAnzahl) {
    }
    
    // Aktuellste Version
    public void speichereSonderwuensche(int[][] parkettSwMitAnzahl) {
    }
    
    @Deprecated
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
        return true;
    }
    
    @Deprecated
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw, int[][] ausgewaehlteSwMitAnzahl) {
        return true;
    }
    
    // Aktuellste Version
 	public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl){
 		return true;
 	}
}
