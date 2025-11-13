package gui.kunde;

import business.kunde.*;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Klasse, welche das Grundfenster mit den Kundendaten bereitstellt.
 */
public class KundeView{
 
	// das Control-Objekt des Grundfensters mit den Kundendaten
	private KundeControl kundeControl;
	// das Model-Objekt des Grundfensters mit den Kundendaten
	private KundeModel kundeModel;

    //---Anfang Attribute der grafischen Oberflaeche---
	private BorderPane borderPane 		= new BorderPane();
	private GridPane gridPane 			= new GridPane();
	private Label lblKunde    	      	= new Label("Kunde");
    private Label lblNummerHaus     	= new Label("Plannummer des Hauses");
    private ComboBox<Integer> 
        cmbBxNummerHaus                 = new ComboBox<Integer>();
    private Label lblKundennummer      = new Label("Kundennummer");
    private TextField txtKundennummer  = new TextField();
    private Label lblVorname         	= new Label("Vorname");
    private TextField txtVorname     	= new TextField();   
    private Label lblNachname          = new Label("Nachname");
    private TextField txtNachname      = new TextField();   
    private Label lblTelefonnummer     = new Label("Telefonnummer");
    private TextField txtTelefonnummer = new TextField();
    private Label lblEmail             = new Label("E-Mail");
    private TextField txtEmail         = new TextField();
    private Button btnAnlegen	 	  	= new Button("Anlegen");
    private Button btnAendern 	      	= new Button("Ändern");
    private Button btnLoeschen 	 		= new Button("Löschen");
    private MenuBar mnBar 			  	= new MenuBar();
    private Menu mnSonderwuensche    	= new Menu("Sonderwünsche");
    private MenuItem mnItmGrundriss  	= new MenuItem("Grundrissvarianten");
    //-------Ende Attribute der grafischen Oberflaeche-------
  
    /**
     * erzeugt ein KundeView-Objekt und initialisiert die Steuerelemente der Maske
     * @param kundeControl KundeControl, enthaelt das zugehoerige Control
     * @param primaryStage Stage, enthaelt das Stage-Objekt fuer diese View
     * @param kundeModel KundeModel, enthaelt das zugehoerige Model
    */
    public KundeView (KundeControl kundeControl, Stage primaryStage, 
    	KundeModel kundeModel){
        this.kundeControl = kundeControl;
        this.kundeModel = kundeModel;
        
        primaryStage.setTitle(this.kundeModel.getUeberschrift());	
	    Scene scene = new Scene(borderPane, 600, 460);
	    primaryStage.setScene(scene);
        primaryStage.show();

	    this.initKomponenten();
	    this.initListener();
    }

 
    /* initialisiert die Steuerelemente auf der Maske */
    private void initKomponenten(){
    	borderPane.setCenter(gridPane);
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(25, 25, 25, 25));
       	
	    gridPane.add(lblKunde, 0, 0, 2, 1);
       	lblKunde.setMinSize(200, 40);
	    lblKunde.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        gridPane.add(lblNummerHaus, 0, 1);
        gridPane.add(cmbBxNummerHaus, 1, 1);
        cmbBxNummerHaus.setMinSize(150,  25);
        cmbBxNummerHaus.setItems(this.kundeModel.getPlannummern());

        gridPane.add(lblKundennummer, 0, 2);
        gridPane.add(txtKundennummer, 1, 2);
        txtKundennummer.setMinSize(150,  25);

        gridPane.add(lblVorname, 0, 3);
        gridPane.add(txtVorname, 1, 3);
        txtVorname.setMinSize(150,  25);

        gridPane.add(lblNachname, 0, 4);
        gridPane.add(txtNachname, 1, 4);
        txtNachname.setMinSize(150, 25);

        gridPane.add(lblTelefonnummer, 0, 5);
        gridPane.add(txtTelefonnummer, 1, 5);
        txtTelefonnummer.setMinSize(150, 25);

        gridPane.add(lblEmail, 0, 6);
        gridPane.add(txtEmail, 1, 6);
        txtEmail.setMinSize(150, 25);

	    // Buttons
	    gridPane.add(btnAnlegen, 0, 8);
	    btnAnlegen.setMinSize(150,  25);
	    gridPane.add(btnAendern, 1, 8);
	    btnAendern.setMinSize(150,  25);
	    gridPane.add(btnLoeschen, 2, 8);
	    btnLoeschen.setMinSize(150,  25);
	    // MenuBar und Menu
	    borderPane.setTop(mnBar);
	    mnBar.getMenus().add(mnSonderwuensche);
	    mnSonderwuensche.getItems().add(mnItmGrundriss);
    }

    /* initialisiert die Listener zu den Steuerelementen auf de Maske */
    private void initListener(){
    	cmbBxNummerHaus.setOnAction(aEvent-> {
    		 holeInfoDachgeschoss();  
    		 leseKunden();
     	});
       	btnAnlegen.setOnAction(aEvent-> {
 	        legeKundenAn();
	    });
    	btnAendern.setOnAction(aEvent-> {
           	aendereKunden();
	    });
       	btnLoeschen.setOnAction(aEvent-> { 
           	loescheKunden();
	    });
      	mnItmGrundriss.setOnAction(aEvent-> {
 	        kundeControl.oeffneGrundrissControl(); 
	    });
    }
    
    private void holeInfoDachgeschoss(){ 
    }
    
    private void leseKunden(){
    }
    
    private void legeKundenAn(){
         // Werte aus den Textfeldern für das Kundenobjekt übernehmen
         Integer kundennummer = null;
         try {
             kundennummer = Integer.parseInt(txtKundennummer.getText());
         } catch (NumberFormatException e) {
             zeigeFehlermeldung("Eingabefehler", "Kundennummer muss eine Zahl sein!");
             return;
         }
         String vorname = txtVorname.getText();
         String nachname = txtNachname.getText();
         String telefonnummer = txtTelefonnummer.getText();
         String email = txtEmail.getText();
         Integer plannummer = cmbBxNummerHaus.getValue();

         Kunde kunde = new Kunde(kundennummer, vorname, nachname, telefonnummer, email, plannummer);
         // Objekt kunde fuellen
         kundeControl.speichereKunden(kunde);
   	}
    
  	private void aendereKunden(){
   	}
  	
   	private void loescheKunden(){
   	}
   	
   /** zeigt ein Fehlermeldungsfenster an
    * @param ueberschrift, Ueberschrift fuer das Fehlermeldungsfenster
    * @param meldung, String, welcher die Fehlermeldung enthaelt
    */
    public void zeigeFehlermeldung(String ueberschrift, String meldung){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setHeaderText(ueberschrift);
        alert.setContentText(meldung);
        alert.show();
    }

}

