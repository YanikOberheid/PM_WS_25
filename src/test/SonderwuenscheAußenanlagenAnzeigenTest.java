package test;

import org.junit.jupiter.api.Test;

import business.kunde.KundeModel;
import business.kunde.SwKategorie;
import static org.mockito.Mockito.*;

import gui.aussenanlagen.AussenanlagenControl;
import gui.aussenanlagen.AussenanlagenView;

public class SonderwuenscheAußenanlagenAnzeigenTest {
    @Test
    void testLeseAussenanlageSonderwuensche_ruftViewUpdateAuf() {

        // Mock-Objekte
        KundeModel model = mock(KundeModel.class);
        AussenanlagenView view = mock(AussenanlagenView.class);

        AussenanlagenControl control = mock(AussenanlagenControl.class);

        // MOCK-DATEN
        int[] mockSw = {90, 91, 92};

        when(model.gibAusgewaehlteSwAusDb(SwKategorie.AUSSENANLAGEN.id))
                .thenReturn(mockSw);

        control.leseAussenanlagenSonderwuensche();

        // View muss aufgerufen werden
        verify(view).updateSwCheckboxen(mockSw);
    }


    @Test
    void testLeseAussenanlageSonderwuensche_ruftViewNichtAufWennNull() {

        KundeModel model = mock(KundeModel.class);
        AussenanlagenView view = mock(AussenanlagenView.class);

        AussenanlagenControl control = mock(AussenanlagenControl.class);

        // Model liefert NULL
        when(model.gibAusgewaehlteSwAusDb(SwKategorie.AUSSENANLAGEN.id))
                .thenReturn(null);

        control.leseAussenanlagenSonderwuensche();

        // View darf NICHT aufgerufen werden
        verify(view, never()).updateSwCheckboxen(any());
    }


@Test
void testSpeichereSonderwuensche_speichertWennKonstellationOK() throws Exception {

     KundeModel model = mock(KundeModel.class);

    AussenanlagenControl control = mock(AussenanlagenControl.class);

    int[] mockSw = {90, 91, 92};

    // Konstellation OK
    doReturn(true).when(control).pruefeKonstellationAussenanlagen(mockSw);

    // --- Testaufruf ---
    control.speichereSonderwuensche(mockSw);

    // Speicherung muss passiert sein
    verify(model).speichereSonderwuenscheFuerKategorie(mockSw, SwKategorie.AUSSENANLAGEN.id);
}


    @Test
    void testSpeichereSonderwuensche_speichertNichtWennKonstellationFehler() throws Exception {

        KundeModel model = mock(KundeModel.class);
        AussenanlagenView view = mock(AussenanlagenView.class);

        AussenanlagenControl control = mock(AussenanlagenControl.class);

        int[] mockSw = {9, 8};
        // Diese Zeilen sollte angepasst werden, wenn die Methode pruefeKonstellationAussenanlagen erweitert wird

        //Konstellation NICHT OK
        doReturn(true).when(control).pruefeKonstellationAussenanlagen(mockSw);

        // --- Test ---
        control.speichereSonderwuensche(mockSw);

        // Speicherung darf NICHT erfolgen
        verify(model, never()).speichereSonderwuenscheFuerKategorie(any(), anyInt());
    }
    
}
