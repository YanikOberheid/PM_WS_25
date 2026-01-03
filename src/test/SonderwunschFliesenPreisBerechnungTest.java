package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

// Dummy-Implementierung für Sw und SwKategorie (ersetzt falls im Projekt vorhanden)
enum SwKategorie {
    FLIESEN(1);

    public final int id;

    SwKategorie(int id) {
        this.id = id;
    }
}

enum Sw {
    BODENFLIESEN_PREMIUM(100.0),
    WANDFLIESEN_GROSSFORMAT(150.0),
    MOSAIK_DUSCHE(80.0),
    SOCKELFLIESEN(50.0);

    public final double preis;

    Sw(double preis) {
        this.preis = preis;
    }
}

public class SonderwunschFliesenPreisBerechnungTest {

    // Kategorie
    private final int FLIESEN_KATEGORIE_ID = SwKategorie.FLIESEN.id;

    // Checkbox-Zustände (Beispiele!)
    boolean state_BODENFLIESEN_PREMIUM;
    boolean state_WANDFLIESEN_GROSSFORMAT;
    boolean state_MOSAIK_DUSCHE;
    boolean state_SOCKELFLIESEN;

    @Before
    public void setUp() {
        resetBooleanValues();
    }

    private void resetBooleanValues() {
        state_BODENFLIESEN_PREMIUM = false;
        state_WANDFLIESEN_GROSSFORMAT = false;
        state_MOSAIK_DUSCHE = false;
        state_SOCKELFLIESEN = false;
    }

    @Test
    public void testAlleFliesenAusgewaehltPreisBerechnung() {
        state_BODENFLIESEN_PREMIUM = true;
        state_WANDFLIESEN_GROSSFORMAT = true;
        state_MOSAIK_DUSCHE = true;
        state_SOCKELFLIESEN = true;

        double erwarteterPreis = Sw.BODENFLIESEN_PREMIUM.preis + Sw.WANDFLIESEN_GROSSFORMAT.preis
                + Sw.MOSAIK_DUSCHE.preis + Sw.SOCKELFLIESEN.preis;

        assertEquals(erwarteterPreis, berechnePreisFliesen(), 0.0001);
    }

    @Test
    public void testZweiFliesenAusgewaehltPreisBerechnung() {
        state_BODENFLIESEN_PREMIUM = true;
        state_MOSAIK_DUSCHE = true;

        double erwarteterPreis = Sw.BODENFLIESEN_PREMIUM.preis + Sw.MOSAIK_DUSCHE.preis;

        assertEquals(erwarteterPreis, berechnePreisFliesen(), 0.0001);
    }

    @Test
    public void testEineFlieseAusgewaehltPreisBerechnung() {
        state_WANDFLIESEN_GROSSFORMAT = true;

        double erwarteterPreis = Sw.WANDFLIESEN_GROSSFORMAT.preis;

        assertEquals(erwarteterPreis, berechnePreisFliesen(), 0.0001);
    }

    // Preislogik (das eigentliche Testziel)
    private double berechnePreisFliesen() {
        double preis = 0.0;

        if (state_BODENFLIESEN_PREMIUM)
            preis += Sw.BODENFLIESEN_PREMIUM.preis;
        if (state_WANDFLIESEN_GROSSFORMAT)
            preis += Sw.WANDFLIESEN_GROSSFORMAT.preis;
        if (state_MOSAIK_DUSCHE)
            preis += Sw.MOSAIK_DUSCHE.preis;
        if (state_SOCKELFLIESEN)
            preis += Sw.SOCKELFLIESEN.preis;

        return preis;
    }
}
