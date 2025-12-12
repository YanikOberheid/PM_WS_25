package gui.innentueren;

import java.util.Vector;

import business.kunde.Sw;
import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InnentuerenView extends BasisView {

    private final InnentuerenControl innentuerenControl;
    
    // --- GUI-Elemente ---
    private Label lblKlarglasTuerInnen =
    		new Label(Sw.KLARGLAS_TUER_INNEN.bes);
    private TextField txtPreisKlarglasTuerInnen = new TextField();
    private Label lblKlarglasTuerInnenEuro = new Label("Euro");
    private Spinner<Integer> spinKlarglasTuerInnen = new Spinner<Integer>(0, 20, 0);
    
    private Label lblMilchglasTuerInnen =
    		new Label(Sw.MILCHGLAS_TUER_INNEN.bes);
    private TextField txtPreisMilchglasTuerInnen = new TextField();
    private Label lblMilchglasTuerInnenEuro = new Label("Euro");
    private Spinner<Integer> spinMilchglasTuerInnen = new Spinner<Integer>(0, 20, 0);
    
    private Label lblHolztuerGarage =
    		new Label(Sw.HOLZTUER_GARAGE.bes);
    private TextField txtPreisHolztuerGarage = new TextField();
    private Label lblHolztuerGarageEuro = new Label("Euro");
    private Spinner<Integer> spinHolztuerGarage = new Spinner<Integer>(0, 20, 0);

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
        
        int row = 1, column = 0;
        // Zeile 1
	   	getGridPaneSonderwunsch().add(lblKlarglasTuerInnen, column++, row);
		getGridPaneSonderwunsch().add(txtPreisKlarglasTuerInnen, column++, row);
		txtPreisKlarglasTuerInnen.setEditable(false);
		txtPreisKlarglasTuerInnen.setText("" + Sw.KLARGLAS_TUER_INNEN.preis);
		getGridPaneSonderwunsch().add(lblKlarglasTuerInnenEuro, column++, row);
		getGridPaneSonderwunsch().add(spinKlarglasTuerInnen, column++, row);
		row++; column = 0;
		
		// Zeile 2
	   	getGridPaneSonderwunsch().add(lblMilchglasTuerInnen, column++, row);
		getGridPaneSonderwunsch().add(txtPreisMilchglasTuerInnen, column++, row);
		txtPreisMilchglasTuerInnen.setEditable(false);
		txtPreisMilchglasTuerInnen.setText("" + Sw.MILCHGLAS_TUER_INNEN.preis);
		getGridPaneSonderwunsch().add(lblMilchglasTuerInnenEuro, column++, row);
		getGridPaneSonderwunsch().add(spinMilchglasTuerInnen, column++, row);
		row++; column = 0;
		
		// Zeile 3
	   	getGridPaneSonderwunsch().add(lblHolztuerGarage, column++, row);
		getGridPaneSonderwunsch().add(txtPreisHolztuerGarage, column++, row);
		txtPreisHolztuerGarage.setEditable(false);
		txtPreisHolztuerGarage.setText("" + Sw.HOLZTUER_GARAGE.preis);
		getGridPaneSonderwunsch().add(lblHolztuerGarageEuro, column++, row);
		getGridPaneSonderwunsch().add(spinHolztuerGarage, column++, row);
		row++; column = 0;
    }

    public void oeffneInnentuerenView() {
        super.oeffneBasisView();
    }
    
    @Deprecated
    @Override
    public void updateSwCheckboxen(int[] ausgewaehlteSw) {
        // DB-IDs für diese Kategorie (bei Bedarf auf eure echten IDs anpassen)
        /*final int ID_4_1_KLARGLAS  = 18; // idSonderwunsch
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
        }*/
    }

    @Deprecated
    @Override
    public boolean[] holeIsSelectedFuerCheckboxen() {
        // Für vorhandene Basiskonzepte: true bedeutet „gewählt“
        /*return new boolean[] {
            getAnzahl41() > 0,
            getAnzahl42() > 0,
            isAuswahl43()
        };*/
    	return null;
    }
    
    @Deprecated
    @Override
    protected int[] checkboxenZuIntArray() {
        // DB-IDs für diese Kategorie (bei Bedarf auf eure echten IDs anpassen)
        /*final int ID_4_1_KLARGLAS  = 18;
        final int ID_4_2_MILCHGLAS = 19;
        final int ID_4_3_GARAGE    = 20;

        java.util.ArrayList<Integer> ids = new java.util.ArrayList<>();
        if (getAnzahl41() > 0) ids.add(ID_4_1_KLARGLAS);
        if (getAnzahl42() > 0) ids.add(ID_4_2_MILCHGLAS);
        if (isAuswahl43())     ids.add(ID_4_3_GARAGE);
        return ids.stream().mapToInt(Integer::intValue).toArray();*/
        return new int[] {};
    }
	
    @Override
    protected void berechneUndZeigePreisSonderwuensche() {
        // TODO
    }

    @Override
    protected void speichereSonderwuensche() {
        innentuerenControl.speichereSonderwuensche(checkboxenZuAnzahlSonderwuensche());
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

	@Override
	public void updateSwInView(int[][] ausgewaehlteSw) {
		// Alles zurücksetzen
    	spinKlarglasTuerInnen.getValueFactory().setValue(0);
        spinMilchglasTuerInnen.getValueFactory().setValue(0);
        spinHolztuerGarage.getValueFactory().setValue(0);

        // Checkboxen für vorkommende IDs ankreuzen
        if (ausgewaehlteSw == null) return;
        for (int[] sw : ausgewaehlteSw) {
        	if (sw == null || sw.length != 2) continue;
            switch (Sw.findeMitId(sw[0])) {
                case KLARGLAS_TUER_INNEN:
                	spinKlarglasTuerInnen.getValueFactory().setValue(sw[1]);
                    break;
                case MILCHGLAS_TUER_INNEN:
                	spinMilchglasTuerInnen.getValueFactory().setValue(sw[1]);
                    break;
                case HOLZTUER_GARAGE:
                	spinHolztuerGarage.getValueFactory().setValue(sw[1]);
                	break;
                default:
                    System.out.println(
                    		"Unbekannte Sonderwunsch-ID zu Innentüren: " + sw[0]);
            }
        }
	}
	
	@Override
	protected int[][] checkboxenZuAnzahlSonderwuensche() {
    	Vector<int[]> v = new Vector<>();
    	
    	int anzahl = 0;
    	
    	if (spinKlarglasTuerInnen.getValue() > 0){
    		anzahl = spinKlarglasTuerInnen.getValue();
    		v.add(new int[]{ Sw.KLARGLAS_TUER_INNEN.id, anzahl });
    	}
    	if (spinMilchglasTuerInnen.getValue() > 0){
    		anzahl = spinMilchglasTuerInnen.getValue();
    		v.add(new int[]{ Sw.MILCHGLAS_TUER_INNEN.id, anzahl });
    	}
    	if (spinHolztuerGarage.getValue() > 0){
    		anzahl = spinHolztuerGarage.getValue();
    		v.add(new int[]{ Sw.HOLZTUER_GARAGE.id, anzahl });
    	}
        
        return getAlleTupel(v);
    }
	
	@Override
    protected int[][] getAlleTupel(Vector<int[]> v) {
        int[][] result = new int[v.size()][];

        for (int i = 0; i < v.size(); i++) {
            result[i] = v.get(i);
        }

        return result;
    }
	
	/*
	@Override
	public int[][] spinnerZu2DIntArray() {
		return new int[][] {
			{Sw.KLARGLAS_TUER_INNEN.id, spinKlarglasTuerInnen.getValue()},
			{Sw.MILCHGLAS_TUER_INNEN.id, spinMilchglasTuerInnen.getValue()},
			{Sw.HOLZTUER_GARAGE.id, spinHolztuerGarage.getValue()},
	};
	}*/
}
