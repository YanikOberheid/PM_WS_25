package gui.innentueren;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InnentuerenView extends BasisView {

    private final InnentuerenControl innentuerenControl;
    
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
        super.oeffneBasisView();
    }

    public int getAnzahl41() { return sp41.getValue(); }
    public int getAnzahl42() { return sp42.getValue(); }
    public boolean isAuswahl43() { return cb43.isSelected(); }

    @Override
    public void updateSwCheckboxen(int[] ausgewaehlteSw) {
        // DB-IDs für diese Kategorie (bei Bedarf auf eure echten IDs anpassen)
        final int ID_4_1_KLARGLAS  = 18; // idSonderwunsch
        final int ID_4_2_MILCHGLAS = 19; // idSonderwunsch
        final int ID_4_3_GARAGE    = 20; // idSonderwunsch

        // UI-Reset
        sp41.getValueFactory().setValue(0);
        sp42.getValueFactory().setValue(0);
        cb43.setSelected(false);

        if (ausgewaehlteSw == null) return;

        // Markierungen anhand der gespeicherten IDs setzen
        for (int id : ausgewaehlteSw) {
            if (id == ID_4_1_KLARGLAS) {
                sp41.getValueFactory().setValue(1);
            } else if (id == ID_4_2_MILCHGLAS) {
                sp42.getValueFactory().setValue(1);
            } else if (id == ID_4_3_GARAGE) {
                cb43.setSelected(true);
            }
        }
    }
	
    @Override
    public boolean[] holeIsSelectedFuerCheckboxen() {
        // Für vorhandene Basiskonzepte: true bedeutet „gewählt“
        return new boolean[] {
            getAnzahl41() > 0,
            getAnzahl42() > 0,
            isAuswahl43()
        };
    }

    @Override
    protected int[] checkboxenZuIntArray() {
        // DB-IDs für diese Kategorie (bei Bedarf auf eure echten IDs anpassen)
        final int ID_4_1_KLARGLAS  = 18;
        final int ID_4_2_MILCHGLAS = 19;
        final int ID_4_3_GARAGE    = 20;

        java.util.ArrayList<Integer> ids = new java.util.ArrayList<>();
        if (getAnzahl41() > 0) ids.add(ID_4_1_KLARGLAS);
        if (getAnzahl42() > 0) ids.add(ID_4_2_MILCHGLAS);
        if (isAuswahl43())     ids.add(ID_4_3_GARAGE);
        return ids.stream().mapToInt(Integer::intValue).toArray();
    }
	
    @Override
    protected void berechneUndZeigePreisSonderwuensche() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Hinweis");
        info.setHeaderText("Nur GUI-Aufgabe");
        info.setContentText("Preis berechnen muss noch implementiert werden, wenn die Task von jemanden bearbeitet wird!");
        info.showAndWait();
    }

    @Override
    protected void speichereSonderwuensche() {
        try {
            int[] ids = checkboxenZuIntArray();
            innentuerenControl.speichereSonderwuensche(ids);
        } catch (Exception ex) {
            zeigeFehler("Fehler", "Speichern nicht möglich.");
        }
    }

    // kurze Dialoge für Control-Rückmeldungen
    public void zeigeFehler(String titel, String nachricht) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titel);
        a.setHeaderText(null);
        a.setContentText(nachricht);
        a.showAndWait();
    }

    public void zeigeInfo(String titel, String nachricht) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titel);
        a.setHeaderText(null);
        a.setContentText(nachricht);
        a.showAndWait();
    }

    @Override
    protected void exportiereSonderwuenscheAlsCsv() {
        // nicht Bestandteil dieses Tasks
    }
}
