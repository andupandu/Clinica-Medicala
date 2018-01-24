package pkg;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> specializari = new ArrayList<String>();
		String query="SELECT * FROM Specialitate";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
						rs=stmt.executeQuery();

				while (rs.next()) {
					String denumire = rs.getString("Specialitate_denumire");
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
	
	public static Persoana getNumePrenumePacient(String email) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Persoana persoanaLogata=new Persoana();
		String query="SELECT * FROM Pacient where pacient_email= ?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, email);
						rs=stmt.executeQuery();

				while (rs.next()) {
					String nume = rs.getString("pacient_nume");
					String prenume=rs.getString("pacient_prenume");
					persoanaLogata.setNume(nume);
					persoanaLogata.setPrenume(prenume);
				
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return persoanaLogata;
	}
	public static Persoana getNumePrenumeMedic(String email) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Persoana persoanaLogata=new Persoana();
		String query="SELECT * FROM Medic where medic_email= ?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, email);
						rs=stmt.executeQuery();

				while (rs.next()) {
					String nume = rs.getString("medic_nume");
					String prenume=rs.getString("medic_prenume");
					persoanaLogata.setNume(nume);
					persoanaLogata.setPrenume(prenume);
				
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return persoanaLogata;
	}
	public static String isAccountInDB(String email,String password) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="SELECT * FROM cont WHERE cont_email= ? AND cont_parola= ?";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,email);
				stmt.setString(2,password);
				rs=stmt.executeQuery();
				if(rs.next()) 
					if(rs.getString("cont_pacient_cod")!=null)
						return "pacient";
				if(rs.getString("cont_medic_cod")!=null)
					return "medic";
				if(rs.getString("cont_pacient_cod")==null && rs.getString("cont_medic_cod")==null)
					return "admin";
					
					
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return null;
	}
public static void CloseResources(Connection conn,ResultSet rs,PreparedStatement stm) {
	try {
		if (rs != null)
			rs.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try {
		if (stm != null)
			 stm.close();
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
