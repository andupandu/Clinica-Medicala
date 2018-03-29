package pkg.Utils;
import pkg.Entities.Analiza;
import pkg.Entities.Medic;
import pkg.Entities.Persoana;
import pkg.Entities.Serviciu;
import pkg.Entities.Specialitate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;


public class DbOperations {
	private static Connection conn = getConnection();
	
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

	public static Long getCodSpecFromDenSpec(String denumire) throws SQLException {
		Long cod=null;
		String query="SELECT specialitate_cod FROM Specialitate where specialitate_denumire=? ";
		ResultSet rs =getQueryResults(query, Arrays.asList(denumire));
		while (rs.next()) {
			cod = rs.getLong("Specialitate_cod");	
		}
		CloseResources(conn, rs, null);
		return cod;
	
	}
	public static ResultSet getQueryResults(String query, List<Object> params) {
		conn = getConnection();
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="Delete FROM medic where medic_cod= ?";
		conn = getConnection();
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
	
	public static void deleteSpecialitate(String codSpec) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="Delete FROM specialitate where specialitate_cod= ?";
		conn = getConnection();
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, Long.valueOf(codSpec));
						stmt.executeUpdate();
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		
	}
	public static void insertNewSpecializare(String denumire) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="insert into specialitate(specialitate_denumire) values(?)";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, denumire);
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	
	public static void insertAnalize(String ora,Date data,String status,Long codPacient,Long analizaCod) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="insert into programareanaliza values(?,?,?,?,?)";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, ora);
				stmt.setDate(2, data);
				stmt.setString(3, status);
				stmt.setLong(4, codPacient);
				stmt.setLong(5,analizaCod);
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static List<Specialitate> getSpecializari() throws SQLException {
		String query="SELECT * FROM Specialitate";
		ResultSet rs =getQueryResults(query, null);
		List<Specialitate> specializari = new ArrayList<Specialitate>();
		while (rs.next()) {
			Specialitate spec=new Specialitate();
			spec.setCod(rs.getLong("specialitate_cod"));
			spec.setDenumire(rs.getString("specialitate_denumire"));
			specializari.add(spec);
		}
		CloseResources(conn, rs, null);
		return specializari;
	}
	public static List<Medic> getMedicFromCodSpec(Long codSpec) throws SQLException{
		String query="Select medic_cod,medic_nume,medic_prenume from medic where specialitate_cod=?";
		ResultSet rs=getQueryResults(query,  Arrays.asList(codSpec));
		List<Medic> medici=new ArrayList<Medic>();
		while(rs.next()) {
			Medic medic=new Medic();
			medic.setId(rs.getLong("medic_cod"));
			medic.setNume(rs.getString("medic_nume"));
			medic.setPrenume(rs.getString("medic_prenume"));
			medici.add(medic);
		}
		CloseResources(conn, rs, null);
		return medici;
	}
	public static String getDenServiciuFromCodServiciu(Long codServiciu) throws SQLException{
		String query="Select serviciu_denumire from serviciu where serviciu_cod=?";
		ResultSet rs=getQueryResults(query,  Arrays.asList(codServiciu));
		String denumireServ=null;
		while(rs.next()) {
			denumireServ=rs.getString("serviciu_denumire");
		}
		CloseResources(conn, rs, null);
		return denumireServ;
	}
	public static List<Serviciu> getServicii() throws SQLException{
		String query="Select * from ofera";
		ResultSet rs=getQueryResults(query,  null);
		List<Serviciu> servicii=new ArrayList<Serviciu>();
		while(rs.next()) {
			Serviciu serviciu=new Serviciu();
			serviciu.setCodMedic(rs.getLong("medic_cod"));
			serviciu.setCod(rs.getLong("serviciu_cod"));
			servicii.add(serviciu);
		}
		CloseResources(conn, rs, null);
		return servicii;
	}	
	
	public static List<Persoana> getPacienti() throws SQLException{
		String query="Select * from pacient";
		ResultSet rs=getQueryResults(query,  null);
		List<Persoana> pacienti=new ArrayList<Persoana>();
		while(rs.next()) {
			Persoana pacient=new Persoana();
			pacient.setNume(rs.getString("pacient_nume"));
			pacient.setPrenume(rs.getString("pacient_prenume"));
			pacient.setId(rs.getLong("pacient_cod"));
			pacienti.add(pacient);
		}
		CloseResources(conn, rs, null);
		return pacienti;
	}	
	
	public static List<Analiza> getAnalize() throws SQLException{
		String query="Select * from analiza";
		ResultSet rs=getQueryResults(query,  null);
		List<Analiza> analize=new ArrayList<Analiza>();
		while(rs.next()) {
			Analiza analiza=new Analiza();
			analiza.setCod(rs.getLong("analiza_cod"));
			analiza.setDenumire(rs.getString("analiza_denumire"));
			analiza.setPret(rs.getLong("analiza_pret"));
			analiza.setDurata(rs.getString("analiza_durata"));
			analize.add(analiza);
		}
		CloseResources(conn, rs, null);
		return analize;
	}
	public static List<Long> getCodServiciuFromCodMedic(String codMedic) throws SQLException{
		String query="Select serviciu_cod from ofera where medic_cod=?";
		ResultSet rs=getQueryResults(query,  Arrays.asList(Long.valueOf(codMedic)));
		List<Long> codServicii=new ArrayList<Long>();
		while(rs.next()) {
			codServicii.add(rs.getLong("serviciu_cod"));
		}
		CloseResources(conn, rs, null);
		return codServicii;
	}
	public static String getSpecializari(long cod) throws SQLException {
		String query="SELECT * FROM Specialitate where specialitate_cod=?";
		ResultSet rs =getQueryResults(query, Arrays.asList(cod));
		String specializare=null;
		while (rs.next()) {
			specializare = rs.getString("Specialitate_denumire");									
		}
		CloseResources(conn, rs, null);
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
	public static void modifyMedic(Medic medic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="update  Medic set medic_nume=?,medic_prenume=?,medic_email=?,specialitate_cod=?,medic_telefon=? where medic_cod= ?";
		try {
			if (conn != null) {			
				stmt=conn.prepareStatement(query);
				stmt.setString(1, medic.getNume());
				stmt.setString(2, medic.getPrenume());
				stmt.setString(3, medic.getEmail());
				stmt.setLong(4, medic.getCodSpec());
				stmt.setString(5, medic.getTelefon());
				stmt.setLong(6, medic.getId());
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	
	
	public static void modifySpecialitate(Specialitate spec) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="update  specialitate set specialitate_denumire=? where specialitate_cod= ?";
		try {
			if (conn != null) {			
				stmt=conn.prepareStatement(query);
				stmt.setString(1, spec.getDenumire());
				stmt.setLong(2,spec.getCod());
				
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
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
					medic.setEmail(rs.getString("medic_email"));
					medic.setTelefon(rs.getString("medic_telefon"));
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
	
//	public static List<Persoana> getListaPacienti() {
//		Connection conn = getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		List<Persoana> pacienti=new ArrayList<Persoana>();
//		
//		String query="SELECT * FROM pacient";
//		try {
//
//			if (conn != null) {
//				
//				stmt=conn.prepareStatement(query);
//						rs=stmt.executeQuery();
//
//				while (rs.next()) {
//					Persoana pacient=new Persoana();
//					pacient.setId(rs.getLong("pacient_cod"));
//					pacient.setNume(rs.getString("pacient_nume"));
//					pacient.setPrenume(rs.getString("pacient_cnp"));
//					pacient.setEmail(rs.getString("pacient_data_nasterii"));
//					pacient.setTelefon(rs.getString("medic_telefon"));
//					pacienti.add(pacient);
//					
//				}
//			}
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		} finally {
//			CloseResources(conn, rs, stmt);
//		}
//		return medici;
//	}
	
	public static Persoana cautaPacientDupaCNP(String cnp) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Persoana pacient=null;
		
		String query="SELECT * FROM pacient where pacient_cnp=?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, cnp);
						rs=stmt.executeQuery();

				while (rs.next()) {
					pacient=new Persoana();
					pacient.setId(rs.getLong("pacient_cod"));
					pacient.setNume(rs.getString("pacient_nume"));
					pacient.setPrenume(rs.getString("pacient_prenume"));
					pacient.setEmail(rs.getString("pacient_email"));
					pacient.setTelefon(rs.getString("pacient_telefon"));
					
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return pacient;
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
