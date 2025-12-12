package gui.heizung;

import business.kunde.KundeModel;
import business.kunde.SwKategorie;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HeizungControl {

    private final HeizungView heizungView;
    private final KundeModel kundeModel;

    public HeizungControl() {
        Stage stageHeizung = new Stage();
        stageHeizung.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = KundeModel.getInstance();
        this.heizungView = new HeizungView(this, stageHeizung);
    }
    
    public void oeffneHeizungView() {
        leseHeizungsSonderwuensche();
        heizungView.oeffneHeizungView();
    }

    public void leseHeizungsSonderwuensche() {
        int[][] swHeizung = kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(SwKategorie.HEIZKOERPER.id);
        if (swHeizung != null)
            heizungView.updateSwInView(swHeizung);
    }
    
    // Speichern der ausgewählten Sonderwuensche
    @Deprecated
    public void speichereSonderwuensche(int[] heizungsSw) {
        // Erst Konstellation prüfen
        if (!pruefeKonstellationHeizkoerper(heizungsSw)) {
            // Konflikt -> nicht speichern
            return;
        }

        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
                    heizungsSw,
                    SwKategorie.HEIZKOERPER.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Heizungen konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
    }
    @Deprecated
    public void speichereSonderwuensche(int[] heizungsSw, int[][] heizungsSwMitAnzahl) {
        // Erst Konstellation prüfen
        if (!pruefeKonstellationHeizkoerper(heizungsSw, heizungsSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }

        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
                    heizungsSw,
                    heizungsSwMitAnzahl,
                    SwKategorie.HEIZKOERPER.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Heizungen konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
    }
    
	// Aktuellste Version
	public void speichereSonderwuensche(int[][] heizungsSwMitAnzahl) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationSonderwuensche(heizungsSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }
        
        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
            		heizungsSwMitAnzahl,
                    SwKategorie.HEIZKOERPER.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Heizungen-Varianten konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	} 
    
    @Deprecated
    public boolean pruefeKonstellationHeizkoerper(int[] ausgewaehlteSw) {
        return true; // Erst alles durchlassen. Implementiation ist Priorität [5]
    }
    
    @Deprecated
    public boolean pruefeKonstellationHeizkoerper(int[] ausgewaehlteSw, int[][] ausgewaehlteSwMitAnzahl) {
        return true; // Erst alles durchlassen. Implementiation ist Priorität [5]
    }
    
	// Aktuellste Version
	public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl){
		return true;
	}
}
