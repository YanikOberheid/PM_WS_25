package gui.innentueren;

import java.io.File;
import java.io.FileWriter;

import business.kunde.KundeModel;
import business.kunde.Sw;
import business.kunde.SwKategorie;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Control für die Innentüren-GUI.
 * Task-Scope: Nur Öffnen/Schließen und Kundenvorprüfung.
 */
public class InnentuerenControl {

    private final InnentuerenView innentuerenView;
    private final KundeModel kundeModel;

    public InnentuerenControl() {
        Stage stageInnentueren = new Stage();
        stageInnentueren.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = KundeModel.getInstance();
        this.innentuerenView = new InnentuerenView(this, stageInnentueren);
    }
    
    public void oeffneInnenturenView() {
        leseInnentuerenSonderwuensche();
        innentuerenView.oeffneInnentuerenView();
    }

    public void leseInnentuerenSonderwuensche() {
        int[][] swInnentueren = kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(SwKategorie.INNENTUEREN.id);
        if (swInnentueren != null)
            innentuerenView.updateSwInView(swInnentueren);
    }
    
    // Entsprechende Task: Auswahl für die Kategorie „Innentüren“ persistieren
    @Deprecated
    public void speichereSonderwuensche(int[] innentuerenSw) {
        try {
            // Speichert ausschließlich für die Zielkategorie, ersetzt dort die Auswahl
            kundeModel.speichereSonderwuenscheFuerKategorie(
                innentuerenSw, 
                SwKategorie.INNENTUEREN.id
            );
            innentuerenView.zeigeInfo("Gespeichert", "Innentüren-Sonderwünsche wurden gespeichert.");
        } catch (Exception e) {
            e.printStackTrace();
            innentuerenView.zeigeFehler("Fehler", "Speichern der Innentüren-Sonderwünsche ist fehlgeschlagen.");
        }
    }
    @Deprecated
    public void speichereSonderwuensche(int[] innentuerenSw, int[][] innentuerenSwMitAnzahl) {
    	// Erst Konstellation prüfen
        if (!pruefeKonstellationSonderwuensche(innentuerenSw, innentuerenSwMitAnzahl))
        	return;
        
    	try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
                innentuerenSw,
                innentuerenSwMitAnzahl,
                SwKategorie.INNENTUEREN.id
            );
            innentuerenView.zeigeInfo("Gespeichert", "Innentüren-Sonderwünsche wurden gespeichert.");
        } catch (Exception e) {
        	System.out.println("Sonderwünsche zu Innentüren konnten nicht gespeichert werden.");
            e.printStackTrace();
            innentuerenView.zeigeFehler("Fehler", "Speichern der Innentüren-Sonderwünsche ist fehlgeschlagen.");
        }
    }
    
    // Aktuellste Version
 	public void speichereSonderwuensche(int[][] innentuerenSwMitAnzahl) {
 		// Erst Konstellation prüfen
         if (!pruefeKonstellationSonderwuensche(innentuerenSwMitAnzahl)) {
             // Konflikt -> nicht speichern
             return;
         }
         
         try {
             kundeModel.speichereSonderwuenscheFuerKategorie(
            		 innentuerenSwMitAnzahl,
                     SwKategorie.INNENTUEREN.id
             );
         } catch (Exception e) {
             System.out.println("Sonderwünsche zu Innentüren-Varianten konnten nicht gespeichert werden.");
             e.printStackTrace();
         }
 	} 
    
    // Spätere Implementierung
    @Deprecated
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
        return true;
    }
    
    @Deprecated
    public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw, int[][] ausgewaehlteSwMitAnzahl) {
        return true; // TODO
    }
    
    // Aktuellste Version
 	public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl){
 		return true;
 	}
 	
 	public void exportiereSonderwuenscheAlsCsv(int[][] innentuerenSwMitAnzahl) {
 	    try { 
 	        if (innentuerenSwMitAnzahl == null || innentuerenSwMitAnzahl.length == 0) {
 	            innentuerenView.zeigeInfo("Export", "Keine Sonderwünsche zu Innentüren vorhanden.");
 	            return;
 	        }
 	       
 	        // Kundendaten
 	        if (kundeModel.getKunde() == null) {
 	            innentuerenView.zeigeFehler("Export fehlgeschlagen", "Kein Kunde ausgewählt.");
 	            return;
 	        }

 	        int kundennummer = kundeModel.getKunde().getIdKunde();
 	        String nachname = kundeModel.getKunde().getNachname();
 	        
 	        String dateiname = kundennummer + "_" + nachname + "_Innentueren.csv";
 	        File file = new File(dateiname);

 	        try (FileWriter writer = new FileWriter(file)) {
 	            writer.write("Sonderwunsch;Anzahl;Einzelpreis;Gesamtpreis\n");

 	            for (int[] sw : innentuerenSwMitAnzahl) {
 	                Sw sonderwunsch = Sw.findeMitId(sw[0]);
 	                int anzahl = sw[1];
 	                double einzelpreis = sonderwunsch.preis;
 	                double gesamtpreis = anzahl * einzelpreis;

 	                writer.write(
 	                    sonderwunsch.bes + ";" +
 	                    anzahl + ";" +
 	                    einzelpreis + ";" +
 	                    gesamtpreis + "\n"
 	                );
 	            }
 	        }

 	        innentuerenView.zeigeInfo(
 	            "Export erfolgreich",
 	            "CSV-Datei wurde erstellt:\n" + file.getAbsolutePath()
 	        );

 	    } catch (Exception e) {
 	        e.printStackTrace();
 	        innentuerenView.zeigeFehler(
 	            "Export fehlgeschlagen",
 	            "Die CSV-Datei konnte nicht erstellt werden."
 	        );
 	    }
 	}


}
