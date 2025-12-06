package test;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import org.junit.jupiter.api.*;

import business.kunde.DatabaseConnection;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FensterAussentuerenPreisTest {

    private Connection con;     // nur zum „expected“ aus der DB

    @BeforeAll
    void init() throws Exception {
        con = DatabaseConnection.getInstance().getConnection();
        con.setAutoCommit(true); // wir lesen nur
    }

    @AfterAll
    void teardown() throws Exception {
        if (con != null && !con.isClosed()) con.close();
    }

    
    @Test
    @DisplayName("[3] Preisberechnung Fenster/Außentüren – Summe der ausgewählten IDs")
    void testPreisberechnungFensterAussentueren() throws Exception {
        // Beispiel: 301 (Schiebetür EG) + 304 (Vorbereitung Rollläden EG)
        final int[] ids = {301, 304};

        // 1) Erwartung direkt aus der DB
        int expected = summePreiseAusDb(ids);

        // 2) Produktive Preisbasis aus FensterControl.PREISE via Reflection
        Class<?> cls = Class.forName("gui.fenster.FensterControl");
        java.lang.reflect.Field f = cls.getDeclaredField("PREISE");
        f.setAccessible(true); // <— wichtig!
        @SuppressWarnings("unchecked")
        java.util.Map<Integer,Integer> preise =
                (java.util.Map<Integer,Integer>) f.get(null); // static field

        int actual = java.util.Arrays.stream(ids)
                .map(id -> preise.getOrDefault(id, 0))
                .sum();

        // 3) Vergleich
        org.junit.jupiter.api.Assertions.assertEquals(
                expected, actual,
                () -> "Summe weicht ab. IDs=" + java.util.Arrays.toString(ids)
        );
    }



    private int summePreiseAusDb(int[] ids) throws Exception {
        String placeholders = String.join(",", Arrays.stream(ids)
            .mapToObj(i -> "?").toArray(String[]::new));

        String sql = "SELECT COALESCE(SUM(Preis),0) FROM Sonderwunsch " +
                     "WHERE idSonderwunsch IN (" + placeholders + ")";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int p = 1;
            for (int id : ids) ps.setInt(p++, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
}
