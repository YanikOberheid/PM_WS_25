package business.kunde;

import java.sql.*;

/**
 * Data Access Object (DAO) für Kunden.
 * Beinhaltet alle Datenbankzugriffe für Kundenobjekte.
 */
public class KundeDaoImplementation implements KundenDAO{
	
	static Connection con = DatabaseConnection.getInstance().getConnection();

	@Override
	public int add(Kunde kunde) throws SQLException {
		System.out.println("Die Kunde hinzufügen Funktionalität muss noch implementiert werden!");
		return 0;
	}
}
