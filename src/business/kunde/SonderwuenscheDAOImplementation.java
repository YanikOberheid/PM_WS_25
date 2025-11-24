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
		String sql = "SELECT \"Sonderwunsch_idSonderwunsch\" FROM \"Sonderwunsch_has_Haus\" WHERE \"Haus_Hausnr\" = ?;";
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
		String sql = "SELECT \"Sonderwunsch_idSonderwunsch\" "
		           + "FROM \"Sonderwunsch_has_Haus\" swh "
		           + "INNER JOIN Sonderwunsch sw "
		           + "ON swh.\"Sonderwunsch_idSonderwunsch\" = sw.\"idSonderwunsch\" "
		           + "WHERE swh.\"Haus_Hausnr\" = ? "
		           + "AND sw.\"Sonderwunschkategorie_idSonderwunschkategorie\" = ?;";

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
		// UPDATE ist nur für existierende Tupel -> DELETE, gefolgt von INSERT nötig
		String sql_del = "DELETE FROM \"Sonderwunsch_has_Haus\" "
	               + "WHERE \"Haus_Hausnr\" = ?";

		String sql_ins = "INSERT INTO \"Sonderwunsch_has_Haus\" "
	               + "(\"Sonderwunsch_idSonderwunsch\", \"Haus_Hausnr\") "
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
			// INSERT
			pstmt = con.prepareStatement(sql_ins);
			for (int id: ausgewaehlteSw) {
				pstmt.setInt(1, id);
				pstmt.setInt(2, hausnummer);
				pstmt.execute();
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

	@Override
	public void delete(int hausnummer) throws SQLException {
		String sql_del = "DELETE FROM `Sonderwunsch_has_Haus` WHERE `Haus_Hausnr` = ?";

		try (PreparedStatement pstmt = con.prepareStatement(sql_del)){
			// DELETE
			con.setAutoCommit(false);
			pstmt.setInt(1, hausnummer);
			pstmt.executeUpdate();
			
			// commit
			con.commit();
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
		} finally {
			try { 
				con.setAutoCommit(false);
			} catch (SQLException e) {
				System.out.println("Fehler beim zurücksetzen von Auto Commit!");
			}
		}
		
	}
}
