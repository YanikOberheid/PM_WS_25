package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import business.kunde.SonderwuenscheDAOImplementation;
import business.kunde.SwKategorie;
import business.kunde.Sw;


class AussenanlagenSpeichernLadenTest {
    private static final int HAUSNUMMER = 1;
    private static final int KATEGORIE_AUSSENANLAGEN = SwKategorie.AUSSENANLAGEN.id;    
    private static final int[] TEST_AUSSENANLAGEN_SW = { Sw.ABSTELL_TERRASSE_EG.id,
    	    Sw.VEA_MARKISE_DG.id,
    	    Sw.ST_GARAGENTOR.id };
    
    private final SonderwuenscheDAOImplementation dao = new SonderwuenscheDAOImplementation();
    
    @AfterEach
    void cleanup() throws Exception {
        // Nach jedem Test: für dieses Haus wieder alles löschen,
        // damit unsere andere Tests nicht beeinflusst werden
        dao.update(HAUSNUMMER, new int[0]);
    }
    
    
	@Test
	 void testSpeichernUndLadenVonAussenanlagenSonderwuenschen() throws Exception {
        // 1. Ausgangszustand: alle Sonderwünsche für dieses Haus löschen
        dao.update(HAUSNUMMER, new int[0]);
        // 2. Außenanlagen-Sonderwünsche speichern
        int[] zuSpeichern = TEST_AUSSENANLAGEN_SW.clone();
        dao.update(HAUSNUMMER, zuSpeichern);
     // 3. Außenanlagen-Sonderwünsche wieder aus der DB laden (nur Kategorie Außenanlagen)
        int[] geladen = dao.get(HAUSNUMMER, KATEGORIE_AUSSENANLAGEN);
     // 4. Sortieren, da Reihenfolge in der DB nicht garantiert ist
        Arrays.sort(zuSpeichern);
        Arrays.sort(geladen);

        assertArrayEquals(
            zuSpeichern,
            geladen,
            "Gespeicherte und geladene Sonderwünsche zu Außenanlagen müssen übereinstimmen."
        );
    }


	}


