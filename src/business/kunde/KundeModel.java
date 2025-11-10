package business.kunde;

import java.sql.SQLException;
import javafx.collections.*;

/**
 * Klasse, welche das Model des Grundfensters mit den Kundendaten enthaelt.
 */
public final class KundeModel {

	// enthaelt den aktuellen Kunden
	private Kunde kunde;

	/*
	 * enthaelt die Plannummern der Haeuser, diese muessen vielleicht noch in eine
	 * andere Klasse verschoben werden
	 */
	ObservableList<Integer> plannummern = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);

	// enthaelt das einzige KundeModel-Objekt
	private static KundeModel kundeModel;

	// privater Konstruktor zur Realisierung des Singleton-Pattern
	private KundeModel() {
		super();
	}

	/**
	 * Methode zum Erhalt des einzigen KundeModel-Objekts. Das Singleton-Pattern
	 * wird realisiert.
	 * 
	 * @return KundeModel, welches das einzige Objekt dieses Typs ist.
	 */
	public static KundeModel getInstance() {
		if (kundeModel == null) {
			kundeModel = new KundeModel();
		}
		return kundeModel;
	}

	/**
	 * gibt die Ueberschrift zum Grundfenster mit den Kundendaten heraus
	 * 
	 * @return String, Ueberschrift zum Grundfenster mit den Kundendaten
	 */
	public String getUeberschrift() {
		return "Verwaltung der Sonderwunschlisten";
	}

	/**
	 * gibt saemtliche Plannummern der Haeuser des Baugebiets heraus.
	 * 
	 * @return ObservableList<Integer> , enthaelt saemtliche Plannummern der Haeuser
	 */
	public ObservableList<Integer> getPlannummern() {
		return this.plannummern;
	}

	// ---- Datenbankzugriffe -------------------

	/**
	 * speichert ein Kunde-Objekt in die Datenbank
	 * 
	 * @param kunde, Kunde-Objekt, welches zu speichern ist
	 * @throws SQLException, Fehler beim Speichern in die Datenbank
	 * @throws Exception,    unbekannter Fehler
	 */
	 
	 /*
	 * public void speichereKunden(Kunde kunde) throws SQLException, Exception{ //
	 * Speicherung des Kunden in der DB this.kunde = kunde; }
	 */

	public void speichereKunden(Kunde kunde) throws SQLException, Exception {
		this.kunde = kunde;
		// Datenbank speichern
		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		kundeDAO.add(kunde);
	}

	/**
	 * Checks whether the given customer data is valid.
	 *
	 * @param kunde the customer object to validate
	 * @return true if all required fields contain valid data; false otherwise
	 */
	public boolean isValidCustomer(Kunde kunde) {
	    if (kunde == null) {
	        System.err.println("❌ Validation failed: Kunde object is null.");
	        return false;
	    }

	    // Validate Hausnummer (must be between 1 and 24)
	    if (kunde.getHausnummer() < 1 || kunde.getHausnummer() > 24) {
	        System.err.println("❌ Validation failed: Invalid house number.");
	        return false;
	    }

	    // Validate first name
	    if (isNullOrEmpty(kunde.getVorname())) {
	        System.err.println("❌ Validation failed: First name is missing.");
	        return false;
	    }

	    // Validate last name
	    if (isNullOrEmpty(kunde.getNachname())) {
	        System.err.println("❌ Validation failed: Last name is missing.");
	        return false;
	    }

	    // Validate phone number (only digits)
	    if (isNullOrEmpty(kunde.getTelefonnummer()) || !kunde.getTelefonnummer().matches("\\d+")) {
	        System.err.println("❌ Validation failed: Invalid phone number.");
	        return false;
	    }

	    // Validate email format (simple check)
	    if (isNullOrEmpty(kunde.getEmail()) || !kunde.getEmail().contains("@")) {
	        System.err.println("❌ Validation failed: Invalid email address.");
	        return false;
	    }

	    return true;
	}

	/**
	 * Helper method to check if a string is null or empty after trimming.
	 */
	private boolean isNullOrEmpty(String s) {
	    return s == null || s.trim().isEmpty();
	}

}
