package gui.heizung;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Vector;

/**
 * View-Klasse für die Sonderwünsche zu Heizungen.
 */
public class HeizungView extends BasisView {

    private HeizungControl heizungControl;

    // --- GUI-Elemente ---

    // 5.1) Mehrpreis für einen zusätzlichen Standard-Heizkörper: 660,- Euro je Stück
    private Label lblStdHeizkoerper =
            new Label("Zusätzlicher Standard-Heizkörper (je Stück)");
    private TextField txtPreisStdHeizkoerper = new TextField();
    private Label lblStdHeizkoerperEuro = new Label("Euro");
    private CheckBox chckBxStdHeizkoerper = new CheckBox();

    // 5.2) Mehrpreis für einen Heizkörper mit glatter Oberfläche: 160,- Euro je Stück
    private Label lblGlattHeizkoerper =
            new Label("Heizkörper mit glatter Oberfläche (je Stück)");
    private TextField txtPreisGlattHeizkoerper = new TextField();
    private Label lblGlattHeizkoerperEuro = new Label("Euro");
    private CheckBox chckBxGlattHeizkoerper = new CheckBox();

    // 5.3) Handtuchheizkörper: 660,- Euro je Stück
    private Label lblHandtuchHeizkoerper =
            new Label("Handtuchheizkörper (je Stück)");
    private TextField txtPreisHandtuchHeizkoerper = new TextField();
    private Label lblHandtuchHeizkoerperEuro = new Label("Euro");
    private CheckBox chckBxHandtuchHeizkoerper = new CheckBox();

    // 5.4) Fußbodenheizung ohne DG: 8.990,- Euro
    private Label lblFbhOhneDG =
            new Label("Fußbodenheizung ohne DG");
    private TextField txtPreisFbhOhneDG = new TextField();
    private Label lblFbhOhneDGEuro = new Label("Euro");
    private CheckBox chckBxFbhOhneDG = new CheckBox();

    // 5.5) Fußbodenheizung mit DG: 9.990,- Euro
    private Label lblFbhMitDG =
            new Label("Fußbodenheizung mit DG");
    private TextField txtPreisFbhMitDG = new TextField();
    private Label lblFbhMitDGEuro = new Label("Euro");
    private CheckBox chckBxFbhMitDG = new CheckBox();

    // Optional: Gesamtpreis-Anzeige
    private Label lblGesamt = new Label("Gesamtpreis Heizungs-Sonderwünsche");
    private TextField txtGesamt = new TextField();
    private Label lblGesamtEuro = new Label("Euro");

    public HeizungView(HeizungControl heizungControl, Stage heizungStage) {
        super(heizungStage);
        this.heizungControl = heizungControl;
        heizungStage.setTitle("Sonderwünsche zu Heizungen");

        this.initKomponenten();
        this.leseHeizungsSonderwuensche();
    }

    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Heizungs-Varianten");

