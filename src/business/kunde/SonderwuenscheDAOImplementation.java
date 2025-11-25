package business.kunde;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SonderwuenscheDAOImplementation implements SonderwuenscheDAO {

	static Connection con = DatabaseConnection.getInstance().getConnection();

	@Override
	public int[] get(int hausnummer) throws SQLException {
		String sql = "SELECT `Sonderwunsch_idSonderwunsch` " + "FROM `Sonderwunsch_has_Haus` "
				+ "WHERE `Haus_Hausnr` = ?";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, hausnummer);
			ResultSet result = pstmt.executeQuery();

			ArrayList<Integer> ausgewaehlteSw = new ArrayList<>();
			while (result.next()) {
				ausgewaehlteSw.add(result.getInt(1));
			}

			int[] arr = new int[ausgewaehlteSw.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = ausgewaehlteSw.get(i);

			return arr;

		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Override
	public int[] get(int hausnummer, int kategorieId) throws SQLException {

		String sql = "SELECT swh.`Sonderwunsch_idSonderwunsch` " + "FROM `Sonderwunsch_has_Haus` swh "
				+ "INNER JOIN `Sonderwunsch` sw " + "ON swh.`Sonderwunsch_idSonderwunsch` = sw.`idSonderwunsch` "
				+ "WHERE swh.`Haus_Hausnr` = ? " + "AND sw.`Sonderwunschkategorie_idSonderwunschkategorie` = ?";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, hausnummer);
			pstmt.setInt(2, kategorieId);
			ResultSet result = pstmt.executeQuery();

			ArrayList<Integer> ausgewaehlteSw = new ArrayList<>();
			while (result.next()) {
				ausgewaehlteSw.add(result.getInt(1));
			}

			int[] arr = new int[ausgewaehlteSw.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = ausgewaehlteSw.get(i);

			return arr;

		} catch (SQLException exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

	@Override
	public void update(int hausnummer, int[] ausgewaehlteSw) throws SQLException, Exception {

		String sql_del = "DELETE FROM `Sonderwunsch_has_Haus` WHERE `Haus_Hausnr` = ?";
		String sql_ins = "INSERT INTO `Sonderwunsch_has_Haus` (`Sonderwunsch_idSonderwunsch`, `Haus_Hausnr`) VALUES (?, ?)";

		try {
			if (!con.getAutoCommit())
				throw new Exception("AutoCommit muss für Update aktiv sein!");

			con.setAutoCommit(false);

			// DELETE
			PreparedStatement pstmt = con.prepareStatement(sql_del);
			pstmt.setInt(1, hausnummer);
			pstmt.execute();

			// INSERT
			pstmt = con.prepareStatement(sql_ins);
			for (int id : ausgewaehlteSw) {
				pstmt.setInt(1, id);
				pstmt.setInt(2, hausnummer);
				pstmt.execute();
			}

			con.commit();
			con.setAutoCommit(true);

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

		} catch (Exception exc) {
			exc.printStackTrace();
			throw exc;
		}
	}

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
