package pkg.Servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Consultatie;
import pkg.Entities.Persoana;
import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;
import pkg.Utils.SMTPHelper;

/**
 * Servlet implementation class ModificaProgramMedic
 */
public class ModificaProgramMedic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaProgramMedic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String idMedic=(String)request.getSession().getValue("idMedic");
		 String optiune=request.getParameter("tip");
		 String zi=request.getParameter("zi");
		 String oraInceput=request.getParameter("orainceput");
		 String oraSfarsit=request.getParameter("orasfarsit");
		 List<Consultatie> consultatii=DbOperations.getConsultatiiPentruOziAsaptamanii(idMedic,zi);
		 if(optiune!=null) {
			 String jsonResponse = "";
			 System.out.println(consultatii.size());
			if (consultatii.size()>0) {
				jsonResponse = "{" + "\"areConsultatii\":true," + "\"consultatii\":[";
				for(Consultatie c : consultatii) {
					String pacient = c.getPacient();
					String serviciu = c.getTipConsutatie();
					String oraCons = c.getOraInceput();
					Date data=c.getData();
					jsonResponse += String.format("{\"pacient\":\"%s\", \"serviciu\":\"%s\", \"oraCons\":\"%s\", \"data\":\"%s\"},", pacient, serviciu, oraCons,data);
				}
				
				jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 1);
				
				jsonResponse += "]}";
			}else {
				jsonResponse = "{" + "\"areConsultatii\":false }";
			}
			
			response.getWriter().append(jsonResponse);
		 }
		 else {
			 String date=request.getParameter("data1");
			
				try {
					String continut="Buna ziua,\n" + 
						"Va anuntam ca programarea din data de "+date+"la medicul "+DbOperations.getNumePrenumeMedic(Long.valueOf(idMedic))+" a fost anulata" + 
						"Va rugam sa reveniti cu un telefon la 07555555555 pentru a muta consultatia .\n" + 
						"Va multumim pentru intelegere!";
					if (consultatii.size()>0) {
					SMTPHelper.SendEmail(DbOperations.getEmailuriPacienti(idMedic,zi), continut, "Anulare consultatie");
					DbOperations.anuleazaToateConsultatiileDinZiaSaptamanii(idMedic, zi);
					DbOperations.anuleazaProgramPeOZi(idMedic, zi);
					DbOperations.insertProgramLucru(idMedic, oraInceput, oraSfarsit, zi);
					
					}
					} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("msg", "Programul de lucru a fost modificat");
				
				request.getRequestDispatcher("ModificaProgramMedic.jsp").forward(request,response);
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
