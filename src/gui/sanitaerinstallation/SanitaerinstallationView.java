package gui.sanitaerinstallation;

import java.util.Vector;

import business.kunde.Sw;
import gui.basis.BasisView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SanitaerinstallationView extends BasisView {
	
	private SanitaerinstallationControl sanitaerControl;
	
	// --- GUI-Elemente ---
	private Label lblWaschbeckenOGGross =
			new Label(Sw.WASCHBECKEN_OG_GROSS.bes);
	private TextField txtWaschbeckenOGGross = new TextField();
	private Label lblWaschbeckenOGGrossEuro = new Label("Euro");
	private CheckBox chckBxWaschbeckenOGGross = new CheckBox();
	
	private Label lblWaschbeckenDGGross =
			new Label(Sw.WASCHBECKEN_DG_GROSS.bes);
	private TextField txtWaschbeckenDGGross = new TextField();
	private Label lblWaschbeckenDGGrossEuro = new Label("Euro");
	private CheckBox chckBxWaschbeckenDGGross = new CheckBox();
	
	private Label lblDuscheOGTief =
			new Label(Sw.DUSCHE_OG_TIEF.bes);
	private TextField txtDuscheOGTief = new TextField();
	private Label lblDuscheOGTiefEuro = new Label("Euro");
	private CheckBox chckBxDuscheOGTief = new CheckBox();
	
	private Label lblDuscheDGTief =
			new Label(Sw.DUSCHE_DG_TIEF.bes);
	private TextField txtDuscheDGTief = new TextField();
	private Label lblDuscheDGTiefEuro = new Label("Euro");
	private CheckBox chckBxDuscheDGTief = new CheckBox();
	
	public SanitaerinstallationView(
			SanitaerinstallationControl sanitaerControl,
			Stage stage) {
		super(stage);
		this.sanitaerControl = sanitaerControl;
		stage.setTitle("Sonderwünsche zur Sanitärinstallation");
		initKomponenten();
	}
	
	protected void initKomponenten() {
		super.initKomponenten();
		super.getLblSonderwunsch().setText("Sanitärinstallation");

		int row = 1;
		int column = 0;
		// super.getGridPaneSonderwunsch().add(Element, Spalte, Zeile);
        // Zeile 1
		getGridPaneSonderwunsch().add(lblWaschbeckenOGGross, column++, row);
        getGridPaneSonderwunsch().add(txtWaschbeckenOGGross, column++, row);
        txtWaschbeckenOGGross.setEditable(false);
        txtWaschbeckenOGGross.setText("" + Sw.WASCHBECKEN_OG_GROSS.preis);
        getGridPaneSonderwunsch().add(lblWaschbeckenOGGrossEuro, column++, row);
        getGridPaneSonderwunsch().add(chckBxWaschbeckenOGGross, column++, row);
        row++;
        column = 0;
        
        // Zeile 2
        getGridPaneSonderwunsch().add(lblWaschbeckenDGGross, column++, row);
        getGridPaneSonderwunsch().add(txtWaschbeckenDGGross, column++, row);
        txtWaschbeckenDGGross.setEditable(false);
        txtWaschbeckenDGGross.setText("" + Sw.WASCHBECKEN_DG_GROSS.preis);
        getGridPaneSonderwunsch().add(lblWaschbeckenDGGrossEuro, column++, row);
        getGridPaneSonderwunsch().add(chckBxWaschbeckenDGGross, column++, row);
        row++;
        column = 0;
        
        // Zeile 3
        getGridPaneSonderwunsch().add(lblDuscheOGTief, column++, row);
        getGridPaneSonderwunsch().add(txtDuscheOGTief, column++, row);
        txtDuscheOGTief.setEditable(false);
        txtDuscheOGTief.setText("" + Sw.DUSCHE_OG_TIEF.preis);
        getGridPaneSonderwunsch().add(lblDuscheOGTiefEuro, column++, row);
        getGridPaneSonderwunsch().add(chckBxDuscheOGTief, column++, row);
        row++;
        column = 0;
        
        // Zeile 4
        getGridPaneSonderwunsch().add(lblDuscheDGTief, column++, row);
        getGridPaneSonderwunsch().add(txtDuscheDGTief, column++, row);
        txtDuscheDGTief.setEditable(false);
        txtDuscheDGTief.setText("" + Sw.DUSCHE_DG_TIEF.preis);
        getGridPaneSonderwunsch().add(lblDuscheDGTiefEuro, column++, row);
        getGridPaneSonderwunsch().add(chckBxDuscheDGTief, column++, row);
        row++;
        column = 0;
	}
	
	public void oeffneSanitaerinstallationView() {
		super.oeffneBasisView();
	}
	
	private void leseSanitaerinstallationSonderwuensche() {
		sanitaerControl.leseSanitaerinstallationSonderwuensche();
	}

	@Override
	protected void exportiereSonderwuenscheAlsCsv() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void berechneUndZeigePreisSonderwuensche() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void speichereSonderwuensche() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean[] holeIsSelectedFuerCheckboxen() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Deprecated
	@Override
	public void updateSwCheckboxen(int[] ausgewaehlteSw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSwInView(int[][] ausgewaehlteSw) {
		// TODO Auto-generated method stub
		
	}

	@Deprecated
	@Override
	protected int[] checkboxenZuIntArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	@Override
	protected int[][] checkboxenZuAnzahlSonderwuensche() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int[][] getAlleTupel(Vector<int[]> v) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
