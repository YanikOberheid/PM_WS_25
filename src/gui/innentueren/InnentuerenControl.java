package gui.innentueren;

import business.kunde.KundeModel;
import business.kunde.SwKategorie;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Control für die Innentüren-GUI.
 * Task-Scope: Nur Öffnen/Schließen und Kundenvorprüfung.
 */
public class InnentuerenControl {

	private final InnentuerenView innentuerenView;
    private final KundeModel kundeModel;

    public InnentuerenControl() {
        Stage stageInnentueren = new Stage();
        stageInnentueren.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = KundeModel.getInstance();
        this.innentuerenView = new InnentuerenView(this, stageInnentueren);
    }
    
    public void oeffneInnenturenView() {
    	leseInnentuerenSonderwuensche();
    	innentuerenView.oeffneInnentuerenView();
    }
    
    public void leseInnentuerenSonderwuensche() {
        int[] swInnentueren = kundeModel.gibAusgewaehlteSwAusDb(SwKategorie.INNENTUEREN.id);
        if (swInnentueren != null)
        	innentuerenView.updateSwCheckboxen(swInnentueren);
    }
    
    // Muss noch implementiert werden
	// Entsprechende Task
    public void speichereSonderwuensche(int [] innentuerenSw) {
    	
    }
    
    // Spatere Implementierung Priorität 5
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
        return true;
    }
}
