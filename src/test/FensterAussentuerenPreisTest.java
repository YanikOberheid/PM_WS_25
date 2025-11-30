package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import business.kunde.DatabaseConnection;
import business.kunde.KundeModel;

/**
 * Integrationstest für die Preisberechnung ausgewählter Sonderwünsche
 * im Bereich Fenster/Außentüren.
 *
 * Idee:
 *  - Erwarteten Wert direkt über die Datenbank ermitteln (SUM(Preis) über IDs)
 *  - Tatsächlichen Wert über die produktive Modelllogik ermitteln
 *  - Beide Werte vergleichen
 */
public class FensterAussentuerenPreisTest {

    /** Gemeinsame DB-Verbindung für den Testlauf. */
    private static Connection con;

    /** Produktives Modell, dessen Berechnungslogik geprüft wird. */
    private static KundeModel model;

    /**
     * Einmalige Initialisierung vor allen Tests.
     *  - Holt eine gültige DB-Verbindung aus der Infrastruktur
     *  - Initialisiert das Modell analog zur Anwendung
     */
    @BeforeAll
    static void init() throws Exception {
        con = DatabaseConnection.getInstance().getConnection();
        // Autocommit genügt hier, da nur lesende Abfragen (SUM) durchgeführt werden
        con.setAutoCommit(true);

        // Modellinitialisierung wie im Produktivcode (Singleton/Stamminstanz)
        model = KundeModel.getInstance(); // alternativ: new KundeModel()
    }

    /**
     * Ressourcenbereinigung am Ende.
     */
    @AfterAll
    static void teardown() throws Exception {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

    /**
     * Prüft, dass die vom Modell berechnete Summe der Preise den in der
     * Datenbank hinterlegten Preisen für die angegebenen Sonderwunsch-IDs entspricht.
     */
    @Test
    @DisplayName("[3] Preisberechnung Fenster/Außentüren – Summe der ausgewählten IDs")
    void testPreisberechnungFensterAussentueren() throws Exception {
        // Reale IDs aus Tabelle „Sonderwunsch“ (Fenster/Außentüren):
        // 2 = Zusätzliche Fenster, 3 = Balkontür statt Fenster
        final int[] sonderwunschIds = { 2, 3 };

        // Erwartung direkt aus der DB (Quelle der Wahrheit)
        int expected = summePreiseAusDb(sonderwunschIds);

        // Tatsächliche Berechnung über die produktive Modellfunktion
        int actual = model.berechnePreisFuerSonderwunschIds(sonderwunschIds);

        // Vergleich beider Ergebnisse
        assertEquals(
            expected,
            actual,
            () -> "Summe weicht ab. IDs=" + Arrays.toString(sonderwunschIds)
        );
    }

    /**
     * Hilfsfunktion: Aggregiert den erwarteten Wert aus der DB.
     * Bildet dynamisch die Platzhalter für das IN-Statement
     * und summiert die Preise der übergebenen IDs.
     */
    private int summePreiseAusDb(int[] ids) throws Exception {
        // Platzhalterliste ?,?,?... passend zur Anzahl der IDs
        String placeholders = String.join(",", Arrays.stream(ids)
                                                     .mapToObj(i -> "?")
                                                     .toArray(String[]::new));

        String sql = "SELECT COALESCE(SUM(Preis), 0) "
                   + "FROM Sonderwunsch "
                   + "WHERE idSonderwunsch IN (" + placeholders + ")";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int p = 1;
            for (int id : ids) {
                ps.setInt(p++, id);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
}
