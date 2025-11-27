package gui.heizung;

import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Vector;

/**
 * View für die Sonderwünsche zu Heizungen.
 */
public class HeizungView extends BasisView {

    private HeizungControl heizungControl;

    // IDs aus der DB (Kategorie 50)
    private static final int SW_STD_HEIZKOERPER   = 13;
    private static final int SW_GLATT_HEIZKOERPER = 14;
    private static final int SW_HANDTUCH          = 15;
    private static final int SW_FBH_OHNE_DG       = 16;
    private static final int SW_FBH_MIT_DG        = 17;

    // --- GUI-Elemente ---

    private Label lblStdHeizkoerper =
            new Label("Zusätzlicher Standard-Heizkörper (je Stück)");
    private TextField txtStdHeizkoerper = new TextField();
    private Label lblStdHeizkoerperEuro = new Label("Euro");
    private CheckBox chckBxStdHeizkoerper = new CheckBox();

    private Label lblGlattHeizkoerper =
            new Label("Heizkörper mit glatter Oberfläche (je Stück)");
    private TextField txtGlattHeizkoerper = new TextField();
    private Label lblGlattHeizkoerperEuro = new Label("Euro");
    private CheckBox chckBxGlattHeizkoerper = new CheckBox();

    private Label lblHandtuchHeizkoerper =
            new Label("Handtuchheizkörper (je Stück)");
    private TextField txtHandtuchHeizkoerper = new TextField();
    private Label lblHandtuchHeizkoerperEuro = new Label("Euro");
    private CheckBox chckBxHandtuchHeizkoerper = new CheckBox();

    private Label lblFbhOhneDG =
            new Label("Fußbodenheizung ohne DG");
    private TextField txtFbhOhneDG = new TextField();
    private Label lblFbhOhneDGEuro = new Label("Euro");
    private CheckBox chckBxFbhOhneDG = new CheckBox();

    private Label lblFbhMitDG =
            new Label("Fußbodenheizung mit DG");
    private TextField txtFbhMitDG = new TextField();
    private Label lblFbhMitDGEuro = new Label("Euro");
    private CheckBox chckBxFbhMitDG = new CheckBox();

    // Gesamtpreis-Anzeige
    private Label lblGesamt = new Label("Gesamtpreis Heizungs-Sonderwünsche");
    private TextField txtGesamt = new TextField();
    private Label lblGesamtEuro = new Label("Euro");

    public HeizungView(HeizungControl heizungControl, Stage heizungStage) {
        super(heizungStage);
        this.heizungControl = heizungControl;
        heizungStage.setTitle("Sonderwünsche zu Heizungen");

        initKomponenten();
        leseHeizungsSonderwuensche();
    }

    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Heizungs-Varianten");

        // Zeile 1
        super.getGridPaneSonderwunsch().add(lblStdHeizkoerper, 0, 1);
        super.getGridPaneSonderwunsch().add(txtStdHeizkoerper, 1, 1);
        txtStdHeizkoerper.setEditable(false);
        txtStdHeizkoerper.setText("660");
        super.getGridPaneSonderwunsch().add(lblStdHeizkoerperEuro, 2, 1);
        super.getGridPaneSonderwunsch().add(chckBxStdHeizkoerper, 3, 1);

        // Zeile 2
        super.getGridPaneSonderwunsch().add(lblGlattHeizkoerper, 0, 2);
        super.getGridPaneSonderwunsch().add(txtGlattHeizkoerper, 1, 2);
        txtGlattHeizkoerper.setEditable(false);
        txtGlattHeizkoerper.setText("160");
        super.getGridPaneSonderwunsch().add(lblGlattHeizkoerperEuro, 2, 2);
        super.getGridPaneSonderwunsch().add(chckBxGlattHeizkoerper, 3, 2);

        // Zeile 3
        super.getGridPaneSonderwunsch().add(lblHandtuchHeizkoerper, 0, 3);
        super.getGridPaneSonderwunsch().add(txtHandtuchHeizkoerper, 1, 3);
        txtHandtuchHeizkoerper.setEditable(false);
        txtHandtuchHeizkoerper.setText("660");
        super.getGridPaneSonderwunsch().add(lblHandtuchHeizkoerperEuro, 2, 3);
        super.getGridPaneSonderwunsch().add(chckBxHandtuchHeizkoerper, 3, 3);

