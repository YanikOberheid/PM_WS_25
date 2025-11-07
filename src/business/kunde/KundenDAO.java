package business.kunde;

import java.sql.SQLException;

/*
 * Damit entprchende Funktionalitäten auch implementiert werden
 */

public interface KundenDAO {
	public int add(Kunde kunde)
	        throws SQLException;
	// Weitere Funktionen entprechend hinzufügen
	// Beispiel: GetInfo, Löschen usw. von Kunden
	// usw.
}
