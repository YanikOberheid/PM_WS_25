package gui.basis;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Klasse, welche die Basis fuer die Fenster zu den Sonderwuenschen bereitstellt.
 */
public abstract class BasisView {
 
    //---Anfang Attribute der grafischen Oberflaeche---
	Stage sonderwunschStage;
	private BorderPane borderPane 		= new BorderPane();
	private GridPane gridPane 		 	= new GridPane();
	private GridPane gridPaneButtons 	= new GridPane();
   	private Label lblSonderwunsch   	= new Label("Sonderwunsch");
    private Button btnBerechnen 	 	= new Button("Preis berechnen");
    private Button btnSpeichern 	 	= new Button("Speichern");
    
    // Gesamtpreis-Anzeige - protected, da Zeile im GridPane unbekannt
    protected Label lblGesamt =
    		new Label("Gesamtpreis");
    protected TextField txtGesamt = new TextField();
    protected Label lblGesamtEuro = new Label("Euro");
    //-------Ende Attribute der grafischen Oberflaeche-------
  
   /**
    * erzeugt ein BasisView-Objekt
    */
    public BasisView(Stage sonderwunschStage){
    	this.sonderwunschStage = sonderwunschStage;
	    Scene scene = new Scene(borderPane, 560, 400);
	    sonderwunschStage.setScene(scene);
	
	    this.initListener();
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
      	borderPane.setCenter(gridPane);
      	borderPane.setBottom(gridPaneButtons);
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(25, 25, 25, 25));
	    gridPane.setStyle("-fx-background-color: #FED005;");
        gridPaneButtons.setHgap(10);
	    gridPaneButtons.setPadding(new Insets(25, 25, 25, 200));
	    
    	gridPane.add(lblSonderwunsch, 0, 0);
    	lblSonderwunsch.setFont(Font.font("Arial", FontWeight.BOLD, 24));
	    // Buttons
    	gridPaneButtons.add(btnBerechnen, 1, 0);
	    btnBerechnen.setMinSize(150,  25);
    	gridPaneButtons.add(btnSpeichern, 2, 0);
	    btnSpeichern.setMinSize(150,  25);
    }  
    
    /* Es muessen die Listener implementiert werden. */
    protected void initListener(){
       	btnBerechnen.setOnAction(aEvent -> {
    		berechneUndZeigePreisSonderwuensche();
     	});
        btnSpeichern.setOnAction(aEvent -> {
    		speichereSonderwuensche();
    	});
    }
    
    protected GridPane getGridPaneSonderwunsch() {
  		return this.gridPane;
  	}

  	protected Label getLblSonderwunsch() {
  		return lblSonderwunsch;
  	}
  	
  	/*
  	 * macht das BasisView-Objekt sichtbar.
  	 */
  	protected void oeffneBasisView(){ 
	    sonderwunschStage.showAndWait();
  	}
  	     	
  	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
  	protected abstract void berechneUndZeigePreisSonderwuensche();
  	
   	/* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
  	protected abstract void speichereSonderwuensche();
  	
  	public abstract boolean[] holeIsSelectedFuerCheckboxen();
  	protected abstract void updateSwCheckboxen(int[] ausgewaehlteSw);
  	protected abstract int[] checkboxenZuIntArray();
  	
  	/** Zeigt ein Fehlerfenster; in Unit-Tests (ohne FX-Thread) wird nur geloggt. */
    protected void zeigeKonfliktFenster(String header, String text) {
        if (Platform.isFxApplicationThread()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Konflikt");
            alert.setHeaderText(header);
            alert.setContentText(text);
            alert.showAndWait();
        } else {
            // In Tests ohne JavaFX-Thread vermeiden wir Fehler:
            System.out.println("Konflikt (ohne FX-UI): " + header + " - " + text);
        }
    }
 	
}


