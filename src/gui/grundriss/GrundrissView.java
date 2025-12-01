package gui.grundriss;

import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Vector;

import business.kunde.Sw;
import gui.basis.BasisView;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu 
 * den Grundrissvarianten bereitstellt.
 */
public class GrundrissView extends BasisView{

 	// das Control-Objekt des Grundriss-Fensters
	private GrundrissControl grundrissControl;
   
    //---Anfang Attribute der grafischen Oberflaeche---
    private Label lblWandKueche =
    		new Label(Sw.WAND_KUECHE.bes);
    private TextField txtPreisWandKueche 	= new TextField();
    private Label lblWandKuecheEuro 		= new Label("Euro");
    private CheckBox chckBxWandKueche 		= new CheckBox();
    
    private Label lblTuerKueche =
    		new Label(Sw.TUER_KUECHE.bes);
    private TextField txtPreisTuerKueche 	= new TextField();
    private Label lblTuerKuecheEuro 		= new Label("Euro");
    private CheckBox chckBxTuerKueche 		= new CheckBox();
    
    private Label lblGrossesZimmerOG =
    		new Label(Sw.GROSSES_ZIMMER_OG.bes);
    private TextField txtPreisGrossesZimmerOG = new TextField();
    private Label lblGrossesZimmerOGEuro 	= new Label("Euro");
    private CheckBox chckBxGrossesZimmerOG 	= new CheckBox();
    
    private Label lblTreppenraumDG =
    		new Label(Sw.TREPPENRAUM_DG.bes);
    private TextField txtPreisTreppenraumDG = new TextField();
    private Label lblTreppenraumDGEuro 		= new Label("Euro");
    private CheckBox chckBxTreppenraumDG 	= new CheckBox();
    
    private Label lblVorrichtungBadDG =
    		new Label(Sw.VORRICHTUNG_BAD_DG.bes);
    private TextField txtPreisVorrichtungBadDG = new TextField();
    private Label lblVorrichtungBadDGEuro 	= new Label("Euro");
    private CheckBox chckBxVorrichtungBadDG	= new CheckBox();
    
    private Label lblAusfuehrungBadDG =
    		new Label(Sw.AUSFUEHRUNG_BAD_DG.bes);
    private TextField txtPreisAusfuehrungBadDG = new TextField();
    private Label lblAusfuehrungBadDGEuro 	= new Label("Euro");
    private CheckBox chckBxAusfuehrungBadDG = new CheckBox();
    //-------Ende Attribute der grafischen Oberflaeche-------
  
