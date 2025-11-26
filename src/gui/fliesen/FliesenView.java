package gui.fliesen;

import gui.basis.BasisView;
<<<<<<< HEAD
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu
 * den Fliesen bereitstellt.
 */
public class FliesenView extends BasisView{

    // das Control-Objekt des Fliesen-Fensters
    private FliesenControl fliesenControl;

    private String[][] sonderwuensche;
 // --- Anfang Attribute der grafischen Oberfläche ---
    private Label[] lblPlatzhalter = new Label[6]; // Array für Labels
    private TextField[] txtPreisPlatzhalter = new TextField[6]; // Array für Textfelder
    private Label[] lblPlatzhalterEuro = new Label[6]; // Array für Euro Labels
    private CheckBox[] chckBxPlatzhalter = new CheckBox[6]; // Array für Checkboxes
    //-------Ende Attribute der grafischen Oberflaeche-------

    /**
     * erzeugt ein Fliesen-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param fliesenControl FliesenControl, enthaelt das zugehoerige Control
     * @param fliesenStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public FliesenView (FliesenControl fliesenControl, Stage fliesenStage){
        super(fliesenStage);
        this.fliesenControl = fliesenControl;
        fliesenStage.setTitle("Sonderwünsche zu Fliesen-Varianten");
        sonderwuensche = this.leseFliesenSonderwuensche();
        this.initKomponenten();
        
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Fliesen-Varianten");

        // Initialisieren der Arrays mit den entsprechenden Elementen
        for (int i = 0; i < 6; i++) {
            lblPlatzhalter[i] = new Label(sonderwuensche[i][0]);
            txtPreisPlatzhalter[i] = new TextField();
            lblPlatzhalterEuro[i] = new Label("Euro");
            chckBxPlatzhalter[i] = new CheckBox();
            txtPreisPlatzhalter[i].setText(sonderwuensche[i][1]);

            // Setze Textfelder auf nicht editierbar
            txtPreisPlatzhalter[i].setEditable(false);

            // Füge alle Komponenten zum GridPane hinzu
            super.getGridPaneSonderwunsch().add(lblPlatzhalter[i], 0, i + 1);
            super.getGridPaneSonderwunsch().add(txtPreisPlatzhalter[i], 1, i + 1);
            super.getGridPaneSonderwunsch().add(lblPlatzhalterEuro[i], 2, i + 1);
            super.getGridPaneSonderwunsch().add(chckBxPlatzhalter[i], 3, i + 1);
        }
    }
    /**
     * macht das FliesenView-Objekt sichtbar.
     */
    public void oeffneFliesenView(){
        super.oeffneBasisView();
    }

    private String[][] leseFliesenSonderwuensche(){
        return this.fliesenControl.leseFliesenSonderwuensche();
    }

    /* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
    public void berechneUndZeigePreisSonderwuensche() {
        double gesamtpreis = 0.0;

        for (int i = 0; i < chckBxPlatzhalter.length; i++) {
            // Prüfen, ob die Checkbox ausgewählt ist
            if (chckBxPlatzhalter[i].isSelected()) {
                try {
                    // Den Preis aus der entsprechenden Spalte der sonderwuensche-Array lesen und addieren
                    double preis = Double.parseDouble(sonderwuensche[i][1]);
                    gesamtpreis += preis;
                } catch (NumberFormatException e) {
                    // Falls ein ungültiger Preis-String vorhanden ist, wird dieser ignoriert
                    System.err.println("Ungültiger Preis für Sonderwunsch an Position " + i + ": " + sonderwuensche[i][1]);
                }
            }
        }
        // Berechnen des Gesamtpreises

        // Neues Fenster erstellen
        Stage preisFenster = new Stage();
        preisFenster.initModality(Modality.APPLICATION_MODAL); // Modalität einstellen
        preisFenster.setTitle("Gesamtpreis");

        // Label mit dem Gesamtpreis
        Label lblGesamtpreis = new Label("Gesamtpreis der ausgewählten Sonderwünsche: " + gesamtpreis + " Euro");
        lblGesamtpreis.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

        // Schließen-Button
        Button btnSchliessen = new Button("Schließen");
        btnSchliessen.setOnAction(e -> preisFenster.close());

        // Layout für das Fenster
        VBox vbox = new VBox(10, lblGesamtpreis, btnSchliessen);
        vbox.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        // Szene erstellen und zum Fenster hinzufügen
        Scene scene = new Scene(vbox, 800, 200);
        preisFenster.setScene(scene);

        // Fenster anzeigen
        preisFenster.showAndWait();
        System.out.println(gesamtpreis);
    }

    @Override
    protected void speichereSonderwuensche() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'speichereSonderwuensche'");
    }

   
}


=======
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
>>>>>>> refs/heads/main
