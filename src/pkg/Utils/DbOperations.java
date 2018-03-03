package pkg.Utils;
import pkg.Entities.Medic;
import pkg.Entities.Persoana;
import java.util.ArrayList;
import java.util.Arrays;
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

	public static ResultSet getQueryResults(String query, List<Object> params) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			if (conn != null) {		
				stmt=conn.prepareStatement(query);	
				if(params!=null)
					for(int i=0;i<params.size();i++) {
						Object var = params.get(i);
						if(var instanceof String ) {
							stmt.setString(i+1, (String) var);
						}
						else if(var instanceof Long ){
							stmt.setLong(i+1, (long)var);
						}
					}
					rs=stmt.executeQuery();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
		return rs;
	}
		
	public static void deleteMedic(String id) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="Delete FROM medic where medic_cod= ?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, Long.valueOf(id));
						stmt.executeUpdate();
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		
	}
	
	public static List<String> getSpecializari() throws SQLException {
		String query="SELECT * FROM Specialitate";
		ResultSet rs =getQueryResults(query, null);
		List<String> specializari = new ArrayList<String>();
		while (rs.next()) {
			String denumire = rs.getString("Specialitate_denumire");
			specializari.add(denumire);
		}
		
		return specializari;
	}
	
	public static String getSpecializari(long cod) throws SQLException {
		String query="SELECT * FROM Specialitate where specialitate_cod=?";
		ResultSet rs =getQueryResults(query, Arrays.asList(cod));
		String specializare=null;
		while (rs.next()) {
			specializare = rs.getString("Specialitate_denumire");									
		}
		return specializare==null?"-":specializare;
	}
	
	public static Persoana getPacient(String email) {
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
					int cod=rs.getInt("pacient_cod");
					String nume = rs.getString("pacient_nume");
					String prenume=rs.getString("pacient_prenume");
					persoanaLogata.setNume(nume);
					persoanaLogata.setPrenume(prenume);
					persoanaLogata.setId(Long.valueOf(cod));
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
	public static Persoana getMedic(String email) {
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
					int cod=rs.getInt("medic_cod");
					String nume = rs.getString("medic_nume");
					String prenume=rs.getString("medic_prenume");
					persoanaLogata.setNume(nume);
					persoanaLogata.setPrenume(prenume);
					persoanaLogata.setId(Long.valueOf(cod));
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
	
	public static List<Medic> getListaMedici() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Medic> medici=new ArrayList<Medic>();
		
		String query="SELECT * FROM Medic";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
						rs=stmt.executeQuery();

				while (rs.next()) {
					Medic medic=new Medic();
					medic.setId(rs.getLong("medic_cod"));
					medic.setNume(rs.getString("medic_nume"));
					medic.setPrenume(rs.getString("medic_prenume"));
					medic.setCodSpec(rs.getLong("specialitate_cod"));
					medici.add(medic);
					
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return medici;
	}
	public static String isAccountInDB(String email) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="SELECT * FROM cont WHERE cont_email= ?";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,email);
				
				rs=stmt.executeQuery();
				if(rs.next()) {
					if(rs.getString("cont_pacient_cod")!=null)
						return "pacient";
				if(rs.getString("cont_medic_cod")!=null)
					return "medic";
				if(rs.getString("cont_pacient_cod")==null && rs.getString("cont_medic_cod")==null)
					return "admin";
				}
					
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return null;
	}
	public static void insertPacient(Persoana pacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO Pacient(pacient_nume,pacient_prenume,pacient_cnp,pacient_email,pacient_telefon,pacient_data_nastere) values(?,?,?,?,?,?)";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,pacient.getNume());
				stmt.setString(2,pacient.getPrenume());
				stmt.setString(3,pacient.getCnp());
				stmt.setString(4,pacient.getEmail());
				stmt.setString(5,pacient.getTelefon());
				stmt.setDate(6,pacient.getData_nastere());
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	
	public static int getCodContPacient(String email) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int cod=0;
		String query="select cont_id from cont where cont_email =?";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,email);
				rs=stmt.executeQuery();
				if(rs.next()) 
				 cod=rs.getInt("cont_id");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return cod;
	}
	public static void insertCodContIntoPacient(String email,Long cod) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="Update pacient set pacient_cont_id=? where pacient_email=?";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setInt(1,cod.intValue());
				stmt.setString(2,email);
				
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertUser(String email,String parola,int id) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO Cont(cont_email,cont_parola,cont_pacient_cod) values(?,?,?)";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,email);
				stmt.setString(2,parola);
				stmt.setInt(3, id);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
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
