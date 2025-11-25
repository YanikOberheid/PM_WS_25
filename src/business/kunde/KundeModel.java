package business.kunde;

import java.sql.SQLException;
import javafx.collections.*;

/**
 * Klasse, welche das Model des Grundfensters mit den Kundendaten enthaelt.
 */
public final class KundeModel {

	// enthaelt den aktuellen Kunden
	private Kunde kunde;

	// Bearbeitet von Yamam
	// speichert die letzte Validierungsfehlermeldung
	private String lastValidationError;

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
		
		// Die ID, die von der DB gegeben wurde laden und speichern lokal in Kunde.idKunde
		int idKunde = (kundeDAO.findByHausnummer(this.kunde.getHausnummer())).getIdKunde();
		this.kunde.setIdKunde(idKunde);
	}

	/**
	 * Prüft zunächst, ob ein Kunde unter der angegebenen Hausnummer existiert.
	 * Falls ja, wird der Kunde aus der Datenbank geladen und als aktueller Kunde im
	 * Model gesetzt. Andernfalls wird das aktuelle Kundenobjekt auf null gesetzt.
	 *
	 * @param hausnummer die ausgewählte Hausnummer / Plannummer
	 * @return der gefundene Kunde oder null, falls kein Kunde unter dieser
	 *         Hausnummer existiert
	 * @throws SQLException Fehler beim Datenbankzugriff
	 */
	public Kunde ladeKunde(int hausnummer) throws SQLException {
<<<<<<< HEAD
	    KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
	    
	    if (kundeDAO.istHausnummerBesetzt(hausnummer)) {
	        // Kunde existiert, lade das Objekt
	    	System.out.println("Hausnummer besetzt");
	        this.kunde = kundeDAO.findByHausnummer(hausnummer);
	    } else {
	        // Kein Kunde unter dieser Hausnummer
	        this.kunde = null;
	    }
	    return this.kunde;
=======
		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();

		if (kundeDAO.istHausnummerBesetzt(hausnummer)) {
			// Kunde existiert, lade das Objekt
			this.kunde = kundeDAO.findByHausnummer(hausnummer);
		} else {
			// Kein Kunde unter dieser Hausnummer
			this.kunde = null;
		}

		return this.kunde;
>>>>>>> refs/remotes/origin/dev
	}

	// Löscht den Kunden zur angegebenen Hausnummer.
