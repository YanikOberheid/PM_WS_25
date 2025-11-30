package business.kunde;

import java.sql.SQLException;

public interface SonderwuenscheDAO {
	public int[] get(int hausnummer)
			throws SQLException;
	
	public int[] get(int hausnummer, int kategorieId)
			throws SQLException;
	
	public void update(int hausnummer, int[] ausgewaehlteSw)
	        throws SQLException, Exception;
	// Delete benoetigt wenn der Kunde selbst gelöscht wird
	public void delete(int hausnummer)
			throws SQLException;
}