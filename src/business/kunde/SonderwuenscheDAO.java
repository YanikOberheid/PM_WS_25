package business.kunde;

import java.sql.SQLException;
import java.lang.Exception;

public interface SonderwuenscheDAO {
<<<<<<< HEAD
	public int[] get(int hausnummer)
			throws SQLException;
	
	public int[] get(int hausnummer, int kategorieId)
			throws SQLException;
	
	public void update(int hausnummer, int[] ausgewaehlteSw)
	        throws SQLException, Exception;
	
	public void delete(int hausnummer)
			throws SQLException;
=======
    public int[] get(int hausnummer) throws SQLException;

    public int[] get(int hausnummer, int kategorieId) throws SQLException;

    public void update(int hausnummer, int[] ausgewaehlteSw)
            throws SQLException, Exception;
>>>>>>> refs/remotes/origin/dev
}
