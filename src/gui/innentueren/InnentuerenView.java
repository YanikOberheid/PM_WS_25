package gui.innentueren;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * View für die Sonderwünsche "Innentüren".
 * Task-Scope: Nur GUI-Elemente, kein Speichern/DB.
 */
public class InnentuerenView extends BasisView {

    private final InnentuerenControl innentuerenControl;
    
    // GUI-Elemente
    // 4.1 / 4.2: Anzahl-Eingaben (Spinner), 4.3: Checkbox
    private final Label lbl41 = new Label("4.1 Mehrpreis Klarglas-Ausschnitt je Tür (460 €)");
    private final Spinner<Integer> sp41 = new Spinner<>(0, 20, 0);

    private final Label lbl42 = new Label("4.2 Mehrpreis Milchglas-Ausschnitt je Tür (560 €)");
    private final Spinner<Integer> sp42 = new Spinner<>(0, 20, 0);

    private final Label lbl43 = new Label("4.3 Innentür zur Garage als Holztür (660 €) – je 1x");
    private final CheckBox cb43 = new CheckBox();

    public InnentuerenView(InnentuerenControl innentuerenControl, Stage innentuerenStage) {
        super(innentuerenStage);
        this.innentuerenControl = innentuerenControl;
        innentuerenStage.setTitle("Sonderwuensche zu Innentüren");
        initKomponenten();
    }

    @Override
    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Innentüren – Varianten");

        // etwas Luft für Eingabefelder
        sp41.setEditable(true);
        sp42.setEditable(true);

        GridPane gp = super.getGridPaneSonderwunsch();
        int row = 1;

        gp.add(lbl41, 0, row);
        gp.add(sp41, 3, row++);

        gp.add(lbl42, 0, row);
        gp.add(sp42, 3, row++);

        gp.add(lbl43, 0, row);
        gp.add(cb43, 3, row);
    }

    public void oeffneInnentuerenView() {
        super.oeffneBasisView(); // geschützter Aufruf innerhalb der erbenden Klasse zulässig
    }

    // --- Getter für Control (später für Logik/Validierung/Speichern nützlich) ---
    public int getAnzahl41() { return sp41.getValue(); }
    public int getAnzahl42() { return sp42.getValue(); }
    public boolean isAuswahl43() { return cb43.isSelected(); }

	@Override
	public void updateSwCheckboxen(int[] ausgewaehlteSw) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean[] holeIsSelectedFuerCheckboxen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int[] checkboxenZuIntArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    protected void berechneUndZeigePreisSonderwuensche() {
        // Optional: Hier könnte man eine reine UI-Summenanzeige ergänzen (nicht gefordert).
		Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Hinweis");
        info.setHeaderText("Nur GUI-Aufgabe");
        info.setContentText("Preis berechnen muss noch implementiert werden, wenn die Task von jemanden bearbeitet wird!");
        info.showAndWait();
    }
	
    // Task-Scope: kein Speichern – wir zeigen nur eine freundliche Meldung
    @Override
    protected void speichereSonderwuensche() {
    	// Sobal die innentuerenControl.speichereSonderwuensche und checkBoxZuIntArray Methoden implementiert wurde das hier ausfuehren, 
    	// statt Alert info usw.
    	// innentuerenControl.speichereSonderwuensche(checkboxenZuIntArray)
        
    	Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Hinweis");
        info.setHeaderText("Nur GUI-Aufgabe");
        info.setContentText("Speichern ist in diesem Task noch nicht vorgesehen.");
        info.showAndWait();
    }
    // TODO: CSV-Export für Innentüren-Sonderwünsch implementieren.
	@Override
	protected void exportiereSonderwuenscheAlsCsv() {
	}

}
