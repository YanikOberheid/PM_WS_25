package gui.kunde;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.SQLException;

import business.kunde.Kunde;
import business.kunde.KundeModel;

/**
 * Klasse, welche das Grundfenster mit den Kundendaten bereitstellt.
 */
public class KundeView {

	// das Control-Objekt des Grundfensters mit den Kundendaten
	private KundeControl kundeControl;
	// das Model-Objekt des Grundfensters mit den Kundendaten
	private KundeModel kundeModel;
  
  // Pfad zur Platzhalter-Grafik im Klassenpfad
	private static final String STANDARD_HAUS_BILD = "/gui/images/haus_placeholder.png";

	//private static final String DACHGESCHOSS_HAUS_BILD = "/gui/images/haus_placeholder.png";
	private static final int DACHGESCHOSS_HAUS_BILD = 1;
	private static final int STANDARD_HAUS_BILD_ = 2;
  
	// ---Anfang Attribute der grafischen Oberflaeche---
  
  // --- Bildanzeige (neu für Task [3]) ---
	private final ImageView hausImageView = new ImageView();
  
	private BorderPane borderPane = new BorderPane();
	private GridPane gridPane = new GridPane();
	private Label lblKunde = new Label("Kunde");
	private Label lblNummerHaus = new Label("Plannummer des Hauses");
	private ComboBox<Integer> cmbBxNummerHaus = new ComboBox<Integer>();
	
	private Label lblKundennummer = new Label("Kundennummer");
	private TextField txtKundennummer = new TextField();
	
	private Label lblVorname = new Label("Vorname");
	private TextField txtVorname = new TextField();

	private Label lblNachname = new Label("Nachname");
	private TextField txtNachname = new TextField();

	private Label lblNummer = new Label("Telefonnummer");
	private TextField txtNummer = new TextField();

	private Label lblEmail = new Label("E-Mail");
	private TextField txtEmail = new TextField();

	private Button btnAnlegen = new Button("Anlegen");
	private Button btnAendern = new Button("Aendern");
	private Button btnLoeschen = new Button("Loeschen");
	private MenuBar mnBar = new MenuBar();
	private Menu mnSonderwuensche = new Menu("Sonderwuensche");
	private MenuItem mnItmGrundriss = new MenuItem("Grundrissvarianten");
	private MenuItem mnItmFenster = new MenuItem("Fenster und Außentueren");
	private MenuItem mnItmInnentueren = new MenuItem("Innentüren");
	private MenuItem mnItmHeizung = new MenuItem("Heizungsvarianten");
	// hier Sanitär
	private MenuItem mnItmFliesen = new MenuItem("Fliesenvarianten");
	// hier Parkett
	private MenuItem mnItmAussenanlagen = new MenuItem("Außenanlagen");

	// -------Ende Attribute der grafischen Oberflaeche-------

	/**
	 * erzeugt ein KundeView-Objekt und initialisiert die Steuerelemente der Maske
	 * 
	 * @param kundeControl KundeControl, enthaelt das zugehoerige Control
	 * @param primaryStage Stage, enthaelt das Stage-Objekt fuer diese View
	 * @param kundeModel   KundeModel, enthaelt das zugehoerige Model
	 */
	public KundeView(KundeControl kundeControl, Stage primaryStage, KundeModel kundeModel) {
		this.kundeControl = kundeControl;
		this.kundeModel = kundeModel;

		primaryStage.setTitle(this.kundeModel.getUeberschrift());
		Scene scene = new Scene(borderPane, 750, 420);
		primaryStage.setScene(scene);
		primaryStage.show();

		this.initKomponenten();
		this.initListener();
	}

