package gui.fenster;

import java.util.Vector;

import business.kunde.Sw;
import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * Fenster mit den Sonderwuenschen zu
 * Fenstern und Außentüren
 */
public class FensterView extends BasisView {

    private FensterControl fensterControl;
    
    // GUI-Elemente
    private Label lblSchiebetuerEg =
    		new Label(Sw.STUEREN_TERRASSE.bes);
    private TextField txtPreisSchiebetuerEg	= new TextField();
    private Label lblSchiebetuerEgEuro		= new Label("Euro");
    private CheckBox chckBxSchiebetuerEg	= new CheckBox();
    
    private Label lblSchiebetuerDg =
    		new Label(Sw.STUEREN_DACHTERRASSE.bes);
    private TextField txtPreisSchiebetuerDg	= new TextField();
    private Label lblSchiebetuerDgEuro		= new Label("Euro");
    private CheckBox chckBxSchiebetuerDg	= new CheckBox();
    
    private Label lblEinbruchschutz =
    		new Label(Sw.EBS_HAUSTUER.bes);
    private TextField txtPreisEinbruchschutz= new TextField();
    private Label lblEinbruchschutzEuro		= new Label("Euro");
    private CheckBox chckBxEinbruchschutz 	= new CheckBox();
    
    private Label lblVorbereitungEg =
    		new Label(Sw.VEAR_EG.bes);
    private TextField txtPreisVorbereitungEg= new TextField();
    private Label lblVorbereitungEgEuro		= new Label("Euro");
    private CheckBox chckBxVorbereitungEg 	= new CheckBox();
    
    private Label lblVorbereitungOg =
    		new Label(Sw.VEAR_OG.bes);
    private TextField txtPreisVorbereitungOg= new TextField();
    private Label lblVorbereitungOgEuro		= new Label("Euro");
    private CheckBox chckBxVorbereitungOg	= new CheckBox();
    
    private Label lblVorbereitungDg =
    		new Label(Sw.VEAR_DG.bes);
    private TextField txtPreisVorbereitungDg= new TextField();
    private Label lblVorbereitungDgEuro		= new Label("Euro");
    private CheckBox chckBxVorbereitungDg 	= new CheckBox();
    
    private Label lblRollladenEg =
    		new Label(Sw.ER_EG.bes);
    private TextField txtPreisRollladenEg	= new TextField();
    private Label lblRollladenEgEuro		= new Label("Euro");
    private CheckBox chckBxRollladenEg 		= new CheckBox();
    
    private Label lblRollladenOg =
    		new Label(Sw.ER_OG.bes);
    private TextField txtPreisRollladenOg 	= new TextField();
    private Label lblRollladenOgEuro		= new Label("Euro");
    private CheckBox chckBxRollladenOg 		= new CheckBox();
    
    private Label lblRollladenDg =
    		new Label(Sw.ER_DG.bes);
    private TextField txtPreisRollladenDg 	= new TextField();
    private Label lblRollladenDgEuro		= new Label("Euro");
    private CheckBox chckBxRollladenDg 		= new CheckBox();
    

    public FensterView(FensterControl fensterControl, Stage fensterStage) {
        super(fensterStage);
        this.fensterControl = fensterControl;
        fensterStage.setTitle("Sonderwuensche zu Fenstern und Außentueren");
        this.initKomponenten();
        // WICHTIG: keine Daten hier laden! Control macht das beim Öffnen.
        // KEIN: this.leseFensterSonderwuensche();
        //Bitttee !!!
    }

    @Override
    protected void initKomponenten() {
        super.initKomponenten();
        super.getLblSonderwunsch().setText("Fenster und Außentueren");

        int row = 1;

        // Schiebetueren und Haustuer
        getGridPaneSonderwunsch().add(lblSchiebetueren, 0, row++);
        getGridPaneSonderwunsch().add(chckBxSchiebetuerEg, 0, row++);
        getGridPaneSonderwunsch().add(chckBxSchiebetuerDg, 0, row++);
        getGridPaneSonderwunsch().add(chckBxEinbruchschutz, 0, row++);

        row++;

        // Vorbereitung Rolllaeden
        getGridPaneSonderwunsch().add(lblVorbereitung, 0, row++);
        getGridPaneSonderwunsch().add(chckBxVorbereitungEg, 0, row++);
        getGridPaneSonderwunsch().add(chckBxVorbereitungOg, 0, row++);
        getGridPaneSonderwunsch().add(chckBxVorbereitungDg, 0, row++);

        row++;

        // Elektrische Rolllaeden
        getGridPaneSonderwunsch().add(lblRolllaeden, 0, row++);
        getGridPaneSonderwunsch().add(chckBxRollladenEg, 0, row++);
        getGridPaneSonderwunsch().add(chckBxRollladenOg, 0, row++);
        getGridPaneSonderwunsch().add(chckBxRollladenDg, 0, row++);

        // Optional: Gesamtpreis wie bei Heizung
        getGridPaneSonderwunsch().add(lblGesamt, 0, row);
        getGridPaneSonderwunsch().add(txtGesamt, 1, row);
        txtGesamt.setEditable(false);
        getGridPaneSonderwunsch().add(lblGesamtEuro, 2, row);
    }

    public void oeffneFensterView() {
        super.oeffneBasisView();
    }


