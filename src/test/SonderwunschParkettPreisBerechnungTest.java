package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.kunde.SonderwuenscheDAOImplementation;
import business.kunde.Sw;
import gui.aussenanlagen.AussenanlagenControl;
import gui.basis.BasisView;
import gui.parkett.ParkettView;

public class SonderwunschParkettPreisBerechnungTest {

   private ParkettView parkettViewMock;


    // Mock
    private AussenanlagenControl aussenMock;

    boolean LHD_M_ESS_EG;			
    boolean LHD_M_KUECHE_EG;	
    boolean SP_ESS_EG;				
    boolean SP_KUECHE_EG;		
    boolean LHD_M_OG;			
    boolean SP_OG;				
    boolean LHD_M_KOMPLETT_DG;	
    boolean LDH_M_OHNE_BAD_DG;	
    boolean SP_KOMPLETT_DG;	
    boolean SP_OHNE_BAD_DG;

    @BeforeEach
    void setUp() {
        parkettViewMock = mock(ParkettView.class);
        aussenMock = mock(AussenanlagenControl.class);
        when(aussenMock.pruefeKonstellationAussenanlagen(any())).thenReturn(true);

    }

    @Test
    void testBerechnungSonderwuenschezuParkett() {
        // ------------------------------
        // Vorbereitung: Checkboxen setzen
        // ------------------------------
         LHD_M_ESS_EG = true;			
         LHD_M_KUECHE_EG	= true;	
         SP_ESS_EG = true;				
         SP_KUECHE_EG = true;		
         LHD_M_OG = true;			 


        // Vordefinierter Preis
        double gesamtpreis = 8860.0;
		assertEquals(berechneUndZeigePreisSonderwuensche(), gesamtpreis);
    }

    @Test
    void testKeineCheckboxAusgewaehlt() {
        double gesamtpreis = 0.0;
		assertEquals(berechneUndZeigePreisSonderwuensche(), gesamtpreis);
    }
    

    public double berechneUndZeigePreisSonderwuensche(){
  		double preis = 0.0;
  		
  		if (LHD_M_ESS_EG)	preis += Sw.ABSTELL_TERRASSE_EG.preis;
    	if (LHD_M_KUECHE_EG)		preis += Sw.VEA_MARKISE_EG.preis;
    	if (SP_ESS_EG) 		preis += Sw.VEA_MARKISE_DG.preis;
    	if (SP_KUECHE_EG)			preis += Sw.E_MARKISE_EG.preis;
    	if (LHD_M_OG)			preis += Sw.E_MARKISE_DG.preis;
    	if (SP_OG)		preis += Sw.EA_GARAGENTOR.preis;
    	if (LHD_M_KOMPLETT_DG)		preis += Sw.ST_GARAGENTOR.preis;
        if (LDH_M_OHNE_BAD_DG)		preis += Sw.EA_GARAGENTOR.preis;
    	if (SP_KOMPLETT_DG)		preis += Sw.ST_GARAGENTOR.preis;
        if (SP_OHNE_BAD_DG)		preis += Sw.EA_GARAGENTOR.preis;

  		return preis;
  	}
}
