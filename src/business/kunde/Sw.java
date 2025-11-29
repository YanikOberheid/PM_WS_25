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
	// --- Vordefinierte Enums ---
	// Grundriss-Varianten
	WAND_KUECHE			(201, "Wand zur Abtrennung der Küche von dem Essbereich", 300.0),
	TUER_KUECHE			(202, "Tür in der Wand zwischen Küche und Essbereich", 300.0),
	GROSSES_ZIMMER		(203, "Großes Zimmer im OG statt uwei kleinen Zimmern", 0.0),
	TREPPENRAUM_DG		(204, "Abgetrennter Treppenraum im DG", 890.0),
	VORRICHTUNG_BAD_DG	(205, "Vorrichtung eines Bades im DG", 990.0),
	AUSFUEHRUNG_BAD_DG	(206, "Ausführung eines Bades im DG", 8990.0),
	// Fenster & Außentüren
	STUEREN_TERRASSE	(301, "Schiebetüren im EG zur Terrasse", 590.0),
	STUEREN_DACHTERRASSE(302, "Schiebetüren im DG zur Dachterrasse", 590.0),
	EBS_HAUSTUER		(303, "Erhöhter Einbruchschutz an der Haustür", 690.0),
	VEAR_EG				(304, "Vorbereitung für elektrische Antriebe Rolläden EG", 190.0),
	VEAR_OG				(305, "Vorbereitung für elektrische Antriebe Rolläden OG", 190.0),
	VEAR_DG				(306, "Vorbereitung für elektrische Antriebe Rolläden DG", 190.0),
	ER_EG				(307, "Elektrische Rolläden EG", 990.0),
	ER_OG				(308, "Elektrische Rolläden OG", 990.0),
	ER_DG				(309, "Elektrische Rolläden DG", 990.0),
	// Innentüren
	KLARGLAS_TUER_INNEN	(401, "Mehrpreis für die Ausführung eines Glasausschnitts (Klarglas) in einer Innentür (je Stück)", 460.0),
	MILCHGLAS_TUER_INNEN(402, "Mehrpreis für die Ausführung eines Glasausschnitts (Milchglas) in einer Innentür (je Stück)", 560.0),
	HOLZTUER_GARAGE		(403, "Innentür zur Garage als Holztür", 660.0),
	// Heizkörper
    STD_HEIZKOERPER		(501, "Zusätzlicher Standard-Heizkörper (je Stück)", 660.0),
    GLATT_HEIZKOERPER	(502, "Heizkörper mit glatter Oberfläche (je Stück)", 160.0),
    HANDTUCH			(503, "Handtuchheizkörper (je Stück)", 660.0),
    FBH_OHNE_DG			(504, "Fußbodenheizung ohne DG", 8990.0),
    FBH_MIT_DG			(505, "Fußbodenheizung mit DG", 9990.0),
	// Sanitärinstallationen
    WASCHBECKEN_OG_GROSS(601, "Mehrpreis für ein größeres Waschbecken im OG", 160.0),
    WASCHBECKEN_DG_GROSS(602, "Mehrpreis für ein größeres Waschbecken im DG", 160.0),
    DUSCHE_OG_TIEF		(603, "Mehrpreis für eine bodentiefe Dusche im OG", 560.0),
    DUSCHE_DG_TIEF		(604, "Mehrpreis für eine bodentiefe Dusche im DG", 560.0),
	// Fliesen
    F_KUECHE_EG_OHNE	(701, "Keine Fliesen im Küchenbereich des EG", -590.0),
    F_BAD_OG_OHNE		(702, "Keine Fliesen im Bad des OG", -1870.0),
    F_KUECHE_EG_GROSS	(703, "Mehrpreis bei großformatige Fliesen im Küchenbereich des EG", 170.0),
    F_BAD_OG_GROSS		(704, "Mehrpreis bei großformatige Fliesen im Bad des OG", 190.0),
    F_BAD_DG			(705, "Fliesen im Bad des DG", 2190.0),
    F_BAD_DG_GROSS		(706, "Mehrpreis bei großformatige Fliesen im Bad des DG", 190.0),
	// Parkett
    LHD_M_ESS_EG		(801, "Landhausdielen massiv im Essbereich des EG", 2890.0),
    LHD_M_KUECHE_EG		(802, "Landhausdielen massiv im Küchenbereich des EG", 2090.0),
    SP_ESS_EG			(803, "Stäbchenparkett im Essbereich des EG", 2090.0),
    SP_KUECHE_EG		(804, "Stäbchenparkett im Küchenbereich des EG", 1790.0),
    LHD_M_OG			(805, "Landhausdielen massiv im OG", 2490.0),
    SP_OG				(806, "Stäbchenparkett im OG", 1690.0),
    LHD_M_KOMPLETT_DG	(807, "Landhausdielen massiv komplett im DG", 2490.0),
    LDH_M_OHNE_BAD_DG	(808, "Landhausdielen massiv ohne Badbereich im DG", 2090.0),
    SP_KOMPLETT_DG		(809, "Stäbchenparkett komplett im DG", 1690.0),
    SP_OHNE_BAD_DG		(810, "Stäbchenparkett ohne Badbereich im DG", 1690.0),
    // Außenanlagen
    ABSTELL_TERRASSE_EG	(901, "Abstellraum auf der Terrasse des EG", 3590.0),
    VEA_MARKISE_EG		(902, "Vorbereitug für elektrische Antriebe Markise EG", 170.0),
    VEA_MARKISE_DG		(903, "Vorbereitung für elektrische Antriebe Markise DG", 170.0),
    E_MARKISE_EG		(904, "Elektrische Markise EG", 890.0),
    E_MARKISE_DG		(905, "Elektrische Markise DG", 890.0),
    EA_GARAGENTOR		(906, "Elektrischen Antrieb für das Garagentor", 990.0),
    ST_GARAGENTOR		(907, "Sektionaltor anstatt Schwingtor für die Garage", 790.0);
	
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