<<<<<<< HEAD
	public boolean loescheKunden(int kundennummer, int hausnummer) throws Exception {
	    KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
	    
	    // Damit der Kunde mit der jeweiligen ID geändert wird und nicht 
	    // ausversehen ein weitere Datensatz hinzugefügt wird
	    //kunde.setIdKunde(this.kunde.getIdKunde());
	    boolean geloescht = kundeDAO.deleteKunde(kundennummer);
	    
	    // Wenn gelöscht, auch aktuelles Kunde-Objekt im Model leeren
	    if (geloescht && this.kunde != null && this.kunde.getHausnummer() == hausnummer) {
	    	deleteSonderwunschHasHaus(hausnummer);
	        this.kunde = null;
	    }
	    return geloescht;
=======
	public boolean loescheKunden(int hausnummer) throws SQLException {
		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		boolean geloescht = kundeDAO.deleteKunde(hausnummer);

		// Wenn gelöscht, auch aktuelles Kunde-Objekt im Model leeren
		if (geloescht && this.kunde != null && this.kunde.getHausnummer() == hausnummer) {
			this.kunde = null;
		}
		return geloescht;
>>>>>>> refs/remotes/origin/dev
	}
<<<<<<< HEAD
	
	public void updateKunde (Kunde kunde) throws SQLException, Exception {
	    KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
	    // Damit der Kunde mit der jeweiligen ID geändert wird und nicht 
	    // ausversehen ein weitere Datensatz hinzugefügt wird
	    //kunde.setIdKunde(this.kunde.getIdKunde());
	    kundeDAO.updateKunde(kunde);
=======

	public void updateKunde(Kunde kunde) throws SQLException, Exception {
		KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		kundeDAO.updateKunde(kunde);
>>>>>>> refs/remotes/origin/dev
	}

	/**
	 * Checks whether the given customer data is valid.
	 *
	 * @param kunde the customer object to validate
	 * @return true if all required fields contain valid data; false otherwise
	 * @throws SQLException
	 */
	public boolean isValidCustomer(Kunde kunde) throws SQLException {
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
		if (kundeDAO.istHausnummerBesetzt(kunde.getHausnummer())) {
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

	// Name normalisieren: trimmen, alles klein, ersten Buchstaben groß
	private String normalizeName(String name) {
		if (name == null) {
			return null;
		}
		name = name.trim(); // Leerzeichen am Anfang/Ende weg
		if (name.isEmpty()) {
			return name;
		}
		name = name.toLowerCase();
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	// Prüft, ob Name nur aus Buchstaben besteht (inkl. deutscher Umlaute)
	private boolean isValidName(String name) {
		if (isNullOrEmpty(name)) {
			return false;
		}
		name = name.trim();
		return name.matches("[A-Za-zÄÖÜäöüß]+");
	}

	// bearbeitet von Yamam
	public String getLastValidationError() {
		return lastValidationError;
	}

	// ------------ Sonderwünsche ------------
	private int[] ausgewaehlteSw = null;// enhaelt die IDs der ausgewaehlten Sonderwünsche
	private SonderwuenscheDAOImplementation swDao = new SonderwuenscheDAOImplementation();
<<<<<<< HEAD
	
	public int[] gibAusgewaehlteSwLokal() {
		if (kunde == null) return null;
		if (this.ausgewaehlteSw == null) return null;
		return this.ausgewaehlteSw.clone();
	}
	
=======

	public int[] gibAusgewaehlteSwLokal() {
		if (kunde == null)
			return null;
		if (this.ausgewaehlteSw == null)
			return null;
		return this.ausgewaehlteSw.clone();
	}

>>>>>>> refs/remotes/origin/dev
	/**
	 * Holt Sonderwünsche zu einem Kunden und gibt ein Array an Sonderwunschoptionen
	 * oder null.
	 *
	 * @return Klon von this.ausgewaehlteSw oder null
	 */
	public int[] gibAusgewaehlteSwAusDb() {
<<<<<<< HEAD
		if (kunde == null) return null; 
=======
		if (kunde == null)
			return null;
>>>>>>> refs/remotes/origin/dev
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
	 * Holt Sonderwünsche zu einem Kunden und gibt ein Array an Sonderwunschoptionen
	 * oder null.
	 *
	 * @param ID einer Sonderwunschkategorie als int
	 * @return Klon von this.ausgewaehlteSw oder null
	 */
	public int[] gibAusgewaehlteSwAusDb(int kategorieId) {
<<<<<<< HEAD
		if (this.kunde == null) return null;
		// throw new Exception("Fehler beim Laden ausgewählter Sonderwünsche: Es konnte kein Kunde gefunden werden");
=======
		if (this.kunde == null)
			return null;
		// throw new Exception("Fehler beim Laden ausgewählter Sonderwünsche: Es konnte
		// kein Kunde gefunden werden");
>>>>>>> refs/remotes/origin/dev
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
	 * Holt Sonderwünsche zu einem Kunden Gibt ein Array an Sonderwunschoptionen
	 * zurueck, wenn ausgewaehlteSw nicht null ist. Ansonsten wird
	 * holeAusgewaehlteSwAusDb() aufgerufen.
	 *
	 * @param int[] mit IDs der ausgewaehlten Sonderwünsche
	 * @throws SQLExceptio oder Exception
	 */
	public void updateAusgewaehlteSw(int[] ausgewaehlteSw) throws SQLException, Exception {
		if (this.kunde == null)
			throw new Exception(
					"Fehler beim Aktualisieren ausgewählter Sonderwünsche: Es konnte kein Kunde gefunden werden");
		;
		int hausnr = this.kunde.getHausnummer();

		try {
			this.swDao.update(hausnr, ausgewaehlteSw);
			this.ausgewaehlteSw = ausgewaehlteSw;
		} catch (SQLException exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche: SQL Fehler");
			// exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche");
			// exc.printStackTrace();
<<<<<<< HEAD
			throw exc;
		}
	}
	
	public void deleteSonderwunschHasHaus(int hausnummer) throws SQLException, Exception {
		try {
			this.swDao.delete(hausnummer);
		} catch (SQLException exc) {
			System.out.println("Fehler beim Delete Sondderwunsch_has_Haus: SQL Fehler");
			exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			System.out.println("Fehler beim Delete Sondderwunsch_has_Haus");
			exc.printStackTrace();
=======
>>>>>>> refs/remotes/origin/dev
			throw exc;
		}
	}
}