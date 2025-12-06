package business.kunde;

import java.sql.SQLException;

public interface SonderwuenscheDAO {
	public int[] get(int hausnummer)
			throws SQLException;
	
	public int[] get(int hausnummer, int kategorieId)
			throws SQLException;
	
	/**
	 * Holt alle Sonderwunsch-IDs, die der angegeben Kategorie NICHT angehören.
	 * 
	 * @param hausnummer, zu der Sonderwünsche geholt werden sollen
	 * @param kategorieId, zu der KEINE IDs geholt werden sollen
	 * @return int[] mit Sonderwunsch-IDs ohne die angegeben Kategorie
	 */
	public int[] getExcluding(int hausnummer, int kategorieId)
			throws SQLException;
	
	public int[][] getMitAnzahl(int hausnummer)
			throws SQLException;
	
	public int[][] getMitAnzahl(int hausnummer, int kategorieId)
			throws SQLException;
	
	 /** Holt alle Sonderwunsch-IDs und Anzahl, die der angegeben Kategorie NICHT angehören.
	 * 
	 * @param hausnummer, zu der Sonderwünsche geholt werden sollen
	 * @param kategorieId, zu der KEINE IDs geholt werden sollen
	 * @return int[][] Sonderwunsch-IDs (mit Anzahl) ohne die angegeben Kategorie
	 */
	public int[][] getMitAnzahlExcluding(int hausnummer, int kategorieId)
			throws SQLException;
	
	/**
	 * Löscht alle vorhandenen Sonderwünsche zu einem Haus und fügt die übergebenen wieder ein.
	 * Falls es Sonderwünsche mit Anzahl gibt, müssen diese als zusätzlicher Parameter angegeben
	 * werden. Diese muss ein int[][] sein, wobei Sonderwunsch-ID und Anzahl nach folgendem Schema
	 * angegeben werden sollen:
	 * 		{{id_1, anz_1}, {id_2, anz_2}, ...}
	 * 
	 * @param hausnummer ist diejenige Hausnummer, die die angegebnen Sonderwünsche betreffen   
	 * @param ausgewaehlteSw ist ein int[] an allen Sonderwunsch-IDs, die zu einem Haus ausgesucht wurden
	 * @throws SQLException
	 * @throws Exception
	 */
	public void update(int hausnummer, int[] ausgewaehlteSw)
	        throws SQLException, Exception;
	
	/**
	 * Löscht alle vorhandenen Sonderwünsche zu einem Haus und fügt die übergebenen wieder ein.
	 * Falls es Sonderwünsche mit Anzahl gibt, müssen diese als zusätzlicher Parameter angegeben
	 * werden. Diese muss ein int[][] sein, wobei Sonderwunsch-ID und Anzahl nach folgendem Schema
	 * angegeben werden sollen:
	 * 		{{id_1, anz_1}, {id_2, anz_2}, ...}
	 * 
	 * @param hausnummer ist diejenige Hausnummer, die die angegebnen Sonderwünsche betreffen   
	 * @param ausgewaehlteSw ist ein int[] an allen Sonderwunsch-IDs, die zu einem Haus ausgesucht wurden
	 * @throws SQLException
	 * @throws Exception
	 */
	public void update(int hausnummer, int[] ausgewaehlteSw, int[][] ausgewaehlteSwMitAnzahl)
			throws SQLException, Exception;
	
	// Delete benoetigt wenn der Kunde selbst gelöscht wird
	public void delete(int hausnummer)
			throws SQLException;
}
