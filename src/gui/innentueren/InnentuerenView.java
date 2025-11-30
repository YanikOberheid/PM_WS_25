package gui.innentueren;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
//InnentuerenView.java
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * View für die Sonderwünsche "Innentüren". Task-Scope: Nur GUI-Elemente, kein
 * Speichern/DB.
 */
public class InnentuerenView extends BasisView {

	private final InnentuerenControl control;

	// 4.1 / 4.2: Anzahl-Eingaben (Spinner), 4.3: Checkbox
	private final Label lbl41 = new Label("4.1 Mehrpreis Klarglas-Ausschnitt je Tür (460 €)");
	private final Spinner<Integer> sp41 = new Spinner<>(0, 20, 0);

	private final Label lbl42 = new Label("4.2 Mehrpreis Milchglas-Ausschnitt je Tür (560 €)");
	private final Spinner<Integer> sp42 = new Spinner<>(0, 20, 0);

	private final Label lbl43 = new Label("4.3 Innentür zur Garage als Holztür (660 €) – je 1x");
	private final CheckBox cb43 = new CheckBox();

	public InnentuerenView(InnentuerenControl control, Stage stage) {
		super(stage);
		this.control = control;
		stage.setTitle("Sonderwünsche zu Innentüren");
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

	public void oeffne() {
	    // Titel hast du bereits im Konstruktor gesetzt; erneut setzen ist optional.
	    // Vor dem Anzeigen aus DB laden:
	    control.ladeAuswahl();            // statt getControl()
	    super.oeffneBasisView();          // passt (protected in BasisView)
	}

	// --- echte UI-Werte statt Platzhalter zurückgeben ---
	public int getAnzahl41() {
	    return sp41.getValue();
	}

	public int getAnzahl42() {
	    return sp42.getValue();
	}

	public boolean isGarageTuer() {
	    return cb43.isSelected();
	}


	// --- IDs aus aktueller UI-Auswahl bilden (unverändert außer Zugriff auf Konstanten) ---
	private int[] ermittleAusgewaehlteIds() {
	    java.util.ArrayList<Integer> ids = new java.util.ArrayList<>();
	    if (getAnzahl41() > 0) ids.add(InnentuerenControl.SW_4_1_KLARGLAS);
	    if (getAnzahl42() > 0) ids.add(InnentuerenControl.SW_4_2_MILCHGLAS);
	    if (isGarageTuer())    ids.add(InnentuerenControl.SW_4_3_GARAGE);
	    return ids.stream().mapToInt(Integer::intValue).toArray();
	}

	// --- bestehende DB-Auswahl in der UI markieren ---
	public void updateInnentuerenAuswahl(int[] ids) {
	    sp41.getValueFactory().setValue(0);
	    sp42.getValueFactory().setValue(0);
	    cb43.setSelected(false);

	    if (ids == null) return;
	    for (int id : ids) {
	        if (id == InnentuerenControl.SW_4_1_KLARGLAS) {
	            sp41.getValueFactory().setValue(1);
	        } else if (id == InnentuerenControl.SW_4_2_MILCHGLAS) {
	            sp42.getValueFactory().setValue(1);
	        } else if (id == InnentuerenControl.SW_4_3_GARAGE) {
	            cb43.setSelected(true);
	        }
	    }
	}


	// --- Speichern Button (nur Aufruf ans Control; Logik unverändert) ---
	@Override
	protected void speichereSonderwuensche() {
	    int[] ids = ermittleAusgewaehlteIds();
	    control.speichereSonderwuensche(ids);   // statt getControl()
	}

	@Override
	protected void berechneUndZeigePreisSonderwuensche() {
		// Optional: Hier könnte man eine reine UI-Summenanzeige ergänzen (nicht
		// gefordert).
	}
	
	/** Kurze Fehlermeldung im Dialog anzeigen. */
	public void zeigeFehler(String titel, String nachricht) {
	    Alert a = new Alert(AlertType.ERROR);
	    a.setTitle(titel);
	    a.setHeaderText(null);
	    a.setContentText(nachricht);
	    a.showAndWait();
	}
	
	/** Kurze Info-/Erfolgsmeldung im Dialog anzeigen. */
	public void zeigeInfo(String titel, String nachricht) {
	    Alert a = new Alert(AlertType.INFORMATION);
	    a.setTitle(titel);
	    a.setHeaderText(null);
	    a.setContentText(nachricht);
	    a.showAndWait();
	}
	
	

}