        // 1. Zeile
        super.getGridPaneSonderwunsch().add(lblStdHeizkoerper, 0, 1);
        super.getGridPaneSonderwunsch().add(txtPreisStdHeizkoerper, 1, 1);
        txtPreisStdHeizkoerper.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblStdHeizkoerperEuro, 2, 1);
        super.getGridPaneSonderwunsch().add(chckBxStdHeizkoerper, 3, 1);

        // 2. Zeile
        super.getGridPaneSonderwunsch().add(lblGlattHeizkoerper, 0, 2);
        super.getGridPaneSonderwunsch().add(txtPreisGlattHeizkoerper, 1, 2);
        txtPreisGlattHeizkoerper.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblGlattHeizkoerperEuro, 2, 2);
        super.getGridPaneSonderwunsch().add(chckBxGlattHeizkoerper, 3, 2);

        // 3. Zeile
        super.getGridPaneSonderwunsch().add(lblHandtuchHeizkoerper, 0, 3);
        super.getGridPaneSonderwunsch().add(txtPreisHandtuchHeizkoerper, 1, 3);
        txtPreisHandtuchHeizkoerper.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblHandtuchHeizkoerperEuro, 2, 3);
        super.getGridPaneSonderwunsch().add(chckBxHandtuchHeizkoerper, 3, 3);

        // 4. Zeile
        super.getGridPaneSonderwunsch().add(lblFbhOhneDG, 0, 4);
        super.getGridPaneSonderwunsch().add(txtPreisFbhOhneDG, 1, 4);
        txtPreisFbhOhneDG.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblFbhOhneDGEuro, 2, 4);
        super.getGridPaneSonderwunsch().add(chckBxFbhOhneDG, 3, 4);

        // 5. Zeile
        super.getGridPaneSonderwunsch().add(lblFbhMitDG, 0, 5);
        super.getGridPaneSonderwunsch().add(txtPreisFbhMitDG, 1, 5);
        txtPreisFbhMitDG.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblFbhMitDGEuro, 2, 5);
        super.getGridPaneSonderwunsch().add(chckBxFbhMitDG, 3, 5);

        // Gesamtpreis (optional)
        super.getGridPaneSonderwunsch().add(lblGesamt, 0, 6);
        super.getGridPaneSonderwunsch().add(txtGesamt, 1, 6);
        txtGesamt.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblGesamtEuro, 2, 6);
    }

    public void oeffneHeizungView() {
        super.oeffneBasisView();
    }

    private void leseHeizungsSonderwuensche() {
        this.heizungControl.leseHeizungsSonderwuensche();
    }

    /**
     * Setzt Checkboxen anhand der im Model gespeicherten IDs.
     * Hier wieder Beispiel-ID-Bereich 500–505 (bitte an DB anpassen).
     */
    protected void updateHeizungCheckboxen(int[] ausgewaehlteSw) {
        chckBxStdHeizkoerper.setSelected(false);
        chckBxGlattHeizkoerper.setSelected(false);
        chckBxHandtuchHeizkoerper.setSelected(false);
        chckBxFbhOhneDG.setSelected(false);
        chckBxFbhMitDG.setSelected(false);

        for (int sw : ausgewaehlteSw) {
            if (sw < 500 || sw >= 600) continue;
            switch (sw) {
                case 501:
                    chckBxStdHeizkoerper.setSelected(true);
                    break;
                case 502:
                    chckBxGlattHeizkoerper.setSelected(true);
                    break;
                case 503:
                    chckBxHandtuchHeizkoerper.setSelected(true);
                    break;
                case 504:
                    chckBxFbhOhneDG.setSelected(true);
                    break;
                case 505:
                    chckBxFbhMitDG.setSelected(true);
                    break;
                default:
                    System.out.println("Unbekannte Heizungs-SW-ID: " + sw);
            }
        }

        // Preise anzeigen (hier fix kodiert; bei euch evtl. aus DB holen)
        txtPreisStdHeizkoerper.setText("660,00");
        txtPreisGlattHeizkoerper.setText("160,00");
        txtPreisHandtuchHeizkoerper.setText("660,00");
        txtPreisFbhOhneDG.setText("8990,00");
        txtPreisFbhMitDG.setText("9990,00");

        berechneUndZeigePreisSonderwuensche();
    }

    /* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
    protected void berechneUndZeigePreisSonderwuensche() {
        double preis = 0.0;

        if (chckBxStdHeizkoerper.isSelected()) {
            preis += 660.0;
        }
        if (chckBxGlattHeizkoerper.isSelected()) {
            preis += 160.0;
        }
        if (chckBxHandtuchHeizkoerper.isSelected()) {
            preis += 660.0;
        }
        if (chckBxFbhOhneDG.isSelected()) {
            preis += 8990.0;
        }
        if (chckBxFbhMitDG.isSelected()) {
            preis += 9990.0;
        }

        txtGesamt.setText(String.format("%.2f", preis));
    }

    /* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
    protected void speichereSonderwuensche() {
        Vector<Integer> v = new Vector<>();

        // *** IDs wieder an eure DB anpassen ***
        if (chckBxStdHeizkoerper.isSelected())
            v.add(501);
        if (chckBxGlattHeizkoerper.isSelected())
            v.add(502);
        if (chckBxHandtuchHeizkoerper.isSelected())
            v.add(503);
        if (chckBxFbhOhneDG.isSelected())
            v.add(504);
        if (chckBxFbhMitDG.isSelected())
            v.add(505);

        int[] heizungSw = new int[v.size()];
        for (int i = 0; i < v.size(); i++)
            heizungSw[i] = v.get(i);

        this.heizungControl.speichereSonderwuensche(heizungSw);
    }
}
