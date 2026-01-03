package business.kunde;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class SonderwuenscheDAOImplementation implements SonderwuenscheDAO {

	static Connection con = DatabaseConnection.getInstance().getConnection();

	@Deprecated
	@Override
	public int[] get(int hausnummer) throws SQLException {
		String sql = "SELECT Sonderwunsch_idSonderwunsch FROM Sonderwunsch_has_Haus WHERE Haus_Hausnr = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setFetchSize(100);
			pstmt.setInt(1, hausnummer);
			ResultSet result = pstmt.executeQuery();

			ArrayList<Integer> ausgewaehlteSw = new ArrayList<Integer>();
			while (result.next()) {
				ausgewaehlteSw.add(result.getInt(1));
			}
			result.close();
			pstmt.close();

			int[] arr = new int[ausgewaehlteSw.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = (int) ausgewaehlteSw.get(i);
			return arr;
		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Deprecated
	@Override
	public int[] get(int hausnummer, int kategorieId) throws SQLException {
		String sql = "SELECT swh.Sonderwunsch_idSonderwunsch " + "FROM Sonderwunsch_has_Haus swh "
				+ "INNER JOIN Sonderwunsch sw " + "ON swh.Sonderwunsch_idSonderwunsch = sw.idSonderwunsch "
				+ "WHERE swh.Haus_Hausnr = ? " + "AND sw.Sonderwunschkategorie_idSonderwunschkategorie = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setFetchSize(100);
			pstmt.setInt(1, hausnummer);
			pstmt.setInt(2, kategorieId);
			ResultSet result = pstmt.executeQuery();

			ArrayList<Integer> ausgewaehlteSw = new ArrayList<Integer>();
			while (result.next()) {
				ausgewaehlteSw.add(result.getInt(1));
			}
			result.close();
			pstmt.close();

			int[] arr = new int[ausgewaehlteSw.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = (int) ausgewaehlteSw.get(i);
			return arr;
		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Deprecated
	@Override
	public int[] getExcluding(int hausnummer, int kategorieId) throws SQLException {
		String sql = "SELECT swh.Sonderwunsch_idSonderwunsch " + "FROM Sonderwunsch_has_Haus swh "
				+ "INNER JOIN Sonderwunsch sw " + "ON swh.Sonderwunsch_idSonderwunsch = sw.idSonderwunsch "
				+ "WHERE swh.Haus_Hausnr = ? " + "AND sw.Sonderwunschkategorie_idSonderwunschkategorie != ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setFetchSize(100);
			pstmt.setInt(1, hausnummer);
			pstmt.setInt(2, kategorieId);
			ResultSet result = pstmt.executeQuery();

			ArrayList<Integer> ausgewaehlteSw = new ArrayList<Integer>();
			while (result.next()) {
				ausgewaehlteSw.add(result.getInt(1));
			}
			result.close();
			pstmt.close();

			int[] arr = new int[ausgewaehlteSw.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = (int) ausgewaehlteSw.get(i);
			return arr;
		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Override
	public int[][] getMitAnzahl(int hausnummer) throws SQLException {
		String sql = "SELECT Sonderwunsch_idSonderwunsch, Sonderwunsch_Anzahl "
				+ "FROM Sonderwunsch_has_Haus WHERE Haus_Hausnr = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setFetchSize(100);
			pstmt.setInt(1, hausnummer);
			ResultSet result = pstmt.executeQuery();

			LinkedList<Object> ausgewaehlteSw = new LinkedList<Object>();
			while (result.next()) {
				ausgewaehlteSw.add(new int[] { result.getInt(1), result.getInt(2) });
			}
			result.close();
			pstmt.close();

			int[][] arr = new int[ausgewaehlteSw.size()][2];
			for (int i = 0; i < arr.length; i++)
				arr[i] = (int[]) ausgewaehlteSw.get(i);
			return arr;
		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Override
	public int[][] getMitAnzahl(int hausnummer, int kategorieId) throws SQLException {
		String sql = "SELECT swh.Sonderwunsch_idSonderwunsch, swh.Sonderwunsch_Anzahl "
				+ "FROM Sonderwunsch_has_Haus swh " + "INNER JOIN Sonderwunsch sw "
				+ "ON swh.Sonderwunsch_idSonderwunsch = sw.idSonderwunsch " + "WHERE swh.Haus_Hausnr = ? "
				+ "AND sw.Sonderwunschkategorie_idSonderwunschkategorie = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setFetchSize(100);
			pstmt.setInt(1, hausnummer);
			pstmt.setInt(2, kategorieId);
			ResultSet result = pstmt.executeQuery();

			LinkedList<Object> ausgewaehlteSw = new LinkedList<Object>();
			while (result.next()) {
				ausgewaehlteSw.add(new int[] { result.getInt(1), result.getInt(2) });
			}
			result.close();
			pstmt.close();

			int[][] arr = new int[ausgewaehlteSw.size()][2];
			for (int i = 0; i < arr.length; i++)
				arr[i] = (int[]) ausgewaehlteSw.get(i);
			return arr;
		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Override
	public int[][] getMitAnzahlExcluding(int hausnummer, int kategorieId) throws SQLException {
		String sql = "SELECT swh.Sonderwunsch_idSonderwunsch, swh.SOnderwunsch_Anzahl "
				+ "FROM Sonderwunsch_has_Haus swh " + "INNER JOIN Sonderwunsch sw "
				+ "ON swh.Sonderwunsch_idSonderwunsch = sw.idSonderwunsch " + "WHERE swh.Haus_Hausnr = ? "
				+ "AND sw.Sonderwunschkategorie_idSonderwunschkategorie != ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setFetchSize(100);
			pstmt.setInt(1, hausnummer);
			pstmt.setInt(2, kategorieId);
			ResultSet result = pstmt.executeQuery();

			LinkedList<Object> ausgewaehlteSw = new LinkedList<Object>();
			while (result.next()) {
				ausgewaehlteSw.add(new int[] { result.getInt(1), result.getInt(2) });
			}
			result.close();
			pstmt.close();

			int[][] arr = new int[ausgewaehlteSw.size()][2];
			for (int i = 0; i < arr.length; i++)
				arr[i] = (int[]) ausgewaehlteSw.get(i);
			return arr;
		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Deprecated
	@Override
	public void update(int hausnummer, int[] ausgewaehlteSw) throws SQLException, Exception {
		/*
		 * UPDATE ist nur für existierende Tupel -> DELETE, gefolgt von INSERT nötig
		 * Alle Eintraege mit der Hausnummer löschen/entfernen, also alle Zeilen mit der
		 * uebergebenden Hausnummer
		 */
		String sql_del = "DELETE FROM Sonderwunsch_has_Haus " + "WHERE Haus_Hausnr = ?";

		// Insert Sql Statement vorbereiten
		// Gedacht für mehrmalige Nutzung - Zeile 137 Bis 142 - For Schleife
		String sql_ins = "INSERT INTO Sonderwunsch_has_Haus " + "(Sonderwunsch_idSonderwunsch, Haus_Hausnr) "
				+ "VALUES (?, ?)";

		try {
			/*
			 * Hinweis: Connection-Objekte des JDBC sind standardmäßig im auto-commit Modus,
			 * was hier verhindert, dass DELETE und INSERT nacheinander in einer Transaktion
			 * durchgeführt werden können. Daher wird der Modus hier kurz- zeitig verlassen.
			 * Dies könnte an anderen Stellen zu Problemen führen, wenn zeitleich Trans-
			 * aktionen gestartet werden, da sich bspw. in KundeDaoImplementation auf
			 * auto-commit verlassen wird und commit() und dort rollback() nicht manuell
			 * aufgerufen werden. Für mehr, siehe Java Doc zu commit() und rollback().
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
			// SELECT (existierende IDs)
			PreparedStatement checkStmt = con.prepareStatement("SELECT idSonderwunsch FROM Sonderwunsch");
			ResultSet rs = checkStmt.executeQuery();
			ArrayList<Integer> validSw = new ArrayList<Integer>();
			while (rs.next()) {
				validSw.add(rs.getInt(1));
			}
			rs.close();
			checkStmt.close();
			// INSERT
			pstmt = con.prepareStatement(sql_ins);
			for (int id : ausgewaehlteSw) {
				if (validSw.contains(id)) {
					pstmt.setInt(1, id);
					pstmt.setInt(2, hausnummer);
					pstmt.executeUpdate();
					System.out.println("Speichere Sonderwunsch mit ID " + id);
				} else {
					System.out.println("Kein Sonderwunsch mit ID " + id + " gefunden. ID übersprungen.");
				}
			}
			// commit
			con.commit();
			con.setAutoCommit(true);
			pstmt.close();
		} catch (SQLException exc) {
			try {
				con.rollback();
			} catch (SQLException exc2) {
				System.out.println("Fehler beim Rollback während der Aktualisierung von Sonderwünschen");
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException exc2) {
				System.out.println(
						"Fehler beim Setzen der DB-Verbindung in auto-commit während der Aktualisierung von Sonderwünschen");
			}
			exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Deprecated
	public void update(int hausnummer, int[] ausgewaehlteSw, int[][] ausgewaehlteSwMitAnzahl)
			throws SQLException, Exception {
		/*
		 * UPDATE ist nur für existierende Tupel -> DELETE, gefolgt von INSERT nötig
		 * Alle Eintraege mit der Hausnummer löschen/entfernen, also alle Zeilen mit der
		 * uebergebenden Hausnummer
		 */
		String sql_del = "DELETE FROM Sonderwunsch_has_Haus " + "WHERE Haus_Hausnr = ?";

		// Insert Sql Statement vorbereiten
		// Gedacht für mehrmalige Nutzung - Zeile 137 Bis 142 - For Schleife
		String sql_ins = "INSERT INTO Sonderwunsch_has_Haus " + "(Sonderwunsch_idSonderwunsch, Haus_Hausnr) "
				+ "VALUES (?, ?)";

		String sql_ins_c = "INSERT INTO Sonderwunsch_has_Haus "
				+ "(Sonderwunsch_idSonderwunsch, Haus_Hausnr, Sonderwunsch_Anzahl) " + "VALUES (?, ?, ?)";

		try {
			/*
			 * Hinweis: Connection-Objekte des JDBC sind standardmäßig im auto-commit Modus,
			 * was hier verhindert, dass DELETE und INSERT nacheinander in einer Transaktion
			 * durchgeführt werden können. Daher wird der Modus hier kurz- zeitig verlassen.
			 * Dies könnte an anderen Stellen zu Problemen führen, wenn zeitleich Trans-
			 * aktionen gestartet werden, da sich bspw. in KundeDaoImplementation auf
			 * auto-commit verlassen wird und commit() und dort rollback() nicht manuell
			 * aufgerufen werden. Für mehr, siehe Java Doc zu commit() und rollback().
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
			// SELECT (existierende IDs)
			PreparedStatement checkStmt = con.prepareStatement("SELECT idSonderwunsch FROM Sonderwunsch");
			ResultSet rs = checkStmt.executeQuery();
			ArrayList<Integer> validSw = new ArrayList<Integer>();
			while (rs.next()) {
				validSw.add(rs.getInt(1));
			}
			rs.close();
			checkStmt.close();
			// INSERT (ohne Anzahl)
			pstmt = con.prepareStatement(sql_ins);
			if (ausgewaehlteSw != null) {
				for (int id : ausgewaehlteSw) {
					if (validSw.contains(id)) {
						pstmt.setInt(1, id);
						pstmt.setInt(2, hausnummer);
						pstmt.executeUpdate();
						System.out.println("Speichere Sonderwunsch mit ID " + id);
					} else {
						System.out.println("Kein Sonderwunsch mit ID " + id + " gefunden. ID übersprungen.");
					}
				}
			}
			// INSERT (mit Anzahl)
			pstmt = con.prepareStatement(sql_ins_c);
			if (ausgewaehlteSwMitAnzahl != null) {
				for (int[] pair : ausgewaehlteSwMitAnzahl) {
					if (pair[1] > 0 && validSw.contains(pair[0])) {
						pstmt.setInt(1, hausnummer);
						pstmt.setInt(2, pair[0]);
						pstmt.setInt(3, pair[1]);
						pstmt.executeUpdate();
						System.out.println("Speichere Sonderwunsch mit ID " + pair[0] + " und Anzahl " + pair[1]);
					} else {
						System.out.println("Sonderwunsch-ID " + pair[0] + " oder Anzahl " + pair[1]
								+ " nicht erlaubt. ID übersprungen.");
					}
				}
			}
			// commit
			con.commit();
			con.setAutoCommit(true);
			pstmt.close();
		} catch (SQLException exc) {
			try {
				con.rollback();
			} catch (SQLException exc2) {
				System.out.println("Fehler beim Rollback während der Aktualisierung von Sonderwünschen");
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException exc2) {
				System.out.println(
						"Fehler beim Setzen der DB-Verbindung in auto-commit während der Aktualisierung von Sonderwünschen");
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
			pstmt.close();

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

	@Override
	public void update(int hausnummer, int[][] ausgewaehlteSwMitAnzahl) throws SQLException, Exception {
		/*
		 * UPDATE ist nur für existierende Tupel -> DELETE, gefolgt von INSERT nötig
		 * Alle Eintraege mit der Hausnummer löschen/entfernen, also alle Zeilen mit der
		 * uebergebenden Hausnummer
		 */
		String sql_del = "DELETE FROM Sonderwunsch_has_Haus " + "WHERE Haus_Hausnr = ?";

		String sql_ins_c = "INSERT INTO Sonderwunsch_has_Haus "
				+ "(Sonderwunsch_idSonderwunsch, Haus_Hausnr, Sonderwunsch_Anzahl) " + "VALUES (?, ?, ?)";

		try {
			/*
			 * Hinweis: Connection-Objekte des JDBC sind standardmäßig im auto-commit Modus,
			 * was hier verhindert, dass DELETE und INSERT nacheinander in einer Transaktion
			 * durchgeführt werden können. Daher wird der Modus hier kurz- zeitig verlassen.
			 * Dies könnte an anderen Stellen zu Problemen führen, wenn zeitleich Trans-
			 * aktionen gestartet werden, da sich bspw. in KundeDaoImplementation auf
			 * auto-commit verlassen wird und commit() und dort rollback() nicht manuell
			 * aufgerufen werden. Für mehr, siehe Java Doc zu commit() und rollback().
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
			// SELECT (existierende IDs)
			PreparedStatement checkStmt = con.prepareStatement("SELECT idSonderwunsch FROM Sonderwunsch");
			ResultSet rs = checkStmt.executeQuery();
			ArrayList<Integer> validSw = new ArrayList<Integer>();
			while (rs.next()) {
				validSw.add(rs.getInt(1));
			}
			rs.close();
			checkStmt.close();

			// INSERT (mit Anzahl)
			pstmt = con.prepareStatement(sql_ins_c);
			if (ausgewaehlteSwMitAnzahl != null) {
				for (int[] pair : ausgewaehlteSwMitAnzahl) {
					// Überprüfung der Anzahl Sonderwunsche (sollte größer 0 sein) und das die ID in
					// der Sonderwunsch Tabelle existiert
					if (pair[1] > 0 && validSw.contains(pair[0])) {
						pstmt.setInt(1, pair[0]);
						pstmt.setInt(2, hausnummer);
						pstmt.setInt(3, pair[1]);
						pstmt.executeUpdate();
						System.out.println("Speichere Sonderwunsch mit ID " + pair[0] + " und Anzahl " + pair[1]);
					} else {
						System.out.println("Sonderwunsch-ID " + pair[0] + " oder Anzahl " + pair[1]
								+ " nicht erlaubt. ID übersprungen.");
					}
				}
			}
			// commit
			con.commit();
			con.setAutoCommit(true);
			pstmt.close();
		} catch (SQLException exc) {
			try {
				con.rollback();
			} catch (SQLException exc2) {
				System.out.println("Fehler beim Rollback während der Aktualisierung von Sonderwünschen");
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException exc2) {
				System.out.println(
						"Fehler beim Setzen der DB-Verbindung in auto-commit während der Aktualisierung von Sonderwünschen");
			}
			exc.printStackTrace();
			throw exc;
		} catch (Exception exc) {
			exc.printStackTrace();
			throw exc;
		}

	}
}
