package pkg.Servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Consultatie;
import pkg.Entities.Persoana;
import pkg.Utils.ConsultatiiHelper;
import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;
import pkg.Utils.SMTPHelper;

/**
 * Servlet implementation class AnuleazaProgramare
 */
public class AnuleazaProgramare extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws Exception 
     * @see HttpServlet#HttpServlet()
     */
	public Consultatie getConsultatieUrmatoare(String codMedic,String codServiciu) throws Exception {
		Consultatie consultatie=new Consultatie();
		String dataFormatata=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String dataProgramare=DateUtil.addDayToCurrentDate(dataFormatata, 1);
		while(!DbOperations.hasProgramInThatDay(DateUtil.getDateFromString(dataProgramare), Long.valueOf(codMedic))) {
			dataProgramare=DateUtil.addDayToCurrentDate(dataProgramare, 1);
		}
		List<String> oredisponibile=ConsultatiiHelper.getOreDisp(codMedic, dataProgramare, codServiciu);
		while(oredisponibile.isEmpty()) {
			dataProgramare=DateUtil.addDayToCurrentDate(dataProgramare, 1);
			
		}
		consultatie.setData(DateUtil.getDateFromString(dataProgramare));
		consultatie.setOraInceput(ConsultatiiHelper.getOreDisp(codMedic, dataProgramare, codServiciu).get(0));
		consultatie.setTipConsutatie(codServiciu);
		consultatie.setMedic(codMedic);
		return consultatie;
	}
    public AnuleazaProgramare() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String metoda=request.getParameter("verif");
		System.out.println("Metoda:"+metoda);
		String pacient=request.getParameter("pacient");
		Persoana user=(Persoana) request.getSession().getValue("persoanaLogata");
		String tipUser=(String)request.getSession().getValue("tipUser");
		String idMedic=null;
		String serviciu=request.getParameter("serviciu");
		if(tipUser=="medic") {
			idMedic=DbOperations.getUserCodFromPassword(user,tipUser).toString();
		}
		else {
		 idMedic=request.getParameter("medic");
		}
		System.out.println("IDMEDIC"+idMedic);
		String msg=null;
		String continut=null;
		if(metoda!=null) {
			java.sql.Date data = null;
			System.out.println("dataaa"+request.getParameter("data"));
			if(tipUser=="medic") {
			try { 
				data = DateUtil.getDateFromString(request.getParameter("data"));
				 continut="Buna ziua,\n" + 
						"Va anuntam ca programarea din data de "+data+" la medicul "+DbOperations.getNumePrenumeMedic(Long.valueOf(idMedic))+" a fost anulata.";  
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			try {
			switch(metoda) {
			case "anulare":
				DbOperations.changeConsultatieStatus(pacient, idMedic, data,"anulat");
				if(tipUser=="medic") {
					Consultatie consultatie=getConsultatieUrmatoare(idMedic, DbOperations.getCodServiciuFromDenServiciu(serviciu).toString());
					consultatie.setPacient(pacient);
					continut+="Ati fost reprogramat/a in data de "+consultatie.getData()+",ora "+consultatie.getOraInceput()+"."
							+ "\n In caz ca doriti sa anulati programarea,va invitam sa sunati"
							+ " la numarul 0764324575 sau sa va prezentati la clinica.\n Va multumim pentru intelegere!";
					SMTPHelper.SendEmail(Arrays.asList(DbOperations.getPacientEmail(pacient)), continut, "Anulare consultatie");
					DbOperations.insertConsultatie(consultatie);
				}
				 msg="Programarea a fost anulata";
				//request.setAttribute("msg", msg);
				break;
			case "anularetotala":
				
				if(tipUser=="medic") {
					DbOperations.anuleazaToateConsultatiileDinZi(idMedic, data);
					for(String email:DbOperations.getEmailuriPacienti(idMedic,data)) {
						Consultatie consultatie=getConsultatieUrmatoare(idMedic, DbOperations.getCodServiciuFromDenServiciu(serviciu).toString());
						consultatie.setPacient(pacient);
						continut+="Ati fost reprogramat/a in data de "+consultatie.getData()+",ora "+consultatie.getOraInceput()+"."
								+ "\n In caz ca doriti sa anulati programarea,va invitam sa sunati"
								+ " la numarul 0764324575 sau sa va prezentati la clinica.\n Va multumim pentru intelegere!";
						SMTPHelper.SendEmail(Arrays.asList(email), continut, "Anulare consultatie");
						DbOperations.insertConsultatie(consultatie);
					}
				}
				
				 msg="Programarile au fost anulate";
			}
			}catch(Exception e) {
				System.out.println("Exceptieee: "+e.getMessage());
				e.printStackTrace();
			}
			
		}
		String dataFormatata=request.getParameter("data1");
		System.out.println(dataFormatata);
		request.setAttribute("consultatii", DbOperations.getConsultatii(idMedic, dataFormatata));
		if(metoda!=null) {
		response.getWriter().write(msg);
		response.getWriter().flush();
		response.getWriter().close();
	
		}else {
			request.getRequestDispatcher("AnulareProgramari.jsp").forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
