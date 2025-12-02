package business.kunde;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class SonderwuenscheDAOImplementation implements SonderwuenscheDAO {
	
	static Connection con = DatabaseConnection.getInstance().getConnection();
	
	@Override
	public int[] get(int hausnummer) throws SQLException {
		// [FIX] Removed quotes
		String sql = "SELECT Sonderwunsch_idSonderwunsch FROM Sonderwunsch_has_Haus WHERE Haus_Hausnr = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setFetchSize(100);
			pstmt.setInt(1, hausnummer);
			ResultSet result = pstmt.executeQuery();
			
			ArrayList<Integer> ausgewaehlteSw = new ArrayList<Integer>();
			while (result.next()) {
				ausgewaehlteSw.add(result.getInt(1));
			}
			
			int[] arr = new int[ausgewaehlteSw.size()];
			for (int i = 0; i < arr.length; i++) arr[i] = (int) ausgewaehlteSw.get(i);
			return arr;
		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Override
	public int[] get(int hausnummer, int kategorieId) throws SQLException {
		// [FIX] Removed quotes and added spaces to ends of lines
				String sql = "SELECT swh.Sonderwunsch_idSonderwunsch "
						+ "FROM Sonderwunsch_has_Haus swh "
						+ "INNER JOIN Sonderwunsch sw " 
						+ "ON swh.Sonderwunsch_idSonderwunsch = sw.idSonderwunsch "
						+ "WHERE swh.Haus_Hausnr = ? "
						+ "AND sw.Sonderwunschkategorie_idSonderwunschkategorie = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setFetchSize(100);
			pstmt.setInt(1, hausnummer);
			pstmt.setInt(2, kategorieId);
			ResultSet result = pstmt.executeQuery();
			
			ArrayList<Integer> ausgewaehlteSw = new ArrayList<Integer>();
			while (result.next()) {
				ausgewaehlteSw.add(result.getInt(1));
			}
			
			int[] arr = new int[ausgewaehlteSw.size()];
			for (int i = 0; i < arr.length; i++) arr[i] = (int) ausgewaehlteSw.get(i);
			return arr;
		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}
	
	@Override
	public int[] getExcluding(int hausnummer, int kategorieId) throws SQLException {
		// [FIX] Removed quotes and added spaces to ends of lines
				String sql = "SELECT swh.Sonderwunsch_idSonderwunsch "
						+ "FROM Sonderwunsch_has_Haus swh "
						+ "INNER JOIN Sonderwunsch sw " 
						+ "ON swh.Sonderwunsch_idSonderwunsch = sw.idSonderwunsch "
						+ "WHERE swh.Haus_Hausnr = ? "
						+ "AND sw.Sonderwunschkategorie_idSonderwunschkategorie != ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setFetchSize(100);
			pstmt.setInt(1, hausnummer);
			pstmt.setInt(2, kategorieId);
			ResultSet result = pstmt.executeQuery();
			
			ArrayList<Integer> ausgewaehlteSw = new ArrayList<Integer>();
			while (result.next()) {
				ausgewaehlteSw.add(result.getInt(1));
			}
			
			int[] arr = new int[ausgewaehlteSw.size()];
			for (int i = 0; i < arr.length; i++) arr[i] = (int) ausgewaehlteSw.get(i);
			return arr;
		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Override
	public void update(int hausnummer, int[] ausgewaehlteSw) throws SQLException, Exception {
		/* UPDATE ist nur für existierende Tupel -> DELETE, gefolgt von INSERT nötig
		 * Alle Eintraege mit der Hausnummer löschen/entfernen, also alle Zeilen mit der uebergebenden Hausnummer
		*/ 
		String sql_del = "DELETE FROM Sonderwunsch_has_Haus "
						+ "WHERE Haus_Hausnr = ?";
		
		// Insert Sql Statement vorbereiten
		// Gedacht für mehrmalige Nutzung - Zeile 137 Bis 142 - For Schleife
		String sql_ins = "INSERT INTO Sonderwunsch_has_Haus "
						+ "(Sonderwunsch_idSonderwunsch, Haus_Hausnr) "
						+ "VALUES (?, ?)";
		
		try {
			/* 
			 * Hinweis:
			 * Connection-Objekte des JDBC sind standardmäßig im auto-commit Modus,
			 * was hier verhindert, dass DELETE und INSERT nacheinander in einer
			 * Transaktion durchgeführt werden können. Daher wird der Modus hier kurz-
			 * zeitig verlassen.
			 * Dies könnte an anderen Stellen zu Problemen führen, wenn zeitleich Trans-
			 * aktionen gestartet werden, da sich bspw. in KundeDaoImplementation auf
			 * auto-commit verlassen wird und commit() und dort rollback() nicht manuell
			 * aufgerufen werden.
			 * Für mehr, siehe Java Doc zu commit() und rollback().
			 */
			if (!con.getAutoCommit())
				throw new Exception("Der Auto-Commit Modus sollte zu dem Zeitpunkt eines Updates"
						+ "der Sonderwünsche eingeschaltet sein, war er aber nicht. Möglicherweise"
						+ "wurde SonderwuenscheDAOImplementation.update() zu schnell"
						+ "hintereinander aufgerufen oder der Modus an anderer Stelle deaktiviert"
						+ "und nicht anschließend reaktiviert.");
			con.setAutoCommit(false);
			// DELETE
			PreparedStatement pstmt = con.prepareStatement(sql_del);
			pstmt.setInt(1, hausnummer);
			pstmt.execute();
			pstmt = con.prepareStatement(sql_ins);
			// Mehrmaliges ausfuehren des INSERT SQL-Statement
			// fuer die verschiedenen Ausgewaehlten Sonderwuensche
			/*for (int id: ausgewaehlteSw) {
				System.out.println("Folgende ID: " + Integer.toString(id));
				pstmt.setInt(1, id);
				pstmt.setInt(2, hausnummer);
				pstmt.execute();
			*/
			for (int id : ausgewaehlteSw) {
    			PreparedStatement checkStmt = con.prepareStatement(
        		"SELECT COUNT(*) FROM Sonderwunsch WHERE idSonderwunsch = ?"
    			);
    			checkStmt.setInt(1, id);
    			ResultSet rs = checkStmt.executeQuery();
    			rs.next();
    			int count = rs.getInt(1);
    			rs.close();
    			checkStmt.close();

    			if (count > 0) { 
        			pstmt.setInt(1, id);        
        			pstmt.setInt(2, hausnummer); 
        			pstmt.executeUpdate();
        			System.out.println("Speichere SW-ID: " + id);
    			} else {
        			System.out.println("SW-ID " + id + " existiert nicht, Insert übersprungen.");
    			} 
			}
			// commit
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException exc) {
			try {
				con.rollback();
			} catch (SQLException exc2) {
				System.out.println("Fehler beim Rollback während der Aktualisierung von Sonderwünschen");
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException exc2) {
				System.out.println("Fehler beim Setzen der DB-Verbindung in auto-commit während der Aktualisierung von Sonderwünschen");
			}
			exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			exc.printStackTrace();
			throw exc;
		}
	}
	// Delete benoetigt für wenn der Kunde selbst gelöscht wird
	@Override
	public void delete(int hausnummer) throws SQLException {

		String sql_del = "DELETE FROM `Sonderwunsch_has_Haus` WHERE `Haus_Hausnr` = ?";

		try (PreparedStatement pstmt = con.prepareStatement(sql_del)) {

			con.setAutoCommit(false);

			pstmt.setInt(1, hausnummer);
			pstmt.executeUpdate();

			con.commit();

		} catch (SQLException exc) {

			try {
				con.rollback();
			} catch (SQLException ignored) {
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException ignored) {
			}

			exc.printStackTrace();
			throw exc;

		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException ignored) {
			}
		}
	}
}