    /** Checkboxen anhand der gespeicherten IDs setzen. (wie bei andere Klassen.) */
    @Override
    public void updateSwCheckboxen(int[] ausgewaehlteSw) {

        // alle auf false setzen
        chckBxSchiebetuerEg.setSelected(false);
        chckBxSchiebetuerDg.setSelected(false);
        chckBxEinbruchschutz.setSelected(false);
        chckBxVorbereitungEg.setSelected(false);
        chckBxVorbereitungOg.setSelected(false);
        chckBxVorbereitungDg.setSelected(false);
        chckBxRollladenEg.setSelected(false);
        chckBxRollladenOg.setSelected(false);
        chckBxRollladenDg.setSelected(false);

        if (ausgewaehlteSw == null) {
            return;
        }
        
        for (int swId : ausgewaehlteSw) {
            // **Neu:** nur Fenster-/Außentüren-IDs akzeptieren (301–309)
            if (swId < 301 || swId > 309) {
                continue; // fachfremde IDs ignorieren
            }
            
        
            Sw sw = Sw.findeMitId(swId);
            switch (sw) {
                case STUEREN_TERRASSE:
                    chckBxSchiebetuerEg.setSelected(true);
                    break;
                case STUEREN_DACHTERRASSE:
                    chckBxSchiebetuerDg.setSelected(true);
                    break;
                case EBS_HAUSTUER:
                	chckBxEinbruchschutz.setSelected(true);
                    break;
                case VEAR_EG:
                    chckBxVorbereitungEg.setSelected(true);
                    break;
                case VEAR_OG:
                    chckBxVorbereitungOg.setSelected(true);
                    break;
                case VEAR_DG:
                    chckBxVorbereitungDg.setSelected(true);
                    break;
                case ER_EG:
                    chckBxRollladenEg.setSelected(true);
                    break;
                case ER_OG:
                    chckBxRollladenOg.setSelected(true);
                    break;
                case ER_DG:
                    chckBxRollladenDg.setSelected(true);
                    break;
                default:
                    System.out.println("Unbekannte Sonderwunsch-ID zu Fenstern: " + swId);
            }
        }
    }

    @Override
    public boolean[] holeIsSelectedFuerCheckboxen() {
        return new boolean[] {
                chckBxSchiebetuerEg.isSelected(),
                chckBxSchiebetuerDg.isSelected(),
                chckBxEinbruchschutz.isSelected(),
                chckBxVorbereitungEg.isSelected(),
                chckBxVorbereitungOg.isSelected(),
                chckBxVorbereitungDg.isSelected(),
                chckBxRollladenEg.isSelected(),
                chckBxRollladenOg.isSelected(),
                chckBxRollladenDg.isSelected()
        };
    }

    @Override
    protected int[] checkboxenZuIntArray() {
        Vector<Integer> v = new Vector<>();

        if (chckBxSchiebetuerEg.isSelected())
            v.add(Sw.STUEREN_TERRASSE.id);
        if (chckBxSchiebetuerDg.isSelected())
            v.add(Sw.STUEREN_DACHTERRASSE.id);
        if (chckBxEinbruchschutz.isSelected())
            v.add(Sw.EBS_HAUSTUER.id);

        if (chckBxVorbereitungEg.isSelected())
            v.add(Sw.VEAR_EG.id);
        if (chckBxVorbereitungOg.isSelected())
            v.add(Sw.VEAR_OG.id);
        if (chckBxVorbereitungDg.isSelected())
            v.add(Sw.VEAR_DG.id);

        if (chckBxRollladenEg.isSelected())
            v.add(Sw.ER_EG.id);
        if (chckBxRollladenOg.isSelected())
            v.add(Sw.ER_OG.id);
        if (chckBxRollladenDg.isSelected())
            v.add(Sw.ER_DG.id);

        int[] fensterSw = new int[v.size()];
        for (int i = 0; i < v.size(); i++)
            fensterSw[i] = v.get(i);

        return fensterSw;
    }

    @Override
    protected void berechneUndZeigePreisSonderwuensche() {
        // Konstellationsprüfung kommt später (Prio [5])

        double preis = 0.0;

        if (chckBxSchiebetuerEg.isSelected())   preis += Sw.STUEREN_TERRASSE.preis;
        if (chckBxSchiebetuerDg.isSelected())   preis += Sw.STUEREN_DACHTERRASSE.preis;
        if (chckBxEinbruchschutz.isSelected())  preis += Sw.EBS_HAUSTUER.preis;

        if (chckBxVorbereitungEg.isSelected())  preis += Sw.VEAR_EG.preis;
        if (chckBxVorbereitungOg.isSelected())  preis += Sw.VEAR_OG.preis;
        if (chckBxVorbereitungDg.isSelected())  preis += Sw.VEAR_DG.preis;

        if (chckBxRollladenEg.isSelected())     preis += Sw.ER_EG.preis;
        if (chckBxRollladenOg.isSelected())     preis += Sw.ER_OG.preis;
        if (chckBxRollladenDg.isSelected())     preis += Sw.ER_DG.preis;

        txtGesamt.setText(String.format("%.2f", preis));
    }

    /**
     * Wird von BasisView-Button "Speichern" aufgerufen.
     * Übergibt die Auswahl zum Speichern an Control.
     */
    @Override
    protected void speichereSonderwuensche() {
        fensterControl.speichereSonderwuensche(checkboxenZuIntArray());
    }
    
    // TODO: CSV-Export für Fenster-Sonderwünsch implementieren.
	@Override
	protected void exportiereSonderwuenscheAlsCsv() {
	}
}
