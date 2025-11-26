package gui.fliesen;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import business.kunde.*;
import gui.kunde.KundeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Fliesen-Varianten
 * kontrolliert.
 */
public final class FliesenControl {

    // das View-Objekt des Fliesen-Fensters
    private FliesenView fliesenView;
    private KundeModel kundeModel;
    private DatabaseConnection connection;

    /**
     * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum
     * Fenster fuer die Sonderwuensche zum Fliesen.
     * @param kundeModel, KundeModel zum abgreifen der Kunden
     */
    public FliesenControl(KundeModel kundeModel, DatabaseConnection connection){
        Stage stageFliesen = new Stage();
        stageFliesen.initModality(Modality.APPLICATION_MODAL);
        this.fliesenView = new FliesenView(this, stageFliesen);
        this.kundeModel = kundeModel;
        this.connection = connection;
    }
    
    /**
     * macht das FliesenView-Objekt sichtbar.
     */
    public void oeffneFliesenView(){
        this.fliesenView.oeffneFliesenView();
    }

    //Fliesen aus Datenbankladen
    public String[][] leseFliesenSonderwuensche(){
    	this.connection = DatabaseConnection.getInstance();
    	return connection.executeSelectNameAndPrice("Wunschoption", 6);
    }

}