    /**
     * erzeugt ein GrundrissView-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param grundrissControl GrundrissControl, enthaelt das zugehoerige Control
     * @param grundrissStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public GrundrissView (GrundrissControl grundrissControl, Stage grundrissStage){
    	super(grundrissStage);
        this.grundrissControl = grundrissControl;
        grundrissStage.setTitle("Sonderwuensche zu Grundriss-Varianten");
                
	    initKomponenten();
    }
  
    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
    	super.initKomponenten(); 
       	getLblSonderwunsch().setText("Grundriss-Varianten");
       	
       	// super.getGridPaneSonderwunsch().add(Element, Spalte, Zeile);
       	// Zeile 1
       	getGridPaneSonderwunsch().add(lblWandKueche, 0, 1);
    	getGridPaneSonderwunsch().add(txtPreisWandKueche, 1, 1);
    	txtPreisWandKueche.setEditable(false);
    	txtPreisWandKueche.setText("" + Sw.WAND_KUECHE.preis);
    	getGridPaneSonderwunsch().add(lblWandKuecheEuro, 2, 1);
    	getGridPaneSonderwunsch().add(chckBxWandKueche, 3, 1);
    	
    	// Zeile 2
    	getGridPaneSonderwunsch().add(lblTuerKueche, 0, 2);
    	getGridPaneSonderwunsch().add(txtPreisTuerKueche, 1, 2);
    	txtPreisTuerKueche.setEditable(false);
    	txtPreisTuerKueche.setText("" + Sw.TUER_KUECHE.preis);
    	getGridPaneSonderwunsch().add(lblTuerKuecheEuro, 2, 2);
    	getGridPaneSonderwunsch().add(chckBxTuerKueche, 3, 2);
    	
    	// Zeile 3
    	getGridPaneSonderwunsch().add(lblGrossesZimmerOG, 0, 3);
    	getGridPaneSonderwunsch().add(txtPreisGrossesZimmerOG, 1, 3);
    	txtPreisGrossesZimmerOG.setEditable(false);
    	txtPreisGrossesZimmerOG.setText("" + Sw.GROSSES_ZIMMER_OG.preis);
    	getGridPaneSonderwunsch().add(lblGrossesZimmerOGEuro, 2, 3);
    	getGridPaneSonderwunsch().add(chckBxGrossesZimmerOG, 3, 3);
    	
    	// Zeile 4
    	getGridPaneSonderwunsch().add(lblTreppenraumDG, 0, 4);
    	getGridPaneSonderwunsch().add(txtPreisTreppenraumDG, 1, 4);
    	txtPreisTreppenraumDG.setEditable(false);
    	txtPreisTreppenraumDG.setText("" + Sw.TREPPENRAUM_DG.preis);
		getGridPaneSonderwunsch().add(lblTreppenraumDGEuro, 2, 4);
    	getGridPaneSonderwunsch().add(chckBxTreppenraumDG, 3, 4);
    	
    	// Zeile 5
    	getGridPaneSonderwunsch().add(lblVorrichtungBadDG, 0, 5);
    	getGridPaneSonderwunsch().add(txtPreisVorrichtungBadDG, 1, 5);
    	txtPreisVorrichtungBadDG.setEditable(false);
    	txtPreisVorrichtungBadDG.setText("" + Sw.VORRICHTUNG_BAD_DG.preis);
		getGridPaneSonderwunsch().add(lblVorrichtungBadDGEuro, 2, 5);
    	getGridPaneSonderwunsch().add(chckBxVorrichtungBadDG, 3, 5);
    	
    	// Zeile 6
    	getGridPaneSonderwunsch().add(lblAusfuehrungBadDG, 0, 6);
    	getGridPaneSonderwunsch().add(txtPreisAusfuehrungBadDG, 1, 6);
		txtPreisAusfuehrungBadDG.setEditable(false);
		txtPreisAusfuehrungBadDG.setText("" + Sw.AUSFUEHRUNG_BAD_DG.preis);
		getGridPaneSonderwunsch().add(lblAusfuehrungBadDGEuro, 2, 6);
    	getGridPaneSonderwunsch().add(chckBxAusfuehrungBadDG, 3, 6);
    	
    	// Gesamtpreis (Zeile 7) - aus BasisView
        getGridPaneSonderwunsch().add(lblGesamt, 0, 7);
        getGridPaneSonderwunsch().add(txtGesamt, 1, 7);
        txtGesamt.setEditable(false);
        getGridPaneSonderwunsch().add(lblGesamtEuro, 2, 7);
    }  
    
    /**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView(){ 
		super.oeffneBasisView();
	}
    
    private void leseGrundrissSonderwuensche(){
    	this.grundrissControl.leseGrundrissSonderwuensche();
    }
    
    @Override
    public void updateSwCheckboxen(int[] ausgewaehlteSw) {
    	// Alles zurücksetzen
    	chckBxWandKueche.setSelected(false);
    	chckBxTuerKueche.setSelected(false);
    	chckBxGrossesZimmerOG.setSelected(false);
    	chckBxTreppenraumDG.setSelected(false);
    	chckBxVorrichtungBadDG.setSelected(false);
    	chckBxAusfuehrungBadDG.setSelected(false);
    	
    	// Checkboxen für vorkommende IDs ankreuzen 
    	if (ausgewaehlteSw == null) return;
    	for (int sw: ausgewaehlteSw) {
    		switch (Sw.findeMitId(sw)) {
    			case WAND_KUECHE:
    				chckBxWandKueche.setSelected(true);
    				break;
    			case TUER_KUECHE:
    				chckBxTuerKueche.setSelected(true);
    				break;
    			case GROSSES_ZIMMER_OG:
    				chckBxGrossesZimmerOG.setSelected(true);
    				break;
    			case TREPPENRAUM_DG:
    				chckBxTreppenraumDG.setSelected(true);
    				break;
    			case VORRICHTUNG_BAD_DG:
    				chckBxVorrichtungBadDG.setSelected(true);
    				break;
    			case AUSFUEHRUNG_BAD_DG:
    				chckBxAusfuehrungBadDG.setSelected(true);
    				break;
    			default:
    				System.out.println(
    						"Unbekannte Sonderwunsch-ID zu Heizkörpern: " + sw);
    		}
    	}
    }
    
    @Override
    public boolean[] holeIsSelectedFuerCheckboxen() {
    	return new boolean[] {
    			chckBxWandKueche.isSelected(),
    			chckBxTuerKueche.isSelected(),
    			chckBxGrossesZimmerOG.isSelected(),
    			chckBxTreppenraumDG.isSelected(),
    			chckBxVorrichtungBadDG.isSelected(),
    			chckBxAusfuehrungBadDG.isSelected()};
    }
    
    @Override
    protected int[] checkboxenZuIntArray() {
    	Vector<Integer> v = new Vector<>();

        if (chckBxWandKueche.isSelected())
            v.add(Sw.WAND_KUECHE.id);
        if (chckBxTuerKueche.isSelected())
            v.add(Sw.TUER_KUECHE.id);
        if (chckBxGrossesZimmerOG.isSelected())
            v.add(Sw.GROSSES_ZIMMER_OG.id);
        if (chckBxTreppenraumDG.isSelected())
            v.add(Sw.TREPPENRAUM_DG.id);
        if (chckBxVorrichtungBadDG.isSelected())
            v.add(Sw.VORRICHTUNG_BAD_DG.id);
        if (chckBxAusfuehrungBadDG.isSelected())
        	v.add(Sw.AUSFUEHRUNG_BAD_DG.id);

        int[] grundrissSw = new int[v.size()];
        for (int i = 0; i < v.size(); i++)
        	grundrissSw[i] = v.get(i);
        
        return grundrissSw;
    }
    
 	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
  	@Override
    protected void berechneUndZeigePreisSonderwuensche(){
  		grundrissControl.pruefeKonstellationSonderwuensche(checkboxenZuIntArray());
  		
  		double preis = 0.0;
  		
  		if (chckBxWandKueche.isSelected())		preis += Sw.WAND_KUECHE.preis;
    	if (chckBxTuerKueche.isSelected())		preis += Sw.TUER_KUECHE.preis;
    	if (chckBxGrossesZimmerOG.isSelected()) preis += Sw.GROSSES_ZIMMER_OG.preis;
    	if (chckBxTreppenraumDG.isSelected())	preis += Sw.TREPPENRAUM_DG.preis;
    	if (chckBxVorrichtungBadDG.isSelected())preis += Sw.VORRICHTUNG_BAD_DG.preis;
    	if (chckBxAusfuehrungBadDG.isSelected())preis += Sw.AUSFUEHRUNG_BAD_DG.preis;
  		
  		txtGesamt.setText(String.format("%.2f", preis));
  	}
  	
  	/*
	 * Wird von BasisView-Button "Speichern" aufgerufen.
	 * Übergibt die Auswahl zum Speichern an Control.
	 */
  	@Override
  	protected void speichereSonderwuensche(){
  		
  		// Speichere Sonderwünsche (Prüfung in Control, da das Feld kundeModel private ist)
  		this.grundrissControl.speichereSonderwuensche(checkboxenZuIntArray());
  	}
 }


