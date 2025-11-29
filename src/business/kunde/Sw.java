package business.kunde;

/*
 * Beispiele:
 * 	 // ID über Enum erhalten
 *   int idSw = Sw.STD_HEIZKOERPER.id;
 *   
 *   // Enum über ID erhalten und switch-case
 *   Sw sw = Sw.findeMitId(501);
 *   switch (sw) {
 *   	case STD_HEIZKOERPER:
 *   		...
 *   		break;
 *   	default:
 *   		...
 *   		break;
 *   }
 */

public enum Sw {
	// TODO: Restliche Optionen ergänzen (siehe "[Info] Sonderwunsch-Optionen" im Backlog)
	// TODO: Beschreibung ersetzen
	
	// --- Vordefinierte Enums ---
	// Grundriss-Varianten
	// Fenster & Außentüren
	// Innentüren
	// Heizkörper
    STD_HEIZKOERPER		(501, "Zusätzlicher Standard-Heizkörper (je Stück)", 660.0),
    GLATT_HEIZKOERPER	(502, "Heizkörper mit glatter Oberfläche (je Stück)", 160.0),
    HANDTUCH			(503, "Handtuchheizkörper (je Stück)", 660.0),
    FBH_OHNE_DG			(504, "Fußbodenheizung ohne DG", 8990.0),
    FBH_MIT_DG			(505, "Fußbodenheizung mit DG", 9990.0),
	// Sanitärinstallationen
	// Fliesen
	// Parkett
    ;
	
	// --- Enum Definition ---
    // Felder
	public final int id;
    public final String bes;
    public final double preis;

    // Konstruktor
    private Sw(int id, String bes, double preis) {
    	this.id = id;
    	this.bes = bes;
    	this.preis = preis;
    }
    
    /**
     * Finde Element mit übergebener id. Hilfreich für Switch-Case-Statements.
     * @param id als int
     * @return Element vom Typ Sw oder null
     */
    public static Sw findeMitId(int id) {
    	for (Sw sw: values())
    		if (sw.id == id)
    			return sw;
		return null;
    }
}
