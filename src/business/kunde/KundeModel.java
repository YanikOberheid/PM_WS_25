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
	 * Prüft zunächst, ob ein Kunde unter der angegebenen Hausnummer existiert.
	 * Falls ja, wird der Kunde aus der Datenbank geladen und als aktueller Kunde
	 * im Model gesetzt. Andernfalls wird das aktuelle Kundenobjekt auf null gesetzt.
	 *
	 * @param hausnummer die ausgewählte Hausnummer / Plannummer
	 * @return der gefundene Kunde oder null, falls kein Kunde unter dieser Hausnummer existiert
	 * @throws SQLException Fehler beim Datenbankzugriff
	 */
	public Kunde ladeKunde(int hausnummer) throws SQLException {
	    KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
	    
	    if (kundeDAO.istHausnummerBesetzt(hausnummer)) {
	        // Kunde existiert, lade das Objekt
	        this.kunde = kundeDAO.findByHausnummer(hausnummer);
	    } else {
	        // Kein Kunde unter dieser Hausnummer
	        this.kunde = null;
	    }
	    
	    return this.kunde;
	}
	
	// Löscht den Kunden zur angegebenen Hausnummer.
	public boolean loescheKunden(int hausnummer) throws SQLException {
	    KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
	    boolean geloescht = kundeDAO.deleteKunde(hausnummer);

	    // Wenn gelöscht, auch aktuelles Kunde-Objekt im Model leeren
	    if (geloescht && this.kunde != null && this.kunde.getHausnummer() == hausnummer) {
	        this.kunde = null;
	    }
	    return geloescht;
	}
	
	public void updateKunde (Kunde kunde) throws SQLException, Exception {
	    KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
	    kundeDAO.updateKunde (kunde);
	}

	/**
	 * Checks whether the given customer data is valid.
	 *
	 * @param kunde the customer object to validate
	 * @return true if all required fields contain valid data; false otherwise
	 * @throws SQLException 
	 */
	public boolean isValidCustomer(Kunde kunde, boolean isUpdate) throws SQLException {
		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		
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
		
	    boolean hausnummerBesetzt = kundeDAO.istHausnummerBesetzt(kunde.getHausnummer());

		if (!isUpdate && hausnummerBesetzt) {
			System.err.println("Fehlgeschlagen: Die Hausnummer ist besetzt.");
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
	
	// ------------ Sonderwünsche ------------ 
	private int[] ausgewaehlteSw = null;// enhaelt die IDs der ausgewaehlten Sonderwünsche
	private SonderwuenscheDAOImplementation swDao = new SonderwuenscheDAOImplementation();
	
	public int[] gibAusgewaehlteSwLokal() {
		if (kunde == null) return null;
		if (this.ausgewaehlteSw == null) return null;
		return this.ausgewaehlteSw.clone();
	}
	
	/**
	 * Holt Sonderwünsche zu einem Kunden und gibt ein Array an Sonderwunschoptionen oder null.
	 *
	 * @return Klon von this.ausgewaehlteSw oder null 
	 */
	public int[] gibAusgewaehlteSwAusDb() {
		if (kunde == null) return null; 
		// throw new Exception("Es konnte kein Kunde gefunden werden");
		int hausnr = this.kunde.getHausnummer();
		
		
		try {
			this.ausgewaehlteSw = this.swDao.get(hausnr);
			return this.ausgewaehlteSw.clone();
		} catch (SQLException exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche: SQL Fehler");
			exc.printStackTrace();
		} catch (Exception exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche");
			exc.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Holt Sonderwünsche zu einem Kunden und gibt ein Array an Sonderwunschoptionen oder null.
	 *
	 * @param ID einer Sonderwunschkategorie als int
	 * @return Klon von this.ausgewaehlteSw oder null
	 */
	public int[] gibAusgewaehlteSwAusDb(int kategorieId) {
		if (this.kunde == null) return null;
		// throw new Exception("Fehler beim Laden ausgewählter Sonderwünsche: Es konnte kein Kunde gefunden werden");
		int hausnr = this.kunde.getHausnummer();
		
		try {
			this.ausgewaehlteSw = this.swDao.get(hausnr, kategorieId);
			return this.ausgewaehlteSw.clone();
		} catch (SQLException exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche: SQL Fehler");
			exc.printStackTrace();
		} catch (Exception exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche");
			exc.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Holt Sonderwünsche zu einem Kunden Gibt ein Array an Sonderwunschoptionen zurueck, wenn ausgewaehlteSw nicht null ist. Ansonsten wird holeAusgewaehlteSwAusDb() aufgerufen.
	 *
	 * @param int[] mit IDs der ausgewaehlten Sonderwünsche
	 * @throws SQLExceptio oder Exception 
	 */
	public void updateAusgewaehlteSw(int[] ausgewaehlteSw) throws SQLException, Exception{
		if (this.kunde == null)
			throw new Exception("Fehler beim Aktualisieren ausgewählter Sonderwünsche: Es konnte kein Kunde gefunden werden");;
		int hausnr = this.kunde.getHausnummer();
		
		try {
			this.swDao.update(hausnr, ausgewaehlteSw);
			this.ausgewaehlteSw = ausgewaehlteSw;
		} catch (SQLException exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche: SQL Fehler");
			exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche");
			exc.printStackTrace();
			throw exc;
		}
	}
}
