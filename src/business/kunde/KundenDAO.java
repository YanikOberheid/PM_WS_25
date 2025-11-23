package business.kunde;

import java.sql.SQLException;

/*
 * Damit entprchende Funktionalitäten auch implementiert werden
 */

public interface KundenDAO {
	public int add(Kunde kunde)
	        throws SQLException;
	
	public boolean istHausnummerBesetzt(int hausnummer)
			throws SQLException;
	
	public Kunde findByHausnummer(int hausnummer)
			throws SQLException;
	
	public Kunde findByKundennummer(int idKunde)
			throws SQLException;
	
	public boolean deleteKunde(int kundennummer)
			throws SQLException;
	
	public void updateKunde(Kunde kunde)
			throws SQLException;
}