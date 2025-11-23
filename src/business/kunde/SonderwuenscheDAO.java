package business.kunde;

import java.sql.SQLException;

public interface SonderwuenscheDAO {
    public int[] get(int hausnummer) throws SQLException;

    public int[] get(int hausnummer, int kategorieId) throws SQLException;

    public void update(int hausnummer, int[] ausgewaehlteSw)
            throws SQLException, Exception;
}
