package pkg;

import java.util.ArrayList;
import java.util.List;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbOperations {
	public static Connection getConnection() {
		String connectionUrl = "jdbc:mysql://localhost:3306/clinicamedicala";
		String connectionUser = "root";
		String connectionPassword = "admin";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			return conn;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		return null;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static List<String> getSpecializari() {
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<String> specializari = new ArrayList<String>();
		try {

			if (conn != null) {
				rs=conn.createStatement().executeQuery("SELECT * FROM Specializare");

				while (rs.next()) {
					String denumire = rs.getString("Specializare_denumire");
					specializari.add(denumire);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return specializari;
	}
	
public static void CloseResources(Connection conn,ResultSet rs,Statement stm) {
	try {
		if (rs != null)
			rs.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try {
		if (stm != null)
			 ((Connection) stm).close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try {
		if (conn != null)
			conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
}
