package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SonderwunschHeizungExportTest {

    
    @Test
    public void testHeizkoerperAuswahlExport() {
       
        final int STD_HEIZKOERPER_ID = 201;
        final int GLATT_HEIZKOERPER_ID = 202;
        final int HANDTUCH_ID = 203;
        final int FBH_OHNE_DG_ID = 204;
        final int FBH_MIT_DG_ID = 205;

        int[][] result = new int[][] {
            {STD_HEIZKOERPER_ID, 3},
            {GLATT_HEIZKOERPER_ID, 1},
            {HANDTUCH_ID, 2},
            {FBH_OHNE_DG_ID, 1},
            {FBH_MIT_DG_ID, 0} 
        };

        
        assertEquals(5, result.length);

        assertArrayEquals(new int[] {STD_HEIZKOERPER_ID, 3}, result[0]);
        assertArrayEquals(new int[] {GLATT_HEIZKOERPER_ID, 1}, result[1]);
        assertArrayEquals(new int[] {HANDTUCH_ID, 2}, result[2]);
        assertArrayEquals(new int[] {FBH_OHNE_DG_ID, 1}, result[3]);
        assertArrayEquals(new int[] {FBH_MIT_DG_ID, 0}, result[4]);
    }
}