	/* initialisiert die Steuerelemente auf der Maske */
	private void initKomponenten() {
		borderPane.setCenter(gridPane);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));

		// Titel
		gridPane.add(lblKunde, 0, 1);
		lblKunde.setMinSize(150, 40);
		lblKunde.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		// Hausnummer
		gridPane.add(lblNummerHaus, 0, 2);
		gridPane.add(cmbBxNummerHaus, 1, 2);
		cmbBxNummerHaus.setMinSize(150, 25);
		cmbBxNummerHaus.setItems(this.kundeModel.getPlannummern());
		cmbBxNummerHaus.setValue(0);
		
		// Kundennummer
		gridPane.add(lblKundennummer, 0, 3);
		gridPane.add(txtKundennummer, 1, 3);
		
		// Vorname
		gridPane.add(lblVorname, 0, 4);
		gridPane.add(txtVorname, 1, 4);

		// Nachname
		gridPane.add(lblNachname, 0, 5);
		gridPane.add(txtNachname, 1, 5);

		// Telefonnummer
		gridPane.add(lblNummer, 0, 6);
		gridPane.add(txtNummer, 1, 6);

		// E-Mail
		gridPane.add(lblEmail, 0, 7);
		gridPane.add(txtEmail, 1, 7);

		// Buttons
		gridPane.add(btnAnlegen, 0, 8);
		btnAnlegen.setMinSize(150, 25);
		gridPane.add(btnAendern, 1, 8);
		btnAendern.setMinSize(150, 25);
		gridPane.add(btnLoeschen, 2, 8);
		btnLoeschen.setMinSize(150, 25);

		// MenuBar und Menu
		borderPane.setTop(mnBar);
		
		mnBar.getMenus().add(mnSonderwuensche);
		mnSonderwuensche.getItems().add(mnItmGrundriss);
		mnSonderwuensche.getItems().add(mnItmFenster);
		mnSonderwuensche.getItems().add(mnItmInnentueren);
		mnSonderwuensche.getItems().add(mnItmHeizung);
		// hier Sanitär
		mnSonderwuensche.getItems().add(mnItmFliesen);
		// hier Parkett
		mnSonderwuensche.getItems().add(mnItmAussenanlagen);
    
    // --- Rechts: Bildbereich (neu) ---
		VBox rightBox = new VBox(10);
		rightBox.setAlignment(Pos.TOP_CENTER);
		
		Label lblBild = new Label("Hausbild");
		initialisiereHausBild(); // Größe/Platzhalter konfigurieren
		
		rightBox.getChildren().addAll(lblBild, hausImageView);
    
		
		 // --- Beide Seiten in eine HBox ---
	    HBox mainContent = new HBox(30);
	    mainContent.setPadding(new Insets(20));
	    mainContent.getChildren().addAll(gridPane, rightBox);
		
	    borderPane.setCenter(mainContent);
	    
    // Hinweis: Bild wird NICHT sofort gesetzt – erst nach Laden der Kundendaten
		// Noch kein Bild laden – erst wenn Kundendaten/Plannummer gewählt wurden
		// Bild aber anzeigen, sonst ist ImageView zu klein oder leer!
		/*var url = getClass().getResource(STANDARD_HAUS_BILD);
		if (url != null) {
			hausImageView.setImage(new Image(url.toExternalForm()));
		} else {
			hausImageView.setImage(null);
		}*/
		hausImageView.setImage(null);
	}

	/* initialisiert die Listener zu den Steuerelementen auf de Maske */
	private void initListener() {
		cmbBxNummerHaus.setOnAction(aEvent -> {
			holeInfoDachgeschoss();
			leseKunden();
		});
		btnAnlegen.setOnAction(aEvent -> {
			legeKundenAn();
		});
		btnAendern.setOnAction(aEvent -> {
			aendereKunden();
		});
		btnLoeschen.setOnAction(aEvent -> {
			loescheKunden();
		});
		mnItmGrundriss.setOnAction(aEvent -> {
			kundeControl.oeffneGrundrissControl();
		});
		mnItmFliesen.setOnAction(aEvent -> {
	        kundeControl.oeffneFliesenControl();
	    });
		mnItmHeizung.setOnAction(aEvent -> {
		    kundeControl.oeffneHeizungControl();        
		});
		mnItmFenster.setOnAction(aEvent -> {            
		    kundeControl.oeffneFensterControl();
		});
		mnItmInnentueren.setOnAction(aEvent -> {
		    kundeControl.oeffneInnentuerenControl();
		});
		mnItmAussenanlagen.setOnAction(aEvent -> {
			kundeControl.oeffneAussenanlagenControl();
		});
	}

	private void holeInfoDachgeschoss() {
	}

	private void leseKunden() {
	    Integer hausnummer = cmbBxNummerHaus.getValue();
	    if (hausnummer == 0) {
	    	txtKundennummer.clear();
	        txtVorname.clear();
	        txtNachname.clear();
	        txtNummer.clear();
	        txtEmail.clear();
	        kundeControl.setAttributeNull();
	    }
	    else {
	        kundeControl.ladeKundenZuHausnummer(hausnummer);
	    }
	}

	private void legeKundenAn() {
		Kunde kunde = new Kunde(cmbBxNummerHaus.getValue(), txtVorname.getText(), txtNachname.getText(),
				txtNummer.getText(), txtEmail.getText());
		kundeControl.speichereKunden(kunde);
	}

	private void aendereKunden() {
		Integer kundenummer = Integer.parseInt(txtKundennummer.getText());
		
		Kunde kunde = new Kunde(
				kundenummer,
		        cmbBxNummerHaus.getValue(),
		        txtVorname.getText(),
		        txtNachname.getText(),
		        txtNummer.getText(),
		        txtEmail.getText()
		    );
		    kundeControl.updateKunde(kunde);
	}

	private void loescheKunden() {
		Integer hausnummer = cmbBxNummerHaus.getValue();
		Integer kundenummer = Integer.parseInt(txtKundennummer.getText());
		// Aktuell kann man selber die Kundennummer ändern!!!
	    if (kundenummer != null & hausnummer != null) {
	        kundeControl.loescheKunden(kundenummer, hausnummer);
	    } else {
	        zeigeFehlermeldung("Fehler", "Bitte zuerst eine Hausnummer auswählen.");
	    }
	}
	
	public void zeigeKundeAufGui(Kunde kunde) {
	    if (kunde == null) {
	    	txtKundennummer.clear();
	        txtVorname.clear();
	        txtNachname.clear();
	        txtNummer.clear();
	        txtEmail.clear();
	        return;
	    }
	    txtKundennummer.setText(Integer.toString(kunde.getIdKunde()));
	    txtVorname.setText(kunde.getVorname());
	    txtNachname.setText(kunde.getNachname());
	    txtNummer.setText(kunde.getTelefonnummer());
	    txtEmail.setText(kunde.getEmail());
	}

	/**
	 * zeigt ein Fehlermeldungsfenster an
	 * 
	 * @param ueberschrift, Ueberschrift fuer das Fehlermeldungsfenster
	 * @param meldung,      String, welcher die Fehlermeldung enthaelt
	 */
	public void zeigeFehlermeldung(String ueberschrift, String meldung) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Fehlermeldung");
		alert.setHeaderText(ueberschrift);
		alert.setContentText(meldung);
		alert.show();
	}
	
	public void zeigeErfolgsmeldung(String ueberschrift, String meldung) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Erfolg");
		alert.setHeaderText(ueberschrift);
		alert.setContentText(meldung);
		alert.show();
	}

	// === Neu: Methoden für Task [3] – Bildanzeige nach dem Laden ===

	/**
	 * Wird vom Control aufgerufen, sobald Kundendaten geladen wurden. Lädt das
	 * Standardbild aus dem Klassenpfad und zeigt es im rechten Bereich.
	 */
	public void zeigeHausBildNachLaden() {
		try {
			var url = getClass().getResource(STANDARD_HAUS_BILD);
			if (url != null) {
				hausImageView.setImage(new Image(url.toExternalForm()));
			} else {
				System.err.println("Hausbild nicht gefunden: " + STANDARD_HAUS_BILD);
				hausImageView.setImage(null);
			}
		} catch (Exception ex) {
			System.err.println("Fehler beim Laden des Hausbildes: " + ex.getMessage());
			hausImageView.setImage(null);
		}
	}

	/**
	 * Blendet das Hausbild aus (z. B. wenn kein Kunde vorhanden ist).
	 */
	public void entferneHausBild() {
		hausImageView.setImage(null);
	}

	private void initialisiereHausBild() {
		hausImageView.setFitWidth(260);
		hausImageView.setFitHeight(180);
		hausImageView.setPreserveRatio(true);
		// Noch kein Bild zeigen – erst nach dem Laden der Kundendaten:
		hausImageView.setImage(null);
	}

	public void zeigeHausBild(InputStream inputStream) {
		if (inputStream != null) {
			hausImageView.setImage(new Image(inputStream));
		} else {
			hausImageView.setImage(null);
		}
	}

	/**
	 * Wird nach dem Laden der Kundendaten aufgerufen.
	 * Ein entsprechendes Bild wird je nach mit oder ohne Dachgeschoss angezeigt
	 */
	public void zeigeHausBildFuerHausnummer(int hausnummer) {
		
		if (hausnummer == 0) {
			zeigeHausBild(null);
		} else {
		
			int[] keinDachgeschoss = {1, 6, 7 ,14, 15, 24};
			boolean found = false;
			
			for(int n : keinDachgeschoss) {
				if (hausnummer == n) {
					found = true;
					break;
				}
			}
			
			if (found) {
				System.out.println("Haus hat keinen Dachgeschoss!");
				try {
					InputStream img = kundeControl.ladeBildAusDB(STANDARD_HAUS_BILD_);
					zeigeHausBild(img);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//zeigeHausBild(STANDARD_HAUS_BILD);
			} else {
				System.out.println("Haus hat einen Dachgeschoss!");
				try {
					InputStream img = kundeControl.ladeBildAusDB(DACHGESCHOSS_HAUS_BILD);
					zeigeHausBild(img);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
