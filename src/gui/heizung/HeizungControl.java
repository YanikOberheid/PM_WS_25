package gui.heizung;

import java.io.File;
import java.io.FileWriter;

import business.kunde.KundeModel;
import business.kunde.Sw;
import business.kunde.SwKategorie;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HeizungControl {

    private final HeizungView heizungView;
    private final KundeModel kundeModel;

    public HeizungControl() {
        Stage stageHeizung = new Stage();
        stageHeizung.initModality(Modality.APPLICATION_MODAL);
        this.kundeModel = KundeModel.getInstance();
        this.heizungView = new HeizungView(this, stageHeizung);
    }
    
    public void oeffneHeizungView() {
        leseHeizungsSonderwuensche();
        heizungView.oeffneHeizungView();
    }

    public void leseHeizungsSonderwuensche() {
        int[][] swHeizung = kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(SwKategorie.HEIZKOERPER.id);
        if (swHeizung != null)
            heizungView.updateSwInView(swHeizung);
    }
    
    // Speichern der ausgewählten Sonderwuensche
    @Deprecated
    public void speichereSonderwuensche(int[] heizungsSw) {
        // Erst Konstellation prüfen
        if (!pruefeKonstellationHeizkoerper(heizungsSw)) {
            // Konflikt -> nicht speichern
            return;
        }

        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
                    heizungsSw,
                    SwKategorie.HEIZKOERPER.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Heizungen konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
    }
    @Deprecated
    public void speichereSonderwuensche(int[] heizungsSw, int[][] heizungsSwMitAnzahl) {
        // Erst Konstellation prüfen
        if (!pruefeKonstellationHeizkoerper(heizungsSw, heizungsSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }

        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
                    heizungsSw,
                    heizungsSwMitAnzahl,
                    SwKategorie.HEIZKOERPER.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Heizungen konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
    }
    
	// Aktuellste Version
	public void speichereSonderwuensche(int[][] heizungsSwMitAnzahl) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationSonderwuensche(heizungsSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }
        
        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
            		heizungsSwMitAnzahl,
                    SwKategorie.HEIZKOERPER.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Heizungen-Varianten konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	} 
    
    @Deprecated
    public boolean pruefeKonstellationHeizkoerper(int[] ausgewaehlteSw) {
        return true; // Erst alles durchlassen. Implementiation ist Priorität [5]
    }
    
    @Deprecated
    public boolean pruefeKonstellationHeizkoerper(int[] ausgewaehlteSw, int[][] ausgewaehlteSwMitAnzahl) {
        return true; // Erst alles durchlassen. Implementiation ist Priorität [5]
    }
    
	// Aktuellste Version
	public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl){
		return true;
	}
	
	public void exportiereSonderwuenscheAlsCsv(int[][] heizungSwMitAnzahl) {
	    try {
	        if (heizungSwMitAnzahl == null || heizungSwMitAnzahl.length == 0) {
	            heizungView.zeigeInfo("Export", "Keine Sonderwünsche zu Heizungen vorhanden.");
	            return;
	        }

	        if (kundeModel.getKunde() == null) {
	            heizungView.zeigeInfo("Export fehlgeschlagen", "Kein Kunde ausgewählt.");
	            return;
	        }

	        int kundennummer = kundeModel.getKunde().getIdKunde();
	        String nachname = kundeModel.getKunde().getNachname();
	        String dateiname = kundennummer + "_" + nachname + "_Heizungen.csv";
	        File file = new File(dateiname);

	        try (FileWriter writer = new FileWriter(file)) {
	            writer.write("Sonderwunsch;Anzahl;Einzelpreis;Gesamtpreis\n");

	            for (int[] sw : heizungSwMitAnzahl) {
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

	        heizungView.zeigeInfo(
	            "Export erfolgreich",
	            "CSV-Datei wurde erstellt:\n" + file.getAbsolutePath()
	        );

	    } catch (Exception e) {
	        e.printStackTrace();
	        heizungView.zeigeInfo(
	            "Export fehlgeschlagen",
	            "Die CSV-Datei konnte nicht erstellt werden."
	        );
	    }
	}

}
