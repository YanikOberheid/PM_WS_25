package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.kunde.Kunde;
import business.kunde.KundeModel;
import business.kunde.Sw;
import business.kunde.SwKategorie;

/**
 * JUnit-Test zum Speichern und Laden von Sonderwünschen zu Heizkörpern
 * (Heizungen) über das KundeModel / DB.
 *
 * Aus: [3] Heizkörper Sonderwünsche speichern, [3] Heizkörper Sonderwünsche laden
 */
public class HeizkoerperLadenSpeichernTest {

    private KundeModel kundeModel;

    @BeforeEach
    void setUp() throws Exception {
        kundeModel = KundeModel.getInstance();

        // Test-Kunde anlegen und über das Model speichern
        // -> dabei wird intern this.kunde gesetzt
        Kunde testKunde = new Kunde(
                1,                       
                "Max",
                "Mustermann",
                "123456",
                "testmail@test.de"
        );

        kundeModel.speichereKunden(testKunde);
    }

    @Test
    void testSpeichernUndLadenHeizungsSonderwuensche() throws Exception {
        int[] erwartet = {
                Sw.STD_HEIZKOERPER.id,
                Sw.HANDTUCH.id
        };   
        kundeModel.speichereSonderwuenscheFuerKategorie(
                erwartet,
                SwKategorie.HEIZKOERPER.id
        );

        int[] geladen = kundeModel.gibAusgewaehlteSwAusDb(SwKategorie.HEIZKOERPER.id);

        // Sicherheitshalber sortieren, falls DB-Reihenfolge abweicht
        Arrays.sort(erwartet);
        Arrays.sort(geladen);

        assertArrayEquals(
                erwartet,
                geladen,
                "Die gespeicherten Heizkörper-Sonderwünsche sollten nach dem Laden identisch sein"
        );
    }
}
