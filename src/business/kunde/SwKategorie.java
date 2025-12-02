package business.kunde;

/*
 * Beispiele:
 * 	 // ID über Enum erhalten
 *   int idSwk = SwKategorie.GRUNDRISS.id;
 *   
 *   // Enum über ID erhalten und switch-case
 *   SwKategorie swk = SwKategorie.findeMitId(20);
 *   switch (swk) {
 *   	case GRUNDRISS:
 *   		...
 *   		break;
 *   	default:
 *   		...
 *   		break;
 *   }
 */

public enum SwKategorie {
	// --- Vordefinierte Enums ---
	GRUNDRISS(20, "Grundriss-Varianten"),
	FENSTER_AUSSENTUEREN(30, "Fenster & Außentüren"),
	INNENTUEREN(40, "Innentüren"),
	HEIZKOERPER(50, "Heizkörper"),
	SANITAERINSTALLATION(60, "Sanitärinstallation"),
	FLIESEN(70, "Fliesen"),
	PARKETT(80, "Parkett");
	
	
	// --- Enum Definition ---
    // Felder
	public final int id;
	public final String bes;
	
	// Konstruktor
    private SwKategorie(int id, String bes) {
    	this.id = id;
    	this.bes = bes;
    }
    
    /**
     * Finde Element mit übergebener id. Hilfreich für Switch-Case-Statements.
     * @param id als int
     * @return Element vom Typ SwKategorie oder null
     */
    public static SwKategorie findeMitId(int id) {
    	for (SwKategorie swk: values())
    		if (swk.id == id)
    			return swk;
		return null;
    }
}
