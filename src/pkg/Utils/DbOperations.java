package pkg.Utils;
import pkg.Entities.Analiza;
import pkg.Entities.Categorie;
import pkg.Entities.Consultatie;
import pkg.Entities.Indicator;
import pkg.Entities.Interval;
import pkg.Entities.Medic;
import pkg.Entities.Persoana;
import pkg.Entities.ProgramLucru;
import pkg.Entities.ProgramareAnaliza;
import pkg.Entities.Rezultat;
import pkg.Entities.Serviciu;
import pkg.Entities.Specialitate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.sql.*;
import java.text.ParseException;


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
			System.out.println("Exception:" + e.getMessage());
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
	public static boolean medicAreServicii(Long  codMedic) throws SQLException {
		boolean areServicii=false;
		String query="SELECT * FROM ofera where medic_cod=? ";
		ResultSet rs =getQueryResults(query, Arrays.asList(codMedic));
		if (rs.next()) {
				areServicii= true;
		}
		CloseResources(conn, rs, null);
		return areServicii;
	
	}
	public static String getSpecialitateMedic(String codMedic) throws SQLException {
		String query="SELECT specialitate_denumire FROM medic,specialitate where medic_cod=? and specialitate.specialitate_cod=medic.specialitate_cod ";
		String cod=null;
		ResultSet rs =getQueryResults(query, Arrays.asList(codMedic));
		if (rs.next()) {
				 cod=rs.getString("specialitate_denumire");
		}
		CloseResources(conn, rs, null);
		return cod;
	
	}
	public static boolean medicAreProgram(Long  codMedic) throws SQLException {
		boolean areProgram=false;
		String query="SELECT * FROM areprogram where areprogram_medic_cod=? and areprogram_valid=1";
		ResultSet rs =getQueryResults(query, Arrays.asList(codMedic));
		if (rs.next()) {
				areProgram= true;
		}
		CloseResources(conn, rs, null);
		return areProgram;
	
	}
	public static String getPacientEmail(String  pacient) throws SQLException {
		
		String query="SELECT * FROM pacient where CONCAT(pacient_nume, ' ', pacient_prenume)=? ";
		ResultSet rs =getQueryResults(query, Arrays.asList(pacient));
		String email=null;
		if (rs.next()) {
				email= rs.getString("pacient_email");
		}
		CloseResources(conn, rs, null);
		return email;
	
	}
	public static List<Medic> getMediciPentruConsultatii(Long codSpec) throws SQLException {
		List<Medic> medici=DbOperations.getMedicFromCodSpec(codSpec);
		for (Iterator<Medic> iter = medici.listIterator(); iter.hasNext(); ) {
		    Medic medic = iter.next();
		    if (!DbOperations.medicAreProgram(medic.getId()) ||!DbOperations.medicAreServicii(medic.getId())) {
		        iter.remove();
		    }
		}
		return medici;
	
	}
	public static List<Long> getListaPacienti(Date data,String codMedic) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="SELECT * FROM programareconsultatie where programareconsultatie_medic_cod=? and programareconsultatie_data_consultatie=? and programareconsultatie_status='in curs'";
		conn = getConnection();
		List<Long> coduriPacienti=new ArrayList<Long>();
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, Long.valueOf(codMedic));
				stmt.setDate(2 ,data);
				rs=stmt.executeQuery();

				while (rs.next()) {
					coduriPacienti.add(rs.getLong("programareconsultatie_pacient_cod"));
				}
			}
	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return coduriPacienti;
		
	}
	
	public static List<Long> getListaPacienti(String zi,String codMedic) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="SELECT * FROM programareconsultatie where programareconsultatie_medic_cod=? and ro_dayname(programareconsultatie_data_consultatie)=? and programareconsultatie_status='in curs'";
		conn = getConnection();
		List<Long> coduriPacienti=new ArrayList<Long>();
		try {
			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, Long.valueOf(codMedic));
				stmt.setString(2 ,zi);
				rs=stmt.executeQuery();
				while (rs.next()) {
					coduriPacienti.add(rs.getLong("programareconsultatie_pacient_cod"));
				}
			}
	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return coduriPacienti;
		
	}
	public static String getEmailPacient(Long codPacient) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="SELECT * FROM pacient where pacient_cod=?";
		conn = getConnection();
		String email=null;
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setLong(1,codPacient);
				rs=stmt.executeQuery();
				while (rs.next()) {
					email=rs.getString("pacient_email");
				}
			}
	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return email;
		
	}
	public static List<String> getEmailuriPacienti(String codMedic,Date data) throws SQLException {
		List<String> emails=new ArrayList<String>();
		for(Long codP :getListaPacienti(data, codMedic)){
			System.out.println("EMAILURIIIII "+getEmailPacient(codP));
			emails.add(getEmailPacient(codP));
		}
		return emails;
	}
	public static List<String> getEmailuriPacienti(String codMedic,String zi) throws SQLException {
		List<String> emails=new ArrayList<String>();
		for(Long codP :getListaPacienti(zi, codMedic))	{
			System.out.println("EMAILURIIIII "+getEmailPacient(codP));
			emails.add(getEmailPacient(codP));
		}
		return emails;
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
		String query="update medic set medic_valid =0 where medic_cod= ?";
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
	
	public static void deletePacient(String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="update pacient set pacient_valid =0 where pacient_cod= ?";
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
		String query="update specialitate set specialitate_valid=0 where specialitate_cod=?";
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
		String query="insert into specialitate(specialitate_denumire,specialitate_valid) values(?,1)";
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
	
	public static void insertAnalize(String ora,Date data,String status,Long codPacient,Long analizaCod,Long maxBuletin) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="insert into programareanaliza values(?,?,?,?,?,?)";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, ora);
				stmt.setDate(2, data);
				stmt.setString(3, status);
				stmt.setLong(4, codPacient);
				stmt.setLong(5,analizaCod);
				stmt.setLong(6, maxBuletin);
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static Long getMaxBuletin() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Long max=null;
		String query="select max(programareanaliza_codbuletin)+1 maxim from  programareanaliza";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				rs=stmt.executeQuery();
			}
			if(rs.next()) {
				max=rs.getLong("maxim");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return max;
	}
	
	public static List<Specialitate> getSpecializari() throws SQLException {
		String query="SELECT * FROM Specialitate where specialitate_valid=1";
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
	
	public static List<Categorie> getCategorii() throws SQLException {
		String query="SELECT * FROM categorie";
		ResultSet rs =getQueryResults(query, null);
		List<Categorie> categorii = new ArrayList<Categorie>();
		while (rs.next()) {
			Categorie categorie=new Categorie();
			categorie.setCod(rs.getLong("categorie_id"));
			categorie.setDenumire(rs.getString("categorie_denumire"));
			categorii.add(categorie);
		}
		CloseResources(conn, rs, null);
		return categorii;
	}
	public static boolean isServiciu(String serviciu) throws SQLException {
		String query="SELECT * FROM serviciu where serviciu_denumire=?";
		ResultSet rs =getQueryResults(query, Arrays.asList(serviciu));
		if (rs.next()) {
			return true;
		}
		CloseResources(conn, rs, null);
		return false;
	}
	public static List<Medic> getMedicFromCodSpec(Long codSpec) throws SQLException{
		String query="Select * from medic where specialitate_cod=? and medic_valid=1";
		ResultSet rs=getQueryResults(query,  Arrays.asList(codSpec));
		List<Medic> medici=new ArrayList<Medic>();
		while(rs.next()) {
			Medic medic=new Medic();
			medic.setId(rs.getLong("medic_cod"));
			medic.setNume(rs.getString("medic_nume"));
			medic.setPrenume(rs.getString("medic_prenume"));
			medic.setTelefon(rs.getString("medic_telefon"));
			medic.setEmail(rs.getString("medic_email"));
			medic.setCodSpec(rs.getLong("specialitate_cod"));
			medici.add(medic);
		}
		CloseResources(conn, rs, null);
		return medici;
	}
	public static boolean isFreeDay(Date data) throws SQLException, ParseException {
	return getFreeDays().contains(data);
	}
	public static List<Date> getFreeDays() throws SQLException, ParseException{
		String query="Select * from zilibera";
		ResultSet rs=getQueryResults(query,  null);
		List<Date> dateLibere=new ArrayList<Date>();
		while(rs.next()) {
			String data=Calendar.getInstance().get(Calendar.YEAR)+"-"+rs.getString("zilibera_luna")+"-"+rs.getString("zilibera_zi");
			Date dataLibera=DateUtil.getDateFromString(data);
			
			dateLibere.add(dataLibera);
		}
		CloseResources(conn, rs, null);
		return dateLibere;
	}
	
	public static List<Medic> getMedici() throws SQLException{
		String query="Select * from medic where medic_valid=1";
		ResultSet rs=getQueryResults(query,  null);
		List<Medic> medici=new ArrayList<Medic>();
		while(rs.next()) {
			Medic medic=new Medic();
			medic.setId(rs.getLong("medic_cod"));
			medic.setNume(rs.getString("medic_nume"));
			medic.setPrenume(rs.getString("medic_prenume"));
			medic.setTelefon(rs.getString("medic_telefon"));
			medic.setEmail(rs.getString("medic_email"));
			medic.setCodSpec(rs.getLong("specialitate_cod"));
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
	public static List<Serviciu> getServiciiMedic(String codMedic) throws SQLException{
		String query="select ofera_pret,serviciu_denumire from clinicamedicala.ofera,clinicamedicala.serviciu" + 
				" where medic_cod=? and serviciu.serviciu_cod=ofera.serviciu_cod";
		ResultSet rs=getQueryResults(query,  Arrays.asList(codMedic));
		List<Serviciu> servicii=new ArrayList<Serviciu>();
		while(rs.next()) {
			Serviciu serviciu=new Serviciu();
			serviciu.setDenumire(rs.getString("serviciu_denumire"));
			serviciu.setPret(rs.getLong("ofera_pret"));
			servicii.add(serviciu);
		}
		CloseResources(conn, rs, null);
		return servicii;
	}	
	public static String getTimpServiciu(Long codMedic,Long codServiciu) throws SQLException{
		String query="Select ofera_timp from ofera where medic_cod=? and serviciu_cod=?";
		ResultSet rs=getQueryResults(query, Arrays.asList(codMedic,codServiciu));
		String minute=null;
		while(rs.next()) {
			minute=rs.getString("ofera_timp");
		}
		CloseResources(conn, rs, null);
		return minute;
		
	}
	public static List<Persoana> getPacienti() throws SQLException{
		String query="Select * from pacient where pacient_valid=1";
		ResultSet rs=getQueryResults(query,  null);
		List<Persoana> pacienti=new ArrayList<Persoana>();
		while(rs.next()) {
			Persoana pacient=new Persoana();
			pacient.setNume(rs.getString("pacient_nume"));
			pacient.setPrenume(rs.getString("pacient_prenume"));
			pacient.setId(rs.getLong("pacient_cod"));
			pacient.setCnp(rs.getString("pacient_cnp"));
			pacient.setEmail(rs.getString("pacient_email"));
			pacient.setTelefon(rs.getString("pacient_telefon"));
			pacient.setData_nastere(rs.getDate("pacient_data_nastere"));
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
	public static List<Analiza> getAnalizeFromCodCategorie(Long codCategorie) throws SQLException{
		String query="Select * from analiza where categorie_id=?";
		ResultSet rs=getQueryResults(query,  Arrays.asList(codCategorie));
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
	public static boolean esteAnalizaInDb(String denumire) throws SQLException{
		String query="Select * from analiza where analiza_denumire=?";
		ResultSet rs=getQueryResults(query,  Arrays.asList(denumire));
		List<Analiza> analize=new ArrayList<Analiza>();
		if(rs.next()) {
			return true;
		}
		CloseResources(conn, rs, null);
		return false;
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
	public static Long getCodServiciuFromDenServiciu(String denServiciu) throws SQLException{
		String query="Select serviciu_cod from serviciu where serviciu_denumire=?";
		ResultSet rs=getQueryResults(query, Arrays.asList(denServiciu));
		Long codServiciu=null;
		while(rs.next()) {
			codServiciu=rs.getLong("serviciu_cod");
		}
		CloseResources(conn, rs, null);
		return codServiciu;
	}

	public static String getSpecializari(long cod) throws SQLException {
		String query="SELECT * FROM Specialitate where specialitate_cod=? and specialitate_valid=1";
		ResultSet rs =getQueryResults(query, Arrays.asList(cod));
		String specializare=null;
		while (rs.next()) {
			specializare = rs.getString("Specialitate_denumire");									
		}
		CloseResources(conn, rs, null);
		return specializare==null?"-":specializare;
	}
	public static String getNumePrenumeMedic(long cod) throws SQLException {
		String query="SELECT * FROM medic where medic_cod=? and medic_valid=1";
		ResultSet rs =getQueryResults(query, Arrays.asList(cod));
		String numePrenume=null;
		while (rs.next()) {
			 numePrenume = rs.getString("medic_nume")+" "+rs.getString("medic_prenume");									
		}
		CloseResources(conn, rs, null);
		return numePrenume;
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
	public static List<Serviciu> getMedicServicii(String idMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Serviciu> servicii=new ArrayList<Serviciu>();
		String query="SELECT * from ofera,serviciu where serviciu.serviciu_cod=ofera.serviciu_cod and medic_cod= ?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, Long.valueOf(idMedic));
						rs=stmt.executeQuery();

				while (rs.next()) {
					Serviciu serviciu=new Serviciu();
					serviciu.setCod(rs.getLong("serviciu_cod"));
					serviciu.setDenumire(rs.getString("serviciu_denumire"));
					serviciu.setPret(rs.getLong("ofera_pret"));
					serviciu.setTimp(rs.getString("ofera_timp"));
					servicii.add(serviciu);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return servicii;
	}
	public static List<Consultatie> getConsultatiiInFunctieDeData(String dataInceput,String dataSfarsit) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Consultatie> consultatii=new ArrayList<Consultatie>();
		String query="select programareconsultatie_detalii,programareconsultatie_ora_consultatie,programareconsultatie_data_consultatie,serviciu_denumire," + 
				"concat(pacient_nume,' ',pacient_prenume) pacient" + 
				" from programareconsultatie,pacient,serviciu" + 
				" where programareconsultatie_data_consultatie>=?" + 
				" and programareconsultatie_data_consultatie<=?" + 
				" and programareconsultatie_status='in curs'" + 
				" and pacient_cod=programareconsultatie.programareconsultatie_pacient_cod" + 
				" and serviciu_cod=programareconsultatie.programareconsultatie_tip_consultatie";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, dataInceput);
				stmt.setString(2, dataSfarsit);
						rs=stmt.executeQuery();

				while (rs.next()) {
					Consultatie consultatie=new Consultatie();
					consultatie.setData(rs.getDate("programareconsultatie_data_consultatie"));
					consultatie.setDetalii(rs.getString("programareconsultatie_detalii"));
					consultatie.setOraInceput(rs.getString("programareconsultatie_ora_consultatie"));
					consultatie.setPacient(rs.getString("pacient"));
					consultatie.setTipConsutatie(rs.getString("serviciu_denumire"));
					consultatii.add(consultatie);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return consultatii;
	}
	public static List<Consultatie> getConsultatiiPentruPDF(String codPacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Consultatie> consultatii=new ArrayList<Consultatie>();
		String query="select programareconsultatie_status,programareconsultatie_ora_consultatie,programareconsultatie_data_consultatie,serviciu_denumire," + 
				"concat(medic_nume,' ',medic_prenume) medic " + 
				"from programareconsultatie,medic,serviciu " + 
				"where  medic_cod=programareconsultatie.programareconsultatie_medic_cod " + 
				"and serviciu_cod=programareconsultatie.programareconsultatie_tip_consultatie " + 
				"and programareconsultatie_pacient_cod=?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, codPacient);
	
						rs=stmt.executeQuery();

				while (rs.next()) {
					Consultatie consultatie=new Consultatie();
					consultatie.setData(rs.getDate("programareconsultatie_data_consultatie"));
					consultatie.setStatus(rs.getString("programareconsultatie_status"));
					consultatie.setOraInceput(rs.getString("programareconsultatie_ora_consultatie"));
					consultatie.setMedic(rs.getString("medic"));
					consultatie.setTipConsutatie(rs.getString("serviciu_denumire"));
					consultatii.add(consultatie);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return consultatii;
	}
	public static Long getCodPacientFromCnp(String cnp) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Long codPacient=null;
		String query="SELECT * FROM Pacient where pacient_cnp= ?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, cnp);
						rs=stmt.executeQuery();

				while (rs.next()) {
					codPacient=rs.getLong("pacient_cod");
					
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return codPacient;
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
	public static void modifyPacient(Persoana pacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="update pacient set pacient_nume=?,pacient_prenume=?,pacient_email=?,pacient_data_nastere=?,pacient_telefon=? where pacient_cod= ?";
		try {
			if (conn != null) {			
				stmt=conn.prepareStatement(query);
				stmt.setString(1, pacient.getNume());
				stmt.setString(2, pacient.getPrenume());
				stmt.setString(3, pacient.getEmail());
				stmt.setDate(4, pacient.getData_nastere());
				stmt.setString(5, pacient.getTelefon());
				stmt.setLong(6, pacient.getId());
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void modifyPacientEmailInCont(Persoana pacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="update cont set cont_email=? where cont_pacient_cod= ?";
		try {
			if (conn != null) {			
				stmt=conn.prepareStatement(query);	
				stmt.setString(1, pacient.getEmail());
				stmt.setLong(2, pacient.getId());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void modifyMedicEmailInCont(Medic  medic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="update cont set cont_email=? where cont_medic_cod= ?";
		try {
			if (conn != null) {			
				stmt=conn.prepareStatement(query);	
				stmt.setString(1, medic.getEmail());
				stmt.setLong(2, medic.getId());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void modifyUserEmailInCont(Long userCod,String tipUser,String email) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query=null;
		if(tipUser=="medic") {
		 query="update cont set cont_email=? where cont_medic_cod= ?";
		}
		else if(tipUser=="receptioner") {
			query="update cont set cont_email=? where cont_receptioner_cod= ?";
		}
		try {
			if (conn != null) {			
				stmt=conn.prepareStatement(query);	
				stmt.setString(1, email);
				stmt.setLong(2, userCod);
				
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
	public static void modifyCategorie(Categorie categorie) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="update categorie set categorie_denumire=? where categorie_id= ?";
		try {
			if (conn != null) {			
				stmt=conn.prepareStatement(query);
				stmt.setString(1, categorie.getDenumire());
				stmt.setLong(2,categorie.getCod());
				
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void modifyServiciu(Serviciu serviciu,String idMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="update ofera set ofera_timp=?,ofera_pret=? where serviciu_cod=? and medic_cod=?";
		try {
			if (conn != null) {			
				stmt=conn.prepareStatement(query);
				stmt.setString(1, serviciu.getTimp());
				stmt.setLong(2,serviciu.getPret());
				stmt.setLong(3,serviciu.getCod());
				stmt.setLong(4,Long.valueOf(idMedic));
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static Persoana getReceptioner(String email) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Persoana persoanaLogata=new Persoana();
		String query="SELECT receptioner_id,receptioner_nume,receptioner_prenume FROM receptioner,cont where receptioner_id=cont_receptioner_cod and cont_email=?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, email);
				
						rs=stmt.executeQuery();

				while (rs.next()) {
					String nume = rs.getString("receptioner_nume");
					String prenume=rs.getString("receptioner_prenume");
					persoanaLogata.setNume(nume);
					persoanaLogata.setPrenume(prenume);
					persoanaLogata.setId(rs.getLong("receptioner_id"));
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
	public static Persoana getReceptioner(Persoana receptioner) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Persoana persoanaLogata=new Persoana();
		String query="SELECT receptioner_id,receptioner_nume,receptioner_prenume FROM receptioner where receptioner_nume=? and receptioner_prenume=?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, receptioner.getNume());
				stmt.setString(2, receptioner.getPrenume());
						rs=stmt.executeQuery();

				while (rs.next()) {
					String nume = rs.getString("receptioner_nume");
					String prenume=rs.getString("receptioner_prenume");
					persoanaLogata.setNume(nume);
					persoanaLogata.setPrenume(prenume);
					persoanaLogata.setId(rs.getLong("receptioner_id"));
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
		String query="SELECT * FROM Medic where medic_email= ? ";
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
	public static List<String> getAnalizeleInCursAlePacientului(Long codPacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<String> analize=new ArrayList<String>();
		String query="select analiza_denumire from clinicamedicala.analiza,clinicamedicala.programareanaliza" + 
				" where analiza_cod=programareanaliza_analiza_cod" + 
				" and programareanaliza_pacient_cod=?" + 
				" and programareanaliza_status='in curs'";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, codPacient);
						rs=stmt.executeQuery();

				while (rs.next()) {
					String denumire=rs.getString("analiza_denumire");
					analize.add(denumire);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return analize;
	}
	
	public static List<Rezultat> getAnalizePentruPDF(String codPacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Rezultat> rezultate=new ArrayList<Rezultat>();
		String query="select analiza_denumire,arerezultat_interpretare ,arerezultat_val_rezultat,arerezultat_data_rezultat" + 
				" from analiza,programareanaliza,arerezultat " + 
				"where analiza_cod=programareanaliza_analiza_cod " + 
				"and programareanaliza_pacient_cod=? " + 
				"and arerezultat_analiza_cod=programareanaliza_analiza_cod and arerezultat_pacient_cod=programareanaliza.programareanaliza_pacient_cod" ;
				
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, codPacient);
						rs=stmt.executeQuery();

				while (rs.next()) {
					Rezultat rezultat=new Rezultat();
					rezultat.setAnaliza(rs.getString("analiza_denumire"));
					rezultat.setInterpretare(rs.getString("arerezultat_interpretare"));
					rezultat.setValoare(rs.getString("arerezultat_val_rezultat"));
					rezultat.setData(rs.getString("arerezultat_data_rezultat"));

					rezultate.add(rezultat);
					
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return rezultate;
	}
	public static List<ProgramareAnaliza> getAnalizeleBuletinului(String codBuletin,String codPacient,String categorie) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ProgramareAnaliza> programari=new ArrayList<ProgramareAnaliza>();
		String query=" select programareanaliza_analiza_cod,programareanaliza_ora_cerere,analiza_denumire," + 
				"programareanaliza_data_analiza,categorie_denumire from programareanaliza,analiza,categorie where" + 
				" programareanaliza_analiza_cod=analiza.analiza_cod and programareanaliza.programareanaliza_codbuletin=? and programareanaliza_pacient_cod=?"
				+ "and analiza.categorie_id=(select categorie_id from clinicamedicala.categorie where categorie_denumire=?) and analiza.categorie_id=categorie.categorie_id" ;
				
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, codBuletin);
				stmt.setString(2, codPacient);
				stmt.setString(3, categorie);

						rs=stmt.executeQuery();
				while (rs.next()) {
					ProgramareAnaliza programare=new ProgramareAnaliza();
					programare.setCodAnaliza(rs.getString("programareanaliza_analiza_cod"));
					programare.setData(rs.getDate("programareanaliza_data_analiza"));
					programare.setOra(rs.getString("programareanaliza_ora_cerere"));
					programare.setDenumireAnaliza(rs.getString("analiza_denumire"));
					programare.setCategorieAnaliza(rs.getString("categorie_denumire"));
					programari.add(programare);
					
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return programari;
	}
	public static List<String> getCategoriiAnalizeBuletin(String codBuletin,String codPacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<String> categorii=new ArrayList<String>();
		String query=" select distinct categorie_denumire from programareanaliza,analiza,categorie where" + 
				" programareanaliza_analiza_cod=analiza.analiza_cod and programareanaliza.programareanaliza_codbuletin=? and programareanaliza_pacient_cod=?"
				+ "and analiza.categorie_id=categorie.categorie_id" ;
				
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, codBuletin);
				stmt.setString(2, codPacient);
						rs=stmt.executeQuery();
				while (rs.next()) {
					categorii.add(rs.getString("categorie_denumire"));
					
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return categorii;
	}
	public static List<String> getOreIndisponibileAnaliza(String data) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<String> oreIndisponibile=new ArrayList<String>();
		String query="select programareanaliza_ora_cerere" + 
				" from clinicamedicala.programareanaliza" + 
				" where programareanaliza_data_analiza=?" + 
				" group by programareanaliza_ora_cerere" + 
				" having count(distinct programareanaliza_pacient_cod)>=10";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, data);
						rs=stmt.executeQuery();

				while (rs.next()) {
					oreIndisponibile.add(rs.getString("programareconsultatie_ora_consultatie"));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return oreIndisponibile;
	}
	public static Rezultat getRezultatAnalize(String codAnaliza,String codBuletin) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Rezultat rezultat=new Rezultat();
		String query="select distinct arerezultat_interpretare ,arerezultat_val_rezultat" + 
				" from clinicamedicala.analiza,clinicamedicala.programareanaliza,clinicamedicala.arerezultat ,clinicamedicala.indicator" + 
				" where  programareanaliza_codbuletin=? and analiza.analiza_cod=programareanaliza_analiza_cod" + 
				" and programareanaliza_pacient_cod=arerezultat_pacient_cod and" + 
				" arerezultat_analiza_cod=programareanaliza_analiza_cod and arerezultat_analiza_cod=?";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, codBuletin);
				stmt.setString(2, codAnaliza);
						rs=stmt.executeQuery();

				if (rs.next()) {
					rezultat.setInterpretare(rs.getString("arerezultat_interpretare"));
					rezultat.setValoare(rs.getString("arerezultat_val_rezultat"));
					
				}
				else {
					rezultat.setInterpretare("In curs");
					rezultat.setValoare("In curs");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return rezultat;
	}
	
	public static List<Medic> getListaMedici() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Medic> medici=new ArrayList<Medic>();
		
		String query="SELECT * FROM Medic where medic_valid=1";
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
	public static Indicator getIndicator(String codAnaliza,String sex,String tipPersoana) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Indicator indicator=new Indicator();
		String query="select * from clinicamedicala.indicator where indicator_analiza_cod=? and (indicator_sex=? or indicator_sex is null) and (indicator_tip_persoana=? or indicator_tip_persoana is null)"; 	
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, codAnaliza);
				stmt.setString(2, sex);
				stmt.setString(3, tipPersoana);
						rs=stmt.executeQuery();

				while (rs.next()) {
					indicator.setIntervalRefMax(rs.getString("indicator_interval_referinta_max"));
					indicator.setIntervalRefMin(rs.getString("indicator_interval_referinta_min"));
					
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return indicator;
	}
	

	
	public static Persoana cautaPacientDupaCNP(String cnp) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Persoana pacient=new Persoana();
		
		String query="SELECT * FROM pacient where pacient_cnp=? and pacient_valid=1";
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
					pacient.setCnp(rs.getString("pacient_cnp"));
					pacient.setData_nastere(rs.getDate("pacient_data_nastere"));
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
	public static boolean areZiuaLibera(Long medicId,String data) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="SELECT * FROM concediu where medic_cod=? and concediu_data=?";
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, medicId);
				stmt.setString(2, data);
						rs=stmt.executeQuery();

				if (rs.next()) {
					return true;				
					}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return false;
	}
	public static String getNumeAnalizaFromCod(Long codAnaliza) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String denumireAnaliza="";
		String query="SELECT * FROM analiza where analiza_cod=?";
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, codAnaliza);
						rs=stmt.executeQuery();

				while (rs.next()) {
					denumireAnaliza=rs.getString("analiza_denumire");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return denumireAnaliza;
	}
	public static boolean mediculOferaServiciul(String denumireServ,String idMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="SELECT * FROM ofera where medic_cod=? and serviciu_cod=(select serviciu_cod from serviciu where serviciu_denumire=?)";
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, Long.valueOf(idMedic));
				stmt.setString(2, denumireServ);
						rs=stmt.executeQuery();

				if (rs.next()) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return false;
	}
	
	// se verifica daca pacientul are o programare in curs la analiza selectata
	public static List<Consultatie> cautaProgramareAnaliza(Long codPacient,List<String> coduriAnaliza) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Consultatie> consultatii=new ArrayList<Consultatie>();
		
		String query="SELECT * FROM programareanaliza where programareanaliza_pacient_cod=? and programareanaliza_analiza_cod=? and programareanaliza_status='in curs'";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, codPacient);
				for(String codAnaliza:coduriAnaliza) {
				stmt.setLong(2, Long.valueOf(codAnaliza));
						rs=stmt.executeQuery();

				while(rs.next()) {
					Consultatie consultatie=new Consultatie();
					consultatie.setOraInceput(rs.getString("programareanaliza_ora_cerere"));
					consultatie.setData(rs.getDate("programareanaliza_data_analiza"));
					consultatie.setTipConsutatie(getNumeAnalizaFromCod(rs.getLong("programareanaliza_analiza_cod")));
					consultatii.add(consultatie);
				}
			}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return consultatii;
	}
	public static String isAccountInDB(String email,String parola) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query=null;
		if(parola==null) {
			 query="SELECT * FROM cont WHERE cont_email= ?";

		}else {
			 query="SELECT * FROM cont WHERE cont_email= ? and cont_parola= ?";
		}
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,email);
				if(parola!=null) {
				stmt.setString(2, parola);
				}
				
				rs=stmt.executeQuery();
				if(rs.next()) {
					if(rs.getString("cont_pacient_cod")!=null)
						return "pacient";
				if(rs.getString("cont_medic_cod")!=null)
					return "medic";
				if(rs.getString("cont_receptioner_cod")!=null)
					return "receptioner";
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
	public static void insertPacient(Persoana pacient) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO Pacient(pacient_nume,pacient_prenume,pacient_cnp,pacient_email,pacient_telefon,pacient_data_nastere,pacient_valid) values(?,?,?,?,?,?,1)";

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
		
			CloseResources(conn, rs, stmt);
		}
	
	
	public static void insertZiLibera(Long idMedic,String data) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO concediu(medic_cod,concediu_data) values(?,?)";

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setLong(1,idMedic);
				stmt.setString(2,data);
				stmt.executeUpdate();
	}
			CloseResources(conn, rs, stmt);
		}
	
	
	public static void insertMedicNou(Medic medic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO Medic(medic_nume,medic_prenume,medic_email,medic_telefon,medic_data_nastere,specialitate_cod,medic_valid) values(?,?,?,?,?,?,1)";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,medic.getNume());
				stmt.setString(2,medic.getPrenume());
				stmt.setString(3,medic.getEmail());
				stmt.setString(4,medic.getTelefon());
				stmt.setDate(5,medic.getData_nastere());
				stmt.setLong(6,medic.getCodSpec());
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertReceptionerNou(Persoana receptioner) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO receptioner(receptioner_nume,receptioner_prenume) values(?,?)";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,receptioner.getNume());
				stmt.setString(2,receptioner.getPrenume());
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertRezultat(Rezultat rezultat) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO arerezultat(arerezultat_val_rezultat,arerezultat_interpretare,arerezultat_data_rezultat,arerezultat_analiza_cod,arerezultat_pacient_cod)"
				+ " values(?,?,?,(select analiza_cod from analiza where analiza_denumire=?),?)";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,rezultat.getValoare());
				stmt.setString(2,rezultat.getInterpretare());
				stmt.setString(3,rezultat.getData());
				stmt.setString(4,rezultat.getAnaliza());
				stmt.setString(5, rezultat.getPacient());
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertServiciuNou(String denServiciu) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO serviciu(serviciu_denumire) values(?)";
		
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,denServiciu);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertCategorieNoua(String denCategorie) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO categorie(categorie_denumire) values(?)";
		
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,denCategorie);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void alocaServiciuNou(String denServiciu,String timp,String pret,String idMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO ofera(ofera_pret,ofera_timp,medic_cod,serviciu_cod) values(?,?,?,(select serviciu_cod from serviciu where serviciu_denumire=?))";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setLong(1,Long.valueOf(pret));
				stmt.setString(2,timp);
				stmt.setLong(3,Long.valueOf(idMedic));
				stmt.setString(4,denServiciu);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static int getCodCont(String email) {
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
	public static Long getUserCodFromPassword(Persoana user,String tipUser) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Long cod=null;
		String query=null;
		if(tipUser=="receptioner") {
		query="select cont_receptioner_cod from cont where cont_receptioner_cod=(select receptioner_id from receptioner where receptioner_nume=? and receptioner_prenume=?)";
		}
		else if(tipUser=="medic"){
			query="select cont_medic_cod from cont where cont_medic_cod=(select medic_cod from medic where medic_nume=? and medic_prenume=?)";
		}
		else if(tipUser=="pacient"){
			query="select cont_pacient_cod from cont where cont_pacient_cod=(select pacient_cod from pacient where pacient_nume=? and pacient_prenume=?)";
		}

		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
			
				stmt.setString(1,user.getNume());
				stmt.setString(2,user.getPrenume());
				System.out.println(user.getNume()+user.getPrenume());
				rs=stmt.executeQuery();
				if(rs.next()) 
					if(tipUser=="receptioner") {
				 cod=rs.getLong("cont_receptioner_cod");
					}
					else if(tipUser=="medic"){
						cod=rs.getLong("cont_medic_cod");
					}
					else {
						cod=rs.getLong("cont_pacient_cod");
					}
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return cod;
	}
	public static String getUserEmail(Persoana user,String tipUser) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String email=null;
		String query=null;
		if(tipUser=="receptioner") {
		query="select cont_email from cont where cont_receptioner_cod=(select receptioner_id from receptioner where receptioner_nume=? and receptioner_prenume=?)";
		}
		else {
			query="select cont_email from cont where cont_medic_cod=(select medic_cod from medic where medic_nume=? and medic_prenume=?)";
		}
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
			
				stmt.setString(1,user.getNume());
				stmt.setString(2,user.getPrenume());
				rs=stmt.executeQuery();
				if(rs.next()) 
				 email=rs.getString("cont_email");
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return email;
	}
	public static void modifyUserParola(Long codUser,String parola,String tipUser) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query=null;
		if(tipUser=="receptioner") {
		 query="update cont set cont_parola=? where cont_receptioner_cod=?";
		}
		else if(tipUser=="medic") {
			 query="update cont set cont_parola=? where cont_medic_cod=?";

		}
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,parola);
				stmt.setLong(2,codUser);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertPacientUser(String email,String parola,int id) {
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
	
	public static void insertCodContIntoMedic(String email,Long cod) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="Update medic set medic_cont_id=? where medic_email=?";
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
	
	public static void insertCodContIntoReceptioner(int id,Long cod) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="Update receptioner set receptioner_cont_cod=? where receptioner_id=?";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setInt(1,cod.intValue());
				stmt.setInt(2,id);
				
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void changeMedicEmail(String email,Long codMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="Update medic set medic_email=? where medic_cont_id=?";
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setString(1,email);
				stmt.setLong(2,codMedic);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertMedicUser(String email,String parola,int id) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO Cont(cont_email,cont_parola,cont_medic_cod) values(?,?,?)";
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
	public static void insertReceptionerUser(String email,String parola,int id) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO Cont(cont_email,cont_parola,cont_receptioner_cod) values(?,?,?)";
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

	public static void insertAnaliza(Analiza analiza) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="INSERT INTO analiza (analiza_denumire, analiza_pret, analiza_durata,analiza_tip,categorie_id,indicator_id)" + 
				"  VALUES(?,?,?,?,?,LAST_INSERT_ID());";
		
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setString(1, analiza.getDenumire());
				stmt.setLong(2, analiza.getPret());
				stmt.setString(3, analiza.getDurata());
				stmt.setString(4, analiza.getTip());
				stmt.setString(5, analiza.getCategorie());
				stmt.executeUpdate();
				
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertIndicator(Indicator indicator) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query= "INSERT INTO indicator (indicator_interval_referinta_min,indicator_interval_referinta_max,indicator_sex,indicator_tip_persoana,indicator_analiza_cod)" + 
				"  VALUES(?,?,?,?,?)";
	
				
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,indicator.getIntervalRefMin());
				stmt.setString(2,indicator.getIntervalRefMax());
				stmt.setString(3, indicator.getSex());
				stmt.setString(4, indicator.getTipPersoana());
				stmt.setString(5, indicator.getAnalizacod());
				stmt.executeUpdate();
			

	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void setCompatibility() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="set @@global.show_compatibility_56=ON;";
		try {
			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static Long GetDayFromDate(Date data) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="select zi_id from clinicamedicala.zi where zi_denumire=clinicamedicala.ro_dayname(?)";
		Long zi=null;
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setDate(1,data);
				rs=stmt.executeQuery();
				if(rs.next()) 
				 zi=rs.getLong("zi_id");
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return zi;
	}
	
	public static Map<Long, String> getZileNelucrateMedic(String codMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map<Long, String> zile=new HashMap<Long,String>();
		String query="select zi_denumire,zi_id from clinicamedicala.zi where zi_denumire not in (select zi_denumire from clinicamedicala.zi,clinicamedicala.areprogram where zi_id=areprogram_zi_id and areprogram_medic_cod=? and areprogram_valid=1) and zi_denumire not in ('Sâmbătă','Duminică')";
		String zi=null;
		Long ziId=null;
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setString(1,codMedic);
				rs=stmt.executeQuery();
				while(rs.next()) {
				 zi=rs.getString("zi_denumire");
				 ziId=rs.getLong("zi_id");
				zile.put(ziId,zi);
				}
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return zile;
	}
	
	public static Map<String, Long> getNumarMediciPerSpecialitate() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map<String,Long> nrMedici=new HashMap<String,Long>();
		String query="select count(medic_cod) nr,specialitate_denumire from clinicamedicala.medic,clinicamedicala.specialitate " + 
				"where medic.specialitate_cod=specialitate.specialitate_cod and medic_valid=1 group by medic.specialitate_cod";
		
		
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				rs=stmt.executeQuery();
				while(rs.next()) {
				nrMedici.put(rs.getString("specialitate_denumire"),rs.getLong("nr"));
				}
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return nrMedici;
	}
	public static List<String> getZileProgramLucruMedic(String codMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> zile=new ArrayList<String>();
		String query=" select zi_denumire from zi where zi_denumire in " + 
				"(select zi_denumire from zi,areprogram" + 
				" where zi_id=areprogram_zi_id and areprogram_medic_cod=? and areprogram_valid=1)" + 
				" and zi_denumire not in ('Sâmbătă','Duminică')";
		String zi=null;
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setString(1,codMedic);
				rs=stmt.executeQuery();
				while(rs.next()) {
				 zi=rs.getString("zi_denumire");
				zile.add(zi);
				}
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return zile;
	}
	public static boolean  hasProgramInThatDay(Date data,Long codMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		setCompatibility();
		String query=" select areprogram_zi_id from clinicamedicala.areprogram where (select zi_id from clinicamedicala.zi where zi_denumire=clinicamedicala.ro_dayname(?))=areprogram_zi_id and areprogram_medic_cod=? and areprogram_valid=1";
		Boolean hasProgram=false;
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setDate(1,data);
				stmt.setLong(2, codMedic);
				rs=stmt.executeQuery();
				while(rs.next()) 
				hasProgram=true;
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return hasProgram;
	}
	public static List<String> getPogramMedic(Long codMedic,Date data) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		System.out.println(data);
		String query=" select str_to_date(areprogram_ora_inceput,\"%H:%i\") orainceput,str_to_date(areprogram_ora_sfarsit,\"%H:%i\") orasfarsit from "
				+ "clinicamedicala.areprogram where areprogram_medic_cod=? and  areprogram_valid=1 and areprogram_zi_id=(select zi_id from clinicamedicala.zi where zi_denumire=clinicamedicala.ro_dayname(?))"; 
		 List<String> oreProgram=new ArrayList<String>();
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setLong(1, codMedic);
				stmt.setDate(2, data);
				rs=stmt.executeQuery();
				if(rs.next()) {
					//System.out.println(rs.getString("orainceput")+"-----"+rs.getString("orasfarsit"));
					oreProgram.add(rs.getString("orainceput"));
					oreProgram.add(rs.getString("orasfarsit"));
				}
				
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	return oreProgram;
	}
	public static List<ProgramLucru> getProgramMedic(String codMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query=" SELECT areprogram_ora_inceput,areprogram_ora_sfarsit,zi_denumire FROM clinicamedicala.areprogram,clinicamedicala.zi " + 
				"where areprogram_zi_id=zi.zi_id and areprogram_medic_cod=?";
			
		List<ProgramLucru> oreProgram=new ArrayList<ProgramLucru>();
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setString(1, codMedic);
				System.out.println(query);
				rs=stmt.executeQuery();
				while(rs.next()) {
					ProgramLucru program=new ProgramLucru();
					program.setOraInceput(rs.getString("areprogram_ora_inceput"));
					program.setOraSfarsit(rs.getString("areprogram_ora_sfarsit"));
					program.setZi(rs.getString("zi_denumire"));
				oreProgram.add(program);
				}
				
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	return oreProgram;
	}
	public static Persoana getPacientFromCod(String codPacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query=" SELECT * from pacient where pacient_cod=?";
			
		Persoana pacient=new Persoana();
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setString(1, codPacient);
				System.out.println(query);
				rs=stmt.executeQuery();
				if(rs.next()) {
					pacient.setCnp(rs.getString("pacient_cnp"));
					pacient.setId(rs.getLong("pacient_cod"));
					pacient.setNume(rs.getString("pacient_nume"));
					pacient.setPrenume(rs.getString("pacient_prenume"));
					pacient.setVarsta(DateUtil.getVarsta(rs.getDate("pacient_data_nastere")));
					if(rs.getString("pacient_cnp").startsWith("1")) {
					pacient.setSex("M");
					}
					else {
						pacient.setSex("F");
					}
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
	
	public static List<Consultatie> getConsultatii(String medic,String data) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//System.out.println(data);
		String query="select programareconsultatie_status,programareconsultatie_ora_consultatie,programareconsultatie_data_consultatie,CONCAT(pacient_nume, ' ', pacient_prenume) pacient,medic_cod medic,serviciu_denumire " + 
				"from programareconsultatie,pacient,medic,serviciu " + 
				"where programareconsultatie_tip_consultatie=serviciu_cod" + 
				" and programareconsultatie_medic_cod=medic_cod" + 
				" and programareconsultatie_pacient_cod=pacient_cod" + 
				" and programareconsultatie_data_consultatie=?" + 
				" and programareconsultatie_medic_cod= ? and programareconsultatie_status='in curs'";
		
		List<Consultatie> consultatii=new ArrayList<Consultatie>();
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setString(1, data);
				stmt.setString(2, medic);
				rs=stmt.executeQuery();
				while(rs.next()) {
					Consultatie consultatie=new Consultatie();
					consultatie.setStatus(rs.getString("programareconsultatie_status"));
					consultatie.setOraInceput(rs.getString("programareconsultatie_ora_consultatie"));
					consultatie.setPacient(rs.getString("pacient"));
					consultatie.setTipConsutatie(rs.getString("serviciu_denumire"));
					consultatie.setData(rs.getDate("programareconsultatie_data_consultatie"));
					consultatie.setMedic(rs.getString("medic"));
					consultatii.add(consultatie);

				}

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return consultatii;
	}
	public static List<Consultatie> getConsultatiiPentruOziAsaptamanii(String medic,String zi) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//System.out.println(data);
		String query="select programareconsultatie_ora_consultatie,programareconsultatie_data_consultatie,CONCAT(pacient_nume, ' ', pacient_prenume) "
				+ "pacient,serviciu_denumire from programareconsultatie ,pacient,serviciu" + 
				" where ro_dayname(programareconsultatie_data_consultatie)=?" + 
				" and programareconsultatie_status='in curs' and programareconsultatie_pacient_cod=pacient_cod" + 
				" and serviciu_cod=programareconsultatie_tip_consultatie and programareconsultatie_medic_cod=?";
		
		List<Consultatie> consultatii=new ArrayList<Consultatie>();
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setString(1, zi);
				stmt.setLong(2, Long.valueOf(medic));
				rs=stmt.executeQuery();
				while(rs.next()) {
					Consultatie consultatie=new Consultatie();
					consultatie.setOraInceput(rs.getString("programareconsultatie_ora_consultatie"));
					consultatie.setPacient(rs.getString("pacient"));
					consultatie.setTipConsutatie(rs.getString("serviciu_denumire"));
					consultatie.setData(rs.getDate("programareconsultatie_data_consultatie"));
					consultatii.add(consultatie);

				}

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return consultatii;
	}
	public static List<Consultatie> getConsultatii(String medic,String data,String cnp) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="select programareconsultatie_status,programareconsultatie_ora_consultatie,programareconsultatie_data_consultatie,CONCAT(pacient_nume, ' ', pacient_prenume) pacient,medic_cod medic,serviciu_denumire " + 
				"from programareconsultatie,pacient,medic,serviciu " + 
				"where programareconsultatie_tip_consultatie=serviciu_cod" + 
				" and programareconsultatie_medic_cod=medic_cod" + 
				" and programareconsultatie_pacient_cod=pacient_cod" + 
				" and programareconsultatie_data_consultatie=?" + 
				" and programareconsultatie_medic_cod= ?"+
				" and programareconsultatie_pacient_cod= ?";
		
		List<Consultatie> consultatii=new ArrayList<Consultatie>();
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setString(1, data);
				stmt.setString(2, medic);
				stmt.setLong(3, getCodPacientFromCnp(cnp));
				rs=stmt.executeQuery();
				while(rs.next()) {
					Consultatie consultatie=new Consultatie();
					consultatie.setStatus(rs.getString("programareconsultatie_status"));
					consultatie.setOraInceput(rs.getString("programareconsultatie_ora_consultatie"));
					consultatie.setPacient(rs.getString("pacient"));
					consultatie.setTipConsutatie(rs.getString("serviciu_denumire"));
					consultatie.setData(rs.getDate("programareconsultatie_data_consultatie"));
					consultatie.setMedic(rs.getString("medic"));
					consultatii.add(consultatie);

				}

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
		return consultatii;
	}
	public static void changeConsultatieStatus(String pacient,String medic,Date data,String status) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//System.out.println(pacient+""+medic+""+data);
		String query="Update programareconsultatie set programareconsultatie_status=? where programareconsultatie_pacient_cod=(select pacient_cod from pacient "
				+ "where CONCAT(pacient_nume, ' ', pacient_prenume)=?)and programareconsultatie_data_consultatie=? and programareconsultatie_medic_cod=?"
				+ " and programareconsultatie_status='in curs'";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,status);
				stmt.setString(2,pacient);
				stmt.setDate(3,data);
				stmt.setString(4,medic);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void changeAnalizaStatus(String pacient,String analiza,String status) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="Update programareanaliza set programareanaliza_status=? where programareanaliza_pacient_cod=? and"
				+ " programareanaliza_analiza_cod=(select analiza_cod from analiza where analiza_denumire=?) and programareanaliza_status='in curs'";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,status);
				stmt.setString(2,pacient);
				stmt.setString(3,analiza);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void anuleazaProgramPeOZi(String medic,String zi) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//System.out.println(medic+""+data);
		String query="update areprogram set areprogram_valid=0 where areprogram_medic_cod=?" + 
				" and areprogram_zi_id=(select zi_id from zi where zi_denumire=?)";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setLong(1,Long.valueOf(medic));
				stmt.setString(2,zi);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	
	public static void anuleazaToateConsultatiileDinZi(String medic,Date data) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//System.out.println(medic+""+data);
		String query="Update programareconsultatie set programareconsultatie_status='anulat' where programareconsultatie_data_consultatie=?"
				+ " and programareconsultatie_medic_cod=? and programareconsultatie_status='in curs' ";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setDate(1,data);
				stmt.setString(2,medic);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	
	public static void anuleazaToateConsultatiileDinZiaSaptamanii(String medic,String zi) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="Update programareconsultatie set programareconsultatie_status='anulat'"+
				" where ro_dayname(programareconsultatie_data_consultatie)=? and programareconsultatie_medic_cod=? "
				+ "and programareconsultatie_status='in curs'";
		try {

			if (conn != null) {
			
				stmt=conn.prepareStatement(query);
				stmt.setString(1,zi);
				stmt.setString(2,medic);
				stmt.executeUpdate();
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static boolean  checkIfConsultatieExists(String codMedic,String codPacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query=" select * from programareconsultatie where programareconsultatie_status='in curs'"+
		"and programareconsultatie_medic_cod=? and  programareconsultatie_pacient_cod=?";
		boolean hasProgramare=false;
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setLong(1,Long.valueOf(codMedic));
				stmt.setLong(2, Long.valueOf(codPacient));
				rs=stmt.executeQuery();
				if(rs.next()) {
					hasProgramare=true;
				}
					
				
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	return hasProgramare;
	}
	
	public static List<Interval>  getBusyHoursConsultatie(Date data,Long codMedic) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//System.out.println("DATA"+data);
		String query=" select programareconsultatie_ora_consultatie,ADDTIME(programareconsultatie_ora_consultatie,ofera_timp) ora" + 
				"	 from clinicamedicala.areprogram ,clinicamedicala.programareconsultatie,clinicamedicala.ofera  where areprogram_valid=1 "
				+ "and  areprogram_medic_cod=programareconsultatie_medic_cod and areprogram_zi_id= (select zi_id from clinicamedicala.zi where"
				+ " zi_denumire=clinicamedicala.ro_dayname(programareconsultatie_data_consultatie)) and programareconsultatie_tip_consultatie=serviciu_cod "
				+ "and programareconsultatie_data_consultatie=? and areprogram_medic_cod=? and programareconsultatie_medic_cod=ofera.medic_cod order by ora"; 
		List<Interval> ore=new ArrayList<Interval>();
		try {

			if (conn != null) {
				stmt=conn.prepareStatement(query);
				stmt.setDate(1,data);
				
				stmt.setLong(2, codMedic);
				rs=stmt.executeQuery();
				while(rs.next()) {
					Interval interval=new Interval();
					interval.setInceput(rs.getString("programareconsultatie_ora_consultatie"));
					interval.setSfarsit(rs.getString("ora"));
					//System.out.println("Ore-"+rs.getString("ora"));
					ore.add(interval);
				}
					
				
	}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	return ore;
	}
	
	public static void insertConsultatie(String detalii,String status,String codServiciu,String oraCons,Date data,String medicCod,String codPacient) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="insert into programareconsultatie values(?,?,?,?,?,?,?)";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, detalii);
				stmt.setString(2, status);
				stmt.setLong(3, Long.valueOf(codServiciu));
				stmt.setString(4, oraCons);
				stmt.setDate(5,data);
				stmt.setLong(6, Long.valueOf(medicCod));
				stmt.setLong(7, Long.valueOf(codPacient));
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertConsultatie(Consultatie consultatie) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="insert into programareconsultatie values(?,?,?,?,?,?,(select pacient_cod from pacient"+ 
						" where CONCAT(pacient_nume, ' ', pacient_prenume)=?))";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, consultatie.getDetalii());
				stmt.setString(2, "in curs");
				stmt.setString(3, consultatie.getTipConsutatie());
				stmt.setString(4, consultatie.getOraInceput());
				stmt.setDate(5,consultatie.getData());
				stmt.setString(6, consultatie.getMedic());
				stmt.setString(7, consultatie.getPacient());
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	public static void insertProgramLucru(String codMedic,String oraInceput,String oraSfarsit,Long ziCod) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="insert into areprogram(areprogram_ora_inceput,areprogram_ora_sfarsit,areprogram_medic_cod,areprogram_zi_id,areprogram_valid)"
				+ " values(?,?,?,?,1)";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, oraInceput);
				stmt.setString(2, oraSfarsit);
				stmt.setLong(3, Long.valueOf(codMedic));
				stmt.setLong(4, ziCod);
				
						stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			CloseResources(conn, rs, stmt);
		}
	}
	
	public static void insertProgramLucru(String codMedic,String oraInceput,String oraSfarsit,String zi) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query="insert into areprogram(areprogram_ora_inceput,areprogram_ora_sfarsit,areprogram_medic_cod,areprogram_zi_id,areprogram_valid)"
				+ " values(?,?,?,(select zi_id from zi where zi_denumire=?),1)";
		try {

			if (conn != null) {
				
				stmt=conn.prepareStatement(query);
				stmt.setString(1, oraInceput);
				stmt.setString(2, oraSfarsit);
				stmt.setLong(3, Long.valueOf(codMedic));
				stmt.setString(4, zi);
				
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
