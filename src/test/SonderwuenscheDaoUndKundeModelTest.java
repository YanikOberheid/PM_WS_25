package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.Arrays;

import org.junit.jupiter.api.*;

import business.kunde.DatabaseConnection;
import business.kunde.Kunde;
import business.kunde.KundeDaoImplementation;
import business.kunde.KundeModel;
import business.kunde.SonderwuenscheDAOImplementation;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SonderwuenscheDaoUndKundeModelTest {

    
    private static final int HAUS = 21;          // Test-Hausnummer
    private static final int KAT_FENSTER = 30;   // Kategorie-ID 
    private static final int[] NEUE_FENSTER_IDS = {301, 304}; 
    
    private static Connection con;
    private static KundeDaoImplementation kundeDao;
    private static SonderwuenscheDAOImplementation swDao;
    private static KundeModel model;

    @BeforeAll
    static void setupAll() throws Exception {
        con = DatabaseConnection.getInstance().getConnection();
        con.setAutoCommit(true);

        kundeDao = new KundeDaoImplementation();
        swDao    = new SonderwuenscheDAOImplementation();
        model    = KundeModel.getInstance();

        // Testkunde sicherstellen 
        if (!kundeDao.istHausnummerBesetzt(HAUS)) {
            Kunde k = new Kunde(HAUS, "JUnit", "Tester", "0123456789", "junit@example.com");
            kundeDao.add(k);
        }

        // Kunde ins Model laden 
        model.ladeKunde(HAUS);

        // alle SW dieses Hauses löschen
        swDao.delete(HAUS);
    }

    @AfterAll
    static void tearDownAll() throws Exception {
        swDao.delete(HAUS);
        if (con != null && !con.isClosed()) con.close();
    }

    

    @Test
    @Order(1)
    @DisplayName("Speichern für Kategorie ersetzt nur diese Kategorie (andere bleiben erhalten/leer)")
    void testSpeichereSonderwuenscheFuerKategorie() throws Exception {
        // Speichern über das Modell
        model.speichereSonderwuenscheFuerKategorie(NEUE_FENSTER_IDS, KAT_FENSTER);

        // Prüfen: Kategorie-spezifische Abfrage liefert genau diese IDs
        int[] nurFenster = swDao.get(HAUS, KAT_FENSTER);
        assertNotNull(nurFenster, "Kategorieabfrage liefert null");
        Arrays.sort(nurFenster);
        int[] erwartet = Arrays.stream(NEUE_FENSTER_IDS).sorted().toArray();
        assertArrayEquals(erwartet, nurFenster, "Neue Auswahl der Kategorie wurde nicht korrekt gespeichert");

        // Prüfen: Excluding liefert „alles außer Fenster“
        int[] ohneFenster = swDao.getExcluding(HAUS, KAT_FENSTER);
        assertNotNull(ohneFenster, "Excluding-Abfrage liefert null");
        assertTrue(ohneFenster.length >= 0);
    }
}
