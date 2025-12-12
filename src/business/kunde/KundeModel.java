package business.kunde;

import java.io.InputStream;
import java.sql.SQLException;
import javafx.collections.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.io.InputStream;


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
		
		// Die ID, die von der DB gegeben wurde laden und speichern lokal in Kunde.idKunde
		int idKunde = (kundeDAO.findByHausnummer(this.kunde.getHausnummer())).getIdKunde();
		this.kunde.setIdKunde(idKunde);
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
	}

	public void updateKunde (Kunde kunde) throws SQLException, Exception {
	    KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
	    kundeDAO.updateKunde(kunde);
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
	@Deprecated
	private int[] ausgewaehlteSw = null;// enhaelt die IDs der ausgewaehlten Sonderwünsche
	private int[][] ausgewaehlteSwMitAnzahl = null;
	private SonderwuenscheDAOImplementation swDao = new SonderwuenscheDAOImplementation();
	
	@Deprecated
	public int[] gibAusgewaehlteSwLokal() {
		if (kunde == null) return null;
		if (this.ausgewaehlteSw == null) return null;
		return this.ausgewaehlteSw.clone();
	}
	public int[][] gibAusgewaehlteSwMitAnzahlLokal() {
		if (kunde == null) return null;
		if (this.ausgewaehlteSwMitAnzahl == null) return null;
		return this.ausgewaehlteSwMitAnzahl.clone();
	}
	
	/**
	 * Holt Sonderwünsche zu einem Kunden, setzt sie und gibt ein Array an Sonderwunschoptionen oder null.
	 *
	 * @return Klon von this.ausgewaehlteSw oder null 
	 */
	@Deprecated
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
	 * Holt Sonderwünsche zu einem Kunden, setzt sie und gibt ein Array an Sonderwunschoptionen oder null.
	 *
	 * @param ID einer Sonderwunschkategorie als int
	 * @return Klon von this.ausgewaehlteSw oder null
	 */
	@Deprecated
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
	 * Gibt Sonderwünsche zu einem Kunden ohne angegebener Kategorie oder null zurück.
	 * 
	 * @param kategorieId, zu der Kategorie, die aus der Rückgabe ausgeschlossen werden soll
	 * @return int[] mit IDs ohne die der angegebenen Kategorie
	 */
	@Deprecated
	public int[] holeAusgewaehlteSwAusDbOhneKategorie(int kategorieId) {
		if (this.kunde == null) return null;
		int hausnr = this.kunde.getHausnummer();
		
		try {
			return this.swDao.getExcluding(hausnr, kategorieId);
		} catch (SQLException exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche: SQL Fehler");
			exc.printStackTrace();
		} catch (Exception exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche ohne " + kategorieId);
			exc.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * 
	 * @return ein int[i][2] mit Paaren aus Sonderwunsch-ID und Anzahl
	 */
	public int[][] gibAusgewaehlteSwMitAnzahlAusDb() {
		if (kunde == null) return null; 
		// throw new Exception("Es konnte kein Kunde gefunden werden");
		int hausnr = this.kunde.getHausnummer();
		
		try {
			this.ausgewaehlteSwMitAnzahl = this.swDao.getMitAnzahl(hausnr);
			return this.ausgewaehlteSwMitAnzahl.clone();
		} catch (SQLException exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche mit Anzahl: SQL Fehler");
			exc.printStackTrace();
		} catch (Exception exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche mit Anzahl");
			exc.printStackTrace();
		}
		return null;
	}
	
	public int[][] gibAusgewaehlteSwMitAnzahlAusDb(int kategorieId) {
		if (kunde == null) return null; 
		// throw new Exception("Es konnte kein Kunde gefunden werden");
		int hausnr = this.kunde.getHausnummer();
		
		try {
			this.ausgewaehlteSwMitAnzahl = this.swDao.getMitAnzahl(hausnr, kategorieId);
			return this.ausgewaehlteSwMitAnzahl.clone();
		} catch (SQLException exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche mit Anzahl: SQL Fehler");
			exc.printStackTrace();
		} catch (Exception exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche mit Anzahl");
			exc.printStackTrace();
		}
		return null;
	}
	
	public int[][] holeAusgewaehlteSwMitAnzahlAusDbOhneKategorie(int kategorieId) {
		if (this.kunde == null) return null;
		int hausnr = this.kunde.getHausnummer();
		
		try {
			return this.swDao.getMitAnzahlExcluding(hausnr, kategorieId);
		} catch (SQLException exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche mit Anzahl: SQL Fehler");
			exc.printStackTrace();
		} catch (Exception exc) {
			System.out.println("Fehler beim Laden ausgewählter Sonderwünsche (mit Anzahl) ohne " + kategorieId);
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
	@Deprecated
	public void updateAusgewaehlteSw(int[] ausgewaehlteSw) throws SQLException, Exception {
		if (this.kunde == null)
			throw new Exception("Fehler beim Aktualisieren ausgewählter Sonderwünsche: Es konnte kein Kunde gefunden werden");;
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
			throw exc;
		}
	}
	
	/**
	 * Löscht alle vorhandenen Sonderwünsche zu einem Haus und fügt die übergebenen wieder ein.
	 * Falls es Sonderwünsche mit Anzahl gibt, müssen diese als zusätzlicher Parameter angegeben
	 * werden. Diese muss ein int[][] sein, wobei Sonderwunsch-ID und Anzahl nach folgendem Schema
	 * angegeben werden sollen:
	 * 		{{id_1, anz_1}, {id_2, anz_2}, ...}
	 * 
	 * @param hausnummer ist diejenige Hausnummer, die die angegebnen Sonderwünsche betreffen   
	 * @param ausgewaehlteSw ist ein int[] an allen Sonderwunsch-IDs, die zu einem Haus ausgesucht wurden
	 * @throws SQLException
	 * @throws Exception
	 */
	@Deprecated
	public void updateAusgewaehlteSw(int[] ausgewaehlteSw, int[][] ausgewaehlteSwMitAnzahl)
			throws SQLException, Exception {
		if (this.kunde == null)
			throw new Exception("Fehler beim Aktualisieren ausgewählter Sonderwünsche: Es konnte kein Kunde gefunden werden");;
		int hausnr = this.kunde.getHausnummer();
		
		// Auf Duplikate prüfen
		if (ausgewaehlteSw != null && ausgewaehlteSwMitAnzahl != null)
			for (int sw: ausgewaehlteSw)
				for (int[] swMA: ausgewaehlteSwMitAnzahl)
					if (sw == swMA[0])
						throw new IllegalArgumentException("Die Sonderwunsch-ID " + sw + " wurde mehrfach übergeben");
		
		try {
			this.swDao.update(hausnr, ausgewaehlteSw, ausgewaehlteSwMitAnzahl);
			this.ausgewaehlteSw = ausgewaehlteSw;
			this.ausgewaehlteSwMitAnzahl = ausgewaehlteSwMitAnzahl;
		} catch (SQLException exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche mit Anzahl: SQL Fehler");
			// exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche mit Anzahl");
			// exc.printStackTrace();
			throw exc;
		}
	}
	// Aktuellste Variante
	public void updateAusgewaehlteSw(int[][] ausgewaehlteSwMitAnzahl)
			throws SQLException, Exception {
		if (this.kunde == null)
			throw new Exception("Fehler beim Aktualisieren ausgewählter Sonderwünsche: Es konnte kein Kunde gefunden werden");;
		int hausnr = this.kunde.getHausnummer();
		try {
			this.swDao.update(hausnr, ausgewaehlteSwMitAnzahl);
			this.ausgewaehlteSwMitAnzahl = ausgewaehlteSwMitAnzahl;
		} catch (SQLException exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche mit Anzahl: SQL Fehler");
			// exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche mit Anzahl");
			// exc.printStackTrace();
			throw exc;
		}
	}
	
	/**
     * [3] Speichert Sonderwünsche einer spezifischen Kategorie (z.B. Fliesen),
     * ohne die Sonderwünsche anderer Kategorien (z.B. Grundriss) zu löschen.
     * * @param neueAuswahlIDs Ein Array mit den IDs der neu gewählten Sonderwünsche.
     * @param kategorieId    Die ID der Kategorie (z.B. 1 für Fliesen), deren Daten ersetzt werden sollen.
     */
	@Deprecated
    public void speichereSonderwuenscheFuerKategorie(int[] neueAuswahlIDs, int kategorieId) throws Exception {
        if (this.kunde == null) {
            throw new Exception("Fehler: Kein Kunde ausgewählt.");
        }
        int hausnr = this.kunde.getHausnummer();

        // 1. Hole ALLE aktuellen Sonderwünsche des Kunden aus der DB (z.B. Grundriss + alte Fliesen)
        int[] alleVorhandenen = swDao.get(hausnr);
        
        // 2. Hole nur die alten Sonderwünsche dieser speziellen Kategorie (z.B. alte Fliesen)
        int[] alteKategorieDaten = swDao.get(hausnr, kategorieId);

        // Hilfsliste zum Bearbeiten erstellen
        ArrayList<Integer> neueGesamtListe = new ArrayList<>();

        // Füge zunächst alle vorhandenen Einträge der Liste hinzu
        if (alleVorhandenen != null) {
            for (int id : alleVorhandenen) {
                neueGesamtListe.add(id);
            }
        }

        // 3. Entferne die "alten" Einträge der angegebenen Kategorie aus der Liste
        // (Wir löschen den alten Stand der Fliesen, um den neuen zu setzen)
        if (alteKategorieDaten != null) {
            for (int altId : alteKategorieDaten) {
                // Wichtig: Integer.valueOf nutzen, damit das Objekt (die ID) entfernt wird, nicht der Index
                neueGesamtListe.remove(Integer.valueOf(altId));
            }
        }

        // 4. Füge die NEUE Auswahl hinzu
        if (neueAuswahlIDs != null) {
            for (int neuId : neueAuswahlIDs) {
                // Nur hinzufügen, wenn noch nicht vorhanden (Vermeidung von Duplikaten)
                if (!neueGesamtListe.contains(neuId)) {
                    neueGesamtListe.add(neuId);
                }
            }
        }

        // 5. Konvertiere die Liste zurück in ein int-Array
        int[] updateArray = neueGesamtListe.stream().mapToInt(i -> i).toArray();

        // 6. Rufe die sichere Update-Methode auf
        // Da updateArray jetzt ALLES enthält (Grundriss + NEUE Fliesen), ist das Löschen im DAO sicher.
        this.updateAusgewaehlteSw(updateArray);
        
        System.out.println("✅ Sonderwünsche für Kategorie " + kategorieId + " erfolgreich gespeichert.");
    }
    @Deprecated
    public void speichereSonderwuenscheFuerKategorie(int[] neueSw, int[][] neueSwMitAnzahl, int kategorieId)
    		throws Exception {
        if (this.kunde == null) {
            throw new Exception("Fehler: Kein Kunde ausgewählt.");
        }
        int hausnr = this.kunde.getHausnummer();
        
        try {
	        // Auf Duplikate prüfen
        	if (neueSw != null && ausgewaehlteSwMitAnzahl != null)
	 		for (int sw: neueSw)
	 			for (int[] swMA: ausgewaehlteSwMitAnzahl)
	 				if (sw == swMA[0])
	 					throw new IllegalArgumentException("Die Sonderwunsch-ID " + sw + " wurde mehrfach übergeben");
	     	
	 		// Alte Sw, die nicht zur Kategorie zählen, mit Anzahl holen
	 		// {{id_1, anz_1}, {id_2, anz_2}, ...)
	     	int[][] alteSwMitAnzahlExcluding = this.swDao.getMitAnzahlExcluding(hausnr, kategorieId);
	     	
	     	// Alte mit neuen Sonderwünschen fusionieren
	     	int[][] alleSwMitAnzahl = null;
			if (neueSw != null && ausgewaehlteSwMitAnzahl != null) {
				alleSwMitAnzahl = new int[neueSwMitAnzahl.length + alteSwMitAnzahlExcluding.length][2];
				for (int i = 0; i < alleSwMitAnzahl.length; i++) {
					if (i < alteSwMitAnzahlExcluding.length) { // alte Sw mit Anzahl
						alleSwMitAnzahl[i] = alteSwMitAnzahlExcluding[i];
					} else { // neue Sw mit Anzahl
						alleSwMitAnzahl[i] = neueSwMitAnzahl[i];
					}
				}
        	}
	     	
	     	this.swDao.update(hausnr, neueSw, alleSwMitAnzahl);
	    } catch (SQLException exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche: SQL Fehler");
			// exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche");
			// exc.printStackTrace();
			throw exc;
		}
        
        System.out.println("✅ Sonderwünsche für Kategorie " + kategorieId + " erfolgreich gespeichert.");
    }
    
    // Aktuellste Variante
    public void speichereSonderwuenscheFuerKategorie(int[][] neueSwMitAnzahl, int kategorieId)
    		throws Exception {
        if (this.kunde == null) {
            throw new Exception("Fehler: Kein Kunde ausgewählt.");
        }
        int hausnr = this.kunde.getHausnummer();
        
        try {
	 		// Alte Sw, die nicht zur Kategorie zählen, mit Anzahl holen
	 		// {{id_1, anz_1}, {id_2, anz_2}, ...)
	     	int[][] alteSwMitAnzahlExcluding = this.swDao.getMitAnzahlExcluding(hausnr, kategorieId);
	     	
	     	// Alte mit neuen Sonderwünschen fusionieren
	     	int[][] alleSwMitAnzahl = null;
	     	if (ausgewaehlteSwMitAnzahl != null && neueSwMitAnzahl != null) {
				alleSwMitAnzahl = new int[neueSwMitAnzahl.length + alteSwMitAnzahlExcluding.length][2];
				for (int i = 0; i < alleSwMitAnzahl.length; i++) {
					if (i < alteSwMitAnzahlExcluding.length) { 
						// Alte Sw mit Anzahl
						alleSwMitAnzahl[i] = alteSwMitAnzahlExcluding[i];
					} else { 
						// Neue Sw mit Anzahl
						int neueIndex = i -  alteSwMitAnzahlExcluding.length;
						if(neueIndex < alleSwMitAnzahl.length) {
							alleSwMitAnzahl[i] = neueSwMitAnzahl[neueIndex];
						} else {

						}
					}
				}
        	}
	     	this.swDao.update(hausnr, alleSwMitAnzahl);
	    } catch (SQLException exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche: SQL Fehler");
			// exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			System.out.println("Fehler beim Updaten ausgewählter Sonderwünsche");
			// exc.printStackTrace();
			throw exc;
		}
        
        System.out.println("✅ Sonderwünsche für Kategorie " + kategorieId + " erfolgreich gespeichert.");
    }
    
    /**
     * Gibt das aktuelle Kunde-Objekt zurück.
     * Wird benötigt, um zu prüfen, ob ein Kunde ausgewählt ist.
     */
    public Kunde getKunde() {
        return this.kunde;
    }
    
    /* 		Damit sich die Sonderwunsch-View nicht öffnet, wenn keine Kundendaten vorhanden sind.
     * 		Details zum Bug:
     * 		Wenn man einen Kunden bzw. eine Hausnummer mit vorhandenen Kundendaten auswählt
     * 		und anschließend auf Hausnummer 0 wechselt
     * 		und dann ein Sonderwunsch-Fenster öffnen möchte, wird dieses dennoch geöffnet.
     * 		Eine andere Loesung waere beim ...control.oeffneSonderwuenscheFenster neben existiert Kunde, 
     * 		nachschaut ob die Hausnummer ungleich 0 ist.
     */
    public void setAttributesNull() {
        this.kunde = null;
        this.ausgewaehlteSw = null;
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
			throw exc;
		}
	}
    
  	// Bild aus der DB bekommen
	  public InputStream holBildAusDB(int idBild) throws SQLException, Exception {
		    KundeDaoImplementation kundeDAO = new KundeDaoImplementation();
		    return kundeDAO.loadImage(idBild);
	  }
    
}
