package gui.aussenanlagen;

import java.io.File;
import java.io.FileWriter;

import business.kunde.KundeModel;
import business.kunde.Sw;
import business.kunde.SwKategorie;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AussenanlagenControl {
	
	private final AussenanlagenView aussenanlagenView;
	private final KundeModel kundeModel;
	
	public AussenanlagenControl() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		kundeModel = KundeModel.getInstance();
		aussenanlagenView = new AussenanlagenView(this, stage);
	}
	
	public void oeffneAussenanlagenView() {
		leseAussenanlagenSonderwuensche();
		aussenanlagenView.oeffneAussenanlagenView();
	}
	
	public void leseAussenanlagenSonderwuensche() {
		int[][] swAussenanlagen = kundeModel.gibAusgewaehlteSwMitAnzahlAusDb(SwKategorie.AUSSENANLAGEN.id);
        if (swAussenanlagen != null)
            aussenanlagenView.updateSwInView(swAussenanlagen);
	}
	
	@Deprecated
	public void speichereSonderwuensche(int[] aussenanlagenSw) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationAussenanlagen(aussenanlagenSw)) {
            // Konflikt -> nicht speichern
            return;
        }

        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
            		aussenanlagenSw,
                    SwKategorie.AUSSENANLAGEN.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Heizungen konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	}
	@Deprecated
	public void speichereSonderwuensche(int[] aussenanlagenSw, int[][] aussenanlagenSwMitAnzahl) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationAussenanlagen(aussenanlagenSw, aussenanlagenSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }

        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
            		aussenanlagenSw,
            		aussenanlagenSwMitAnzahl,
                    SwKategorie.AUSSENANLAGEN.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Außenanlagen konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	}
	
	// Aktuellse Version
	public void speichereSonderwuensche(int[][] aussenanlagenSwMitAnzahl) {
		// Erst Konstellation prüfen
        if (!pruefeKonstellationSonderwuensche(aussenanlagenSwMitAnzahl)) {
            // Konflikt -> nicht speichern
            return;
        }
        
        try {
            kundeModel.speichereSonderwuenscheFuerKategorie(
            		aussenanlagenSwMitAnzahl,
                    SwKategorie.AUSSENANLAGEN.id
            );
        } catch (Exception e) {
            System.out.println("Sonderwünsche zu Aussenanlagen-Varianten konnten nicht gespeichert werden.");
            e.printStackTrace();
        }
	}
	
	@Deprecated
	public boolean pruefeKonstellationAussenanlagen(int[] ausgewaehlteSw) {
		return true; // TODO
	}
	@Deprecated
	public boolean pruefeKonstellationAussenanlagen(int[] ausgewaehlteSw, int[][] aussenanlagenSwMitAnzahl) {
		return true; // TODO
	}
	
	// Aktuellse Version
	public boolean pruefeKonstellationSonderwuensche(int[][] ausgewaehlteSwMitAnzahl){
		/* 
		 * - Prüfe, ob ein Sw in ausgewaehlteSw und ausgewaehlteSwMitAnzahl doppelt vorkommt.
		 * - Hole mit KundeModel.holeAusgewaehlteSwAusDbOhneKategorie() oder
		 * KundeModel.holeAusgewaehlteSwMitAnzahlAusDbOhneKategorie() die Auswahl der anderen
		 * Kategorien.
		 * - Prüfe, ob neue Auswahl mit der alten vereinbar ist und die Anzahlen erlaubt. 
		 */ 
		return true;
	}
	
	public void exportiereSonderwuenscheAlsCsv(int[][] aussenanlagenSwMitAnzahl) {
	    try {
	        if (aussenanlagenSwMitAnzahl == null || aussenanlagenSwMitAnzahl.length == 0) {
	            aussenanlagenView.zeigeInfo(
	                "Export",
	                "Keine Sonderwünsche zu Außenanlagen vorhanden."
	            );
	            return;
	        }

	        if (kundeModel.getKunde() == null) {
	            aussenanlagenView.zeigeInfo(
	                "Export fehlgeschlagen",
	                "Kein Kunde ausgewählt."
	            );
	            return;
	        }

	        int kundennummer = kundeModel.getKunde().getIdKunde();
	        String nachname = kundeModel.getKunde().getNachname();

	        String dateiname =
	            kundennummer + "_" + nachname + "_Aussenanlagen.csv";
	        File file = new File(dateiname);

	        try (FileWriter writer = new FileWriter(file)) {
	            writer.write("Sonderwunsch;Anzahl;Einzelpreis;Gesamtpreis\n");

	            for (int[] sw : aussenanlagenSwMitAnzahl) {
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

	        aussenanlagenView.zeigeInfo(
	            "Export erfolgreich",
	            "CSV-Datei wurde erstellt:\n" + file.getAbsolutePath()
	        );

	    } catch (Exception e) {
	        e.printStackTrace();
	        aussenanlagenView.zeigeInfo(
	            "Export fehlgeschlagen",
	            "Die CSV-Datei konnte nicht erstellt werden."
	        );
	    }
	}
}