        // Zeile 4
        super.getGridPaneSonderwunsch().add(lblFbhOhneDG, 0, 4);
        super.getGridPaneSonderwunsch().add(txtFbhOhneDG, 1, 4);
        txtFbhOhneDG.setEditable(false);
        txtFbhOhneDG.setText("8990");
        super.getGridPaneSonderwunsch().add(lblFbhOhneDGEuro, 2, 4);
        super.getGridPaneSonderwunsch().add(chckBxFbhOhneDG, 3, 4);

        // Zeile 5
        super.getGridPaneSonderwunsch().add(lblFbhMitDG, 0, 5);
        super.getGridPaneSonderwunsch().add(txtFbhMitDG, 1, 5);
        txtFbhMitDG.setEditable(false);
        txtFbhMitDG.setText("9990");
        super.getGridPaneSonderwunsch().add(lblFbhMitDGEuro, 2, 5);
        super.getGridPaneSonderwunsch().add(chckBxFbhMitDG, 3, 5);

        // Gesamtpreis (Zeile 6)
        super.getGridPaneSonderwunsch().add(lblGesamt, 0, 6);
        super.getGridPaneSonderwunsch().add(txtGesamt, 1, 6);
        txtGesamt.setEditable(false);
        super.getGridPaneSonderwunsch().add(lblGesamtEuro, 2, 6);
    }

    public void oeffneHeizungView() {
        super.oeffneBasisView();
    }

    private void leseHeizungsSonderwuensche() {
        heizungControl.leseHeizungsSonderwuensche();
    }

    /** Checkboxen anhand der gespeicherten IDs setzen. */
    protected void updateHeizungCheckboxen(int[] ausgewaehlteSw) {

        // alles zurücksetzen
        chckBxStdHeizkoerper.setSelected(false);
        chckBxGlattHeizkoerper.setSelected(false);
        chckBxHandtuchHeizkoerper.setSelected(false);
        chckBxFbhOhneDG.setSelected(false);
        chckBxFbhMitDG.setSelected(false);

        if (ausgewaehlteSw != null) {
            for (int sw : ausgewaehlteSw) {
                switch (sw) {
                    case SW_STD_HEIZKOERPER:
                        chckBxStdHeizkoerper.setSelected(true);
                        break;
                    case SW_GLATT_HEIZKOERPER:
                        chckBxGlattHeizkoerper.setSelected(true);
                        break;
                    case SW_HANDTUCH:
                        chckBxHandtuchHeizkoerper.setSelected(true);
                        break;
                    case SW_FBH_OHNE_DG:
                        chckBxFbhOhneDG.setSelected(true);
                        break;
                    case SW_FBH_MIT_DG:
                        chckBxFbhMitDG.setSelected(true);
                        break;
                    default:
                        System.out.println("Unbekannte Heizungs-SW-ID: " + sw);
                }
            }
        }

        berechneUndZeigePreisSonderwuensche();
    }

    /** Gesamtpreis berechnen und anzeigen. */
    protected void berechneUndZeigePreisSonderwuensche() {
        double preis = 0.0;

        if (chckBxStdHeizkoerper.isSelected())   preis += 660.0;
        if (chckBxGlattHeizkoerper.isSelected()) preis += 160.0;
        if (chckBxHandtuchHeizkoerper.isSelected()) preis += 660.0;
        if (chckBxFbhOhneDG.isSelected())        preis += 8990.0;
        if (chckBxFbhMitDG.isSelected())         preis += 9990.0;

        txtGesamt.setText(String.format("%.2f", preis));
    }

    /** Auswahl speichern (IDs aus den Checkboxen sammeln). */
    protected void speichereSonderwuensche() {
        Vector<Integer> v = new Vector<>();

        if (chckBxStdHeizkoerper.isSelected())
            v.add(SW_STD_HEIZKOERPER);
        if (chckBxGlattHeizkoerper.isSelected())
            v.add(SW_GLATT_HEIZKOERPER);
        if (chckBxHandtuchHeizkoerper.isSelected())
            v.add(SW_HANDTUCH);
        if (chckBxFbhOhneDG.isSelected())
            v.add(SW_FBH_OHNE_DG);
        if (chckBxFbhMitDG.isSelected())
            v.add(SW_FBH_MIT_DG);

        int[] heizungSw = new int[v.size()];
        for (int i = 0; i < v.size(); i++)
            heizungSw[i] = v.get(i);

        heizungControl.speichereSonderwuensche(heizungSw);
    }
}
