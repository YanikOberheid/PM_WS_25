package src.business.kunde;

import java.sql.SQLException;

/*
 * Damit entprchende Funktionalitäten auch implementiert werden
 */

public interface KundenDAO {
	public int add(Kunde kunde)
	        throws SQLException;
	
	public boolean istHausnummerBesetzt(int hausnummer)
			throws SQLException;
}