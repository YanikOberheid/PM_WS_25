package gui.fliesen;

import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FliesenView extends BasisView {

    private FliesenControl fliesenControl;
    private int[] angezeigteSonderwuenscheIDs = {1, 2}; // Beispiel-IDs für Fliesen

    // GUI Elemente
    private Label lblFliesenBad = new Label("Bodenfliesen Bad (Großformat)");
    private CheckBox chckBxFliesenBad = new CheckBox();
    
    private Label lblFliesenKueche = new Label("Wandfliesen Küche (Mosaik)");
    private CheckBox chckBxFliesenKueche = new CheckBox();

    public FliesenView(FliesenControl fliesenControl, Stage stage) {
        super(stage);
        this.fliesenControl = fliesenControl;
        stage.setTitle("Sonderwünsche zu Fliesen");
        
        this.initKomponenten();
    }

    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Fliesen-Varianten");

        // Zeile 1: Bad
        super.getGridPaneSonderwunsch().add(lblFliesenBad, 0, 1);
        super.getGridPaneSonderwunsch().add(chckBxFliesenBad, 3, 1);

      
    }

    public void oeffneFliesenView() {
        super.oeffneBasisView();
    }

    /**
     * Setzt die Haken in der GUI basierend auf den geladenen Daten aus der DB.
     */
    public void updateFliesenCheckboxen(int[] ausgewaehlteSw) {
        chckBxFliesenBad.setSelected(false);

        if (ausgewaehlteSw == null) return;

        for (int id : ausgewaehlteSw) {
            if (id == 9) chckBxFliesenBad.setSelected(true);
        }
    }

    /**
     * Wird aufgerufen, wenn "Speichern" geklickt wird (Methode in BasisView definiert)
     */
    @Override
    protected void speichereSonderwuensche() {
        ArrayList<Integer> auswahlListe = new ArrayList<>();
        
        if (chckBxFliesenBad.isSelected()) auswahlListe.add(9);

        // Konvertieren in int[]
        int[] auswahlArray = auswahlListe.stream().mapToInt(i -> i).toArray();

        // An Control übergeben
        this.fliesenControl.speichereSonderwuensche(auswahlArray);
    }

    @Override
    protected void berechneUndZeigePreisSonderwuensche() {
        // Optional: Preisberechnung hier implementieren
    }
}