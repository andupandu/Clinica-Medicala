package pkg.Servlets;

import java.util.List;
import java.io.IOException;
import java.text.ParseException;

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
 * Servlet implementation class AdaugaZiLibera
 */
public class AdaugaZiLibera extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdaugaZiLibera() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		Persoana user=(Persoana) request.getSession().getValue("persoanaLogata");
		String tipUser=(String)request.getSession().getValue("tipUser");
		 String idMedic=DbOperations.getUserCodFromPassword(user,tipUser).toString();
		 String optiune=request.getParameter("tip");
		 String data=request.getParameter("data");
		 List<Consultatie> consultatii=DbOperations.getConsultatii(idMedic, data);
		 if(optiune!=null) {
			 String jsonResponse = "";
				
			 
			 System.out.println(consultatii.size());
			if (consultatii.size()>0) {
				jsonResponse = "{" + "\"areConsultatii\":true," + "\"consultatii\":[";
				for(Consultatie c : consultatii) {
					String pacient = c.getPacient();
					String serviciu = c.getTipConsutatie();
					String oraInceput = c.getOraInceput();
					
					jsonResponse += String.format("{\"pacient\":\"%s\", \"serviciu\":\"%s\", \"oraInceput\":\"%s\"},", pacient, serviciu, oraInceput);
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
			
			// daca nu exista deja ziua libera pt medic adauga zi libera
			 if(!DbOperations.areZiuaLibera(Long.valueOf(idMedic), date)) {
				try {
					String continut="Buna ziua,\n" + 
						"Va anuntam ca programarea din data de "+date+"la medicul "+DbOperations.getNumePrenumeMedic(Long.valueOf(idMedic))+" a fost anulata" + 
						"Va rugam sa reveniti cu un telefon la 07555555555 pentru a muta consultatia .\n" + 
						"Va multumim pentru intelegere!";
					if (consultatii.size()>0) {
					DbOperations.anuleazaToateConsultatiileDinZi(idMedic, DateUtil.getDateFromString(date));
					SMTPHelper.SendEmail(DbOperations.getEmailuriPacienti(idMedic,DateUtil.getDateFromString(date)), continut, "Anulare consultatie");

					DbOperations.insertZiLibera(Long.valueOf(idMedic), date);
				}} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("msg", "Ziua libera a fost adaugata");
			 }else {
				 request.setAttribute("msg", "Aceasta zi este trecuta deja ca zi libera!");
			 }
				
				request.getRequestDispatcher("ZileLibereMedic.jsp").forward(request,response);
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
